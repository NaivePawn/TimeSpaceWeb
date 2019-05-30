package com.edu.iip.time_space_web.service;

import com.edu.iip.time_space_web.model.MyDataSourceProperty;
import com.edu.iip.time_space_web.model.Orientation;
import com.edu.iip.time_space_web.model.PeopleOrientation;
import com.edu.iip.time_space_web.model.TimeSpaceForm;
import com.edu.iip.time_space_web.util.DataSourceUtil;
import com.sun.javafx.binding.StringFormatter;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TimeSpaceService {
    public static Date strToDate(String dateStr){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(dateStr);
            return date;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static List<PeopleOrientation> getPeopleOrientations(MyDataSourceProperty myDataSourceProperty, TimeSpaceForm timeSpaceForm){
        Connection conn = null;
        Statement statement = null;
        try {
            conn = MysqlUtil.getConnection(myDataSourceProperty);
            statement = conn.createStatement();
            ResultSet resultSet = null;
            if(myDataSourceProperty.getDataSourceUrl().contains("oracle")||myDataSourceProperty.getDataSourceUrl().contains("ORACLE"))
                resultSet = statement.executeQuery("SELECT * FROM " + "\""+myDataSourceProperty.getTableName()+ "\"");
            else
                resultSet = statement.executeQuery("SELECT * FROM " +myDataSourceProperty.getTableName());
            Map<String, List<Orientation> > hashMapPeopleOrientations = new HashMap<>();
            Map<String, String> frequentPlace = new HashMap<>();
            while (resultSet.next()) {
                String name = resultSet.getString(timeSpaceForm.getNameColumn());
                String timeStr = resultSet.getString(timeSpaceForm.getTimeColumn());
                String place = resultSet.getString(timeSpaceForm.getSpaceColumn());
                String fre = resultSet.getString(timeSpaceForm.getFrequentPlaceColumn());
                Orientation orientation = new Orientation(place, strToDate(timeStr));
                if(hashMapPeopleOrientations.containsKey(name) == false)
                {
                    hashMapPeopleOrientations.put(name, new ArrayList<>());
                    frequentPlace.put(name,fre);
                }
                hashMapPeopleOrientations.get(name).add(orientation);
            }
            List<PeopleOrientation> peopleOrientations = new ArrayList<>();
            for(String name: hashMapPeopleOrientations.keySet()){
                PeopleOrientation peopleOrientation = new PeopleOrientation();
                peopleOrientation.setName(name);
                peopleOrientation.setOrientations(hashMapPeopleOrientations.get(name));
                Orientation src = new Orientation(frequentPlace.get(name),peopleOrientation.getOrientations().get(0).getDate());
                peopleOrientation.setFrequentOrientation(src);
                peopleOrientations.add(peopleOrientation);

            }

            return peopleOrientations;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            try {
                if(statement!=null)
                    statement.close();
                if(conn !=null)
                    conn.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
