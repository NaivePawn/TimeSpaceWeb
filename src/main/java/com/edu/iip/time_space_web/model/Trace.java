package com.edu.iip.time_space_web.model;

import java.util.ArrayList;

/**
 * @author zengc
 * @date 2019/5/23 17:49
 */
public class Trace {

    private String label;
    private String path;
    private String color;
    private String places;
    private String time;

    public Trace(String label, String path, String color, String places, String time) {
        this.label = label;
        this.path = path;
        this.color = color;
        this.places = places;
        this.time = time;
    }

    public Trace(String label, String path, String places, String time) {
        this.label = label;
        this.path = path;
        this.color = "red";
        this.places = places;
        this.time = time;
    }



    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPlaces() {
        return places;
    }

    public void setPlaces(String places) {
        this.places = places;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Trace{" +
                "label='" + label + '\'' +
                ", path='" + path + '\'' +
                ", color='" + color + '\'' +
                ", places='" + places + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
