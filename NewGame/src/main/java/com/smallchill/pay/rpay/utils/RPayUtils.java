package com.smallchill.pay.rpay.utils;

import com.alibaba.fastjson.JSON;
import com.smallchill.pay.mhdPay.model.MhdPay;
import com.smallchill.pay.rpay.model.RPay;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.HttpClientUtils;
import com.smallchill.system.treasure.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.smallchill.core.constant.ConstUrl.EXCHANGE_GALAXY_CALLBACK_URL;
import static com.smallchill.core.constant.ConstUrl.RECHARGE_GALAXY_CALLBACK_URL;

public class RPayUtils {
    private static final Logger LOGGER = LogManager.getLogger(RPayUtils.class);
    public static String recharge(RechargeRecords rechargeRecords, RPay rPay, Map<String, Object> channel){
        String response;
        HashMap<String, Object> map = new HashMap<>();
        // 商户号
        map.put("merchant", rPay.getMerchant());
        // 支付类型
        map.put("payment_type", 3);
        // 提交金额
        map.put("amount", rechargeRecords.getTopUpAmount());
        // 订单号
        map.put("order_id", rechargeRecords.getOrderNumber());
        // 银行代码
        map.put("bank_code", channel.get("code").toString());
        // 回调地址
        map.put("callback_url", RECHARGE_GALAXY_CALLBACK_URL);
        // 跳转地址
        map.put("return_url", "https://www.baidu.com");
        //
        String sign = Utils.getSign(map, rPay.getKey());
        map.put("sign", sign);
        LOGGER.error(JSON.toJSONString(map));
        response = HttpClientUtils.sendPostJson(rPay.getPayUrl(), JSON.toJSONString(map));
        return response;
    }

    public static String exchange(ExchangeReview exchangeReview, RPay rPay){
        String response;
        HashMap<String, Object> map = new HashMap<>();
        // 商户号
        map.put("merchant", rPay.getMerchant());
        // 提交金额
        map.put("total_amount", exchangeReview.getMoney().toString());
        // 回调地址
        map.put("callback_url", EXCHANGE_GALAXY_CALLBACK_URL);
        // 订单号
        map.put("order_id", exchangeReview.getOrderNumber());
        // 银行代码
        map.put("bank", "gcash");
        // 收款人姓名
        map.put("bank_card_name", exchangeReview.getBankNumber());
        // 收款人账号
        map.put("bank_card_account", exchangeReview.getBankNumber());
        // IFSC
        map.put("bank_card_remark", exchangeReview.getBankNumber());
        // 获取签名
        String sign = Utils.getSign(map, rPay.getKey());
        map.put("sign", sign);
        LOGGER.error(JSON.toJSONString(map));
        response = HttpClientUtils.sendPostJson(rPay.getPayOutUrl(), JSON.toJSONString(map));
        return response;
    }
}
