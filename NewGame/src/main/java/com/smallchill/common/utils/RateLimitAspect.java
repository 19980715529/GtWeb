package com.smallchill.common.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
public class RateLimitAspect {

    private final Map<String, AtomicInteger> counters = new ConcurrentHashMap<>();
    private final Map<String, Long> timestamps = new ConcurrentHashMap<>();

    @Around("@annotation(rateLimit)")
    public Object checkRateLimit(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        String methodName = joinPoint.getSignature().toLongString();
        int limit = rateLimit.limit(); // 限制次数
        int period = rateLimit.period(); // 限制时间，单位秒
        long now = System.currentTimeMillis();
        long timestamp = timestamps.getOrDefault(methodName, now);
        if (now - timestamp > period * 1000L) { // 时间窗口过期，重置计数器和时间戳
            counters.put(methodName, new AtomicInteger(0));
            timestamps.put(methodName, now);
        }
        AtomicInteger counter = counters.getOrDefault(methodName, new AtomicInteger(0));
        if (counter.incrementAndGet() > limit) {
            throw new RuntimeException("接口访问次数超限");
        }
        return joinPoint.proceed();
    }
}

