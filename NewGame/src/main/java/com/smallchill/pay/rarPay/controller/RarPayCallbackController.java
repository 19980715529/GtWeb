package com.smallchill.pay.rarPay.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.pay.rarPay.model.RarPay;
import com.smallchill.system.service.ExchangeReviewService;
import com.smallchill.system.service.RechargeRecordsService;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.HttpClientUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.smallchill.system.treasure.utils.CallBackUtils.*;

@RestController
@RequestMapping("/callback/RarPay")
public class RarPayCallbackController extends BaseController implements ConstShiro {

    @Resource
    private RarPay rarPay;
    @Resource
    private RechargeRecordsService rechargeRecordsService;

    @Resource
    private ExchangeReviewService exchangeReviewService;
    /**
     * 充值回调接口
     * @return
     */
    @PostMapping("/recharge")
    @Transactional
    public String callback(@RequestParam Map<String,Object> param){
        // 回调需要执行RechargeRecord函数
        if (param==null){
            return "fail";
        }
        LOGGER.error(param);
        Boolean b = HttpClientUtils.Md5RarpVerification(param,rarPay);
        if (!b){
            return "fail";
        }
        String orderNum = param.get("mchOrdernum").toString();
        // 订单状态:0=未支付;10=支付中;20=支付成功;30=支付失败。一般成功、失败时才会回调
        String status = param.get("status").toString();
        // 根据订单id查询用户订单
        Map<String, Object> map = new HashMap<>();
        map.put("orderNumber",orderNum);
        // 根据订单号查询兑换订单,不应该从队列中取出，可能回调时间比较常大于5分钟
        RechargeRecords rechargeRecords = rechargeRecordsService.findFirstBy("orderNumber=#{orderNumber}", map);
        if (rechargeRecords==null){
            return "fail";
        }
        // 判断充值是否成功  订单状态:0=未支付;10=支付中;20=支付成功;30=支付失败。一般成功、失败时才会回调   订单状态，1：待支付，2：已完成，3：已失败
        if(Integer.parseInt(status)==20){
            // 判断状态已经改变为已经完成，就不在处理
            if (rechargeRecords.getOrderStatus()==2){
                return "success";
            }
            successRecExecuted(orderNum,rechargeRecords);
        }else if(Integer.parseInt(status)==30){
            // 充值失败将状态修改为已关闭
            failRecExecuted(rechargeRecords);
        }else{
            // 将充值中和为充值的订单状态不改变
            return "success";
        }
        return "success";
    }

    // 兑换回调
    @PostMapping(value = "/exchange")
    public String exchangeCallback(@RequestParam Map<String,Object> param) {
        if (param==null){
            return "fail";
        }
        LOGGER.error(param);
        Boolean b = HttpClientUtils.Md5RarpVerification(param,rarPay);
        if (!b){
            return "fail";
        }
        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
        String orderNum = params.getString("mchOrdernum");
        // 订单状态:0=未支付;10=支付中;20=支付成功;30=支付失败。一般成功、失败时才会回调
        int status = params.getIntValue("status");
        // 平台订单号
        String ordernum = params.getString("ordernum");
        // 根据订单号查询兑换订单
        ExchangeReview exchangeReview = exchangeReviewService.findFirstBy("orderNumber=#{orderNumber}", CMap.init().set("orderNumber",orderNum));
        if (exchangeReview == null) {
            return "";
        }
        // 存储平台订单号
        exchangeReview.setPfOrderNum(ordernum);
        // 判断充值是否成功
        if (status == 20) {
            // 判断状态已经改变为已经完成，就不在处理
            if (exchangeReview.getStatus()==3 || exchangeReview.getStatus() ==4){
                return "success";
            }
            successExcExecuted(exchangeReview);
        } else if (status == 30) {
            // 支付失败兑换变为支付失败
            exchangeReview.setStatus(6);
            exchangeReview.setEndTime(new Date());
        }
        // 修改订单状态
        exchangeReviewService.update(exchangeReview);
        return "success";
    }
}
