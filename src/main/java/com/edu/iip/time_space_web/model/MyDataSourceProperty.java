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

    public MyDataSourceProperty(String dataSourceUrl, String tableName, String userName, String password) {
        this.dataSourceUrl = dataSourceUrl.trim();
        this.tableName = tableName.trim();
        this.userName = userName.trim();
        this.password = password.trim();
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

    @Override
    public int hashCode(){
        return toString().hashCode();
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
