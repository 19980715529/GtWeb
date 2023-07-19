package com.smallchill.pay.omopay.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@PropertySource("classpath:PayConf/OmoPayConfig.properties")
public class OmoPay {
    @Value("${omo.pay_memberid}")
    private String payMemberId;

    @Value("${omo.pay_bankcode}")
    private String payBankCode;

    @Value("${omo.currency}")
    private String currency;

    @Value("${omo.bankname}")
    private String bankName;

    @Value("${omo.key}")
    private String key;

    @Value("${omo.payUrl}")
    private String payUrl;

    @Value("${omo.payOutUrl}")
    private String payOutUrl;
}
