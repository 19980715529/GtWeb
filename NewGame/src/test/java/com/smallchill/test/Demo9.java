package com.smallchill.test;

import com.alibaba.druid.sql.visitor.SQLASTOutputVisitorUtils;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.HttpClientUtils;
import com.smallchill.system.treasure.utils.SendHttp;
import com.smallchill.system.treasure.utils.Utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


/**
 * @Description TODO
 * @classNameDemo9
 * @Date 2023/2/22 17:28
 * @Version 1.0
 **/
public class Demo9 {
    public static void main(String[] args) {
        extracted();
    }

    private static void extracted1() {
        RechargeRecords rechargeRecords = new RechargeRecords();
        rechargeRecords.setTopUpAmount(new BigDecimal(50));
        String orderNum = Utils.getOrderNum(45555);
        rechargeRecords.setOrderNumber(orderNum);
        HashMap<String, Object> map = new HashMap<>();
        map.put("code",21001); //range:21001:50-50000
        ExchangeReview exchangeReview = new ExchangeReview();
        exchangeReview.setAmount(new BigDecimal(555.00));
        String res = SendHttp.sendRechargeSafe(rechargeRecords, map);
        System.out.println(res);
    }

    private static void extracted() {
//        RechargeRecords rechargeRecords = new RechargeRecords();
//        rechargeRecords.setTopUpAmount(new BigDecimal(23521529));
        String orderNum = Utils.getOrderNum(45555);
//        rechargeRecords.setOrderNumber("A20230327175936GzFYUN");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("code","test");
        ExchangeReview exchangeReview = new ExchangeReview();
        exchangeReview.setAmount(new BigDecimal("100"));
        exchangeReview.setBankNumber("yuexia");
        exchangeReview.setOrderNumber(orderNum);
        exchangeReview.setMoney(new BigDecimal("100"));
//        String res = SendHttp.sendExchangeSafe(exchangeReview, map);
        String res = SendHttp.sendExchangeRarp(exchangeReview);
        System.out.println(res);
    }
}
