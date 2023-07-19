package com.smallchill.pay.omopay.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.pay.metapay.utils.MetaPayUtils;
import com.smallchill.pay.omopay.model.OmoPay;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.HttpClientUtils;
import com.smallchill.system.treasure.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.smallchill.core.constant.ConstKey.OMOM_APPID;
import static com.smallchill.core.constant.ConstKey.OMOM_KEY;
import static com.smallchill.core.constant.ConstUrl.*;
import static com.smallchill.core.constant.ConstUrl.EXCHANGE_OMOM_URL;

public class OmoPayUtils {
    private static final Logger LOGGER = LogManager.getLogger(OmoPayUtils.class);

    public static String recharge(RechargeRecords rechargeRecords, OmoPay omoPay){
        String response="";
        Date date = new Date();
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        Map<String, Object> map = new HashMap<>();
        // 商户号
        map.put("pay_memberid",omoPay.getPayMemberId());
        // 商户订单号
        map.put("pay_orderid", rechargeRecords.getOrderNumber());
        // 订单号
        map.put("pay_amount",rechargeRecords.getTopUpAmount());
        // 提交时间
        map.put("pay_applydate",format);
        //
        map.put("pay_bankcode",omoPay.getPayBankCode());
        // 回调地址
        map.put("pay_notifyurl",RECHARGE_OMOM_CALLBACK_URL);
        //生产签名
        String sign = Utils.getSign(map, omoPay.getKey());
        // 签名
        map.put("pay_md5sign",sign.toUpperCase());
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(map));
        LOGGER.error(jsonObject.toJSONString());
        response = HttpClientUtils.sendPostJson(omoPay.getPayUrl(), jsonObject.toJSONString());
        return response;
    }

    public static String exchange(ExchangeReview exchangeReview, OmoPay omoPay){
        String response="";
        Map<String, Object> map = new HashMap<>();
        // 商户号
        map.put("mchid",omoPay.getPayMemberId());
        // 商户订单号
        map.put("out_trade_no", exchangeReview.getOrderNumber());
        // 币种
        map.put("currency","PHP");
        // 代付金额
        map.put("money",exchangeReview.getMoney());
        //  银行编码
        map.put("bankname","gcash");
        // 收款人姓名
        map.put("accountname",exchangeReview.getCardholder());
        // 收款账号
        map.put("cardnumber",exchangeReview.getBankNumber());
        // 回调地址
        map.put("notifyurl",EXCHANGE_OMOM_CALLBACK_URL);
        //生产签名
        String sign = Utils.getSign(map, omoPay.getKey());
        // 签名
        map.put("pay_md5sign",sign.toUpperCase());
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(map));
        LOGGER.error(jsonObject.toJSONString());
        response = HttpClientUtils.sendPostJson(omoPay.getPayOutUrl(), jsonObject.toJSONString());
        return response;
    }

    public static boolean OmoPayExchange(ExchangeReview exchangeReview,OmoPay omoPay) {

        // omom 请求的金额必须是整数这里进行处理
        exchangeReview.setMoney(exchangeReview.getMoney().setScale(0, RoundingMode.FLOOR));
        String response = OmoPayUtils.exchange(exchangeReview, omoPay);
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
