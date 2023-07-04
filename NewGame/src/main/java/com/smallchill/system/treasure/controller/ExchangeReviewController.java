package com.smallchill.system.treasure.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.smallchill.common.base.BaseController;
import com.smallchill.common.vo.ShiroUser;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.kit.CharsetKit;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.kit.URLKit;
import com.smallchill.game.service.CommonService;
import com.smallchill.pay.aipay.model.AIPay;
import com.smallchill.pay.aipay.utils.AIPayUtils;
import com.smallchill.pay.betcatpay.model.BetcatPay;
import com.smallchill.pay.betcatpay.utils.BetcatPayUtils;
import com.smallchill.pay.bpay.model.BPay;
import com.smallchill.pay.cloudpay.model.CloudPay;
import com.smallchill.pay.cloudpay.utils.CloudPayUtils;
import com.smallchill.pay.globalPay.model.GlobalPay;
import com.smallchill.pay.luckypay.model.LuckPay;
import com.smallchill.pay.luckypay.utils.LuckyPayUtils;
import com.smallchill.pay.mhdPay.utils.MhdPayUtils;
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
import com.smallchill.pay.safePay.utils.SafePayUtils;
import com.smallchill.pay.wepay.utils.WePayUtils;
import com.smallchill.system.treasure.meta.intercept.ExchangeReviewValidator;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.service.ExchangeReviewService;
import com.smallchill.system.treasure.utils.ExchangeUtils;
import com.smallchill.system.treasure.utils.RechargeExchangeCommon;
import com.smallchill.system.treasure.utils.SendHttp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
兑换审核接口
 */
@Controller
@RequestMapping("/exchangereview")
public class ExchangeReviewController extends BaseController implements ConstShiro {
    @Autowired
    private CommonService commonService;
    @Autowired
    private ExchangeReviewService service;
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
    private CloudPay cloudPay;
    @Resource
    private MhdPay mhdPay;
    @Resource
    private AIPay aiPay;
    @Resource
    private LuckPay luckPay;
    @Resource
    private BetcatPay betcatPay;
    private static String BASE_PATH = "/system/exchangereview/";
    private static String CODE = "exchangereview";
    private static String LIST_SOURCE = "exchange_review.all_list";
    private static String PREFIX = "exchange_review";
    @DoControllerLog(name="进入兑换审核界面")
    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return "/modules/platform/plog/platform_exchange_review.html";
    }
    /**
     *查询兑换列表
     */
    @Json
    @RequestMapping(KEY_LIST)
    //@Permission({ ADMINISTRATOR, ADMIN })
    public Object list() {
        Object gird = new Object();
        String parameter = HttpKit.getRequest().getParameter("where");
        if(StrKit.isBlank(parameter)) {
            return gird;
        }
        if(parameter.contains("%")){
            parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
        }
        // 解析查询条件
        gird = paginateBySelf(LIST_SOURCE);
        return gird;
    }
    /*
    进入修改界面
     */
    @RequestMapping(KEY_EDIT+"/{id}")
    @Permission(ADMINISTRATOR)
    public String edit(@PathVariable Integer id,ModelMap mm){
        Map map = new HashMap();
        map.put("id",id);
        Map infoByOne = commonService.getInfoByOne("exchange_review.one_list", map);
        mm.put("exchange",infoByOne);
        mm.put("code",CODE);
        return BASE_PATH + "exchange_review_eidt.html";
    }
    /*
    修改数据
     */
    @Json
    @RequestMapping(KEY_UPDATE)
    @Before(ExchangeReviewValidator.class)
    @Permission(ADMINISTRATOR)
    public AjaxResult update(@RequestBody String mm) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        ExchangeReview exchange = mapping(PREFIX, ExchangeReview.class);
        // 根据id拿取到兑换订单
        ExchangeReview exchangeReview = service.findById(exchange.getId());
        exchangeReview.setStatus(exchange.getStatus());
        exchangeReview.setFeedback(exchange.getFeedback());
        // 判断当前审核状态
        int progress= exchangeReview.getStatus();
        exchangeReview.setAuditMethod(0);
        if (progress ==1) {
            // 根据渠道id，和pid进行查询
//            Map channel = commonService.getInfoByOne("recharge_channel.get_channel", CMap.init().set("id",exchangeReview.getChannelId()));
            Map channel = commonService.getInfoByOne("channel_list.exchange_one",
                    CMap.init().set("id",exchange.getChannelId()).set("clientType",exchangeReview.getSourcePlatform()));
            BigDecimal fee = new BigDecimal(channel.get("fee").toString());
            Integer pid = Integer.parseInt(channel.get("pid").toString());
            exchangeReview.setChannelId(pid);
            // 计算需要发送到第三方的钱 兑换的钱*（1-渠道税率）
            BigDecimal amount = exchangeReview.getAmount();
            BigDecimal taxRate = new BigDecimal(channel.get("channelTaxRate").toString());
            BigDecimal money = amount.multiply(new BigDecimal("1").subtract(taxRate));
            exchangeReview.setMoney(money.subtract(fee).setScale(2, RoundingMode.FLOOR));
            RechargeExchangeCommon.exc(exchangeReview,channel);
            // 设置人工审核
            // 判断是哪个渠道
            switch (pid) {
                case 35:
                    if (ExchangeUtils.PayPlusExchange(exchangeReview,payPlus)) return error("充值服务器异常");
                    break;
                case 2:
                    if (ExchangeUtils.BetcatPayExchange(exchangeReview,betcatPay)) return error("充值服务器异常");
                    break;
                default:
                    break;
                }
        }else if(progress==4){
            // 兑换完成
            Map<String, Object> emailParam = getEmailConf(4);
            emailParam.put("toUserid",exchangeReview.getUserId());
            emailParam.put("gold",0);
            emailParam.put("goldType",206);
            SendHttp.sendEmail(emailParam);
        }else if(progress==5){
            // 兑换失败需要退回金币
            Map<String, Object> map = new HashMap<>();
            map.put("UserId",exchangeReview.getUserId());
            map.put("userId",exchangeReview.getUserId());
            String gold = getGold(map);
            Long aftAmount = new BigDecimal(gold).add(new BigDecimal(exchangeReview.getGold())).longValue();
            map.put("Amount",aftAmount);
            // 总赢变动后保存到数控
            Map<String, Object> emailParam = getEmailConf(5);
            emailParam.put("toUserid",exchangeReview.getUserId());
            emailParam.put("gold",exchangeReview.getGold());
            // 兑换退回需要发送邮件
            emailParam.put("content",emailParam.get("content")+"["+exchangeReview.getFeedback()+"]");
//            System.out.println(exchangeReview.getFeedback());
            //  兑换退回 退回时游戏服务器添加金币变动记录
            emailParam.put("goldType",211);
            try {
                SendHttp.sendEmail(emailParam);
            }catch (Exception e){
                LOGGER.error("邮件发送失败");
                return error("邮件发送失败");
            }

        }
        // 获取当前操作用户信息
        ShiroUser user = ShiroKit.getUser();
        String username = Func.toStr(user.getName());
        exchangeReview.setOperator(username);
        exchangeReview.setEndTime(new Date());
        boolean temp = service.update(exchangeReview);
        if (temp) {
            CacheKit.removeAll(SYS_CACHE);
            return success(UPDATE_SUCCESS_MSG);
        } else {
            return error(UPDATE_FAIL_MSG);
        }
    }

    private boolean GlobalPayExchange(ExchangeReview exchangeReview, Map channel) {
        String response = GlobalPayUtils.sendExchange(exchangeReview, channel, globalPay);
        if ("".equals(response)){
            return true;
        }
        JSONObject respJson = JSONObject.parseObject(response);
        String statusStr = respJson.getString("code");
        // 成功
        if ("10000".equals(statusStr)) {
            // 请求成功 ,获取平台订单号
            exchangeReview.setStatus(1);
            String sysOrderNo = respJson.getString("outTradeNo");
            exchangeReview.setPfOrderNum(sysOrderNo);
        } else {
            // 请求失败, 存储失败原因
            exchangeReview.setMsg(respJson.getString("msg"));
            // 将状态设置为失败
            exchangeReview.setStatus(6);
        }
        return false;
    }

    private boolean BPayExchange(ExchangeReview exchangeReview, Map channel) {

        String response = BPayUtils.sendExchangeBPay(exchangeReview, channel, bPay);
        if ("".equals(response)){
            return true;
        }
        JSONObject respJson = JSONObject.parseObject(response);
        String code = respJson.getString("code");
        // 成功
        if ("200".equals(code)) {
            // 请求成功 ,获取平台订单号
            exchangeReview.setStatus(1);
            String sysOrderNo = respJson.getJSONObject("data").getString("orderNo");
            exchangeReview.setPfOrderNum(sysOrderNo);
        } else {
            // 请求失败, 存储失败原因
            exchangeReview.setMsg(respJson.getString("message"));
            // 将状态设置为失败
            exchangeReview.setStatus(6);
        }
        return false;
    }

    private boolean LuckPayExchange(ExchangeReview exchangeReview) {

        String response = LuckyPayUtils.sendExchangeLuckyPay(exchangeReview,luckPay);
        LOGGER.error(response);
        if ("".equals(response)) {
            return true;
        }
        JSONObject respJson = JSONObject.parseObject(response);
        String code = respJson.getString("code");
        // 成功
        if ("00".equals(code)) {
            // 请求成功 ,获取平台订单号
            exchangeReview.setStatus(1);
            String sysOrderNo = respJson.getString("sysOrderNo");
            exchangeReview.setPfOrderNum(sysOrderNo);
        } else {
            // 请求失败, 存储失败原因
            exchangeReview.setMsg(respJson.getString("msg"));
            // 将状态设置为失败
            exchangeReview.setStatus(6);
        }
        return false;
    }

    private boolean MhdPayExchange(ExchangeReview exchangeReview) {

        String response = MhdPayUtils.exchange(exchangeReview,mhdPay);
        LOGGER.error(response);
        if ("".equals(response)) {
            return true;
        }
        JSONObject respJson = JSONObject.parseObject(response);
        int status = respJson.getIntValue("status");
        // 成功
        if (status == 1) {
            // 请求成功 ,获取平台订单号
            exchangeReview.setStatus(1);
        } else {
            // 请求失败, 存储失败原因
            exchangeReview.setMsg(respJson.getString("message"));
            // 将状态设置为失败
            exchangeReview.setStatus(6);
        }
        return false;
    }


    private boolean CloudPayExchange(ExchangeReview exchangeReview) {

        String response = CloudPayUtils.exchange(exchangeReview,cloudPay);
        LOGGER.error(response);
        if ("".equals(response)) {
            return true;
        }
        JSONObject respJson = JSONObject.parseObject(response);
        int status = respJson.getIntValue("status");
        // 成功
        if (status == 1) {
            // 请求成功 ,获取平台订单号
            exchangeReview.setStatus(1);
        } else {
            // 请求失败, 存储失败原因
            exchangeReview.setMsg(respJson.getString("message"));
            // 将状态设置为失败
            exchangeReview.setStatus(6);
        }
        return false;
    }

    private boolean WePayExchange(ExchangeReview exchangeReview, Map channel) {

        String response = WePayUtils.exchange(exchangeReview, channel);
        LOGGER.error(response);
        if ("".equals(response)) {
            return true;
        }
        JSONObject respJson = JSONObject.parseObject(response);
        String result = respJson.getString("respCode");
        if ("SUCCESS".equals(result)) {
            // 请求成功 ,获取平台订单号
            String PfOrderNum = respJson.getString("tradeNo");
            exchangeReview.setPfOrderNum(PfOrderNum);
            exchangeReview.setStatus(1);
        } else {
            // 请求失败, 存储失败原因
            exchangeReview.setMsg(respJson.getString("errorMsg"));
            // 将状态设置为失败
            exchangeReview.setStatus(6);
        }
        return false;
    }

    private boolean AIPayExchange(ExchangeReview exchangeReview, Map channel) {

        String response = AIPayUtils.exchange(exchangeReview,aiPay, channel);
        LOGGER.error(response);
        if ("".equals(response)) {
            return true;
        }
        JSONObject respJson = JSONObject.parseObject(response);
        int code = respJson.getIntValue("code");
        if (code == 0) {
            // 请求成功, 获取平台订单号
            String PfOrderNum = respJson.getJSONObject("data").getString("payoutId");
            exchangeReview.setPfOrderNum(PfOrderNum);
            exchangeReview.setStatus(1);
        } else {
            // 请求失败, 存储失败原因
            exchangeReview.setMsg(respJson.getString("error"));
            // 将状态设置为失败
            exchangeReview.setStatus(6);
        }
        return false;
    }

    private boolean OmoPayExchange(ExchangeReview exchangeReview, Map channel) {

        // omom 请求的金额必须是整数这里进行处理
        exchangeReview.setMoney(exchangeReview.getMoney().setScale(0, RoundingMode.FLOOR));
        String response = OmoPayUtils.exchange(exchangeReview, channel);
        LOGGER.error(response);
        if ("".equals(response)) {
            return true;
        }
        // 获取平台订单号
        JSONObject respJson = JSONObject.parseObject(response);
        String statusStr = respJson.getString("status");
        if ("success".equals(statusStr)) {
            // 请求成功, 获取平台订单号
            String PfOrderNum = respJson.getString("transaction_id");
            exchangeReview.setPfOrderNum(PfOrderNum);
            exchangeReview.setStatus(1);
        } else {
            // 请求失败, 存储失败原因
            exchangeReview.setMsg(respJson.getString("msg"));
            // 将状态设置为失败
            exchangeReview.setStatus(6);
        }
        return false;
    }

    private boolean MetaPayExchange(ExchangeReview exchangeReview, Map channel) {

        String response = MetaPayUtils.exchange(exchangeReview, channel);
        LOGGER.error(response);
        if ("".equals(response)) {
            return true;
        }
        // 获取平台订单号
        JSONObject respJson = JSONObject.parseObject(response);
        // 获取请求状态
        int code = respJson.getIntValue("platRespCode");
        if (code == 0) {
            // 请求成功, 获取平台订单号
            String PfOrderNum = respJson.getString("transId");
            exchangeReview.setPfOrderNum(PfOrderNum);
            exchangeReview.setStatus(1);
        } else {
            // 请求失败, 存储失败原因
            exchangeReview.setMsg(respJson.getString("msg"));
            // 将状态设置为失败
            exchangeReview.setStatus(6);
        }
        return false;
    }

    private boolean SafePayExchange(ExchangeReview exchangeReview, Map channel) {


        String response = SafePayUtils.exchange(exchangeReview, channel);
        LOGGER.error(response);
        if ("".equals(response)) {
            return true;
        }
        // 获取平台订单号
        JSONObject respJson = JSONObject.parseObject(response);
        String status = respJson.getString("status");
        if ("success".equals(status)) {
            // 申请成功，需要等待三方回调才能知道最终结果
            exchangeReview.setStatus(1);
        } else {
            // 获取三方反馈
            exchangeReview.setMsg(respJson.getString("status_mes"));
            // 申请失败，将订单状态设置为支付失败
            exchangeReview.setStatus(6);
        }
        return false;
    }

    private boolean RarPayExchange(ExchangeReview exchangeReview, Map channel) {
        // 订单状态为1：代表发送订单成功，需要向第三方发起代付请求， 发送请求成功并不代表订单支付成功，需要回调返回支付结果
        String response = RarPayUtils.sendExchangeRar(exchangeReview, channel,rarPay);
        // rarp      Gcash account format error   SIGN_ERROR
        LOGGER.error(response);
        if ("".equals(response)) {
            return true;
        }
        // 获取平台订单号
        JSONObject respJson = JSONObject.parseObject(response);
        int code = respJson.getIntValue("code");
        // 同步请求状态为0代表请求失败，订单变成失败
        if (code == 0) {
            exchangeReview.setMsg(respJson.getString("msg"));
            // 支付失败
            exchangeReview.setStatus(6);
        } else {
            int status = respJson.getJSONObject("data").getIntValue("status");
            // 订单状态:0=未支付;10=支付中;20=支付成功;30=支付失败
            if (status == 0) {
                // 未支付，将订单状态设置为待支付
                exchangeReview.setStatus(1);
            } else if (status == 10) {
                // 支付中，将订单状态设置为待支付
                exchangeReview.setStatus(1);
            } else if (status == 20) {
                // 支付成功，将订单状态设置为已完成
                exchangeReview.setStatus(4);
            } else if (status == 30) {
                // 支付失败，将订单状态设置为支付失败
                exchangeReview.setStatus(6);
            }
        }
        return false;
    }

    // 查询用户金币
    private String getGold(Map<String, Object> userMap) {
        Map feeMap = commonService.getInfoByOne("db_Shop_Prop_UserProp.getGold", userMap);
        if (feeMap == null){
            return "";
        }
        // 获取到用户的金币
        return String.valueOf(feeMap.get("Amount"));
    }
    /**
     * 查询发送成功邮件
     */
    public Map getEmailConf(Integer id){
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",id);
        return commonService.getInfoByOne("emailmodel.one_email", map);
    }
}
