package com.edu.iip.time_space_web.util;

/**
 * @author zengc
 * @date 2019/4/21 16:38
 */
public enum DriverClassUtil {

    MySQL("com.mysql.jdbc.Driver");


    private String driverClass;

    private DriverClassUtil(String driverClass){
        this.driverClass = driverClass;
    }

    public String getDriverClass() {
        return driverClass;
    }
}
