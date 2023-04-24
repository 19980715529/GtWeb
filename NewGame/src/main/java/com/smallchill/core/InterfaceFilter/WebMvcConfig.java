package com.smallchill.core.InterfaceFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
 
     /**
      * 请求限流拦截器
      */
     @Autowired
     protected RequestLimitingInterceptor requestLimitingInterceptor;
 
     @Override
     public void addInterceptors(InterceptorRegistry registry) {
         // 请求限流
         registry.addInterceptor(requestLimitingInterceptor).addPathPatterns("/**");
     }
 
}
