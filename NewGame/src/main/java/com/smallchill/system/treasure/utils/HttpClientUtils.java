package com.smallchill.system.treasure.utils;

import com.smallchill.pay.rarPay.model.RarPay;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Map;

import static com.smallchill.core.constant.ConstKey.*;

public class HttpClientUtils {
    private static final Logger LOGGER = LogManager.getLogger(HttpClientUtils.class);
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
            return EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return "";
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
    public static Boolean Md5RarpVerification(Map<String, Object> requestParams, RarPay rarPay) {
        String sign = requestParams.remove("sign").toString();
        if (sign==null){
            return false;
        }
        String sign1 = Utils.getSign(requestParams, rarPay.getKey());
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
    public static Boolean Md5OmoOrLetsPayVerification(Map<String, Object> requestParams,String key) {
        try {
            String sign = requestParams.remove("sign").toString();
            if (sign==null){
                return false;
            }
            String sign1 = Utils.getSign(requestParams,key);
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
    public static Boolean Md5GalaxyVerification(Map<String,Object> map,String key){
        try {
            String sign = map.remove("sign").toString();
            if (sign  == null){
                return false;
            }
            String sign1 = Utils.getSign(map, key);
            return sign.equals(sign1);
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 发送get请求
     *
     * @param url   请求URL
     * @param param 请求参数 key:value url携带参数 或者无参可不填
     * @return
     */
    public static String doGet(String url, Map<String, String> param) {

        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            response = httpClient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }


}
