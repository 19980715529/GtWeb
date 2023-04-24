package com.smallchill.test;

import com.smallchill.core.toolbox.kit.MD5Utils;
import com.smallchill.core.utils.MD5;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class Demo4 {
    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String s = MD5.EncoderByMd5("lsadfsdjfLLLLLKJLJDKS");
        System.out.println(s);
        // 解码

    }
}
