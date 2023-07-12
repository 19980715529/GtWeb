package com.smallchill.system.treasure.controller;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.smallchill.core.toolbox.ajax.AjaxResult;
import org.beetl.sql.core.OnConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.kit.CharsetKit;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.kit.URLKit;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/plog")
public class PlatformLogController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/modules/platform/plog/";
	private static String CODE = "plog";
	/*private static String LIST_SOURCE = "platform.new_list";暂无法使用，数据库不对应*/

	@Autowired
	private CommonService commonService;
	@DoControllerLog(name="进入平台明显记录页面")
	@RequestMapping("/")
	//@Permission({ ADMINISTRATOR, ADMIN })
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "platform_detail1.html";
	}
	
	//	@SystemControllerLog(description = "平台明细列表")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Json
	@RequestMapping(KEY_LIST)
	//@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult list() {
		Object gird = new Object();
		String parameter = HttpKit.getRequest().getParameter("where");
		if(StrKit.isBlank(parameter)) {
			return json(gird);
		}
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		// 解析查询条件
		Map paras = JSON.parseObject(parameter, Map.class);
		gird = paginateBySelf("platform.record_list");
		Map map = new HashMap();
		map.put("rows", gird);
		return json(gird);
	}

	/**
	 * 数据汇总 充值总金币、总充值、系统总输赢、总税收、总顺差
	 * @return DetailSummary
	 */
	@Json
	@RequestMapping("/summary")
	public AjaxResult summary(){
		Map executeCall = Db.executeCall(new OnConnection<Map>() {
			@Override
			public Map call(Connection conn) throws SQLException {
				CallableStatement cstmt = conn.prepareCall("{call [QPGameRecordDB].[dbo].[DetailSummary]() }");
				ResultSet rs = null;
				rs = cstmt.executeQuery(); //注意： 所有调用存储过程的sql语句都是使用executeQuery方法执行！！！
				Map ret = new HashMap();
				// RecTotalGold,RecTotalMoney,ExcTotalMoney,(RecTotalMoney-ExcTotalMoney) as RE,RewardCoins,totalTaxScores,RegUserNum,zsy
				while (rs.next()){
					// 总充值金币
					ret.put("RecTotalGold",rs.getLong("RecTotalGold"));
					// 总充值
					ret.put("RecTotalMoney",rs.getLong("RecTotalMoney"));
					// 总兑换
					ret.put("ExcTotalMoney",rs.getLong("ExcTotalMoney"));
					// 充提差
					ret.put("RE",rs.getLong("RE"));
					// 总奖励金币
					ret.put("RewardCoins",rs.getLong("RewardCoins"));
					// 总税收
					ret.put("totalTaxScores",rs.getLong("totalTaxScores"));
					// 总注册
					ret.put("RegUserNum",rs.getLong("RegUserNum"));
					// 总输赢
					ret.put("zsy",rs.getLong("zsy"));
				}
				return ret;
			}
		});
		return json(executeCall);
	}
	@DoControllerLog(name="进入平台汇总明显页面")
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(KEY_PTOTAL_LOG)
	//@Permission({ ADMINISTRATOR, ADMIN })
	public String ptotallog(ModelMap mm) {
		Map executeCall = Db.executeCall(new OnConnection<Map>(){
			@SuppressWarnings("unchecked")
			@Override
			public Map call(Connection conn) throws SQLException {
				CallableStatement cstmt = conn.prepareCall("{call [QPGameUserDB].[dbo].[Bg_SelScoreCountToday]() }");
				ResultSet rs = null;
				//设置输入参数
				//cstmt.setInt(1, Integer.parseInt(PlatformID));
				
				//发送参数
				rs = cstmt.executeQuery(); //注意： 所有调用存储过程的sql语句都是使用executeQuery方法执行！！！
				
				Map ret = new HashMap();
				//遍历结果
				while(rs.next()){
	                // 通过字段检索
					// 平台总金币
	                ret.put("totalscore", rs.getString("score"));

					// 平台总兑换
	                ret.put("totalExchangeMoney", new BigDecimal(rs.getString("totalexchange")).longValue());
					// 兑换总人数
					ret.put("totalExchangeUser", rs.getString("dhcount"));
					// 今日总兑换
					ret.put("todayExchangeMoney", rs.getString("drtotalexc"));
					// 当日兑换人数
					ret.put("todayExchangeUser", rs.getString("drdhcount"));

					// 当日登录
	                ret.put("czUserLogin", rs.getString("logincount"));
					// 平台当日输赢
	                ret.put("xtsy", rs.getString("zsy"));
					// 总登录
	                ret.put("DLoginUserCount", rs.getString("drlogin"));
					// 系统奖励金币
	                ret.put("sendscore", rs.getString("sendscore"));
					// 总注册
	                ret.put("totalreg", rs.getString("reg"));
					// 总充值金额
	                ret.put("totalmoney", new BigDecimal(rs.getString("totalmoney")).longValue());
					// 最高在线
	                ret.put("maxonline", rs.getString("onlist"));
					// 安卓总注册
	                ret.put("andRegister", rs.getString("andreg"));
					// 总充值人数
	                ret.put("totalRechargeUser", rs.getString("czcount"));
					// 当日充值总人数
	                ret.put("todayRechargeUser", rs.getString("drcount"));
					//
	                ret.put("OnlineUserCount", rs.getString("dqzx"));
					// ios总注册
	                ret.put("iosRegister", rs.getString("iosreg"));
					// 当日充值金额
	                ret.put("todaymoney", rs.getString("drmoney"));
					// 当日注册
	                ret.put("todayreg", rs.getString("drreg"));
					// 安卓当日注册
	                ret.put("anddRegiste", rs.getString("anddrreg"));
					// ios当日注册
	                ret.put("iosdRegister", rs.getString("driosreg"));
					// 充值获得金币
	                ret.put("czscore", rs.getString("czscore"));
					// 转盘活动产出金币总量
					ret.put("turntablegold",rs.getString("turntablegold"));
					// 充值返利产出金币总量
					ret.put("czcoderebategold",rs.getString("czcoderebategold"));

					// 白银金币奖励
					ret.put("silverGoldRewards",rs.getString("silverGoldRewards"));
					// 白银金币奖励次数
					ret.put("silvercount",new BigDecimal(rs.getString("silvercount")).longValue());
					// 黄金转盘金币奖励次数
					ret.put("goldenGoldRewards",rs.getString("goldenGoldRewards"));
					// 使用每日签到获得转盘次数使用
					ret.put("goldcount",new BigDecimal(rs.getString("goldcount")).longValue());

					// 砖石
					ret.put("diamondGoldRewards",rs.getString("diamondGoldRewards"));

//					 打码返利获得转盘使用次数
					ret.put("diamondcount",new BigDecimal(rs.getString("diamondcount")).longValue());

					// 在线奖领取励转盘次数,
//					ret.put("onlinerewards",new BigDecimal(rs.getString("onlinerewards")).longValue());
					// 每日签到领取转盘次数,
//					ret.put("dailyattendances",new BigDecimal(rs.getString("dailyattendances")).longValue());
					// 打码返利领取转盘次数
//					ret.put("coderebates",new BigDecimal(rs.getString("coderebates")).longValue());

					// 绑定手机号奖励总金币
					ret.put("bindmobilerewards",rs.getString("bindmobilerewards"));
	            }
				// 其他代码
				return ret;
			}
		});
		mm.put("total", executeCall);
		return BASE_PATH + "platform_total_log.html";
	}

}


