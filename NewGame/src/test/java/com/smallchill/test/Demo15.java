package com.smallchill.test;

import com.smallchill.system.treasure.utils.Utils;
import com.smallchill.test.base.BaseTest;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    public void test1() {
        BigDecimal gold=new BigDecimal("9100000");
        BigDecimal divide = gold.divide(new BigDecimal(10000), 0, RoundingMode.FLOOR);
        System.out.println(divide);
    }

    @Test
    public void testReaderFile() throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:banner.txt");
        InputStream is = resource.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String data = null;
        while((data = br.readLine()) != null) {
            System.out.println(data);
        }
        br.close();
        isr.close();
        is.close();
    }
}
