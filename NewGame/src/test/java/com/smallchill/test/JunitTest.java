package com.smallchill.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.smallchill.pay.payplus.model.PayPlus;
import com.smallchill.system.treasure.utils.HttpClientUtils;
import com.smallchill.system.treasure.utils.Utils;
import org.junit.Test;

import com.smallchill.test.base.BaseTest;
import org.springframework.util.Base64Utils;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

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

	}
	@Resource
	private PayPlus payPlus;
	@Test
	public void test6(){
//
//		Map<String, String> map = JSON.parseObject(JSON.toJSONString(superPayPlus), new TypeReference<Map<String, String>>() {
//		});
//		System.out.println(map);
	}
	@Test
	public void test7(){
		JSONObject params = new JSONObject();
		// 商户号
		params.put("alliesNo","IG20230523001");
		// 订单号
		params.put("tradeNo","154554445445545");
		// 充值金额
		params.put("totalAmount",new BigDecimal("100.00"));
		params.put("tradeType",31);
		// 商品名字
		params.put("productName","GT_Game");
		// 支付成功跳转地址
		params.put("frontNotifyUrl","https://www.baidu.com");
		//
		params.put("noticeUrl","https://www.baidu.com");
		// 产品id
		params.put("productId",0);
		// 生成签名
		String sign = Utils.getSign(params, "DLwoXH7vCwEFbaF3AJAYWJ8J5bdkef");
		params.put("sign",sign.toUpperCase());
		String res = sendPost("https://www.globalpay.best/alliesPay/bussiness/order", params);
		System.out.println(res);
	}

	@Test
	public void test8(){
		JSONObject params = new JSONObject();
		// 商户号
		params.put("alliesNo","IG20230523001");
		// 订单号
		params.put("tradeNo","154554445445545");
		// 充值金额
		params.put("orderAmount",new BigDecimal("100.00"));
		//
		params.put("tradeType",31);
		//
		params.put("noticeUrl","https://www.baidu.com");
		// 产品id
		params.put("productId",0);
		// 生成attach参数
		JSONObject map=new JSONObject();
		// 姓名
		map.put("bankAccount","tom");
		// 银行卡号
		map.put("bankCard","45566666");
		// 银行编号
		map.put("bankMark","GCASH");
		//
		map.put("bankName","gcash");
		// 参数进行Base64Utils加密
		String data = map.toJSONString();
		byte[] encode = Base64Utils.encode(data.getBytes());
		String attach = new String(encode);
		params.put("attach",attach);
		// 生成签名
		String sign = Utils.getSign(params, "DLwoXH7vCwEFbaF3AJAYWJ8J5bdkef");
		params.put("sign",sign.toUpperCase());
		String res = sendPost("https://www.globalpay.best/peerpay/bussiness/order", params);
		System.out.println(res);
	}



	public static String sendPost(String url,Map<String,Object> params){
		OutputStreamWriter out =null;
		BufferedReader reader = null;
		String response = "";
		//创建连接
		try {
			StringBuilder postData = new StringBuilder();
			for (Map.Entry<String,Object> param: params.entrySet()){
				if (postData.length() != 0){
					postData.append("&");
				}
				postData.append(URLEncoder.encode(param.getKey(),"UTF-8"));
				postData.append("=");
				postData.append(URLEncoder.encode(String.valueOf(param.getValue()),"UTF-8"));
			}
			URL httpUrl = null; //HTTP URL类 用这个类来创建连接
			//创建URL
			httpUrl = new URL(url);
			//建立连接
			HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("User-Agent", "Mozilla/4.76");
			conn.setUseCaches(false);//设置不要缓存
			conn.setInstanceFollowRedirects(true);
			conn.setDoOutput(true);
			conn.setDoInput(true);
//			conn.setConnectTimeout(5000);
			conn.connect();
			//POST请求
			out = new OutputStreamWriter(
					conn.getOutputStream());
			out.write(postData.toString());
			out.flush();
			//读取响应
			reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String lines;
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				response+=lines;
			}
			reader.close();
			// 断开连接
			conn.disconnect();
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！"+e);
			e.printStackTrace();
		}
		//使用finally块来关闭输出流、输入流
		finally{
			try{
				if(out!=null){
					out.close();
				}
				if(reader!=null){
					reader.close();
				}
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
		}

		return response;
	}
}
