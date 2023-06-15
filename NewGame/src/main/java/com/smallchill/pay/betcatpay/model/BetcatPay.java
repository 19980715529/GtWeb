package com.smallchill.pay.betcatpay.model;

import com.smallchill.core.base.model.BaseModel;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource(value = "classpath:PayConf/BetcatPayConfig.properties")
public class BetcatPay extends BaseModel {
    @Value("${Betcat.payAppId}")
    public String payAppId;

    @Value("${Betcat.payOutAppId}")
    public String payOutAppId;

    @Value("${Betcat.currency}")
    public String currency;
    // 代收密钥
    @Value("${Betcat.payKey}")
    public String payKey;
    // 代付密钥
    @Value("${Betcat.payOutKey}")
    public String payOutKey;
    // 收款地址
    @Value("${Betcat.payUrl}")
    public String payUrl;
    // 付款地址
    @Value("${Betcat.payOutUrl}")
    public String payOutUrl;

}
