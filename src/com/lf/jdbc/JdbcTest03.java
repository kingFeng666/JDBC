package com.lf.jdbc;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * @ClassName: JdbcTest03
 * @Description:处理查询结果集 resultset
 * @Author: 李峰
 * @Date: 2021 年 03月 02 14:56
 * @Version 1.0
 */
public class JdbcTest03 {
    public static void main(String[] args) {
        //使用资源绑定器绑定资源文件
        ResourceBundle bundle=ResourceBundle.getBundle("db");
        String driver=bundle.getString("driver");
        String url=bundle.getString("url");
        String username=bundle.getString("username");
        String password=bundle.getString("password");
        Connection connection=null;
        Statement statement=null;
        ResultSet rs=null;
        try {
            //1.注册驱动
          Class.forName(driver);
            //2.获取连接
             connection = DriverManager.getConnection(url, username, password);
            //3.获取数据库对象
             statement = connection.createStatement();
             //4.执行sql语句
            //String sql="select * from student"
            String sql="select id ,name,email,age from student";
             rs = statement.executeQuery(sql);
            //处理结果集
            while (rs.next()){
                //第一种方式,不健壮
                /*int id =rs.getInt(1);
                String name=rs.getString(2);
                String email=rs.getString(3);
                int age=rs.getInt(4);*/
                //第二种方式 比较健壮 参数使用的查询结果集中的列名, 注意参数是查询结果集中的列的名字
                int id =rs.getInt("id");
                String name=rs.getString("name");
                String email=rs.getString("email");
                int age=rs.getInt("age");
                System.out.println(id+name+email+age);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //关闭资源,先关闭statement
            if (rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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
