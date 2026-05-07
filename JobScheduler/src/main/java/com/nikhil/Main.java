package com.nikhil;

import java.util.concurrent.TimeUnit;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() throws InterruptedException {
        JobScheduler scheduler = new JobScheduler(2, 4);
        scheduler.schedule(() ->
                        System.out.println("Job 1 executed at " + System.currentTimeMillis()),
                2, TimeUnit.SECONDS
        );

        scheduler.schedule(() ->
                        System.out.println("Job 2 executed at " + System.currentTimeMillis()),
                4, TimeUnit.SECONDS
        );

        scheduler.schedule(() ->
                        System.out.println("Job 3 executed at " + System.currentTimeMillis()),
                1, TimeUnit.SECONDS
        );

        Thread.sleep(6000);
        scheduler.shutdown();
    }
}
