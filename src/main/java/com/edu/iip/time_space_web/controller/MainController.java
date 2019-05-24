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


        PreviewData previewData = mainService.preview(myDataSource);
        if(previewData != null) {
            httpSession.setAttribute("previewColumn", previewData.getColumnName());
            httpSession.setAttribute("previewData", previewData.getData());
            httpSession.setAttribute("dataSource",myDataSource);
            httpSession.removeAttribute("uniqueNameList");
        }else{
            httpSession.removeAttribute("dataSource");
            httpSession.removeAttribute("previewColumn");
            httpSession.removeAttribute("previewData");
            model.addAttribute("msg","数据库配置错误");
        }
        return "index";
    }

    @RequestMapping("/getUniqueName")
    private String getUniqueName(TimeSpaceForm timeSpaceForm, Model model, HttpSession httpSession){


        if(httpSession.getAttribute("dataSource") == null){
            return "index";
        }
        MyDataSourceProperty myDataSourceProperty = (MyDataSourceProperty)httpSession.getAttribute("dataSource");
        httpSession.setAttribute("timeSpaceForm", timeSpaceForm);
        List<Object> res = mainService.getUniqueName(myDataSourceProperty, timeSpaceForm);
        httpSession.removeAttribute("previewSingleColumn");
        httpSession.removeAttribute("previewSingleData");
        httpSession.setAttribute("uniqueNameList",res);

        System.out.println(timeSpaceForm);

        return "index";
    }

    @RequestMapping("/previewSingle")
    private String previewSingle(TimeSpaceForm timeSpaceForm, Model model, HttpSession httpSession){
        if(httpSession.getAttribute("dataSource") == null){
            return "index";
        }
        MyDataSourceProperty myDataSourceProperty = (MyDataSourceProperty)httpSession.getAttribute("dataSource");
        httpSession.setAttribute("timeSpaceForm", timeSpaceForm);

        PreviewData previewData = mainService.previewSingle(myDataSourceProperty,timeSpaceForm);
        if(previewData != null) {
            System.out.println(previewData.getColumnName());
            System.out.println(previewData.getData());
            httpSession.setAttribute("previewSingleColumn", previewData.getColumnName());
            httpSession.setAttribute("previewSingleData", previewData.getData());
        }else{
            httpSession.removeAttribute("previewSingleColumn");
            httpSession.removeAttribute("previewSingleData");
        }

        return "index";
    }

//    @RequestMapping("previewSingleMap")
//    private String previewSingleMap(HttpSession httpSession, HttpServletResponse httpServletResponse){
//        MyDataSourceProperty myDataSourceProperty = (MyDataSourceProperty)httpSession.getAttribute("dataSource");
//        TimeSpaceForm timeSpaceForm = (TimeSpaceForm) httpSession.getAttribute("timeSpaceForm");
//        String name = timeSpaceForm.getUniqueName();
//        //String name = "李白";
//        System.out.println(myDataSourceProperty.toString());
//        System.out.println(timeSpaceForm.getUniqueName());
//        List<PeopleOrientation> peopleOrientations = TimeSpaceService.getPeopleOrientations(myDataSourceProperty);
//        peopleOrientations.toString();
//        for( PeopleOrientation peopleOrientation : peopleOrientations){
//            System.out.println(peopleOrientation.getName() + " : ");
//            for(Orientation orientation: peopleOrientation.getOrientations()){
//                System.out.println(orientation.toString());
//            }
//        }
//        String url = "redirect:track1.html";
//        for(PeopleOrientation peopleOrientation: peopleOrientations){
//            if(peopleOrientation.getName().compareTo(name) == 0){
//                url += "?" + DataSourceUtil.locationsFormatToString(peopleOrientation.getOrientations());
//            }
//        }
//        return url;
//    }

    @RequestMapping("previewSingleMap")
    private ModelAndView previewSingleMap(HttpSession httpSession, HttpServletResponse httpServletResponse){
        MyDataSourceProperty myDataSourceProperty = (MyDataSourceProperty)httpSession.getAttribute("dataSource");
        TimeSpaceForm timeSpaceForm = (TimeSpaceForm) httpSession.getAttribute("timeSpaceForm");
        String name = timeSpaceForm.getUniqueName();
        //String name = "李白";
        //System.out.println(myDataSourceProperty.toString());
        //System.out.println(timeSpaceForm.getUniqueName());
        List<PeopleOrientation> peopleOrientations = TimeSpaceService.getPeopleOrientations(myDataSourceProperty);
        //peopleOrientations.toString();
//        for( PeopleOrientation peopleOrientation : peopleOrientations){
//            System.out.println(peopleOrientation.getName() + " : ");
//            for(Orientation orientation: peopleOrientation.getOrientations()){
//                System.out.println(orientation.toString());
//            }
//        }
        String url = "track1";
        String data = "";
        String places = "";
        String time = "";
        for(PeopleOrientation peopleOrientation: peopleOrientations){
            if(peopleOrientation.getName().compareTo(name) == 0){
                data += "?" + DataSourceUtil.locationsFormatToString(peopleOrientation.getOrientations());
                places += "?" + DataSourceUtil.placeFormatToString(peopleOrientation.getOrientations());
                time += "?" + DataSourceUtil.timeFormatToString(peopleOrientation.getOrientations());
            }
        }
        ArrayList<Trace> traces = new ArrayList<>();
        Trace trace = new Trace(name,data,"green",places,time);

        traces.add(trace);
        ModelAndView view = new ModelAndView(url);
        view.addObject("data",traces);
        return view;
    }

    @RequestMapping("previewMap")
    private ModelAndView previewMap(HttpSession httpSession, HttpServletResponse httpServletResponse){
        MyDataSourceProperty myDataSourceProperty = (MyDataSourceProperty)httpSession.getAttribute("dataSource");
        TimeSpaceForm timeSpaceForm = (TimeSpaceForm) httpSession.getAttribute("timeSpaceForm");
        //System.out.println(myDataSourceProperty.toString());
        //System.out.println(timeSpaceForm.getUniqueName());
        List<PeopleOrientation> peopleOrientations = TimeSpaceService.getPeopleOrientations(myDataSourceProperty);

        for( PeopleOrientation peopleOrientation : peopleOrientations){
            System.out.println(peopleOrientation.getName() + " : ");
            for(Orientation orientation: peopleOrientation.getOrientations()){
                System.out.println(orientation.toString());
            }
        }

        String url = "track1";
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

        ModelAndView view = new ModelAndView(url);
        view.addObject("data",traces);
        return view;
    }

}
