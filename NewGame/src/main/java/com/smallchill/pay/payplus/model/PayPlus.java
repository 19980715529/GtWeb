package com.smallchill.pay.payplus.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@PropertySource("classpath:PayConf/LetsPayConfig.properties")
public class PayPlus {
    // 类型
    @Value("${lets_type}")
    public String type;
    // 商户id
    @Value("${lets_mchId}")
    public String mchId;
    // 产品
    @Value("${lets_product}")
    public String product;
    // 密钥
    @Value("${lets_key}")
    public String key;
    // 支付地址
    @Value("${lets_payUrl}")
    public String payUrl;
    // 代付地址
    @Value("${lets_payOutUrl}")
    public String payOutUrl;
    // 银行代码
    @Value("${lets_bankcode}")
    public String bankCode;

}
