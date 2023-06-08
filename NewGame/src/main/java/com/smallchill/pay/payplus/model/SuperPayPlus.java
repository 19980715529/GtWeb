package com.smallchill.pay.payplus.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@PropertySource("classpath:PayConf/LetsPaySupConfig.properties")
public class SuperPayPlus {
    // 类型
    @Value("${lets_sup_type}")
    public String type;
    // 商户id
    @Value("${lets_sup_mchId}")
    public String mchId;
    // 产品
    @Value("${lets_sup_product}")
    public String product;
    // 密钥
    @Value("${lets_sup_key}")
    public String key;
    // 支付地址
    @Value("${lets_sup_payUrl}")
    public String payUrl;
    // 代付地址
    @Value("${lets_sup_payOutUrl}")
    public String payOutUrl;
    // 银行代码
    @Value("${lets_sup_bankcode}")
    public String bankCode;
}
