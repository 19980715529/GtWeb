package com.smallchill.game.player.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smallchill.core.toolbox.CMap;
import com.smallchill.system.treasure.utils.RechargeExchangeCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.game.newmodel.Accountsinfo;
import com.smallchill.game.newmodel.serverinfodb.Changeviplevel;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/player")
public class PlayerInfoDetailController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/gameplayer/";
	private static String CODE = "player";
	
	@Autowired
	private CommonService commonService;

	@DoControllerLog(name="进入玩家详细信息页面")
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(KEY_PLAYER_DETAIL_INFO + "/{id}")
	public String detailinfo(@PathVariable Integer id, ModelMap mm) {
		Map paras = new HashMap();
		paras.put("UserID", id);
		Map infoByOne = commonService.getInfoByOne("player_search.new_detail", paras);
		if(infoByOne==null){
			return "/error/10000.html";
		}
		String accounts = "";
		if(infoByOne.get("Accounts") != null) {
			accounts = JSON.toJSONString(infoByOne.get("Accounts")).replaceAll("\"", "").replaceAll("@", "\\\\@");
		}
		boolean mobile = false;
		if(infoByOne.get("bindPhone")!=null){
			//mobile = Func.isNum(accounts.replaceAll("\"", ""));
			mobile = true;
		}
		boolean isAccounts = false;
		if(infoByOne.get("Accounts")!=null){
			isAccounts = Func.isNum(accounts.replaceAll("\"", ""));
		}
		
		// 获取ip所属地
		String ip = String.valueOf(infoByOne.get("LastLogonIP"));
		/*
		 * if(infoByOne.get("LastLogonIP") != null) { File f =
		 * FileKit.file("qqwry.Dat"); IPSeeker ipSeeker=new
		 * IPSeeker(f.getName(),f.getParent()); ip =
		 * JSON.toJSONString(infoByOne.get("LastLogonIP")).replaceAll("\"", ""); ip =
		 * ipSeeker.getIPLocation(ip).getCountry(); }
		 */
		// 获取总赢配置
		List<Map> pay_channelPool = Db.selectList("select * from Pay_ChannelPool where type=1");
		Map channel = pay_channelPool.get(0);
		BigDecimal winConfig = new BigDecimal(channel.get("winConf").toString());
		// 用户的总赢
		BigDecimal totalWin = RechargeExchangeCommon.getUserWin(id);
		BigDecimal fee = totalWin.divide(new BigDecimal(channel.get("exchangeRatio").toString()),RoundingMode.DOWN).divide(winConfig, RoundingMode.DOWN);
		if (fee.doubleValue()<=0.1){
			fee = new BigDecimal("0");
		}
		infoByOne.put("money",fee);
		mm.put("LastLoginIp", ip);
		mm.put("user", infoByOne);
		mm.put("mobile", mobile);
		mm.put("isAccounts", isAccounts);
		
		boolean reMobile = false; // 手机号关联设置
		/*if(mobile) {
			List<Accountsinfo> list = Blade.create(Accountsinfo.class).findBy("Accounts='"+accounts+"'", null);
			Integer queryInt = list.size();
			//Integer queryInt = Db.queryInt("SELECT COUNT(1) FROM [QPGameUserDB].[dbo].[AccountsInfo] where Accounts='"+accounts+"'", null);
			if(queryInt!=null && queryInt>1) {
				reMobile = true;
			}
		}*/
		boolean LastLogonMachine = false; // 机器码关联设置
		Integer MacqueryInt = null;
		if(infoByOne.get("LastLoginMachine")!=null) {
			MacqueryInt = Db.queryInt("SELECT COUNT(1) FROM [QPGameUserDB].[dbo].[AccountsInfo] where LOWER(LastLoginMachine) like '%'+LOWER('"+JSON.toJSONString(infoByOne.get("LastLoginMachine")).replaceAll("\"", "")+"')+'%'", null);
			if(MacqueryInt!=null && MacqueryInt>1) {
				LastLogonMachine = true;
			}
		}
		boolean reLastLogonIP = false; // 最后登录IP关联设置
		Integer IPqueryInt = null;
		if(infoByOne.get("LastLogonIP")!=null) {
			IPqueryInt = Db.queryInt("SELECT COUNT(1) FROM [QPGameUserDB].[dbo].[AccountsInfo] where LastLogonIP='"+JSON.toJSONString(infoByOne.get("LastLogonIP")).replaceAll("\"", "")+"'", null);
			if(IPqueryInt!=null && IPqueryInt>1) {
				reLastLogonIP = true;
			}
		}
		// 获取用户充值返利奖励金币
//		Integer rebateGold = Db.queryInt("select isnull(sum(Data),0) from [QPGameRecordDB].[dbo].[AA_ZZ_Log_CodeRebateHistory] where UserId=#{UserID} and DataType=1", paras);
//		mm.put("rebateGold",rebateGold);
		// 白银转盘金币领取
		Map onlineData = Db.selectOne("select isnull(sum(Award),0) gold,count(1) num from " +
				"[QPGameRecordDB].[dbo].[Turntable_History] where UserID=#{UserID} and Type=1 and Fake=0", paras);
		mm.put("onlineData",onlineData);
		// 黄金转盘金币领取
		Map dailyData = Db.selectOne("select isnull(sum(Award),0) gold,count(1) num from " +
				"[QPGameRecordDB].[dbo].[Turntable_History] where UserID=#{UserID} and Type=0 and Fake=0", paras);
		mm.put("dailyData",dailyData);
		// 钻石
		Map codeData = Db.selectOne("select isnull(sum(Award),0) gold,count(1) num from " +
				"[QPGameRecordDB].[dbo].[Turntable_History] where UserID=#{UserID} and Type=3 and Fake=1", paras);
		mm.put("codeData",codeData);
		// 分析代理金币
		Map shareMap = Db.selectOne("select isnull(sum(Amount),0) gold from [QPGameUserDB].[dbo].[AA_ZZ_Log_PropChange] where User_Id=#{UserID} and ChangeType_Id in (212,213)", paras);
		// 邮件数据
		Map mail = commonService.getInfoByOne("player_search.mail_info", paras);
		mm.put("share",Long.parseLong(shareMap.get("gold").toString()));
		mm.put("reMobile", reMobile);
		mm.put("LastLogonMachine", LastLogonMachine);
		mm.put("sumMachine",MacqueryInt);
		mm.put("reLastLogonIP", reLastLogonIP);
		mm.put("sumLastLogonIP",IPqueryInt);
		mm.put("mail",mail);

		Changeviplevel changeviplevel = Blade.create(Changeviplevel.class).findFirstBy("account='"+accounts+"'", null);
		mm.put("code", CODE);
		if(changeviplevel != null) {
			mm.put("isActive", changeviplevel.getIsaction());
			mm.put("cvlid", changeviplevel.getId());
		} else {
			mm.put("isActive", -1);
		}
		return BASE_PATH + "player_detail_info.html";
	}

	@Json
	@RequestMapping("/getUserByJson")
	public AjaxResult getUserByJson() {
		final String id = getParameter("id");
		Accountsinfo user = Blade.create(Accountsinfo.class).findById(id);
		return json(user);
	}

}