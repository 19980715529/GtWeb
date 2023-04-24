package com.smallchill.system.treasure.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.smallchill.core.constant.ConstConfig.GAME_URL;
import static com.smallchill.core.constant.ConstConfig.META_Pay_URL;
import static com.smallchill.core.constant.ConstKey.*;
import static com.smallchill.core.constant.ConstUrl.*;

/**
 * @Description TODO
 * @classNameSendHttp
 * @Date 2023/2/23 9:55
 * @Version 1.0
 **/
public class SendHttp {
    private static Logger LOGGER = LogManager.getLogger(SendHttp.class);
    /**
     * 充值请求safe
     */
    public static String sendRechargeSafe(RechargeRecords rechargeRecords, Map<String,Object> channel){
        Map<String, Object> map = new HashMap<>();
        map.put("currency", "PHP");
        map.put("mer_no", ACCOUNT_SAFE_NUM);
        map.put("order_amount", rechargeRecords.getTopUpAmount());
        map.put("method", "trade.create");
        map.put("order_no", rechargeRecords.getOrderNumber());
        map.put("payemail", ACCOUNT_SAFE_EMAIL);
        map.put("payname", "LI LAN LAN");
        map.put("payphone", "85256332649");
        map.put("paytypecode", channel.get("code")); // 支付类型 21001 gacsh 21002 paymaya
        map.put("returnurl", RECHARGE_SAFE_CALLBACK_URL);
        //  签名
        String sign  = HttpClientUtils.getSign(map, SECRET_SAFE_KEY);
        map.put("sign", sign);
        String res = HttpClientUtils.sendPostJson(SAFE_URL, JSONObject.toJSONString(map));
        return res;
    }
    /**
     * 兑换请求safe
     */
    public static String sendExchangeSafe(ExchangeReview exchangeReview,Map<String,Object> channel){
        Map<String, Object> map = new HashMap<>();
        map.put("currency", "PHP");
        map.put("mer_no", ACCOUNT_SAFE_NUM);
        map.put("order_amount", exchangeReview.getMoney().toString());
        map.put("method", "fund.apply");
        map.put("order_no", exchangeReview.getOrderNumber());
        map.put("acc_code", "PH_GCASH");
        map.put("acc_name", exchangeReview.getCardholder());
        map.put("acc_no", exchangeReview.getBankNumber());
        map.put("returnurl", EXCHANGE_SAFE_CALLBACK_URL);
        // 签名
        String sign  = HttpClientUtils.getSign(map, SECRET_SAFE_KEY);
        map.put("sign", sign);
        String res = HttpClientUtils.sendPostJson(SAFE_URL, JSONObject.toJSONString(map));
        return res;
    }
    /**
     * 充值rpra
     */
    public static String sendRechargeRarp(RechargeRecords rechargeRecords, Map<String,Object> channel){
        // 生成请求条件
        String response="";
        Date date = new Date();
        String dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        HashMap<String, Object> map = new HashMap<>();
        map.put("mchNo", ACCOUNT_RARP_NUM);
        map.put("time", dateFormat);
        map.put("type", channel.get("code"));
        map.put("fee", rechargeRecords.getTopUpAmount());
        map.put("orderNo", rechargeRecords.getOrderNumber());
        map.put("notifyUrl", RECHARGE_RARP_CALLBACK_URL);// 回调地址
        // 获取签名
        String sign  = Utils.getSign(map, SECRET_RARP_KEY);
        if ("".equals(sign)){
            return response;
        }
        map.put("sign", sign);
        LOGGER.error(map);
        response = Utils.post(RECHARGE_URL, map);
        return response;
    }
    /**
     * rarp兑换
     */

    public static String sendExchangeRarp(ExchangeReview exchangeReview){
        String response="";
        Date date = new Date();
        String dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> extra = new HashMap<>();
        // 通过请求过后向地三方发起代付请求
        map.put("mchNo",ACCOUNT_RARP_NUM);
        map.put("time",dateFormat);
        map.put("type","gcash_dpay");
        map.put("orderNo", exchangeReview.getOrderNumber());
        map.put("notifyUrl",EXCHANGE_RARP_CALLBACK_URL);
        map.put("fee",exchangeReview.getMoney());
        map.put("bankCode","PAYMAYA");
        map.put("bankAccount",exchangeReview.getBankNumber());
        map.put("ifsc","no");
        map.put("customerName",exchangeReview.getBankNumber());
        extra.put("custPhone", exchangeReview.getBankNumber());
        extra.put("bank_province", "no");
        extra.put("to_city", "no");
        map.put("extra", Utils.parseExtInfo(extra));
        // 获取签名
        String sign = Utils.getSign(map, SECRET_RARP_KEY);
        if ("".equals(sign)){
            return response;
        }
        map.put("sign",sign);
        LOGGER.error(map);
        response = Utils.post(EXCHANGE_URL, map);
        return response;
    }

    // MetaPay代收
    public static String sendRechargeMetaPay(RechargeRecords records){
        String response="";
        HashMap<String, Object> map = new HashMap<>();
        map.put("appId",META_APPID);
        // 代收渠道
        map.put("channel",3);
        // 平台订单号
        map.put("referenceNo", records.getOrderNumber());
        // 金额
        map.put("amount",records.getTopUpAmount());
        // 用户手机号
        map.put("mobile","09111111111");
        // 用户名称
        map.put("userName","Lucio,Drew,Bongalos");
        // 用户地址
        map.put("address","E Flores St, Pasay, Metro Manila");
        // 备注
        map.put("remark","recharge");
        // 用户邮箱
        map.put("email","gtpay@gmail.com");
        //
        map.put("productType","GCASH_ONLINE");
        //
        map.put("notificationURL",RECHARGE_META_CALLBACK_URL);
        // 生成签名
        String sign = RequestSignUtil.getSign(map, PRIVATE_KEY);
        map.put("sign",sign);
        JSONObject jsonParams = JSONObject.parseObject(JSON.toJSONString(map));
        response = RequestSignUtil.doPost(META_Pay_URL+"payment/collect/collect", jsonParams);
        return response;
    }

    /**
     *
     * @param records
     * @return
     */
    public static String sendExchangeMetaPay(ExchangeReview exchangeReview){
        String response="";
        HashMap<String, Object> map = new HashMap<>();
        // 商户号
        map.put("appId",META_APPID);
        // 代付渠道
        map.put("pickupCenter",7);
        // 订单号
        map.put("referenceNo", exchangeReview.getOrderNumber());
        // 代付金额
        map.put("collectedAmount",exchangeReview.getMoney());
        // 账户
        map.put("accountNo",exchangeReview.getBankNumber());
        // 取款人名
        map.put("userName",exchangeReview.getCardholder());
        // 生日
        map.put("birthDate","2019-08-30");
        // 电话号
        map.put("mobileNumber",exchangeReview.getPhone());
        // 证件类型
        map.put("certificateType","SSS");
        // 证件号码，没有随机10位
        map.put("certificateNo",getRandomNickname(10));
        // 居住地址
        map.put("address","190 Poblacion Street");
        // 城市
        map.put("city","HOUSTON");
        // 省份
        map.put("province","TEXAS");
        // 回调url
        map.put("notificationURL",EXCHANGE_RARP_CALLBACK_URL);
        // 生成签名
        String sign = RequestSignUtil.getSign(map, PRIVATE_KEY);
        map.put("sign",sign);
        JSONObject jsonParams = JSONObject.parseObject(JSON.toJSONString(map));
        // 发起请求
        response = RequestSignUtil.doPost("http://t.metapaytest.com/openapi/payment/remit/payout", jsonParams);
        return response;
    }




    // 发送邮件请求1001  金币类型:GoldType
    public static void sendEmail(Map<String,Object> map){
        String str = "gold:" +map.get("gold").toString()+
                ",action:1001"+
                ",title:" +map.get("title").toString()+
                ",toUserid:" +map.get("toUserid").toString()+
                ",content:"+map.get("content").toString()+
                ",FromUserID:"+map.get("senderId").toString()+
                ",GoldType:"+map.get("goldType").toString();
        HttpClientUtils.sendPostJson(GAME_URL, str);
    }

    /** 1002 充值兑换成功后需要调用
     * type:1 usdt充值，0:普通充值兑换
     * @param map
     */
    public static void sendGame1002(Map<String,Object> map){
        String str = "gold:" +map.get("gold")+
                ",action:1002"+
                ",Type:" +map.get("Type")+
                ",Userid:" +map.get("Userid")+
                ",gameCoin:"+map.get("gameCoin");
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
            val.append(String.valueOf(random.nextInt(10)));
        }
        return val.toString();
    }
}
