package com.nikhil;

import java.util.concurrent.*;

public class JobScheduler {
    private final BlockingQueue<ScheduledJob> queue = new DelayQueue<>();
    private final ExecutorService jobExecutor;
    private final ExecutorService workerExecutor;

    public JobScheduler(int workerThread, int jobThreads) {
        this.jobExecutor = Executors.newFixedThreadPool(jobThreads);
        this.workerExecutor = Executors.newFixedThreadPool(workerThread);

        for(int i=0;i<workerThread;i++){
            workerExecutor.submit(new Worker(queue, jobExecutor));
        }
    }

    public void schedule(Runnable task, long delay, TimeUnit timeUnit){
        queue.offer(new ScheduledJob(task, delay, timeUnit));
    }

    public void shutdown(){
        workerExecutor.shutdownNow();
        jobExecutor.shutdownNow();
    }
}
