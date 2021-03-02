package com.lf.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * @ClassName: JdbcTest02
 * @Description:
 * @Author: 李峰
 * @Date: 2021 年 03月 01 21:04
 * @Version 1.0
 */
public class JdbcTest02 {
    public static void main(String[] args) {
        //使用资源绑定器绑定资源文件
        ResourceBundle bundle=ResourceBundle.getBundle("db");
        String driver=bundle.getString("driver");
        String url=bundle.getString("url");
        String username=bundle.getString("username");
        String password=bundle.getString("password");
        Connection connection=null;
        Statement statement=null;
        try {
            //注册驱动
            Class.forName(driver);
            connection= DriverManager.getConnection(url,username,password);
            //获取连接
             statement = connection.createStatement();
            //执行sql语句
            String sql="insert into  student(id,name,email,age) values (1006,'张飞','12345@qq.com',45)";
            int i = statement.executeUpdate(sql);//返回值表示这条sql语句影响到数据库中数据的条数
            System.out.println("影响数据库中的纪录的行数"+i);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭资源,先关闭statement
                if (statement!=null){
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
