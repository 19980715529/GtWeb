package com.smallchill.pay.payplus.cotroller;

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
import com.smallchill.pay.payplus.model.PayPlus;
import com.smallchill.pay.payplus.utils.PayPlusUtils;
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
@RequestMapping("/pay/PayPlus")
public class PayPlusController extends BaseController implements ConstShiro {
    @Resource
    private CommonService commonService;
    @Resource
    private RechargeRecordsService rechargeRecordsService;

    @Resource
    private PayPlus payPlus;

    /**
     * 需要的参数
     * recharge.isFirstCharge:0普通充值，1首充，2随机充值
     * recharge.userId:用户id
     * recharge.topUpAmount：充值的金额：这个不要了
     * recharge.pid: 父渠道id
     * recharge.id: 渠道id
     * 新加：充值挡位id recharge.gear
     * @return
     */
    @PostMapping("/recharge")
    @Transactional
    public AjaxResult recharge(){
        RechargeRecords rechargeRecords=mapping("recharge", RechargeRecords.class);
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
        String response = PayPlusUtils.recharge(rechargeRecords,payPlus);
        if ("".equals(response)) {
            return json(resultMap, "105005", 1);
        }
        JSONObject jsonObject;
        try {
            jsonObject = JSON.parseObject(response);
            LOGGER.error(jsonObject.toJSONString());
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return fail("105005");
        }
        String retCode = jsonObject.getString("retCode");
        // 1：成功，0:失败
        if ("SUCCESS".equals(retCode)) {
            // 获取支付链接
            String payUrl = jsonObject.getString("payUrl");
//            String payCode = jsonObject.getString("code");
            resultMap.put("urlPay", payUrl);
//            resultMap.put("payCode", payCode);
            rechargeRecords.setUrlPay(payUrl);
            // 平台订单号
            String PfOrderNum = jsonObject.getString("platOrder");
            rechargeRecords.setPfOrderNum(PfOrderNum);
            // 将订单加入到未支付队列中
            GlobalDelayQueue.orderQueue.add(rechargeRecords);
            rechargeRecordsService.saveRtId(rechargeRecords);
            if (rechargeRecords.getIsFirstCharge()==2){
                // [QPGameUserDB].[dbo].[PlayerActiveInfo]这个表的 activeid=4 subActveid=1的ispick重置为1
                Db.update("update [QPGameUserDB].[dbo].[PlayerActiveInfo] set IsPick=1 where ActiveID =4 and SubActiveID=1 and UserID=#{userId}",
                        CMap.init().set("userId",rechargeRecords.getUserId()));
            }
            return json(resultMap, "105006");
        } else {
            rechargeRecords.setMsg(jsonObject.getString("retMsg"));
            rechargeRecords.setOrderStatus(3);
            rechargeRecordsService.saveRtId(rechargeRecords);
            return json(resultMap, "105005", 1);
        }
    }
}
