package in.dev.gmsk.executor_service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Java ExecutorService - Part 1 - Introduction
 * 1.) FixedThreadPool
 * 2.) CachedThreadPool
 * 3.) ScheduledThreadPool -> {
 * Service.schedule
 * service.scheduleAtFixedRate
 * service.scheduleAtFixedDelay
 * }
 * 4.) SingleThreadedExecutor
 */
public class IntroClass {

    // get count available cores
    public static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        //exampleOne();
        //exampleTwo();
        //exampleThree();
        exampleFour();
    }

    public static void exampleOne() {

        // creating the pool
        ExecutorService service = Executors.newFixedThreadPool(10);

        //  Submit to task to execute
        for (int i = 0; i < 100; i++) {
            service.execute(new Task());
        }
        System.out.println("current thread = " + Thread.currentThread().getName());
        service.shutdown();
    }

    public static void exampleTwo() {

        ExecutorService executorService = Executors.newFixedThreadPool(CORE_POOL_SIZE);

        //  Submit to task to execute
        executorService.execute(new CPUIntensiveTask());
        System.out.println("current thread = " + Thread.currentThread().getName());
        executorService.shutdown();
    }

    public static void exampleThree() {

        // creating the pool for lot of short-lived tasks
        ExecutorService service = Executors.newCachedThreadPool();

        //  Submit to task to execute
        for (int i = 0; i < 100; i++) {
            service.execute(new Task());
        }
        System.out.println("current thread = " + Thread.currentThread().getName());
        service.shutdown();
    }

    public static void exampleFour() {

        // for scheduling of tasks
        ScheduledExecutorService service = Executors.newScheduledThreadPool(CORE_POOL_SIZE);

        // task to run after 10-second delay
        service.schedule(new Task(), 10, TimeUnit.SECONDS);

        // task to run repeatedly every 10 seconds
        service.scheduleAtFixedRate(new Task(), 15, 10, TimeUnit.SECONDS);

        // task to run repeatedly 10 seconds after previous task completes
        service.scheduleWithFixedDelay(new Task(), 15, 10, TimeUnit.SECONDS);
    }
}
