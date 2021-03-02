package com.lf.jdbc;

/**
 * @ClassName: JdbcTest01
 * @Description:JDBC 加载Mysql驱动的两种方式
 * @Author: 李峰
 * @Date: 2021 年 03月 01 20:44
 * @Version 1.0
 */
import java.sql.*;
public class JdbcTest01 {
    public static void main(String[] args) {

        try {
            //第一种
            DriverManager.deregisterDriver(new com.mysql.jdbc.Driver());
            //第二种:通过反射机制来实现  不需要返回值 ,我们要的是类加载的这个动作
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hello", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
