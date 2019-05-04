package com.edu.iip.time_space_web.service;

import com.edu.iip.time_space_web.model.MyDataSourceProperty;
import com.edu.iip.time_space_web.util.DataSourceUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;


public class MysqlUtil {
    public static ConcurrentHashMap<MyDataSourceProperty, Connection> connPool = new ConcurrentHashMap<MyDataSourceProperty, Connection>();

    public static Connection getConnection(MyDataSourceProperty myDataSourceProperty){
//        if(connPool.containsKey(myDataSourceProperty) == false) {
//            try {
//                DataSource dataSource = DataSourceUtil.createDataSource(myDataSourceProperty);
//                Connection connection = dataSource.getConnection();
//                connPool.put(myDataSourceProperty, connection);
//            }catch (Exception ex){
//                ex.printStackTrace();
//            }
//        }
//        return connPool.get(myDataSourceProperty);
        try {
            DataSource dataSource = DataSourceUtil.createDataSource(myDataSourceProperty);
            Connection connection = dataSource.getConnection();
            return connection;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static ResultSet executeSelect(MyDataSourceProperty myDataSourceProperty, String sql) {
        try {
            Connection conn = getConnection(myDataSourceProperty);
            Statement statement = conn.createStatement();
            return statement.executeQuery(sql);
        }catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }finally {
            
        }
    }
}
