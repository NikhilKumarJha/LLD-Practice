package com.nikhil;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class ScheduledJob implements Delayed {

    private final Runnable task;
    private final long executeAt;

    public Runnable getTask() {
        return task;
    }

    public ScheduledJob(Runnable task, long executeAt, TimeUnit unit) {
        this.task = task;
        this.executeAt = System.currentTimeMillis() + unit.toMillis(executeAt);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(executeAt - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return Long.compare(this.executeAt, ((ScheduledJob) o).executeAt);
    }
}
