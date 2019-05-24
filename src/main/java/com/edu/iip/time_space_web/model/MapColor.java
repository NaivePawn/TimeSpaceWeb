package com.edu.iip.time_space_web.model;

import java.util.Random;

/**
 * @author zengc
 * @date 2019/5/24 14:02
 */
public class MapColor {

    public static String GREEN = "green";
    public static String RED = "red";
    public static String YELLOW = "yellow";
    public static String BLUE = "blue";
    public static String BLACK = "black";

    private static String[] colors = new String[]{GREEN,RED,YELLOW,BLUE,BLACK};




    public static String randomColor(){
        String r,g,b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();

        r = r.length()==1 ? "0" + r : r ;
        g = g.length()==1 ? "0" + g : g ;
        b = b.length()==1 ? "0" + b : b ;

        return "#"+r+g+b;
    }

}
