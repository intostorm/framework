package com.ccesun.framework.core.druid;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class CustomLog4jFilter extends CustomLogFilter {

	private Logger dataSourceLogger = Logger.getLogger(dataSourceLoggerName);
    private Logger connectionLogger = Logger.getLogger(connectionLoggerName);
    private Logger statementLogger  = Logger.getLogger(statementLoggerName);
    private Logger resultSetLogger  = Logger.getLogger(resultSetLoggerName);

    @Override
    public String getDataSourceLoggerName() {
        return dataSourceLoggerName;
    }

    @Override
    public void setDataSourceLoggerName(String dataSourceLoggerName) {
        this.dataSourceLoggerName = dataSourceLoggerName;
        dataSourceLogger = Logger.getLogger(dataSourceLoggerName);
    }

    public void setDataSourceLogger(Logger dataSourceLogger) {
        this.dataSourceLogger = dataSourceLogger;
        this.dataSourceLoggerName = dataSourceLogger.getName();
    }

    @Override
    public String getConnectionLoggerName() {
        return connectionLoggerName;
    }

    @Override
    public void setConnectionLoggerName(String connectionLoggerName) {
        this.connectionLoggerName = connectionLoggerName;
        connectionLogger = Logger.getLogger(connectionLoggerName);
    }

    public void setConnectionLogger(Logger connectionLogger) {
        this.connectionLogger = connectionLogger;
        this.connectionLoggerName = connectionLogger.getName();
    }

    @Override
    public String getStatementLoggerName() {
        return statementLoggerName;
    }

    @Override
    public void setStatementLoggerName(String statementLoggerName) {
        this.statementLoggerName = statementLoggerName;
        statementLogger = Logger.getLogger(statementLoggerName);
    }

    public void setStatementLogger(Logger statementLogger) {
        this.statementLogger = statementLogger;
        this.statementLoggerName = statementLogger.getName();
    }

    @Override
    public String getResultSetLoggerName() {
        return resultSetLoggerName;
    }

    @Override
    public void setResultSetLoggerName(String resultSetLoggerName) {
        this.resultSetLoggerName = resultSetLoggerName;
        resultSetLogger = Logger.getLogger(resultSetLoggerName);
    }

    public void setResultSetLogger(Logger resultSetLogger) {
        this.resultSetLogger = resultSetLogger;
        this.resultSetLoggerName = resultSetLogger.getName();
    }

    @Override
    public boolean isConnectionLogErrorEnabled() {
        return connectionLogger.isEnabledFor(Level.ERROR) && super.isConnectionLogErrorEnabled();
    }

    @Override
    public boolean isDataSourceLogEnabled() {
        return dataSourceLogger.isDebugEnabled() && super.isDataSourceLogEnabled();
    }

    @Override
    public boolean isConnectionLogEnabled() {
        return connectionLogger.isDebugEnabled() && super.isConnectionLogEnabled();
    }

    @Override
    public boolean isStatementLogEnabled() {
        return statementLogger.isDebugEnabled() && super.isStatementLogEnabled();
    }

    @Override
    public boolean isResultSetLogEnabled() {
        return resultSetLogger.isDebugEnabled() && super.isResultSetLogEnabled();
    }

    @Override
    public boolean isResultSetLogErrorEnabled() {
        return resultSetLogger.isEnabledFor(Level.ERROR) && super.isResultSetLogErrorEnabled();
    }

    @Override
    public boolean isStatementLogErrorEnabled() {
        return statementLogger.isEnabledFor(Level.ERROR) && super.isStatementLogErrorEnabled();
    }

    @Override
    protected void connectionLog(String message) {
        connectionLogger.debug(message);
    }

    @Override
    protected void statementLog(String message) {
        statementLogger.debug(message);
    }

    @Override
    protected void resultSetLog(String message) {
        resultSetLogger.debug(message);
    }

    @Override
    protected void resultSetLogError(String message, Throwable error) {
        resultSetLogger.error(message, error);
    }

    @Override
    protected void statementLogError(String message, Throwable error) {
        statementLogger.error(message, error);
    }

}
