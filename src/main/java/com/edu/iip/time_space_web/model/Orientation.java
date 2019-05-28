package com.edu.iip.time_space_web.model;


import com.edu.iip.time_space_web.util.DistanceUtil;

import java.util.Date;

/**
 * @Author Junnor.G
 * @Date 2018/12/19 下午2:36
 */
public class Orientation implements Comparable<Orientation>{
    public Orientation(){}

    public Orientation(String place, Date date){
        this.date=date; this.place=place;
        double temp [] = DistanceUtil.getLatitude(this.place);
        this.lng = temp[0];
        this.lat = temp[1];

    }

    public Orientation(String place, Date date, double lng, double lat){
        this.date=date; this.place=place;
        this.lng = lng; this.lat = lat;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    private String place;
    private Date date;
    private double lng;
    private double lat;

    public static double calDistance(Orientation ori1, Orientation ori2){
        return DistanceUtil.calculateDistance(ori1.lng, ori1.lat, ori2.lng, ori2.lat);
    }

    public static double calThroughDays(Orientation ori1, Orientation ori2){ // 默认ori2的时间在ori1的后面
        long through_milliseconds = ori2.getDate().getTime() - ori1.getDate().getTime();
        return through_milliseconds / 1000.0 / 3600 / 24;
    }

    @Override
    public int compareTo(Orientation obj){
        return date.compareTo(obj.date);
    }

    @Override
    public String toString(){
        return "{place: " + place + " ; date: " + date.toString() + " ; " + "pos: " + lng + "," + lat + " }";
    }

}