package com.smallchill.core.enums;

import com.smallchill.core.constant.PayUrl;

import static com.smallchill.core.constant.ConstConfig.CALLBACK_IP;

public enum RarPayConstEnum implements PayUrl {
    // 充值地址
    RECHARGE_URL("https://merchant.rarpay.com/payapi"),
    // 兑换请求地址
    EXCHANGE_URL("https://merchant.rarpay.com/dpayapi"),
    //充值回调地址
    RECHARGE_CALLBACK_URL(CALLBACK_IP+"/NewGame/callback/recharge_rarp_callback"),
    // 兑换回调地址
    EXCHANGE_CALLBACK_URL(CALLBACK_IP+"/NewGame/callback/exchange_rarp_callback"),
    // 商户名
    APPID("test123456"),
    // 密钥
    SECRET_KEY("6012A2FF24D0D37CF6A491F5091BEA81");
    //
    private final String value;

    RarPayConstEnum(String value){
        this.value=value;
    }

    @Override
    public String getUrl() {
        return value;
    }

    @Override
    public String toString(){
        return value;
    }
}
