package com.smallchill.system.treasure.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Random;

import static com.smallchill.core.constant.ConstConfig.GAME_URL;
import static com.smallchill.core.constant.ConstKey.*;

/**
 * @Description TODO
 * @classNameSendHttp
 * @Date 2023/2/23 9:55
 * @Version 1.0
 **/
public class SendHttp {
    private static final Logger LOGGER = LogManager.getLogger(SendHttp.class);

    @NotNull
    public static String getSign(JSONObject jsonObject) {
        String str ="";
        try {
            str = "merchantId=" + jsonObject.getString("merchantId") + "&" +
                    "merchantOrderId=" + jsonObject.getString("merchantOrderId") + "&" +
                    "amount=" + jsonObject.getString("amount") + "&" +
                    AIPAY_KEY;
        }catch (Exception e){
            return str;
        }
        return Utils.MD5(str);
    }

    // 发送邮件请求1001  金币类型:GoldType
    public static void sendEmail(Map<String,Object> map){
        String str = "gold:" +map.get("gold").toString()+
                "|action:1001"+
                "|title:" +map.get("title").toString()+
                "|toUserid:" +map.get("toUserid").toString()+
                "|content:"+map.get("content").toString()+
                "|FromUserID:"+map.get("senderId").toString()+
                "|GoldType:"+map.get("goldType").toString();
        HttpClientUtils.sendPostJson(GAME_URL, str);
    }

    /** 1002 充值兑换成功后需要调用
     * type:1 usdt充值，0:普通充值兑换
     * @param map
     */
    public static void sendGame1002(Map<String,Object> map){
        String str = "gold:" +map.get("gold")+
                "|action:1002"+
                "|Type:" +map.get("Type")+
                "|Userid:" +map.get("Userid")+
                "|gameCoin:"+map.get("gameCoin");
        HttpClientUtils.sendPostJson(GAME_URL, str);
    }
    // 1003 发送到后台提现前端修改金币数量
    public static void sendGame1003(Integer userId){
        String str = "Userid:" + userId;
        HttpClientUtils.sendPostJson(GAME_URL, str);
    }

    /**
     * 生产随机位数字符串
     * @param length
     * @return
     */
    public static String getRandomNickname(int length) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            val.append(random.nextInt(10));
        }
        return val.toString();
    }
}
