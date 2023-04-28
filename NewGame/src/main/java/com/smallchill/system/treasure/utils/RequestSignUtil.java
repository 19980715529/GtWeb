package com.smallchill.system.treasure.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
//import org.apache.tomcat.util.http.fileupload.IOUtils;
//import org.assertj.core.util.Strings;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * @author Hardy
 * @version 1.0
 * @date 2022/2/10 14:13
 * @desc
 */
@Component
public class RequestSignUtil {

    /**
     * 请求参数签名
     * @param objectMap
     * @param customerPrivateKey
     * @return
     */
    public static String getSign(Map<String,Object> objectMap,String customerPrivateKey){
        List<String> paramNameList = new ArrayList<>();
        for (String key : objectMap.keySet()) {
            paramNameList.add(key);
        }
        Collections.sort(paramNameList);  // 排序key
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < paramNameList.size(); i++) {
            String key = paramNameList.get(i);
            stringBuilder.append(objectMap.get(key));  // 拼接参数
        }
        String keyStr = stringBuilder.toString();  // 得到待加密的字符串
        System.out.println("带加密的字符串："+keyStr);
        String signedStr = "";
        try {
            signedStr = privateEncrypt(keyStr, getPrivateKey(customerPrivateKey));  // 私钥加密
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return signedStr;
    }


    /**
     * 验证签名
     *
     * @param params
     * @return
     */
    public static boolean verifySign(JSONObject params, String publickey) {
        String platSign = params.getString("sign"); // 签名
        List<String> paramNameList = new ArrayList<>();
        for (String key : params.keySet()) {
            if (!"sign".equals(key)) {
                String b = params.getString(key);
                if (!isNullOrEmpty(b)){
                    paramNameList.add(key);
                }
            }
        }
        Collections.sort(paramNameList);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < paramNameList.size(); i++) {
            String name = paramNameList.get(i);
            stringBuilder.append(params.getString(name));
        }

        String decryptSign = "";
        try {
            decryptSign = publicDecrypt(platSign, getPublicKey(publickey));
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        if (!stringBuilder.toString().equalsIgnoreCase(decryptSign)) {
            return false;
        }
        return true;
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0;
    }
    /**
     * 私钥加密
     *
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateEncrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return Base64.getEncoder().encodeToString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes("UTF-8"), privateKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 公钥解密
     *
     * @param data
     * @param publicKey
     * @return
     */

    public static String publicDecrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.getDecoder().decode(data), publicKey.getModulus().bitLength()), "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 得到私钥
     *
     * @param privateKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        return key;
    }

    /**
     * 得到公钥
     *
     * @param publicKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        return key;
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
        int maxBlock = 0;
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try {
            while (datas.length > offSet) {
                if (datas.length - offSet > maxBlock) {
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
        }
        byte[] resultDatas = out.toByteArray();
        IOUtils.closeQuietly(out);
        return resultDatas;
    }

    public static String doPost(String url, JSONObject json) {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.setHeader("x-forwarded-for", "52.76.118.232");
        String response = "";
        try {
            StringEntity s = new StringEntity(json.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);
            HttpResponse res = client.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                String result = EntityUtils.toString(res.getEntity(),"utf-8");// 返回json格式：
//                response = JSONObject.parseObject(result);
                response = result;
            }else {
                HttpEntity entity = res.getEntity();
                String result = EntityUtils.toString(res.getEntity(),"utf-8");// 返回json格式：
                response = result;
            }
        } catch (Exception e) {
//            throw new RuntimeException(e);
            return "";
        }
        return response;
    }


    /**
     * 将请求值装换成JSONObject
     *
     * @param request
     * @return
     */
    public JSONObject getRequestParams(HttpServletRequest request) {
        Map map = new HashMap();
        Enumeration<String> it = request.getParameterNames();
        while (it.hasMoreElements()) {
            String name = it.nextElement();
            String value = request.getParameter(name);
            value = (StringUtils.isBlank(value)) || (value.equals("null")) ? "" : value;
            map.put(name, value);
        }
        return JSONObject.parseObject(JSON.toJSONString(map));
    }


}
