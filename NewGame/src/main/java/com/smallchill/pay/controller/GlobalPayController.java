package com.smallchill.pay.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.common.base.BaseController;
import com.smallchill.common.task.GlobalDelayQueue;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.pay.model.globalPay.GlobalPay;
import com.smallchill.pay.utils.bPayUtils.RSAUtils;
import com.smallchill.pay.utils.bPayUtils.SortUtils;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.Utils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

import static com.smallchill.system.treasure.utils.CallBackUtils.successExcExecuted;
import static com.smallchill.system.treasure.utils.CallBackUtils.successRecExecuted;

@RestController
@RequestMapping("/callback/GlobalPay")
public class GlobalPayController extends BaseController implements ConstShiro {
    @Resource
    private GlobalPay globalPay;
    @PostMapping(value = "/recharge",produces = MediaType.TEXT_PLAIN_VALUE)
    public String rechargeCallback(@RequestBody Map<String,Object> param){
        // 验证签名
        if (param==null){
            return "fail";
        }
        LOGGER.error(param);
        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
        String mcSign = params.remove("sign").toString();
        String sign = Utils.getSign(params,globalPay.getKey());
        if (mcSign.toUpperCase().equals(sign)){
            return "fail";
        }
        LOGGER.error("校验成功");
        // 获取订单号
        String orderNum = params.getString("tradeNo");
        Blade blade = Blade.create(RechargeRecords.class);
        RechargeRecords rechargeRecords = blade.findFirstBy("orderNumber=#{orderNum}", CMap.init().set("orderNum", orderNum));
        if (rechargeRecords==null){
            return "success";
        }
        // 获取订单状态
        int status = params.getIntValue("tradeStatus");
        GlobalDelayQueue globalDelayQueue = new GlobalDelayQueue();
        if (status==1){
            if (rechargeRecords.getOrderStatus()==2){
                return "success";
            }
            successRecExecuted(blade,orderNum,rechargeRecords,globalDelayQueue);
        }else if (status==0){
            // 从延迟队列中移除并且更新状态
            globalDelayQueue.cancelOrder(orderNum);
            rechargeRecords.setOrderStatus(3);
            rechargeRecords.setEndTime(new Date());
            blade.update(rechargeRecords);
        }
        return "success";
    }

    @PostMapping(value = "/exchange",produces = MediaType.TEXT_PLAIN_VALUE)
    public String exchangeAIPayCallback(@RequestBody Map<String,Object> param){
        // 验证签名
        if (param==null){
            return "fail";
        }
        LOGGER.error(param);
        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
        String mcSign = params.remove("sign").toString();
        String sign = Utils.getSign(params,globalPay.getKey());
        if (mcSign.toUpperCase().equals(sign)){
            return "fail";
        }
        LOGGER.error("校验成功");
        // 根据订单号查询兑换订单
        String orderNum = params.getString("tradeNo");
        Blade blade = Blade.create(ExchangeReview.class);
        ExchangeReview exchangeReview =blade.findFirstBy("orderNumber = #{orderNumber}", CMap.init().set("orderNumber",orderNum));
        if (exchangeReview==null){
            return "success";
        }
        // 获取订单状态
        // 0 -审核中 1- 审核成功 2- 审核失败 3- 代付中 4- 代付已转账 （1和4都为成功状态）
        int status = params.getIntValue("tradeStatus");
        if (status==1 || status==4){
            if (exchangeReview.getStatus()==3||exchangeReview.getStatus()==4){
                return "success";
            }
            // 回调成功
            successExcExecuted(exchangeReview);
            blade.update(exchangeReview);
        } else if (status==2){
            // 支付失败兑换变为支付失败
            exchangeReview.setStatus(6);
            exchangeReview.setEndTime(new Date());
            blade.update(exchangeReview);
        }
        // 修改订单状态
        return "success";
    }

}
