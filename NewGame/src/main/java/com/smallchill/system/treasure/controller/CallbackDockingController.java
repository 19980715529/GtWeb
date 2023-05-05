package com.smallchill.system.treasure.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.smallchill.common.base.BaseController;
import com.smallchill.common.task.GlobalDelayQueue;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;
import static com.smallchill.core.constant.ConstKey.*;

import com.smallchill.core.toolbox.kit.ThreadKit;
import com.smallchill.system.service.ExchangeReviewService;
import com.smallchill.system.service.RechargeRecordsService;
import com.smallchill.system.treasure.model.*;
import com.smallchill.system.treasure.utils.*;
import org.beetl.sql.core.OnConnection;
import org.beetl.sql.core.SQLManager;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import static com.smallchill.core.constant.ConstEmail.*;

/**
 * @Description TODO
 * @classNameExchangeDockingController
 * @Date 2023/2/25 12:42
 * @Version 1.0 接口回调接口
 **/
@RestController
@RequestMapping("/callback")
public class CallbackDockingController extends BaseController implements ConstShiro {
    @Autowired
    private RechargeRecordsService rechargeRecordsService;
    @Autowired
    private ExchangeReviewService exchangeReviewService;
    /**
     * 充值回调接口
     * @return
     */
    @PostMapping("/recharge_rarp_callback")
    @Transactional
    public String callback(@RequestParam Map<String,Object> param){
        // 回调需要执行RechargeRecord函数
        if (param==null){
            return "fail";
        }
        LOGGER.error(param);
        Boolean b = HttpClientUtils.Md5RarpVerification(param);
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
        Blade blade = Blade.create(RechargeRecords.class);
        GlobalDelayQueue globalDelayQueue = new GlobalDelayQueue();
        // 判断充值是否成功  订单状态:0=未支付;10=支付中;20=支付成功;30=支付失败。一般成功、失败时才会回调   订单状态，1：待支付，2：已完成，3：已失败
        if(Integer.parseInt(status)==20){
            // 判断状态已经改变为已经完成，就不在处理
            if (rechargeRecords.getOrderStatus()==2){
                return "success";
            }
            successRecExecuted(blade,orderNum,rechargeRecords,globalDelayQueue);
        }else if(Integer.parseInt(status)==30){
            // 充值失败将状态修改为已关闭
            rechargeRecords.setOrderStatus(3);
            // 将订单从队列中移除
            globalDelayQueue.cancelOrder(orderNum);
            rechargeRecords.setEndTime(new Date());
            rechargeRecordsService.update(rechargeRecords);
        }else{
            // 将充值中和为充值的订单状态不改变
            return "success";
        }
        return "success";
    }


    @PostMapping("/test")
    @Transactional
    public String callbackTest(@RequestParam Map<String,Object> param){
        // 回调需要执行RechargeRecord函数
        if (param==null){
            return "fail";
        }
        // 获取自己的订单号
        String orderNum = param.get("orderNum").toString();
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
        rechargeRecords.setMsg("测试");
        Blade blade = Blade.create(RechargeRecords.class);
        GlobalDelayQueue globalDelayQueue = new GlobalDelayQueue();
        // 判断充值是否成功  订单状态:0=未支付;10=支付中;20=支付成功;30=支付失败。一般成功、失败时才会回调   订单状态，1：待支付，2：已完成，3：已失败
        if(Integer.parseInt(status)==20){
            // 判断状态已经改变为已经完成，就不在处理
            if (rechargeRecords.getOrderStatus()==2){
                return "success";
            }
            successRecExecuted(blade,orderNum,rechargeRecords,globalDelayQueue);
        }else if(Integer.parseInt(status)==30){
            // 充值失败将状态修改为已关闭
            rechargeRecords.setOrderStatus(3);
            // 将订单从队列中移除
            globalDelayQueue.cancelOrder(orderNum);
            rechargeRecords.setEndTime(new Date());
            rechargeRecordsService.update(rechargeRecords);
        }else{
            // 将充值中和为充值的订单状态不改变
            return "success";
        }
        return "success";
    }
    // 兑换回调
    @PostMapping(value = "/exchange_rarp_callback")
    public String exchangeCallback(@RequestParam Map<String,Object> param) {
        if (param==null){
            return "fail";
        }
        LOGGER.error(param);
        Boolean b = HttpClientUtils.Md5RarpVerification(param);
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

    /**
     * 充值回调
     * @return
     */
    @PostMapping(value = "/recharge_safa_callback",consumes = "application/json")
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
        GlobalDelayQueue globalDelayQueue = new GlobalDelayQueue();
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
            successRecExecuted(blade,order_no,rechargeRecords,globalDelayQueue);
        } else if ("fail".equals(status)) {
            // 从延迟队列中移除并且更新状态
            globalDelayQueue.cancelOrder(order_no);
            rechargeRecords.setOrderStatus(3);
            rechargeRecords.setEndTime(new Date());
            blade.update(rechargeRecords);
        }
        return "ok";
    }

    /**
     * 兑换回调
     * @return
     */
    @PostMapping(value = "/exchange_safe_callback",consumes = "application/json")
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



    /**
     * MetaPay充值回调
     */
    @PostMapping(value = "/recharge_meta_callback",consumes = "application/json")
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
        // 获取平台订单
        GlobalDelayQueue globalDelayQueue = new GlobalDelayQueue();
        int status = params.getIntValue("status");
        if (status==0){
            if (rechargeRecords.getOrderStatus()==2){
                return "SUCCESS";
            }
            successRecExecuted(blade, orderNum, rechargeRecords, globalDelayQueue);
        }else if (status==2){
            // 从延迟队列中移除并且更新状态
            globalDelayQueue.cancelOrder(orderNum);
            rechargeRecords.setOrderStatus(3);
            rechargeRecords.setEndTime(new Date());
            blade.update(rechargeRecords);
        }
        return "SUCCESS";
    }



    /**
     * MetaPay兑换回调
     */
    @PostMapping(value = "/exchange_meta_pay_callback",consumes = "application/json")
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
    /**
     * OMOM 充值回调
     */
    @PostMapping(value = "/recharge_omom_callback",consumes = "application/json")
    public String rechargeOmomCallback(@RequestBody Map<String,Object> param){
        // 验证签名
        if (param==null){
            return "fail";
        }
        LOGGER.error(param);
        Boolean temp = HttpClientUtils.Md5OmoVerification(param);
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
        GlobalDelayQueue globalDelayQueue = new GlobalDelayQueue();
        // 获取状态
        String status = params.getString("returncode");
        if ("00".equals(status)){
            if (rechargeRecords.getOrderStatus()==2){
                return "ok";
            }
            successRecExecuted(blade,orderNum,rechargeRecords,globalDelayQueue);
        }else {
            // 从延迟队列中移除并且更新状态
            globalDelayQueue.cancelOrder(orderNum);
            rechargeRecords.setOrderStatus(3);
            rechargeRecords.setEndTime(new Date());
            blade.update(rechargeRecords);
        }
        return "ok";
    }
    /**
     * OMOM 兑换回调
     */
    @PostMapping(value = "/exchange_omom_callback",consumes = "application/json")
    public String exchangeOmomCallback(@RequestBody Map<String,Object> param){
        if (param==null){
            return "fail";
        }
        LOGGER.error(param);
        Boolean temp = HttpClientUtils.Md5OmoVerification(param);
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

    /**
     * AIPay 充值回调
     * @return
     */
    @PostMapping(value = "/recharge_AIPay_callback",consumes = "application/json")
    public String rechargeAIPayCallback(@RequestBody Map<String,Object> param){
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
        // 获取订单号
        String orderNum = params.getString("merchantOrderId");
        Blade blade = Blade.create(RechargeRecords.class);
        RechargeRecords rechargeRecords = blade.findFirstBy("orderNumber=#{orderNum}", CMap.init().set("orderNum", orderNum));
        if (rechargeRecords==null){
            return "ok";
        }
        // 获取订单状态
        int status = params.getIntValue("status");
        GlobalDelayQueue globalDelayQueue = new GlobalDelayQueue();
        if (status==1){
            if (rechargeRecords.getOrderStatus()==2){
                return "ok";
            }
            successRecExecuted(blade,orderNum,rechargeRecords,globalDelayQueue);
        }else if (status==2){
            // 从延迟队列中移除并且更新状态
            globalDelayQueue.cancelOrder(orderNum);
            rechargeRecords.setOrderStatus(3);
            rechargeRecords.setEndTime(new Date());
            blade.update(rechargeRecords);
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
    @PostMapping(value = "/exchange_AIPay_callback",consumes = "application/json")
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

    /**
     * WePay 充值回调
     * @return
     */
    @PostMapping("/recharge_WePay_callback")
    public String rechargeWePayCallback(@RequestParam Map<String,Object> param){
        // 验证签名
        if (param==null){
            return "fail";
        }
        LOGGER.error(param);
        Boolean temp = HttpClientUtils.Md5WePayVerification(param,WEPAY_CKEY);
        if (!temp){
            return "fail";
        }
        LOGGER.error("认证成功");
        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
        // 获取订单号
        String orderNum = params.getString("mchOrderNo");
        Blade blade = Blade.create(RechargeRecords.class);
        RechargeRecords rechargeRecords = blade.findFirstBy("orderNumber=#{orderNum}", CMap.init().set("orderNum", orderNum));
        if (rechargeRecords==null){
            return "success";
        }
        // 获取支付状态
        int result = params.getIntValue("tradeResult");
        GlobalDelayQueue globalDelayQueue = new GlobalDelayQueue();
        if (result==1){
            if (rechargeRecords.getOrderStatus()==2){
                return "success";
            }
            successRecExecuted(blade,orderNum,rechargeRecords,globalDelayQueue);
        }else {
            // 从延迟队列中移除并且更新状态
            globalDelayQueue.cancelOrder(orderNum);
            rechargeRecords.setOrderStatus(3);
            rechargeRecords.setEndTime(new Date());
            blade.update(rechargeRecords);
        }
        return "success";
    }


    /**
     * WePay 兑换回调
     * @param param
     * @return
     */
    @PostMapping("/exchange_WePay_callback")
    public String exchangeWePayCallback(@RequestParam Map<String,Object> param){
        // 验证签名
        if (param==null){
            return "fail";
        }
        LOGGER.error(param);
        Boolean temp = HttpClientUtils.Md5WePayVerification(param,WEPAY_PKEY);
        if (!temp){
            return "fail";
        }
        LOGGER.error("认证成功");
        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
        // 获取订单号
        String orderNum = params.getString("merTransferId");
        Blade blade = Blade.create(ExchangeReview.class);
        ExchangeReview exchangeReview =blade.findFirstBy("orderNumber = #{orderNumber}", CMap.init().set("orderNumber",orderNum));
        if (exchangeReview==null){
            return "success";
        }
        // 获取订单状态
        int result = params.getIntValue("tradeResult");
        if (result==1){
            if (exchangeReview.getStatus()==3||exchangeReview.getStatus()==4){
                return "success";
            }
            // 回调成功
            successExcExecuted(exchangeReview);
        }else if (result == 2 || result==3) {
            // 支付失败兑换变为支付失败
            exchangeReview.setStatus(6);
            exchangeReview.setEndTime(new Date());
            // 兑换失败，发送邮件是在退回的时候进行发送
        }else {
            return "success";
        }
        // 修改订单状态
        blade.update(exchangeReview);
        return "success";
    }

    /**
     * 银河系统充值回调
     * @param param
     * @return
     */
    @PostMapping("/recharge_galaxy_callback")
    public String rechargeGalaxyCallback(@RequestParam Map<String,Object> param){
        // 验证签名
        if (param==null){
            return "fail";
        }
        LOGGER.error(param);
        Boolean temp = HttpClientUtils.Md5GalaxyVerification(param);
        if (!temp){
            return "fail";
        }
        LOGGER.error("认证成功");
        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
        // 获取订单号
        String orderNum = params.getString("order_id");
        Blade blade = Blade.create(RechargeRecords.class);
        RechargeRecords rechargeRecords = blade.findFirstBy("orderNumber=#{orderNum}", CMap.init().set("orderNum", orderNum));
        if (rechargeRecords==null){
            return "success";
        }
        // 获取支付状态 5:成功，3：失败
        int status = params.getIntValue("status");
        GlobalDelayQueue globalDelayQueue = new GlobalDelayQueue();
        if (status==5){
            if (rechargeRecords.getOrderStatus()==2){
                return "success";
            }
            successRecExecuted(blade,orderNum,rechargeRecords,globalDelayQueue);
        }else {
            // 从延迟队列中移除并且更新状态
            globalDelayQueue.cancelOrder(orderNum);
            rechargeRecords.setOrderStatus(3);
            rechargeRecords.setEndTime(new Date());
            String remark = params.getString("remark");
            rechargeRecords.setMsg(remark);
            blade.update(rechargeRecords);
        }
        return "success";
    }

    @PostMapping("/exchange_galaxy_callback")
    public String exchangeGalaxyCallback(@RequestParam Map<String,Object> param){
        // 验证签名
        if (param==null){
            return "fail";
        }
        LOGGER.error(param);
        Boolean temp = HttpClientUtils.Md5GalaxyVerification(param);
        if (!temp){
            return "fail";
        }
        LOGGER.error("认证成功");
        JSONObject params = JSONObject.parseObject(JSON.toJSONString(param));
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

    // 执行存储过程
    public Object storedProcedure(Map<String,Object> map){
        SQLManager dao = Blade.dao("gameroomitemdb");
        Object o = dao.executeOnConnection(new OnConnection<Object>() {
            @Override
            public Object call(Connection connection) throws SQLException {
                try {
                    CallableStatement callableStatement = connection.prepareCall("{call [QPServerInfoDB].[dbo].[RechargeRecord](?,?,?,?,?)}");
                    callableStatement.setInt("UserID",Integer.valueOf(map.get("userId").toString()));
                    callableStatement.setInt("Gold",Integer.valueOf(map.get("Gold").toString()));
                    callableStatement.setInt("GameCoin",Integer.valueOf(map.get("GameCoin").toString()));
                    int type = Integer.valueOf(map.get("type").toString());
                    callableStatement.setInt("Type",type);
                    callableStatement.setString("OrderNum","");
                    callableStatement.setString("OrderNum",map.get("OrderNum").toString());
                    callableStatement.executeQuery();
                    return "";
                }catch (Exception e){
                    LOGGER.error(e.getMessage());
                    return e.getMessage();
                }
            }
        });
        return o;
    }

    // 充值成功需要执行的存储过程 SharePlayerRechargeRebate
    public Object rechargeStored(Map<String,Object> map){
        SQLManager dao = Blade.dao("gameuserdb");
        Object o = dao.executeOnConnection(new OnConnection<Object>() {
            @Override
            public Object call(Connection connection) {
                try {
                    CallableStatement callableStatement = connection.prepareCall("{call [QPGameUserDB].[dbo].[SharePlayerRechargeRebate](?,?)}");
                    callableStatement.setInt("UserID", Integer.valueOf(map.get("userId").toString()));
                    callableStatement.setInt("RechargeGold", Integer.valueOf(map.get("GameCoin").toString()));
                    callableStatement.executeQuery();
                    return "";
                }catch (Exception e){
                    LOGGER.error(e.getMessage());
                    return e.getMessage();
                }
            }
        });
        return o;
    }
    /**
     * 充值成功后执行后需要存储过程
     * @param rechargeRecords
     */
    private void extracted(RechargeRecords rechargeRecords) {
        // 执行存储过程
        HashMap<String, Object> stored = new HashMap<>();
        stored.put("userId", rechargeRecords.getUserId());
        stored.put("Gold", rechargeRecords.getTopUpAmount());
        stored.put("GameCoin", rechargeRecords.getGold());
        stored.put("type",0);
        stored.put("OrderNum",rechargeRecords.getOrderNumber());
        // 执行分享存储过程
        storedProcedure(stored);
        // 充值成功需要执行的存储过程，充值兑换记录
        rechargeStored(stored);
    }

    /**
     * 兑换需要执行的存储过程
     * @param review
     */
    private void extracted(ExchangeReview review) {
        HashMap<String, Object> stored = new HashMap<>();
        stored.put("userId", review.getUserId());
        stored.put("Gold", review.getAmount());
        stored.put("GameCoin", review.getGold());
        stored.put("OrderNum",review.getOrderNumber());
        stored.put("type",1);
        storedProcedure(stored);
        // 需要向游戏服务器发送请求
        Map<String, Object> gameParam = new HashMap<>();
        gameParam.put("Userid", review.getUserId());
        gameParam.put("gameCoin", review.getGold());
        gameParam.put("gold", review.getAmount());
        gameParam.put("Type",1);
        SendHttp.sendGame1002(gameParam);
    }


    /**
     * 成功需要执行的代码
     * @param blade
     * @param orderNum
     * @param rechargeRecords
     * @param globalDelayQueue
     */

    private void successRecExecuted(Blade blade, String orderNum, RechargeRecords rechargeRecords, GlobalDelayQueue globalDelayQueue) {
        // 充值成功将状态修改为已完成
        rechargeRecords.setOrderStatus(2);
        // 执行存储过程
        extracted(rechargeRecords);
        // 回调成功,根据超时订单号将订单从延迟列表中取消
        globalDelayQueue.cancelOrder(orderNum);
        // 金币变动记录
        if (rechargeRecords.getIsFirstCharge()==1){
            RechargeExchangeCommon.AddGoldChangeRecords(rechargeRecords.getUserId(),207, rechargeRecords.getGold());
        }else {
            RechargeExchangeCommon.AddGoldChangeRecords(rechargeRecords.getUserId(),5, rechargeRecords.getGold());
        }
        // 判断是否首充
        if (rechargeRecords.getIsFirstCharge()==1){
            Db.update("update [QPGameUserDB].[dbo].[AccountsInfo] set IsFirstRecharge=#{IsFirstRecharge} where UserID=#{UserID}",
                    CMap.init().set("IsFirstRecharge",1).set("UserID", rechargeRecords.getUserId()));
        }
        // 判断是否用户第一笔成功的充值
        int re = Db.queryInt("select count(1) from Recharge_records where userId=#{userId} and orderStatus=2",
                CMap.init().set("userId", rechargeRecords.getUserId()));
        if (re<1){
            rechargeRecords.setIsThatTay(1);
        }
        // 向游戏服务器发送请求
        Map<String, Object> gameParam = new HashMap<>();
        gameParam.put("Userid", rechargeRecords.getUserId());
        gameParam.put("gameCoin", rechargeRecords.getGold());
        gameParam.put("gold", rechargeRecords.getTopUpAmount());
        gameParam.put("Type",0);
        gameParam.put("IsFirstRecharge",0);
        // 获取充值成功的邮件
        Map emailParam = RechargeExchangeCommon.getEmailConf(2);
        // 普通充值类型
        emailParam.put("goldType",5);
        // 判断是否首充
        if (rechargeRecords.getIsFirstCharge()==1){
            gameParam.put("IsFirstRecharge",1);
            // 邮件类型首充
            emailParam.put("goldType",207);
        }
        SendHttp.sendGame1002(gameParam);
        emailParam.put("gold",0);
        emailParam.put("toUserid", rechargeRecords.getUserId());
        SendHttp.sendEmail(emailParam);
        rechargeRecords.setEndTime(new Date());
        rechargeRecords.setOrderStatus(2);
        blade.update(rechargeRecords);
    }

    /**
     * 兑换回调成功需要执行
     * @param exchangeReview
     */
    private void successExcExecuted(ExchangeReview exchangeReview) {
        // 回调成功
        exchangeReview.setStatus(4);
        exchangeReview.setEndTime(new Date());
        // 执行存储过程
        extracted(exchangeReview);
        // 兑换成功发送邮件
        Map emailParam = RechargeExchangeCommon.getEmailConf(4);
        emailParam.put("gold", 0);
        emailParam.put("toUserid", exchangeReview.getUserId());
        emailParam.put("goldType",206);
        SendHttp.sendEmail(emailParam);
    }

}
