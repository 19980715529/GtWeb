package com.smallchill.system.treasure.utils;

import com.alibaba.fastjson.JSONObject;
import com.smallchill.pay.betcatpay.model.BetcatPay;
import com.smallchill.pay.betcatpay.utils.BetcatPayUtils;
import com.smallchill.pay.payplus.model.PayPlus;
import com.smallchill.pay.payplus.utils.PayPlusUtils;
import com.smallchill.system.treasure.model.ExchangeReview;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class ExchangeUtils {
    private static final Logger LOGGER = LogManager.getLogger(ExchangeUtils.class);


//    public static boolean PayPlusExchange(ExchangeReview exchangeReview, PayPlus payPlus) {
//        String response = PayPlusUtils.exchange(exchangeReview,payPlus);
//        LOGGER.error(response);
//        if ("".equals(response)) {
//            exchangeReview.setStatus(6);
//            return true;
//        }
//        JSONObject respJson = JSONObject.parseObject(response);
//        String retCode = respJson.getString("retCode");
//        // 成功
//        if ("SUCCESS".equals(retCode)) {
//            // 请求成功 ,获取平台订单号
//            exchangeReview.setStatus(1);
//            // 获取平台订单号
//            String platOrder = respJson.getString("platOrder");
//            exchangeReview.setPfOrderNum(platOrder);
//        } else {
//            // 请求失败, 存储失败原因
//            exchangeReview.setMsg(respJson.getString("retMsg"));
//            // 将状态设置为失败
//            exchangeReview.setStatus(6);
//        }
//        return false;
//    }

    public static boolean BetcatPayExchange(ExchangeReview exchangeReview, BetcatPay betcatPay) {
        String response = BetcatPayUtils.exchange(exchangeReview,betcatPay);
        LOGGER.error(response);
        if ("".equals(response)) {
            exchangeReview.setStatus(6);
            return true;
        }
        JSONObject respJson = JSONObject.parseObject(response);
        int code = respJson.getIntValue("code");
        // 成功
        if (code==0) {
            // 0生成订单，1支付中，2支付未通知，3支付已通知，-1交易失败，-2交易过期，-3交易退还，-4交易异常
            int status = respJson.getJSONObject("data").getIntValue("orderStatus");
            if (status<0){
                respJson.getJSONObject("data").getIntValue("message");
                return true;
            }
            // 请求成功 ,获取平台订单号
            exchangeReview.setStatus(1);
            // 获取平台订单号
            String platOrder = respJson.getString("orderNo");
            exchangeReview.setPfOrderNum(platOrder);
        } else {
            // 请求失败, 存储失败原因
            exchangeReview.setMsg(respJson.getString("error"));
            // 将状态设置为失败
            exchangeReview.setStatus(6);
        }
        return false;
    }
}
