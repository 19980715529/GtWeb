package com.smallchill.pay.cloudpay.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@PropertySource("classpath:PayConf/CloudPayConfig.properties")
public class CloudPay {
    @Value("${c_merchant}")
    private String merchant;

    @Value("${c_key}")
    private String key;

    @Value("${c_payUrl}")
    private String payUrl;

    @Value("${c_payOutUrl}")
    private String payOutUrl;
}
