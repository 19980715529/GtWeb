package com.smallchill.common.utils;

import java.lang.annotation.*;

@Target(ElementType.METHOD) // 该注解只能用于方法上
@Retention(RetentionPolicy.RUNTIME) // 运行时保留该注解
@Documented
public @interface RateLimit {
    int limit() default 10; // 限制次数
    int period() default 60; // 限制时间，单位秒
}