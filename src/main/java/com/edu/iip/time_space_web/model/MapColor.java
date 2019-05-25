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

    public static String getColorByBaiFenBi(double per) {
        //var 百分之一 = (单色值范围) / 50;  单颜色的变化范围只在50%之内
        int bili = (int) Math.round(per * 100);
        double one = (255 + 255) / 100;
        double r = 0;
        double g = 0;
        double b = 0;
        if (bili < 50) {
            // 比例小于50的时候红色是越来越多的,直到红色为255时(红+绿)变为黄色.
            r = one * bili;
            g = 255;
        }
        if (bili >= 50) {
            // 比例大于50的时候绿色是越来越少的,直到0 变为纯红
            g = 255 - ((bili - 50) * one);
            r = 255;
        }

        String rs = Integer.toHexString((int) r).toUpperCase();
        String gs = Integer.toHexString((int) g).toUpperCase();
        String bs = Integer.toHexString((int) b).toUpperCase();

        rs = rs.length() == 1 ? "0" + rs : rs;
        gs = gs.length() == 1 ? "0" + gs : gs;
        bs = bs.length() == 1 ? "0" + bs : bs;

        return "#" + rs + gs + bs;
    }

}
