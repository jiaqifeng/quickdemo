package com.jack.pinpoint.addressbook.controller;

import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by jack on 17-6-7.
 */
@Component
public class SingleExecutor {
    private ExecutorService executorService = null;

    public SingleExecutor() {
        executorService = Executors.newSingleThreadExecutor();
    }

    public void execute(Runnable command) {
        executorService.execute(command);
    }

    public <T> Future<T> submit(Callable<T> callable) {
        return executorService.submit(callable);
    }

}
