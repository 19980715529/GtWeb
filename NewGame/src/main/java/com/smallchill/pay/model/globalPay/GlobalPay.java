package com.smallchill.pay.model.globalPay;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@PropertySource("classpath:PayConf/GlobalPayConfig.properties")
public class GlobalPay {
    // 商户号
    @Value("${alliesNo}")
    public String alliesNo;

    @Value("${tradeType}")
    public Integer tradeType;

    @Value("${productName}")
    public String productName;

    @Value("${g_payUrl}")
    public String payUrl;

    @Value("${g_payOutUrl}")
    public String payOutUrl;

    @Value("${g_key}")
    public String key;

    @Value("${productId}")
    public String productId;
    @Value("${bankName}")
    public String bankName;
    @Value("${bankMark}")
    public String bankMark;
}
