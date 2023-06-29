package com.smallchill.pay.payplus.cotroller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.pay.payplus.model.PayPlus;
import com.smallchill.pay.payplus.model.SuperPayPlus;
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

import static com.smallchill.system.treasure.utils.CallBackUtils.*;

@RestController
@RequestMapping("/callback/PayPlus")
public class PayPlusCallbackController extends BaseController implements ConstShiro {
    @Resource
    private PayPlus payPlus;
    @Resource
    private SuperPayPlus superPayPlus;
    @PostMapping("/recharge")
    public String rechargeLetsPayCallback(@RequestParam Map<String,Object> param){
        // 验证签名
        if (param==null){
            return "fail";
        }
//        LOGGER.error(param);
        String mchId = param.get("mchId").toString();
        if (mchId==null){
            return "fail";
        }
        Boolean temp = HttpClientUtils.Md5OmoOrLetsPayVerification(param,payPlus.key);
        if (!temp){
            return "fail";
        }
//        LOGGER.error("认证成功");
        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
        // 获取订单号
        String orderNum = params.getString("orderNo");
        Blade blade = Blade.create(RechargeRecords.class);
        RechargeRecords rechargeRecords = blade.findFirstBy("orderNumber=#{orderNum}", CMap.init().set("orderNum", orderNum));
        if (rechargeRecords==null){
            return "success";
        }
        // 1 支付中,2 成功,5 失效,-1 失败
        int status = params.getIntValue("status");
        if (status==2){
            if (rechargeRecords.getOrderStatus()==2){
                return "success";
            }
            successRecExecuted(orderNum,rechargeRecords);
        }else if (status==5||status==-1){
            // 从延迟队列中移除并且更新状态
            failRecExecuted(rechargeRecords);
        }
        return "success";
    }

    @PostMapping("/exchange")
    public String exchangeLetsPayCallback(@RequestParam Map<String,Object> param){
        // 验证签名
        if (param==null){
            return "fail";
        }
//        LOGGER.error(param);
        // 去除msg,不参与签名
        String msg = param.remove("msg").toString();
        String mchId = param.get("mchId").toString();
        if (mchId==null){
            return "fail";
        }
        String key="";
        if (payPlus.getMchId().equals(mchId)){
            key= payPlus.getKey();
        }else {
            key= superPayPlus.getKey();
        }
        Boolean temp = HttpClientUtils.Md5OmoOrLetsPayVerification(param,key);
        if (!temp){
            return "fail";
        }
//        LOGGER.error("认证成功");
        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
        // 获取订单号
        String orderNum = params.getString("mchTransNo");
        Blade blade = Blade.create(ExchangeReview.class);
        ExchangeReview exchangeReview =blade.findFirstBy("orderNumber = #{orderNumber}", CMap.init().set("orderNumber",orderNum));
        if (exchangeReview==null){
            return "success";
        }
        // 获取订单状态 2； 成功，3：失败 1:处理中
        int status = params.getIntValue("status");
        if (status==2){
//            synchronized (lock){
            if (exchangeReview.getStatus()==3||exchangeReview.getStatus()==4){
                return "success";
            }
            // 回调成功
            successExcExecuted(exchangeReview);
//            }
        }else if (status==3){
            // 支付失败兑换变为支付失败
            exchangeReview.setStatus(6);
            exchangeReview.setEndTime(new Date());
            exchangeReview.setMsg(msg);
        }else {
            // 处理中不操作
            exchangeReview.setEndTime(new Date());
        }
        // 修改订单状态
        blade.update(exchangeReview);
        return "success";
    }
}
