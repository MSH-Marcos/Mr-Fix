package com.msh.mrfix.helpers;

import java.util.Date;

public class Helper {

    public static Date arriveTime(Date d, String addres){
        d.setHours(d.getHours()+2);
        return d;
    }

    public static float calculatePrice(float p, String city){
        switch (city){
            case "Valencia":
                return p + 10f;
            case "Bilbao":
                return p + 15f;
            case "Madrid":
                return p + 20f;
            default:
                return p;
        }
    }
}
