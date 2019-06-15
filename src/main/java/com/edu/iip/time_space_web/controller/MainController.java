package com.edu.iip.time_space_web.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.edu.iip.time_space_web.model.*;
import com.edu.iip.time_space_web.service.MainService;
import com.edu.iip.time_space_web.service.TimeSpaceService;
import com.edu.iip.time_space_web.util.DataSourceUtil;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zengc
 * @date 2019/4/21 14:17
 */
@Controller
public class MainController {

    @Autowired
    MainService mainService;

    @RequestMapping({"/","/index"})
    private String getIndex(HttpSession httpSession){
        httpSession.removeAttribute("dataSource");
        httpSession.removeAttribute("previewColumn");
        httpSession.removeAttribute("previewData");
        httpSession.removeAttribute("uniqueNameList");
        httpSession.removeAttribute("previewSingleColumn");
        httpSession.removeAttribute("previewSingleData");
        return "index";
    }

    @RequestMapping({"/index1"})
    private ModelAndView getIndex1(HttpSession httpSession){
        return new ModelAndView("index");
    }

    @RequestMapping("/preview")
    private String preView(MyDataSourceProperty myDataSource, Model model, HttpSession httpSession){

        String database = (String) httpSession.getAttribute("database");
        if(database.equals("population")){
            myDataSource.setDataSourceUrl("jdbc:mysql://localhost:3306/timespacedatasource");
        } else if (database.equals("legalperson")) {
            //TODO
        }
        else if (database.equals("certificate")) {
            //TODO
        }
        else if (database.equals("credit")) {
            //TODO
        }
        else{
            model.addAttribute("msg","数据库配置错误");
            return "index";
        }
        myDataSource.setUserName("root");
        myDataSource.setPassword("root");

        PreviewData previewData = mainService.preview(myDataSource);
        if(previewData != null) {
            httpSession.setAttribute("previewColumn", previewData.getColumnName());
            httpSession.setAttribute("previewData", previewData.getData());
            httpSession.setAttribute("dataSource",myDataSource);
            httpSession.setAttribute("tableName", myDataSource.getTableName());
            httpSession.removeAttribute("uniqueNameList");
        }else{
            httpSession.removeAttribute("dataSource");
            httpSession.removeAttribute("previewColumn");
            httpSession.removeAttribute("previewData");
            model.addAttribute("msg","数据库配置错误");
        }
        return "index";
    }

    @RequestMapping("/connectDatabase")
    private String connectDatabase(MyDataSourceProperty myDataSource, Model model, HttpSession httpSession) {

        httpSession.setAttribute("database", myDataSource.getDataSourceUrl());
//        System.out.println(myDataSource.getDataSourceUrl());
        if(myDataSource.getDataSourceUrl().equals("population")){
            myDataSource.setDataSourceUrl("jdbc:mysql://localhost:3306/timespacedatasource");
        } else if (myDataSource.getDataSourceUrl().equals("legalperson")) {
            //TODO
        }
        else if (myDataSource.getDataSourceUrl().equals("certificate")) {
            //TODO
        }
        else if (myDataSource.getDataSourceUrl().equals("credit")) {
            //TODO
        }
        else{
            model.addAttribute("msg","数据库配置错误");
            return "index";
        }
        myDataSource.setUserName("root");
        myDataSource.setPassword("root");

        PreviewTable previewTable = mainService.getTables(myDataSource);
        if(previewTable != null){
            httpSession.setAttribute("tableNames", previewTable.getTableName());
//            httpSession.setAttribute("dataSource",myDataSource);
        }
        else{
            httpSession.removeAttribute("dataSource");
            httpSession.removeAttribute("tableNames");
            model.addAttribute("msg","数据库配置错误");
        }
        return "index";
    }

    @RequestMapping("/getUniqueName")
    private String getUniqueName(@RequestBody TimeSpaceForm timeSpaceForm, Model model, HttpSession httpSession){

        if(httpSession.getAttribute("dataSource") == null){
            return "index";
        }
        MyDataSourceProperty myDataSourceProperty = (MyDataSourceProperty) httpSession.getAttribute("dataSource");
        httpSession.setAttribute("timeSpaceForm", timeSpaceForm);
        List<Object> res = mainService.getUniqueName(myDataSourceProperty, timeSpaceForm);
        httpSession.removeAttribute("previewSingleColumn");
        httpSession.removeAttribute("previewSingleData");
        httpSession.setAttribute("uniqueNameList",res);

        return "index::setting_table";
    }

    @RequestMapping("/previewSingle")
    private String previewSingle(@RequestBody TimeSpaceForm timeSpaceForm, Model model, HttpSession httpSession){
        if(httpSession.getAttribute("dataSource") == null){
            return "index";
        }

        MyDataSourceProperty myDataSourceProperty = (MyDataSourceProperty)httpSession.getAttribute("dataSource");
        httpSession.setAttribute("timeSpaceForm", timeSpaceForm);

        PreviewData previewData = mainService.previewSingle(myDataSourceProperty,timeSpaceForm);
        if(previewData != null) {
            httpSession.setAttribute("previewSingleColumn", previewData.getColumnName());
            httpSession.setAttribute("previewSingleData", previewData.getData());

        }else{
            httpSession.removeAttribute("previewSingleColumn");
            httpSession.removeAttribute("previewSingleData");
        }

        return "index::single_table";
    }

    @RequestMapping("previewSingleMap")
    private ModelAndView previewSingleMap(HttpSession httpSession, HttpServletResponse httpServletResponse){
        MyDataSourceProperty myDataSourceProperty = (MyDataSourceProperty)httpSession.getAttribute("dataSource");
        TimeSpaceForm timeSpaceForm = (TimeSpaceForm) httpSession.getAttribute("timeSpaceForm");
        String name = timeSpaceForm.getUniqueName();
        List<PeopleOrientation> peopleOrientations = TimeSpaceService.getPeopleOrientations(myDataSourceProperty,timeSpaceForm);
        //peopleOrientations.toString();
//        for( PeopleOrientation peopleOrientation : peopleOrientations){
//            System.out.println(peopleOrientation.getName() + " : ");
//            for(Orientation orientation: peopleOrientation.getOrientations()){
//                System.out.println(orientation.toString());
//            }
//        }
        String url = "track";
        String data = "";
        String places = "";
        String time = "";
        PeopleOrientation now = null;
        for(PeopleOrientation peopleOrientation: peopleOrientations){
            if(peopleOrientation.getName().compareTo(name) == 0){
                data += "?" + DataSourceUtil.locationsFormatToString(peopleOrientation.getOrientations());
                places += "?" + DataSourceUtil.placeFormatToString(peopleOrientation.getOrientations());
                time += "?" + DataSourceUtil.timeFormatToString(peopleOrientation.getOrientations());
                now = peopleOrientation;
            }
        }
        ArrayList<Trace> traces = new ArrayList<>();
        Trace trace = new Trace(name,data,MapColor.randomColor(),places,time);
        traces.add(trace);

        //todo
        Map<String,PersonStatus> scores = AnalysisUtil.analyzeSingle(now);

        //------------------------暂时-----------------
//        Map<String,PersonStatus> scores = new HashMap<>();//Util.getScore(peopleOrientations);
//        scores.put("张乾",new PersonStatus("张乾",0.87,"居家").fillColor());
        //----------------------------------------------

        ModelAndView view = new ModelAndView(url);
        view.addObject("data",traces);
        view.addObject("scores",scores);
        httpSession.removeAttribute("scores");
        httpSession.removeAttribute("data");
        return view;
    }

    @RequestMapping("previewMap")
    private ModelAndView previewMap(HttpSession httpSession, HttpServletResponse httpServletResponse,TimeSpaceForm timeSpaceForm){
        MyDataSourceProperty myDataSourceProperty = (MyDataSourceProperty)httpSession.getAttribute("dataSource");
        httpSession.setAttribute("timeSpaceForm", timeSpaceForm);

        List<PeopleOrientation> peopleOrientations = TimeSpaceService.getPeopleOrientations(myDataSourceProperty,timeSpaceForm);

//        for( PeopleOrientation peopleOrientation : peopleOrientations){
////            System.out.println(peopleOrientation.getName() + " : ");
//            for(Orientation orientation: peopleOrientation.getOrientations()){
//                System.out.println(orientation.toString());
//            }
//        }

        String url = "track";
        ArrayList<Trace> traces = new ArrayList<>();
        for(PeopleOrientation peopleOrientation: peopleOrientations){
            String name = peopleOrientation.getName();
            String path = "?"+DataSourceUtil.locationsFormatToString(peopleOrientation.getOrientations());
            String color = MapColor.randomColor();
            String place = "?"+DataSourceUtil.placeFormatToString(peopleOrientation.getOrientations());
            String time = "?"+DataSourceUtil.timeFormatToString(peopleOrientation.getOrientations());

            Trace trace = new Trace(name,path,color,place,time);
            traces.add(trace);

        }

        //todo
        Map<String,PersonStatus> scores = AnalysisUtil.analyze(peopleOrientations);


        //------------------------暂时-----------------
//        Map<String,PersonStatus> scores = new HashMap<>();//Util.getScore(peopleOrientations);
//        scores.put("张乾",new PersonStatus("张乾",0.57,"居家").fillColor());
//        scores.put("李霄云",new PersonStatus("李霄云",0.98,"出差达人").fillColor());
//        scores.put("刘龙",new PersonStatus("刘龙",0.3,"旅游达人").fillColor());
//        scores.put("秦骏",new PersonStatus("秦骏",0.1,"旅游狂魔").fillColor());
        //----------------------------------------------

        ModelAndView view = new ModelAndView(url);
        view.addObject("data",traces);
        view.addObject("scores",scores);
        httpSession.setAttribute("scores", scores);
        httpSession.setAttribute("data", traces);

        return view;
    }

    @RequestMapping("/cluster")
    private ModelAndView cluster(@RequestBody Cluster cluster, HttpSession httpSession){
        Map<String, PersonStatus> scores = (Map<String, PersonStatus>) httpSession.getAttribute("scores");
        ArrayList<Trace> traces = (ArrayList<Trace>) httpSession.getAttribute("data");
        int clusterNum = cluster.getClusterNum();
        Map<Integer, ClusterNameLabel> classAndNames = new HashMap<Integer, ClusterNameLabel>();
        for(String name : scores.keySet()){
            PersonStatus tmp = scores.get(name);
            double state = tmp.getState();
            int clusterID = 0;
            for(int i = 0;i < clusterNum; i++){
                if(state >= (double)i/clusterNum && state <= (double)(i+1)/clusterNum){
                    clusterID = i+1;
                    break;
                }
            }
            if(classAndNames.containsKey(clusterID)){
                classAndNames.get(clusterID).getNameList().add(name);
            }
            else {
                List<String> nameList = new ArrayList<String>();
                nameList.add(name);
                ClusterNameLabel clusterNameLabel = new ClusterNameLabel();
                clusterNameLabel.setClusterID(clusterID);
                clusterNameLabel.setNameList(nameList);
                classAndNames.put(clusterID, clusterNameLabel);
            }
        }
        for(int i = 0; i< clusterNum;i++){
            if(!classAndNames.containsKey(i+1)){
                List<String> nameList = new ArrayList<String>();
                ClusterNameLabel clusterNameLabel = new ClusterNameLabel();
                clusterNameLabel.setClusterID(i+1);
                clusterNameLabel.setNameList(nameList);
                classAndNames.put(i+1, clusterNameLabel);
            }
        }

        httpSession.setAttribute("classAndNames", classAndNames);
        httpSession.setAttribute("clusterNum",clusterNum);
        httpSession.setAttribute("scores", scores);
        httpSession.setAttribute("data", traces);
        httpSession.removeAttribute("labeled");

        String url = "track::setting_cluster";
        ModelAndView view = new ModelAndView(url);
        view.addObject("data",traces);
        view.addObject("scores",scores);
        view.addObject("classAndNames", classAndNames);
        return view;
    }

    @RequestMapping("/label")
    private ModelAndView label(@RequestBody Label labels, HttpSession httpSession) {
        Map<String, PersonStatus> scores = (Map<String, PersonStatus>) httpSession.getAttribute("scores");
        ArrayList<Trace> traces = (ArrayList<Trace>) httpSession.getAttribute("data");

        List<String> labelList = new ArrayList<String>();
        labelList.add(labels.getLabel1());
        labelList.add(labels.getLabel2());
        labelList.add(labels.getLabel3());
        labelList.add(labels.getLabel4());
        labelList.add(labels.getLabel5());
        labelList.add(labels.getLabel6());
        labelList.add(labels.getLabel7());
        labelList.add(labels.getLabel8());
        labelList.add(labels.getLabel9());
        labelList.add(labels.getLabel10());

        Map<Integer, ClusterNameLabel> classAndNames = (Map<Integer, ClusterNameLabel>) httpSession.getAttribute("classAndNames");

        for(Integer clusterID : classAndNames.keySet()){
            classAndNames.get(clusterID).setLabel(labelList.get(clusterID-1));
        }

        httpSession.setAttribute("classAndNames", classAndNames);
        httpSession.setAttribute("labeled", true);

        String url = "track::setting_cluster";
        ModelAndView view = new ModelAndView(url);
        view.addObject("data",traces);
        view.addObject("scores",scores);
        view.addObject("classAndNames", classAndNames);
        return view;
    }
}