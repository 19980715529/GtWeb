package com.smallchill.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.system.treasure.utils.HttpClientUtils;
import com.smallchill.system.treasure.utils.RequestSignUtil;
import com.smallchill.system.treasure.utils.Utils;

import java.util.HashMap;

public class Demo12 {
    public static void main(String[] args) {
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
        HashMap<String, Object> map = new HashMap<>();
        map.put("appId","1880087979143360");
        // 代收渠道
        map.put("channel",3);
        // 平台订单号
        map.put("referenceNo", Utils.getOrderNum(12345));
        // 金额
        map.put("amount","50");
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
        map.put("productType","CASHIER_DESK");
        //
        map.put("notificationURL","http://x/payment/collect/collect");
        // 生成签名
        String sign = RequestSignUtil.getSign(map, privateKey);
        map.put("sign",sign);
        System.out.println(map);
        JSONObject jsonParams = JSONObject.parseObject(JSON.toJSONString(map));
        String jsonObject = RequestSignUtil.doPost("http://t.metapaytest.com/openapi/payment/collect/collect", jsonParams);
//        String jsonObject = HttpClientUtils.sendPostJson("http://t.metapaytest.com/openapi/", jsonParams.toJSONString());
        System.out.println(jsonObject);
    }
}
