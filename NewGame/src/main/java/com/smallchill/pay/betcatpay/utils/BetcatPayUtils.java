package com.smallchill.pay.betcatpay.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
//        LOGGER.error(JSON.toJSONString(map));
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
//        LOGGER.error(JSON.toJSONString(map));
        response = HttpClientUtils.sendPostJson(betcatPay.payOutUrl, JSON.toJSONString(map));
        return response;
    }

    // 发起兑换功能
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
