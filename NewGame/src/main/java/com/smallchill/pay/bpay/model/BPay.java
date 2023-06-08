package com.smallchill.pay.bpay.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * BPay充值参数
 */
@Component
@Data
@PropertySource("classpath:PayConf/BPayConfig.properties")
public class BPay {
    //商户号
    @Value("${merchantNo}")
    private String merchantNo;
    // 密钥
    @Value("${accessPrivateKey}")
    private String accessPrivateKey;
    // 公钥
    @Value("${callbackPublicKey}")
    private String callbackPublicKey;
    // 国家代码
    @Value("${countryCode}")
    private String countryCode;
    // 币种
    @Value("${currencyCode}")
    private String currencyCode;
    // 支付/代付业务编码
    @Value("${paymentType}")
    private String paymentType;
    // 兑换需要的代码
    @Value("${transferType}")
    private String transferType;
    // 充值地址
    @Value("${b_payUrl}")
    private String payUrl;
    // 兑换地址
    @Value("${b_payOutUrl}")
    private String payOutUrl;
    @Value("${sign_type}")
    private String type;
}