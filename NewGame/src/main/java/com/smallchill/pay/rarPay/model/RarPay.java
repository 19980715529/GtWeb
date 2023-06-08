package com.smallchill.pay.rarPay.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@PropertySource("classpath:PayConf/RarPayConfig.properties")
public class RarPay {
    // 商户名
    @Value("${mchNo}")
    private String mchNo;
    // 密钥
    @Value("${key}")
    private String key;
    // 带收地址
    @Value("${payUrl}")
    private String payUrl;
    // 代付地址
    @Value("${payOutUrl}")
    private String payOutUrl;
}
