package com.smallchill.system.treasure.utils;

import okhttp3.Request;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.smallchill.core.constant.ConstKey.*;

public class HttpClientUtils {

    public static String sendPostJson(String url, String json) {
        HttpPost httpPost = null;
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setConnectTimeout(5000) //连接时间
                .setSocketTimeout(2000) //请求处理时间
                .setConnectionRequestTimeout(5000) //从连接池获取连接超时时间
                .build();
        CloseableHttpResponse response=null;
        CloseableHttpClient httpClient = null;
        try{
            httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
            httpPost = new HttpPost(url);
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            String resp = EntityUtils.toString(response.getEntity(), "utf-8");
            return resp;
        } catch (Exception e) {
            return e.getMessage();
        }finally {
            if(response!=null){
                try {
                    response.close();
                }catch (IOException e){
                    return "";
                }finally {
                    if (httpClient!=null){
                        try {
                            httpClient.close();
                        }catch (IOException e){
                            return "";
                        }
                    }
                }
            }


        }
    }

    public static String getSign(Map<String, Object> map, String appkey){
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
            if (Utils.isIgnoredItem(value)) {
                continue;
            }
            if(i==keys.length-1){
                originStr.append(keys[i]).append("=").append(value);
                continue;
            }
            originStr.append(keys[i]).append("=").append(value).append("&");
        }
        originStr.append(appkey);
        sign = Utils.MD5(originStr.toString());
        return sign;
    }
    /**
     *verification
     */
    public static Boolean Md5RarpVerification(Map<String, Object> requestParams) {
        String sign = requestParams.remove("sign").toString();
        if (sign==null){
            return false;
        }
        String sign1 = Utils.getSign(requestParams, SECRET_RARP_KEY);
        System.out.println("sign:"+sign1);
        return sign.equals(sign1);
    }
    /**
     * safe 密钥校验 Md5RarpVerification
     */
    public static Boolean Md5SafeVerification(Map<String,Object> map){
        String sign = map.remove("sign").toString();
        if (sign  == null){
            return false;
        }
        String sign1 = getSign(map, SECRET_SAFE_KEY);
        return sign.equals(sign1);
    }

    /** Omo签名校验
     *verification
     */
    public static Boolean Md5OmoVerification(Map<String, Object> requestParams) {
        try {
            String sign = requestParams.remove("sign").toString();
            if (sign==null){
                return false;
            }
            String sign1 = Utils.getSign(requestParams, OMOM_KEY);
            if (sign.equals(sign1.toUpperCase())){
                return true;
            }
        }catch (Exception e){
            return false;
        }
        return false;
    }

    public static Boolean Md5WePayVerification(Map<String, Object> requestParams,String key) {
        try {
            String sign = requestParams.remove("sign").toString();
            requestParams.remove("signType");
            if (sign==null){
                return false;
            }
            String sign1 = Utils.getSign(requestParams, key);
            if (sign.equals(sign1)){
                return true;
            }
        }catch (Exception e){
            return false;
        }
        return false;
    }

    /**
     * safe 密钥校验 Md5RarpVerification
     */
    public static Boolean Md5GalaxyVerification(Map<String,Object> map){
        try {
            String sign = map.remove("sign").toString();
            if (sign  == null){
                return false;
            }
            String sign1 = Utils.getSign(map, GALAXY_KEY);
            return sign.equals(sign1);
        }catch (Exception e){
         return false;
        }
    }


}
