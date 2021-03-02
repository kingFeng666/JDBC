package com.lf;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * @ClassName: JdbcLogin
 * @Description:使用JDBC实现登陆功能 这种情况下存在sql注入问题 ,解决SQL注入问题
 * @Author: 李峰
 * @Date: 2021 年 03月 02 18:13
 * @Version 1.0
 */
/*
* 1.使用powerDesiginer完成数据库的建模
* */
public class JdbcLogin01 {
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
        //定义表示符号
        Boolean flag=false;
        //单独定义变量
        String loginName=userLoginUi.get("loginName");
        String loginPwd=userLoginUi.get("loginPwd");
        //JDBC的代码
        //使用资源绑定器绑定资源文件
        ResourceBundle bundle=ResourceBundle.getBundle("db");
        String driver=bundle.getString("driver");
        String url=bundle.getString("url");
        String username=bundle.getString("username");
        String password=bundle.getString("password");
        Connection connection=null;
        //preparedStatement叫做预编译的数据库操作对象,使用这个可以防止sql注入
        PreparedStatement statement=null;
        ResultSet rs=null;
        try {
            //1.注册驱动
            Class.forName(driver);
            //2.获取连接
            connection = DriverManager.getConnection(url, username, password);
            //3.获取数据库对象
            //sql语句的框架,问号表示一个占位符 ,将来一个?接收一个传过来的值 , 注意占位符不能使用''括起来
            String sql="select loginName,loginPwd from t_user where loginName=? and loginPwd=?" ;
            //程序执行到这里会对sql框架发送给DBMS,DBMS对sql预计进行预编译
            statement = connection.prepareStatement(sql);
            //按照下标对占位符进行传值,1表示第一个问号
            statement.setString(1,loginName);
            statement.setString(2,loginPwd);
            //4.执行sql语句
            rs = statement.executeQuery();
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
        loginMap.put("loginName",loginName);
        loginMap.put("loginPwd",loginPwd);
        return loginMap;
    }
}
