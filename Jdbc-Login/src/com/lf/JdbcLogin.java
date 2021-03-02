package com.lf;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
/**
 * @ClassName: JdbcLogin
 * @Description:使用JDBC实现登陆功能
 * @Author: 李峰
 * @Date: 2021 年 03月 02 18:13
 * @Version 1.0
 */
/*
* 1.使用powerDesiginer完成数据库的建模
* */
public class JdbcLogin {
    public static void main(String[] args) {
        //初始化一个用户登陆界面
        Map<String,String> userLoginUi=intUi();
        //登陆验证
        Boolean loginSuccess= login(userLoginUi);
        //返回登陆结果
        System.out.println(loginSuccess ? "登陆成功" : "登陆失败");
    }
/*
* 用来实现登陆验证的方法
* @return用户名和密码判断的结果
* */
    private static Boolean login(Map<String, String> userLoginUi) {
        Boolean flag=false;
        //JDBC的代码
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
            String sql="select loginName,loginPwd from t_user where loginName='"+userLoginUi.get("loginName")+"' and " +
                    "loginName='"+userLoginUi.get("loginPwd")+"'" ;
            rs = statement.executeQuery(sql);
            //处理结果集  如果登陆成功会返回一条数据
            if (rs.next()){
                flag=true;
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
        return flag;
    }
    /*初始化界面的方法
* @return 用户输入的用户名密码等登陆信息
* */
    private static Map<String, String> intUi() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("用户名:");
        String loginName = scanner.nextLine();
        System.out.println("密码:");
        String loginPwd=scanner.nextLine();
        Map<String,String> loginMap= new HashMap<>();
        loginMap.put("loginName",loginPwd);
        loginMap.put("loginPwd",loginPwd);
        return loginMap;
    }
}
