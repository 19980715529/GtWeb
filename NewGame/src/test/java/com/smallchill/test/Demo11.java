package com.smallchill.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.common.task.GlobalDelayQueue;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.utils.MD5;
import com.smallchill.system.treasure.utils.RequestSignUtil;
import com.smallchill.system.treasure.utils.SendHttp;
import com.smallchill.system.treasure.utils.Utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Random;

/**
 * @Description TODO
 * @classNameDemo11
 * @Date 2023/3/24 17:48
 * @Version 1.0
 **/
public class Demo11 {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 公钥
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCcUDnLiX9eU6RlgiUBwQn+rhaf/53h" +
                "6zawEXoBHv9pPQxRdqsr1q0RwRvqJePecd8r6jmbKN/+3WVml8Mv3VWvLtLXv85X768OPAXSOd/WUugc" +
                "6GG2s5nvnMxJ6ZOKfJP8Oqs1T/KyAZGq860374nDy8rrk/GXa6BDvW+j94eyKQIDAQAB";
        // 私钥
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJxQOcuJf15TpGWCJQHBCf6u" +
                "Fp//neHrNrARegEe/2k9DFF2qyvWrRHBG+ol495x3yvqOZso3/7dZWaXwy/dVa8u0te/zlfvrw48BdI539ZS6B" +
                "zoYbazme+czEnpk4p8k/w6qzVP8rIBkarzrTfvicPLyuuT8ZdroEO9b6P3h7IpAgMBAAECgYB18HIrCEt5uFJJ5L" +
                "BXandIGcIoZo2RcA+LOSR8xA6iMNWigY8VPJzI0JNnT79mjzR7xfZBA84+Vn2zsr4q1y1WGm/jGvZCBV+2nVM19" +
                "hw0sTdPDiVdKV01wTIKZ9Oc/JvSMeC3RPu8wMFumCdPctAcc/4tQN0P4OczfTdE+0stIQJBAMr1z5AGlFYj6dSgOR" +
                "tV8P6j6TzytJpayUhvskGB4aAgRLEHb2Su9Z4ObN2JZTDhkYbh/ircYRcoVQk0ltX1m48CQQDFKbVBKyPAd31iKzllrqj" +
                "qLRbwdAtCJ5Z+5fRr5mUkjiW4LXrCNFxxcDGnMaicx61ho8upD34oclwbA5UMnNrHAkEAkEibXCoxPeOeEbgtoM9B2eSw" +
                "pNFtruWnfkVZldQ9j9YgvzEn8Y2nsstSfVN1ioBxMlBqnhG5O/tCKJ8oM8a85QJAc6u5hCc8DWHM9LgpL2ed/+0+JQnfx" +
                "QsWtxJQKLF9q0meBYThHWi8x2ysJMv0gS6AoOUIygGl4TLeqJacbk7j5wJAAxUtUwWE5cbMNOMjNS6o+LS1UTydbFPN+rWK" +
                "vcwUipLSEr6ys09fRgn82CKDI5yDpT2QSlBNXEwBJ1thTrKwfg==";
//        HashMap<String, Object> map = new HashMap<>();
//        // 商户号
//        map.put("appId","1880087979143360");
//        // 代付渠道
//        map.put("pickupCenter",7);
//        // 订单号
//        map.put("referenceNo", Utils.getOrderNum(12345));
//        // 代付金额
//        map.put("collectedAmount","200.00");
//        // 账户
//        map.put("accountNo","0999327818");
//        // 取款人名
//        map.put("userName","SANTOS,CRUZ,JUAN");
//        // 生日
//        map.put("birthDate","2019-08-30");
//        // 电话号
//        map.put("mobileNumber","15454545454");
//        // 证件类型
//        map.put("certificateType","SSS");
//        // 证件号码，没有随机10位
//        map.put("certificateNo","1234567890");
//        // 居住地址
//        map.put("address","190 Poblacion Street");
//        // 城市
//        map.put("city","HOUSTON");
//        // 省份
//        map.put("province","TEXAS");
//        // 回调url
//        map.put("notificationURL","http://t.metapaytest.com/openapi/payment/collect/collect1");
//
//        // 生成签名
//        String sign = RequestSignUtil.getSign(map, privateKey);
//        System.out.println("加密后的字符串："+sign);
//        map.put("sign",sign);
//        JSONObject jsonParams = JSONObject.parseObject(JSON.toJSONString(map));
//        // 发起请求
//        String jsonObject = RequestSignUtil.doPost("http://t.metapaytest.com/openapi/payment/remit/payout", jsonParams);
//        System.out.println(jsonObject);
        System.out.println(getRandomNickname(10));
    }
    public static String getRandomNickname(int length) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            val.append(String.valueOf(random.nextInt(10)));
        }
        return val.toString();
    }
}
