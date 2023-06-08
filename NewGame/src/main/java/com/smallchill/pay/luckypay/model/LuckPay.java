package com.smallchill.pay.luckypay.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@PropertySource("classpath:PayConf/LuckPayConfig.properties")
public class LuckPay {
    @Value("${merNo}")
    public String merNo;

    @Value("${rechargeMethod}")
    public String rechargeMethod;

    @Value("${luckypay_public_key}")
    public String publicKey;

    @Value("${luckypay_private_key}")
    public String privateKey;

    @Value("${platform_public_key}")
    public String platformPublicKey;

//    @Value("${strategy}")
//    public String strategy;

    @Value("${method}")
    public String method;

    @Value("${ifsc}")
    public String ifsc;

    @Value("${luck_payUrl}")
    public String payUrl;

    @Value("${luck_payOutUrl}")
    public String payOutUrl;


}
