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
    @Resource
    private RechargeRecordsService rechargeRecordsService;
    @Resource
    private ExchangeReviewService exchangeReviewService;

    /**
     * 充值回调接口
     * @return
     */
//    @PostMapping("/recharge_rarp_callback")
//    @Transactional
//    public String callback(@RequestParam Map<String,Object> param){
//        // 回调需要执行RechargeRecord函数
//        if (param==null){
//            return "fail";
//        }
//        LOGGER.error(param);
//        Boolean b = HttpClientUtils.Md5RarpVerification(param,rarPay);
//        if (!b){
//            return "fail";
//        }
//        String orderNum = param.get("mchOrdernum").toString();
//        // 订单状态:0=未支付;10=支付中;20=支付成功;30=支付失败。一般成功、失败时才会回调
//        String status = param.get("status").toString();
//        // 根据订单id查询用户订单
//        Map<String, Object> map = new HashMap<>();
//        map.put("orderNumber",orderNum);
//        // 根据订单号查询兑换订单,不应该从队列中取出，可能回调时间比较常大于5分钟
//        RechargeRecords rechargeRecords = rechargeRecordsService.findFirstBy("orderNumber=#{orderNumber}", map);
//        if (rechargeRecords==null){
//            return "fail";
//        }
//        // 判断充值是否成功  订单状态:0=未支付;10=支付中;20=支付成功;30=支付失败。一般成功、失败时才会回调   订单状态，1：待支付，2：已完成，3：已失败
//        if(Integer.parseInt(status)==20){
//            // 判断状态已经改变为已经完成，就不在处理
//            if (rechargeRecords.getOrderStatus()==2){
//                return "success";
//            }
//            successRecExecuted(orderNum,rechargeRecords);
//        }else if(Integer.parseInt(status)==30){
//            // 充值失败将状态修改为已关闭
//            failRecExecuted(rechargeRecords);
//        }else{
//            // 将充值中和为充值的订单状态不改变
//            return "success";
//        }
//        return "success";
//    }


//    // 兑换回调
//    @PostMapping(value = "/exchange_rarp_callback")
//    public String exchangeCallback(@RequestParam Map<String,Object> param) {
//        if (param==null){
//            return "fail";
//        }
//        LOGGER.error(param);
//        Boolean b = HttpClientUtils.Md5RarpVerification(param,rarPay);
//        if (!b){
//            return "fail";
//        }
//        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
//        String orderNum = params.getString("mchOrdernum");
//        // 订单状态:0=未支付;10=支付中;20=支付成功;30=支付失败。一般成功、失败时才会回调
//        int status = params.getIntValue("status");
//        // 平台订单号
//        String ordernum = params.getString("ordernum");
//        // 根据订单号查询兑换订单
//        ExchangeReview exchangeReview = exchangeReviewService.findFirstBy("orderNumber=#{orderNumber}", CMap.init().set("orderNumber",orderNum));
//        if (exchangeReview == null) {
//            return "";
//        }
//        // 存储平台订单号
//        exchangeReview.setPfOrderNum(ordernum);
//        // 判断充值是否成功
//        if (status == 20) {
//            // 判断状态已经改变为已经完成，就不在处理
//            if (exchangeReview.getStatus()==3 || exchangeReview.getStatus() ==4){
//                return "success";
//            }
//            successExcExecuted(exchangeReview);
//        } else if (status == 30) {
//            // 支付失败兑换变为支付失败
//            exchangeReview.setStatus(6);
//            exchangeReview.setEndTime(new Date());
//        }
//        // 修改订单状态
//        exchangeReviewService.update(exchangeReview);
//        return "success";
//    }

    /**
     * 充值回调
     * @return
     */
//    @PostMapping(value = "/recharge_safa_callback",consumes = "application/json")
//    private String rechageSafeCallback(@RequestBody Map<String,Object> param){
//        if (param == null){
//            return "fail1";
//        }
//        Boolean b = HttpClientUtils.Md5SafeVerification(param);
//        if (!b){
//            return "fail2";
//        }
//        String order_no = param.get("order_no").toString();
//        String status = param.get("status").toString();
//        Map<String,Object> params = new HashMap<>();
//        // 根据订单号查询兑换订单
//        params.put("orderNumber",order_no);
//        Blade blade = Blade.create(RechargeRecords.class);
//        RechargeRecords rechargeRecords = blade.findFirstBy("orderNumber=#{orderNumber}", params);
//        if (rechargeRecords == null){
//            return "ok";
//        }
//        if ("success".equals(status)){
//            // 判断状态已经改变为已经完成，就不在处理
//                if (rechargeRecords.getOrderStatus()==2){
//                    return "ok";
//                }
//                successRecExecuted(order_no,rechargeRecords);
//        } else if ("fail".equals(status)) {
//            failRecExecuted(rechargeRecords);
//        }
//        return "ok";
//    }

    /**
     * 兑换回调
     * @return
     */
//    @PostMapping(value = "/exchange_safe_callback",consumes = "application/json")
//    private String exchangeSafeCallback(@RequestBody Map<String,Object> param){
//        if (param == null){
//            return "fail";
//        }
//        Boolean b = HttpClientUtils.Md5SafeVerification(param);
//        if (!b){
//            return "fail";
//        }
//        String result =param.get("result").toString();
//        String order_no = param.get("order_no").toString();
//        // 渠道订单号
//        String sys_no = param.get("sys_no").toString();
//        Map<String, Object> map = new HashMap<>();
//        map.put("orderNumber",order_no);
//        // 根据订单号查询兑换订单
//        Blade blade = Blade.create(ExchangeReview.class);
//        ExchangeReview exchangeReview =blade.findFirstBy("orderNumber = #{orderNumber}", map);
//        if (exchangeReview==null){
//            return "fail";
//        }
//        if ("success".equals(result)){
//            // 防止重复回调
//            if (exchangeReview.getStatus()==3 || exchangeReview.getStatus()==4){
//                return "ok";
//            }
//            successExcExecuted(exchangeReview);
//        } else if ("fail".equals(result)) {
//            // 支付失败兑换变为支付失败
//            exchangeReview.setStatus(6);
//            exchangeReview.setEndTime(new Date());
//            // 兑换失败，发送邮件是在退回的时候进行发送
//        }
//        // 添加平台订单号
//        exchangeReview.setPfOrderNum(sys_no);
//        // 修改订单状态
//        blade.update(exchangeReview);
//        return "ok";
//    }



    /**
     * MetaPay充值回调
     */
//    @PostMapping(value = "/recharge_meta_callback",consumes = "application/json")
//    public String rechargeMetaCallback(@RequestBody Map<String,Object> param){
//        if (param==null){
//            return "fail";
//        }
//        // 验证签名
//        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
//        boolean temp = RequestSignUtil.verifySign(params, PUBLIC_KEY1);
//        if (!temp){
//            return "fail";
//        }
//        // 判断订单号是否存在
//        Blade blade = Blade.create(RechargeRecords.class);
//        String orderNum = params.getString("referenceNo");
//        RechargeRecords rechargeRecords = blade.findFirstBy("orderNumber=#{orderNum}",
//                CMap.init().set("orderNum",orderNum));
//        if (rechargeRecords==null){
//            return "SUCCESS";
//        }
//        int status = params.getIntValue("status");
//        if (status==0){
//            if (rechargeRecords.getOrderStatus()==2){
//                return "SUCCESS";
//            }
//            successRecExecuted(orderNum, rechargeRecords);
//        }else if (status==2){
//            failRecExecuted(rechargeRecords);
//        }
//        return "SUCCESS";
//    }

//    @PostMapping(value = "/test",consumes = "application/json")
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

//    @PostMapping(value = "/exchange/test",consumes = "application/json")
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

    /**
     * MetaPay兑换回调
     */
//    @PostMapping(value = "/exchange_meta_pay_callback",consumes = "application/json")
//    public String exchangeMetaPayCallback(@RequestBody Map<String,Object> param){
//        if (param==null){
//            return "fail";
//        }
//        // 验证签名
//        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
//        boolean temp = RequestSignUtil.verifySign(params, PUBLIC_KEY1);
//        if (!temp){
//            return "fail";
//        }
//        // 获取订单号
//        String orderNum = params.getString("referenceNo");
//        // 根据订单号查询兑换订单
//        Blade blade = Blade.create(ExchangeReview.class);
//        ExchangeReview exchangeReview =blade.findFirstBy("orderNumber = #{orderNumber}", CMap.init().set("orderNumber",orderNum));
//        int status = params.getIntValue("status");
//        if (status==0){
//            if (exchangeReview.getStatus()==3 || exchangeReview.getStatus() ==4){
//                return "SUCCESS";
//            }
//            // 回调成功
//            successExcExecuted(exchangeReview);
//        }else if (status==2){
//            // 支付失败兑换变为支付失败
//            exchangeReview.setStatus(6);
//            exchangeReview.setEndTime(new Date());
//            // 兑换失败，发送邮件是在退回的时候进行发送
//        }
//        // 修改订单状态
//        blade.update(exchangeReview);
//        return "SUCCESS";
//    }
//    /**
//     * OMOM 充值回调
//     */
//    @PostMapping(value = "/recharge_omom_callback",consumes = "application/json")
//    public String rechargeOmomCallback(@RequestBody Map<String,Object> param){
//        // 验证签名
//        if (param==null){
//            return "fail";
//        }
//        LOGGER.error(param);
//        Boolean temp = HttpClientUtils.Md5OmoOrLetsPayVerification(param,OMOM_KEY);
//        if (!temp){
//            return "fail";
//        }
//        LOGGER.error("校验成功");
//        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
//        // 获取订单号
//        String orderNum = params.getString("orderid");
//        // 获取平台订单号
//        String PfOrderNum = params.getString("transaction_id");
//        Blade blade = Blade.create(RechargeRecords.class);
//        RechargeRecords rechargeRecords = blade.findFirstBy("orderNumber=#{orderNum}", CMap.init().set("orderNum", orderNum));
//        if (rechargeRecords==null){
//            return "ok";
//        }
//        rechargeRecords.setPfOrderNum(PfOrderNum);
//        // 获取状态
//        String status = params.getString("returncode");
//        if ("00".equals(status)){
//                if (rechargeRecords.getOrderStatus()==2){
//                    return "ok";
//                }
//                successRecExecuted(orderNum,rechargeRecords);
//        }else {
////            // 从延迟队列中移除并且更新状态
////            GlobalDelayQueue.cancelOrder(orderNum);
////            rechargeRecords.setOrderStatus(3);
////            rechargeRecords.setEndTime(new Date());
////            blade.update(rechargeRecords);
//            failRecExecuted(rechargeRecords);
//        }
//        return "ok";
//    }
//    /**
//     * OMOM 兑换回调
//     */
//    @PostMapping(value = "/exchange_omom_callback",consumes = "application/json")
//    public String exchangeOmomCallback(@RequestBody Map<String,Object> param){
//        if (param==null){
//            return "fail";
//        }
//        LOGGER.error(param);
//        Boolean temp = HttpClientUtils.Md5OmoOrLetsPayVerification(param,OMOM_KEY);
//        if (!temp){
//            return "fail";
//        }
//        LOGGER.error("校验成功");
//        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
//        // 获取订单号
//        String orderNum = params.getString("orderid");
//        // 根据订单号查询兑换订单
//        Blade blade = Blade.create(ExchangeReview.class);
//        ExchangeReview exchangeReview =blade.findFirstBy("orderNumber = #{orderNumber}", CMap.init().set("orderNumber",orderNum));
//        if (exchangeReview==null){
//            return "ok";
//        }
//        // 获取平台订单号
//        int code = params.getIntValue("returncode");
//        if (code==2){
//                if (exchangeReview.getStatus()==3 || exchangeReview.getStatus()==4){
//                    return "ok";
//                }
//                // 回调成功
//                successExcExecuted(exchangeReview);
//        }else if (code==3){
//            // 支付失败兑换变为支付失败
//            exchangeReview.setStatus(6);
//            exchangeReview.setEndTime(new Date());
//            // 兑换失败，发送邮件是在退回的时候进行发送
//        }else {
//            // 待处理中的不做如何处理
//            return "ok";
//        }
//        // 修改订单状态
//        blade.update(exchangeReview);
//        return "ok";
//    }

    /**
     * AIPay 充值回调
     * @return
     */
//    @PostMapping(value = "/recharge_AIPay_callback",consumes = "application/json")
//    public String rechargeAIPayCallback(@RequestBody Map<String,Object> param){
//        // 验证签名
//        if (param==null){
//            return "fail";
//        }
//        LOGGER.error(param);
//        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
//        String sign = SendHttp.getSign(params);
//        if (!params.getString("sign").equals(sign)){
//            return "fail";
//        }
//        LOGGER.error("校验成功");
//        // 获取订单号
//        String orderNum = params.getString("merchantOrderId");
//        Blade blade = Blade.create(RechargeRecords.class);
//        RechargeRecords rechargeRecords = blade.findFirstBy("orderNumber=#{orderNum}", CMap.init().set("orderNum", orderNum));
//        if (rechargeRecords==null){
//            return "ok";
//        }
//        // 获取订单状态
//        int status = params.getIntValue("status");
//        if (status==1){
//                if (rechargeRecords.getOrderStatus()==2){
//                    return "ok";
//                }
//                successRecExecuted(orderNum,rechargeRecords);
//        }else if (status==2){
//            // 从延迟队列中移除并且更新状态
//            failRecExecuted(rechargeRecords);
//        }else {
//            return "ok";
//        }
//        return "ok";
//    }
//
//    /**
//     * AIPay 兑换回调
//     * @param param
//     * @return
//     */
//    @PostMapping(value = "/exchange_AIPay_callback",consumes = "application/json")
//    public String exchangeAIPayCallback(@RequestBody Map<String,Object> param){
//        // 验证签名
//        if (param==null){
//            return "fail";
//        }
//        LOGGER.error(param);
//        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
//        String sign = SendHttp.getSign(params);
//        if (!params.getString("sign").equals(sign)){
//            return "fail";
//        }
//        LOGGER.error("校验成功");
//        // 根据订单号查询兑换订单
//        String orderNum = params.getString("merchantOrderId");
//        Blade blade = Blade.create(ExchangeReview.class);
//        ExchangeReview exchangeReview =blade.findFirstBy("orderNumber = #{orderNumber}", CMap.init().set("orderNumber",orderNum));
//        if (exchangeReview==null){
//            return "ok";
//        }
//        // 获取订单状态
//        int status = params.getIntValue("status");
//        if (status==1){
////            synchronized (lock){
//                if (exchangeReview.getStatus()==3||exchangeReview.getStatus()==4){
//                    return "ok";
//                }
//                // 回调成功
//                successExcExecuted(exchangeReview);
////            }
//        } else if (status==2) {
//            // 支付失败兑换变为支付失败
//            exchangeReview.setStatus(6);
//            exchangeReview.setEndTime(new Date());
//            // 兑换失败，发送邮件是在退回的时候进行发送
//        }else {
//            // 不做处理
//            return "ok";
//        }
//        // 修改订单状态
//        blade.update(exchangeReview);
//        return "ok";
//    }

//    /**
//     * WePay 充值回调
//     * @return
//     */
//    @PostMapping("/recharge_WePay_callback")
//    public String rechargeWePayCallback(@RequestParam Map<String,Object> param){
//        // 验证签名
//        if (param==null){
//            return "fail";
//        }
//        LOGGER.error(param);
//        Boolean temp = HttpClientUtils.Md5WePayVerification(param,WEPAY_CKEY);
//        if (!temp){
//            return "fail";
//        }
//        LOGGER.error("认证成功");
//        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
//        // 获取订单号
//        String orderNum = params.getString("mchOrderNo");
//        Blade blade = Blade.create(RechargeRecords.class);
//        RechargeRecords rechargeRecords = blade.findFirstBy("orderNumber=#{orderNum}", CMap.init().set("orderNum", orderNum));
//        if (rechargeRecords==null){
//            return "success";
//        }
//        // 获取支付状态
//        int result = params.getIntValue("tradeResult");
//        GlobalDelayQueue globalDelayQueue = new GlobalDelayQueue();
//        if (result==1){
////            synchronized (lock){
//                if (rechargeRecords.getOrderStatus()==2){
//                    return "success";
//                }
//                successRecExecuted(orderNum,rechargeRecords);
////            }
//        }else {
//            // 从延迟队列中移除并且更新状态
////            globalDelayQueue.cancelOrder(orderNum);
////            rechargeRecords.setOrderStatus(3);
////            rechargeRecords.setEndTime(new Date());
////            blade.update(rechargeRecords);
//            failRecExecuted(rechargeRecords);
//        }
//        return "success";
//    }


//    /**
//     * WePay 兑换回调
//     * @param param
//     * @return
//     */
//    @PostMapping("/exchange_WePay_callback")
//    public String exchangeWePayCallback(@RequestParam Map<String,Object> param){
//        // 验证签名
//        if (param==null){
//            return "fail";
//        }
//        LOGGER.error(param);
//        Boolean temp = HttpClientUtils.Md5WePayVerification(param,WEPAY_PKEY);
//        if (!temp){
//            return "fail";
//        }
//        LOGGER.error("认证成功");
//        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
//        // 获取订单号
//        String orderNum = params.getString("merTransferId");
//        Blade blade = Blade.create(ExchangeReview.class);
//        ExchangeReview exchangeReview =blade.findFirstBy("orderNumber = #{orderNumber}", CMap.init().set("orderNumber",orderNum));
//        if (exchangeReview==null){
//            return "success";
//        }
//        // 获取订单状态
//        int result = params.getIntValue("tradeResult");
//        if (result==1){
//                if (exchangeReview.getStatus()==3||exchangeReview.getStatus()==4){
//                    return "success";
//                }
//                // 回调成功
//                successExcExecuted(exchangeReview);
//        }else if (result == 2 || result==3) {
//            // 支付失败兑换变为支付失败
//            exchangeReview.setStatus(6);
//            exchangeReview.setEndTime(new Date());
//            // 兑换失败，发送邮件是在退回的时候进行发送
//        }else {
//            return "success";
//        }
//        // 修改订单状态
//        blade.update(exchangeReview);
//        return "success";
//    }

    /**
     * 银河系统充值回调
     * @param param
     * @return
     */
//    @PostMapping("/recharge_galaxy_callback")
//    public String rechargeGalaxyCallback(@RequestParam Map<String,Object> param){
//        // 验证签名
//        if (param==null){
//            return "fail";
//        }
//        LOGGER.error(param);
//        String merchant = param.get("merchant").toString();
//        boolean temp = false;
//        if ("88052".equals(merchant)){
//            temp= HttpClientUtils.Md5GalaxyVerification(param,CLOUDPAY_KEY);
//        }else {
//            temp= HttpClientUtils.Md5GalaxyVerification(param,MHDPAY_KEY);
//        }
//        if (!temp){
//            return "fail";
//        }
//        LOGGER.error("认证成功");
//        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
//        // 获取订单号
//        String orderNum = params.getString("order_id");
//        Blade blade = Blade.create(RechargeRecords.class);
//        RechargeRecords rechargeRecords = blade.findFirstBy("orderNumber=#{orderNum}", CMap.init().set("orderNum", orderNum));
//        if (rechargeRecords==null){
//            return "success";
//        }
//        // 获取支付状态 5:成功，3：失败
//        int status = params.getIntValue("status");
//        if (status==5){
//                if (rechargeRecords.getOrderStatus()==2){
//                    return "success";
//                }
//                successRecExecuted(orderNum,rechargeRecords);
//        }else {
//            // 从延迟队列中移除并且更新状态
//            failRecExecuted(rechargeRecords);
//        }
//        return "success";
//    }

//    @PostMapping("/exchange_galaxy_callback")
//    public String exchangeGalaxyCallback(@RequestParam Map<String,Object> param){
//        // 验证签名
//        if (param==null){
//            return "fail";
//        }
//        LOGGER.error(param);
//        String merchant = param.get("merchant").toString();
//        boolean temp = false;
//        if ("88052".equals(merchant)){
//            temp= HttpClientUtils.Md5GalaxyVerification(param,CLOUDPAY_KEY);
//        }else {
//            temp= HttpClientUtils.Md5GalaxyVerification(param,MHDPAY_KEY);
//        }
//        if (!temp){
//            return "fail";
//        }
//        LOGGER.error("认证成功");
//        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
//        // 获取订单号
//        String orderNum = params.getString("order_id");
//        Blade blade = Blade.create(ExchangeReview.class);
//        ExchangeReview exchangeReview =blade.findFirstBy("orderNumber = #{orderNumber}", CMap.init().set("orderNumber",orderNum));
//        if (exchangeReview==null){
//            return "success";
//        }
//        // 获取订单状态 5； 成功，3：失败
//        int status = params.getIntValue("status");
//        if (status==5){
//                if (exchangeReview.getStatus()==3||exchangeReview.getStatus()==4){
//                    return "success";
//                }
//                // 回调成功
//                successExcExecuted(exchangeReview);
//        }else {
//            // 支付失败兑换变为支付失败
//            exchangeReview.setStatus(6);
//            exchangeReview.setEndTime(new Date());
//            // 兑换失败，发送邮件是在退回的时候进行发送
//            String remark = params.getString("remark");
//            exchangeReview.setMsg(remark);
//        }
//        // 修改订单状态
//        blade.update(exchangeReview);
//        return "success";
//    }

//    @PostMapping("/recharge_letspay_callback")
//    public String rechargeLetsPayCallback(@RequestParam Map<String,Object> param){
//        // 验证签名
//        if (param==null){
//            return "fail";
//        }
//        LOGGER.error(param);
//        String mchId = param.get("mchId").toString();
//        if (mchId==null){
//            return "fail";
//        }
//        String key="";
//        if (payPlus.getMchId().equals(mchId)){
//            key= payPlus.getKey();
//        }else {
//            key= superPayPlus.getKey();
//        }
//        Boolean temp = HttpClientUtils.Md5OmoOrLetsPayVerification(param,key);
//        if (!temp){
//            return "fail";
//        }
//        LOGGER.error("认证成功");
//        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
//        // 获取订单号
//        String orderNum = params.getString("orderNo");
//        Blade blade = Blade.create(RechargeRecords.class);
//        RechargeRecords rechargeRecords = blade.findFirstBy("orderNumber=#{orderNum}", CMap.init().set("orderNum", orderNum));
//        if (rechargeRecords==null){
//            return "success";
//        }
//        // 1 支付中,2 成功,5 失效,-1 失败
//        int status = params.getIntValue("status");
//        if (status==2){
//                if (rechargeRecords.getOrderStatus()==2){
//                    return "success";
//                }
//                successRecExecuted(orderNum,rechargeRecords);
//        }else if (status==5||status==-1){
//            // 从延迟队列中移除并且更新状态
//            failRecExecuted(rechargeRecords);
//        }
//        return "success";
//    }

//    @PostMapping("/exchange_letspay_callback")
//    public String exchangeLetsPayCallback(@RequestParam Map<String,Object> param){
//        // 验证签名
//        if (param==null){
//            return "fail";
//        }
//        LOGGER.error(param);
//        // 去除msg,不参与签名
//        String msg = param.remove("msg").toString();
//        String mchId = param.get("mchId").toString();
//        if (mchId==null){
//            return "fail";
//        }
//        String key="";
//        if (payPlus.getMchId().equals(mchId)){
//            key= payPlus.getKey();
//        }else {
//            key= superPayPlus.getKey();
//        }
//        Boolean temp = HttpClientUtils.Md5OmoOrLetsPayVerification(param,key);
//        if (!temp){
//            return "fail";
//        }
//        LOGGER.error("认证成功");
//        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
//        // 获取订单号
//        String orderNum = params.getString("mchTransNo");
//        Blade blade = Blade.create(ExchangeReview.class);
//        ExchangeReview exchangeReview =blade.findFirstBy("orderNumber = #{orderNumber}", CMap.init().set("orderNumber",orderNum));
//        if (exchangeReview==null){
//            return "success";
//        }
//        // 获取订单状态 2； 成功，3：失败 1:处理中
//        int status = params.getIntValue("status");
//        if (status==2){
////            synchronized (lock){
//                if (exchangeReview.getStatus()==3||exchangeReview.getStatus()==4){
//                    return "success";
//                }
//                // 回调成功
//                successExcExecuted(exchangeReview);
////            }
//        }else if (status==3){
//            // 支付失败兑换变为支付失败
//            exchangeReview.setStatus(6);
//            exchangeReview.setEndTime(new Date());
//            exchangeReview.setMsg(msg);
//        }else {
//            // 处理中不操作
//            exchangeReview.setEndTime(new Date());
//        }
//        // 修改订单状态
//        blade.update(exchangeReview);
//        return "success";
//    }

//    @PostMapping(value = "/recharge_LuckyPay_callback")
//    public String rechargeLuckyPayCallback(@RequestParam Map<String,Object> param) {
//        // 验证签名
//        if (param == null) {
//            return "fail";
//        }
//        LOGGER.error(param);
//        Boolean temp = HttpClientUtils.Md5GalaxyVerification(param, PUBLIC_LUCKYPAY_KEY1);
//        if (!temp) {
//            return "fail";
//        }
//        LOGGER.error("认证成功");
//        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
//        // 获取订单号
//        String orderNum = params.getString("orderNo");
//        Blade blade = Blade.create(RechargeRecords.class);
//        RechargeRecords rechargeRecords = blade.findFirstBy("orderNumber=#{orderNum}", CMap.init().set("orderNum", orderNum));
//        if (rechargeRecords == null) {
//            return "SUCCESS";
//        }
//        // 1 支付中,2 成功,5 失效,-1 失败
//        String code = params.getString("code");
//        if ("00".equals(code)) {
//            if (rechargeRecords.getOrderStatus() == 2) {
//                return "SUCCESS";
//            }
//            successRecExecuted(orderNum, rechargeRecords);
//        } else {
//            // 从延迟队列中移除并且更新状态
//            failRecExecuted(rechargeRecords);
//        }
//            return "SUCCESS";
//        }

//    @PostMapping(value = "/exchange_LuckyPay_callback")
//    public String exchangeLuckyPayCallback(@RequestParam Map<String,Object> param){
//        if (param==null){
//            return "fail";
//        }
//        LOGGER.error(param);
//        Boolean temp = HttpClientUtils.Md5GalaxyVerification(param,PUBLIC_LUCKYPAY_KEY1);
//        if (!temp){
//            return "fail";
//        }
//        LOGGER.error("认证成功");
//        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
//        // 获取订单号
//        String orderNum = params.getString("orderNo");
//        Blade blade = Blade.create(ExchangeReview.class);
//        ExchangeReview exchangeReview =blade.findFirstBy("orderNumber = #{orderNumber}", CMap.init().set("orderNumber",orderNum));
//        if (exchangeReview==null){
//            return "SUCCESS";
//        }
//        // 获取订单状态 2； 成功，3：失败 1:处理中
//        String code = params.getString("code");
//        if ("00".equals(code)){
//                if (exchangeReview.getStatus()==3||exchangeReview.getStatus()==4){
//                    return "SUCCESS";
//                }
//                int status = params.getIntValue("status");
//                if (status==1){
//                    // 回调成功
//                    successExcExecuted(exchangeReview);
//                }
//        }else{
//            // 支付失败兑换变为支付失败
//            exchangeReview.setStatus(6);
//            exchangeReview.setEndTime(new Date());
//            String msg = params.getString("msg");
//            exchangeReview.setMsg(msg);
//        }
//        // 修改订单状态
//        blade.update(exchangeReview);
//        return "SUCCESS";
//    }

}
