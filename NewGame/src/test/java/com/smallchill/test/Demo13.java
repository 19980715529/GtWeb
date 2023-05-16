package com.smallchill.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.utils.HttpClientUtils;
import com.smallchill.system.treasure.utils.SendHttp;
import com.smallchill.system.treasure.utils.Utils;
import com.smallchill.test.base.BaseTest;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.smallchill.core.constant.ConstKey.OMOM_APPID;
import static com.smallchill.core.constant.ConstKey.OMOM_KEY;

public class Demo13 extends BaseTest {
    @Test
    public void Test1(){
        // 代收
        String url = "https://pay.omompay.com/pay";
        // 代付 https://pay.omompay.com/payment
        // 密钥 jckwhe1z1gdt0305yafwvoh0576u01hr
        // 商户号 230431183
        Date date = new Date();
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        Map<String, Object> map = new HashMap<>();
        // 商户号
        map.put("pay_memberid",OMOM_APPID);
        // 商户订单号
        map.put("pay_orderid", Utils.getOrderNum(12345));
        // 订单号
        map.put("pay_amount",100);
        // 提交时间
        map.put("pay_applydate",format);
        //
        map.put("pay_bankcode",804);
        // 回调地址
        map.put("pay_notifyurl","https://www.baidu.com");
        //生产签名  4CA7EF8A88BDFCF0BFF19DF5419037BC
        String sign = Utils.getSign(map, OMOM_KEY);
        System.out.println(sign);
        // 签名
        map.put("pay_md5sign",sign.toUpperCase());
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(map));
        System.out.println(jsonObject.toJSONString());
        String s = HttpClientUtils.sendPostJson(url, jsonObject.toString());
        System.out.println(s);

    }

    @Test
    public void test(){
        // 代收
//        String url = "https://pay.omompay.com/pay";
        // 代付 https://pay.omompay.com/payment
        String url = "https://pay.omompay.com/payment";
        // 密钥 jckwhe1z1gdt0305yafwvoh0576u01hr
        // 商户号 230431183
        Map<String, Object> map = new HashMap<>();
        // 商户号
        map.put("mchid",OMOM_APPID);
        // 商户订单号
        map.put("out_trade_no", Utils.getOrderNum(12345));
        // 币种
        map.put("currency","PHP");
        // 代付金额
        map.put("money",100);
        //  银行编码
        map.put("bankname","1234567889");
        // 收款人姓名
        map.put("accountname","lizi");
        // 收款账号
        map.put("cardnumber","54544545");
        // 回调地址
        map.put("notifyurl","https://www.baidu.com");
        //生产签名
        String sign = Utils.getSign(map, OMOM_KEY);
        // 签名
        map.put("pay_md5sign",sign.toUpperCase());
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(map));
        String s = HttpClientUtils.sendPostJson(url, jsonObject.toJSONString());
        System.out.println(s);
        System.out.println(SendHttp.getRandomNickname(10));
    }

    /**
     * "out_trade_no":"20230425155025676316421761","money":150.00,"accountname":"yue,xiao","cardnumber":"9123456541"}
     */
    @Test
    public void test3(){
        ExchangeReview exchangeReview = new ExchangeReview();
        exchangeReview.setOrderNumber(Utils.getOrderNum(12345));
        exchangeReview.setMoney(new BigDecimal("100"));
        exchangeReview.setCardholder("yue,xiao");
        exchangeReview.setBankNumber("954621335666");
//        String s = SendHttp.sendExchangeOmom(exchangeReview);
//        System.out.println(s);
    }
}
