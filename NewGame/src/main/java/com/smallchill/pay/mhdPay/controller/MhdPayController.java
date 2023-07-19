package com.smallchill.pay.mhdPay.controller;

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
import com.smallchill.pay.mhdPay.model.MhdPay;
import com.smallchill.pay.mhdPay.utils.MhdPayUtils;
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
@RequestMapping("/pay/MhdPay")
public class MhdPayController extends BaseController implements ConstShiro {
    @Resource
    private CommonService commonService;
    @Resource
    private RechargeRecordsService rechargeRecordsService;
    @Resource
    private MhdPay mhdPay;

    /**
     * 充值回调都使用同一个接口galaxy
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
        Map channel =(Map) info.get("channel");
        return rechargeGalaxy(rechargeRecords,resultMap,channel);
    }

    /**
     * 银河系统支付逻辑
     */
    private AjaxResult rechargeGalaxy(RechargeRecords rechargeRecords, JSONObject resultMap, Map<String,Object> channel) {
        JSONObject jsonObject;
        String response = MhdPayUtils.recharge(rechargeRecords,mhdPay,channel);
        if ("".equals(response)) {
            return json(resultMap, "105005", 1);
        }
        try {
            jsonObject = JSON.parseObject(response);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return json(resultMap, "105005", 1);
        }
        rechargeRecords.setPfOrderNum("");
        int status = jsonObject.getIntValue("status");
        // 1：成功，0:失败
        if (status==1) {
            // 获取支付链接
            String payUrl = jsonObject.getString("redirect_url");
            resultMap.put("urlPay", payUrl);
            rechargeRecords.setUrlPay(payUrl);
            // 将订单加入到未支付队列中
            GlobalDelayQueue.orderQueue.add(rechargeRecords);
            rechargeRecordsService.saveRtId(rechargeRecords);
//            if (rechargeRecords.getIsFirstCharge()==2){
//                // [QPGameUserDB].[dbo].[PlayerActiveInfo]这个表的 activeid=4 subActveid=1的ispick重置为1
//                Db.update("update [QPGameUserDB].[dbo].[PlayerActiveInfo] set IsPick=1 where ActiveID =4 and SubActiveID=1 and UserID=#{userId}",
//                        CMap.init().set("userId",rechargeRecords.getUserId()));
//            }
            return json(resultMap, "105006");
        } else {
            rechargeRecords.setMsg(jsonObject.getString("message"));
            rechargeRecords.setOrderStatus(3);
            rechargeRecordsService.saveRtId(rechargeRecords);
            return json(resultMap, "105005", 1);
        }
    }
}
