package com.edu.iip.time_space_web.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zengc
 * @date 2019/5/25 21:22
 */
public class AnalysisUtil {

    public static CycleTrack analyseCycle(List<Orientation> orientations){
        return new CycleTrack(orientations);
    }

    public static String asignLabel(List<CycleTrack> cycles){
        String [] scopeLabels = {"CITY", "PROVINCE", "COUNTRY"};
        String [] frequenceLabels = {"SOMETIMES", "OFTEN", "ALWAYS"};

        String label = "";

        int cityTime = 0, provincveTime = 0, countryTime = 0;
        for (CycleTrack cycle: cycles){
            if(cycle.allDistance > 1000) countryTime ++;
            else if(cycle.allDistance > 300) provincveTime ++;
            else cityTime ++;
        }

        if (countryTime > 1.0/3*cycles.size()) label += scopeLabels[2];
        else if (provincveTime > 1.0/3*cycles.size()) label += scopeLabels[1];
        else if (cityTime > 1.0/3*cycles.size()) label += scopeLabels[0];

        label += " ";
        // 计算整个路线的经历总时间
        if(cycles==null || cycles.size()==0)
            return label;
        double allDays = Orientation.calThroughDays(cycles.get(0).orientations.get(0),
                cycles.get(cycles.size()-1).orientations.get(cycles.get(cycles.size()-1).orientations.size()-1));
        if (allDays * 1.0 / cycles.size() < 30) label += frequenceLabels[2];
        else if (allDays * 1.0 / cycles.size() < 120) label += frequenceLabels[1];
        else if (allDays * 1.0 / cycles.size() < 360) label += frequenceLabels[0];
        return label;
    }

    public static PersonStatus analyzePeopleOrientation(PeopleOrientation peopleOrientation){
        PersonStatus personStatus = new PersonStatus(peopleOrientation.getName(), 0);
        if(peopleOrientation == null || peopleOrientation.getOrientations().size() == 0)
            return personStatus;
        double exceptionState = 0.0;

        // 1. 将行动路径的起始地和终点地补充完整
        peopleOrientation.sort();
        int n = peopleOrientation.getOrientations().size();
        String src = peopleOrientation.getFrequentOrientation().getPlace();
        if(peopleOrientation.getOrientations().get(0).getPlace().compareTo(src) != 0){
            Orientation start = new Orientation(src, peopleOrientation.getOrientations().get(0).getDate());
            peopleOrientation.getOrientations().add(start);
        }
        if(peopleOrientation.getOrientations().get(n-1).getPlace().compareTo(src) != 0) {
            Orientation end = new Orientation(src, peopleOrientation.getOrientations().get(n-1).getDate());
            peopleOrientation.getOrientations().add(end);
        }
        peopleOrientation.sort();
        // 2. 提取周期 以及 周期数, 计算异常概率
        int cycleCnt = 0;
        n = peopleOrientation.getOrientations().size();
        List<CycleTrack> cycles = new ArrayList<>();
        List<Orientation> cycle = new ArrayList<>();
        cycle.add(peopleOrientation.getOrientations().get(0));
        for (int i=1 ; i<n ; i++){
            Orientation ori = peopleOrientation.getOrientations().get(i);
            cycle.add(ori);
            if(ori.getPlace().compareTo(src) == 0){
                if(cycle.size() > 2) {
                    CycleTrack track = analyseCycle(cycle);
                    cycles.add(track);
                    exceptionState = Math.max(exceptionState, track.exceptionState);
                }
                cycle.clear();
                cycle.add(ori);
            }
        }
        personStatus.setState(exceptionState);
        // 3. 计算label
        personStatus.setLabel(asignLabel(cycles));
        return personStatus;
    }

    public static Map<String,PersonStatus> analyze(List<PeopleOrientation> peopleOrientations){
        Map<String,PersonStatus> scores = new HashMap<String,PersonStatus>();
        for (PeopleOrientation peopleOrientation: peopleOrientations){
            PersonStatus personStatus = analyzePeopleOrientation(peopleOrientation);
            scores.put(peopleOrientation.getName(), personStatus);
        }
        return postAnalyze(scores);
    }

    public static Map<String,PersonStatus> analyzeSingle(PeopleOrientation peopleOrientation){
        if(peopleOrientation == null || peopleOrientation.getOrientations().size() == 0)
            return postAnalyze(new HashMap<>());

        PersonStatus personStatus = analyzePeopleOrientation(peopleOrientation);
        Map<String,PersonStatus> scores = new HashMap<String,PersonStatus>();
        scores.put(peopleOrientation.getName(), personStatus);
        return postAnalyze(scores);
    }

    public static Map<String,PersonStatus> postAnalyze(Map<String,PersonStatus> scores){
        for(String key:scores.keySet()){
            scores.put(key,scores.get(key).fillColor());
        }
        return scores;
    }
}
