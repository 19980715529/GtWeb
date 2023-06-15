package com.smallchill.pay.betcatpay.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.pay.betcatpay.model.BetcatPay;
import com.smallchill.pay.betcatpay.utils.SignUtils;
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

import static com.smallchill.system.treasure.utils.CallBackUtils.*;

@RestController
@RequestMapping("/callback/BetcatPay")
public class BetcatPayCallbackController extends BaseController implements ConstShiro {

    @Resource
    private BetcatPay betcatPay;

    @PostMapping(value = "/recharge",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String rechargeLuckyPayCallback(@RequestParam Map<String,Object> param) {
        // 验证签名
        if (param == null) {
            return "fail";
        }
        LOGGER.error(param);
        Boolean temp = SignUtils.verify(betcatPay.payKey,param);
        if (!temp) {
            return "fail";
        }
        LOGGER.error("认证成功");
        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
        // 获取订单号
        String orderNum = params.getString("merOrderNo");
        Blade blade = Blade.create(RechargeRecords.class);
        RechargeRecords rechargeRecords = blade.findFirstBy("orderNumber=#{orderNum}", CMap.init().set("orderNum", orderNum));
        if (rechargeRecords == null) {
            return "ok";
        }
        // 订单状态（0生成订单，1支付中，2支付未通知，3支付已通知，-1交易失败，-2交易过期，-3交易退还，-4交易异常）
        int status = params.getIntValue("orderStatus");
        if (status==3) {
            if (rechargeRecords.getOrderStatus() == 2) {
                return "ok";
            }
            successRecExecuted(orderNum, rechargeRecords);
        } else if (status < 0){
            // 从延迟队列中移除并且更新状态
            rechargeRecords.setMsg(params.getString("message"));
            failRecExecuted(rechargeRecords);
        }
        return "ok";
    }

    @PostMapping(value = "/exchange",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String exchangeLuckyPayCallback(@RequestParam Map<String,Object> param){
        if (param==null){
            return "fail";
        }
        LOGGER.error(param);
        Boolean temp = SignUtils.verify(betcatPay.payOutKey,param);
        if (!temp){
            return "fail";
        }
        LOGGER.error("认证成功");
        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
        // 获取订单号
        String orderNum = params.getString("merOrderNo");
        Blade blade = Blade.create(ExchangeReview.class);
        ExchangeReview exchangeReview =blade.findFirstBy("orderNumber = #{orderNumber}", CMap.init().set("orderNumber",orderNum));
        if (exchangeReview==null){
            return "ok";
        }
        // 订单状态（0生成订单，1支付中，2支付未通知，3支付已通知，-1交易失败，-2交易过期，-3交易退还，-4交易异常）
        int status = params.getIntValue("orderStatus");
        if (status==3){
            if (exchangeReview.getStatus()==3||exchangeReview.getStatus()==4){
                return "ok";
            }
            // 回调成功
            successExcExecuted(exchangeReview);
        }else if (status<0){
            // 支付失败兑换变为支付失败
            exchangeReview.setStatus(6);
            exchangeReview.setEndTime(new Date());
            String msg = params.getString("message");
            exchangeReview.setMsg(msg);
        }
        // 修改订单状态
        blade.update(exchangeReview);
        return "ok";
    }
}
