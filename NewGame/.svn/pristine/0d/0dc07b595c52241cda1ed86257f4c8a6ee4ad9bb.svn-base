package com.smallchill.system.treasure.utils;


import org.apache.commons.lang3.RandomStringUtils;
import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;


public class Utils {
    /**
     * 生成订单号（25位）：时间（精确到毫秒）+3位随机数+5位用户id
     */
    public static String getOrderNum(Integer userId) {
        //时间（精确到毫秒）
        DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String localDate = LocalDateTime.now().format(ofPattern);
        //3位随机数
        String randomNumeric = RandomStringUtils.randomNumeric(4);
        //5位用户id
        int subStrLength = 5;
        String sUserId = userId.toString();
        int length = sUserId.length();
        String str;
        if (length >= subStrLength) {
            str = sUserId.substring(length - subStrLength, length);
        } else {
            str = String.format("%0" + subStrLength + "d", userId);
        }
        String orderNum = localDate + randomNumeric + str;
        return orderNum;
    }

    // 生成拼接成请求的Sign格式
    public static String getSign(Map<String,Object> map,String appkey){
        String sign =null;
        if (map.isEmpty()){
            return "";
        }
        Object[] keys = map.keySet().toArray();
        Arrays.sort(keys);
        StringBuilder originStr = new StringBuilder();
        Object value;
        for (Object key:keys){
            value = map.get(key.toString());
            // 判断字符串是否为空
            if (isIgnoredItem(value)){
                continue;
            }
            originStr.append(key).append("=").append(value).append("&");
        }
        // 得到加密前数据
        originStr.append("key=").append(appkey);
        try {
            sign = MD5(originStr.toString());
        }catch (Exception e){
            return sign;
        }
        return sign;
    }

    public static boolean isIgnoredItem(Object value) {
        String sValue;
        if (null == value) {
            return true;
        }

        sValue = value.toString();
        return isBlank(sValue);
    }

    // 判断字符串是否为空
    private static Boolean isBlank(String s){
        if (s == null) {
            return true;
        }
        return s.trim().length() <= 0;
    }


    // 发起post请求
    public static String post(String postURL,Map<String,Object> params){
        /*
        postURL:请求路径
        params:请求参数
         */
        String response = "";
        try {
            trustAllHosts();
            URL url= new URL(postURL);
            // 开始访问
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> param: params.entrySet()){
                if (postData.length() != 0){
                    postData.append("&");
                }
                postData.append(URLEncoder.encode(param.getKey(),"UTF-8"));
                postData.append("=");
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()),"UTF-8"));
            }

            HttpURLConnection conn=null;
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
        } catch (Exception e) {
           return response;
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
    public static String parseExtInfo(Object ext) {
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

}
