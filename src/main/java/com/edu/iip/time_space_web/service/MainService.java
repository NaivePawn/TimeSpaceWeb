package com.edu.iip.time_space_web.service;

import com.edu.iip.time_space_web.model.MyDataSourceProperty;
import com.edu.iip.time_space_web.model.PreviewData;
import com.edu.iip.time_space_web.model.TimeSpaceForm;
import com.edu.iip.time_space_web.util.DataSourceUtil;
import com.sun.javafx.binding.StringFormatter;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zengc
 * @date 2019/4/21 14:18
 */
@Service
public class MainService {
    private static int previewNumber = 30;
    public PreviewData preview(MyDataSourceProperty myDataSourceProperty){
        PreviewData previewData;
        try {
            DataSource dataSource = DataSourceUtil.createDataSource(myDataSourceProperty);
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;
            if(myDataSourceProperty.getDataSourceUrl().contains("oracle")||myDataSourceProperty.getDataSourceUrl().contains("ORACLE")) {
                resultSet = statement.executeQuery("SELECT * FROM " + "\"" + myDataSourceProperty.getTableName() + "\"" + " where rownum<="+previewNumber);
            }

            else
                resultSet = statement.executeQuery("SELECT * FROM " +myDataSourceProperty.getTableName()+" limit "+previewNumber);

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int count = resultSetMetaData.getColumnCount();
            previewData = new PreviewData();
            List<String> column = new ArrayList<>();
            for(int i=1;i<=count;i++)
                column.add(resultSetMetaData.getColumnName(i));
            List<List<Object>> data = new ArrayList<>();
            while (resultSet.next()) {
                List<Object> line_data  = new ArrayList<>();
                for(int i=1;i<=count;i++)
                    line_data.add(resultSet.getObject(i));
                data.add(line_data);
            }
            previewData.setColumnName(column);
            previewData.setData(data);
            statement.close();
            connection.close();

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {

        }
        return previewData;
    }

    public List<Object> getUniqueName(MyDataSourceProperty myDataSourceProperty, TimeSpaceForm timeSpaceForm){
        PreviewData previewData;
        List<Object> res = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        try {
            DataSource dataSource = DataSourceUtil.createDataSource(myDataSourceProperty);
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            String sqlFormat = "SELECT DISTINCT %s FROM %s";
            if(myDataSourceProperty.getDataSourceUrl().contains("oracle")||myDataSourceProperty.getDataSourceUrl().contains("ORACLE"))
                sqlFormat = "SELECT DISTINCT \"%s\" FROM \"%s\"";
            String sql = String.format(sqlFormat,timeSpaceForm.getNameColumn(),myDataSourceProperty.getTableName());
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int count = resultSetMetaData.getColumnCount();
            while (resultSet.next()) {
                res.add(resultSet.getObject(1));
            }


        }catch (Exception e){
            e.printStackTrace();
            return res;
        }finally {
            try {
                if(statement!=null)
                    statement.close();
                if(connection !=null)
                    connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }


        }
        return res;
    }

    public PreviewData previewSingle(MyDataSourceProperty myDataSourceProperty, TimeSpaceForm timeSpaceForm){
        PreviewData previewData;
        Connection connection =null;
        Statement statement = null;
        try {
            DataSource dataSource = DataSourceUtil.createDataSource(myDataSourceProperty);
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            String sqlFormat = "SELECT %s,%s,%s FROM %s WHERE %s='%s' ORDER BY %s LIMIT "+previewNumber;
            if(myDataSourceProperty.getDataSourceUrl().contains("oracle")||myDataSourceProperty.getDataSourceUrl().contains("ORACLE"))
                sqlFormat = "SELECT \"%s\",\"%s\",\"%s\" FROM \"%s\" WHERE \"%s\"='%s' AND rownum<="+previewNumber+" ORDER BY \"%s\"";
            String sql = String.format(sqlFormat,timeSpaceForm.getNameColumn(),timeSpaceForm.getTimeColumn(),timeSpaceForm.getSpaceColumn(),myDataSourceProperty.getTableName(),timeSpaceForm.getNameColumn(),timeSpaceForm.getUniqueName(),timeSpaceForm.getTimeColumn());
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int count = resultSetMetaData.getColumnCount();
            previewData = new PreviewData();
            List<String> column = new ArrayList<>();
            for(int i=1;i<=count;i++)
                column.add(resultSetMetaData.getColumnName(i));
            List<List<Object>> data = new ArrayList<>();
            while (resultSet.next()) {
                List<Object> line_data  = new ArrayList<>();
                for(int i=1;i<=count;i++)
                    line_data.add(resultSet.getObject(i));
                data.add(line_data);
            }
            previewData.setColumnName(column);
            previewData.setData(data);
            statement.close();
            connection.close();

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            try {
                if(statement!=null)
                    statement.close();
                if(connection !=null)
                    connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return previewData;
    }

}
