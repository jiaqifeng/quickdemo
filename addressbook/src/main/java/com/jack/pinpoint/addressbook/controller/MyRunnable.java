package com.jack.pinpoint.addressbook.controller;

import com.jack.pinpoint.addressbook.dao.AddressDao;
import com.jack.pinpoint.addressbook.domain.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

/**
 * Created by jack on 17-6-7.
 */
@Component("myRunnable")
@Scope(value = "prototype")
public class MyRunnable implements Runnable {
    @Autowired
    private AddressDao addressDao;

    private Integer id;

    private static int index=0;

    public MyRunnable() {}

    public MyRunnable(Integer id) {
        this.id=id;
        System.out.println("---- construct MyRunnable id="+id);
    }

    public void run() {

        if (addressDao==null) System.out.println("------addressDao==null");
        System.out.println("---- MyRunnable.run() id="+id);

        Address src;
        src=addressDao.findById(id);

        System.out.println("index="+(2/(index++%2)));
        if (src==null)
            System.out.println("return 500 not exist");
        else
            System.out.println("return 200 address="+src);

    }
}
