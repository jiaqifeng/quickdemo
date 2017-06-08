package com.jack.pinpoint.addressbook.controller;

import com.jack.pinpoint.addressbook.dao.AddressDao;
import com.jack.pinpoint.addressbook.domain.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

/**
 * Created by jack on 17-6-8.
 */
@Component("myCallable")
@Scope(value = "prototype")
public class MyCallable implements Callable<Address> {
    private AddressDao addressDao;

    private Integer id;

    private static int index=0;

    public MyCallable() {}

    public MyCallable(Integer id, AddressDao addressDao) {
        this.id=id;
        this.addressDao=addressDao;
        System.out.println("---- construct MyCallable id="+id);
    }

    public Address call() throws Exception {
        if (addressDao==null) System.out.println("------addressDao==null");
        System.out.println("---- MyCallable.call() id="+id);

        Address src;
        src=addressDao.findById(id);

        System.out.println("index="+(2/(index++%2)));
        if (src==null)
            System.out.println("return null");
        else
            System.out.println("return "+src);

        return src;
    }
}
