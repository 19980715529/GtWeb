package com.smallchill.test;

import com.alibaba.fastjson.JSON;
import com.smallchill.system.treasure.utils.HttpClientUtils;
import com.smallchill.system.treasure.utils.SendHttp;
import com.smallchill.system.treasure.utils.Utils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.kit.AESKit;
import com.smallchill.test.base.BaseTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

import static com.smallchill.core.constant.ConstKey.LETSPAY_KEY;

public class JunitTest extends BaseTest{


	@Test
	public void test2() {
		String url = "https://pay.xxxx.cash/api/transfer";
		HashMap<String, Object> map = new HashMap<>();
		// 商户号
		map.put("merchant", "88052");
		// 支付类型
		map.put("payment_type", 3);
		// 提交金额
		map.put("amount", 100);
		// 订单号
		map.put("order_id", Utils.getOrderNum(12345));
		// 银行代码
		map.put("bank_code", "gcash");
		// 回调地址
		map.put("callback_url", "https://www.baidu.com");
		// 跳转地址
		map.put("return_url", "https://www.baidu.com");
		//
		String sign = Utils.getSign(map, "f0bff0f0c0e3b25257677a79fc2e9fb2");
		map.put("sign", sign);
		String s = HttpClientUtils.sendPostJson(url, JSON.toJSONString(map));
		System.out.println(s);
	}

	@Test
	public void test3(){
		// merchant
		String url = "https://pay.xxxx.cash/api/daifu";
		HashMap<String, Object> map = new HashMap<>();
		// 商户号
		map.put("merchant", "88052");
		BigDecimal bigDecimal = new BigDecimal("117.607").setScale(2, RoundingMode.FLOOR);
		// 提交金额
		map.put("total_amount", bigDecimal.toString());
		// 回调地址
		map.put("callback_url", "http://52.76.118.232:1234/NewGame/callback/exchange_galaxy_callback");
		// 订单号
		map.put("order_id", "20230505203117922827622051");
		// 银行代码
		map.put("bank", "gcash");
		// 收款人姓名
		map.put("bank_card_name", "9777777777");
		// 收款人账号
		map.put("bank_card_account", "9777777777");
		// IFSC
		map.put("bank_card_remark", "9777777777");
		//
		System.out.println(map);
		String sign = Utils.getSign(map, "f0bff0f0c0e3b25257677a79fc2e9fb2");
		System.out.println(sign);
		map.put("sign", sign);
	}

	@Test
	public void test4(){
		HashMap<String, Object> map = new HashMap<>();
		map.put("mchId", "1683279488703");
		map.put("orderNo", Utils.getOrderNum(12345));
		map.put("amount", "100.60");
		map.put("product", "philiwallet");
		map.put("bankcode", "gcash");
		map.put("goods", "email:520155@gmail.com/name:tom/phone:7894561230");
		map.put("notifyUrl", "https://www.baidu.com");
		map.put("returnUrl", "https://www.baidu.com");
		String sign = Utils.getSign(map, LETSPAY_KEY);
		map.put("sign", sign);
		System.out.println(JSON.toJSONString(map));
		String s = Utils.post("http://api.letspayfast.com/apipay", map);
		System.out.println(s);
	}
	@Test
	public void test5(){
		HashMap<String, Object> map = new HashMap<>();
		map.put("type","api");
		map.put("mchId","1683279488703");
		map.put("mchTransNo","api");
		map.put("notifyUrl","api");
		map.put("accountName","api");
		map.put("accountNo","api");
		map.put("bankCode","api");
		map.put("remarkInfo","api");
	}
}
