package com.fishqq.mybatis.plugins.sequence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;

/**
 * generate sequence
 */
public class Generator {
    private long id;
    private long end;
    private boolean inited;

    private final long step;
    private final Lock lock;
    private final String tableName;

    public Generator(long step, String tableName) {
        this.step = step;
        this.lock = new ReentrantLock();
        this.tableName = tableName;
    }

    /**
     * generate next sequence
     *
     * @param dataSource
     * @return
     */
    public long next(DataSource dataSource) {
        return next(dataSource, 1);
    }

    /**
     * generate a batch of sequence: [next, next + batch]
     *
     * @param dataSource
     * @param batch
     * @return next sequence
     */
    public long next(DataSource dataSource, int batch) {
        lock.lock();

        try {
            long next;
            long remain = end - id;

            if (remain < batch) {
                long need = ((batch - remain) / step + 1) * step;
                long preEnd = batchFromDb(dataSource, need);
                end = preEnd + need;

                if (!inited) {
                    id = preEnd;
                }
            }

            next = id + 1;

            id += batch;

            inited = true;

            return next;
        } finally {
            lock.unlock();
        }
    }

    private long batchFromDb(DataSource dataSource, long batch) {
        Connection conn;

        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            throw new SequenceException("connection failed", e.getCause());
        }

        boolean oldAutoCommit;

        try {
            oldAutoCommit = conn.getAutoCommit();
        } catch (SQLException e) {
            close(conn);
            throw new SequenceException("get auto commit failed", e.getCause());
        }

        long end;

        try {
            conn.setAutoCommit(false);

            end = batchFromDb(conn, batch);

            conn.commit();
        } catch (Throwable e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("rollback failed: " + e.getMessage());
            }

            throw new SequenceException("get sequence from db error: " + e.getMessage(), e.getCause());
        } finally {
            try {
                conn.setAutoCommit(oldAutoCommit);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            close(conn);
        }

        return end;
    }

    private long batchFromDb(Connection conn, long batch) throws SQLException {
        long preEnd;
        long newEnd;

        PreparedStatement stat = null;
        ResultSet resultSet = null;

        try {
            stat = conn.prepareStatement(sqlOfGetId());
            resultSet = stat.executeQuery();

            if (resultSet.next()) {
                preEnd = resultSet.getLong(1);

                if (preEnd < 0) {
                    throw new SequenceException(
                        String.format("bad sequence value in db: value=%d, table=%s",
                            preEnd, this.tableName));
                }

                if (resultSet.next()) {
                    throw new SequenceException("there are more than one sequence value in db: " + tableName);
                }

                close(resultSet);
                resultSet = null;

                close(stat);
                stat = null;

                newEnd = preEnd + batch;

                stat = conn.prepareStatement(sqlOfUpdateId());
                stat.setLong(1, newEnd);
                stat.setDate(2, new Date(System.currentTimeMillis()));
                int r = stat.executeUpdate();
                if (r != 1) {
                    throw new SequenceException("update sequence value failed: " + tableName);
                }
            } else {
                close(resultSet);
                resultSet = null;

                close(stat);
                stat = null;

                preEnd = 0;
                newEnd = batch;

                stat = conn.prepareStatement(sqlOfInsertId());
                stat.setLong(1, newEnd);
                stat.setDate(2, new Date(System.currentTimeMillis()));
                int r = stat.executeUpdate();
                if (r != 1) {
                    throw new SequenceException("insert sequence value failed: " + tableName);
                }
            }
        } finally {
            close(resultSet);
            close(stat);
        }

        return preEnd;
    }

    private void close(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                System.out.println("close error:" + e.getMessage());
            }
        }
    }

    private String sqlOfGetId() {
        return String.format("select current_id from %s for update", this.tableName);
    }
    private String sqlOfUpdateId() {
        return String.format("update %s set current_id = ?, last_fetch_time = ?", tableName);
    }
    private String sqlOfInsertId() {
        return String.format("insert into %s(current_id, last_fetch_time) values(?, ?)", tableName);
    }
}
