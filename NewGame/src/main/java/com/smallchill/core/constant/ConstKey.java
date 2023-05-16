package com.smallchill.core.constant;

/**
 * @Description 存放密钥
 * @classNameConstKey
 * @Date 2023/2/15 11:53
 * @Version 1.0
 **/
public interface ConstKey {
    /**RARPAY
     * 密钥6012A2FF24D0D37CF6A491F5091BEA81
     *正式 9756478D13DED7D003516E38CABD3A67
     */
    // 正式
    String SECRET_RARP_KEY="9756478D13DED7D003516E38CABD3A67";
    // 测试
//    String SECRET_RARP_KEY="6012A2FF24D0D37CF6A491F5091BEA81";

    /**RARPAY
     * 商户账号 正式：GTsuperwinner
     */
//    String ACCOUNT_RARP_NUM="test123456";
    // 正式
    String ACCOUNT_RARP_NUM="GTsuperwinner";


    /**
     * safe 密钥
     */
    String SECRET_SAFE_KEY ="b3da14ea21d689b3c72dd7ca0dbd388f";
    /**
     * safe 账号
     */
    String ACCOUNT_SAFE_NUM = "1083053";
    /**
     *
     */
    String ACCOUNT_SAFE_EMAIL = "youyanjianghu@gmail.com";

    /**
     * Metapay  的appId
     */
    String META_APPID = "6800393961650943";

    // 公钥
    String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCof//a2L5bs/65w0i" +
            "igBrIIpsFHlNlDVr8XLOBlBAFl4KFAGRrLEcsiNQc/Jzrl8gD7yTPXQeTh251w/KG1i" +
            "+v4bNoDuSpO3FhiUKa7eLZsey9zX9x9KOQJ7BY5/ok7uYCrd+KANPCIuTv3YbGJfHR5VsMdS//KsATU0fquDEJXQIDAQAB";
    // 私钥
    String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKh//9rYvluz/rnDSKKAGsg" +
            "imwUeU2UNWvxcs4GUEAWXgoUAZGssRyyI1Bz8nOuXyAPvJM9dB5OHbnXD8obWL6/hs2gO5Kk7cWGJQprt4tmx" +
            "7L3Nf3H0o5AnsFjn+iTu5gKt34oA08Ii5O/dhsYl8dHlWwx1L/8qwBNTR+q4MQldAgMBAAECgYEAjhJuzj2WM" +
            "4gOrN6Fbc6Jxlm3T5eERV3/a21WRVlsMOrQuAfeo6bP1DMCdfZg8RwnH3oSFW2hJ7k3X8B5y0rpJQrnojh51+" +
            "gQgaXu2u/CQV0uV/ij5mFrY0Rgv2118hpWrewhrfRrG4qbwyyqlTu/vhsVH0ar2TRYv0p1tCm20JkCQQDSzDbUH" +
            "wpJKzbq4WKPrZmhv6kcLGGeSGs8fDAikQ8Hh6g6BrAq8LxD3TjsDomI4rpqQfhYQBIHReMNGqFQuexvAkEAzKHX" +
            "1/QpDQzCC3YvLHSQ+4Y9FhRZbYCtFAcs8JA8H4h+AEA+4s60HzUD6Ss2SvYCpdzGtX2Ubba2nGEy++ok8wJAPG2" +
            "ELiyzwyPbO1ve6DcoumB59vSWC0zRSOaE2fPhbhQqWm/+YmDeZ0nsfmbwcEgmjj1ZFttPD7yuCNygb8bMiwJBAI" +
            "4cz9PQYzoG5DQljbln8tBbd8z/sOOVe53vJcyjvr1/g1IVTBcjyn/px5+FPR1uIISkAPRGFHDldYjBEDFXePUCQ" +
            "GUGjkN2AUM+vU6HnN8oAxJulggXH0nzCrBj0SUCprx6OM2OASVlrHrKdPhObNvF8R5Q2+WMujMjvov+DNZMe80=";


    // 三方公钥
    String PUBLIC_KEY1 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDVL4zP+d+CPC7fmNvQyd+jT+DEHwRnr9" +
            "IEYUN+dmEodf6ArN8hxD6wgxxDH2f9SrYVgYg1wfs8+8L3wdXsirSZ5+BknMNBoEW2NoHNVVVBIrDnFSgUH9" +
            "zouUHMskkLH7m2VnqfOAZLj5SUq3xuRJvbEl2blaZDhygCDSacmcTnlQIDAQAB";

    /**
     *  omom 支付
     */

    // 密钥 jckwhe1z1gdt0305yafwvoh0576u01hr
    String OMOM_KEY = "jckwhe1z1gdt0305yafwvoh0576u01hr";
    // 商户号 230431183
    String OMOM_APPID="230431183";

    /**
     * AIPay 商户号
     */
    Integer AIPAY_APPID=4005;
    // 密钥
    String AIPAY_KEY = "SDOFI2t6m5rVgAe4";

    /**
     * WePay 商户号
     */
//    String WEPAY_APPID="902902001"; 测试
    String WEPAY_APPID="111777558";

    // 代收密钥  c503bd1f6b6a408aa94a2df8189e8ef0
//    String WEPAY_CKEY = "c503bd1f6b6a408aa94a2df8189e8ef0"; 测试
    String WEPAY_CKEY = "b83e35ffe67e4b1183995a122cdc3a92";
    // 代付密钥
//    String WEPAY_PKEY = "KSIZ9QAGUTP79B1V5BBNNJ9LJP78GLLJ"; 测试
    String WEPAY_PKEY = "I4EIOUBMQKHHZ1XOQ7TGLJ6JI4QCBHMH";
    /**
     * 银河系统
     */
    // 商户号
    String CLOUDPAY_APPID = "88052";
    // 密钥
    String CLOUDPAY_KEY = "f0bff0f0c0e3b25257677a79fc2e9fb2";
    /**
     * LetsPay
     */
    // 商户号
    String LETSPAY_APPID = "1683279488703";
    // 密钥
    String LETSPAY_KEY = "LLMORHLEWPMCAVZKVLHKACMX1V6IY3ZDLRHXGZS8AZY1KQDVOYR1XLPFFLBKLMTPZWYB7ZUHLGBQR3MSP5GRRNXGSWRQQZDRA3YXVKHHGXVL4UJ5CN4G2FKJDCBXX8PE";

    /**
     * MHDPay
     */
    String MHDPAY_APPID = "GTgame";

    String MHDPAY_KEY = "4f66c7999f88ec70f2c8bd27b9dda6e2";

    /**
     * LuckyPay 支付
     */
    // 商户号
    String LUCKYPAY_APPID = "ZT23050516105";
    // 公钥
    String PUBLIC_LUCKYPAY_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDccCaYDJH3DEIDUXC7pT+j05e6xRNI8X" +
            "PRc/JBuVoXPguHobTK4I1Fs452eGhQUuKIzbbG0RBrUmGp/3mA+xLBXDVsQ9Ls0eU47epOrv8uD3joo2AN/" +
            "wGmZQO/IVHmCAHNT7Tqyi3FURT8V3aqENsTsIHiayONxgRFhCDDUd7gMQIDAQAB";

    // 私钥
    String PRIVATE_LUCKYPAY_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANxwJpgMkfcMQgNRcLulP6PTl" +
            "7rFE0jxc9Fz8kG5Whc+C4ehtMrgjUWzjnZ4aFBS4ojNtsbREGtSYan/eYD7EsFcNWxD0uzR5Tjt6k6u/y4PeOijYA3/" +
            "AaZlA78hUeYIAc1PtOrKLcVRFPxXdqoQ2xOwgeJrI43GBEWEIMNR3uAxAgMBAAECgYEAlWxQPPEIYHYmqSkjSY8SPHL" +
            "JfwjnsaI559i4dmS19NtR5XZvnrXoDT9RamzkCM6mUCVhqBM/FgCZwyoxqoSGiNlxBlZkcVdn89xVp5QvZw7sGdVKDsI" +
            "CK/KFwBTqO/eA8HpU48WN8YKvGhNs18JqM8JcQkK0LwASS+65qp6xhyECQQDuskDNiy8NvMclyfoH/T9hd6IFQ+He+tmd" +
            "fTnsUa1rWk16IhQjvZywWxVBavwqU72X+yay4kUgtj+e1qgAXIBnAkEA7GsPY8cTCALyArZSSmVmagiaAbZbijE5jRr/" +
            "UFNWJx0yIogbmnzvItH8dmdDmGwCQezSIYNWRGR/UgKMxXnbpwJAO+2ZA4H8UFCjF0O8eKEGdqwYi4XdgaQtWrMEBecDy" +
            "T4k0ZMYx1hlrIlRrtjzcpUPtsahkqvoeywGkRSHnX+b5QJBAOJ+htXuaJsqRzS1+wxvLLi02OZ/EB/KnPpEPQl1bXo3jE" +
            "ps3xUYT/mp9xZwQ+AIyfjiq0WUD9eh+CBXdQnTfs0CQQDZF5OLGn8R3kitc+dPnouNGSo4wbDaP4cEbP4GpWsA5DZRF/1ADFzPqeCf9qptgVHqGid7r0Ug0mRNyS3K6dnx";

    // 三方公钥
    String PUBLIC_LUCKYPAY_KEY1 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQ88CLmOfbpHXjYaF1el3i3qa9vBBlae" +
            "8gyGlK3desGb3uKe2JWGtsRonVa1QIzha2JCjTm0rUzbhttfM3oJ0Jo9+Awmxfjxa0okkxxCnXGlAS/9O" +
            "vsry0wwKsCAHcu5wd/3oFeOznwX7uvuFo7Y/67y8/PpybU1qyheglSGxSnwIDAQAB";

}
