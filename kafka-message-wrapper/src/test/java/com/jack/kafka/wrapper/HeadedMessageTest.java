package com.jack.kafka.wrapper;

import static org.junit.Assert.*;

/**
 * Created by jack on 4/14/16.
 */
public class HeadedMessageTest {

    @org.junit.Test
    public void test1() throws Exception {
        HeadedMessage hm1 = new HeadedMessage();
        HeadedMessage hm2 = new HeadedMessage();

        hm1.setHeader("key1", "value1");
        hm1.setHeader("id", "3");
        hm1.setHeader("flag", "true");
        hm1.setMessage("message");

        System.out.println("hm1=["+hm1+"]");
        hm2.decode(hm1.encode());
        System.out.println("hm2=["+hm2+"]");
        assertEquals(hm2.getHeader("key1"), "value1");
        assertEquals(hm2.getHeader("id"), "3");
        assertEquals(hm2.getHeader("flag"), "true");
        assertEquals(hm2.getMessage(), "message");
    }

    @org.junit.Test
    public void testSetHeader() throws Exception {

    }

    @org.junit.Test
    public void testGetHeader() throws Exception {

    }

    @org.junit.Test
    public void testSetMessage() throws Exception {

    }

    @org.junit.Test
    public void testEncode() throws Exception {

    }

    @org.junit.Test
    public void testDecode() throws Exception {

    }
}