package com.smallchill.pay.safePay.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.pay.rarPay.utils.RarPayUtils;
import com.smallchill.pay.safePay.model.SafePay;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.HttpClientUtils;
import com.smallchill.system.treasure.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static com.smallchill.core.constant.ConstKey.*;
import static com.smallchill.core.constant.ConstUrl.*;

public class SafePayUtils {
    private static final Logger LOGGER = LogManager.getLogger(SafePayUtils.class);

    public static String recharge(RechargeRecords rechargeRecords, Map<String,Object> channel, SafePay safePay){
        Map<String, Object> params = new HashMap<>();
        params.put("currency", safePay.getCurrency());
        params.put("mer_no", safePay.getMerNo());
        params.put("order_amount", rechargeRecords.getTopUpAmount());
        params.put("method", "trade.create");
        params.put("order_no", rechargeRecords.getOrderNumber());
        params.put("payemail", "youyanjianghu@gmail.com");
        params.put("payname", "LI LAN LAN");
        String phone = rechargeRecords.getPhone();
        if (!Utils.isIgnoredItem(phone)){
            params.put("payphone", phone);
        }
        params.put("payphone", "85256332649");
        params.put("paytypecode", channel.get("code")); // 支付类型 21001 gacsh 21002 paymaya
        params.put("returnurl", RECHARGE_SAFE_CALLBACK_URL);
        //  签名
        String sign  = HttpClientUtils.getSign(params, safePay.getKey());
        params.put("sign", sign);
        LOGGER.error(JSON.toJSONString(params));
        return HttpClientUtils.sendPostJson(safePay.getPayUrl(), JSON.toJSONString(params));
    }

    public static String exchange(@NotNull ExchangeReview exchangeReview, Map<String,Object> channel, SafePay safePay){
        Map<String, Object> params = new HashMap<>();
        params.put("currency", safePay.getCurrency());
        params.put("mer_no", safePay.getPayUrl());
        params.put("order_amount", exchangeReview.getMoney().toString());
        params.put("method", "fund.apply");
        params.put("order_no", exchangeReview.getOrderNumber());
        params.put("acc_code", channel.get("code")); // PAYMAYA  "PH_GCASH"
        params.put("acc_name", exchangeReview.getCardholder());
        params.put("acc_no", exchangeReview.getBankNumber());
        params.put("returnurl", EXCHANGE_SAFE_CALLBACK_URL);
        // 签名
        String sign  = HttpClientUtils.getSign(params, safePay.getKey());
        params.put("sign", sign);
        LOGGER.error(JSON.toJSONString(params));
        return HttpClientUtils.sendPostJson(safePay.getPayUrl(), JSON.toJSONString(params));
    }


    public static boolean SafePayExchange(ExchangeReview exchangeReview, Map channel,SafePay safePay) {


        String response = SafePayUtils.exchange(exchangeReview, channel,safePay);
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
}
