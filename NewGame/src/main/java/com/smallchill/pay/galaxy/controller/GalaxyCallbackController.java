package com.smallchill.pay.galaxy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.pay.rpay.model.RPay;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.HttpClientUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

import static com.smallchill.core.constant.ConstKey.CLOUDPAY_KEY;
import static com.smallchill.core.constant.ConstKey.MHDPAY_KEY;
import static com.smallchill.system.treasure.utils.CallBackUtils.*;

@RestController
@RequestMapping("/callback/Galaxy")
public class GalaxyCallbackController extends BaseController implements ConstShiro {

    @Resource
    private RPay rPay;
    /**
     * 银河系统充值回调
     * @param param
     * @return
     */
    @PostMapping("/recharge")
    public String rechargeGalaxyCallback(@RequestParam Map<String,Object> param){
        // 验证签名
        if (param==null){
            return "fail";
        }
        LOGGER.error(param);
//        String merchant = param.get("merchant").toString();
        boolean temp= HttpClientUtils.Md5GalaxyVerification(param,rPay.key);
        if (!temp){
            return "fail";
        }
        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
        LOGGER.error(params);
        // 获取订单号
        String orderNum = params.getString("order_id");
        Blade blade = Blade.create(RechargeRecords.class);
        RechargeRecords rechargeRecords = blade.findFirstBy("orderNumber=#{orderNum}", CMap.init().set("orderNum", orderNum));
        if (rechargeRecords==null){
            return "success";
        }
        // 获取支付状态 5:成功，3：失败
        int status = params.getIntValue("status");
        if (status==5){
            if (rechargeRecords.getOrderStatus()==2){
                return "success";
            }
            successRecExecuted(orderNum,rechargeRecords);
        }else {
            // 从延迟队列中移除并且更新状态
            failRecExecuted(rechargeRecords);
        }
        return "success";
    }

    @PostMapping("/exchange")
    public String exchangeGalaxyCallback(@RequestParam Map<String,Object> param){
        // 验证签名
        if (param==null){
            return "fail";
        }
        LOGGER.error(param);
//        String merchant = param.get("merchant").toString();
        boolean temp= HttpClientUtils.Md5GalaxyVerification(param,rPay.key);
        if (!temp){
            return "fail";
        }
        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
        LOGGER.error(params);
        // 获取订单号
        String orderNum = params.getString("order_id");
        Blade blade = Blade.create(ExchangeReview.class);
        ExchangeReview exchangeReview =blade.findFirstBy("orderNumber = #{orderNumber}", CMap.init().set("orderNumber",orderNum));
        if (exchangeReview==null){
            return "success";
        }
        // 获取订单状态 5； 成功，3：失败
        int status = params.getIntValue("status");
        if (status==5){
            if (exchangeReview.getStatus()==3||exchangeReview.getStatus()==4){
                return "success";
            }
            // 回调成功
            successExcExecuted(exchangeReview);
        }else {
            // 支付失败兑换变为支付失败
            exchangeReview.setStatus(6);
            exchangeReview.setEndTime(new Date());
            // 兑换失败，发送邮件是在退回的时候进行发送
            String remark = params.getString("remark");
            exchangeReview.setMsg(remark);
        }
        // 修改订单状态
        blade.update(exchangeReview);
        return "success";
    }
}
