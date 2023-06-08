package com.smallchill.pay.aipay.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.pay.aipay.model.AIPay;
import com.smallchill.pay.omopay.utils.OmoPayUtils;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.HttpClientUtils;
import com.smallchill.system.treasure.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import static com.smallchill.core.constant.ConstUrl.*;
public class AIPayUtils {
    private static final Logger LOGGER = LogManager.getLogger(AIPayUtils.class);

    public static String recharge(RechargeRecords records, AIPay aiPay, Map<String, Object> channel){
        String response="";
        HashMap<String, Object> map = new HashMap<>();
        // 商户id
        map.put("merchantId",aiPay.getMerchantId());
        // 订单号
        map.put("merchantOrderId", records.getOrderNumber());
        // 金额。带两位小数
        map.put("amount",records.getTopUpAmount());
        // 时间戳
        map.put("timestamp",new Date().getTime());
        // 支付类型
        map.put("payType",aiPay.getPayType());
        // 异步回调地址
        map.put("notifyUrl",RECHARGE_AIPAY_CALLBACK_URL);
        // 用户名字 尽量传
        // map.put("firstName","yue xia");
        // map.put("lastName",106);
        // map.put("mobile",106);
        // map.put("email",106);
        // 备注
        map.put("remark","GtGame");
        // 生成签名  加密格式 merchantId=999&merchantOrderId=1000&amount=100.00&abc#123!
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(map));
        String sign = getSign(jsonObject,aiPay.getKey());
        jsonObject.put("sign",sign);
        LOGGER.error(jsonObject.toJSONString());
        response = HttpClientUtils.sendPostJson(aiPay.getPayUrl(), jsonObject.toString());
        return response;
    }

    public static String exchange(ExchangeReview exchangeReview,AIPay aiPay, Map<String,Object> channel){
        String response;
        Map<String, Object> map = new HashMap<>();
        // 商户id
        map.put("merchantId",aiPay.getMerchantId());
        // 订单号
        map.put("merchantOrderId",exchangeReview.getOrderNumber());
        map.put("amount",exchangeReview.getMoney());
        // 时间戳
        map.put("timestamp",new Date().getTime());
        // 回调通知地址
        map.put("notifyUrl",EXCHANGE_AIPAY_CALLBACK_URL);
        // 收款信息
        Map<String, Object> fundInfo = new HashMap<>();
        //  账户类型
        fundInfo.put("accountType","ph");
        // 用户信息
        Map<String, Object> contact = new HashMap<>();
        contact.put("name",exchangeReview.getCardholder());
        fundInfo.put("contact",contact);
        Map<String, Object> ph = new HashMap<>();
        // 账号类型 2为 gcash
        ph.put("accountType",2);
        // 收款账号
        ph.put("accountNumber",exchangeReview.getBankNumber());
        fundInfo.put("ph",ph);
        map.put("fundAccount",fundInfo);
        // 签名  格式  'merchantId=999&merchantOrderId=1000&amount=100.00&abc#123!'
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(map));
        String sign = getSign(jsonObject,aiPay.getKey());
        jsonObject.put("sign",sign);
        LOGGER.error(jsonObject.toJSONString());
        response = HttpClientUtils.sendPostJson(aiPay.getPayOutUrl(), jsonObject.toString());
        return response;
    }


    @NotNull
    public static String getSign(JSONObject jsonObject,String key) {
        String str ="";
        try {
            str = "merchantId=" + jsonObject.getString("merchantId") + "&" +
                    "merchantOrderId=" + jsonObject.getString("merchantOrderId") + "&" +
                    "amount=" + jsonObject.getString("amount") + "&" +
                    key;
        }catch (Exception e){
            return str;
        }
        return Utils.MD5(str);
    }
}
