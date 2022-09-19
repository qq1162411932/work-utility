package core.utils.others;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
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


    /**
     *  查询的通用方法
     * @param sql;
     * @param args;
     * @return
     * */
    public static Map<String,Object> queryForMap(String sql,Object... args){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            //有可能有参数
            for(int i=0;i<args.length;i++){
                ps.setObject(i+1,args[i]);
            }
            //执行sql语句
            rs = ps.executeQuery();
            //创建map集合
            Map<String, Object> map = new HashMap<>();
            //获取本次查询结果集有多少列
            int count = rs.getMetaData().getColumnCount();
            if(count>1) {
                throw new RuntimeException("the query result is greater than one column");
            }
            //while循环
            while(rs.next()){
                //获取本次查询结果集的列名
                String name = rs.getMetaData().getColumnLabel(1);
                map.put(name,rs.getObject(name));
            }
            //返回值
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            //关闭资源
            closeResource(conn,ps,rs);
        }
        return null;
    }


    /**
     * @Function commonQueryOne
     * @Description 查找单条记录
     * @param sql 执行的SQL语句
     * @param cls 实体类对象
     * @param args SQL语句中的限制条件
     */
    public static <E> E queryForBean(String sql, Class<E> cls, Object...args) {
        Connection conn = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        E entity = null;
        try {
            ps = conn.prepareStatement(sql);
            if(args != null && args.length > 0) {
                for(int i = 0; i < args.length; i++) {
                    ps.setObject(i+1, args[i]);
                }
            }
            //获取结果集
            rs = ps.executeQuery();

            /**
             * 以下通过数据库表中字段去查找实体类中的属性名
             */
            //获取结果集中对象的数量、列名等
            ResultSetMetaData rsmd = rs.getMetaData();
            //获取字段数
            int columnCount = rsmd.getColumnCount();
            while(rs.next()) {
                //ͨ通过反射获取实体类对象
                entity = cls.newInstance();
                for(int i = 0; i < columnCount; i++) {
                    //获取字段名称
                    String columnName = rsmd.getColumnName(i+1);
                    //获取该字段对应的值ֵ
                    Object columnValue = rs.getObject(columnName);
                    //通过字段名获取属性，try{名称不匹配}catch{到配置文件查找对应属性名}
                    Field field = null;
                    try{
                        field = cls.getDeclaredField(columnName);
                    }catch (Exception e){
                        throw new RuntimeException("The column name does not match the field name");
                    }
                    //将私有属性非可访问设置为可访问
                    field.setAccessible(true);
                    //给实体类中的属性赋值ֵ
                    field.set(entity, columnValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(conn,ps,rs);
        }
        return entity;
    }


    //selectAll
    /**
     * @Function commonQueryList
     * @Description 查找多条记录
     * @param sql 执行的SQL语句
     * @param cls 实体类对象
     * @param objects SQL语句中的限制条件
     */
    public static <E> List<E> queryForBeanList(String sql,  Class<E> cls, Object...objects) {
        Connection conn = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        E entity = null;
        List<E> list = new ArrayList<E>();
        try {
            ps = conn.prepareStatement(sql);
            if(objects != null && objects.length > 0) {
                for(int i = 0; i < objects.length; i++) {
                    ps.setObject(i+1, objects[i]);
                }
            }
            rs = ps.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while(rs.next()) {
                entity = cls.newInstance();
                for(int i = 0; i < columnCount; i++) {
                    String columnName = rsmd.getColumnName(i+1);
                    Object columnValue = rs.getObject(columnName);
                    //通过字段名获取属性，try{名称不匹配}catch{到配置文件查找对应名称}
                    Field field = null;
                    try{
                        field = cls.getDeclaredField(columnName);
                    }catch (Exception e){
                        throw new RuntimeException("The column name does not match the field name");
                    }
                    field.setAccessible(true);
                    field.set(entity, columnValue);
                }
                list.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(conn,ps,rs);
        }
        return list;
    }
}
