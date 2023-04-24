package com.smallchill.test;

import com.alibaba.fastjson.JSONObject;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.utils.HttpClientUtils;
import com.smallchill.system.treasure.utils.SendHttp;
import com.smallchill.system.treasure.utils.Utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.smallchill.core.constant.ConstKey.*;
import static com.smallchill.core.constant.ConstKey.SECRET_SAFE_KEY;
import static com.smallchill.core.constant.ConstUrl.*;
import static com.smallchill.core.constant.ConstUrl.SAFE_URL;

/**
 * @Description TODO
 * @classNameDemo10
 * @Date 2023/2/24 15:03
 * @Version 1.0
 **/
public class Demo10 {
    public static void main(String[] args) {
//        String response="";
//        Date date = new Date();
//        String dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
//        Map<String, Object> map = new HashMap<>();
//        Map<String, Object> extra = new HashMap<>();
//        // 通过请求过后向地三方发起代付请求
//        map.put("mchNo","GTsuperwinner");
//        map.put("time",dateFormat);
//        map.put("type","gcash_dpay.140");
//        map.put("orderNo", "fsdfsladfasdf6s54565");
//        map.put("notifyUrl",EXCHANGE_RARP_CALLBACK_URL);
//        map.put("fee","58.00");
//        map.put("bankCode","gcash");
//        map.put("bankAccount","985623");
//        map.put("ifsc","no");
//        map.put("customerName","985623");
//        extra.put("custPhone", "985623");
//        extra.put("bank_province", "no");
//        extra.put("to_city", "no");
//        map.put("extra", Utils.parseExtInfo(extra));
//        // 获取签名
//        String sign = Utils.getSign(map, "9756478D13DED7D003516E38CABD3A67");
//        map.put("sign",sign);
//        response = Utils.post(EXCHANGE_URL, map);
//        System.out.println(response);

//        Map<String, Object> map = new HashMap<>();
//        map.put("currency", "PHP");
//        map.put("mer_no", ACCOUNT_SAFE_NUM);
//        map.put("order_amount", "150.00");
//        map.put("method", "fund.apply");
//        map.put("order_no", "15455152");
//        map.put("acc_code", "PH_GCASH");
//        map.put("acc_name", "HONGL.QIAN");
//        map.put("acc_no", "9063256511");
//        map.put("returnurl", EXCHANGE_SAFE_CALLBACK_URL);
//        // 签名
//        String sign  = HttpClientUtils.getSign(map, SECRET_SAFE_KEY);
//        map.put("sign", sign);
//        String res = HttpClientUtils.sendPostJson(SAFE_URL, JSONObject.toJSONString(map));
//        System.out.println(res);
//        String ss = "mchNo=test123456&mchOrdernum=A20230310111526O4gICV&ordernum=20230310111526sJfEV7wm&attach=&createTime" +
//                "=2023-03-10+11%3A15%3A26&fee=58.00000000&status=20&time=2023-03-10+11%3A17%3A42&sign=6b2e25e067388ec32991eec2041f5db4";
//        String str = "createTime=2023-03-10 11:15:26&fee=58.00000000&mchNo=test123456&mchOrdernum=A20230310111526O4gICV&ordernum=" +
//                "20230310111526sJfEV7wm&status=20&time=2023-03-10 11:17:42&sign=6b2e25e067388ec32991eec2041f5db4";
//        Boolean aBoolean = HttpClientUtils.Md5RarpVerification(ss);
//        Utils.getSign();
//        System.out.println(aBoolean);
//        Map<String, String> map = new HashMap<>();
//        map.put("name","tom");
//        map.put("age","11");
//        String name = map.remove("names");
//        System.out.println(name);
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();

        map.put("id", "id1");
        map.put("name", "name-1");
        map.put("age", "22");
        list.add(new HashMap<>(map));

        map.put("id", "id1");
        map.put("name", "name-21");
        map.put("age", "22");
        list.add(new HashMap<>(map));

        map.put("id", "id2");
        map.put("name", "name-52");
        map.put("age", "22");
        list.add(new HashMap<>(map));

        map.put("id", "id2");
        map.put("name", "name-52");
        map.put("age", "32");
        list.add(new HashMap<>(map));
        list = list.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(() ->
                                new TreeSet<>(Comparator.comparing((o)-> o.get("id")))), ArrayList::new));
        List<Object> collect = list.stream().map((Map i) -> getMap(i)).collect(Collectors.toList());
        System.out.println(collect);
    }

    private static Map getMap(Map item){
        String name = item.get("name").toString().split("-")[0];
        item.put("name", name);
        return item;
    }
}
