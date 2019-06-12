package com.edu.iip.time_space_web.model;

import java.util.List;

public class ClusterNameLabel {
    private int clusterID;
    private List<String> nameList;
    private String label;

    public ClusterNameLabel(int clusterID, List<String> nameList, String label) {
        this.clusterID = clusterID;
        this.nameList = nameList;
        this.label = label;
    }

    public ClusterNameLabel() {
    }

    public int getClusterID() {
        return clusterID;
    }

    public void setClusterID(int clusterID) {
        this.clusterID = clusterID;
    }

    public List<String> getNameList() {
        return nameList;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
