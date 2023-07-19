package com.smallchill.core.constant;

import java.util.Map;

import static com.smallchill.core.constant.ConstConfig.CALLBACK_IP;
import static com.smallchill.core.constant.ConstConfig.META_Pay_URL;

/**
 * @Description TODO
 * @classNameConstUrl
 * @Date 2023/2/15 11:56
 * @Version 1.0
 **/

public interface ConstUrl {

    //===================================================== RARPAY渠道地址======================================================
    /**
     * 充值对应的三方请求地址rarp 测试：https://rarpay.test.rarpay.com/payapi
     * 正式：https://merchant.rarpay.com/payapi
     */
    //===================================================Gcash的统一回调地址======================================================
    String RARP_RECHARGE_GCASH_URL="https://merchant.rarpay.com/payapi";
    // 测试
//    String RARP_RECHARGE_GCASH_URL="https://rarpay.test.rarpay.com/payapi";
    /**
     * 兑换对应的三方请求地址rarp 测试：https://rarpay.test.rarpay.com/dpayapi
     * 正式：https://merchant.rarpay.com/dpayapi
     */
    String RARP_EXCHANGE_GCASH_URL="https://merchant.rarpay.com/dpayapi";
    // 测试
//    String RARP_EXCHANGE_GCASH_URL="https://rarpay.test.rarpay.com/dpayapi";

    /**
     * 兑换回调地址   http://8.222.205.176:1234/NewGame/callback/exchange_rarp_callback
     */
    String EXCHANGE_RARP_CALLBACK_URL=CALLBACK_IP+"/NewGame/callback/RarPay/exchange";
    /**
     * 充值回调    http://8.222.205.176:1234/NewGame/callback/recharge_rarp_callback
     */
    String RECHARGE_RARP_CALLBACK_URL=CALLBACK_IP+"/NewGame/callback/RarPay/recharge";
    //===================================================Paymaya的统一回调地址======================================================


    /**
     * 游戏端地址 18.140.146.117  http://192.168.0.108:5555
     */
    //=====================================================safe================================================================

    /**
     *  safe 兑换充值请求地址
     */
    String SAFE_URL = "http://api.pnsafepay.com/gateway.aspx";
    /**
     * safe兑换回调
     */
    String EXCHANGE_SAFE_CALLBACK_URL=CALLBACK_IP+"/NewGame/callback/SafePay/exchange";
    /**
     * safe充值回调d
     */
    String RECHARGE_SAFE_CALLBACK_URL=CALLBACK_IP+"/NewGame/callback/SafePay/recharge";

    //================================================MetaPay=====================================================

    /**
     * MetaPay 充值地址
     */
    String RECHARGE_META_URL=META_Pay_URL+"payment/collect/collect";
    /**
     * MetaPay兑换地址
     */
    String EXCHANGE_META_URL=META_Pay_URL+"payment/remit/payout";
    /**
     * MetaPay 充值回调
     */
    String RECHARGE_META_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/MetaPay/recharge";
    /**
     * MetaPay兑换回调地址
     */
    String EXCHANGE_META_PAY_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/MetaPay/exchange";

    //==================================================OMOM===================================================

    /**
     * OMOM充值请求地址
     */
    String RECHARGE_OMOM_URL="https://pay.omompay.com/pay";
    /**
     * OMOM兑换请求地址
     */
    String EXCHANGE_OMOM_URL="https://pay.omompay.com/payment";
    /**
     * OMOM 充值回调地址
     */
    String RECHARGE_OMOM_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/OmoPay/recharge";
    /**
     * OMOM 兑换回调地址
     */
    String EXCHANGE_OMOM_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/OmoPay/exchange";
    //==================================================AIPay充值请求地址===================================================

    /**
     * AIPay充值请求地址
     */
    String RECHARGE_AIPAY_URL = "https://top.adkjk.in/rpay-api/order/submit";
    /**
     * AIPay 兑换地址
     */
    String EXCHANGE_AIPAY_URL="https://top.adkjk.in/rpay-api/payout/submit";
    /**
     * AIPay 充值回调地址
     */
    String RECHARGE_AIPAY_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/AIPay/recharge";
    /**
     * AIPay 兑换回调地址
     */
    String EXCHANGE_AIPAY_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/AIPay/exchange";
    //==================================================WePay===================================================
    /**
     * WePay 充值地址
     */
    String RECHARGE_WEPAY_URL = "https://pyabxum.weglobalex.com/pay/web";
    /**
     * WePay 兑换地址
     */
    String EXCHANGE_WEPAY_URL="https://pyabxum.weglobalex.com/pay/transfer";
    /**
     * WePay 充值回调地址
     */
    String RECHARGE_WEPAY_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/callback/WePay/recharge";
    /**
     * WePay 兑换回调地址
     */
    String EXCHANGE_WEPAY_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/callback/WePay/exchange";
    //==================================================CloudPay===================================================
    /**
     * 银河系统充值地址 CloudPay
     */
    // GALAXY 充值回调地址
    String RECHARGE_GALAXY_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/Galaxy/recharge";
    // GALAXY 兑换回调地址
    String EXCHANGE_GALAXY_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/Galaxy/exchange";
    //==================================================LetsPay===================================================
    /**
     * LetsPay
     */
    // LetsPay 充值回调地址
    String RECHARGE_LETSPAY_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/PayPlus/recharge";
    // LetsPay 兑换回调地址
    String EXCHANGE_LETSPAY_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/PayPlus/exchange";
    //==================================================MHDPay===================================================
    /**
     * MHDpay
     */
    //==================================================LuckyPay===================================================
    /**
     * Luckypay
     */
    // 兑换地址
    String EXCHANGE_LUCKYPAY_URL = "https://api.lumypay.com/api/payout";
    //  Luckypay 充值回调地址
    String RECHARGE_LUCKYPAY_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/LuckyPay/recharge";
    // Luckypay 兑换回调地址
    String EXCHANGE_LUCKYPAY_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/LuckyPay/exchange";
    /**
     * BPay支付回调地址地址
     */
    String RECHARGE_BPAY_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/BPay/recharge";
    // BPay 回调地址
    String EXCHANGE_BPAY_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/BPay/exchange";
    /**
     * GlobalPay回调地址
     */
    String RECHARGE_GLOBALPAY_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/GlobalPay/recharge";
    // GlobalPay 回调地址
    String EXCHANGE_GLOBALPAY_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/GlobalPay/exchange";

    /**
     * BetcatPay回调地址
     */
    String RECHARGE_BETCATPAY_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/BetcatPay/recharge";
    // GlobalPay 回调地址
    String EXCHANGE_BETCATPAY_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/BetcatPay/exchange";
}

