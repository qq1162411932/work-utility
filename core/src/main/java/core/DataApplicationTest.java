package core;

import core.entity.CoreTest;
import core.mapper.CoreTestMapper;
import core.utils.others.JDBCUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;


@SpringBootTest
public class DataApplicationTest {

    @Autowired
    DataSource dataSource;
    @Autowired
    CoreTestMapper coreTestMapper;


    @Test
    void testConnection() throws SQLException {
        System.out.println(dataSource.getClass());
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }


    /**
     * 测试类方法为 public 不能有返回值
     */
    @Test
    void mybatisSqlTest() {
        List<CoreTest> coreTests = coreTestMapper.selectList(null);
        System.out.println(coreTests);
    }

    /**
     * jdbc
     */
    @Test
    void JDBCTest() throws Exception {
//        new Driver();
        Class.forName("com.mysql.cj.jdbc.Driver");
//        Class.forName("com.mysql.cj.jdbc.Driver");
    }

    /**
     * id重复插入回报错
     *
     * @throws Exception
     */
    @Test
    void getPropertiesTest() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            //1. 加载配置
            Properties pros = new Properties();
            pros.load(ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties"));

            String user = pros.getProperty("user");
            String password = pros.getProperty("password");
            String url = pros.getProperty("url");
            String driverClass = pros.getProperty("driverClass");

            //2.加载驱动
            //通过driver静态方法调用
            Class.forName(driverClass);
            //3.获取连接
            conn = DriverManager.getConnection(url, user, password);
//            System.out.println(conn);

            //4. 预编译sql语句，返回PreparedStatement的实例
            String sql = "insert into core_test (id,name,value) values(?,?,?)"; //？为占位符
            ps = conn.prepareStatement(sql);
            //5.填充占位符
            ps.setInt(1, 1001);
            ps.setString(2, "梁登峰");
            ps.setString(3, "afeng@gmail.com");
            //ps.setDate(3, (java.sql.Date)new Date(date.getTime()));

            ps.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void JDBCUtilTest() {
//        String sql = "insert into core_test(id,name,value) values (?,?,?)";
//        System.out.println(JDBCUtils.update(sql, 123, "utils_test", "test"));

        String sql = "select * from core_test";
        JDBCUtils.queryForList(sql);
    }

    @Autowired
    SqlSession session;

    /**
     * SqlSession
     * 正如之前所提到的，SqlSession 在 MyBatis 中是非常强大的一个类。它包含了所有执行语句、提交或回滚事务以及获取映射器实例的方法。
     *
     * SqlSession 类的方法超过了 20 个，为了方便理解，我们将它们分成几种组别。
     *
     * 语句执行方法
     * 这些方法被用来执行定义在 SQL 映射 XML 文件中的 SELECT、INSERT、UPDATE 和 DELETE 语句。
     * 你可以通过名字快速了解它们的作用，每一方法都接受语句的 ID 以及参数对象，参数可以是原始类型（支持自动装箱或包装类）、JavaBean、POJO 或 Map。
     * 游标（Cursor）与列表（List）返回的结果相同，不同的是，游标借助迭代器实现了数据的惰性加载。
     */

    @Test
    public void sqlSessionFactory() {
        //第一种方式
        //使用SqlSession直接操作,
        //参数是nameSpace+节点(select,update等)id
        //此时是使用Executor 来实现后续的处理
        CoreTestMapper mapper = session.getMapper(CoreTestMapper.class);
        mapper.selectList(null);

        //第二种方式,直接调用Mapper,
        // 使用mapper执行后续操作, 此处是使用动态代理实现的
        //最后调用MapperMethod.execute实现数据库交互
        session.selectList("core.mapper.CoreTestMapper.getById", 1);
    }
}
