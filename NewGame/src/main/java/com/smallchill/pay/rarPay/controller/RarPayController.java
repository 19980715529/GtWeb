package com.smallchill.pay.rarPay.controller;

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
import com.smallchill.pay.rarPay.model.RarPay;
import com.smallchill.pay.rarPay.utils.RarPayUtils;
import com.smallchill.system.service.RechargeRecordsService;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.RechargeExchangeCommon;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/pay/RarPay")
public class RarPayController extends BaseController implements ConstShiro {
    @Resource
    private CommonService commonService;
    @Resource
    private RechargeRecordsService rechargeRecordsService;

    @Resource
    private RarPay rarPay;

    @PostMapping("/recharge")
    @Transactional
    @RateLimit(limit = 1,period = 30)
    public AjaxResult recharge() {
        RechargeRecords rechargeRecords = mapping("recharge", RechargeRecords.class);
        // 根据用户id查询用户数据
        Map user = commonService.getInfoByOne("player_operate.new_info", CMap.init().set("UserID", rechargeRecords.getUserId()));
        JSONObject resultMap = new JSONObject();
        // 获取充值渠道id
        int channelId = Integer.parseInt(HttpKit.getRequest().getParameter("recharge.id"));
        Map<String, Object> info = RechargeExchangeCommon.recharge(rechargeRecords, resultMap, user, commonService, channelId);
        int code = Integer.parseInt(info.get("code").toString());
        if (code == 1) {
            return fail(info.get("msg").toString());
        }
        Map channel =(Map) info.get("channel");
        return rechargeRar(rechargeRecords,resultMap,channel);
    }

    private AjaxResult rechargeRar(RechargeRecords rechargeRecords, JSONObject resultMap, Map channel) {
        String response;
        JSONObject jsonObject;
        int code;
        response = RarPayUtils.sendRechargeRar(rechargeRecords, channel,rarPay);
        if (response.equals("")) {
            return json(resultMap, "fail", 1);
        }
        try {
            jsonObject = JSON.parseObject(response);
        }catch (Exception e){
            return json(resultMap, "105005", 1);
        }
        LOGGER.error(jsonObject.toString());
        code = jsonObject.getIntValue("code");
        if (code == 0) {
            // 错误时状态为30
            rechargeRecords.setMsg(jsonObject.getString("msg"));
            rechargeRecordsService.saveRtId(rechargeRecords);
            // 关闭订单
            rechargeRecords.setOrderStatus(3);
            return json(resultMap, "105005", 1);
        } else {
            String pay_url = jsonObject.getJSONObject("data").getString("pay_url");
            rechargeRecords.setUrlPay(pay_url);
            // 获取平台订单号
            String ordernum = jsonObject.getJSONObject("data").getString("ordernum");
            rechargeRecords.setPfOrderNum(ordernum);
            // 生成订单记录
            rechargeRecordsService.saveRtId(rechargeRecords);
            String PayUrl = jsonObject.getJSONObject("data").getString("pay_url");
            resultMap.put("urlPay", PayUrl);
            rechargeRecords.setUrlPay(PayUrl);
            GlobalDelayQueue.orderQueue.add(rechargeRecords);
            // 判断支付状态 订单状态:0=未支付;10=支付中;20=支付成功;30=支付失败
//            if (rechargeRecords.getIsFirstCharge()==2){
//                // [QPGameUserDB].[dbo].[PlayerActiveInfo]这个表的 activeid=4 subActveid=1的ispick重置为1
//                Db.update("update [QPGameUserDB].[dbo].[PlayerActiveInfo] set IsPick=1 where ActiveID =4 and SubActiveID=1 and UserID=#{userId}",
//                        CMap.init().set("userId",rechargeRecords.getUserId()));
//            }
            return json(resultMap, "105006");
        }
    }
}
