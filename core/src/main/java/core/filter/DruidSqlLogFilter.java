package core.filter;

import com.alibaba.druid.DbType;
import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.JdbcParameter;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.util.StringUtils;
import core.config.CoreMybatisProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;


import java.sql.SQLException;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author: 栗真的很棒 copy pigx
 * @Date: 2022/9/15 9:46
 */
@Slf4j
@RequiredArgsConstructor
public class DruidSqlLogFilter extends FilterEventAdapter {

    private static final SQLUtils.FormatOption FORMAT_OPTION = new SQLUtils.FormatOption(false, false);
    private final CoreMybatisProperties properties;



    @Override
    @SneakyThrows
    protected void statementExecuteAfter(StatementProxy statement, String sql, boolean firstResult) {
        super.statementExecuteAfter(statement, sql, firstResult);
        intercept(statement);
//        statement.setLastExecuteTimeNano();
    }

    @Override
    @SneakyThrows
    protected void statementExecuteBatchAfter(StatementProxy statement, int[] result) {
        super.statementExecuteBatchAfter(statement, result);
        intercept(statement);
//        statement.setLastExecuteTimeNano();
    }

    @Override
    @SneakyThrows
    protected void statementExecuteQueryAfter(StatementProxy statement, String sql, ResultSetProxy resultSet) {
        super.statementExecuteQueryAfter(statement, sql, resultSet);
        intercept(statement);
//        statement.setLastExecuteTimeNano();
    }

    @Override
    @SneakyThrows
    protected void statementExecuteUpdateAfter(StatementProxy statement, String sql, int updateCount) {
        super.statementExecuteUpdateAfter(statement, sql, updateCount);
        intercept(statement);
//        statement.setLastExecuteTimeNano();
    }

    public void intercept(StatementProxy statement) throws SQLException {
        // 支持动态开启
        if (!properties.isShowSql()) {
            return;
        }

        // 是否开启调试
        if (!log.isInfoEnabled()) {
            return;
        }

        // 打印可执行的 sql
        String sql = statement.getBatchSql();
        // sql 为空直接返回
        if (StringUtils.isEmpty(sql)) {
            return;
        }
        int parametersSize = statement.getParametersSize();
        List<Object> parameters = new ArrayList<>(parametersSize);
        for (int i = 0; i < parametersSize; ++i) {
            // 转换参数，处理 java8 时间
            parameters.add(getJdbcParameter(statement.getParameter(i)));
        }
        String dbType = statement.getConnectionProxy().getDirectDataSource().getDbType();
        String formattedSql = SQLUtils.format(sql, DbType.of(dbType), parameters, FORMAT_OPTION);
        printSql(formattedSql, statement);
    }

    ///捕获不到
//    @Override
    public void statement_close(FilterChain chain, StatementProxy statement) throws SQLException {
        // 先调用父类关闭 statement
        super.statement_close(chain, statement);
        // 支持动态开启
        if (!properties.isShowSql()) {
            return;
        }

        // 是否开启调试
        if (!log.isInfoEnabled()) {
            return;
        }

        // 打印可执行的 sql
        String sql = statement.getBatchSql();
        // sql 为空直接返回
        if (StringUtils.isEmpty(sql)) {
            return;
        }
        int parametersSize = statement.getParametersSize();
        List<Object> parameters = new ArrayList<>(parametersSize);
        for (int i = 0; i < parametersSize; ++i) {
            // 转换参数，处理 java8 时间
            parameters.add(getJdbcParameter(statement.getParameter(i)));
        }
        String dbType = statement.getConnectionProxy().getDirectDataSource().getDbType();
        String formattedSql = SQLUtils.format(sql, DbType.of(dbType), parameters, FORMAT_OPTION);
        printSql(formattedSql, statement);
    }

    private static Object getJdbcParameter(JdbcParameter jdbcParam) {
        if (jdbcParam == null) {
            return null;
        }
        Object value = jdbcParam.getValue();
        // 处理 java8 时间
        if (value instanceof TemporalAccessor) {
            return value.toString();
        }
        return value;
    }

    private static void printSql(String sql, StatementProxy statement) {
        // 打印 sql
        String sqlLogger = "\n\n======= Sql Logger ======================" + "\n{}"
                + "\n======= Sql Execute Time: {} =======\n";
        log.info(sqlLogger, sql.trim(), format(statement.getLastExecuteTimeNano()));
    }

    /**
     * 格式化执行时间，单位为 ms 和 s，保留三位小数
     *
     * @param nanos 纳秒
     * @return 格式化后的时间
     */
    private static String format(long nanos) {
        if (nanos < 1) {
            return "0ms";
        }
        double millis = (double) nanos / (1000 * 1000);
        // 不够 1 ms，最小单位为 ms
        if (millis > 1000) {
            return String.format("%.3fs", millis / 1000);
        } else {
            return String.format("%.3fms", millis);
        }
    }
}
