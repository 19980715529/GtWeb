package com.smallchill.pay.aipay.model;

import com.smallchill.core.base.model.BaseModel;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@PropertySource("classpath:PayConf/AIPayConfig.properties")
public class AIPay extends BaseModel {
    @Value("${merchantId}")
    private Integer merchantId;

    @Value("${payType}")
    private Integer payType;

    @Value("${aipay_key}")
    private String key;

    @Value("${aipay_payUrl}")
    private String payUrl;

    @Value("${aipay_payOutUrl}")
    private String payOutUrl;

}
