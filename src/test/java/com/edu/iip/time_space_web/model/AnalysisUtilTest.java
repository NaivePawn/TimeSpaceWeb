package com.edu.iip.time_space_web.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnalysisUtilTest {
    public List<Orientation> generateOrientations1(){
        List<Orientation> orientations = new ArrayList<>();
        orientations.add(new Orientation("南京市", new Date(2018, 5, 3)));
        orientations.add(new Orientation("无锡市", new Date(2018, 5, 6)));
        orientations.add(new Orientation("常州市", new Date(2018, 5, 10)));
        orientations.add(new Orientation("南京市", new Date(2018, 5, 18)));

        orientations.add(new Orientation("南京市", new Date(2018, 9, 28)));
        orientations.add(new Orientation("南昌市", new Date(2018, 10, 3)));
        orientations.add(new Orientation("景德镇", new Date(2018, 10, 6)));
        orientations.add(new Orientation("井冈山", new Date(2018, 10, 10)));
        return orientations;
    }
    @Test
    public void testAnalyseSingle(){
        PeopleOrientation peopleOrientation = new PeopleOrientation();
        peopleOrientation.setName("张三");
        peopleOrientation.setOrientations(generateOrientations1());
        Orientation src = new Orientation("南京市", new Date(2018, 5, 3));
        peopleOrientation.setFrequentOrientation(src);

        AnalysisUtil.analyzePeopleOrientation(peopleOrientation);
    }
}
