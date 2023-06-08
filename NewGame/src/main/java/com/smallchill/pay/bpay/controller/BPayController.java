package com.smallchill.pay.bpay.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.common.base.BaseController;
import com.smallchill.common.task.GlobalDelayQueue;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.game.service.CommonService;
import com.smallchill.pay.bpay.model.BPay;
import com.smallchill.pay.bpay.utils.BPayUtils;
import com.smallchill.system.service.RechargeRecordsService;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.RechargeExchangeCommon;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pay/BPay")
public class BPayController extends BaseController implements ConstShiro {
    @Resource
    private CommonService commonService;
    @Resource
    private RechargeRecordsService rechargeRecordsService;
    @Resource
    private BPay bPay;

    @PostMapping("/recharge")
    @Transactional
    public AjaxResult recharge(){
        RechargeRecords rechargeRecords=mapping("recharge", RechargeRecords.class);
        // 根据用户id查询用户数据
        HashMap<String, Object> user_map = new HashMap<>();
        user_map.put("UserID",rechargeRecords.getUserId());
        Map user = commonService.getInfoByOne("player_operate.new_info", user_map);
        JSONObject resultMap = new JSONObject();
        // 获取充值渠道id
        int channelId = Integer.parseInt(HttpKit.getRequest().getParameter("recharge.id"));
        LOGGER.error("小渠道id:"+channelId);
        Map<String, Object> info = RechargeExchangeCommon.recharge(rechargeRecords, resultMap, user,commonService,channelId);
        int code = Integer.parseInt(info.get("code").toString());
        if (code==1){
            return fail(info.get("msg").toString());
        }
        Map channel =(Map) info.get("channel");
        // 判断商家
        int pid = Integer.parseInt(channel.get("pid").toString());
        rechargeRecords.setChannelPid(pid);
//        RechargeExchangeCommon.rec(rechargeRecords,channel);

        return rechargeBPay(rechargeRecords,resultMap,channel);
    }

    private AjaxResult rechargeBPay(RechargeRecords rechargeRecords, JSONObject resultMap,Map<String,Object> channel) {
        String code;
        String PfOrderNum;
        String response;
        JSONObject jsonObject;
        response = BPayUtils.sendRechargeBPay(rechargeRecords,channel,bPay);
        LOGGER.error(response);
        if ("".equals(response)) {
            return json(resultMap, "Recharge application failed", 1);
        }
        jsonObject = JSON.parseObject(response);
        code = jsonObject.getString("code");
        if ("200".equals(code)) {
            // 获取支付链接
            String payUrl = jsonObject.getJSONObject("data").getString("paymentUrl");
            resultMap.put("urlPay", payUrl);
            rechargeRecords.setUrlPay(payUrl);
            // 设置平台订单号
            PfOrderNum = jsonObject.getJSONObject("data").getString("orderNo");
            rechargeRecords.setPfOrderNum(PfOrderNum);
            // 将订单加入到未支付队列中
            GlobalDelayQueue.orderQueue.add(rechargeRecords);
            rechargeRecordsService.saveRtId(rechargeRecords);
            if (rechargeRecords.getIsFirstCharge()==2){
                // [QPGameUserDB].[dbo].[PlayerActiveInfo]这个表的 activeid=4 subActveid=1的ispick重置为1
                Db.update("update [QPGameUserDB].[dbo].[PlayerActiveInfo] set IsPick=1 where ActiveID =4 and SubActiveID=1 and UserID=#{userId}",
                        CMap.init().set("userId",rechargeRecords.getUserId()));
            }
            return json(resultMap, "Recharge application success");
        } else {
            rechargeRecords.setMsg(jsonObject.getString("message"));
            rechargeRecords.setOrderStatus(3);
            rechargeRecordsService.saveRtId(rechargeRecords);
            return json(resultMap, "Recharge application failed", 1);
        }
    }
}
