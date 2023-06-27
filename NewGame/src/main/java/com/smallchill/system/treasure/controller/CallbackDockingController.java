package com.smallchill.system.treasure.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.common.base.BaseController;
import com.smallchill.common.task.GlobalDelayQueue;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import static com.smallchill.core.constant.ConstKey.*;
import static com.smallchill.system.treasure.utils.CallBackUtils.*;

import com.smallchill.pay.payplus.model.PayPlus;
import com.smallchill.pay.payplus.model.SuperPayPlus;
import com.smallchill.pay.rarPay.model.RarPay;
import com.smallchill.system.service.ExchangeReviewService;
import com.smallchill.system.service.RechargeRecordsService;
import com.smallchill.system.treasure.model.*;
import com.smallchill.system.treasure.utils.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @classNameExchangeDockingController
 * @Date 2023/2/25 12:42
 * @Version 1.0 接口回调接口
 **/
@RestController
@RequestMapping("/callback")
public class CallbackDockingController extends BaseController implements ConstShiro {

    @PostMapping(value = "/test",consumes = "application/json")
    public String rechargeTest(@RequestBody Map<String,Object> param){
        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
        // 判断订单号是否存在
        Blade blade = Blade.create(RechargeRecords.class);
        String orderNum = params.getString("orderNum");
        RechargeRecords rechargeRecords = blade.findFirstBy("orderNumber=#{orderNum}",
                CMap.init().set("orderNum",orderNum));
        if (rechargeRecords==null){
            return "SUCCESS";
        }
        // 获取平台订单
        int status = params.getIntValue("status");
        if (status==0){
            if (rechargeRecords.getOrderStatus()==2){
                return "SUCCESS";
            }
            successRecExecuted(orderNum, rechargeRecords);
        }else if (status==2){
            // 从延迟队列中移除并且更新状态
            failRecExecuted(rechargeRecords);
        }
        return "SUCCESS";
    }

    @PostMapping(value = "/exchange/test",consumes = "application/json")
    public String exchangeMetaPayCallback(@RequestBody Map<String,Object> param){
        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
        String orderNum = params.getString("orderNum");
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
