package com.smallchill.pay.wepay.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@PropertySource("classpath:PayConf/WePayConfig.properties")
public class WePay {
    @Value("${we.mch_id}")
    private String mchId;

    @Value("${we.ckey}")
    private String ckey;

    @Value("${we.pkey}")
    private String pkey;

    @Value("${we.sign_type}")
    private String signType;


    @Value("${we.bank_code}")
    private String bankCode;

    @Value("${we.payUrl}")
    private String payUrl;

    @Value("${we.payOutUrl}")
    private String payOutUrl;
}
