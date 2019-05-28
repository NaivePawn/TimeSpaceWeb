package com.edu.iip.time_space_web.model;

import java.util.ArrayList;
import java.util.List;

public class CycleTrack {
    public List<Orientation> orientations = new ArrayList<>();
    public List<Double> distances = new ArrayList<>();
    public List<Double> days = new ArrayList<>();
    public double allDistance; // 这段cycle经历的总距离
    public double allDays; // 这段cycle经历的时间段
    public int outPlaceCount = 0; // 出差地个数

    public double exceptionState = 0.0;

    public final double AVE_DISTANCE = 300;
    public final double MAX_TIME = 30;
    public final double NEXT_DISTANCE = 1000;
    public final int MAX_OUT_PLACE_COUNT = 5;

    public CycleTrack(List<Orientation> orientations){
        this.orientations.clear();
        for(Orientation ori: orientations) this.orientations.add(ori);
        allDistance = 0; distances.clear();
        allDays = 0 ; days.clear();
        for(int i=1 ; i<orientations.size() ; i++){
            double distance = Orientation.calDistance(orientations.get(i-1), orientations.get(i));
            double day = Orientation.calThroughDays(orientations.get(i-1), orientations.get(i));
            distances.add(distance);
            allDistance += distance;
            days.add(day);
            allDays += day;
        }
        outPlaceCount = orientations.size()-2;
        exceptionState = calExceptionState();
    }

    public double aveDistanceException(){
        return Math.min(1.0 , Math.sqrt(allDistance/allDays) / Math.sqrt(AVE_DISTANCE));
    }

    public double maxTimeException(){
        return Math.min(1.0 , Math.sqrt(allDays) / Math.sqrt(MAX_TIME));
    }

    public double nextDistanceException(){
        double maxNextDistance = distances.get(0);
        for(int i=1 ; i<distances.size()-1 ;  i++){
            maxNextDistance = Math.max(maxNextDistance, distances.get(i));
        }
        return Math.min(1.0 , maxNextDistance / NEXT_DISTANCE);
    }

    public double maxOutPlaceCount(){
        return Math.min(1.0, outPlaceCount*1.0 / MAX_OUT_PLACE_COUNT);
    }

    public double calExceptionState(){
        double ret = 0.0 ;
        ret = Math.max(aveDistanceException(), ret);
        ret = Math.max(maxTimeException(), ret);
        ret = Math.max(nextDistanceException(), ret);
        ret = Math.max(maxOutPlaceCount(), ret);
        return ret;
//        double state = 1.0;
//        return state * aveDistanceException() * maxTimeException() * nextDistanceException() * maxOutPlaceCount();
    }
}
