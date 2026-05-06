package com.nikhil.strategy;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.locks.ReentrantLock;

public class TokenBucketRateLimiter implements RateLimiterStrategy{

    private int capacity;
    private int refreshRate;
    private Bucket bucket;
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private long refillIntervalMillis;

    private class Bucket {
        private int tokens;
        private final ReentrantLock lock = new ReentrantLock();

        Bucket(int tokens){
            this.tokens = tokens;
        }

        public boolean tryConsume() {
            lock.lock();
            try {
                if (tokens>0){
                    tokens--;
                    return true;
                }
                return false;
            } finally {
                lock.unlock();
            }
        }

        public void refill(){
            lock.lock();
            try {
                tokens = Math.min(capacity, tokens + refreshRate);
            } finally {
                lock.unlock();
            }
        }
    }

    @Override
    public boolean giveAccess(String rateLimitKey) {
        return false;
    }

    @Override
    public void updateConfig(Map<String, Object> config) {

    }

    @Override
    public void shutdown() {

    }
}
