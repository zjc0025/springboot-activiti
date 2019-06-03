package com.bzgwl.mybatis_plus;

import com.bzgwl.mybatis_plus.utils.DBUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBUtilTest {

    @Test
    public void getTables() throws Exception{
        DBUtils dbUtils = new DBUtils();
        String url = "jdbc:mysql://localhost:3306/mybatis_plus02?serverTimezone=GMT%2B8&useSSL=false";
        String username = "root";
        String pass = "root";
        Connection conn = dbUtils.getConnection(DBUtils.mySqlDriver,url, username, pass);

        Statement st = conn.createStatement();
        String sql = "show tables";
        ResultSet rs=st.executeQuery(sql);
        //4.处理数据库的返回结果(使用ResultSet类)
        while(rs.next()){
            String tableName = rs.getString(1);
            System.out.println(tableName);
        }
        //关闭资源
        rs.close();
        st.close();
        conn.close();
    }
}
