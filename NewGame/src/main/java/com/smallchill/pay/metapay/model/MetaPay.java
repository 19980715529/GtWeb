package com.smallchill.pay.metapay.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@PropertySource("classpath:PayConf/MetaPayConfig.properties")
public class MetaPay {
    @Value("${appId}")
    private String appId;

    @Value("${channel}")
    private String channel;

    @Value("${productType}")
    private String productType;

    @Value("${meta_public_key}")
    private String metaPublicKey;

    @Value("${meta_private_key}")
    private String metaPrivateKey;

    @Value("${platform_public_key}")
    private String platformPublicKey;

    @Value("${Meta.payUrl}")
    private String payUrl;

    @Value("${Meta.payOutUrl}")
    private String payOutUrl;


    @Value("${pickupCenter}")
    private String pickupCenter;

    @Value("${certificateType}")
    private String certificateType;
}
