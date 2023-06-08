package com.smallchill.pay.omopay.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@PropertySource("classpath:PayConf/OmoPayConfig.properties")
public class OmoPay {
    @Value("${pay_memberid}")
    private String appid;

    @Value("${pay_bankcode}")
    private String bankCode;

    @Value("${omo_key}")
    private String key;

    @Value("${bankname}")
    private String bankName;

    @Value("${currency}")
    private String currency;

    @Value("${omo_payUrl}")
    private String payUrl;

    @Value("${omo_payOutUrl}")
    private String payOutUrl;
}
