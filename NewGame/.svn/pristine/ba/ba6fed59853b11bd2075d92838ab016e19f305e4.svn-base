package com.smallchill.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.common.HttpCli.HttpClientFactory;
import com.smallchill.common.HttpCli.HttpConstant;
import com.smallchill.system.treasure.utils.HttpClientUtils;
import com.smallchill.system.treasure.utils.Utils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description TODO
 * @classNameDemo8
 * @Date 2023/2/22 11:39
 * @Version 1.0
 **/
public class Demo8 {
    public static void main(String[] args) throws IOException {
        Map<String, Object> map = new HashMap<>();
        String orderNo = Utils.getOrderNum(23553);
        map.put("currency", "PHP");
        map.put("mer_no", "1083053");
        map.put("order_amount", "50.00");
        map.put("method", "trade.create");
        map.put("order_no", orderNo);
        map.put("payemail", "youyanjianghu@gmail.com");
        map.put("payname", "LI LAN LAN");
        map.put("payphone", "85256332649");
        map.put("paytypecode", "21001"); // 支付类型
        map.put("returnurl", "http://api.pnsafepay.com/Test/OrderReceive.aspx");
        //签名
        String sign  = getSign1(map, "b3da14ea21d689b3c72dd7ca0dbd388f");
        map.put("sign", sign);
        String s1 = HttpClientUtils.sendPostJson("http://api.pnsafepay.com/gateway.aspx", JSONObject.toJSONString(map));
        System.out.println(s1);
        /**代付
         *  {
         *   "acc_code": "CUB",
         *   "acc_name": "LILAILAI",
         *   "acc_no": "62289966557898554",
         *   "currency": "INR",
         *   "mer_no": "1001724",
         *   "method": "fund.apply",
         *   "order_amount": "10",
         *   "order_no": "2021071116564620181",
         *   "province": "UTIB0000897",
         *   "returnurl": "http://guest.station.com/result.aspx",
         *   "sign": "4c743a96d95d5e51d17735f3d2f0e0ea"
         * }
         */
        /*Map<String, Object> map = new HashMap<>();
        String orderNo = Utils.getOrderNum(23553);
        map.put("currency", "PHP");
        map.put("mer_no", "1083053");
        map.put("order_amount", "150.00");
        map.put("method", "fund.apply");
        map.put("order_no", orderNo);
        map.put("acc_code", "CUB");
        map.put("acc_name", "LI LAN LAN");
        map.put("acc_no", "85256332649");
//        map.put("province", "UTIB0000897");
        map.put("returnurl", "http://api.pnsafepay.com/Test/OrderReceive.aspx");
        //签名
        String sign  = getSign1(map, "b3da14ea21d689b3c72dd7ca0dbd388f");
        map.put("sign", sign);
        String s1 = HttpClientUtils.sendPostJson("http://api.pnsafepay.com/gateway.aspx", JSONObject.toJSONString(map));
        System.out.println(s1);*/



    }



    // 获取到Sign
    public static String getSign(Map<String, Object> map, String appkey){
        String sign=null;
        if(map.isEmpty()){
            return "";
        }
        Object[] keys = map.keySet().toArray();
        Arrays.sort(keys);
        StringBuilder originStr = new StringBuilder();
        Object value;
        for ( Object key: keys){
            value = map.get(key.toString());
            if (isIgnoredItem(value)) {
                continue;
            }
            originStr.append(key).append("=").append(value).append("&");
        }
        originStr.append("key=").append(appkey);
        System.out.println("MD5加密前字串" + originStr.toString());
        sign = MD5(originStr.toString());
        System.out.println("【sign：】" + sign);


        return sign;
    }

    public static String getSign1(Map<String, Object> map, String appkey){
        String sign=null;
        if(map.isEmpty()){
            return "";
        }
        Object[] keys = map.keySet().toArray();
        Arrays.sort(keys);
        System.out.println(Arrays.toString(keys));
        StringBuilder originStr = new StringBuilder();
        Object value;
        for (int i =0;i<keys.length;i++){
            value = map.get(keys[i].toString());
            if (isIgnoredItem(value)) {
                continue;
            }
            if(i==keys.length-1){
                originStr.append(keys[i]).append("=").append(value);
                continue;
            }
            originStr.append(keys[i]).append("=").append(value).append("&");
        }
        originStr.append(appkey);
        System.out.println("MD5加密前字串" + originStr.toString());
        sign = MD5(originStr.toString());
        System.out.println("【sign：】" + sign);
        return sign;
    }


    public static String MD5(String str) {
        // 加密后的16进制字符串
        String hexStr = "";
        try {
            // 此 MessageDigest 类为应用程序提供信息摘要算法的功能
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            // 转换为MD5码
            byte[] digest = md5.digest(str.getBytes("utf-8"));
            hexStr = byteToHexString(digest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hexStr;
    }


    public static String byteToHexString(byte[] src) {
        int iLen = src.length;
        // 每个byte(8位)用两个(16进制)字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuilder sb = new StringBuilder(iLen * 2);
        for (int tmp : src) {
            // 把负数转换为正数
            while (tmp < 0) {
                tmp = tmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (tmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(tmp, 16));
        }
        return sb.toString();


    }




    private static String parseExtInfo(Object ext) {
        if (!(ext instanceof Map)) {
            return null;
        }


        Map<Object, Object> extInfo = (Map<Object, Object>) ext;
        if (extInfo.isEmpty()) {
            return null;
        }


        Object[] keys = extInfo.keySet().toArray();
        Arrays.sort(keys);


        StringBuilder extStr = new StringBuilder();
        Object value;
        boolean isFirst = true;
        for (Object key : keys) {
            value = extInfo.get(key.toString());

            if (!isFirst) {
                extStr.append(",");
            }else{
                extStr.append("{");
                isFirst = false;
            }
            extStr.append("\""+key+"\"").append(":\"").append(value.toString()).append("\"");
        }
        extStr.append("}");
        return extStr.toString();
    }
    private static boolean isIgnoredItem(Object value) {
        String sValue;

        if (null == value) {
            return true;
        }
        sValue = value.toString();
        return isBlank(sValue);
    }


    private static boolean isBlank(String s) {
        if (s == null) {
            return true;
        }
        return s.trim().length() <= 0;
    }




    private static String post(String url, String jsonParam,int reSend) throws IOException {
        //声明返回结果
        String result = "";
        //开始请求API接口时间
        long startTime=System.currentTimeMillis();
        //请求API接口的响应时间
        long endTime= 0L;
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;
        HttpClient httpClient = null;
        try {
            // 创建连接
            httpClient = HttpClientFactory.getInstance().getHttpClient();
            // 设置请求头和报文
            HttpPost httpPost = HttpClientFactory.getInstance().httpPost(url);
            Header header=new BasicHeader("Accept-Encoding",null);
            httpPost.setHeader(header);
            // 设置报文和通讯格式
            StringEntity stringEntity = new StringEntity(jsonParam, HttpConstant.UTF8_ENCODE);
            stringEntity.setContentEncoding(HttpConstant.UTF8_ENCODE);
            stringEntity.setContentType(HttpConstant.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);
//            logger.info("请求{}接口的参数为{}",url,jsonParam);
            //执行发送，获取相应结果
            httpResponse = httpClient.execute(httpPost);
            httpEntity= httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity);
        } catch (Exception e) {
//            logger.error("请求{}接口出现异常",url,e);
            if (reSend > 0) {
//                logger.info("请求{}出现异常:{}，进行重发。进行第{}次重发",url,e.getMessage(),(HttpConstant.REQ_TIMES-reSend +1));
                result = post(url, jsonParam, reSend - 1);
                if (result != null && !"".equals(result)) {
                    return result;
                }
            }
        }finally {
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
//                logger.error("http请求释放资源异常",e);
            }
        }
        //请求接口的响应时间
        endTime=System.currentTimeMillis();
//        logger.info("请求{}接口的响应报文内容为{},本次请求API接口的响应时间为:{}毫秒",url,result,(endTime-startTime));
        return result;

    }



    private final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier()
    {
        public boolean verify(String hostname, SSLSession session)
        {
            return true;
        }
    };



    private static void trustAllHosts()
    {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager()
        {
            public X509Certificate[] getAcceptedIssuers()
            {
                return new X509Certificate[]{};
            }


            public void checkClientTrusted(X509Certificate[] chain, String authType)
            {
            }


            public void checkServerTrusted(X509Certificate[] chain, String authType)
            {
            }
        }};
        // Install the all-trusting trust manager
        try
        {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
