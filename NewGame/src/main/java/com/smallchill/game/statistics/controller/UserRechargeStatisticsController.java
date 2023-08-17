package com.smallchill.game.statistics.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smallchill.common.vo.ShiroUser;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.system.model.UserPack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.CharsetKit;
import com.smallchill.core.toolbox.kit.DateFormatKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.kit.URLKit;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/statistics")
public class UserRechargeStatisticsController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/statistics/";
	private static String CODE = "statistics";
	protected Logger LOGGER = LogManager.getLogger(this.getClass());
	
	@Autowired
	private CommonService commonService;

	@DoControllerLog(name="进入用户充值统计页面")
	@RequestMapping("/userrecharge/")
	//@Permission({ ADMINISTRATOR, ADMIN })
	public String index(ModelMap mm) {
		ShiroUser user = ShiroKit.getUser();
		Integer id =(Integer) user.getId();
		// 查询包id
		Blade blade = Blade.create(UserPack.class);
		UserPack pack = blade.findFirstBy("uid=#{uid}", CMap.init().set("uid", id));
		if (pack!=null){
			String clientType = pack.getClientType();
			Integer[] ids = Convert.toIntArray(clientType);
			mm.put("clientType", ids[0]);
		}else {
			mm.put("clientType", -9);
		}
		mm.put("code", CODE);
		return BASE_PATH + "user_recharge_statistics.html";
	}

	/**
	 * 用户充值统计
	 * @return
	 */
	//	@SystemControllerLog(description = "用户充值统计")
	@SuppressWarnings({ "rawtypes" })
	@Json
	@RequestMapping("/urlist")
	public Object urlist() {
		String parameter = getParameter("where");
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		LOGGER.info("充值统计->获取表格数据查询参数：-------------------");
		LOGGER.info(parameter);
		Map paras = JSON.parseObject(parameter, Map.class);
		Object gird = commonService.getInfoList("charts_recharge_statistics.new_list1", paras);
		return gird;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Json
	@RequestMapping("/getRechargeAxis")
	public AjaxResult getRechargeAxis() {
		String parameter = getParameter("where");
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		LOGGER.info("充值统计->获取图表数据查询参数：-------------------");
		LOGGER.info(parameter);
		Map where = JSON.parseObject(parameter, Map.class);
		
		String startTime = JSON.toJSONString(where.get("startTime")).replaceAll("\"", "");
		String endTime = JSON.toJSONString(where.get("endTime")).replaceAll("\"", "");
		String afterDayDate = startTime;
		List<Map> infoList = commonService.getInfoList("charts_recharge_statistics.new_list1", where);
		
		String data = "[";
		DateFormatKit format = new DateFormatKit();
		while(afterDayDate.compareTo(endTime) <= 0) {
			LOGGER.info("充值统计->图表数据->afterDayDate：" + afterDayDate);
			if(StrKit.isBlank(afterDayDate)) {
				continue;
			}
			String d = format.getDay(format.parseDate(afterDayDate));
			Double OrderAmount = 0D;
			for (Map m : infoList) {
				if(StrKit.equals(d, JSON.toJSONString(m.get("writedate")).replaceAll("\"", ""))) {
					if(m.get("recAmount") != null && StrKit.notBlank(JSON.toJSONString(m.get("recAmount")))) {
						OrderAmount = Double.parseDouble(JSON.toJSONString(m.get("recAmount")));
					}
					break;
				}
			}
			data += "[\"" + d + "\"," + OrderAmount + "],";
			afterDayDate = format.getSpecifiedDayAfter(afterDayDate);
		}
		data += "]";
		List<Object> parseArray = JSON.parseArray(data,Object.class);
		Map map = new HashMap();
		map.put("data", parseArray);
		return json(map);
	}
	// ARPU统计
	@SuppressWarnings({ "rawtypes" })
	@Json
	@RequestMapping("/arpulist")
	public Object arpulist() {
		String parameter = getParameter("where");
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		LOGGER.info("ARPU->获取表格数据查询参数：-------------------");
		Map paras = JSON.parseObject(parameter, Map.class);
		Object gird = commonService.getInfoList("charts_recharge_statistics.new_ARPU_list1", paras);
		return gird;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Json
	@RequestMapping("/getRechargeARPUAxis")
	public AjaxResult getRechargeARPUAxis() {
		String parameter = getParameter("where");
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		LOGGER.info("ARPU->获取图表数据查询参数：-------------------");
		LOGGER.info(parameter);
		Map where = JSON.parseObject(parameter, Map.class);
		
		String startTime = JSON.toJSONString(where.get("startTime")).replaceAll("\"", "");
		String endTime = JSON.toJSONString(where.get("endTime")).replaceAll("\"", "");
		String afterDayDate = startTime;
		List<Map> infoList = commonService.getInfoList("charts_recharge_statistics.new_ARPU_list1", where);
		
		String data = "[";
		double mOrderAmount = 0D;
		DateFormatKit format = new DateFormatKit();
		while(afterDayDate.compareTo(endTime) <= 0) {
			LOGGER.info("ARPU统计->图表数据->afterDayDate：" + afterDayDate);
			String d = format.getDay(format.parseDate(afterDayDate));
			for (Map m : infoList) {
				if(StrKit.equals(d, JSON.toJSONString(m.get("writedate")).replaceAll("\"", ""))) {
					if(StrKit.notBlank(JSON.toJSONString(m.get("ARPU")))) {
						mOrderAmount = Double.parseDouble(JSON.toJSONString(m.get("ARPU")));
					}
					break;
				}
			}
			data += "[\"" + d + "\"," + mOrderAmount + "],";
			afterDayDate = format.getSpecifiedDayAfter(afterDayDate);
		}
		data += "]";
		List<Object> parseArray = JSON.parseArray(data,Object.class);
		Map map = new HashMap();
		map.put("data", parseArray);
		return json(map);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Json
	@RequestMapping("/getDayRecharge")
	public AjaxResult getDayRecharge() {
		final String PlatformID = getParameter("PlatformID");
		/*
		Map m = new HashMap();
		if(parameter != null && StrKit.equals(parameter, "-1")) {
			
		}else if(parameter != null){
			m.put("PlatformID", parameter);
		}
		Map list = commonService.getInfoByOne("charts_recharge_statistics.day_recharge", m);
		return json(list);
		*/
		Map executeCall = Db.executeCall(new OnConnection<Map>(){
			@Override
			public Map call(Connection conn) throws SQLException {
				CallableStatement cstmt = conn.prepareCall("{call [QPGameRecordDB].[dbo].[RealTransactionStatistics]( ? ) }");
				ResultSet rs = null;
				//设置输入参数
				cstmt.setInt(1, Integer.parseInt(PlatformID));
				
				//发送参数
				rs = cstmt.executeQuery(); //注意： 所有调用存储过程的sql语句都是使用executeQuery方法执行！！！
				
				Map ret = new HashMap();
				//遍历结果
				while(rs.next()){
	                // 总充值
	                ret.put("TotalRec", rs.getLong("TotalRec"));
					// 充值人数
	                ret.put("TotalRecUserNum", rs.getInt("TotalRecUserNum"));
					// 充值次数
	                ret.put("TotalRecNum", rs.getInt("TotalRecNum"));
					// 总兑换
	                ret.put("TotalExc", rs.getInt("TotalExc"));
					// 总兑换人数
	                ret.put("TotalExcUserNum", rs.getInt("TotalExcUserNum"));
					// 兑换次数
	                ret.put("TotalExcNum", rs.getInt("TotalExcNum"));
					// 新增充值
	                ret.put("newTotalRec", rs.getInt("newTotalRec"));
					// 新增充值人数
	                ret.put("newTotalRecUserNum", rs.getInt("newTotalRecUserNum"));
					// 今日兑换
	                ret.put("jrTotalExc", rs.getInt("jrTotalExc"));
					// 今日兑换人数
	                ret.put("jrTotalExcUserNum", rs.getInt("jrTotalExcUserNum"));
					// 今日兑换次数
	                ret.put("jrTotalExcNum", rs.getInt("jrTotalExcNum"));
					// 总充提差
	                ret.put("RE", rs.getInt("RE"));
					// 今日充提差
	                ret.put("jrRe", rs.getLong("jrRe"));
	            }
				
				// 其他代码
				return ret;
			}
		});
		
		return json(executeCall);
	}
}
