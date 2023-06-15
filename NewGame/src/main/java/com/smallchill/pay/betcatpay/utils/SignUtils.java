package com.smallchill.pay.betcatpay.utils;

import lombok.NonNull;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

public class SignUtils {
    public static final String KEY_SIGN = "sign";
    public static final String KEY_EXT = "extra";

    public static String create(@NonNull String appSecret, @NonNull Map<String, Object> map) {
        String originStr = createSignStr(appSecret, map);
        try {
            return sha256(originStr);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static Boolean verify(String key,Map<String,Object> map){
        String sign = map.remove("sign").toString();
        String s = create(key, map);
        assert s != null;
        return s.equals(sign);
    }

    public static String createSignStr(@NonNull String appSecret, @NonNull Map<String, Object> map) {
        if (map.isEmpty()) {
            return "";
        }
        Object[] keys = map.keySet().toArray();
        Arrays.sort(keys);

        StringBuilder originStr = new StringBuilder();
        Object value;
        for (Object key: keys) {
            value = map.get(key.toString());

            if (KEY_EXT.equals(key.toString())){
                value = parseExtInfo(value);
            }

            if (isIgnoredItem(key, value)) {
                continue;
            }

            originStr.append(key).append("=").append(value).append("&");
        }
        originStr.append("key=").append(appSecret);
        return originStr.toString();
    }

    private static boolean isIgnoredItem(Object key, Object value) {
        String sKey, sValue;
        sKey = key.toString().trim();

        if (KEY_SIGN.equals(sKey)) {
            return true;
        }

        if (null == value) {
            return true;
        }

        sValue = value.toString().trim();
        return isBlank(sValue);
    }

    @SuppressWarnings("unchecked")
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
            if (isBlank(value)) {
                continue;
            }

            if (!isFirst) {
                extStr.append("&");
            }
            extStr.append(key).append("=").append(value.toString());
            isFirst = false;
        }

        return extStr.toString();
    }


    private static String sha256(String text) throws NoSuchAlgorithmException {
        return encrypt(text, "SHA-256");
    }

    private static String sha512(String text) throws NoSuchAlgorithmException {
        return encrypt(text, "SHA-512");
    }

    private static String encrypt(String text, String algorithm) throws NoSuchAlgorithmException {
        if (text == null || text.length() == 0) {
            return null;
        }
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        messageDigest.update(text.getBytes(StandardCharsets.UTF_8));
        byte[] byteBuffer = messageDigest.digest();

        StringBuilder sb = new StringBuilder();
        for (byte b : byteBuffer) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    private static boolean isBlank(String s) {
        if (s == null) {
            return true;
        }

        return s.trim().length() <= 0;
    }

    private static boolean isBlank(Object o) {
        if (o == null) {
            return true;
        }

        return isBlank(o.toString());
    }
}
