package com.smallchill.test;

import com.smallchill.common.task.GlobalDelayQueue;
import com.smallchill.core.utils.MD5;
import com.smallchill.system.treasure.utils.SendHttp;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.NoSuchAlgorithmException;

/**
 * @Description TODO
 * @classNameDemo11
 * @Date 2023/3/24 17:48
 * @Version 1.0
 **/
public class Demo11 {
    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//        BigDecimal amount = new BigDecimal("100");
//        BigDecimal taxRate = new BigDecimal("0.015");
//        BigDecimal tax = new BigDecimal("1").subtract(taxRate);
//        BigDecimal money1 = amount.multiply(new BigDecimal("1").subtract(taxRate));
//        BigDecimal money = amount.multiply(tax);
//        System.out.println(money.setScale(2,RoundingMode.FLOOR));
//        System.out.println(money1.setScale(2, RoundingMode.FLOOR));
        int size = GlobalDelayQueue.orderQueue.size();
        System.out.println(size);
    }
}
