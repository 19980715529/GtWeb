package com.smallchill.pay.payplus.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.pay.payplus.model.PayPlus;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.RechargeExchangeCommon;
import com.smallchill.system.treasure.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.smallchill.core.constant.ConstUrl.EXCHANGE_LETSPAY_CALLBACK_URL;
import static com.smallchill.core.constant.ConstUrl.RECHARGE_LETSPAY_CALLBACK_URL;

public class PayPlusUtils {
    private static final Logger LOGGER = LogManager.getLogger(PayPlusUtils.class);

    public static String recharge(RechargeRecords rechargeRecords, Map<String,String> params){
        String response;
        HashMap<String, Object> map = new HashMap<>();
        // 商户号
        map.put("mchId", params.get("mchId"));
        // 订单号
        map.put("orderNo", rechargeRecords.getOrderNumber());
        // 金额
        map.put("amount", rechargeRecords.getTopUpAmount().toString());
        // 产品号
        map.put("product", params.get("product"));
        // 银行代号
        map.put("bankcode", params.get("bankCode"));
        // 物品说明
        String username = RechargeExchangeCommon.RandomUsername();
        String email = RechargeExchangeCommon.RandomEmail();
        String phone = "";
        if (!isNullOrEmpty(rechargeRecords.getPhone())){
            phone = "7894561230";
        }else {
            phone = rechargeRecords.getPhone();
        }
        map.put("goods", "email:"+email+"/name:"+ username +"/phone:"+phone+"/cpf:1234567892");
        // 异步通知地址
        map.put("notifyUrl", RECHARGE_LETSPAY_CALLBACK_URL);
        // 跳转地址
        map.put("returnUrl", "https://www.baidu.com");
        // 生成签名
        String sign = Utils.getSign(map, params.get("key"));
        // 生成签名全部大写
        map.put("sign", sign.toUpperCase());
        response = Utils.post(params.get("payUrl"), map);
        return response;
    }

    public static String exchange(ExchangeReview exchangeReview, Map<String,String> params){
        String response;
        HashMap<String, Object> map = new HashMap<>();
        // 转账类型
        map.put("type",params.get("type"));
        // 商户号
        map.put("mchId",params.get("mchId"));
        // 订单号
        map.put("mchTransNo",exchangeReview.getOrderNumber());
        // 金额
        map.put("amount",exchangeReview.getMoney());
        // 通知地址
        map.put("notifyUrl",EXCHANGE_LETSPAY_CALLBACK_URL);
        // 账户名
        map.put("accountName",RechargeExchangeCommon.RandomUsername());
        // 账号,cpf
        map.put("accountNo",exchangeReview.getBankNumber());
        // 银行代码
        map.put("bankCode",params.get("bankCode"));
        // 备注
        String email = RechargeExchangeCommon.RandomEmail();
        // email:520155@gmail.com/phone:9784561230/mode:pix/cpf:555555555(必须是真实的)
        map.put("remarkInfo","email:"+email+"/phone:"+exchangeReview.getPhone()+"/mode:pix/cpf:"+exchangeReview.getBankNumber());
        // 生成签名全部大写
        String sign = Utils.getSign(map, params.get("key"));
        map.put("sign",sign.toUpperCase());
        response = Utils.post(params.get("payOutUrl"), map);
        return response;
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0;
    }

    // 兑换工具类
    public static boolean PayPlusExchange(ExchangeReview exchangeReview, Map<String,String> map) {
        String response = PayPlusUtils.exchange(exchangeReview,map);
        if ("".equals(response)) {
            exchangeReview.setStatus(6);
            return true;
        }
        JSONObject respJson;
        try {
            respJson = JSONObject.parseObject(response);
        }catch (Exception e){
            return true;
        }
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
}
