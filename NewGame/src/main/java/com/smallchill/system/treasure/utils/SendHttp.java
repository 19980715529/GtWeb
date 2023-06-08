package com.smallchill.system.treasure.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.pay.cloudpay.model.CloudPay;
import com.smallchill.pay.mhdPay.model.MhdPay;
import com.smallchill.pay.utils.model.GalaxyPay;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.smallchill.core.constant.ConstConfig.GAME_URL;
import static com.smallchill.core.constant.ConstKey.*;
import static com.smallchill.core.constant.ConstUrl.*;

/**
 * @Description TODO
 * @classNameSendHttp
 * @Date 2023/2/23 9:55
 * @Version 1.0
 **/
public class SendHttp {
    private static final Logger LOGGER = LogManager.getLogger(SendHttp.class);
//    public static String sendRechargeGalaxy(RechargeRecords rechargeRecords, MhdPay mhdPay, Map<String, Object> channel){
//        String response;
//        HashMap<String, Object> map = new HashMap<>();
//        // 商户号
//        map.put("merchant", mhdPay.getMerchant());
//        // 支付类型
//        map.put("payment_type", 3);
//        // 提交金额
//        map.put("amount", rechargeRecords.getTopUpAmount());
//        // 订单号
//        map.put("order_id", rechargeRecords.getOrderNumber());
//        // 银行代码
//        map.put("bank_code", channel.get("code").toString());
//        // 回调地址
//        map.put("callback_url", RECHARGE_GALAXY_CALLBACK_URL);
//        // 跳转地址
//        map.put("return_url", "https://www.baidu.com");
//        //
//        String sign = Utils.getSign(map, mhdPay.getKey());
//        map.put("sign", sign);
//        LOGGER.error(JSON.toJSONString(map));
//        response = HttpClientUtils.sendPostJson(mhdPay.getPayUrl(), JSON.toJSONString(map));
//        return response;
//    }


//    public static String sendExchangeGalaxy(ExchangeReview exchangeReview,JSONObject params,Map<String,Object> channel){
//        String response;
//        HashMap<String, Object> map = new HashMap<>();
//        // 商户号
//        map.put("merchant", params.getString("merchant"));
//        // 提交金额
//        map.put("total_amount", exchangeReview.getMoney().toString());
//        // 回调地址
//        map.put("callback_url", EXCHANGE_GALAXY_CALLBACK_URL);
//        // 订单号
//        map.put("order_id", exchangeReview.getOrderNumber());
//        // 银行代码
//        map.put("bank", "gcash");
//        // 收款人姓名
//        map.put("bank_card_name", exchangeReview.getBankNumber());
//        // 收款人账号
//        map.put("bank_card_account", exchangeReview.getBankNumber());
//        // IFSC
//        map.put("bank_card_remark", exchangeReview.getBankNumber());
//        // 获取签名
//        String sign = Utils.getSign(map, params.getString("key"));
//        map.put("sign", sign);
//        LOGGER.error(JSON.toJSONString(map));
//        response = HttpClientUtils.sendPostJson(params.getString("payOutUrl"), JSON.toJSONString(map));
//        return response;
//    }


    @NotNull
    public static String getSign(JSONObject jsonObject) {
        String str ="";
        try {
            str = "merchantId=" + jsonObject.getString("merchantId") + "&" +
                    "merchantOrderId=" + jsonObject.getString("merchantOrderId") + "&" +
                    "amount=" + jsonObject.getString("amount") + "&" +
                    AIPAY_KEY;
        }catch (Exception e){
            return str;
        }
        return Utils.MD5(str);
    }

    // 发送邮件请求1001  金币类型:GoldType
    public static void sendEmail(Map<String,Object> map){
        String str = "gold:" +map.get("gold").toString()+
                "|action:1001"+
                "|title:" +map.get("title").toString()+
                "|toUserid:" +map.get("toUserid").toString()+
                "|content:"+map.get("content").toString()+
                "|FromUserID:"+map.get("senderId").toString()+
                "|GoldType:"+map.get("goldType").toString();
        HttpClientUtils.sendPostJson(GAME_URL, str);
    }

    /** 1002 充值兑换成功后需要调用
     * type:1 usdt充值，0:普通充值兑换
     * @param map
     */
    public static void sendGame1002(Map<String,Object> map){
        String str = "gold:" +map.get("gold")+
                "|action:1002"+
                "|Type:" +map.get("Type")+
                "|Userid:" +map.get("Userid")+
                "|gameCoin:"+map.get("gameCoin");
        HttpClientUtils.sendPostJson(GAME_URL, str);
    }
    // 1003 发送到后台提现前端修改金币数量
    public static void sendGame1003(Integer userId){
        String str = "Userid:" + userId;
        HttpClientUtils.sendPostJson(GAME_URL, str);
    }

    /**
     * 生产随机位数字符串
     * @param length
     * @return
     */
    public static String getRandomNickname(int length) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            val.append(random.nextInt(10));
        }
        return val.toString();
    }
}
