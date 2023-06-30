package com.smallchill.system.treasure.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.smallchill.pay.aipay.model.AIPay;
import com.smallchill.pay.aipay.utils.AIPayUtils;
import com.smallchill.pay.bpay.model.BPay;
import com.smallchill.pay.bpay.utils.BPayUtils;
import com.smallchill.pay.cloudpay.model.CloudPay;
import com.smallchill.pay.cloudpay.utils.CloudPayUtils;
import com.smallchill.pay.globalPay.model.GlobalPay;
import com.smallchill.pay.globalPay.utils.GlobalPayUtils;
import com.smallchill.pay.luckypay.model.LuckPay;
import com.smallchill.pay.luckypay.utils.LuckyPayUtils;
import com.smallchill.pay.metapay.utils.MetaPayUtils;
import com.smallchill.pay.mhdPay.model.MhdPay;
import com.smallchill.pay.mhdPay.utils.MhdPayUtils;
import com.smallchill.pay.omopay.utils.OmoPayUtils;
import com.smallchill.pay.payplus.utils.PayPlusUtils;
import com.smallchill.pay.rarPay.model.RarPay;
import com.smallchill.pay.rarPay.utils.RarPayUtils;
import com.smallchill.pay.safePay.utils.SafePayUtils;
import com.smallchill.pay.wepay.utils.WePayUtils;
import com.smallchill.system.treasure.model.ExchangeReview;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.RoundingMode;
import java.util.Map;

public class ExchangeUtils {
    private static final Logger LOGGER = LogManager.getLogger(ExchangeUtils.class);

    public static boolean RarPayExchange(ExchangeReview exchangeReview, Map channel, RarPay rarPay) {
        // 订单状态为1：代表发送订单成功，需要向第三方发起代付请求， 发送请求成功并不代表订单支付成功，需要回调返回支付结果
        String response = RarPayUtils.sendExchangeRar(exchangeReview, channel,rarPay);
//        LOGGER.error(response);
        if ("".equals(response)) {
            return true;
        }
        // 获取平台订单号
        JSONObject respJson=null;
        try {
            respJson = JSONObject.parseObject(response);
        }catch (Exception e){
            exchangeReview.setStatus(6);
        }
        int code = respJson.getIntValue("code");
        // 同步请求状态为0代表请求失败，订单变成失败
        if (code == 0) {
            exchangeReview.setMsg(respJson.getString("msg"));
            // 支付失败
            exchangeReview.setStatus(6);
        } else {
            int status = respJson.getJSONObject("data").getIntValue("status");
            // 订单状态:0=未支付;10=支付中;20=支付成功;30=支付失败
            if (status == 0) {
                // 未支付，将订单状态设置为待支付
                exchangeReview.setStatus(1);
            } else if (status == 10) {
                // 支付中，将订单状态设置为待支付
                exchangeReview.setStatus(1);
            } else if (status == 20) {
                // 支付成功，将订单状态设置为已完成
                exchangeReview.setStatus(4);
            } else if (status == 30) {
                // 支付失败，将订单状态设置为支付失败
                exchangeReview.setStatus(6);
            }
        }
        return false;
    }

    public static boolean SafePayExchange(ExchangeReview exchangeReview, Map channel) {
        String response = SafePayUtils.exchange(exchangeReview, channel);
        LOGGER.error(response);
        if ("".equals(response)) {
            return true;
        }
        // 获取平台订单号
        JSONObject respJson = JSONObject.parseObject(response);
        String status = respJson.getString("status");
        if ("success".equals(status)) {
            // 申请成功，需要等待三方回调才能知道最终结果
            exchangeReview.setStatus(1);
        } else {
            // 获取三方反馈
            exchangeReview.setMsg(respJson.getString("status_mes"));
            // 申请失败，将订单状态设置为支付失败
            exchangeReview.setStatus(6);
        }
        return false;
    }

    public static boolean MetaPayExchange(ExchangeReview exchangeReview, Map channel) {

        String response = MetaPayUtils.exchange(exchangeReview, channel);
        LOGGER.error(response);
        if ("".equals(response)) {
            return true;
        }
        // 获取平台订单号
        JSONObject respJson = JSONObject.parseObject(response);
        // 获取请求状态
        int code = respJson.getIntValue("platRespCode");
        if (code == 0) {
            // 请求成功, 获取平台订单号
            String PfOrderNum = respJson.getString("transId");
            exchangeReview.setPfOrderNum(PfOrderNum);
            exchangeReview.setStatus(1);
        } else {
            // 请求失败, 存储失败原因
            exchangeReview.setMsg(respJson.getString("msg"));
            // 将状态设置为失败
            exchangeReview.setStatus(6);
        }
        return false;
    }

    public static boolean GlobalPayExchange(ExchangeReview exchangeReview, Map channel, GlobalPay globalPay) {
        String response = GlobalPayUtils.sendExchange(exchangeReview, channel, globalPay);
        if ("".equals(response)){
            return true;
        }
        JSONObject respJson = JSONObject.parseObject(response);
        String statusStr = respJson.getString("code");
        // 成功
        if ("10000".equals(statusStr)) {
            // 请求成功 ,获取平台订单号
            exchangeReview.setStatus(1);
            String sysOrderNo = respJson.getString("outTradeNo");
            exchangeReview.setPfOrderNum(sysOrderNo);
        } else {
            // 请求失败, 存储失败原因
            exchangeReview.setMsg(respJson.getString("msg"));
            // 将状态设置为失败
            exchangeReview.setStatus(6);
        }
        return false;
    }

    public static boolean BPayExchange(ExchangeReview exchangeReview, Map channel, BPay bPay) {

        String response = BPayUtils.sendExchangeBPay(exchangeReview, channel, bPay);
        if ("".equals(response)){
            return true;
        }
        JSONObject respJson = JSONObject.parseObject(response);
        String code = respJson.getString("code");
        // 成功
        if ("200".equals(code)) {
            // 请求成功 ,获取平台订单号
            exchangeReview.setStatus(1);
            String sysOrderNo = respJson.getJSONObject("data").getString("orderNo");
            exchangeReview.setPfOrderNum(sysOrderNo);
        } else {
            // 请求失败, 存储失败原因
            exchangeReview.setMsg(respJson.getString("message"));
            // 将状态设置为失败
            exchangeReview.setStatus(6);
        }
        return false;
    }


    public static boolean MhdPayExchange(ExchangeReview exchangeReview, MhdPay mhdPay) {

        String response = MhdPayUtils.exchange(exchangeReview,mhdPay);
        LOGGER.error(response);
        if ("".equals(response)) {
            return true;
        }
        JSONObject respJson = JSONObject.parseObject(response);
        int status = respJson.getIntValue("status");
        // 成功
        if (status == 1) {
            // 请求成功 ,获取平台订单号
            exchangeReview.setStatus(1);
        } else {
            // 请求失败, 存储失败原因
            exchangeReview.setMsg(respJson.getString("message"));
            // 将状态设置为失败
            exchangeReview.setStatus(6);
        }
        return false;
    }

    public static boolean PayPlusExchange(ExchangeReview exchangeReview, Map channel,Map param) {
        String response = PayPlusUtils.exchange(exchangeReview, channel,param);
        LOGGER.error(response);
        if ("".equals(response)) {
            return true;
        }
        JSONObject respJson = JSONObject.parseObject(response);
        String retCode = respJson.getString("retCode");
        // 成功
        if ("SUCCESS".equals(retCode)) {
            // 请求成功 ,获取平台订单号
            exchangeReview.setStatus(1);
            // 获取平台订单号
            String platOrder = respJson.getString("platOrder");
            exchangeReview.setPfOrderNum(platOrder);
        } else {
            // 请求失败, 存储失败原因
            exchangeReview.setMsg(respJson.getString("retMsg"));
            // 将状态设置为失败
            exchangeReview.setStatus(6);
        }
        return false;
    }

    public static boolean CloudPayExchange(ExchangeReview exchangeReview, CloudPay cloudPay) {
        String response = CloudPayUtils.exchange(exchangeReview,cloudPay);
        LOGGER.error(response);
        if ("".equals(response)) {
            return true;
        }
        JSONObject respJson = JSONObject.parseObject(response);
        int status = respJson.getIntValue("status");
        // 成功
        if (status == 1) {
            // 请求成功 ,获取平台订单号
            exchangeReview.setStatus(1);
        } else {
            // 请求失败, 存储失败原因
            exchangeReview.setMsg(respJson.getString("message"));
            // 将状态设置为失败
            exchangeReview.setStatus(6);
        }
        return false;
    }

    public static boolean WePayExchange(ExchangeReview exchangeReview, Map channel) {

        String response = WePayUtils.exchange(exchangeReview, channel);
        LOGGER.error(response);
        if ("".equals(response)) {
            return true;
        }
        JSONObject respJson = JSONObject.parseObject(response);
        String result = respJson.getString("respCode");
        if ("SUCCESS".equals(result)) {
            // 请求成功 ,获取平台订单号
            String PfOrderNum = respJson.getString("tradeNo");
            exchangeReview.setPfOrderNum(PfOrderNum);
            exchangeReview.setStatus(1);
        } else {
            // 请求失败, 存储失败原因
            exchangeReview.setMsg(respJson.getString("errorMsg"));
            // 将状态设置为失败
            exchangeReview.setStatus(6);
        }
        return false;
    }

    public static boolean AIPayExchange(ExchangeReview exchangeReview, Map channel, AIPay aiPay) {

        String response = AIPayUtils.exchange(exchangeReview,aiPay, channel);
        LOGGER.error(response);
        if ("".equals(response)) {
            return true;
        }
        JSONObject respJson = JSONObject.parseObject(response);
        int code = respJson.getIntValue("code");
        if (code == 0) {
            // 请求成功, 获取平台订单号
            String PfOrderNum = respJson.getJSONObject("data").getString("payoutId");
            exchangeReview.setPfOrderNum(PfOrderNum);
            exchangeReview.setStatus(1);
        } else {
            // 请求失败, 存储失败原因
            exchangeReview.setMsg(respJson.getString("error"));
            // 将状态设置为失败
            exchangeReview.setStatus(6);
        }
        return false;
    }

    public static boolean OmoPayExchange(ExchangeReview exchangeReview, Map channel) {

        // omom 请求的金额必须是整数这里进行处理
        exchangeReview.setMoney(exchangeReview.getMoney().setScale(0, RoundingMode.FLOOR));
        String response = OmoPayUtils.exchange(exchangeReview, channel);
        LOGGER.error(response);
        if ("".equals(response)) {
            return true;
        }
        // 获取平台订单号
        JSONObject respJson = JSONObject.parseObject(response);
        String statusStr = respJson.getString("status");
        if ("success".equals(statusStr)) {
            // 请求成功, 获取平台订单号
            String PfOrderNum = respJson.getString("transaction_id");
            exchangeReview.setPfOrderNum(PfOrderNum);
            exchangeReview.setStatus(1);
        } else {
            // 请求失败, 存储失败原因
            exchangeReview.setMsg(respJson.getString("msg"));
            // 将状态设置为失败
            exchangeReview.setStatus(6);
        }
        return false;
    }
}
