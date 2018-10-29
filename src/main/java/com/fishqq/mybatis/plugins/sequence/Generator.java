package com.fishqq.mybatis.plugins.sequence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;

public class Generator {
    private long id;
    private long first;
    private long last;

    private final long step;
    private final Lock lock;
    private final String tableName;

    public Generator(long step, String tableName) {
        this.step = step;
        this.lock = new ReentrantLock();
        this.tableName = tableName;
    }

    public long next(DataSource dataSource) {
        lock.lock();

        try {
            if (id == last) {
                first = nextRange(dataSource);
                last = first + step - 1;
                id = first;
            } else {
                id++;
            }
        } finally {
            lock.unlock();
        }

        return id;
    }

    public long nextRange(DataSource dataSource) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet resultSet = null;

        long preStart;
        long newStart;

        try {
            conn = dataSource.getConnection();

            stat = conn.prepareStatement(sqlOfGetId());
            resultSet = stat.executeQuery();

            if (resultSet.next()) {
                preStart = resultSet.getLong(1);

                if (preStart < 0) {
                    throw new SequenceException(
                        String.format("bad sequence value in db: value=%d, table=%s",
                        preStart, this.tableName));
                }

                if (resultSet.next()) {
                    throw new SequenceException("there are more than one sequence value in db: " + tableName);
                }

                close(resultSet);
                resultSet = null;

                close(stat);
                stat = null;

                newStart = preStart + this.step;

                stat = conn.prepareStatement(sqlOfUpdateId());
                stat.setLong(1, newStart);
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

                newStart = 1L;

                stat = conn.prepareStatement(sqlOfInsertId());
                stat.setLong(1, newStart);
                stat.setDate(2, new Date(System.currentTimeMillis()));
                int r = stat.executeUpdate();
                if (r != 1) {
                    throw new SequenceException("insert sequence value failed: " + tableName);
                }
            }
        } catch (SQLException e) {
            throw new SequenceException("get sequence from db error: " + e.getMessage(), e.getCause());
        } finally {
            close(resultSet);
            close(stat);
            close(conn);
        }

        return newStart;
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
        return String.format("select current_id from %s", this.tableName);
    }
    private String sqlOfUpdateId() {
        return String.format("update %s set current_id = ?, last_fetch_time = ?", tableName);
    }
    private String sqlOfInsertId() {
        return String.format("insert into %s(current_id, last_fetch_time) values(?, ?)", tableName);
    }
}
