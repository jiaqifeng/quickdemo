package com.jack.pinpoint.addressbook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jack on 17-6-7.
 */
public class MyExecutor extends ThreadPoolExecutor {
    public MyExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MICROSECONDS, new LinkedBlockingQueue<Runnable>(), new CaughtExceptionThreadFactory("MyExecutor"));
    }

    public static class CaughtExceptionThreadFactory implements ThreadFactory {

        private Logger logger = LoggerFactory.getLogger(CaughtExceptionThreadFactory.class);

        /**
         * The default thread factory
         */
        private ThreadGroup group;
        private AtomicInteger threadNumber = new AtomicInteger(1);
        private String namePrefix;

        private CaughtExceptionThreadFactory( String threadName ) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "pool-" + threadName + "-thread-";
        }

        public Thread newThread( Runnable r ) {
            Thread t = new Thread( group, r, namePrefix + threadNumber.getAndIncrement(), 0 );
            t.setUncaughtExceptionHandler( new Thread.UncaughtExceptionHandler() {
                public void uncaughtException( Thread t, Throwable e ) {
                    logger.error( t.getName(), e );
                }
            } );
            if( t.isDaemon() ) {
                t.setDaemon( false );
            }
            if( t.getPriority() != Thread.NORM_PRIORITY ) {
                t.setPriority( Thread.NORM_PRIORITY );
            }
            return t;
        }

    }
}
