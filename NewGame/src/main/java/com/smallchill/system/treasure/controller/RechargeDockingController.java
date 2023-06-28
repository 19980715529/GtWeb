package com.smallchill.system.treasure.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.smallchill.common.base.BaseController;
import com.smallchill.common.task.GlobalDelayQueue;
import com.smallchill.common.utils.RateLimit;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.game.service.CommonService;
import com.smallchill.pay.aipay.model.AIPay;
import com.smallchill.pay.aipay.utils.AIPayUtils;
import com.smallchill.pay.bpay.model.BPay;
import com.smallchill.pay.cloudpay.model.CloudPay;
import com.smallchill.pay.cloudpay.utils.CloudPayUtils;
import com.smallchill.pay.globalPay.model.GlobalPay;
import com.smallchill.pay.luckypay.model.LuckPay;
import com.smallchill.pay.luckypay.utils.LuckyPayUtils;
import com.smallchill.pay.mhdPay.utils.MhdPayUtils;
import com.smallchill.pay.omopay.model.OmoPay;
import com.smallchill.pay.payplus.model.PayPlus;
import com.smallchill.pay.payplus.model.SuperPayPlus;
import com.smallchill.pay.metapay.utils.MetaPayUtils;
import com.smallchill.pay.mhdPay.model.MhdPay;
import com.smallchill.pay.omopay.utils.OmoPayUtils;
import com.smallchill.pay.payplus.utils.PayPlusUtils;
import com.smallchill.pay.rarPay.model.RarPay;
import com.smallchill.pay.bpay.utils.BPayUtils;
import com.smallchill.pay.globalPay.utils.GlobalPayUtils;
import com.smallchill.pay.rarPay.utils.RarPayUtils;
import com.smallchill.pay.rpay.model.RPay;
import com.smallchill.pay.rpay.utils.RPayUtils;
import com.smallchill.pay.safePay.utils.SafePayUtils;
import com.smallchill.pay.wepay.utils.WePayUtils;
import com.smallchill.system.service.ExchangeReviewService;
import com.smallchill.system.service.RechargeRecordsService;
import com.smallchill.system.treasure.meta.intercept.ExchangePayValidator;
import com.smallchill.system.treasure.model.*;
import com.smallchill.system.treasure.utils.RechargeExchangeCommon;
import com.smallchill.system.treasure.utils.SendHttp;
import com.smallchill.system.treasure.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/rechargeDock")
public class RechargeDockingController extends BaseController implements ConstShiro {
    // 支付
    @Resource
    private RechargeRecordsService rechargeRecordsService;
    @Resource
    private ExchangeReviewService exchangeReviewService;
    @Resource
    private CommonService commonService;
    @Resource
    private RarPay rarPay;

    @Resource
    private PayPlus payPlus;
    @Resource
    private SuperPayPlus superPayPlus;
    @Resource
    private BPay bPay;
    @Resource
    private GlobalPay globalPay;

    @Resource
    private AIPay aiPay;

    @Resource
    private OmoPay omoPay;
    /**
     * 需要的参数
     * recharge.isFirstCharge:0普通充值，1首充，2随机充值
     * recharge.userId:用户id
     * recharge.topUpAmount：充值的金额
     * recharge.pid: 父渠道id
     * recharge.id: 渠道id
     * recharge.email：用户邮箱
     * recharge.userName：用户名
     * recharge.phone：电话号
     * 响应参数格式
     * Userid userid
     * gold 充值数量
     * gameCoin  游戏币
     * Type	0:充值 1:提现
     * @return
     */
    @Json
    @PostMapping("/phpToUp")
    @Transactional
    public AjaxResult phpRecharge(){
        RechargeRecords rechargeRecords=mapping("recharge", RechargeRecords.class);
        // 根据用户id查询用户数据
        HashMap<String, Object> user_map = new HashMap<>();
        user_map.put("UserID",rechargeRecords.getUserId());
        Map user = commonService.getInfoByOne("player_operate.new_info", user_map);
        JSONObject resultMap = new JSONObject();
        // 获取充值渠道id
        int channelId = Integer.parseInt(HttpKit.getRequest().getParameter("recharge.id"));
        LOGGER.error("小渠道id:" + channelId);
        Map<String, Object> info = RechargeExchangeCommon.recharge(rechargeRecords, resultMap, user,commonService,channelId);
        int code = Integer.parseInt(info.get("code").toString());
        if (code==1){
            return fail(info.get("msg").toString());
        }
        Map channel =(Map) info.get("channel");
        // 判断商家
        int pid = Integer.parseInt(channel.get("pid").toString());
        rechargeRecords.setChannelPid(pid);
        // 钱包统计
        RechargeExchangeCommon.rec(rechargeRecords,channel);
        switch (pid) {
            case 1:
                // RARP
                return rechargeRar(rechargeRecords, resultMap, channel);
            case 2:
                return rechargeOmo(rechargeRecords,resultMap,channel);
            case 3:
                return rechargeLuckyPay(rechargeRecords,resultMap,channel);
            case 4:
                return rechargeGalaxy(rechargeRecords,resultMap,channel);
            default:
                return json(resultMap,"Recharge application failed",1);
//            case 4:
//                // safe
//                return rechargeSafe(rechargeRecords, resultMap, channel);
//            case 20:
//                // MetaPay
//                return rechargeMetaPay(rechargeRecords, resultMap,channel);
//            case 23:
//                // Omo
//                return rechargeOmo(rechargeRecords, resultMap,channel);
//            case 26:
//                // AIPay
//                return rechargeAIPay(rechargeRecords, resultMap,channel);
//            case 29:
//                // WePay
//                return rechargeWePay(rechargeRecords, resultMap,channel);
//            case 32:
//                // CloudPay支付
//                return rechargeGalaxy(rechargeRecords,resultMap,channel,1);
//            case 35:
//                // LetsPay支付
//                return rechargeLetsPay(rechargeRecords,resultMap,channel);
//            case 38:
//                // 银河系统MHDPay
//                return rechargeGalaxy(rechargeRecords,resultMap,channel,2);
//            case 49:
//                // BPay
//                return rechargeBPay(rechargeRecords,resultMap,channel);
//            case 52:
//                // GlobalPay
//                return rechargeGlobalPay(rechargeRecords,resultMap,channel);
        }
    }


    /**兑换接口
     * 获取前端参数：
     * 用户id: exchange.userId
     * 兑换金额：exchange.exchangeAmount
     * 银行卡号：exchange.bankNumber
     * 渠道类型: exchange.channelName
     * 用户名：exchange.cardholder
     * 电话号：exchange.phone
     * @return
     */
    @Json
    @Before(ExchangePayValidator.class)
    @PostMapping("/exchangePay")
    public AjaxResult replacePay(){
        String userId = HttpKit.getRequest().getParameter("exchange.userId");
        BigDecimal examouont = new BigDecimal(HttpKit.getRequest().getParameter("exchange.exchangeAmount"));
        ExchangeReview exchangeReview = mapping("exchange", ExchangeReview.class);
        String name = exchangeReview.getCardholder().trim();
        exchangeReview.setCardholder(name);
        exchangeReview.setAmount(examouont);
        // 根据用户id查询用户数据
        HashMap<String, Object> user_map = new HashMap<>();
        user_map.put("UserID",exchangeReview.getUserId());
        Map user = commonService.getInfoByOne("player_operate.new_info", user_map);
        if (user==null){
            // 用户不存在
            return fail("Userid does not exist");
        }
        // 获取用户绑定的电话
        String phone = exchangeReview.getPhone();
        if ("".equals(phone)){
            return fail("You need to bind your cell phone first");
        }
        // 用户来源平台
        exchangeReview.setSourcePlatform(Integer.parseInt(user.get("ClientType").toString()));
        Map<String, Object> resultMap = new HashMap<>();
//        Map channel = commonService.getInfoByOne("recharge_channel.find_one_channel", exchangeReview);
        Map channel = commonService.getInfoByOne("channel_list.param_max",
                CMap.init().set("type",1).set("clientType",exchangeReview.getSourcePlatform()).set("chName",exchangeReview.getChannelName()));
        //  判断用户的兑换金额是否满足条件;
        BigDecimal amount=exchangeReview.getAmount();
        BigDecimal min = new BigDecimal(String.valueOf(channel.get("min")));
        BigDecimal max = new  BigDecimal(String.valueOf(channel.get("max")));
        // 金币倍率
        String goldPr = channel.get("goldProportion").toString();
        resultMap.put("unit","PHP");
        if ("USDT".equals(exchangeReview.getChannelName())){
            resultMap.put("unit","USDT");
            exchangeReview.setChannelId(19);
        }
        if (amount.intValue()<min.intValue() || amount.intValue()>max.intValue()){
            return json(null,"兑换的金额不满足渠道条件",2);
        }
        // 计算兑换金币
        BigDecimal changeGolds=amount.multiply(new BigDecimal(goldPr)).setScale(0,RoundingMode.DOWN);
        // 获取总赢配置
        BigDecimal winConfig = new BigDecimal(channel.get("winConf").toString());
        // 计算消耗的总赢兑换金额*金币比例*金币倍率*总赢倍率；
        BigDecimal win = amount.multiply(new BigDecimal(goldPr)).multiply(winConfig).setScale(0,RoundingMode.DOWN);
        exchangeReview.setConsumptionCode(win.longValue());
        exchangeReview.setGold(changeGolds.longValue());
        int code;
        try {
            int i = winConfig.multiply(new BigDecimal("10000")).intValue();
            code = RechargeExchangeCommon.ExchangeAmount(exchangeReview.getUserId(), changeGolds.longValue(), i);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return fail("");
        }
        switch (code){
            case 0:
                try {
                    // 生成兑换订单
                    String orderNo = Utils.getOrderNum(Integer.valueOf(userId));
                    // 设置为待审核
                    exchangeReview.setOrderNumber(orderNo);
                    exchangeReview.setCreateTime(new Date());
                    exchangeReview.setStatus(2);
                    // 生成兑换订单
                    exchangeReviewService.saveRtId(exchangeReview);
                }catch (Exception e){
                    return json(null,e.getMessage(),1);
                }
                // 发送请求提示前端刷新金币
                SendHttp.sendGame1003(exchangeReview.getUserId());
                return json(resultMap);
            case 1:
                return fail("Userid does not exist");
            case 2:
                return fail("Unbound phone");
            case 3:
                return fail("Insufficient exchange quota");
            case 4:
                return fail("Lack of player gold");
            default:
                return fail("");
        }
    }


    /**
     * 充值兑换渠道获取
     * @param UserId
     * @param type
     * @param cid
     * @return
     */
    @Json
    @PostMapping("/all_channel")
    public AjaxResult getAllChannel(@RequestParam Integer UserId, @RequestParam Integer type, @RequestParam Integer cid){
        //判断用户是否存在
        Map user = commonService.getInfoByOne("player_operate.new_info", CMap.init().set("UserId",UserId));
        if (user==null){
            // 用户不存在
            return fail("Userid does not exist");
        }
        ArrayList<ChannelVo> channelVos = new ArrayList<>();
        // 获取大渠道 Pay_Channel
        if (type==1){
            // 查询用户用户总赢
            BigDecimal totalWin = RechargeExchangeCommon.getUserWin(Integer.valueOf(UserId));
            String amount = RechargeExchangeCommon.getGold(Integer.valueOf(UserId));
            if ("".equals(amount)){
                return json(null,"用户不存在",1);
            }
            List<Map> payChannel = Db.selectList("select id,cname channel_name,exchangeGear gear,isRecharge,isExchange from Pay_Channel order by sort");
            for (Map map:payChannel) {
                ChannelVo channelVo = JSON.parseObject(JSON.toJSONString(map), ChannelVo.class);
                // 兑换时需要兑换比率
                String exchangeGear = map.get("gear").toString().trim();
                String[] split = exchangeGear.split(",");
                List<Integer> gear = Arrays.stream(split)
                        .map(Integer::valueOf)
                        .collect(Collectors.toList());
                channelVo.setExchangeGear(gear);
                channelVos.add(channelVo);
                if (channelVo.getIsExchange()==0){
                    continue;
                }
                // 获取最大参数
                CMap param =CMap.init().set("clientType", cid).set("type", type).set("cid", map.get("id"));
                Map max_param = commonService.getInfoByOne("channel_list.param_max", param);
                // 获取其中一条
                Map ch = commonService.getInfoByOne("channel_list.param_one", param);
                if (ch==null){
                    continue;
                }
                max_param.put("name",map.get("channel_name"));
                max_param.put("isLabel",ch.get("isLabel"));
                max_param.put("unit",ch.get("unit"));
                max_param.put("mcName",ch.get("mcName"));
                // 获取金币
                BigDecimal gpt = new BigDecimal(max_param.get("goldProportion").toString());
                // 根据用户总赢计算可以兑换的钱  用户总赢/10000/1.5
                BigDecimal fee1 = totalWin.divide(gpt, RoundingMode.DOWN).divide(new BigDecimal(max_param.get("winConf").toString()), RoundingMode.DOWN);
                // 根据用户金币计算能够兑换的钱  用户金币/金币倍率
                BigDecimal am = new BigDecimal(amount).divide(gpt,RoundingMode.DOWN);
                int fee = Math.min(fee1.intValue(), am.intValue());
                if (fee<0.01){
                    fee = 0;
                }
                max_param.put("money",fee);
                channelVo.getTypes().add(max_param);
            }
        }else {
            List<Map> payChannel = Db.selectList("select id,cname channel_name,isRecharge,isExchange from Pay_Channel order by sort");
            for (Map map:payChannel) {
                ChannelVo channelVo = JSON.parseObject(JSON.toJSONString(map), ChannelVo.class);
                channelVos.add(channelVo);
                if (channelVo.getIsRecharge()==0){
                    continue;
                }
                // 根据订单类型，包id进行查询
                List<Map> infoList = commonService.getInfoList("channel_list.new_list",
                        CMap.init().set("clientType",cid).set("type",type).set("cname",map.get("channel_name")));

                List<Map> channels = infoList.stream().peek(map1 ->
                        {
                            map1.put("channelName", map.get("channel_name"));
                        })
                        .collect(Collectors.toList());
                channelVo.setTypes(channels);
            }
        }
        return json(channelVos);
    }


    /**
     * 查询充值记录
     * params userId:用户id  订单状态，1：待支付，2：已完成，3：已失败
     */
    @Json
    @PostMapping("/recharge")
    public AjaxResult queryRecharge(){
        String userId = HttpKit.getRequest().getParameter("userId");
        Map<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        List<Map> rechargeList = commonService.getInfoList("recharge.user_recharge_list", map);
        return json(rechargeList);
    }
    /**
     *查询兑换记录
     * 用户id: userId
     */
    @Json
    @PostMapping("/exchange")
    public AjaxResult queryExchange(){
        String userId = HttpKit.getRequest().getParameter("userId");
        Map<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        List<Map> exchangeList = commonService.getInfoList("exchange_records.user_exchange_list", map);
        // 兑换进度，1：待支付，2：待审合，3：成功已关闭，4：已完成,5：已退回，6: 支付失败 ,7：退回关闭； 8：待发送，1:等待，2：成功 3：失败
        for (Map exchange:exchangeList) {
            String status = String.valueOf(exchange.get("status"));
            if (status.equals("1") || status.equals("2") || status.equals("8")){
                // 待支付、待审核、待发送
                exchange.put("status",1);
            }else if(status.equals("3") || status.equals("4")){
                // 支付完成，完成关闭
                exchange.put("status",2);
            }else if (status.equals("6") || status.equals("7") || status.equals("5")){
                // 失败,已经退回
                exchange.put("status",3);
            }
        }
        return json(exchangeList);
    }
    /**
     * 获取首充配置
     */
    @Json
    @GetMapping("/firstrecharge")
    @RateLimit(limit = 1,period = 1)
    public AjaxResult first(){
        Map first = commonService.getInfoByOne("recharge_channel.first_list", null);
        return json(first);
    }

    private AjaxResult rechargeGlobalPay(RechargeRecords rechargeRecords, JSONObject resultMap,Map<String,Object> channel) {
        String code;
        String PfOrderNum;
        String response;
        JSONObject jsonObject;
        response = GlobalPayUtils.sendRecharge(rechargeRecords,channel, globalPay);
        LOGGER.error(response);
        if ("".equals(response)) {
            return json(resultMap, "Recharge application failed", 1);
        }
        jsonObject = JSON.parseObject(response);
        code = jsonObject.getString("code");
        if ("10000".equals(code)) {
            // 获取支付链接
            String payUrl = jsonObject.getString("payUrl");
            resultMap.put("urlPay", payUrl);
            rechargeRecords.setUrlPay(payUrl);
            // 设置平台订单号
            PfOrderNum = jsonObject.getString("outTradeNo");
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
            rechargeRecords.setMsg(jsonObject.getString("msg"));
            rechargeRecords.setOrderStatus(3);
            rechargeRecordsService.saveRtId(rechargeRecords);
            return json(resultMap, "Recharge application failed", 1);
        }
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
    @Resource
    private LuckPay luckPay;
    /**
     * LuckyPay支付处理
     * @param rechargeRecords
     * @param resultMap
     * @return
     */
    private AjaxResult rechargeLuckyPay(RechargeRecords rechargeRecords, JSONObject resultMap,Map<String,Object> channel) {
        String response = LuckyPayUtils.sendRechargeLuckyPay(rechargeRecords,luckPay);
        LOGGER.error(response);
        if ("".equals(response)) {
            return json(resultMap, "Recharge application failed", 1);
        }
        JSONObject jsonObject = JSON.parseObject(response);
        String code = jsonObject.getString("code");
        if ("00".equals(code)) {
            // 获取支付链接
            String payUrl = jsonObject.getString("backUrl");
            resultMap.put("urlPay", payUrl);
            rechargeRecords.setUrlPay(payUrl);
            // 设置平台订单号
            String PfOrderNum = jsonObject.getString("sysOrderNo");
            rechargeRecords.setPfOrderNum(PfOrderNum);
            // 将订单加入到未支付队列中
            GlobalDelayQueue.orderQueue.add(rechargeRecords);
            rechargeRecordsService.saveRtId(rechargeRecords);
            return json(resultMap, "Recharge application success");
        } else {
            rechargeRecords.setMsg(jsonObject.getString("msg"));
            rechargeRecords.setOrderStatus(3);
            rechargeRecordsService.saveRtId(rechargeRecords);
            return json(resultMap, "Recharge application failed", 1);
        }
    }
    /**
     * LetsPay充值逻辑
     */
    private AjaxResult rechargeLetsPay(RechargeRecords rechargeRecords, JSONObject resultMap,Map<String,Object> channel) {

        String channelName = channel.get("channelName").toString();
        Map<String, String> param;
        if ("super".equals(channelName)){
            param = JSON.parseObject(JSON.toJSONString(superPayPlus), new TypeReference<Map<String, String>>(){});
        }else {
            param = JSON.parseObject(JSON.toJSONString(payPlus), new TypeReference<Map<String, String>>(){});
        }
        String response = PayPlusUtils.recharge(rechargeRecords,channel,param);
        LOGGER.error(response);
        if ("".equals(response)) {
            return json(resultMap, "Recharge application failed", 1);
        }
        JSONObject jsonObject = JSON.parseObject(response);
        String retCode = jsonObject.getString("retCode");
        // 1：成功，0:失败
        if ("SUCCESS".equals(retCode)) {
            // 获取支付链接
            String payUrl = jsonObject.getString("payUrl");
            resultMap.put("urlPay", payUrl);
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
            return json(resultMap, "Recharge application success");
        } else {
            rechargeRecords.setMsg(jsonObject.getString("message"));
            rechargeRecords.setOrderStatus(3);
            rechargeRecordsService.saveRtId(rechargeRecords);
            return json(resultMap, "Recharge application failed", 1);
        }
    }
    @Resource
    private RPay rPay;
    /**
     * 银河系统支付逻辑
     */
    private AjaxResult rechargeGalaxy(RechargeRecords rechargeRecords, JSONObject resultMap, Map<String,Object> channel) {
        JSONObject jsonObject;
        String response;
        response = RPayUtils.recharge(rechargeRecords,rPay,channel);
        LOGGER.error(response);
        if ("".equals(response)) {
            return json(resultMap, "Recharge application failed", 1);
        }
        try {
            jsonObject = JSON.parseObject(response);
        }catch (Exception e){
            return fail("Recharge application failed");
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

    /**
     * wepay支付逻辑
     * @param rechargeRecords
     * @param resultMap
     * @return
     */
    private AjaxResult rechargeWePay(RechargeRecords rechargeRecords, JSONObject resultMap,Map<String,Object> channel) {
        JSONObject jsonObject;
        String PfOrderNum;
        String response;
        response = WePayUtils.recharge(rechargeRecords,channel);
        LOGGER.error(response);
        if ("".equals(response)) {
            return json(resultMap, "Recharge application failed", 1);
        }
        jsonObject = JSON.parseObject(response);
        String respCode = jsonObject.getString("respCode");
        if ("SUCCESS".equals(respCode)) {
            // 获取支付链接
            String payUrl = jsonObject.getString("payInfo");
            resultMap.put("urlPay", payUrl);
            rechargeRecords.setUrlPay(payUrl);
            // 设置平台订单号
            PfOrderNum = jsonObject.getString("orderNo");
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
            rechargeRecords.setMsg(jsonObject.getString("tradeMsg"));
            rechargeRecords.setOrderStatus(3);
            rechargeRecordsService.saveRtId(rechargeRecords);
            return json(resultMap, "Recharge application failed", 1);
        }
    }

    /**
     * AIPay支付逻辑
     * @param rechargeRecords
     * @param resultMap
     * @return
     */
    private AjaxResult rechargeAIPay(RechargeRecords rechargeRecords, JSONObject resultMap,Map<String,Object> channel) {
        int code;
        String PfOrderNum;
        String response;
        JSONObject jsonObject;
        response = AIPayUtils.recharge(rechargeRecords,aiPay,channel);
//        LOGGER.error(response);
        if ("".equals(response)) {
            return json(resultMap, "Recharge application failed", 1);
        }
        jsonObject = JSON.parseObject(response);
        code = jsonObject.getIntValue("code");
        if (code == 0) {
            // 获取支付链接
            String payUrl = jsonObject.getJSONObject("data").getString("h5Url");
            resultMap.put("urlPay", payUrl);
            rechargeRecords.setUrlPay(payUrl);
            // 设置平台订单号
            PfOrderNum = jsonObject.getString("orderId");
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
            rechargeRecords.setMsg(jsonObject.getString("msg"));
            rechargeRecords.setOrderStatus(3);
            rechargeRecordsService.saveRtId(rechargeRecords);
            return json(resultMap, "Recharge application failed", 1);
        }
    }

    /**
     * Omo支付逻辑
     */
    private AjaxResult rechargeOmo(RechargeRecords rechargeRecords, JSONObject resultMap,Map<String,Object> channel) {
        JSONObject jsonObject;
        String response;
        response = OmoPayUtils.recharge(rechargeRecords,omoPay);
        LOGGER.error(response);
        if (response.equals("")) {
            return json(resultMap, "Recharge application failed", 1);
        }
        try {
            jsonObject = JSON.parseObject(response);
        }catch (Exception e){
            return fail("Recharge application failed");
        }
        int status = jsonObject.getIntValue("status");
        if (status==1) {
            // 获取支付地址
            String payUrl = jsonObject.getString("payUrl");
            resultMap.put("urlPay", payUrl);
            rechargeRecords.setUrlPay(payUrl);
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
            rechargeRecords.setMsg(jsonObject.getString("msg"));
            rechargeRecords.setOrderStatus(3);
            rechargeRecordsService.saveRtId(rechargeRecords);
            return json(resultMap, "Recharge application failed", 1);
        }
    }

    /**
     * MetaPay支付逻辑
     * @param rechargeRecords
     * @param resultMap
     * @return
     */
    private AjaxResult rechargeMetaPay(RechargeRecords rechargeRecords, JSONObject resultMap,Map<String,Object> channel) {
        JSONObject jsonObject;
        String PfOrderNum;
        int code;
        String response;
        response = MetaPayUtils.recharge(rechargeRecords,channel);
        LOGGER.error(response);
        if (response.equals("")) {
            return json(resultMap, "Recharge application failed", 1);
        }
        jsonObject = JSON.parseObject(response);
        code = jsonObject.getIntValue("platRespCode");
        PfOrderNum = jsonObject.getString("transId");
        rechargeRecords.setPfOrderNum(PfOrderNum);
        if (code == 0) {
            // 获取支付地址
            String payUrl = jsonObject.getString("url");
            resultMap.put("urlPay", payUrl);
            rechargeRecords.setUrlPay(payUrl);
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
            rechargeRecords.setMsg(code + jsonObject.getString("msg"));
            rechargeRecords.setOrderStatus(3);
            rechargeRecordsService.saveRtId(rechargeRecords);
            return json(resultMap, "Recharge application failed", 1);
        }
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
        response = SafePayUtils.recharge(rechargeRecords, channel);
        LOGGER.error(response);
        if (response.equals("")) {
            return json(resultMap, "file", 1);
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
            if (rechargeRecords.getIsFirstCharge()==2){
                // [QPGameUserDB].[dbo].[PlayerActiveInfo]这个表的 activeid=4 subActveid=1的ispick重置为1
                Db.update("update [QPGameUserDB].[dbo].[PlayerActiveInfo] set IsPick=1 where ActiveID =4 and SubActiveID=1 and UserID=#{userId}",
                        CMap.init().set("userId",rechargeRecords.getUserId()));
            }
            return json(resultMap, "Recharge application success");
        } else {
            rechargeRecords.setMsg(jsonObject.getString("status_mes"));
            // 关闭订单
            rechargeRecords.setOrderStatus(3);
            rechargeRecordsService.saveRtId(rechargeRecords);
            return json(resultMap, "Recharge application failed", 1);
        }
    }

    /**
     * rarp支付逻辑
     * @param rechargeRecords
     * @param resultMap
     * @param channel
     * @return
     */
    private AjaxResult rechargeRar(RechargeRecords rechargeRecords, JSONObject resultMap, Map channel) {
        String response;
        JSONObject jsonObject = null;
        int code;
        response = RarPayUtils.sendRechargeRar(rechargeRecords, channel,rarPay);
        if (response.equals("")) {
            return json(resultMap, "fail", 1);
        }
        try {
            jsonObject = JSON.parseObject(response);
        }catch (Exception e){
            return fail("Third-party payment anomaly");
        }
        LOGGER.error(jsonObject.toString());
        code = jsonObject.getIntValue("code");
        if (code == 0) {
            // 错误时状态为30
            rechargeRecords.setMsg(jsonObject.getString("msg"));
            rechargeRecordsService.saveRtId(rechargeRecords);
            // 关闭订单
            rechargeRecords.setOrderStatus(3);
            resultMap.put("urlPay", "");
            return json(resultMap, "Recharge application failed", 1);
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
            if (rechargeRecords.getIsFirstCharge()==2){
                // [QPGameUserDB].[dbo].[PlayerActiveInfo]这个表的 activeid=4 subActveid=1的ispick重置为1
                Db.update("update [QPGameUserDB].[dbo].[PlayerActiveInfo] set IsPick=1 where ActiveID =4 and SubActiveID=1 and UserID=#{userId}",
                        CMap.init().set("userId",rechargeRecords.getUserId()));
            }
            return json(resultMap);
        }
    }



}
