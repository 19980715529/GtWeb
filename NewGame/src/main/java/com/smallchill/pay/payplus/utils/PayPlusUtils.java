package com.smallchill.pay.payplus.utils;

import com.alibaba.fastjson.JSON;
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

    public static String recharge(RechargeRecords rechargeRecords, Map<String,Object> channel, Map<String,String> param){
        String response;
        HashMap<String, Object> map = new HashMap<>();
        // 商户号
        map.put("mchId", param.get("mchId"));
        // 订单号
        map.put("orderNo", rechargeRecords.getOrderNumber());
        // 金额
        map.put("amount", rechargeRecords.getTopUpAmount().toString());
        // 产品号
        map.put("product", param.get("product"));
        // 银行代号  phqr
        map.put("bankcode", param.get("bankCode"));
        // 物品说明
        String username = RechargeExchangeCommon.RandomUsername();
        String email = RechargeExchangeCommon.RandomEmail();
        map.put("goods", "email:"+email+"/name:"+username+"/phone:"+rechargeRecords.getPhone());
        // 异步通知地址
        map.put("notifyUrl", RECHARGE_LETSPAY_CALLBACK_URL);
        // 跳转地址
        map.put("returnUrl", "https://www.baidu.com");
        // 生成签名
        String sign = Utils.getSign(map, param.get("key"));
        // 生成签名全部大写
        map.put("sign", sign.toUpperCase());
        LOGGER.error(JSON.toJSONString(map));
        response = Utils.post(param.get("payUrl"), map);
        return response;
    }

    public static String exchange(ExchangeReview exchangeReview, Map<String,Object> channel, Map<String,String> param){
        String response;
        HashMap<String, Object> map = new HashMap<>();
        // 转账类型
        map.put("type",param.get("type"));
        // 商户号
        map.put("mchId",param.get("mchId"));
        // 订单号
        map.put("mchTransNo",exchangeReview.getOrderNumber());
        // 金额
        map.put("amount",exchangeReview.getMoney());
        // 通知地址
        map.put("notifyUrl",EXCHANGE_LETSPAY_CALLBACK_URL);
        // 账户名
        map.put("accountName",exchangeReview.getCardholder());
        // 账号
        map.put("accountNo",exchangeReview.getBankNumber());
        // 银行代码
        map.put("bankCode","Gcash");
        // 备注
        String email = RechargeExchangeCommon.RandomEmail();
        map.put("remarkInfo","email:"+email+"/phone:"+exchangeReview.getPhone()+"/mode:wallet");
        // 生成签名全部大写
        String sign = Utils.getSign(map, param.get("key"));
        map.put("sign",sign.toUpperCase());
        LOGGER.error(JSON.toJSONString(map));
        response = Utils.post(param.get("payOutUrl"), map);
        return response;
    }
}
