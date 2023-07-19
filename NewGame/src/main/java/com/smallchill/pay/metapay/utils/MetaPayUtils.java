package com.smallchill.pay.metapay.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.pay.cloudpay.utils.CloudPayUtils;
import com.smallchill.pay.metapay.model.MetaPay;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.RequestSignUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.smallchill.core.constant.ConstKey.META_APPID;
import static com.smallchill.core.constant.ConstKey.PRIVATE_KEY;
import static com.smallchill.core.constant.ConstUrl.*;
import static com.smallchill.core.constant.ConstUrl.EXCHANGE_META_URL;

public class MetaPayUtils {
    private static final Logger LOGGER = LogManager.getLogger(MetaPayUtils.class);

    public static String recharge(RechargeRecords records, MetaPay metaPay){
        String response="";
        HashMap<String, Object> params = new HashMap<>();
        params.put("appId",metaPay.getAppId());
        // 代收渠道
        params.put("channel",metaPay.getChannel());
        // 平台订单号
        params.put("referenceNo", records.getOrderNumber());
        // 金额
        params.put("amount",records.getTopUpAmount());
        // 用户手机号11位
        params.put("mobile","09111111111");
        // 用户名称
        params.put("userName","Lucio,Drew,Bongalos");
        // 用户地址
        params.put("address","E Flores St, Pasay, Metro Manila");
        // 备注
        params.put("remark","recharge");
        // 用户邮箱
        params.put("email","gtpay@gmail.com");
        //
        params.put("productType",metaPay.getProductType());
        //
        params.put("notificationURL",RECHARGE_META_CALLBACK_URL);
        // 生成签名
        String sign = RequestSignUtil.getSign(params, metaPay.getMetaPrivateKey());
        params.put("sign",sign);
        JSONObject jsonParams = JSONObject.parseObject(JSON.toJSONString(params));
        response = RequestSignUtil.doPost(metaPay.getPayUrl(), jsonParams);
        return response;
    }

    public static String exchange(ExchangeReview exchangeReview,MetaPay metaPay){
        String response="";
        HashMap<String, Object> map = new HashMap<>();
        // 商户号
        map.put("appId",metaPay.getAppId());
        // 代付渠道
        map.put("pickupCenter",metaPay.getPickupCenter());
        // 订单号
        map.put("referenceNo", exchangeReview.getOrderNumber());
        // 代付金额
        map.put("collectedAmount",exchangeReview.getMoney());
        // 账户
        map.put("accountNo",exchangeReview.getBankNumber());
        // 取款人名
        map.put("userName",exchangeReview.getCardholder());
        // 生日
        map.put("birthDate","2019-08-30");
        // 电话号
        map.put("mobileNumber",exchangeReview.getPhone());
        // 证件类型
        map.put("certificateType",metaPay.getCertificateType());
        // 证件号码，没有随机10位
        map.put("certificateNo",getRandomNickname(10));
        // 居住地址
        map.put("address","190 Poblacion Street");
        // 城市
        map.put("city","HOUSTON");
        // 省份
        map.put("province","TEXAS");
        // 回调url
        map.put("notificationURL",EXCHANGE_META_PAY_CALLBACK_URL);
        // 生成签名
        String sign = RequestSignUtil.getSign(map, metaPay.getMetaPrivateKey());
        map.put("sign",sign);
        JSONObject jsonParams = JSONObject.parseObject(JSON.toJSONString(map));
        // 发起请求
        response = RequestSignUtil.doPost(metaPay.getPayOutUrl(), jsonParams);
        return response;
    }

    public static String getRandomNickname(int length) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            val.append(random.nextInt(10));
        }
        return val.toString();
    }


    public static boolean MetaPayExchange(ExchangeReview exchangeReview,MetaPay metaPay) {

        String response = MetaPayUtils.exchange(exchangeReview, metaPay);
        LOGGER.error(response);
        if ("".equals(response)) {
            return true;
        }
        // 获取平台订单号
        JSONObject respJson = JSONObject.parseObject(response);
        // 获取请求状态
        int code = respJson.getIntValue("platRespCode");
        if (code == 0) {
            // 请求成功, 获取平台订单号
            String PfOrderNum = respJson.getString("transId");
            exchangeReview.setPfOrderNum(PfOrderNum);
            exchangeReview.setStatus(1);
        } else {
            // 请求失败, 存储失败原因
            exchangeReview.setMsg(respJson.getString("msg"));
            // 将状态设置为失败
            exchangeReview.setStatus(6);
        }
        return false;
    }
}
