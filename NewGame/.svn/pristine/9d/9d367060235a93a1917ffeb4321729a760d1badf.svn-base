package com.smallchill.test;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Demo6 {
    public static void main(String[] args) {
        String json="action:1001,toUserid:31232082,title:award,content:this is an award mail,gold:200";
        sendPostJson("http://192.168.0.112:5555",json);
    }

    public static String sendPostJson(String url, String json) {
        HttpPost httpPost = null;
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setConnectTimeout(5000) //连接时间
                .setSocketTimeout(2000) //请求处理时间
                .setConnectionRequestTimeout(3000) //从连接池获取连接超时时间
                .build();
        CloseableHttpClient httpClient;
        try{
            httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
            httpPost = new HttpPost(url);
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");

//            httpPost.setHeader("Accept", "application/json");
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            try(CloseableHttpResponse response = httpClient.execute(httpPost)){
                String resp = EntityUtils.toString(response.getEntity(), "utf-8");
                return resp;
            }
        } catch (Exception e) {
            System.out.println("请求超时");
//            throw new RuntimeException("发送post请求失败", e);
            return "请求超时";
        }
    }
}
