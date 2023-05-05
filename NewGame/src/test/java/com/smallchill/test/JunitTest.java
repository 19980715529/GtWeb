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

import java.util.HashMap;

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
		// 提交金额
		map.put("total_amount", 3);
		// 回调地址
		map.put("callback_url", 100);
		// 订单号
		map.put("order_id", Utils.getOrderNum(12345));
		// 银行代码
		map.put("bank", "gcash");
		// 收款人姓名
		map.put("bank_card_name", "https://www.baidu.com");
		// 收款人账号
		map.put("bank_card_account", "https://www.baidu.com");
		// IFSC
		map.put("bank_card_remark", "https://www.baidu.com");
		//
		String sign = Utils.getSign(map, "f0bff0f0c0e3b25257677a79fc2e9fb2");
		map.put("sign", sign);
	}
}
