package com.smallchill.pay.rpay.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@PropertySource("classpath:PayConf/RPayConfig.properties")
public class RPay {
    // 商户号
    @Value("${r_merchant}")
    public String merchant;

    @Value("${r_payUrl}")
    public String payUrl;

    @Value("${r_payOutUrl}")
    public String payOutUrl;

    @Value("${r_key}")
    public String key;
}
