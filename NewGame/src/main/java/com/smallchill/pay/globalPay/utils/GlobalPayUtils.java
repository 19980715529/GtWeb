package com.smallchill.pay.globalPay.utils;

import com.alibaba.fastjson.JSONObject;
import com.smallchill.pay.globalPay.model.GlobalPay;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.Base64Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import static com.smallchill.core.constant.ConstUrl.*;

public class GlobalPayUtils {
    private static final Logger LOGGER = LogManager.getLogger(GlobalPayUtils.class);
    /**
     * 充值
     */
    public static String sendRecharge(RechargeRecords rechargeRecords, Map<String,Object> channel, GlobalPay globalPay){
        // 生成请求条件
        String response;
        JSONObject params = new JSONObject();
        // 商户号
        params.put("alliesNo",globalPay.getAlliesNo());
        // 订单号
        params.put("tradeNo",rechargeRecords.getOrderNumber());
        // 充值金额
        params.put("totalAmount",rechargeRecords.getTopUpAmount());
        params.put("tradeType",globalPay.getTradeType());
        // 商品名字
        params.put("productName",globalPay.getProductName());
        // 支付成功跳转地址
        params.put("frontNotifyUrl","https://www.baidu.com");
        //
        params.put("noticeUrl",RECHARGE_GLOBALPAY_CALLBACK_URL);
        // 产品id
        params.put("productId",globalPay.getProductId());
        // 生成签名
        String sign = Utils.getSign(params, globalPay.getKey());
        params.put("sign",sign.toUpperCase());
        response = sendPost(globalPay.getPayUrl(), params);
        return response;
    }


    public static String sendExchange(ExchangeReview exchangeReview, Map<String,Object> channel, GlobalPay globalPay){
        String response="";
        JSONObject params = new JSONObject();
        // 商户号
        params.put("alliesNo",globalPay.getAlliesNo());
        // 订单号
        params.put("tradeNo",exchangeReview.getOrderNumber());
        // 充值金额
        params.put("orderAmount",exchangeReview.getMoney());
        //
        params.put("tradeType",globalPay.getTradeType());
        //
        params.put("noticeUrl",EXCHANGE_GLOBALPAY_CALLBACK_URL);
        // 产品id
        params.put("productId",globalPay.getProductId());
        // 生成attach参数
        JSONObject map=new JSONObject();
        // 姓名
        map.put("bankAccount",exchangeReview.getCardholder());
        // 银行卡号
        map.put("bankCard",exchangeReview.getBankNumber());
        // 银行编号
        map.put("bankMark",globalPay.getBankMark());
        // 银行名字
        map.put("bankName",globalPay.getBankName());
        // 参数进行Base64Utils加密
        String data = map.toJSONString();
        byte[] encode = Base64Utils.encode(data.getBytes());
        String attach = new String(encode);
        params.put("attach",attach);
        // 生成签名
        String sign = Utils.getSign(params, globalPay.getKey());
        params.put("sign",sign.toUpperCase());
        response = sendPost(globalPay.getPayOutUrl(), params);
        return response;
    }


    /**
     * 发起请求
     * @param url
     * @param params
     * @return
     */
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
//            conn.setConnectTimeout(5000);
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


    public static boolean GlobalPayExchange(ExchangeReview exchangeReview, Map channel,GlobalPay globalPay) {
        String response = GlobalPayUtils.sendExchange(exchangeReview, channel, globalPay);
        if ("".equals(response)){
            return true;
        }
        JSONObject respJson = JSONObject.parseObject(response);
        String statusStr = respJson.getString("code");
        // 成功
        if ("10000".equals(statusStr)) {
            // 请求成功 ,获取平台订单号
            exchangeReview.setStatus(1);
            String sysOrderNo = respJson.getString("outTradeNo");
            exchangeReview.setPfOrderNum(sysOrderNo);
        } else {
            // 请求失败, 存储失败原因
            exchangeReview.setMsg(respJson.getString("msg"));
            // 将状态设置为失败
            exchangeReview.setStatus(6);
        }
        return false;
    }

}
