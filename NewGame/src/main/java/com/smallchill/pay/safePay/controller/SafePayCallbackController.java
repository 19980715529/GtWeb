package com.smallchill.pay.safePay.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.HttpClientUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.smallchill.system.treasure.utils.CallBackUtils.*;

@RestController
@RequestMapping("/callback/SafePay")
public class SafePayCallbackController extends BaseController implements ConstShiro {
    /**
     * 充值回调
     * @return
     */
    @PostMapping(value = "/recharge",consumes = "application/json")
    private String rechageSafeCallback(@RequestBody Map<String,Object> param){
        if (param == null){
            return "fail1";
        }
        Boolean b = HttpClientUtils.Md5SafeVerification(param);
        if (!b){
            return "fail2";
        }
        String order_no = param.get("order_no").toString();
        String status = param.get("status").toString();
        Map<String,Object> params = new HashMap<>();
        // 根据订单号查询兑换订单
        params.put("orderNumber",order_no);
        Blade blade = Blade.create(RechargeRecords.class);
        RechargeRecords rechargeRecords = blade.findFirstBy("orderNumber=#{orderNumber}", params);
        if (rechargeRecords == null){
            return "ok";
        }
        if ("success".equals(status)){
            // 判断状态已经改变为已经完成，就不在处理
            if (rechargeRecords.getOrderStatus()==2){
                return "ok";
            }
            successRecExecuted(order_no,rechargeRecords);
        } else if ("fail".equals(status)) {
            failRecExecuted(rechargeRecords);
        }
        return "ok";
    }

    /**
     * 兑换回调
     * @return
     */
    @PostMapping(value = "/exchange",consumes = "application/json")
    private String exchangeSafeCallback(@RequestBody Map<String,Object> param){
        if (param == null){
            return "fail";
        }
        Boolean b = HttpClientUtils.Md5SafeVerification(param);
        if (!b){
            return "fail";
        }
        String result =param.get("result").toString();
        String order_no = param.get("order_no").toString();
        // 渠道订单号
        String sys_no = param.get("sys_no").toString();
        Map<String, Object> map = new HashMap<>();
        map.put("orderNumber",order_no);
        // 根据订单号查询兑换订单
        Blade blade = Blade.create(ExchangeReview.class);
        ExchangeReview exchangeReview =blade.findFirstBy("orderNumber = #{orderNumber}", map);
        if (exchangeReview==null){
            return "fail";
        }
        if ("success".equals(result)){
            // 防止重复回调
            if (exchangeReview.getStatus()==3 || exchangeReview.getStatus()==4){
                return "ok";
            }
            successExcExecuted(exchangeReview);
        } else if ("fail".equals(result)) {
            // 支付失败兑换变为支付失败
            exchangeReview.setStatus(6);
            exchangeReview.setEndTime(new Date());
            // 兑换失败，发送邮件是在退回的时候进行发送
        }
        // 添加平台订单号
        exchangeReview.setPfOrderNum(sys_no);
        // 修改订单状态
        blade.update(exchangeReview);
        return "ok";
    }
}
