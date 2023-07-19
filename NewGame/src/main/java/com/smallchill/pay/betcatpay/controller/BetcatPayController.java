package com.smallchill.pay.betcatpay.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.common.base.BaseController;
import com.smallchill.common.task.GlobalDelayQueue;
import com.smallchill.common.utils.RateLimit;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.game.service.CommonService;
import com.smallchill.pay.betcatpay.model.BetcatPay;
import com.smallchill.pay.betcatpay.utils.BetcatPayUtils;
import com.smallchill.system.service.RechargeRecordsService;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.RechargeExchangeCommon;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pay/BetcatPay")
public class BetcatPayController extends BaseController implements ConstShiro {

    @Resource
    private CommonService commonService;
    @Resource
    private RechargeRecordsService rechargeRecordsService;

    @Resource
    private BetcatPay betcatPay;
    /**
     * 需要的参数
     * recharge.isFirstCharge:0普通充值，1首充，2随机充值
     * recharge.userId:用户id
     * recharge.pid: 父渠道id
     * recharge.id: 渠道id
     * 新加：充值挡位id recharge.gear
     * @return
     */
    @PostMapping("/recharge")
    @Transactional
    @RateLimit(limit = 1,period = 30)
    public AjaxResult recharge(){
        RechargeRecords rechargeRecords = mapping("recharge", RechargeRecords.class);
        // 根据用户id查询用户数据
        Map user = commonService.getInfoByOne("player_operate.new_info", CMap.init().set("UserID",rechargeRecords.getUserId()));
        JSONObject resultMap = new JSONObject();
        // 获取充值渠道id
        int channelId = Integer.parseInt(HttpKit.getRequest().getParameter("recharge.id"));
        Map<String, Object> info = RechargeExchangeCommon.recharge(rechargeRecords, resultMap, user,commonService,channelId);
        int code = Integer.parseInt(info.get("code").toString());
        if (code==1){
            return fail(info.get("msg").toString());
        }
        String response = BetcatPayUtils.recharge(rechargeRecords,betcatPay);
        if ("".equals(response)) {
            return fail("105005");
        }
        JSONObject jsonObject;
        try {
            jsonObject = JSON.parseObject(response);
            LOGGER.error(jsonObject.toJSONString());
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return fail(e.getMessage());
        }
        int code1 = jsonObject.getIntValue("code");
        if (code1!=0){
            rechargeRecords.setMsg(jsonObject.getString("error"));
            rechargeRecords.setOrderStatus(3);
            rechargeRecordsService.saveRtId(rechargeRecords);
            return json(resultMap, "105005", 1);
        }else {
            JSONObject params = jsonObject.getJSONObject("data").getJSONObject("params");
            // 获取充值地址
            String payUrl = params.getString("url");
            // 支付码
            String payCode = params.getString("qrcode");
            resultMap.put("urlPay",payUrl);
            resultMap.put("payCode",payCode);
            resultMap.put("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            rechargeRecords.setUrlPay(payUrl);
            // 平台订单号
            String PfOrderNum = jsonObject.getJSONObject("data").getString("orderNo");
            // 将订单加入到未支付队列中
            GlobalDelayQueue.orderQueue.add(rechargeRecords);
            rechargeRecords.setPfOrderNum(PfOrderNum);
            rechargeRecordsService.saveRtId(rechargeRecords);
//            if (rechargeRecords.getIsFirstCharge()==2){
//                // [QPGameUserDB].[dbo].[PlayerActiveInfo]这个表的 activeid=4 subActveid=1的ispick重置为1
//                Db.update("update [QPGameUserDB].[dbo].[PlayerActiveInfo] set IsPick=1 where ActiveID =4 and SubActiveID=1 and UserID=#{userId}",
//                        CMap.init().set("userId",rechargeRecords.getUserId()));
//            }
            return json(resultMap, "105006");
        }
    }
}