package com.edu.iip.time_space_web.util;

import com.edu.iip.time_space_web.model.MyDataSourceProperty;
import com.edu.iip.time_space_web.model.Orientation;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.sql.DataSource;

/**
 * @author zengc
 * @date 2019/4/21 16:23
 */
public class DataSourceUtil {

    public static DataSource createDataSource (MyDataSourceProperty myDataSourceProperty)throws Exception{
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        if(myDataSourceProperty.getDataSourceUrl().contains("mysql"))
        dataSource.setUrl(myDataSourceProperty.getDataSourceUrl()+"?useUnicode=true&characterEncoding=UTF-8");
        else
            dataSource.setUrl(myDataSourceProperty.getDataSourceUrl());
        dataSource.setUsername(myDataSourceProperty.getUserName());
        dataSource.setPassword(myDataSourceProperty.getPassword());

        return dataSource;
    }

    public static String locationsFormatToString(List<Orientation> orientations){
        String str = "";
        for (Orientation ori: orientations){
            str += String.valueOf(ori.getLng()) + "," + String.valueOf(ori.getLat()) + "&amp;";
            //System.out.println(str);
        }
        return str.substring(0, str.length()-5);
    }

    public static String timeFormatToString(List<Orientation> orientations){
        String str = "";
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
        for (Orientation ori: orientations){
            str += sdf.format(ori.getDate()) + "&amp;";
            //System.out.println(str);
        }
        return str.substring(0, str.length()-5);
    }

    public static String placeFormatToString(List<Orientation> orientations){
        String str = "";
        for (Orientation ori: orientations){
            str += ori.getPlace() + "&amp;";
            //System.out.println(str);
        }
        return str.substring(0, str.length()-5);
    }
}
