package com.smallchill.pay.utils.rarPayUtils;

import com.alibaba.fastjson.JSON;
import com.smallchill.pay.model.rarpPay.RarPay;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.smallchill.core.constant.ConstUrl.EXCHANGE_RARP_CALLBACK_URL;
import static com.smallchill.core.constant.ConstUrl.RECHARGE_RARP_CALLBACK_URL;

public class RarPayUtils {
    private static final Logger LOGGER = LogManager.getLogger(RarPayUtils.class);
    /**
     * 充值rpra
     */
    public static String sendRechargeRar(RechargeRecords rechargeRecords, Map<String,Object> channel, RarPay rarPay){
        // 生成请求条件
        String response;
        Date date = new Date();
        String dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        HashMap<String, Object> params = new HashMap<>();
        params.put("mchNo", rarPay.getMchNo());
        params.put("time", dateFormat);
        params.put("type", channel.get("code"));
        params.put("fee", rechargeRecords.getTopUpAmount());
        params.put("orderNo", rechargeRecords.getOrderNumber());
        params.put("notifyUrl", RECHARGE_RARP_CALLBACK_URL);// 回调地址
        // 获取签名
        String sign  = Utils.getSign(params, rarPay.getKey());
        params.put("sign", sign);
        LOGGER.error(JSON.toJSONString(params));
        response = Utils.post(rarPay.getPayUrl(), params);
        return response;
    }

    /**
     * rarp兑换
     */
    public static String sendExchangeRar(ExchangeReview exchangeReview, Map<String,Object> channel, RarPay rarPay){
        String response="";
        Date date = new Date();
        String dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> extra = new HashMap<>();
        // 通过请求过后向地三方发起代付请求
        map.put("mchNo",rarPay.getMchNo());
        map.put("time",dateFormat);
        map.put("type",channel.get("code"));
        map.put("orderNo", exchangeReview.getOrderNumber());
        map.put("notifyUrl",EXCHANGE_RARP_CALLBACK_URL);// 回调地址
        map.put("fee",exchangeReview.getMoney());
//        map.put("bankCode","gcash");
        map.put("bankAccount",exchangeReview.getBankNumber());
        map.put("ifsc","no");
        map.put("customerName",exchangeReview.getBankNumber());
        extra.put("custPhone", exchangeReview.getBankNumber());
        extra.put("bank_province", "no");
        extra.put("to_city", "no");
        map.put("extra", Utils.parseExtInfo(extra));
        // 获取签名
        String sign = Utils.getSign(map, rarPay.getKey());
        if ("".equals(sign)){
            return response;
        }
        map.put("sign",sign);
        LOGGER.error(JSON.toJSONString(map));
        response = Utils.post(rarPay.getPayOutUrl(), map);
        return response;
    }
}
