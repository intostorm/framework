package com.ccesun.framework.core.druid;

import java.sql.SQLException;
import java.sql.Types;

import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.proxy.jdbc.CallableStatementProxy;
import com.alibaba.druid.proxy.jdbc.JdbcParameter;
import com.alibaba.druid.proxy.jdbc.PreparedStatementProxy;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.ccesun.framework.util.StringUtils;


public abstract class CustomLogFilter extends
		com.alibaba.druid.filter.logging.LogFilter {

	protected boolean           statementCreateAfterLogEnable        = false;
	protected boolean           statementPrepareAfterLogEnable       = false;
	protected boolean           statementPrepareCallAfterLogEnable   = false;
    
	protected boolean           statementExecuteAfterLogEnable       = true;
	protected boolean           statementExecuteQueryAfterLogEnable  = true;
	protected boolean           statementExecuteUpdateAfterLogEnable = true;
	protected boolean           statementExecuteBatchAfterLogEnable  = true;
	
	protected boolean           statementCloseAfterLogEnable         = true;

	protected boolean           statementParameterSetLogEnable       = true;
    
	@Override
    public void statement_close(FilterChain chain, StatementProxy statement) throws SQLException {
    }
	
	private String replacePramas(String sql, StatementProxy statement) {
        for (JdbcParameter parameter : statement.getParameters().values()) {
            int sqlType = parameter.getSqlType();
            Object value = parameter.getValue();
            String param = StringUtils.EMPTY;
            switch (sqlType) {
                case Types.VARCHAR:
                	param = value == null ? "null" : "'" + String.valueOf(value) + "'";
                    break;
                default:
                	param = String.valueOf(value);
                    break;
            }
            
            sql = StringUtils.replaceOnce(sql, "?", param);
        }
    	return sql;
    }
	
    @Override
    protected void statementExecuteBefore(StatementProxy statement, String sql) {
        statement.setLastExecuteStartNano();
        if (statementExecuteAfterLogEnable && isStatementLogEnabled()) {
            statement.setLastExecuteTimeNano();
            statementLog("SQL - " + replacePramas(sql, statement));
        }
    }
    
    @Override
    protected void statementExecuteAfter(StatementProxy statement, String sql, boolean firstResult) {
    }

    @Override
    protected void statementExecuteBatchBefore(StatementProxy statement) {
        statement.setLastExecuteStartNano();
        if (statementExecuteBatchAfterLogEnable && isStatementLogEnabled()) {
            statement.setLastExecuteTimeNano();

            String sql;
            if (statement instanceof PreparedStatementProxy) {
                sql = ((PreparedStatementProxy) statement).getSql();
            } else {
                sql = statement.getBatchSql();
            }

            statementLog("SQL - " + replacePramas(sql, statement));
        }
    }

    @Override
    protected void statementExecuteBatchAfter(StatementProxy statement, int[] result) {
    }

    @Override
    protected void statementExecuteQueryBefore(StatementProxy statement, String sql) {
        statement.setLastExecuteStartNano();
        if (statementExecuteQueryAfterLogEnable && isStatementLogEnabled()) {
            statement.setLastExecuteTimeNano();
            statementLog("SQL - " + replacePramas(sql, statement));
        }
    }

    @Override
    protected void statementExecuteQueryAfter(StatementProxy statement, String sql, ResultSetProxy resultSet) {
        
    }

    @Override
    protected void statementExecuteUpdateBefore(StatementProxy statement, String sql) {
        statement.setLastExecuteStartNano();
        if (statementExecuteUpdateAfterLogEnable && isStatementLogEnabled()) {
            statement.setLastExecuteTimeNano();
            statementLog("SQL - " + replacePramas(sql, statement));
        }
    }

    @Override
    protected void statementExecuteUpdateAfter(StatementProxy statement, String sql, int updateCount) {
    }
    
    protected void statementCreateAfter(StatementProxy statement) {
    }

    protected void statementPrepareAfter(PreparedStatementProxy statement) {
    }

    protected void statementPrepareCallAfter(CallableStatementProxy statement) {
    }
	
    @Override
    public void preparedStatement_clearParameters(FilterChain chain, PreparedStatementProxy statement) throws SQLException {
        chain.preparedStatement_clearParameters(statement);
    }
}
