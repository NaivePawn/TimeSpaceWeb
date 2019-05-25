package com.edu.iip.time_space_web.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zengc
 * @date 2019/5/25 21:22
 */
public class AnalysisUtil {

    public static Map<String,PersonStatus> analyze(List<PeopleOrientation> peopleOrientations){
        return postAnalyze(new HashMap<>());
    }

    public static Map<String,PersonStatus> analyzeSingle(List<PeopleOrientation> peopleOrientations){
        return postAnalyze(new HashMap<>());
    }

    public static Map<String,PersonStatus> postAnalyze(Map<String,PersonStatus> scores){
        for(String key:scores.keySet()){
            scores.put(key,scores.get(key).fillColor());
        }
        return scores;
    }
}
