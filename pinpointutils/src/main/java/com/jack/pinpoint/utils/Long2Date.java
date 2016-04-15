package com.jack.pinpoint.utils;

import java.util.Date;

/**
 * Created by jack on 4/15/16.
 */
public class Long2Date {
    public static void main(String[] args) {
        System.out.println("arg="+args[0]);
        long dd;
        try {
            dd=Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("illegal long : "+args[0]);
            return;
        }

        Date d=new Date(dd);
        System.out.println("Date:"+d);
    }
}
