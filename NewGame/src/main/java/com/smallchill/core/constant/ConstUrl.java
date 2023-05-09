package com.smallchill.core.constant;

import static com.smallchill.core.constant.ConstConfig.CALLBACK_IP;
import static com.smallchill.core.constant.ConstConfig.META_Pay_URL;

/**
 * @Description TODO
 * @classNameConstUrl
 * @Date 2023/2/15 11:56
 * @Version 1.0
 **/

public interface ConstUrl {

    // RARPAY渠道地址
    /**
     * 兑换回调地址   http://8.222.205.176:1234/NewGame/callback/exchange_rarp_callback
     */
    String EXCHANGE_RARP_CALLBACK_URL=CALLBACK_IP+"/NewGame/callback/exchange_rarp_callback";
    /**
     * 充值回调    http://8.222.205.176:1234/NewGame/callback/recharge_rarp_callback
     */
    String RECHARGE_RARP_CALLBACK_URL=CALLBACK_IP+"/NewGame/callback/recharge_rarp_callback";

    /**
     * safe兑换回调
     */
    String EXCHANGE_SAFE_CALLBACK_URL=CALLBACK_IP+"/NewGame/callback/exchange_safe_callback";
    /**
     * safe充值回调d
     */
    String RECHARGE_SAFE_CALLBACK_URL=CALLBACK_IP+"/NewGame/callback/recharge_safa_callback";


    /**
     * 充值对应的三方请求地址rarp 测试：https://rarpay.test.rarpay.com/payapi
     * 正式：https://merchant.rarpay.com/payapi
     */
    String RECHARGE_URL="https://merchant.rarpay.com/payapi";

    /**
     * 兑换对应的三方请求地址rarp 测试：https://rarpay.test.rarpay.com/dpayapi
     * 正式：https://merchant.rarpay.com/dpayapi
     */
    String EXCHANGE_URL="https://merchant.rarpay.com/dpayapi";

    /**
     * 游戏端地址 18.140.146.117  http://192.168.0.108:5555
     */
//    String GAME_URL ="http://192.168.0.108:5555";

    /**
     *  safe 兑换充值请求地址
     */
    String SAFE_URL = "http://api.pnsafepay.com/gateway.aspx";


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
    String RECHARGE_META_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/recharge_meta_callback";
    /**
     * MetaPay兑换回调地址
     */
    String EXCHANGE_META_PAY_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/exchange_meta_pay_callback";



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
    String RECHARGE_OMOM_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/recharge_omom_callback";
    /**
     * OMOM 兑换回调地址
     */
    String EXCHANGE_OMOM_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/exchange_omom_callback";


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
    String RECHARGE_AIPAY_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/recharge_AIPay_callback";
    /**
     * AIPay 兑换回调地址
     */
    String EXCHANGE_AIPAY_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/exchange_AIPay_callback";

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
    String RECHARGE_WEPAY_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/recharge_WePay_callback";
    /**
     * WePay 兑换回调地址
     */
    String EXCHANGE_WEPAY_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/exchange_WePay_callback";

    /**
     * 银河系统充值地址 GALAXY
     */
    String RECHARGE_GALAXY_URL = "https://pay.xxxx.cash/api/transfer";
    // 兑换地址
    String EXCHANGE_GALAXY_URL="https://pay.xxxx.cash/api/daifu";

    // GALAXY 充值回调地址
    String RECHARGE_GALAXY_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/recharge_galaxy_callback";
    // GALAXY 兑换回调地址
    String EXCHANGE_GALAXY_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/exchange_galaxy_callback";
    /**
     * LetsPay
     */
    // 充值
    String RECHARGE_LETSPAY_URL = "http://api.letspayfast.com/apipay";
    // 兑换地址
    String EXCHANGE_LETSPAY_URL="http://api.letspayfast.com/apitrans";

    // GALAXY 充值回调地址
    String RECHARGE_LETSPAY_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/recharge_letspay_callback";
    // GALAXY 兑换回调地址
    String EXCHANGE_LETSPAY_CALLBACK_URL = CALLBACK_IP+"/NewGame/callback/exchange_letspay_callback";

    /**
     * MHDpay
     */
    // 充值
    String RECHARGE_MHDPAY_URL = "https://apimhd.xxxx.cash/api/transfer";
    // 兑换
    String EXCHANGE_MHDPAY_URL = "https://apimhd.xxxx.cash/api/daifu";
}

