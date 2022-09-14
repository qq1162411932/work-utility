package core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;


public class JDBCUtils {

    //得到连接数据库的示例
    public static Connection getConnection() {
        //3.获取连接
        Connection conn = null;
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
            //Properties类：用于获取java中的各种配置参数。
            Properties pros = new Properties();
            //加载文件流。
            pros.load(is);
            //写入四种基本参数。
            String user = pros.getProperty("user");
            String password = pros.getProperty("password");
            String url = pros.getProperty("url");
            String driverClass = pros.getProperty("driverClass");
            //2.加载驱动
            Class.forName(driverClass);
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    //关闭资源。
    public static void closeResource(Connection conn, Statement ps) {
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

    public static void closeResource(Connection conn, Statement ps, ResultSet rs) {
        closeResource(conn, ps);
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Boolean update(String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean execute = true;
        try {
            //1.连接数据库。
            conn = getConnection();
            //2.预处理sql命令。
            ps = conn.prepareStatement(sql);
            //3.填充占位符。
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]); //注意： 形参格式容易出错。
            }
            //4.执行命令
            execute = ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (execute = false) {
                return Boolean.FALSE;
            }
            closeResource(conn, ps);
        }
        return Boolean.TRUE;
    }

    /**
     * 查询的通用方法
     *
     * @param sql;
     * @param args;
     * @return
     */
    public static List<Map<String, Object>> queryForList(String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            //有可能有参数
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            //执行sql语句
            rs = ps.executeQuery();
            //创建List集合
            List<Map<String, Object>> list = new ArrayList<>();
            //获取本次查询结果集有多少列
            int count = rs.getMetaData().getColumnCount();
            //while循环
            while (rs.next()) {
                //创建Map集合   获取一个数据封装成一个Map集合
                Map<String, Object> map = new HashMap<>();
                //for循环  遍历所有的列
                for (int i = 0; i < count; i++) {
                    //获取本次查询结果集的列名
                    String name = rs.getMetaData().getColumnLabel(i + 1);
                    map.put(name, rs.getObject(name));
                }
                //把所有的map集合添加到List集合中
                list.add(map);
            }
            //返回值
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            closeResource(conn, ps, rs);
        }
        return null;
    }
}
