package com.smallchill.test;

import com.alibaba.fastjson.JSON;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.utils.SendHttp;
import com.smallchill.system.treasure.utils.Utils;
import com.smallchill.test.base.BaseTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.smallchill.core.constant.ConstKey.LUCKYPAY_APPID;
import static com.smallchill.core.constant.ConstKey.PUBLIC_LUCKYPAY_KEY;

public class Demo15 extends BaseTest {
    @Test
    public void test(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        Map<String, Object> params = new HashMap();
        // 商户号
        params.put("merNo", LUCKYPAY_APPID);
        // 订单号
        params.put("orderNo", sdf.format(new Date()));
        // 通道
        params.put("rechargeMethod", "1001");
        // 姓名
        params.put("name", "zas");
        // 邮箱
        params.put("email", "test@gmail.com");
        // 电话
        params.put("phone", "13122333688");
        // 订单金额
        params.put("amount", "100");
        // 异步回调地址
        params.put("notifyUrl", "https://google.com");
        // 成功跳转地址
        params.put("backUrl", "https://google.com");
        params.put("term", "");
        params.put("errorBackUrl", "");
        // 获取签名
        String sign = Utils.getSign(params,PUBLIC_LUCKYPAY_KEY);
        params.put("sign",sign);
        params.put("strategy","paymaya");
        String res = Utils.post("https://api.lumypay.com/api/payin", params);
        System.out.println(res);
    }
    @Test
    public void test1(){
        ExchangeReview exchangeReview = new ExchangeReview();
        exchangeReview.setCardholder("tom");
        exchangeReview.setOrderNumber(Utils.getOrderNum(12345));
        exchangeReview.setMoney(new BigDecimal("100.69"));
        exchangeReview.setBankNumber("9111111111");
//        String s = SendHttp.sendExchangeLuckyPay(exchangeReview);
//        System.out.println(s);
    }
}
