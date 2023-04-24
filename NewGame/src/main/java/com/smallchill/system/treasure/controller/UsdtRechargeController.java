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
import com.smallchill.system.service.RechargeRecordsService;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.RechargeExchangeCommon;
import com.smallchill.system.treasure.utils.SendHttp;
import com.smallchill.system.treasure.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/usdtrecharge")
public class UsdtRechargeController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/system/usdtrecharge/";
    private static String CODE = "usdtrecharge";
    private static String LIST_SOURCE = "usdtrecharge.all_list";
    private static String PREFIX = "usdtrecharge";
    @Autowired
    private RechargeRecordsService rechargeRecordsService;
    @Autowired
    private CommonService commonService;
    /**
     * 接入Usdt管理界面
     */
    @DoControllerLog(name = "进入usdt充值管理界面")
    @RequestMapping("/")
    public String index(ModelMap mm){
        mm.put("code",CODE);
        return BASE_PATH+"usdtrecharge.html";
    }
    /**
     * USDT充值列表查询
     */
    @Json
    @RequestMapping(KEY_LIST)
    public Object list(){
        Object grid = new Object();
        String parameter = HttpKit.getRequest().getParameter("where");
        if(StrKit.isBlank(parameter)) {
            return grid;
        }
        if(parameter.contains("%")){
            parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
        }
        grid = paginateBySelf(LIST_SOURCE);
        return grid;
    }

    /**
     * 进入USDT添加界面
     */
    @RequestMapping(KEY_ADD)
    public String add(ModelMap mm){
        mm.put("code",CODE);
        return BASE_PATH+"usdtrecharge_add.html";
    }
    /**
     * 修改界面
     */
    @RequestMapping(KEY_EDIT+"/{id}")
    public String edit(@PathVariable Integer id,ModelMap mm){
        Map recharge_records = Db.findById("Recharge_records", id);
        mm.put("code",CODE);
        mm.put("usdtrecharge",recharge_records);
        return BASE_PATH+"usdtrecharge_edit.html";
    }
    /**
     * 生成USDT充值订单
     */
    @Json
    @RequestMapping(KEY_SAVE)
    public AjaxResult save(){
        RechargeRecords records = mapping(PREFIX, RechargeRecords.class);
        BigDecimal amount = new BigDecimal(HttpKit.getRequest().getParameter("usdtrecharge.topUpAmount"));
        Map user = commonService.getInfoByOne("player_operate.new_info", CMap.init().set("UserID",records.getUserId()));
        if (user==null){
            return fail("用户id不存在");
        }
        // 判断是否是注册当天充值
        Integer userId = Db.queryInt("select UserID from [QPGameUserDB].[dbo].[AccountsInfo] as a where a.UserID=#{userId} and " +
                "CONVERT(varchar(10),a.RegisterDate,120)=CONVERT(varchar(10),GETDATE(),120)", CMap.init().set("userId", records.getUserId()));
        records.setIsThatTay(0);
        if (userId!=null){
            // 注册当天充值
            records.setIsThatTay(1);
        }
        // 获取用户绑定的电话
        String phone = user.get("bindPhone").toString();
        if (phone!=null){
            records.setPhone(phone);
        }
        // 获取到充值的金钱
        Map channel = Db.selectOne("select * from Channel where id=#{id}", CMap.init().set("id",18));
        records.setNickname(user.get("NickName").toString());
        // 订单设置为待支付
        records.setOrderStatus(1);
        String orderNum = Utils.getOrderNum(records.getUserId());
        // 订单
        records.setOrderNumber(orderNum);
        // 商户id
        records.setChannelPid(17);
        // 大渠道名称
        records.setChannel(channel.get("channelName").toString());
        // 小渠道名称
        records.setChannel_type(channel.get("name").toString());
        // 包渠道
        records.setPackageName(Integer.valueOf(user.get("ClientType").toString()));
        // 设置不是首充
        records.setIsFirstCharge(0);
        //
        records.setUrlPay("SDJFLSDJFLDSSDAS");
        // 根据充值的钱计算获得金币  充值的钱*金币倍率
        BigDecimal gold = amount.multiply(new BigDecimal(channel.get("goldProportion").toString()));
        BigDecimal give = gold.multiply(new BigDecimal(channel.get("give").toString()));
        // 获取当前操作人员
        ShiroUser admin = ShiroKit.getUser();
        String username = Func.toStr(admin.getName());
        records.setOperator(username);
        records.setGold(gold.add(give).longValue());
        boolean save = rechargeRecordsService.save(records);
        if (save){
            return success(SAVE_SUCCESS_MSG);
        }else {
            return fail(SAVE_FAIL_MSG);
        }
    }
    /**
     * 修改
     */
    @Json
    @RequestMapping(KEY_UPDATE)
    public AjaxResult update(){
        RechargeRecords rechargeRecords = mapping(PREFIX, RechargeRecords.class);
        if (rechargeRecords.getOrderStatus() == 2){
            // 添加金币
            RechargeExchangeCommon.AddGoldChangeRecords(rechargeRecords.getUserId(),214,rechargeRecords.getGold());
            // 兑换完成通知服务器
            HashMap<String, Object> gameMap = new HashMap<>();
            gameMap.put("gold",0);
            gameMap.put("Type",1);
            gameMap.put("Userid",rechargeRecords.getUserId());
            gameMap.put("gameCoin",0);
            SendHttp.sendGame1002(gameMap);
            // 发送邮件
            Map emailParam = RechargeExchangeCommon.getEmailConf(2);
            emailParam.put("gold", 0);
            emailParam.put("toUserid", rechargeRecords.getUserId());
            // 需要添加邮件类型
            emailParam.put("goldType", 5);
            SendHttp.sendEmail(emailParam);
        }
        rechargeRecords.setEndTime(new Date());
        // 获取当前操作人员
        ShiroUser admin = ShiroKit.getUser();
        String username = Func.toStr(admin.getName());
        rechargeRecords.setOperator(username);
        boolean temp = rechargeRecordsService.update(rechargeRecords);
        if (temp){
            return success(UPDATE_SUCCESS_MSG);
        }else {
            return fail(UPDATE_FAIL_MSG);
        }
    }
}
