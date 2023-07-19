package com.smallchill.pay.bpay.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.pay.bpay.model.BPay;
import com.smallchill.pay.bpay.utils.RSAUtils;
import com.smallchill.pay.bpay.utils.SortUtils;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

import static com.smallchill.system.treasure.utils.CallBackUtils.*;

@RestController
@RequestMapping("/callback/BPay")
public class BPayCallbackController extends BaseController implements ConstShiro {
    @Resource
    private BPay bPay;
    @PostMapping(value = "/recharge",consumes = "application/json")
    public String rechargeAIPayCallback(@RequestBody Map<String,Object> param){
        // 验证签名
        if (param==null){
            return "fail";
        }
        LOGGER.error(param);
        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
        String mcSign = params.remove("sign").toString();
        String srcData = SortUtils.getMapString(params, null, null);
        boolean bb = RSAUtils.validate(srcData, bPay.getCallbackPublicKey(), mcSign);
        if (!bb){
            return "fail";
        }
        // 获取订单号
        String orderNum = params.getString("merchantOrderNo");
        Blade blade = Blade.create(RechargeRecords.class);
        RechargeRecords rechargeRecords = blade.findFirstBy("orderNumber=#{orderNum}", CMap.init().set("orderNum", orderNum));
        if (rechargeRecords==null){
            return "SUCCESS";
        }
        // 获取订单状态
        String status = params.getString("paymentStatus");
        if ("SUCCESS".equals(status)){
                if (rechargeRecords.getOrderStatus()==2){
                    return "SUCCESS";
                }
                successRecExecuted(orderNum,rechargeRecords);
        }else {
            failRecExecuted(rechargeRecords);
        }
        return "SUCCESS";
    }

    @PostMapping(value = "/exchange",consumes = "application/json")
    public String exchangeAIPayCallback(@RequestBody Map<String,Object> param){
        // 验证签名
        if (param==null){
            return "fail";
        }
        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
        String mcSign = params.remove("sign").toString();
        String srcData = SortUtils.getMapString(params, null, null);
        boolean bb = RSAUtils.validate(srcData, bPay.getCallbackPublicKey(), mcSign);
        if (!bb){
            return "fail";
        }
        // 根据订单号查询兑换订单
        String orderNum = params.getString("merchantOrderNo");
        Blade blade = Blade.create(ExchangeReview.class);
        ExchangeReview exchangeReview =blade.findFirstBy("orderNumber = #{orderNumber}", CMap.init().set("orderNumber",orderNum));
        if (exchangeReview==null){
            return "SUCCESS";
        }
        // 获取订单状态
        String status = params.getString("transferStatus");
        if ("SUCCESS".equals(status)){
                if (exchangeReview.getStatus()==3||exchangeReview.getStatus()==4){
                    return "SUCCESS";
                }
                // 回调成功
                successExcExecuted(exchangeReview);
                blade.update(exchangeReview);
        } else if ("FAILED".equals(status)){
            // 支付失败兑换变为支付失败
            exchangeReview.setStatus(6);
            exchangeReview.setEndTime(new Date());
            blade.update(exchangeReview);
        }
        // 修改订单状态
        return "SUCCESS";
    }


}
