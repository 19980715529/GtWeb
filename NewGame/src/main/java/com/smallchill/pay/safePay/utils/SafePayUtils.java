package com.smallchill.pay.safePay.utils;

import com.alibaba.fastjson.JSON;
import com.smallchill.pay.rarPay.utils.RarPayUtils;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.HttpClientUtils;
import com.smallchill.system.treasure.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.smallchill.core.constant.ConstKey.*;
import static com.smallchill.core.constant.ConstUrl.*;

public class SafePayUtils {
    private static final Logger LOGGER = LogManager.getLogger(SafePayUtils.class);

    public static String recharge(RechargeRecords rechargeRecords, Map<String,Object> channel){
        Map<String, Object> params = new HashMap<>();
        params.put("currency", "PHP");
        params.put("mer_no", ACCOUNT_SAFE_NUM);
        params.put("order_amount", rechargeRecords.getTopUpAmount());
        params.put("method", "trade.create");
        params.put("order_no", rechargeRecords.getOrderNumber());
        params.put("payemail", ACCOUNT_SAFE_EMAIL);
        params.put("payname", "LI LAN LAN");
        String phone = rechargeRecords.getPhone();
        if (!Utils.isIgnoredItem(phone)){
            params.put("payphone", phone);
        }
        params.put("payphone", "85256332649");
        params.put("paytypecode", channel.get("code")); // 支付类型 21001 gacsh 21002 paymaya
        params.put("returnurl", RECHARGE_SAFE_CALLBACK_URL);
        //  签名
        String sign  = HttpClientUtils.getSign(params, SECRET_SAFE_KEY);
        params.put("sign", sign);
        LOGGER.error(JSON.toJSONString(params));
        return HttpClientUtils.sendPostJson(SAFE_URL, JSON.toJSONString(params));
    }

    public static String exchange(ExchangeReview exchangeReview, Map<String,Object> channel){
        Map<String, Object> params = new HashMap<>();
        params.put("currency", "PHP");
        params.put("mer_no", ACCOUNT_SAFE_NUM);
        params.put("order_amount", exchangeReview.getMoney().toString());
        params.put("method", "fund.apply");
        params.put("order_no", exchangeReview.getOrderNumber());
        params.put("acc_code", channel.get("code")); // PAYMAYA  "PH_GCASH"
        params.put("acc_name", exchangeReview.getCardholder());
        params.put("acc_no", exchangeReview.getBankNumber());
        params.put("returnurl", EXCHANGE_SAFE_CALLBACK_URL);
        // 签名
        String sign  = HttpClientUtils.getSign(params, SECRET_SAFE_KEY);
        params.put("sign", sign);
        LOGGER.error(JSON.toJSONString(params));
        return HttpClientUtils.sendPostJson(SAFE_URL, JSON.toJSONString(params));
    }
}
