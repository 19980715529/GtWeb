package com.smallchill.system.treasure.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.common.base.BaseController;
import com.smallchill.common.vo.ShiroUser;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.grid.BladePage;
import com.smallchill.core.toolbox.kit.CharsetKit;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.kit.URLKit;
import com.smallchill.game.service.CommonService;
import com.smallchill.system.treasure.meta.intercept.ExchangeReviewValidator;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.service.ExchangeReviewService;
import com.smallchill.system.treasure.model.GoldChangeRecord;
import com.smallchill.system.treasure.model.UserWin;
import com.smallchill.system.treasure.utils.HttpClientUtils;
import com.smallchill.system.treasure.utils.RechargeExchangeCommon;
import com.smallchill.system.treasure.utils.SendHttp;
import com.smallchill.system.treasure.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.smallchill.core.constant.ConstEmail.*;
import static com.smallchill.core.constant.ConstKey.*;
import static com.smallchill.core.constant.ConstUrl.*;

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
    /*
    查询兑换列表
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
        exchangeReview.setChannelId(exchange.getChannelId());
        // 判断是否通过审核
        String response ="";
        int progress= exchangeReview.getStatus();

        if (progress ==1) {
            // 获取渠道消息
            // 根据渠道id，和pid进行查询
            Map<String, Object> cond = new HashMap<>();
            cond.put("id", exchangeReview.getChannelId());
            //
            Map channel = commonService.getInfoByOne("recharge_channel.get_channel", cond);
            Integer pid = Integer.valueOf(channel.get("pid").toString());
            // 计算需要发送到第三方的钱 兑换的钱*（1-渠道税率）
            BigDecimal amount = exchangeReview.getAmount();
            BigDecimal taxRate = new BigDecimal(channel.get("channelTaxRate").toString());
            BigDecimal money = amount.multiply(new BigDecimal("1").subtract(taxRate));
            //
            exchangeReview.setMoney(money.setScale(2, RoundingMode.FLOOR));
            JSONObject respJson;
            int code;
            int status;
            String statusStr;
            // 判断是哪个渠道
            switch (pid) {
                case 1:
                    // 订单状态为1：代表发送订单成功，需要向第三方发起代付请求， 发送请求成功并不代表订单支付成功，需要回调返回支付结果
                    response = SendHttp.sendExchangeRarp(exchangeReview,channel,RARP_EXCHANGE_GCASH_URL);
                    // rarp      Gcash account format error   SIGN_ERROR
                    LOGGER.error(response);
                    if ("".equals(response)) {
                        return error("服务异常，修改失败");
                    }
                    // 获取平台订单号
                    respJson = JSONObject.parseObject(response);
                    code = respJson.getIntValue("code");
                    // 同步请求状态为0代表请求失败，订单变成失败
                    if (code == 0) {
                        exchangeReview.setMsg(respJson.getString("msg"));
                        // 支付失败
                        exchangeReview.setStatus(6);
                    } else {
                        status = respJson.getJSONObject("data").getIntValue("status");
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
                    break;
                case 4:
                    response = SendHttp.sendExchangeSafe(exchangeReview, channel);
                    LOGGER.error(response);
                    if ("".equals(response)) {
                        return error("服务异常，修改失败");
                    }
                    // 获取平台订单号
                    respJson = JSONObject.parseObject(response);
                    statusStr = respJson.getString("status");
                    if ("success".equals(statusStr)) {
                        // 申请成功，需要等待三方回调才能知道最终结果
                        exchangeReview.setStatus(1);
                    } else {
                        // 获取三方反馈
                        exchangeReview.setMsg(respJson.getString("status_mes"));
                        // 申请失败，将订单状态设置为支付失败
                        exchangeReview.setStatus(6);
                    }
                    break;
                case 20:
                    response = SendHttp.sendExchangeMetaPay(exchangeReview,channel);
                    LOGGER.error(response);
                    if ("".equals(response)) {
                        return error("fail");
                    }
                    // 获取平台订单号
                    respJson = JSONObject.parseObject(response);
                    // 获取请求状态
                    code = respJson.getIntValue("platRespCode");
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
                    break;
                case 23:
                    // omom 请求的金额必须是整数这里进行处理
                    exchangeReview.setMoney(exchangeReview.getMoney().setScale(0, RoundingMode.FLOOR));
                    response = SendHttp.sendExchangeOmom(exchangeReview,channel);
                    LOGGER.error(response);
                    if ("".equals(response)) {
                        return error("fail");
                    }
                    // 获取平台订单号
                    respJson = JSONObject.parseObject(response);
                    statusStr = respJson.getString("status");
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
                    break;
                case 26:
                    response = SendHttp.sendExchangeAIPay(exchangeReview,channel);
                    LOGGER.error(response);
                    if ("".equals(response)) {
                        return error("fail");
                    }
                    respJson = JSONObject.parseObject(response);
                    code = respJson.getIntValue("code");
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
                    break;
                case 29:
                    response = SendHttp.sendExchangeWePay(exchangeReview,channel);
                    LOGGER.error(response);
                    if ("".equals(response)) {
                        return error("fail");
                    }
                    respJson = JSONObject.parseObject(response);
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
                    break;
                case 32:
                    response = SendHttp.sendExchangeGalaxy(exchangeReview,CLOUDPAY_APPID,CLOUDPAY_KEY,EXCHANGE_CLOUDPAY_URL,channel);
                    LOGGER.error(response);
                    if ("".equals(response)) {
                        return error("fail");
                    }
                    respJson = JSONObject.parseObject(response);
                    status = respJson.getIntValue("status");
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
                    break;
                case 35:
                    response = SendHttp.sendExchangeLetsPay(exchangeReview,channel);
                    LOGGER.error(response);
                    if ("".equals(response)) {
                        return error("fail");
                    }
                    respJson = JSONObject.parseObject(response);
                    String retCode = respJson.getString("retCode");
                    // 成功
                    if ("SUCCESS".equals(retCode)) {
                        // 请求成功 ,获取平台订单号
                        exchangeReview.setStatus(1);
                        // 获取平台订单号
                        String platOrder = respJson.getString("platOrder");
                        exchangeReview.setPfOrderNum(platOrder);
                    } else {
                        // 请求失败, 存储失败原因
                        exchangeReview.setMsg(respJson.getString("retMsg"));
                        // 将状态设置为失败
                        exchangeReview.setStatus(6);
                    }
                    break;
                case 38:
                    response = SendHttp.sendExchangeGalaxy(exchangeReview,MHDPAY_APPID,MHDPAY_KEY,EXCHANGE_MHDPAY_URL,channel);
                    LOGGER.error(response);
                    if ("".equals(response)) {
                        return error("fail");
                    }
                    respJson = JSONObject.parseObject(response);
                    status = respJson.getIntValue("status");
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
                    break;
                case 43:
                    response = SendHttp.sendExchangeLuckyPay(exchangeReview,channel);
                    LOGGER.error(response);
                    if ("".equals(response)) {
                        return error("fail");
                    }
                    respJson = JSONObject.parseObject(response);
                    statusStr = respJson.getString("code");
                    // 成功
                    if ("00".equals(statusStr)) {
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
            System.out.println(exchangeReview.getFeedback());
            //  兑换退回 退回时游戏服务器添加金币变动记录
            emailParam.put("goldType",211);
            SendHttp.sendEmail(emailParam);
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
