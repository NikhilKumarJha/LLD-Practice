package com.nikhil.strategy;

import java.util.Map;

public interface RateLimiterStrategy {
    boolean giveAccess(String rateLimitKey);
    void updateConfig(Map<String, Object> config);
    void shutdown();
}
