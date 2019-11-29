package com.msh.mrfix.helpers;

import java.util.Date;

public class Helper {

    public static Date arriveTime(Date d, String addres){
        d.setHours(d.getHours()+2);
        return d;
    }
}
