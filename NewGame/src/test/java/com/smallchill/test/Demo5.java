package com.smallchill.test;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.system.treasure.utils.Utils;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.util.*;
import java.security.MessageDigest;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

public class Demo5 {
    public static void main(String[] args)
    {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        String dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> extra = new HashMap<>();

//        String orderNo = "TEST_" + Utils.getOrderNum(23553);
//        map.put("mchNo", "test123456");
//        map.put("time", dateFormat);
//        map.put("type", "bank");
//        map.put("fee", "50");
//        map.put("orderNo", orderNo);
//        map.put("notifyUrl", "http://rarpay.com/app/rarpay/callback");
//        extra.put("custName", "xxxxxx");
//        extra.put("custPhone", "13211112222");
//        extra.put("clientIp", "127.0.0.1");
//        map.put("extra", parseExtInfo(extra));
//        //签名
//        String sign  = getSign(map, "6012A2FF24D0D37CF6A491F5091BEA81");
//        map.put("sign", sign);
//        System.out.println(map);
//
//        String s = post("https://rarpay.test.rarpay.com/payapi", map);
//        System.out.println(s);

//        map.put("mchNo","test123456");
//        map.put("bankAccount","123456789");
//        map.put("customerName","123456789");
//        map.put("time",dateFormat);
//        map.put("type","gcash_dpay.140");
//        map.put("orderNo",Utils.getOrderNum(25555));
//        map.put("desc",100);
//        map.put("notifyUrl","http://127.0.0.1:8081/NewGame_war_exploded/rechargeDock/callback");
//        map.put("fee",5999);
//        extra.put("custPhone", "123456789");
//        extra.put("bank_province", "no");
//        extra.put("to_city", "no");
//        map.put("extra", parseExtInfo(extra));
//        // 获取签名
//        String sign = getSign(map, "6012A2FF24D0D37CF6A491F5091BEA81");
//        map.put("sign",sign);
//        String s = post("https://rarpay.test.rarpay.com/dpayapi", map);
//        System.out.println(s);
        String str = "createTime=2023-03-10 11:15:26&fee=58.00000000&mchNo=test123456&mchOrdernum=A20230310111526O4gICV&ordernum=" +
                "20230310111526sJfEV7wm&status=20&time=2023-03-10 11:17:42&key=6012A2FF24D0D37CF6A491F5091BEA81";
        // 6b2e25e067388ec32991eec2041f5db4

        String s = MD5(str);
        System.out.println(s);
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




    private static String post(String postURL, Map<String, Object> params) {
        System.out.println("请求地址：" + postURL);
        String response = "";
        System.setProperty("https.protocols", "TLSv1.2");
        try {
            trustAllHosts();
            URL url = new URL(postURL);
            // 开始访问
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0){
                    postData.append('&');
                }
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            System.out.println("请求内容：" + postData.toString());
            HttpURLConnection conn=null ;
            // 通过请求地址判断请求类型(http或者是https)
            if (url.getProtocol().toLowerCase().equals("https"))
            {
                HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
                https.setHostnameVerifier(DO_NOT_VERIFY);
                conn = https;
            }else{
                conn = (HttpURLConnection) url.openConnection();
            }

            byte[] postDataBytes = postData.toString().getBytes("UTF-8");


            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=utf-8");
            conn.setRequestProperty("Content-Length",
                    String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);
            Reader in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
                sb.append((char) c);
            response = sb.toString();
            // System.out.println(response);


        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return response;


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

