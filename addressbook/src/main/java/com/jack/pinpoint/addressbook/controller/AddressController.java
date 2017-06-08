package com.jack.pinpoint.addressbook.controller;

import com.jack.pinpoint.addressbook.dao.AddressDao;
import com.jack.pinpoint.addressbook.domain.Address;
import com.jack.pinpoint.addressbook.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@RestController
public class AddressController {
    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private SingleExecutor executor;

    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping(value = "/api/v1/address", method = RequestMethod.GET)
    @ResponseBody
    public Result<Address> findOneAddress(Integer id, String name) {
        Result<Address> result = Result.create();
        try {
            Address src;
            if (name == null)
                src=addressDao.findById(id);
            else
                src=addressDao.findByName(name);

            if (src==null)
                result.fail("500", "not exist");
            else
                result.success(src);
        } catch (Exception e){
            logger.error("can't find general address", e);
            result.fail("500", "服务器错误");
        }
        return result;
    }

    @RequestMapping(value = "/api/v1/address", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> createAddress(@RequestBody Address address) {
        Result<Integer> result = Result.create();
        try {
            Integer count=addressDao.insertAddress(address);
            if (count==1) {
                result.success(address.getId());
                return result;
            }
        } catch (Exception e){
            logger.error("can't insert general address", e);
            result.fail("500", "服务器错误");
        }

        return result;
    }

    @RequestMapping(value = "/runnable", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> runnable(Integer id) {
        Result<String> result = Result.create();
        try {
            Runnable runnable=(Runnable) applicationContext.getBean("myRunnable", id);
            executor.execute(runnable);
            result.success("exist");
        } catch (Exception e){
            logger.error("can't find general address", e);
            result.fail("500", "internal error");
        }
        return result;
    }

    @RequestMapping(value = "/callable", method = RequestMethod.GET)
    @ResponseBody
    public Result<Address> callable(Integer id) {
        Result<Address> result = Result.create();
        try {
            Future<Address> addressFuture = null;
            addressFuture=executor.submit(new MyCallable(id, addressDao));
            Address address=addressFuture.get(1, TimeUnit.SECONDS);
            if (address!=null)
                result.success(address);
        } catch (Exception e){
            logger.error("can't find general address", e);
            result.fail("500", "internal error");
        }
        return result;
    }

}
