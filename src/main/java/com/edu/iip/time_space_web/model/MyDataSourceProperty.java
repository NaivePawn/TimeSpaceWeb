package com.edu.iip.time_space_web.model;

import com.edu.iip.time_space_web.util.DriverClassUtil;

/**
 * @author zengc
 * @date 2019/4/21 15:57
 */
public class MyDataSourceProperty {

    private String dataSourceUrl;
    private String tableName;
    private String userName;
    private String password;
    private String driverClass;

    public MyDataSourceProperty(String dataSourceUrl, String tableName, String userName, String password) {
        this.dataSourceUrl = dataSourceUrl;
        this.tableName = tableName;
        this.userName = userName;
        this.password = password;
        this.driverClass = DriverClassUtil.MySQL.getDriverClass();
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public MyDataSourceProperty() {
    }

    @Override
    public String toString() {
        return "MyDataSource{" +
                "dataSourceUrl='" + dataSourceUrl + '\'' +
                ", tableName='" + tableName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getDataSourceUrl() {
        return dataSourceUrl;
    }

    public void setDataSourceUrl(String dataSourceUrl) {
        this.dataSourceUrl = dataSourceUrl;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
