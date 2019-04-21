package com.edu.iip.time_space_web.model;

/**
 * @author zengc
 * @date 2019/4/21 21:30
 */
public class TimeSpaceForm {

    private String nameColumn;
    private String timeColumn;
    private String spaceColumn;
    private String uniqueName;

    public TimeSpaceForm() {
    }

    public TimeSpaceForm(String nameColumn, String timeColumn, String spaceColumn, String uniqueName) {
        this.nameColumn = nameColumn;
        this.timeColumn = timeColumn;
        this.spaceColumn = spaceColumn;
        this.uniqueName = uniqueName;
    }

    public String getNameColumn() {
        return nameColumn;
    }

    public void setNameColumn(String nameColumn) {
        this.nameColumn = nameColumn;
    }

    public String getTimeColumn() {
        return timeColumn;
    }

    public void setTimeColumn(String timeColumn) {
        this.timeColumn = timeColumn;
    }

    public String getSpaceColumn() {
        return spaceColumn;
    }

    public void setSpaceColumn(String spaceColumn) {
        this.spaceColumn = spaceColumn;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    @Override
    public String toString() {
        return "TimeSpaceForm{" +
                "nameColumn='" + nameColumn + '\'' +
                ", timeColumn='" + timeColumn + '\'' +
                ", spaceColumn='" + spaceColumn + '\'' +
                ", uniqueName='" + uniqueName + '\'' +
                '}';
    }
}
