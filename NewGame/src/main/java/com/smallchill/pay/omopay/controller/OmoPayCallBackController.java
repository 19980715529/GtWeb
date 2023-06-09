package com.smallchill.pay.omopay.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.pay.omopay.model.OmoPay;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.HttpClientUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

import static com.smallchill.core.constant.ConstKey.OMOM_KEY;
import static com.smallchill.system.treasure.utils.CallBackUtils.*;

@RestController
@RequestMapping("/callback/OmoPay")
public class OmoPayCallBackController extends BaseController implements ConstShiro {

    @Resource
    private OmoPay omoPay;
    /**
     * OMOM 充值回调
     */
    @PostMapping(value = "/recharge",consumes = "application/json",produces = MediaType.TEXT_PLAIN_VALUE)
    public String recharge(@RequestBody Map<String,Object> param){
        // 验证签名
        if (param==null){
            return "fail";
        }
        LOGGER.error(param);
        Boolean temp = HttpClientUtils.Md5OmoOrLetsPayVerification(param,omoPay.getKey());
        if (!temp){
            return "fail";
        }
        LOGGER.error("校验成功");
        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
        // 获取订单号
        String orderNum = params.getString("orderid");
        // 获取平台订单号
        String PfOrderNum = params.getString("transaction_id");
        Blade blade = Blade.create(RechargeRecords.class);
        RechargeRecords rechargeRecords = blade.findFirstBy("orderNumber=#{orderNum}", CMap.init().set("orderNum", orderNum));
        if (rechargeRecords==null){
            return "ok";
        }
        rechargeRecords.setPfOrderNum(PfOrderNum);
        // 获取状态
        String status = params.getString("returncode");
        if ("00".equals(status)){
            if (rechargeRecords.getOrderStatus()==2){
                return "ok";
            }
            successRecExecuted(orderNum,rechargeRecords);
        }else {
            // 从延迟队列中移除并且更新状态
            failRecExecuted(rechargeRecords);
        }
        return "ok";
    }
    /**
     * OMOM 兑换回调
     */
    @PostMapping(value = "/exchange",consumes = "application/json",produces = MediaType.TEXT_PLAIN_VALUE)
    public String exchange(@RequestBody Map<String,Object> param){
        if (param==null){
            return "fail";
        }
        LOGGER.error(param);
        Boolean temp = HttpClientUtils.Md5OmoOrLetsPayVerification(param,omoPay.getKey());
        if (!temp){
            return "fail";
        }
        LOGGER.error("校验成功");
        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
        // 获取订单号
        String orderNum = params.getString("orderid");
        // 根据订单号查询兑换订单
        Blade blade = Blade.create(ExchangeReview.class);
        ExchangeReview exchangeReview =blade.findFirstBy("orderNumber = #{orderNumber}", CMap.init().set("orderNumber",orderNum));
        if (exchangeReview==null){
            return "ok";
        }
        // 获取平台订单号
        int code = params.getIntValue("returncode");
        if (code==2){
            if (exchangeReview.getStatus()==3 || exchangeReview.getStatus()==4){
                return "ok";
            }
            // 回调成功
            successExcExecuted(exchangeReview);
        }else if (code==3){
            // 支付失败兑换变为支付失败
            exchangeReview.setStatus(6);
            exchangeReview.setEndTime(new Date());
            // 兑换失败，发送邮件是在退回的时候进行发送
        }else {
            // 待处理中的不做如何处理
            return "ok";
        }
        // 修改订单状态
        blade.update(exchangeReview);
        return "ok";
    }
}
