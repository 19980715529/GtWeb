package com.smallchill.pay.luckypay.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.pay.luckypay.model.LuckPay;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.HttpClientUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

import static com.smallchill.core.constant.ConstKey.PUBLIC_LUCKYPAY_KEY1;
import static com.smallchill.system.treasure.utils.CallBackUtils.*;

@RestController
@RequestMapping("/callback/LuckyPay")
public class LuckyPayCallbackController extends BaseController implements ConstShiro {

    @Resource
    private LuckPay luckPay;

    @PostMapping(value = "/recharge",produces = MediaType.TEXT_PLAIN_VALUE)
    public String rechargeLuckyPayCallback(@RequestParam Map<String,Object> param) {
        // 验证签名
        if (param == null) {
            return "fail";
        }
        LOGGER.error(param);
        Boolean temp = HttpClientUtils.Md5GalaxyVerification(param, luckPay.platformPublicKey);
        if (!temp) {
            return "fail";
        }
        LOGGER.error("认证成功");
        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
        // 获取订单号
        String orderNum = params.getString("orderNo");
        Blade blade = Blade.create(RechargeRecords.class);
        RechargeRecords rechargeRecords = blade.findFirstBy("orderNumber=#{orderNum}", CMap.init().set("orderNum", orderNum));
        if (rechargeRecords == null) {
            return "SUCCESS";
        }
        // 1 支付中,2 成功,5 失效,-1 失败
        String code = params.getString("code");
        if ("00".equals(code)) {
            if (rechargeRecords.getOrderStatus() == 2) {
                return "SUCCESS";
            }
            successRecExecuted(orderNum, rechargeRecords);
        } else {
            // 从延迟队列中移除并且更新状态
            failRecExecuted(rechargeRecords);
        }
        return "SUCCESS";
    }

    @PostMapping(value = "/exchange",produces = MediaType.TEXT_PLAIN_VALUE)
    public String exchangeLuckyPayCallback(@RequestParam Map<String,Object> param){
        if (param==null){
            return "fail";
        }
        LOGGER.error(param);
        Boolean temp = HttpClientUtils.Md5GalaxyVerification(param,luckPay.platformPublicKey);
        if (!temp){
            return "fail";
        }
        LOGGER.error("认证成功");
        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
        // 获取订单号
        String orderNum = params.getString("orderNo");
        Blade blade = Blade.create(ExchangeReview.class);
        ExchangeReview exchangeReview =blade.findFirstBy("orderNumber = #{orderNumber}", CMap.init().set("orderNumber",orderNum));
        if (exchangeReview==null){
            return "SUCCESS";
        }
        // 获取订单状态 2； 成功，3：失败 1:处理中
        String code = params.getString("code");
        if ("00".equals(code)){
            if (exchangeReview.getStatus()==3||exchangeReview.getStatus()==4){
                return "SUCCESS";
            }
            int status = params.getIntValue("status");
            if (status==1){
                // 回调成功
                successExcExecuted(exchangeReview);
            }
        }else{
            // 支付失败兑换变为支付失败
            exchangeReview.setStatus(6);
            exchangeReview.setEndTime(new Date());
            String msg = params.getString("msg");
            exchangeReview.setMsg(msg);
        }
        // 修改订单状态
        blade.update(exchangeReview);
        return "SUCCESS";
    }
}
