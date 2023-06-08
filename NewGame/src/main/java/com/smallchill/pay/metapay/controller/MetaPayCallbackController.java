package com.smallchill.pay.metapay.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.RequestSignUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

import static com.smallchill.core.constant.ConstKey.PUBLIC_KEY1;
import static com.smallchill.system.treasure.utils.CallBackUtils.*;

@RestController
@RequestMapping("/callback/MetaPay")
public class MetaPayCallbackController extends BaseController implements ConstShiro {

    @PostMapping(value = "/recharge",consumes = "application/json")
    public String rechargeMetaCallback(@RequestBody Map<String,Object> param){
        if (param==null){
            return "fail";
        }
        // 验证签名
        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
        boolean temp = RequestSignUtil.verifySign(params, PUBLIC_KEY1);
        if (!temp){
            return "fail";
        }
        // 判断订单号是否存在
        Blade blade = Blade.create(RechargeRecords.class);
        String orderNum = params.getString("referenceNo");
        RechargeRecords rechargeRecords = blade.findFirstBy("orderNumber=#{orderNum}",
                CMap.init().set("orderNum",orderNum));
        if (rechargeRecords==null){
            return "SUCCESS";
        }
        int status = params.getIntValue("status");
        if (status==0){
            if (rechargeRecords.getOrderStatus()==2){
                return "SUCCESS";
            }
            successRecExecuted(orderNum, rechargeRecords);
        }else if (status==2){
            failRecExecuted(rechargeRecords);
        }
        return "SUCCESS";
    }

    /**
     * MetaPay兑换回调
     */
    @PostMapping(value = "/exchange",consumes = "application/json")
    public String exchangeMetaPayCallback(@RequestBody Map<String,Object> param){
        if (param==null){
            return "fail";
        }
        // 验证签名
        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
        boolean temp = RequestSignUtil.verifySign(params, PUBLIC_KEY1);
        if (!temp){
            return "fail";
        }
        // 获取订单号
        String orderNum = params.getString("referenceNo");
        // 根据订单号查询兑换订单
        Blade blade = Blade.create(ExchangeReview.class);
        ExchangeReview exchangeReview =blade.findFirstBy("orderNumber = #{orderNumber}", CMap.init().set("orderNumber",orderNum));
        int status = params.getIntValue("status");
        if (status==0){
            if (exchangeReview.getStatus()==3 || exchangeReview.getStatus() ==4){
                return "SUCCESS";
            }
            // 回调成功
            successExcExecuted(exchangeReview);
        }else if (status==2){
            // 支付失败兑换变为支付失败
            exchangeReview.setStatus(6);
            exchangeReview.setEndTime(new Date());
            // 兑换失败，发送邮件是在退回的时候进行发送
        }
        // 修改订单状态
        blade.update(exchangeReview);
        return "SUCCESS";
    }
}
