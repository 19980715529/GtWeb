package com.smallchill.system.treasure.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.common.base.BaseController;
import com.smallchill.common.task.GlobalDelayQueue;
import com.smallchill.common.utils.RateLimit;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.game.newmodel.gameuserdb.AaZzLogPropchange;
import com.smallchill.game.service.CommonService;
import com.smallchill.system.service.ExchangeReviewService;
import com.smallchill.system.service.RechargeRecordsService;
import com.smallchill.system.treasure.model.*;
import com.smallchill.system.treasure.utils.RechargeExchangeCommon;
import com.smallchill.system.treasure.utils.SendHttp;
import com.smallchill.system.treasure.utils.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static com.smallchill.core.constant.ConstKey.*;
import static com.smallchill.core.constant.ConstUrl.RECHARGE_GALAXY_URL;
import static com.smallchill.core.constant.ConstUrl.RECHARGE_MHDPAY_URL;

@Controller
@RequestMapping("/rechargeDock")
public class RechargeDockingController extends BaseController implements ConstShiro {
    // 支付
    @Autowired
    private RechargeRecordsService rechargeRecordsService;
    @Autowired
    private ExchangeReviewService exchangeReviewService;
    @Autowired
    private CommonService commonService;

    /**
     * 需要的参数
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
        // 判断是否首充
        RechargeRecords rechargeRecords=mapping("recharge", RechargeRecords.class);
        // 根据用户id查询用户数据
        HashMap<String, Object> user_map = new HashMap<>();
        user_map.put("UserID",rechargeRecords.getUserId());
        Map user = commonService.getInfoByOne("player_operate.new_info", user_map);
        if (user==null){
            return json(null,"用户名不存在",1);
        }
        // 获取用户绑定的电话
        String phone = user.get("bindPhone").toString();
        if (phone!=null){
            rechargeRecords.setPhone(phone);
        }
        rechargeRecords.setPackageName(Integer.valueOf(user.get("ClientType").toString()));
        JSONObject resultMap = new JSONObject();
        // 用户昵称
        rechargeRecords.setNickname(String.valueOf(user.get("NickName")));
        // 查询渠道
        Map<String,Object> channelMap =new HashMap();
        channelMap.put("id",HttpKit.getRequest().getParameter("recharge.id"));
        Map channel = commonService.getInfoByOne("recharge_channel.one_channel", channelMap);
        // 将渠道数据存储到渠道里面
        rechargeRecords.setChannel_type(String.valueOf(channel.get("name")));
        rechargeRecords.setChannel(String.valueOf(channel.get("channelName")));
        BigDecimal fee= rechargeRecords.getTopUpAmount();
        // 判断是否首充
        if(rechargeRecords.getIsFirstCharge()==1){
            // 判断是否已经首充过  QPGameUserDB   AccountsInfo
            Integer IsFirstRecharge = Db.queryInt("select IsFirstRecharge from [QPGameUserDB].[dbo].[AccountsInfo] where UserID = #{UserId}",
                    CMap.init().set("UserId", rechargeRecords.getUserId()));
            if (IsFirstRecharge==1){
                return fail("The first charge can only be initiated once");
            }
            Map first = commonService.getInfoByOne("recharge_channel.first_list", null);
            BigDecimal gold = new BigDecimal(String.valueOf(first.get("gold")));
            BigDecimal gold_give = new BigDecimal(String.valueOf(first.get("give_gold")));
            rechargeRecords.setGold(gold.add(gold_give).longValue());
        }else {
            BigDecimal max = new BigDecimal(String.valueOf(channel.get("max")));
            BigDecimal min = new BigDecimal(String.valueOf(channel.get("min")));
            // 判断充值的钱是否满足渠道条件
            if (fee.intValue() <min.intValue() || fee.intValue()>max.intValue()){
                return json(resultMap,"你充值的钱不满足渠道充值条件");
            }
            // 获取渠道外赠送比例
            BigDecimal give = new BigDecimal(String.valueOf(channel.get("give")));
            // 渠道倍率goldProportion
            BigDecimal gpr = new BigDecimal(String.valueOf(channel.get("goldProportion")));
            //  充值的钱*金币倍率*赠送比例
            BigDecimal give_gold = fee.multiply(gpr).multiply(give);
            //  充值的钱*10000*金币倍率+渠道外赠送
            BigDecimal get_Gold = fee.multiply(gpr).add(give_gold).setScale(0,RoundingMode.HALF_UP);
            // 将充值金币存储到订单里面
            rechargeRecords.setGold(get_Gold.longValue());
        }
        // 生成订单号
        String orderNo = Utils.getOrderNum(rechargeRecords.getUserId());
        // 设置订单号
        rechargeRecords.setOrderNumber(orderNo);
        // 设置时间
        rechargeRecords.setCreateTime(new Date());
        // 生成请求条件
        String response="";
        // 设置响应结果
        resultMap.put("Userid",rechargeRecords.getUserId());
        resultMap.put("gameCoin",rechargeRecords.getGold()); //  充值的游戏币
        resultMap.put("gold",fee.setScale(2,RoundingMode.HALF_UP)); //  充值的钱
        resultMap.put("type",0);//充值类型
        rechargeRecords.setIsThatTay(0);
        // 判断商家
        int pid = Integer.parseInt(channel.get("pid").toString());
        rechargeRecords.setChannelPid(pid);
        switch (pid) {
            case 1:
                // RARP
                return rechargeRarp(rechargeRecords, resultMap, channel);
            case 4:
                // safe
                return rechargeSafe(rechargeRecords, resultMap, channel);
            case 20:
                // MetaPay
                return rechargeMetaPay(rechargeRecords, resultMap);
            case 23:
                // Omo
                return rechargeOmo(rechargeRecords, resultMap);
            case 26:
                // AIPay
                return rechargeAIPay(rechargeRecords, resultMap);
            case 29:
                // WePay
                return rechargeWePay(rechargeRecords, resultMap);
            case 32:
                // CloudPay支付
                return rechargeGalaxy(rechargeRecords,resultMap,CLOUDPAY_APPID,CLOUDPAY_KEY,RECHARGE_GALAXY_URL);
            case 35:
                // LetsPay支付
                return rechargeLetsPay(rechargeRecords,resultMap);
            case 38:
                // 银河系统CloudPay
                return rechargeGalaxy(rechargeRecords,resultMap,MHDPAY_APPID,MHDPAY_KEY,RECHARGE_MHDPAY_URL);
            default:
                return json(resultMap,"Recharge application failed",1);
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
        // 获取用户提示
        exchangeReview.setTipsName(user.get("tipsName").toString());
        // 用户来源平台
        exchangeReview.setSourcePlatform(user.get("ClientType").toString());
        exchangeReview.setNickname(String.valueOf(user.get("NickName")));
        Map<String, Object> resultMap = new HashMap<>();
        Map channel = commonService.getInfoByOne("recharge_channel.find_one_channel", exchangeReview);
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
     * type:0充值，1：兑换
     * 用户id: UserId
     * @return 需要手续费fee
     */
    @Json
    @PostMapping("/all_channel")
    public AjaxResult channel_list() {
        String type = HttpKit.getRequest().getParameter("type");
        String UserId = HttpKit.getRequest().getParameter("UserId");
        // 获取用户绑定的电话
        // 获取到所有的大渠道名称  mcName
        List<Map> channelMaxList = commonService.getInfoList("recharge_channel.find_all_max_channel", null);
        ArrayList<Object> channels = new ArrayList<>();
        if (Integer.parseInt(type) == 0){
            int i=1;
            for (Map ch:channelMaxList){
                ch.put("type",0);
                // 判断是否大渠道是否包含小渠道
                List<String> num = commonService.queryList("recharge_channel.find_list_count", ch);
                if ("0".equals(num.get(0))){
                    continue;
                }
                // 根据大渠道名称查询所有充值渠道
                List<Map> list = commonService.getInfoList("recharge_channel.all_child", ch);

                RechargeChannel channel =new RechargeChannel();
                if ("USDT".equals(ch.get("channelName").toString())){
                    for (Map l:list) {
                        l.put("name","klsdlslslddsj");
                    }
                }
                channel.setChannel_name(ch.get("channelName").toString());
                channel.setTypes(list);
                channel.setId(i);
                i++;
                channels.add(channel);
            }
        }else {
            // 兑换,需要返回大渠道信息
            // 获取用户金币
            String amount = getGold(Integer.valueOf(UserId));
            if ("".equals(amount)){
                return json(null,"用户不存在",1);
            }
            Integer fee = null;
            // 查询用户的总赢兑换需总赢计算：兑换金额*金币比例*金币倍率*总赢倍率
            // 查询用户用户总赢
            BigDecimal totalWin = RechargeExchangeCommon.getUserWin(Integer.valueOf(UserId));
            int i=1;
            // 遍历大渠道
            for (Map ch:channelMaxList){
                ch.put("type",1);
                // 判断是否打渠道是否包含小渠道
                List<String> num = commonService.queryList("recharge_channel.find_list_count", ch);
                if ("0".equals(num.get(0))){
                    continue;
                }
                RechargeChannel channel =new RechargeChannel();
                Map cha = commonService.getInfoByOne("recharge_channel.find_all_child", ch);
                // 根据大渠道查询渠道区间最小值和最大值，渠道最大值
                Map infoByOne = commonService.getInfoByOne("recharge_channel.find_one_channel", ch);
                infoByOne.put("name",ch.get("channelName"));
                infoByOne.put("isLabel",cha.get("isLabel"));
                infoByOne.put("unit",cha.get("unit"));
                infoByOne.put("mcName",cha.get("mcName"));
                // 获取金币
                BigDecimal gpt = new BigDecimal(infoByOne.get("goldProportion").toString());
                // 根据用户总赢计算可以兑换的钱  用户总赢/10000/1.5
                BigDecimal fee1 = totalWin.divide(gpt,RoundingMode.DOWN).divide(new BigDecimal(infoByOne.get("winConf").toString()), RoundingMode.DOWN);
                // 根据用户金币计算能够兑换的钱  用户金币/金币倍率
                BigDecimal am = new BigDecimal(amount).divide(gpt,RoundingMode.DOWN);
                System.out.println(fee1);
                if (fee1.longValue()>=am.longValue()){
                    fee=am.intValue();
                }else {
                    fee=fee1.intValue();
                }
                if (fee<0.01){
                    fee = 0;
                }
                infoByOne.put("money",fee);
                infoByOne.remove("winConf");
                channel.setChannel_name(ch.get("channelName").toString());
                channel.setId(i);
                channel.getTypes().add(infoByOne);
                channels.add(channel);
            }
        }
        return json(channels);
    }

    /**
     * 获取用户金币
     * @param userId
     * @return
     */
    private String getGold(Integer userId) {
        String amount = Db.queryStr("SELECT Amount FROM [QPGameUserDB].[dbo].[AA_Shop_Prop_UserProp] where User_Id = #{UserId} and Prop_Id = 1",
                CMap.init().set("UserId",userId));
        if (amount == null){
            return "";
        }
        return amount;
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
    /**
     * LetsPay充值逻辑
     */
    private AjaxResult rechargeLetsPay(RechargeRecords rechargeRecords, JSONObject resultMap) {
        JSONObject jsonObject;
        String response;
        response = SendHttp.sendRechargeLetsPay(rechargeRecords);
        LOGGER.error(response);
        if ("".equals(response)) {
            return json(resultMap, "Recharge application failed", 1);
        }
        jsonObject = JSON.parseObject(response);
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
            return json(resultMap, "Recharge application success");
        } else {
            rechargeRecords.setMsg(jsonObject.getString("message"));
            rechargeRecords.setOrderStatus(3);
            rechargeRecordsService.saveRtId(rechargeRecords);
            return json(resultMap, "Recharge application failed", 1);
        }
    }
    /**
     * 银河系统支付逻辑
     */
    private AjaxResult rechargeGalaxy(RechargeRecords rechargeRecords, JSONObject resultMap,String appid,String key,String url) {
        JSONObject jsonObject;
        String response;
//        response = SendHttp.sendRechargeGalaxy(rechargeRecords,GALAXY_KEY,RECHARGE_GALAXY_URL);
        response = SendHttp.sendRechargeGalaxy(rechargeRecords,appid,key,url);
        LOGGER.error(response);
        if ("".equals(response)) {
            return json(resultMap, "Recharge application failed", 1);
        }
        jsonObject = JSON.parseObject(response);
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
    private AjaxResult rechargeWePay(RechargeRecords rechargeRecords, JSONObject resultMap) {
        JSONObject jsonObject;
        String PfOrderNum;
        String response;
        response = SendHttp.sendRechargeWePay(rechargeRecords);
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
    private AjaxResult rechargeAIPay(RechargeRecords rechargeRecords, JSONObject resultMap) {
        int code;
        String PfOrderNum;
        String response;
        JSONObject jsonObject;
        response = SendHttp.sendRechargeAIPay(rechargeRecords);
        LOGGER.error(response);
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
    private AjaxResult rechargeOmo(RechargeRecords rechargeRecords, JSONObject resultMap) {
        JSONObject jsonObject;
        String response;
        response = SendHttp.sendRechargeOmom(rechargeRecords);
        LOGGER.error(response);
        if (response.equals("")) {
            return json(resultMap, "Recharge application failed", 1);
        }
        jsonObject = JSON.parseObject(response);
        int status = jsonObject.getIntValue("status");
        if (status==1) {
            // 获取支付地址
            String payUrl = jsonObject.getString("payUrl");
            resultMap.put("urlPay", payUrl);
            rechargeRecords.setUrlPay(payUrl);
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
     * MetaPay支付逻辑
     * @param rechargeRecords
     * @param resultMap
     * @return
     */
    private AjaxResult rechargeMetaPay(RechargeRecords rechargeRecords, JSONObject resultMap) {
        JSONObject jsonObject;
        String PfOrderNum;
        int code;
        String response;
        response = SendHttp.sendRechargeMetaPay(rechargeRecords);
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
        response = SendHttp.sendRechargeSafe(rechargeRecords, channel);
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
    private AjaxResult rechargeRarp(RechargeRecords rechargeRecords, JSONObject resultMap, Map channel) {
        String response;
        JSONObject jsonObject;
        int code;
        response = SendHttp.sendRechargeRarp(rechargeRecords, channel);
        if (response.equals("")) {
            return json(resultMap, "fail", 1);
        }
        jsonObject = JSON.parseObject(response);
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

//                    int status = jsonObject.getJSONObject("data").getIntValue("status");
            // 生成订单记录
            rechargeRecordsService.saveRtId(rechargeRecords);
            String url = jsonObject.getJSONObject("data").getString("pay_url");
            resultMap.put("urlPay", url);
            rechargeRecords.setUrlPay(url);
            GlobalDelayQueue.orderQueue.add(rechargeRecords);
            // 判断支付状态 订单状态:0=未支付;10=支付中;20=支付成功;30=支付失败
            return json(resultMap);
        }
    }

}
