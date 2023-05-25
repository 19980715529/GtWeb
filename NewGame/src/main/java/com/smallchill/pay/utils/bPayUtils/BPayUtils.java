package com.smallchill.pay.utils.bPayUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smallchill.pay.model.bpay.BPay;
import com.smallchill.pay.utils.rarPayUtils.RarPayUtils;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.HttpClientUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.HashMap;
import java.util.Map;

import static com.smallchill.core.constant.ConstUrl.*;

public class BPayUtils {
    private static final Logger LOGGER = LogManager.getLogger(RarPayUtils.class);
    /**
     * 充值rar
     */
    public static String sendRechargeBPay(RechargeRecords rechargeRecords, Map<String,Object> channel, BPay bPay){
        // 生成请求条件
        String response="";
        HashMap<String, Object> param = new HashMap<>();
        // 商户号
        param.put("merchantNo", bPay.getMerchantNo());
        // 订单号
        param.put("merchantOrderNo", rechargeRecords.getOrderNumber());
        // 国家代码
        param.put("countryCode", bPay.getCountryCode());
        // 币种代码
        param.put("currencyCode", bPay.getCurrencyCode());
        // 支付/代付业务编码
        param.put("paymentType", bPay.getPaymentType());
        // 金额
        param.put("paymentAmount", rechargeRecords.getTopUpAmount());
        // 商品名称
        param.put("goods", "GT_GAME");
        // 回调地址
        param.put("notifyUrl", RECHARGE_BPAY_CALLBACK_URL);
        // 参数排序
        String paramStr = SortUtils.getMapString(param, null, null);
        // 生成签名
        String sign = RSAUtils.sign(paramStr, bPay.getAccessPrivateKey());
        param.put("sign_type", bPay.getType());
        param.put("sign", sign);
        String jsonStr = JSON.toJSONString(param);
        // 发送请求
        response = HttpClientUtils.sendPostJson(bPay.getPayUrl(), jsonStr);
//        LOGGER.error(response);
        Map<String, Object> resultMap = JSON.parseObject(response, new TypeReference<Map<String, Object>>() {
        });
        String code =(String) resultMap.get("code");
        if (StringUtils.isEmpty(code) || !"200".equals(code)) {
            String respMessage = (String) resultMap.get("message");
            LOGGER.error(respMessage);
            return response;
        }
        Object data = resultMap.get("data");
        Map<String, Object> dataMap = JSON.parseObject(data.toString(), new TypeReference<Map<String, Object>>() {
        });
        String returnSign = dataMap.remove("sign").toString();
        String srcData = SortUtils.getMapString(dataMap, null, null);
        boolean isPass = RSAUtils.validate(srcData, bPay.getCallbackPublicKey(), returnSign);
        if (!isPass){
            return "";
        }
        return response;
    }

    /**
     * rarp兑换
     */
    public static String sendExchangeBPay(ExchangeReview exchangeReview, Map<String,Object> channel, BPay bPay){
        String response="";
        HashMap<String, Object> param = new HashMap<>();
        param.put("merchantNo", bPay.getMerchantNo());
        param.put("merchantOrderNo", exchangeReview.getOrderNumber());
        param.put("countryCode", bPay.getCountryCode());
        param.put("currencyCode", bPay.getCurrencyCode());
        // gcash 902410175001  maya 902410175002
        param.put("transferType", bPay.getTransferType());
        param.put("transferAmount", exchangeReview.getMoney());
        param.put("feeDeduction", "1");
        param.put("notifyUrl", EXCHANGE_BPAY_CALLBACK_URL);
        param.put("remark", "GTGame");
        String extendedParams = "bankAccount^"+exchangeReview.getPhone()+"|bankCode^GCASH";
        param.put("extendedParams", extendedParams);
        String paramStr = SortUtils.getMapString(param, null, null);
        String sign = RSAUtils.sign(paramStr, bPay.getAccessPrivateKey());
        param.put("sign_type", bPay.getType());
        param.put("sign", sign);
        String jsonStr = JSON.toJSONString(param);
        response = HttpClientUtils.sendPostJson(bPay.getPayOutUrl(), jsonStr);
        LOGGER.error(response);
        Map<String, Object> resultMap = JSON.parseObject(response, new TypeReference<Map<String, Object>>() {
        });
        String code =(String) resultMap.get("code");
        if (StringUtils.isEmpty(code) || !"200".equals(code)){
            String respMessage = (String) resultMap.get("message");
            LOGGER.error(respMessage);
            return response;
        }
        Object data = resultMap.get("data");
        LOGGER.error(data.toString());
        Map<String, Object> dataMap = JSON.parseObject(data.toString(), new TypeReference<Map<String, Object>>() {
        });
        String returnSign = dataMap.remove("sign").toString();
        String srcData = SortUtils.getMapString(dataMap, null, null);
        boolean isPass = RSAUtils.validate(srcData, bPay.getCallbackPublicKey(), returnSign);
        if (!isPass){
            return "";
        }
        return response;
    }
}
