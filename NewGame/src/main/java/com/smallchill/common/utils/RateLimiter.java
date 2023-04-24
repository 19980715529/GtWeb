package com.smallchill.common.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class RateLimiter {
    private ConcurrentHashMap<String, AtomicInteger> counters = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Long> timeStamps = new ConcurrentHashMap<>();
    private int limit;
    private int duration;

    public RateLimiter(int limit, int duration) {
        this.limit = limit;
        this.duration = duration;
    }

    public boolean allow(String key) {
        long now = System.currentTimeMillis();
        if (timeStamps.containsKey(key)) {
            long timeStamp = timeStamps.get(key);
            if (now < timeStamp + TimeUnit.SECONDS.toMillis(duration)) {
                AtomicInteger counter = counters.get(key);
                if (counter.get() >= limit) {
                    return false;
                } else {
                    counter.incrementAndGet();
                    return true;
                }
            } else {
                counters.remove(key);
                timeStamps.remove(key);
            }
        }
        counters.put(key, new AtomicInteger(1));
        timeStamps.put(key, now);
        return true;
    }
}