package com.smallchill.pay.wepay.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.common.base.BaseController;
import com.smallchill.common.task.GlobalDelayQueue;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.HttpClientUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

import static com.smallchill.core.constant.ConstKey.WEPAY_CKEY;
import static com.smallchill.core.constant.ConstKey.WEPAY_PKEY;
import static com.smallchill.system.treasure.utils.CallBackUtils.*;

@RestController
@RequestMapping("/callback/WePay")
public class WePayCallbackController extends BaseController implements ConstShiro {
    /**
     * WePay 充值回调
     * @return
     */
    @PostMapping("/recharge")
    public String rechargeWePayCallback(@RequestParam Map<String,Object> param){
        // 验证签名
        if (param==null){
            return "fail";
        }
        LOGGER.error(param);
        Boolean temp = HttpClientUtils.Md5WePayVerification(param,WEPAY_CKEY);
        if (!temp){
            return "fail";
        }
        LOGGER.error("认证成功");
        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
        // 获取订单号
        String orderNum = params.getString("mchOrderNo");
        Blade blade = Blade.create(RechargeRecords.class);
        RechargeRecords rechargeRecords = blade.findFirstBy("orderNumber=#{orderNum}", CMap.init().set("orderNum", orderNum));
        if (rechargeRecords==null){
            return "success";
        }
        // 获取支付状态
        int result = params.getIntValue("tradeResult");
        GlobalDelayQueue globalDelayQueue = new GlobalDelayQueue();
        if (result==1){
//            synchronized (lock){
            if (rechargeRecords.getOrderStatus()==2){
                return "success";
            }
            successRecExecuted(orderNum,rechargeRecords);
//            }
        }else {
            // 从延迟队列中移除并且更新状态
            failRecExecuted(rechargeRecords);
        }
        return "success";
    }

    /**
     * WePay 兑换回调
     * @param param
     * @return
     */
    @PostMapping("/exchange")
    public String exchangeWePayCallback(@RequestParam Map<String,Object> param){
        // 验证签名
        if (param==null){
            return "fail";
        }
        LOGGER.error(param);
        Boolean temp = HttpClientUtils.Md5WePayVerification(param,WEPAY_PKEY);
        if (!temp){
            return "fail";
        }
        LOGGER.error("认证成功");
        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
        // 获取订单号
        String orderNum = params.getString("merTransferId");
        Blade blade = Blade.create(ExchangeReview.class);
        ExchangeReview exchangeReview =blade.findFirstBy("orderNumber = #{orderNumber}", CMap.init().set("orderNumber",orderNum));
        if (exchangeReview==null){
            return "success";
        }
        // 获取订单状态
        int result = params.getIntValue("tradeResult");
        if (result==1){
            if (exchangeReview.getStatus()==3||exchangeReview.getStatus()==4){
                return "success";
            }
            // 回调成功
            successExcExecuted(exchangeReview);
        }else if (result == 2 || result==3) {
            // 支付失败兑换变为支付失败
            exchangeReview.setStatus(6);
            exchangeReview.setEndTime(new Date());
            // 兑换失败，发送邮件是在退回的时候进行发送
        }else {
            return "success";
        }
        // 修改订单状态
        blade.update(exchangeReview);
        return "success";
    }
}
