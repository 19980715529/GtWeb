package com.smallchill.system.treasure.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.common.vo.ShiroUser;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.CharsetKit;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.kit.URLKit;
import com.smallchill.game.service.CommonService;
import com.smallchill.system.service.ExchangeReviewService;
import com.smallchill.system.service.RechargeRecordsService;
import com.smallchill.system.treasure.model.ExchangeReview;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.RechargeExchangeCommon;
import com.smallchill.system.treasure.utils.SendHttp;
import com.smallchill.system.treasure.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description Ustd充值管理
 * @classNameUsdtTransactionController
 * @Date 2023/3/24 17:25
 * @Version 1.0
 **/
@Controller
@RequestMapping("/usdtexchange")
public class UsdtExchangeController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/system/usdtexchange/";
    private static String CODE = "usdtexchange";
    private static String LIST_SOURCE = "usdtexchange.all_list";
    private static String PREFIX = "usdtexchange";
    @Autowired
    private ExchangeReviewService exchangeReviewService;
    @Autowired
    private CommonService commonService;
    /**
     * 接入Usdt管理界面
     */
    @DoControllerLog(name = "进入usdt兑换管理界面")
    @RequestMapping("/")
    public String index(ModelMap mm){
        mm.put("code",CODE);
        return BASE_PATH+"usdtexchange.html";
    }
    /**
     * USDT兑换列表查询
     */
    @Json
    @RequestMapping(KEY_LIST)
    public Object list(){
        Object grid = new Object();
        String parameter = HttpKit.getRequest().getParameter("where");
        if(StrKit.isBlank(parameter)) {
            return grid;
        }
        grid = paginateBySelf(LIST_SOURCE);
        return grid;
    }

    /**
     * 修改界面
     */
    @RequestMapping(KEY_EDIT+"/{id}")
    public String edit(@PathVariable Integer id,ModelMap mm){
        Map exchange = Db.findById("Exchange_review", id);
        mm.put("code",CODE);
        mm.put("usdtexchange",exchange);
        return BASE_PATH+"usdtexchange_edit.html";
    }

    /**
     * 修改
     */
    @Json
    @Transactional
    @RequestMapping(KEY_UPDATE)
    public AjaxResult update(){
        ExchangeReview exchangeReview = mapping(PREFIX, ExchangeReview.class);
        boolean temp = exchangeReviewService.update(exchangeReview);
        if (exchangeReview.getStatus() == 4){
            // 兑换成功发送邮件
            Map emailParam = RechargeExchangeCommon.getEmailConf(4);
            emailParam.put("gold", 0);
            emailParam.put("toUserid", exchangeReview.getUserId());
            SendHttp.sendEmail(emailParam);
        }else if (exchangeReview.getStatus() == 5){
            // 兑换退回
            // 退回总赢
            Map user_win = commonService.getInfoByOne("PlayerSocreInfo.get_by_uid", CMap.init().set("userId",exchangeReview.getUserId()));
            String totalWin =user_win.get("TotalWin").toString();
            long TotalWin = new BigDecimal(totalWin).add(new BigDecimal(exchangeReview.getConsumptionCode())).longValue();
            // 保存到数控
            Db.update("update [QPGameUserDB].[dbo].[PlayerSocreInfo] set TotalWin=#{TotalWin} where Userid=#{UserId}", CMap.init().set("TotalWin",TotalWin));
            // 兑换失败发送邮件领取金币
            Map emailParam = RechargeExchangeCommon.getEmailConf(5);
            emailParam.put("gold", 0);
            emailParam.put("toUserid", exchangeReview.getUserId());
            emailParam.put("content",emailParam.get("content")+"["+exchangeReview.getFeedback()+"]");
            SendHttp.sendEmail(emailParam);
            // 查询用户金币
            Map feeMap = commonService.getInfoByOne("db_Shop_Prop_UserProp.getGold", CMap.init().set("",CMap.init().set("UserId",exchangeReview.getUserId())));
            // 获取到用户的金币
            BigDecimal amount = new BigDecimal(String.valueOf(feeMap.get("Amount")));
            // 变动后的金币
            BigDecimal aft_gold = amount.add(exchangeReview.getAmount());
            // 添加邮件记录
            RechargeExchangeCommon.addGoldChangeRecord(exchangeReview,amount.longValue(),aft_gold.longValue());
        }
        if (temp){
            return success(UPDATE_SUCCESS_MSG);
        }else {
            return fail(UPDATE_SUCCESS_MSG);
        }
    }
}
