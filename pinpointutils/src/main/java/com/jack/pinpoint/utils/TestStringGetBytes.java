package com.jack.pinpoint.utils;

/**
 * Created by jack on 17-3-28.
 */
public class TestStringGetBytes {
    public static void main(String[] args) {
        String blank="";
        byte[] array=blank.getBytes();
        System.out.println("blank string getBytes length="+array.length);
    }
}
