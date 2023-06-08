package com.smallchill.pay.mhdPay.model;

import com.smallchill.pay.utils.model.GalaxyPay;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@PropertySource("classpath:PayConf/MHDPayConfig.properties")
public class MhdPay {
    @Value("${m_merchant}")
    private String merchant;

    @Value("${m_key}")
    private String key;

    @Value("${m_payUrl}")
    private String payUrl;

    @Value("${m_payOutUrl}")
    private String payOutUrl;
}
