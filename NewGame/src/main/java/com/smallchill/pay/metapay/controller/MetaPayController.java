package com.smallchill.pay.metapay.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.common.base.BaseController;
import com.smallchill.common.task.GlobalDelayQueue;
import com.smallchill.common.utils.RateLimit;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.game.service.CommonService;
import com.smallchill.pay.betcatpay.utils.BetcatPayUtils;
import com.smallchill.pay.core.intercept.PayValidator;
import com.smallchill.pay.metapay.model.MetaPay;
import com.smallchill.pay.metapay.utils.MetaPayUtils;
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
@RequestMapping("/pay/MetaPay")
public class MetaPayController extends BaseController implements ConstShiro {

    @Resource
    private CommonService commonService;
    @Resource
    private RechargeRecordsService rechargeRecordsService;
    @Resource
    private MetaPay metaPay;

    @PostMapping("/recharge")
    @Transactional
    @Before(PayValidator.class)
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
        return rechargeMetaPay(rechargeRecords, resultMap);
    }

    private AjaxResult rechargeMetaPay(RechargeRecords rechargeRecords, JSONObject resultMap) {
        String response = MetaPayUtils.recharge(rechargeRecords,metaPay);
        if ("".equals(response)) {
            return fail("105005");
        }
        JSONObject jsonObject;
        try {
            jsonObject = JSON.parseObject(response);
        }catch (Exception e){
            return json(resultMap, "Recharge application failed", 1);
        }
        int code = jsonObject.getIntValue("platRespCode");
        String PfOrderNum = jsonObject.getString("transId");
        rechargeRecords.setPfOrderNum(PfOrderNum);
        if (code == 0) {
            // 获取支付地址
            String payUrl = jsonObject.getString("url");
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
            rechargeRecords.setMsg(code + jsonObject.getString("msg"));
            rechargeRecords.setOrderStatus(3);
            rechargeRecordsService.saveRtId(rechargeRecords);
            return json(resultMap, "105005", 1);
        }
    }
}
