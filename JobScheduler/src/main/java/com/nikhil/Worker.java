package com.nikhil;

import java.util.concurrent.*;

public class Worker implements Runnable{

    private final BlockingQueue<ScheduledJob> delayQueue;
    private final ExecutorService executorService;

    public Worker(BlockingQueue<ScheduledJob> delayQueue, ExecutorService executorService) {
        this.delayQueue = delayQueue;
        this.executorService = executorService;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            try {
                ScheduledJob job = delayQueue.take();
                executorService.submit(job.getTask());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
