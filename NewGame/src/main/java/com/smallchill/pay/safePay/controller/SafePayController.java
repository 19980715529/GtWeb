package com.smallchill.pay.safePay.controller;

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
import com.smallchill.pay.safePay.model.SafePay;
import com.smallchill.pay.safePay.utils.SafePayUtils;
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
@RequestMapping("/pay/SafePay")
public class SafePayController extends BaseController implements ConstShiro{
    @Resource
    private CommonService commonService;
    @Resource
    private RechargeRecordsService rechargeRecordsService;

    @Resource
    private SafePay safePay;
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
        Map channel =(Map) info.get("channel");
        return rechargeSafe(rechargeRecords,resultMap,channel);
    }

    /**
     * safe支付逻辑
     * @param rechargeRecords
     * @param resultMap
     * @param channel
     * @return
     */
    private AjaxResult rechargeSafe(RechargeRecords rechargeRecords, JSONObject resultMap, Map channel) {
        String response;
        JSONObject jsonObject;
        response = SafePayUtils.recharge(rechargeRecords, channel,safePay);
        LOGGER.error(response);
        if (response.equals("")) {
            return json(resultMap, "105005", 1);
        }
        jsonObject = JSON.parseObject(response);
        String statusStr = jsonObject.getString("status");
        if ("success".equals(statusStr)) {
            // 充值请求成功
            // 获取支付链接
            String payUrl = jsonObject.getString("order_data");
            resultMap.put("urlPay", payUrl);
            // 将订单加入到未支付的队列中
            rechargeRecords.setUrlPay(payUrl);
            GlobalDelayQueue.orderQueue.add(rechargeRecords);
            rechargeRecordsService.saveRtId(rechargeRecords);
//            if (rechargeRecords.getIsFirstCharge()==2){
//                // [QPGameUserDB].[dbo].[PlayerActiveInfo]这个表的 activeid=4 subActveid=1的ispick重置为1
//                Db.update("update [QPGameUserDB].[dbo].[PlayerActiveInfo] set IsPick=1 where ActiveID =4 and SubActiveID=1 and UserID=#{userId}",
//                        CMap.init().set("userId",rechargeRecords.getUserId()));
//            }
            return json(resultMap, "105006");
        } else {
            rechargeRecords.setMsg(jsonObject.getString("status_mes"));
            // 关闭订单
            rechargeRecords.setOrderStatus(3);
            rechargeRecordsService.saveRtId(rechargeRecords);
            return json(resultMap, "105005", 1);
        }
    }
}
