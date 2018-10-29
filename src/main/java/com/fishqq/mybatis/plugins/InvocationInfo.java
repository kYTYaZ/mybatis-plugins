package com.fishqq.mybatis.plugins;

import javax.sql.DataSource;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

public class InvocationInfo {
    public final Invocation invocation;
    private final Object[] args;

    private static final int MAPPED_STATEMENT_INDEX = 0;
    private static final int PARAMETER_INDEX = 1;
    private static final int ROW_BOUNDS_INDEX = 2;
    private static final int RESULT_HANDLER_INDEX = 3;

    public InvocationInfo(Invocation invocation) {
        this.invocation = invocation;
        this.args = invocation.getArgs();
    }

    public Executor getTarget() {
        return (Executor) this.invocation.getTarget();
    }

    public DataSource getDataSource() {
        return this.getMappedStatement().getConfiguration().getEnvironment().getDataSource();
    }

    public MappedStatement getMappedStatement() {
        return (MappedStatement)args[MAPPED_STATEMENT_INDEX];
    }

    public void setMappedStatement(MappedStatement mappedStatement) {
        args[MAPPED_STATEMENT_INDEX] = mappedStatement;
    }

    public Object getParameter() {
        return args[PARAMETER_INDEX];
    }

    public void setParameter(Object parameter) {
        args[PARAMETER_INDEX] = parameter;
    }

    public RowBounds getRowBounds() {
        return (RowBounds)args[ROW_BOUNDS_INDEX];
    }

    public void setRowBounds(RowBounds rowBounds) {
        args[ROW_BOUNDS_INDEX] = rowBounds;
    }

    public ResultHandler getResultHandler() {
        return (ResultHandler)args[RESULT_HANDLER_INDEX];
    }

    public void setResultHandler(ResultHandler resultHandler) {
        args[RESULT_HANDLER_INDEX] = resultHandler;
    }

    public BoundSql getBoundSql() {
        return getMappedStatement().getBoundSql(getParameter());
    }
}
