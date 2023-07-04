package com.smallchill.common.utils;

import com.smallchill.common.exceptions.CustomException;
import com.smallchill.core.base.controller.BladeController;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
public class RateLimitAspect extends BladeController {
    // 存储访问次数
    private final Map<String, AtomicInteger> counters = new ConcurrentHashMap<>();
    // 存储过期时间
    private final Map<String, Long> timestamps = new ConcurrentHashMap<>();

    @Around("@annotation(rateLimit)")
    public Object checkRateLimit(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        String url = getRequest().getRequestURI();
        String ip = getIP(getRequest());
        String url_ip= url + ip;
        int limit = rateLimit.limit(); // 限制次数
        int period = rateLimit.period(); // 限制时间，单位秒
        long now = System.currentTimeMillis();
        AtomicInteger atomicInteger = counters.get(url_ip);
        // 判断是否为空，为空添加数据
        if (atomicInteger==null) {
            counters.put(url_ip, new AtomicInteger(0));
            timestamps.put(url_ip, now);
        }
        // 如果存在相应的key则返回其对应的value，否则返回给定的默认值。
        long timestamp = timestamps.getOrDefault(url_ip, now);
        // 时间窗口过期，重置计数器和时间戳
        if (now - timestamp > period * 1000L) {
            counters.put(url_ip, new AtomicInteger(0));
            timestamps.put(url_ip, now);
        }
        // 获取接口访问次数
        AtomicInteger counter = counters.getOrDefault(url_ip, new AtomicInteger(0));
        int count = counter.incrementAndGet();
        System.out.println("接口"+url_ip+"访问次数："+count);
        if (count > limit) {
            throw new CustomException("105014");
        }
        return joinPoint.proceed();
    }

    public String getIP(HttpServletRequest request){
        String ip=request.getHeader("x-forwarded-for");
        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
            ip=request.getHeader("Proxy-Client-IP");
        }
        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
            ip=request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
            ip=request.getHeader("X-Real-IP");
        }
        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
            ip=request.getRemoteAddr();
        }
        return ip;
    }
}

