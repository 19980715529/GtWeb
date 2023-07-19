package com.smallchill.pay.payplus.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@PropertySource("classpath:PayConf/PhpPayPlusConfig.properties")
public class PhpPayPlus extends BasePay {
    // 类型
    @Value("${php_type}")
    public String type;
    // 商户id
    @Value("${php_mchId}")
    public String mchId;
    // 产品
    @Value("${php_product}")
    public String product;
    // 密钥
    @Value("${php_key}")
    public String key;
    // 支付地址
    @Value("${php_payUrl}")
    public String payUrl;
    // 代付地址
    @Value("${php_payOutUrl}")
    public String payOutUrl;
    // 银行代码
    @Value("${php_bankcode}")
    public String bankCode;
}
