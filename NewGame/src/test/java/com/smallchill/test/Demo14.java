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

public class Demo14 extends BaseTest {

    @Test
    public void test(){
        String url = "https://top.adkjk.in/rpay-api/order/submit";
        Date date = new Date();
        HashMap<String, Object> map = new HashMap<>();
        // 商户id
        map.put("merchantId",996);
        // 订单号
        map.put("merchantOrderId", Utils.getOrderNum(12345));
        // 金额。带两位小数
        map.put("amount",100.00);
        // 时间戳
        map.put("timestamp",date.getTime());
        // 支付类型
        map.put("payType",10001);
        // 异步回调地址
        map.put("notifyUrl","http://www.baidu.com");
        // 用户名字 尽量传
        // map.put("firstName","yue xia");
        // map.put("lastName",106);
        // map.put("mobile",106);
        // map.put("email",106);
        // 备注
        map.put("remark","GtGame");
        // 生成签名  加密格式 merchantId=999&merchantOrderId=1000&amount=100.00&abc#123!
        StringBuilder str = new StringBuilder();
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(map));
        str.append("merchantId=").append(jsonObject.getString("merchantId")).append("&");
        str.append("merchantOrderId=").append(jsonObject.getString("merchantOrderId")).append("&");
        str.append("amount=").append(jsonObject.getString("amount")).append("&");
        str.append("abc#123!");
        String sign = Utils.MD5(str.toString());
        jsonObject.put("sign",sign);
        String s = HttpClientUtils.sendPostJson(url, jsonObject.toString());
        System.out.println(s);
    }

    /**
     * 代付
     */
    @Test
    public void test2(){
        String url = "https://top.adkjk.in/rpay-api/payout/submit";
        Map<String, Object> map = new HashMap<>();
        // 商户id
        map.put("merchantId",996);
        // 订单号
        map.put("merchantOrderId",Utils.getOrderNum(12345));
        map.put("amount",100);
        // 时间戳
        map.put("timestamp",new Date().getTime());
        // 回调通知地址
        map.put("notifyUrl","https://www.baidu.com");
        // 收款信息
        Map<String, Object> fundInfo = new HashMap<>();
        //  账户类型
        fundInfo.put("accountType","ph");
        // 用户信息
        Map<String, Object> contact = new HashMap<>();
        contact.put("name","Tom");
        fundInfo.put("contact",contact);
        map.put("fundAccount",fundInfo);
        Map<String, Object> ph = new HashMap<>();
        // 账号类型 2为 gcash
        ph.put("accountType",2);
        // 收款账号
        ph.put("accountNumber","12245415454");
        map.put("ph",ph);
        map.put("merchantOrderId",Utils.getOrderNum(12345));
        // 签名  格式  'merchantId=999&merchantOrderId=1000&amount=100.00&abc#123!'
        StringBuilder str = new StringBuilder();
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(map));
        str.append("merchantId=").append(jsonObject.getString("merchantId")).append("&");
        str.append("merchantOrderId=").append(jsonObject.getString("merchantOrderId")).append("&");
        str.append("amount=").append(jsonObject.getString("amount")).append("&");
        str.append("abc#123!");
        String sign = Utils.MD5(str.toString());
        jsonObject.put("sign",sign);
        System.out.println(jsonObject);
        String s = HttpClientUtils.sendPostJson(url, jsonObject.toString());
        System.out.println(s);
    }

    @Test
    public void test3(){
        ExchangeReview exchangeReview = new ExchangeReview();
        exchangeReview.setMoney(new BigDecimal("100"));
        exchangeReview.setBankNumber("1246646555");
        exchangeReview.setCardholder("Tom");
        exchangeReview.setOrderNumber(Utils.getOrderNum(12345));
        String s = SendHttp.sendExchangeAIPay(exchangeReview);
        System.out.println(s);
    }

    @Test
    public void test4(){
        String url = "https://pyabxum.weglobalex.com/pay/web";
        Map<String, Object> map = new HashMap<>();
        map.put("version","1.0");
        // 商户号
        map.put("mch_id","902902001");
        // \
        map.put("notify_url","https://pyabxum.wegl");
        // 订单号
        map.put("mch_order_no",Utils.getOrderNum(12345));
        // 支付类型
        map.put("pay_type","1720");
        // 支付金额
        map.put("trade_amount","100");
        // 订单时间
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        map.put("order_date",format);
        // 银行代码
        map.put("bank_code","test");
        // 银行代码
        map.put("goods_name","GTGame");
        // 生成签名
        String sign = Utils.getSign(map, "c503bd1f6b6a408aa94a2df8189e8ef0");
        map.put("sign",sign);
        // 不参与签名
        map.put("sign_type","MD5");
        String post = Utils.post(url, map);
        System.out.println(post);
    }
    @Test
    public void test5(){
        String url = "https://pyabxum.weglobalex.com/pay/transfer";
        Map<String, Object> map = new HashMap<>();
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        // 获取当前时间
        map.put("apply_date",format);
        // 收款银行代码
        map.put("bank_code","GCASH");
        // 商户订单
        map.put("mch_id","902902001");
        // 商家转账订单号
        map.put("mch_transferId",Utils.getOrderNum(12345));
        // 收款账号
        map.put("receive_account","123456789");
        // 收款银行户名
        map.put("receive_name","test");
        // 回调地址
        map.put("back_url","https://pyabxum");
        // 收款金额
        map.put("transfer_amount",100);
        // 生成签名
        String sign = Utils.getSign(map, "KSIZ9QAGUTP79B1V5BBNNJ9LJP78GLLJ");
        map.put("sign",sign);
        //
        map.put("sign_type","MD5");
        String post = Utils.post(url, map);
        System.out.println(post);

    }
}
