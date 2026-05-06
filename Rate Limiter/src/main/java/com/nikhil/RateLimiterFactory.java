package com.nikhil;

import com.nikhil.strategy.RateLimiterStrategy;
import com.nikhil.strategy.TokenBucketRateLimiter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class RateLimiterFactory {
    private static Map<RateLimiterType, Function<Map<String, Object>, RateLimiterStrategy>> rateLimiterTypeFunctionMap = new HashMap<>();

    static {
        rateLimiterTypeFunctionMap.put(RateLimiterType.TOKEN_BUCKET, new Function<Map<String, Object>, RateLimiterStrategy>() {
            @Override
            public RateLimiterStrategy apply(Map<String, Object> config) {
                TokenBucketRateLimiter tokenBucketRateLimiter = new TokenBucketRateLimiter();
                return tokenBucketRateLimiter;
            }
        });
    }

    public RateLimiterStrategy getRateLimiter(RateLimiterType rateLimiterType, Map<String, Object> config){
        if (rateLimiterTypeFunctionMap.containsKey(rateLimiterType)){
            return rateLimiterTypeFunctionMap.get(rateLimiterType).apply(config);
        }

    }
}
