package com.smallchill.pay.safePay.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@PropertySource("classpath:PayConf/SafePayConfig.properties")
public class SafePay {
    @Value("${safe.currency}")
    private String currency;

    @Value("${safe.mer_no}")
    private String merNo;

    @Value("${safe.method}")
    private String method;

    @Value("${safe.key}")
    private String key;


    @Value("${safe.payUrl}")
    private String payUrl;
}
