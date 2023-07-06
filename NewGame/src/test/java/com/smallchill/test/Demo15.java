package com.smallchill.test;

//import com.github.benmanes.caffeine.cache.Cache;
//import com.github.benmanes.caffeine.cache.Caffeine;
//import com.github.benmanes.caffeine.cache.Scheduler;
import com.smallchill.system.treasure.utils.Utils;
import com.smallchill.test.base.BaseTest;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    public void test1() throws InterruptedException {
//        Cache<Integer, Integer> cache = Caffeine.newBuilder()
//                .expireAfterWrite(1, TimeUnit.SECONDS)
//                //可以指定调度程序来及时删除过期缓存项，而不是等待Caffeine触发定期维护
//                //若不设置scheduler，则缓存会在下一次调用get的时候才会被动删除
//                .scheduler(Scheduler.systemScheduler())
//                .evictionListener((key, val, removalCause) -> {
//                    System.out.println("淘汰缓存：key:"+key+" val:"+val);
//                })
//                .build();
//        cache.put(1, 2);
//        Thread.sleep(3000);
//        System.out.println(cache.getIfPresent(1));//null
    }
}
