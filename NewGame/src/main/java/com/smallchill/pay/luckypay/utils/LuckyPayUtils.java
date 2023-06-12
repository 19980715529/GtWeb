package com.smallchill.pay.luckypay.utils;

import com.alibaba.fastjson.JSON;
import com.smallchill.pay.luckypay.model.LuckPay;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.RechargeExchangeCommon;
import com.smallchill.system.treasure.utils.SendHttp;
import com.smallchill.system.treasure.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.smallchill.core.constant.ConstKey.LUCKYPAY_APPID;
import static com.smallchill.core.constant.ConstKey.PUBLIC_LUCKYPAY_KEY;
import static com.smallchill.core.constant.ConstUrl.*;
import static com.smallchill.core.constant.ConstUrl.EXCHANGE_LUCKYPAY_URL;

public class LuckyPayUtils {
    private static final Logger LOGGER = LogManager.getLogger(LuckyPayUtils.class);
    public static String sendRechargeLuckyPay(RechargeRecords rechargeRecords, LuckPay luckPay){
        String response;
        Map<String, Object> params = new HashMap();
        // 商户号
        params.put("merNo", luckPay.merNo);
        // 订单号
        params.put("orderNo", rechargeRecords.getOrderNumber());
        // 通道
        params.put("rechargeMethod", luckPay.rechargeMethod);
        // 姓名
        params.put("name", RechargeExchangeCommon.RandomUsername());
        // 邮箱
        params.put("email", RechargeExchangeCommon.RandomEmail());
        // 电话
        params.put("phone", getRandomPhone(10));
        // 订单金额
        params.put("amount", rechargeRecords.getTopUpAmount().toString());
        // 异步回调地址
        params.put("notifyUrl", RECHARGE_LUCKYPAY_CALLBACK_URL);
        // 成功跳转地址
        params.put("backUrl", "https://google.com");
        params.put("term", "");
        params.put("errorBackUrl", "");
        // 获取签名
        String sign = Utils.getSign(params,luckPay.publicKey);
        params.put("sign",sign);
//        params.put("strategy",luckPay.strategy);
        LOGGER.error(JSON.toJSONString(params));
        response = Utils.post(luckPay.payUrl, params);
        return response;
    }

    public static String sendExchangeLuckyPay(ExchangeReview exchangeReview, LuckPay luckPay){
        String response;
        Map<String, Object> params = new HashMap();
        // 商户号
        params.put("merNo", luckPay.merNo);
        // 订单号
        params.put("orderNo", exchangeReview.getOrderNumber());
        // 通道
        params.put("method", luckPay.method);
        // 兑换金额
        params.put("amount", exchangeReview.getMoney().toString());
        // 姓名
        params.put("name", exchangeReview.getCardholder());
        // 邮箱
        params.put("email", "test@gmail.com");
        // 电话
        params.put("phone", exchangeReview.getBankNumber());
        // 回调地址
        params.put("notifyUrl", EXCHANGE_LUCKYPAY_CALLBACK_URL);
        params.put("account",exchangeReview.getBankNumber());
        // UPI 账号
        params.put("upi", "");
        // 银行代码
        params.put("ifsc", luckPay.ifsc);
        // 生成签名
        String sign = Utils.getSign(params, luckPay.publicKey);
        params.put("sign",sign);
        LOGGER.error(JSON.toJSONString(params));
        response = Utils.post(luckPay.payOutUrl, params);
        return response;
    }
    public static String getRandomPhone(int length) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            if (i==0){
                // [min,max]
                // 区间公式 int randNumber = rand.nextInt(MAX - MIN + 1) + MIN;
                val.append(random.nextInt(4)+6);
            }else {
                val.append(random.nextInt(10));
            }
        }
        return val.toString();
    }
}
