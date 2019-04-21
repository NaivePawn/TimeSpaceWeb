package com.edu.iip.time_space_web.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

/**
 * @author zengc
 * @date 2019/4/21 16:47
 */
public class PreviewData {


    private List<String> columnName;
    private List<List<Object>> data;

    public List<String> getColumnName() {
        return columnName;
    }

    public void setColumnName(List<String> columnName) {
        this.columnName = columnName;
    }

    public List<List<Object>> getData() {
        return data;
    }

    public void setData(List<List<Object>> data) {
        this.data = data;
    }


}
