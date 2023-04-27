package com.smallchill.game.statistics.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		mm.put("code", CODE);
		return BASE_PATH + "user_recharge_statistics.html";
	}
	
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
		Object gird = commonService.getInfoList("charts_recharge_statistics.new_list", paras);
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
		List<Map> infoList = commonService.getInfoList("charts_recharge_statistics.new_list", where);
		
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
				if(StrKit.equals(d, JSON.toJSONString(m.get("CollectDate")).replaceAll("\"", ""))) {
					if(m.get("TAmount") != null && StrKit.notBlank(JSON.toJSONString(m.get("TAmount")))) {
						OrderAmount = Double.parseDouble(JSON.toJSONString(m.get("TAmount")));
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
		LOGGER.info(parameter);
		Map paras = JSON.parseObject(parameter, Map.class);
		Object gird = commonService.getInfoList("charts_recharge_statistics.new_ARPU_list", paras);
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
		List<Map> infoList = commonService.getInfoList("charts_recharge_statistics.new_ARPU_list", where);
		
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
				CallableStatement cstmt = conn.prepareCall("{call [QPGameUserDB].[dbo].[Statistics_DayRecharge]( ? ) }");
				ResultSet rs = null;
				//设置输入参数
				cstmt.setInt(1, Integer.parseInt(PlatformID));
				
				//发送参数
				rs = cstmt.executeQuery(); //注意： 所有调用存储过程的sql语句都是使用executeQuery方法执行！！！
				
				Map ret = new HashMap();
				//遍历结果
				while(rs.next()){
	                // 通过字段检索
	                ret.put("TAmount", rs.getLong("TAmount"));
	                ret.put("TUser", rs.getInt("TUser"));
	                ret.put("TCount", rs.getInt("TCount"));
	                ret.put("IOS", rs.getInt("IOS"));
	                ret.put("IOSUser", rs.getInt("IOSUser"));
	                ret.put("IOSCount", rs.getInt("IOSCount"));
	                ret.put("Android", rs.getInt("Android"));
	                ret.put("AndroidUser", rs.getInt("AndroidUser"));
	                ret.put("AndroidCount", rs.getInt("AndroidCount"));
	                ret.put("pcczmoney", rs.getInt("pcczmoney"));
	                ret.put("pcczrc", rs.getInt("pcczrc"));
	                ret.put("pcczcount", rs.getInt("pcczcount"));
	                ret.put("Gm_Money", rs.getLong("Gm_Money"));
	                ret.put("CollectDate", rs.getString("CollectDate"));
	            }
				
				// 其他代码
				return ret;
			}
		});
		
		return json(executeCall);
	}
}
