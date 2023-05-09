package com.smallchill.system.treasure.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
        return HttpClientUtils.sendPostJson(SAFE_URL, JSONObject.toJSONString(map));
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
        map.put("acc_code", channel.get("code")); // PAYMAYA  "PH_GCASH"
        map.put("acc_name", exchangeReview.getCardholder());
        map.put("acc_no", exchangeReview.getBankNumber());
        map.put("returnurl", EXCHANGE_SAFE_CALLBACK_URL);
        // 签名
        String sign  = HttpClientUtils.getSign(map, SECRET_SAFE_KEY);
        map.put("sign", sign);
        return HttpClientUtils.sendPostJson(SAFE_URL, JSONObject.toJSONString(map));
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
        response = RequestSignUtil.doPost(RECHARGE_META_URL, jsonParams);
        return response;
    }

    /**
     * MetaPay兑换
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
        map.put("notificationURL",EXCHANGE_META_PAY_CALLBACK_URL);
        // 生成签名
        String sign = RequestSignUtil.getSign(map, PRIVATE_KEY);
        map.put("sign",sign);
        JSONObject jsonParams = JSONObject.parseObject(JSON.toJSONString(map));
        // 发起请求
        response = RequestSignUtil.doPost(EXCHANGE_META_URL, jsonParams);
        return response;
    }

    /**
     * OMOM 充值
     * @param map
     */
    public static String sendRechargeOmom(RechargeRecords rechargeRecords){
        String response="";
        Date date = new Date();
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        Map<String, Object> map = new HashMap<>();
        // 商户号
        map.put("pay_memberid",OMOM_APPID);
        // 商户订单号
        map.put("pay_orderid", rechargeRecords.getOrderNumber());
        // 订单号
        map.put("pay_amount",rechargeRecords.getTopUpAmount());
        // 提交时间
        map.put("pay_applydate",format);
        //
        map.put("pay_bankcode",821);
        // 回调地址
        map.put("pay_notifyurl",RECHARGE_OMOM_CALLBACK_URL);
        //生产签名
        String sign = Utils.getSign(map, OMOM_KEY);
        // 签名
        map.put("pay_md5sign",sign.toUpperCase());
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(map));
        LOGGER.error(jsonObject.toJSONString());
        response = HttpClientUtils.sendPostJson(RECHARGE_OMOM_URL, jsonObject.toJSONString());
        return response;
    }

    /**
     * 兑换 OMOM
     * @return
     */
    public static String sendExchangeOmom(ExchangeReview exchangeReview){
        String response="";
        Map<String, Object> map = new HashMap<>();
        // 商户号
        map.put("mchid",OMOM_APPID);
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
        String sign = Utils.getSign(map, OMOM_KEY);
        // 签名
        map.put("pay_md5sign",sign.toUpperCase());
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(map));
        LOGGER.error(jsonObject.toJSONString());
        response = HttpClientUtils.sendPostJson(EXCHANGE_OMOM_URL, jsonObject.toJSONString());
        return response;
    }

    /**
     * 充值回调
     * @param map
     */
    public static String sendRechargeAIPay(RechargeRecords records){
        String response="";
        HashMap<String, Object> map = new HashMap<>();
        // 商户id
        map.put("merchantId",AIPAY_APPID);
        // 订单号
        map.put("merchantOrderId", records.getOrderNumber());
        // 金额。带两位小数
        map.put("amount",records.getTopUpAmount());
        // 时间戳
        map.put("timestamp",new Date().getTime());
        // 支付类型
        map.put("payType",10001);
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
        String sign = getSign(jsonObject);
        jsonObject.put("sign",sign);
        response = HttpClientUtils.sendPostJson(RECHARGE_AIPAY_URL, jsonObject.toString());
        return response;
    }

    /**
     * 兑换
     * @return
     */
    public static String sendExchangeAIPay(ExchangeReview exchangeReview){
        String response;
        Map<String, Object> map = new HashMap<>();
        // 商户id
        map.put("merchantId",AIPAY_APPID);
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
        String sign = getSign(jsonObject);
        jsonObject.put("sign",sign);
        response = HttpClientUtils.sendPostJson(EXCHANGE_AIPAY_URL, jsonObject.toString());
        return response;
    }

    public static String sendRechargeWePay(RechargeRecords rechargeRecords){
        String response;
        Map<String, Object> map = new HashMap<>();
        map.put("version","1.0");
        // 商户号
        map.put("mch_id",WEPAY_APPID);
        // \
        map.put("notify_url",RECHARGE_WEPAY_CALLBACK_URL);
        // 订单号
        map.put("mch_order_no",rechargeRecords.getOrderNumber());
        // 支付类型
        map.put("pay_type","1721");
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

    public static String sendExchangeWePay(ExchangeReview exchangeReview){
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
        response= Utils.post(EXCHANGE_WEPAY_URL, map);
        return response;
    }
    public static String sendRechargeGalaxy(RechargeRecords rechargeRecords,String appid,String key,String url){
        String response;
        HashMap<String, Object> map = new HashMap<>();
        // 商户号
        map.put("merchant", appid);
        // 支付类型
        map.put("payment_type", 3);
        // 提交金额
        map.put("amount", rechargeRecords.getTopUpAmount());
        // 订单号
        map.put("order_id", rechargeRecords.getOrderNumber());
        // 银行代码
        map.put("bank_code", "gcash");
        // 回调地址
        map.put("callback_url", RECHARGE_GALAXY_CALLBACK_URL);
        // 跳转地址
        map.put("return_url", "https://www.baidu.com");
        //
        String sign = Utils.getSign(map, key);
        map.put("sign", sign);
        LOGGER.error(map);
        response = HttpClientUtils.sendPostJson(url, JSON.toJSONString(map));
        return response;
    }

    public static String sendExchangeGalaxy(ExchangeReview exchangeReview,String appid,String key,String url){
        String response;
        HashMap<String, Object> map = new HashMap<>();
        // 商户号
        map.put("merchant", appid);
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
        String sign = Utils.getSign(map, key);
        map.put("sign", sign);
        LOGGER.error(map);
        response = HttpClientUtils.sendPostJson(url, JSON.toJSONString(map));
        return response;
    }

    public static String sendRechargeLetsPay(RechargeRecords rechargeRecords){
        String response;
        HashMap<String, Object> map = new HashMap<>();
        // 商户号
        map.put("mchId", LETSPAY_APPID);
        // 订单号
        map.put("orderNo", rechargeRecords.getOrderNumber());
        // 金额
        map.put("amount", rechargeRecords.getTopUpAmount().toString());
        // 产品号
        map.put("product", "philiwallet");
        // 银河代号
        map.put("bankcode", "gcash");
        // 物品说明
        map.put("goods", "email:520155@gmail.com/name:tom/phone:"+rechargeRecords.getPhone());
        // 异步通知地址
        map.put("notifyUrl", RECHARGE_LETSPAY_CALLBACK_URL);
        // 跳转地址
        map.put("returnUrl", "https://www.baidu.com");
        // 生成签名
        String sign = Utils.getSign(map, LETSPAY_KEY);
        // 生成签名全部大写
        map.put("sign", sign.toUpperCase());
        LOGGER.error(JSON.toJSONString(map));
        response = Utils.post(RECHARGE_LETSPAY_URL, map);
        return response;
    }

    public static String sendExchangeLetsPay(ExchangeReview exchangeReview){
        String response;
        HashMap<String, Object> map = new HashMap<>();
        // 转账类型
        map.put("type","api");
        // 商户号
        map.put("mchId",LETSPAY_APPID);
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
        map.put("remarkInfo","email:520155@gmail.com/phone:"+exchangeReview.getPhone()+"/mode:wallet");
        // 生成签名全部大写
        String sign = Utils.getSign(map, LETSPAY_KEY);
        map.put("sign",sign.toUpperCase());
        LOGGER.error(map);
        response = Utils.post(EXCHANGE_LETSPAY_URL, map);
        return response;
    }



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
