package core;

import core.entity.CoreTest;
import core.mapper.CoreTestMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


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
    void mybatisSqlTest(){
        List<CoreTest> coreTests = coreTestMapper.selectList(null);
        System.out.println(coreTests);
    }
}
