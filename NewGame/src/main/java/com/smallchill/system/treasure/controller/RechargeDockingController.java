package com.smallchill.system.treasure.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.smallchill.common.base.BaseController;
import com.smallchill.common.task.GlobalDelayQueue;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.core.toolbox.kit.ThreadKit;
import com.smallchill.game.newmodel.Accountsinfo;
import com.smallchill.game.service.CommonService;
import com.smallchill.pay.aipay.model.AIPay;
import com.smallchill.pay.aipay.utils.AIPayUtils;
import com.smallchill.pay.betcatpay.model.BetcatPay;
import com.smallchill.pay.betcatpay.utils.BetcatPayUtils;
import com.smallchill.pay.bpay.model.BPay;
import com.smallchill.pay.bpay.utils.BPayUtils;
import com.smallchill.pay.cloudpay.model.CloudPay;
import com.smallchill.pay.cloudpay.utils.CloudPayUtils;
import com.smallchill.pay.globalPay.model.GlobalPay;
import com.smallchill.pay.globalPay.utils.GlobalPayUtils;
import com.smallchill.pay.luckypay.model.LuckPay;
import com.smallchill.pay.luckypay.utils.LuckyPayUtils;
import com.smallchill.pay.metapay.model.MetaPay;
import com.smallchill.pay.metapay.utils.MetaPayUtils;
import com.smallchill.pay.mhdPay.model.MhdPay;
import com.smallchill.pay.mhdPay.utils.MhdPayUtils;
import com.smallchill.pay.omopay.model.OmoPay;
import com.smallchill.pay.omopay.utils.OmoPayUtils;
import com.smallchill.pay.payplus.model.PayPlus;
import com.smallchill.pay.payplus.model.PhpPayPlus;
import com.smallchill.pay.payplus.utils.PayPlusUtils;
import com.smallchill.pay.rarPay.model.RarPay;
import com.smallchill.pay.rarPay.utils.RarPayUtils;
import com.smallchill.pay.safePay.model.SafePay;
import com.smallchill.pay.safePay.utils.SafePayUtils;
import com.smallchill.pay.wepay.model.WePay;
import com.smallchill.pay.wepay.utils.WePayUtils;
import com.smallchill.system.service.ExchangeReviewService;
import com.smallchill.system.service.RechargeRecordsService;
import com.smallchill.system.treasure.meta.intercept.ExchangePayValidator;
import com.smallchill.system.treasure.model.ChannelVo;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.RechargeExchangeCommon;
import com.smallchill.system.treasure.utils.SendHttp;
import com.smallchill.system.treasure.utils.Utils;
import org.springframework.stereotype.Controller;
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
    private WePay wePay;
    @Resource
    private PayPlus payPlus;
    @Resource
    private PhpPayPlus phpPayPlus;
    @Resource
    private BPay bPay;
    @Resource
    private GlobalPay globalPay;

    @Resource
    private CloudPay cloudPay;

    @Resource
    private MhdPay mhdPay;

    @Resource
    private AIPay aiPay;

    @Resource
    private BetcatPay betcatPay;
    @Resource
    private SafePay safePay;


    /**兑换接口
     * 获取前端参数：
     * 用户id: exchange.userId
     * 兑换金额：exchange.exchangeAmount
     * 银行卡号：exchange.bankNumber
     * 渠道类型: exchange.channelName
     * // 下面参数是菲律宾需要
     * exchange.cardholder：用户名
     * exchange.phone：电话号
     * @return
     */
    @Json
    @Before(ExchangePayValidator.class)
    @PostMapping("/exchangePay")
    public AjaxResult replacePay(){
        Map<String, String[]> parameterMap = HttpKit.getRequest().getParameterMap();
        LOGGER.error(JSON.toJSONString(parameterMap));
        String userId = HttpKit.getRequest().getParameter("exchange.userId");
        String phone = getRequest().getParameter("exchange.phone");
        BigDecimal exAmount = new BigDecimal(HttpKit.getRequest().getParameter("exchange.exchangeAmount"));
        ExchangeReview exchangeReview = mapping("exchange", ExchangeReview.class);
        exchangeReview.setAmount(exAmount);
        exchangeReview.setPhone(phone);
        LOGGER.error(JSON.toJSONString(exchangeReview));
        // 根据用户id查询用户数据
        Map user = commonService.getInfoByOne("player_operate.new_info", CMap.init().set("UserID",exchangeReview.getUserId()));
        if (user==null){
            // 用户不存在
            return fail("105001");
        }
        // 获取用户绑定的电话
        System.out.println(phone);
        if (phone.length()<=0){
            System.out.println("定手机号");
            return fail("105002");
        }
        // 用户来源平台
        exchangeReview.setSourcePlatform(Integer.parseInt(user.get("ClientType").toString()));
        Map<String, Object> resultMap = new HashMap<>();
        Map channel = commonService.getInfoByOne("channel_list.param_max",
                CMap.init().set("type",1).set("clientType",exchangeReview.getSourcePlatform()).set("chName",exchangeReview.getChannelName()));
        //  判断用户的兑换金额是否满足条件;
        BigDecimal amount=exchangeReview.getAmount();
        BigDecimal min = new BigDecimal(String.valueOf(channel.get("min")));
        BigDecimal max = new  BigDecimal(String.valueOf(channel.get("max")));
        // 金币倍率
        String goldPr = channel.get("goldProportion").toString();
        resultMap.put("unit","BRL");
        if ("USDT".equals(exchangeReview.getChannelName())){
            resultMap.put("unit","USDT");
            exchangeReview.setChannelId(19);
        }
        if (amount.intValue()<min.intValue() || amount.intValue()>max.intValue()){
            return fail("105003");
        }
        // 计算兑换金币
        BigDecimal changeGolds=amount.multiply(new BigDecimal(goldPr)).setScale(0,RoundingMode.DOWN);
        exchangeReview.setGold(changeGolds.longValue());
        int code;
        try {
            code = RechargeExchangeCommon.ExchangeAmount(exchangeReview.getUserId(), changeGolds.longValue());
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return fail("105011");
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
                    int id = exchangeReviewService.saveRtId(exchangeReview);
                    exchangeReview.setId(id);
                    // 判断订单是否满足自动审核条件,异步执行下面
                    Runnable runnable =() -> {
                        Boolean temp = AuditConditioningJudgment(exchangeReview);
                        if (temp){
                            autoReview(exchangeReview);
                        }
                    };
                    ThreadKit.excAsync(runnable,false);
                }catch (Exception e){
                    LOGGER.error(e.getMessage());
                    return fail("105011");
                }
                // 发送请求提示前端刷新金币
                SendHttp.sendGame1003(exchangeReview.getUserId());
                return json(resultMap);
            case 1:
                // 用户不存在
                return fail("105001");
            case 2:
                //  未绑定手机
                System.out.println("未绑定手机号");
                return fail("105002");
            case 3:
                // 未发起首充
                return fail("105012");
            case 4:
                // 玩家金币不足
                return fail("105003");
            case 5:
                // 打码量不满足条件
                return fail("105013");
            default:
                return fail("105011");
        }
    }

    /**
     * 判断兑换条件
     */
    private Boolean AuditConditioningJudgment(ExchangeReview exchangeReview){
        // 判断是否开启自动审核
        Map map = Db.selectOne("select * from [RYPlatformManagerDB].[dbo].AutoReviewConfig", null);
        JSONObject auto = JSONObject.parseObject(JSON.toJSONString(map));
        if (auto.getIntValue("auto")==1){
            // 判断大渠道是否关闭
            Map channel = Db.selectOne("select id from Pay_Channel where isExchange=1 and cname=#{cname} order by sort",
                    CMap.init().set("cname",exchangeReview.getChannelName()));
            if (channel==null || channel.isEmpty()){
                return false;
            }
            // 判断小渠道是否关闭
            Map minChannel = Db.selectOne("select * from Pay_ChannelPool where cid=#{cid} and isOpen=1 AND type=1 and isDel=0 ORDER BY sort",
                    CMap.init().set("cid",channel.get("id")));
            if (minChannel==null || minChannel.isEmpty()){
                return false;
            }
            // 判断用户是否是内部员工
            Accountsinfo accountsinfo = Blade.create(Accountsinfo.class).findById(exchangeReview.getUserId());
            if (accountsinfo.getIsInnerMember()==1){
                return false;
            }
            // 判断用户是否有备注
            if (accountsinfo.getTipsname()!=null && accountsinfo.getTipsname().length()>0){
                return false;
            }
            // 当日所有的充提差是否小于0
            Map info = Db.selectOne("select * from [QPGameUserDB].[dbo].[PlayerSocreInfo] where Userid=#{userId}",
                    CMap.init().set("userId", exchangeReview.getUserId()));
            // 判断用户总充值金额
            BigDecimal TotalRecharge = new BigDecimal(info.get("TotalRecharge").toString());
            if (auto.getIntValue("param1")==1){
                if (TotalRecharge.intValue()<=0){
                    return false;
                }
            }
            // 用户今日充值金额
            String TodayRecharge = info.get("TodayRecharge").toString();
            if (auto.getIntValue("param2")==1){
                // 判断用户当日是否有充值
                int i = Integer.parseInt(TodayRecharge);
                if (i<=0){
                    return false;
                }
            }
            // 用户总兑换金额
            BigDecimal TotalWithDraw = new BigDecimal(info.get("TotalWithDraw").toString()).add(exchangeReview.getAmount());
            // 用户充提差
            BigDecimal dif = TotalRecharge.subtract(TotalWithDraw);
            if (dif.intValue()<auto.getIntValue("param3")){
                // 判断用户充提差是否满足条件
                return false;
            }
            // 判断用户充提倍速是否满足条件，充值金额/兑换金额
            BigDecimal divide = TotalRecharge.divide(TotalWithDraw, 2, RoundingMode.DOWN);
            if (divide.floatValue()<auto.getFloatValue("param4")){
                return false;
            }
            // 判断单次兑换金额是否满足条件
            if (exchangeReview.getAmount().intValue()>auto.getIntValue("param5")){
                return false;
            }
            // 今日兑换是否超过条件
            BigDecimal todayWithDraw = new BigDecimal(info.get("TodayWithDraw").toString());
            if (todayWithDraw.intValue() < auto.getIntValue("param6")){
                exchangeReview.setChannelId(Integer.parseInt(minChannel.get("id").toString()));
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    @Resource
    private MetaPay metaPay;
    @Resource
    private OmoPay omoPay;
    /**
     * 自动审核
     */
    private void autoReview(ExchangeReview exchangeReview) {
        Map channel = commonService.getInfoByOne("channel_list.exchange_one",
                CMap.init().set("id", exchangeReview.getChannelId()).set("clientType", exchangeReview.getSourcePlatform()));
        BigDecimal fee = new BigDecimal(channel.get("fee").toString());
        Integer pid = Integer.parseInt(channel.get("pid").toString());
        exchangeReview.setChannelId(pid);
        // 计算需要发送到第三方的钱 兑换的钱*（1-渠道税率）
        BigDecimal amount = exchangeReview.getAmount();
        BigDecimal taxRate = new BigDecimal(channel.get("channelTaxRate").toString());
        BigDecimal money = amount.multiply(new BigDecimal("1").subtract(taxRate));
        exchangeReview.setMoney(money.subtract(fee).setScale(2, RoundingMode.FLOOR));
        RechargeExchangeCommon.exc(exchangeReview, channel);
        // 设置自动审核
        exchangeReview.setAuditMethod(1);
        // 判断是哪个渠道
        switch (pid) {
            case 1:
                RarPayUtils.RarPayExchange(exchangeReview, channel,rarPay);
                break;
            case 4:
                SafePayUtils.SafePayExchange(exchangeReview, channel,safePay);
                break;
            case 20:
                MetaPayUtils.MetaPayExchange(exchangeReview,metaPay);
                break;
            case 23:
                OmoPayUtils.OmoPayExchange(exchangeReview, omoPay);
                break;
            case 26:
                AIPayUtils.AIPayExchange(exchangeReview, channel, aiPay);
                break;
            case 29:
                WePayUtils.WePayExchange(exchangeReview, channel, wePay);
                break;
            case 32:
                CloudPayUtils.CloudPayExchange(exchangeReview, cloudPay);
                break;
            case 35:
                String channelName = channel.get("channelName").toString();
                Map<String, String> param;
                if ("pix".equals(channelName)){
                    // 巴西
                    param = JSON.parseObject(JSON.toJSONString(payPlus), new TypeReference<Map<String, String>>(){});
                }else {
                    // 菲律宾
                    param = JSON.parseObject(JSON.toJSONString(phpPayPlus), new TypeReference<Map<String, String>>(){});
                }
                PayPlusUtils.PayPlusExchange(exchangeReview,param);
                break;
            case 2:
                BetcatPayUtils.BetcatPayExchange(exchangeReview,betcatPay);
                break;
            default:
                break;
            case 38:
                MhdPayUtils.MhdPayExchange(exchangeReview, mhdPay);
                break;
            case 49:
                BPayUtils.BPayExchange(exchangeReview, channel, bPay);
                break;
            case 52:
                //GlobalPay
                GlobalPayUtils.GlobalPayExchange(exchangeReview, channel, globalPay);
                break;

        }
        exchangeReviewService.update(exchangeReview);
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
            return fail("105001");
        }
        ArrayList<ChannelVo> channelVos = new ArrayList<>();
        // 获取大渠道 Pay_Channel
        if (type==1){
            String amount = RechargeExchangeCommon.getGold(UserId);
            if ("".equals(amount)){
                return json(null,"105001",1);
            }
            // 查询需求打码量
            long needCode = Long.parseLong(Db.queryStr("select NeedCodingQuantity from [QPGameUserDB].[dbo].[PlayerSocreInfo] where Userid=#{Userid}",
                    CMap.init().set("Userid", UserId)));
            List<Map> payChannel = Db.selectList("select id,cname channel_name,exchangeGear gear,isRecharge,isExchange,code as area from Pay_Channel where isDel=0 order by sort");
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
                if (needCode<0){
                    needCode = 0;
                }
                max_param.put("money",needCode);
                channelVo.getTypes().add(max_param);
            }
        }else {
            List<Map> payChannel = Db.selectList("select id,cname channel_name,isRecharge,isExchange,code as area from Pay_Channel where isDel=0 order by sort");
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
    public AjaxResult first(){
        List<Map> infoList = commonService.getInfoList("recharge_channel.first_list", null);
        return json(infoList);
    }

    private AjaxResult rechargeGlobalPay(RechargeRecords rechargeRecords, JSONObject resultMap,Map<String,Object> channel) {
        String code;
        String PfOrderNum;
        String response;
        JSONObject jsonObject;
        response = GlobalPayUtils.sendRecharge(rechargeRecords,channel, globalPay);
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
//        LOGGER.error(response);
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
        String code;
        String PfOrderNum;
        String response;
        JSONObject jsonObject;
        response = LuckyPayUtils.sendRechargeLuckyPay(rechargeRecords,luckPay);
        LOGGER.error(response);
        if ("".equals(response)) {
            return json(resultMap, "Recharge application failed", 1);
        }
        jsonObject = JSON.parseObject(response);
        code = jsonObject.getString("code");
        if ("00".equals(code)) {
            // 获取支付链接
            String payUrl = jsonObject.getString("backUrl");
            resultMap.put("urlPay", payUrl);
            rechargeRecords.setUrlPay(payUrl);
            // 设置平台订单号
            PfOrderNum = jsonObject.getString("sysOrderNo");
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
        JSONObject jsonObject;
        String response;
        String channelName = channel.get("channelName").toString();
        Map<String, String> param;
        if ("pix".equals(channelName)){
            // 巴西
            param = JSON.parseObject(JSON.toJSONString(payPlus), new TypeReference<Map<String, String>>(){});
        }else {
            // 菲律宾
            param = JSON.parseObject(JSON.toJSONString(phpPayPlus), new TypeReference<Map<String, String>>(){});
        }
        response = PayPlusUtils.recharge(rechargeRecords,param);
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
     * 银河系统支付逻辑
     */
    private AjaxResult rechargeGalaxy(RechargeRecords rechargeRecords, JSONObject resultMap, Map<String,Object> channel,Integer i) {
        JSONObject jsonObject;
        String response;
        if (i==1){
            response = CloudPayUtils.recharge(rechargeRecords,cloudPay,channel);
        }else {
            response = MhdPayUtils.recharge(rechargeRecords,mhdPay,channel);
        }
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
        response = WePayUtils.recharge(rechargeRecords,channel,wePay);
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
            return json(resultMap, "105006");
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
//    private AjaxResult rechargeOmo(RechargeRecords rechargeRecords, JSONObject resultMap,Map<String,Object> channel) {
//        JSONObject jsonObject;
//        String response;
//        response = OmoPayUtils.recharge(rechargeRecords,channel);
//        LOGGER.error(response);
//        if (response.equals("")) {
//            return json(resultMap, "Recharge application failed", 1);
//        }
//        try {
//            jsonObject = JSON.parseObject(response);
//        }catch (Exception e){
//            return fail("Recharge application failed");
//        }
//        int status = jsonObject.getIntValue("status");
//        if (status==1) {
//            // 获取支付地址
//            String payUrl = jsonObject.getString("payUrl");
//            resultMap.put("urlPay", payUrl);
//            rechargeRecords.setUrlPay(payUrl);
//            // 将订单加入到未支付队列中
//            GlobalDelayQueue.orderQueue.add(rechargeRecords);
//            rechargeRecordsService.saveRtId(rechargeRecords);
//            if (rechargeRecords.getIsFirstCharge()==2){
//                // [QPGameUserDB].[dbo].[PlayerActiveInfo]这个表的 activeid=4 subActveid=1的ispick重置为1
//                Db.update("update [QPGameUserDB].[dbo].[PlayerActiveInfo] set IsPick=1 where ActiveID =4 and SubActiveID=1 and UserID=#{userId}",
//                        CMap.init().set("userId",rechargeRecords.getUserId()));
//            }
//            return json(resultMap, "Recharge application success");
//        } else {
//            rechargeRecords.setMsg(jsonObject.getString("msg"));
//            rechargeRecords.setOrderStatus(3);
//            rechargeRecordsService.saveRtId(rechargeRecords);
//            return json(resultMap, "Recharge application failed", 1);
//        }
//    }

}
