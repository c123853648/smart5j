package Utils;

import java.sql.*;

public class MysqlUtils {
    // JDBC 驱动名及数据库 URL
    String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    String DB_URL = "jdbc:mysql://localhost:3306/community";

    // 数据库的用户名与密码，需要根据自己的设置
    String USER = "root";
    String PASS = "root";

    public MysqlUtils(){
        Connection conn = null;
        Statement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

        }catch (Exception e){
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
                if(conn!=null) conn.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
            e.printStackTrace();
        }

    }
}
