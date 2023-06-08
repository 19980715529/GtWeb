package com.smallchill.pay.wepay.utils;

import com.alibaba.fastjson.JSON;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.smallchill.core.constant.ConstKey.*;
import static com.smallchill.core.constant.ConstUrl.*;
import static com.smallchill.core.constant.ConstUrl.EXCHANGE_WEPAY_URL;

public class WePayUtils {
    private static final Logger LOGGER = LogManager.getLogger(WePayUtils.class);
    public static String recharge(RechargeRecords rechargeRecords, Map<String,Object> channel){
        String response;
        Map<String, Object> map = new HashMap<>();
        map.put("version","1.0");
        // 商户号
        map.put("mch_id",WEPAY_APPID);
        //
        map.put("notify_url",RECHARGE_WEPAY_CALLBACK_URL);
        // 订单号
        map.put("mch_order_no",rechargeRecords.getOrderNumber());
        // 支付类型  gcash通道配1721；paymaya配1701
        map.put("pay_type",channel.get("code").toString());
        // 支付金额
        map.put("trade_amount",rechargeRecords.getTopUpAmount());
        // 订单时间
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        map.put("order_date",format);
        // 银行代码 非必填
//        map.put("bank_code","test");
        // 银行代码
        map.put("goods_name","GTGame");
        // 生成签名
        String sign = Utils.getSign(map, WEPAY_CKEY);
        map.put("sign",sign);
        // 不参与签名
        map.put("sign_type","MD5");
        LOGGER.error(JSON.toJSONString(map));
        response = Utils.post(RECHARGE_WEPAY_URL, map);
        return response;
    }
    public static String exchange(ExchangeReview exchangeReview, Map<String,Object> channel){
        String response;
        Map<String, Object> map = new HashMap<>();
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        // 获取当前时间
        map.put("apply_date",format);
        // 收款银行代码
        map.put("bank_code","GCASH");
        // 商户订单
        map.put("mch_id",WEPAY_APPID);
        // 商家转账订单号
        map.put("mch_transferId",exchangeReview.getOrderNumber());
        // 收款账号
        map.put("receive_account",exchangeReview.getBankNumber());
        // 收款银行户名
        map.put("receive_name",exchangeReview.getCardholder());
        // 回调地址
        map.put("back_url",EXCHANGE_WEPAY_CALLBACK_URL);
        // 收款金额
        map.put("transfer_amount",exchangeReview.getMoney());
        // 生成签名
        String sign = Utils.getSign(map, WEPAY_PKEY);
        map.put("sign",sign);
        map.put("sign_type","MD5");
        LOGGER.error(JSON.toJSONString(map));
        response= Utils.post(EXCHANGE_WEPAY_URL, map);
        return response;
    }
}
