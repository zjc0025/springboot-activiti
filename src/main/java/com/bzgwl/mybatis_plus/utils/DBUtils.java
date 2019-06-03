package com.bzgwl.mybatis_plus.utils;

import java.sql.*;
import java.util.ArrayList;

public class DBUtils {

    public static final String mySqlDriver ="com.mysql.jdbc.Driver";
    public static final String oraCleDriver ="oracle.jdbc.driver.OracleDriver";


    public  Connection getConnection(String driver, String url,String username, String password){
        Connection conn =null;
        try {
            Class.forName(driver);
            conn= DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public ArrayList<String> getTableNames(Connection conn) throws Exception {


        ArrayList<String> list  = new ArrayList<>();
        String sql = "show tables";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        //4.处理数据库的返回结果(使用ResultSet类)
        while (rs.next()) {
            //数据库 第一列 索引从1开始
            String tableName = rs.getString(1);
            list.add(tableName);
        }
        //关闭资源
        rs.close();
        st.close();
        conn.close();

        return list;
    }
}
