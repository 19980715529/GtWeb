package com.smallchill.pay.google.controller;

import com.alibaba.fastjson.JSONObject;
import com.smallchill.common.base.BaseController;
import com.smallchill.common.utils.RateLimit;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.game.service.CommonService;
import com.smallchill.pay.core.intercept.GooglePayValidator;
import com.smallchill.pay.core.intercept.PayValidator;
import com.smallchill.pay.globalPay.model.GlobalPay;
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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pay/GooglePay")
public class GoogleController extends BaseController implements ConstShiro {
    @Resource
    private CommonService commonService;
    @Resource
    private RechargeRecordsService rechargeRecordsService;

    /**
     * 需要的参数,谷歌支付需要额外参数，
     * recharge.skuId 商品编号
     * recharge.isFirstCharge:0普通充值，1首充，2随机充值
     * recharge.userId:用户id
     * recharge.pid: 父渠道id
     * recharge.id: 渠道id
     * 新加：充值挡位id recharge.gear
     * @return
     */
    @PostMapping("/recharge")
    @Transactional
    @Before(GooglePayValidator.class)
    @RateLimit(limit = 1,period = 30)
    public AjaxResult recharge(){
        RechargeRecords rechargeRecords=mapping("recharge", RechargeRecords.class);
        // 根据用户id查询用户数据
        Map user = commonService.getInfoByOne("player_operate.new_info", CMap.init().set("UserID",rechargeRecords.getUserId()));
        JSONObject resultMap = new JSONObject();
        // 判断谷歌未完成订单，笔数是否已经有一笔存在
        List<RechargeRecords> orders = rechargeRecordsService.findBy("orderStatus in (1,3) and userId=#{userId} and channelPid=3", CMap.init().set("userId", rechargeRecords.getUserId()));
        if (orders.size()>0){
            for (RechargeRecords r:orders){
                // 设置为取消支付
                r.setOrderStatus(4);
                rechargeRecordsService.update(r);
            }
        }
        // 获取充值渠道id
        int channelId = Integer.parseInt(HttpKit.getRequest().getParameter("recharge.id"));
        Map<String, Object> info = RechargeExchangeCommon.recharge(rechargeRecords, resultMap, user,commonService,channelId);
        int code = Integer.parseInt(info.get("code").toString());
        if (code==1){
            return fail(info.get("msg").toString());
        }
//        Map channel =(Map) info.get("channel");
        resultMap.put("urlPay",rechargeRecords.getOrderNumber());
//        resultMap.put("payCode",payCode);
        resultMap.put("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        rechargeRecordsService.saveRtId(rechargeRecords);
        return json(resultMap);
    }
}