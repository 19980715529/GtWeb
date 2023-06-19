package com.smallchill.pay.betcatpay.utils;

import com.alibaba.fastjson.JSON;
import com.smallchill.pay.betcatpay.model.BetcatPay;
import com.smallchill.pay.core.utils.HttpCliUtils;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.HttpClientUtils;
import com.smallchill.system.treasure.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.smallchill.core.constant.ConstUrl.*;

public class BetcatPayUtils {
    private static final Logger LOGGER = LogManager.getLogger(BetcatPayUtils.class);

    public static String recharge(RechargeRecords rechargeRecords, BetcatPay betcatPay){

        String response;
        HashMap<String, Object> map = new HashMap<>();
        // 商户号
        map.put("appId", betcatPay.payAppId);
        // 订单号
        map.put("merOrderNo", rechargeRecords.getOrderNumber());
        // 金额
        map.put("amount", rechargeRecords.getTopUpAmount().toString());
        // 产品号
        map.put("currency", betcatPay.currency);
        // 异步通知地址
        map.put("notifyUrl", RECHARGE_BETCATPAY_CALLBACK_URL);
        // 生成签名
        String sign = SignUtils.create(betcatPay.payKey,map);
        // 生成签名全部大写
        map.put("sign", sign);
        LOGGER.error(JSON.toJSONString(map));
        response = HttpClientUtils.sendPostJson(betcatPay.payUrl, JSON.toJSONString(map));
        return response;
    }

    public static String exchange(ExchangeReview exchangeReview, BetcatPay betcatPay){
        String response;
        HashMap<String, Object> map = new HashMap<>();
        // 商户号
        map.put("appId",betcatPay.payOutAppId);
        // 订单号
        map.put("merOrderNo",exchangeReview.getOrderNumber());
        // 货币类型
        map.put("currency",betcatPay.currency);
        // 金额
        map.put("amount",exchangeReview.getMoney().toString());
        // 通知地址
        map.put("notifyUrl",EXCHANGE_BETCATPAY_CALLBACK_URL);
        Map<String, Object> extra = new HashMap<>();
        extra.put("bankCode","CPF");
        extra.put("accountNo",exchangeReview.getBankNumber());
        extra.put("accountName","CPF");
        map.put("extra",extra);
        // 生成签名全部大写
        String sign = SignUtils.create(betcatPay.payOutKey,map);
        map.put("sign",sign);
        LOGGER.error(JSON.toJSONString(map));
        response = HttpClientUtils.sendPostJson(betcatPay.payOutUrl, JSON.toJSONString(map));
        return response;
    }
}
