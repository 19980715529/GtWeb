package com.smallchill.pay.aipay.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.pay.aipay.model.AIPay;
import com.smallchill.pay.aipay.utils.AIPayUtils;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.SendHttp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

import static com.smallchill.system.treasure.utils.CallBackUtils.*;

@RestController
@RequestMapping("/callback/AIPay")
public class AIPayCallbackController extends BaseController implements ConstShiro {
    @Resource
    private AIPay aiPay;

    /**
     * AIPay 充值回调
     * @return
     */
    @PostMapping(value = "/recharge",consumes = "application/json")
    public String rechargeAIPayCallback(@RequestBody Map<String,Object> param){
        // 验证签名
        if (param==null){
            return "fail";
        }
        LOGGER.error(param);
        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
        String sign = AIPayUtils.getSign(params,aiPay.getKey());
        if (!params.getString("sign").equals(sign)){
            return "fail";
        }
        LOGGER.error("校验成功");
        // 获取订单号
        String orderNum = params.getString("merchantOrderId");
        Blade blade = Blade.create(RechargeRecords.class);
        RechargeRecords rechargeRecords = blade.findFirstBy("orderNumber=#{orderNum}", CMap.init().set("orderNum", orderNum));
        if (rechargeRecords==null){
            return "ok";
        }
        // 获取订单状态
        int status = params.getIntValue("status");
        if (status==1){
            if (rechargeRecords.getOrderStatus()==2){
                return "ok";
            }
            successRecExecuted(orderNum,rechargeRecords);
        }else if (status==2){
            // 从延迟队列中移除并且更新状态
            failRecExecuted(rechargeRecords);
        }else {
            return "ok";
        }
        return "ok";
    }
    /**
     * AIPay 兑换回调
     * @param param
     * @return
     */
    @PostMapping(value = "/exchange",consumes = "application/json")
    public String exchangeAIPayCallback(@RequestBody Map<String,Object> param){
        // 验证签名
        if (param==null){
            return "fail";
        }
        LOGGER.error(param);
        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
        String sign = SendHttp.getSign(params);
        if (!params.getString("sign").equals(sign)){
            return "fail";
        }
        LOGGER.error("校验成功");
        // 根据订单号查询兑换订单
        String orderNum = params.getString("merchantOrderId");
        Blade blade = Blade.create(ExchangeReview.class);
        ExchangeReview exchangeReview =blade.findFirstBy("orderNumber = #{orderNumber}", CMap.init().set("orderNumber",orderNum));
        if (exchangeReview==null){
            return "ok";
        }
        // 获取订单状态
        int status = params.getIntValue("status");
        if (status==1){
            if (exchangeReview.getStatus()==3||exchangeReview.getStatus()==4){
                return "ok";
            }
            // 回调成功
            successExcExecuted(exchangeReview);
        } else if (status==2) {
            // 支付失败兑换变为支付失败
            exchangeReview.setStatus(6);
            exchangeReview.setEndTime(new Date());
            // 兑换失败，发送邮件是在退回的时候进行发送
        }else {
            // 不做处理
            return "ok";
        }
        // 修改订单状态
        blade.update(exchangeReview);
        return "ok";
    }
}
