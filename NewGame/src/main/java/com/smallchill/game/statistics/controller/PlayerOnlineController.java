package com.smallchill.game.statistics.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.CharsetKit;
import com.smallchill.core.toolbox.kit.CollectionKit;
import com.smallchill.core.toolbox.kit.DateFormatKit;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.kit.URLKit;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/statistics")
public class PlayerOnlineController extends BaseController implements
		ConstShiro {
	private static String BASE_PATH = "/statistics/";
	private static String CODE = "statistics";

	@Autowired
	private CommonService commonService;

	@DoControllerLog(name = "进入用户在线统计页面")
	@RequestMapping("/")
	//@Permission({ ADMINISTRATOR, ADMIN })
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "player_online_statistics.html";
	}
	// 日平均在线
	@SuppressWarnings("rawtypes")
	@Json
	@RequestMapping("/getJson")
	public AjaxResult getBtn() {
		String parameter = getParameter("where");
		if (parameter.contains("%")) {
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		LOGGER.info("用户在线统计->日平均在线->获取图表数据查询参数：-------------------");
		LOGGER.info(parameter);
		Map params = JSON.parseObject(parameter, Map.class);
		List<Map> infoList = commonService.getInfoList("charts_online_statistics.new_day_avg_list", params);
		return json(infoList);
	}
	// 周平均在线
	@SuppressWarnings("rawtypes")
	@Json
	@RequestMapping("/getWeekAvg")
	public AjaxResult getWeekAvg() {
		String parameter = getParameter("where");
		if (parameter.contains("%")) {
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		LOGGER.info("用户在线统计->周平均在线->获取图表数据查询参数：-------------------");
		LOGGER.info(parameter);
		Map params = JSON.parseObject(parameter, Map.class);
		List<Map> infoList = commonService.getInfoList("charts_online_statistics.online_week_list", params);
		return json(infoList);
	}
	// 日最高在线
	@SuppressWarnings("rawtypes")
	@Json
	@RequestMapping("/getOnlineHighAxis")
	public AjaxResult getOnlineHighAxis() {
		String parameter = getParameter("where");
		if (parameter.contains("%")) {
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		LOGGER.info("用户在线统计->日最高在线->获取图表数据查询参数：-------------------");
		LOGGER.info(parameter);
		Map params = JSON.parseObject(parameter, Map.class);
		List<Map> infoList = commonService.getInfoList("charts_online_statistics.new_day_high_list", params);
		return json(infoList);
	}
	
	// 按游戏房间统计
	@SuppressWarnings({"rawtypes", "unchecked" })
	@Json
	@RequestMapping("/poclist")
	public Object poclist() {
		Object gird = new Object();
		String parameter = HttpKit.getRequest().getParameter("where");
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		Map parseObject2 = JSON.parseObject(parameter, Map.class);
		List<Map> infoList = commonService.getInfoList("charts_online_statistics.new_gs_list", parseObject2);
		Collection<Map> sort = null;
		sort = CollectionKit.sort(infoList, new Comparator<Map>() {   
			@Override
			public int compare(Map arg0, Map arg1) {
				int i1 = Integer.parseInt(JSON.toJSONString(arg0.get("OnlineCount")).replaceAll("\"", ""));
				int i2 = Integer.parseInt(JSON.toJSONString(arg1.get("OnlineCount")).replaceAll("\"", ""));
				return (i2-i1);
			}
			});
		gird = sort;
		Map map = new HashMap();
		map.put("rows", gird);
		return map;
	}

	@RequestMapping("/rpocdetail")
	public String rpocdetail(@RequestParam(name="id", required=false) Integer id, ModelMap mm) {
		mm.put("code", CODE);
		mm.put("id", id);
		return BASE_PATH + "player_online_detail.html";
	}
	// 获取游戏对应房间统计信息
	@SuppressWarnings({"rawtypes", "unchecked" })
	@Json
	@RequestMapping("/rpoclist")
	public Object rpoclist(@RequestParam(name="id", required=false) String id) {
		Object gird = new Object();
		Map paras = new HashMap();
		paras.put("KindID", id);
		gird = commonService.getInfoList("charts_online_statistics.new_gsr_list", paras);
		Map map = new HashMap();
		map.put("rows", gird);
		return map;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Json
	@RequestMapping("/getOnlineAxisBak")
	public AjaxResult getOnlineAxisBak() {
		String parameter = getParameter("where");
		if (parameter.contains("%")) {
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		LOGGER.info("用户在线统计->获取图表数据查询参数：-------------------");
		LOGGER.info(parameter);
		Map where = JSON.parseObject(parameter, Map.class);

		String startTime = JSON.toJSONString(where.get("startTime"))
				.replaceAll("\"", "");
		String endTime = JSON.toJSONString(where.get("endTime")).replaceAll(
				"\"", "");

		DateFormatKit format = new DateFormatKit();
		String afterDayDate = startTime;
		// x轴数据
		List<String> times = new ArrayList<String>();
		times.add("0.00");times.add("0.05");times.add("0.10");times.add("0.15");times.add("0.20");times.add("0.25");times.add("0.30");times.add("0.35");times.add("0.40");times.add("0.45");times.add("0.50");times.add("0.55");
		times.add("1.00");times.add("1.05");times.add("1.10");times.add("1.15");times.add("1.20");times.add("1.25");times.add("1.30");times.add("1.35");times.add("1.40");times.add("1.45");times.add("1.50");times.add("1.55");
		times.add("2.00");times.add("2.05");times.add("2.10");times.add("2.15");times.add("2.20");times.add("2.25");times.add("2.30");times.add("2.35");times.add("2.40");times.add("2.45");times.add("2.50");times.add("2.55");
		times.add("3.00");times.add("3.05");times.add("3.10");times.add("3.15");times.add("3.20");times.add("3.25");times.add("3.30");times.add("3.35");times.add("3.40");times.add("3.45");times.add("3.50");times.add("3.55");
		times.add("4.00");times.add("4.05");times.add("4.10");times.add("4.15");times.add("4.20");times.add("4.25");times.add("4.30");times.add("4.35");times.add("4.40");times.add("4.45");times.add("4.50");times.add("4.55");
		times.add("5.00");times.add("5.05");times.add("5.10");times.add("5.15");times.add("5.20");times.add("5.25");times.add("5.30");times.add("5.35");times.add("5.40");times.add("5.45");times.add("5.50");times.add("5.55");
		times.add("6.00");times.add("6.05");times.add("6.10");times.add("6.15");times.add("6.20");times.add("6.25");times.add("6.30");times.add("6.35");times.add("6.40");times.add("6.45");times.add("6.50");times.add("6.55");
		times.add("7.00");times.add("7.05");times.add("7.10");times.add("7.15");times.add("7.20");times.add("7.25");times.add("7.30");times.add("7.35");times.add("7.40");times.add("7.45");times.add("7.50");times.add("7.55");
		times.add("8.00");times.add("8.05");times.add("8.10");times.add("8.15");times.add("8.20");times.add("8.25");times.add("8.30");times.add("8.35");times.add("8.40");times.add("8.45");times.add("8.50");times.add("8.55");
		times.add("9.00");times.add("9.05");times.add("9.10");times.add("9.15");times.add("9.20");times.add("9.25");times.add("9.30");times.add("9.35");times.add("9.40");times.add("9.45");times.add("9.50");times.add("9.55");
		times.add("10.00");times.add("10.05");times.add("10.10");times.add("10.15");times.add("10.20");times.add("10.25");times.add("10.30");times.add("10.35");times.add("10.40");times.add("10.45");times.add("10.50");times.add("10.55");
		times.add("11.00");times.add("11.05");times.add("11.10");times.add("11.15");times.add("11.20");times.add("11.25");times.add("11.30");times.add("11.35");times.add("11.40");times.add("11.45");times.add("11.50");times.add("11.55");
		times.add("12.00");times.add("12.05");times.add("12.10");times.add("12.15");times.add("12.20");times.add("12.25");times.add("12.30");times.add("12.35");times.add("12.40");times.add("12.45");times.add("12.50");times.add("12.55");
		times.add("13.00");times.add("13.05");times.add("13.10");times.add("13.15");times.add("13.20");times.add("13.25");times.add("13.30");times.add("13.35");times.add("13.40");times.add("13.45");times.add("13.50");times.add("13.55");
		times.add("14.00");times.add("14.05");times.add("14.10");times.add("14.15");times.add("14.20");times.add("14.25");times.add("14.30");times.add("14.35");times.add("14.40");times.add("14.45");times.add("14.50");times.add("14.55");
		times.add("15.00");times.add("15.05");times.add("15.10");times.add("15.15");times.add("15.20");times.add("15.25");times.add("15.30");times.add("15.35");times.add("15.40");times.add("15.45");times.add("15.50");times.add("15.55");
		times.add("16.00");times.add("16.05");times.add("16.10");times.add("16.15");times.add("16.20");times.add("16.25");times.add("16.30");times.add("16.35");times.add("16.40");times.add("16.45");times.add("16.50");times.add("16.55");
		times.add("17.00");times.add("17.05");times.add("17.10");times.add("17.15");times.add("17.20");times.add("17.25");times.add("17.30");times.add("17.35");times.add("17.40");times.add("17.45");times.add("17.50");times.add("17.55");
		times.add("18.00");times.add("18.05");times.add("18.10");times.add("18.15");times.add("18.20");times.add("18.25");times.add("18.30");times.add("18.35");times.add("18.40");times.add("18.45");times.add("18.50");times.add("18.55");
		times.add("19.00");times.add("19.05");times.add("19.10");times.add("19.15");times.add("19.20");times.add("19.25");times.add("19.30");times.add("19.35");times.add("19.40");times.add("19.45");times.add("19.50");times.add("19.55");
		times.add("20.00");times.add("20.05");times.add("20.10");times.add("20.15");times.add("20.20");times.add("20.25");times.add("20.30");times.add("20.35");times.add("20.40");times.add("20.45");times.add("20.50");times.add("20.55");
		times.add("21.00");times.add("21.05");times.add("21.10");times.add("21.15");times.add("21.20");times.add("21.25");times.add("21.30");times.add("21.35");times.add("21.40");times.add("21.45");times.add("21.50");times.add("21.55");
		times.add("22.00");times.add("22.05");times.add("22.10");times.add("22.15");times.add("22.20");times.add("22.25");times.add("22.30");times.add("22.35");times.add("22.40");times.add("22.45");times.add("22.50");times.add("22.55");
		times.add("23.00");times.add("23.05");times.add("23.10");times.add("23.15");times.add("23.20");times.add("23.25");times.add("23.30");times.add("23.35");times.add("23.40");times.add("23.45");times.add("23.50");times.add("23.55");

		Map params = new HashMap();
		Map temp = null;
		params.put("startTime", startTime);
		List<Map> infoList = null;
		// 日期集合
		List<Object> dates = new ArrayList<Object>();
		// 数据拼接
		String data = "[";
		int count = 0;
		String[] colors = {"4F81BD","C0504D","9BBB59","8064A2","4BACC6","d6131e","fee188","503e06","1895b1","1ed62c","316f36","9044a9","5c1673","73161b","F79646"};
		String legend = null;
		while (afterDayDate.compareTo(endTime) <= 0) {
			LOGGER.info("注册统计->图表数据->afterDayDate：" + afterDayDate);
			if (StrKit.isBlank(afterDayDate)) {
				continue;
			}
			//legend = "{name: '"+afterDayDate+"',icon: 'roundRect'}";
			if(count < 14) {
				legend = "{z:"+count+",name: '"+afterDayDate+"',icon: 'roundRect',textStyle: {color: '#"+colors[count]+"'}}";
			} else {
				legend = "{z:0,name: '"+afterDayDate+"',icon: 'roundRect',textStyle: {color: '#"+colors[14]+"',itemHeight:8}}";
			}
			dates.add(JSON.parse(legend));
			infoList = commonService.getInfoList("charts_online_statistics.new_list_bak", params);
			data += "{name:'"+afterDayDate+"',type: 'line',data:[";
			Long c = 0L;
			for (String time : times) {
				for (Map list : infoList) {
					String Times = JSON.toJSONString(list.get("Times")).replaceAll("\"", "").replaceAll(":", ".");
					if(StrKit.equals(Times.trim(), time)) {
						c = Long.parseLong(JSON.toJSONString(list.get("OnlineCount")));
						break;
					}
				}
				data += c+",";
			}
			if(count < 14) {
				data += "],smooth: true, itemStyle: {normal:{color: '#"+colors[count]+"'}}},";
			} else {
				data += "],smooth: true, itemStyle: {normal:{color: '#"+colors[14]+"'}}},";
			}
			afterDayDate = format.getSpecifiedDayAfter(afterDayDate);
			params.put("startTime", afterDayDate);
			count++;
		}
		data += "]";
		
		temp = new HashMap();
		temp.put("dates", dates);
		temp.put("times", times);
		temp.put("data", JSON.parseArray(data, Object.class));
		return json(temp);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Json
	@RequestMapping("/getOnlineAxis")
	public AjaxResult getOnlineAxis() {
		String parameter = getParameter("where");
		if (parameter.contains("%")) {
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		LOGGER.info("用户在线统计->获取图表数据查询参数：-------------------");
		LOGGER.info(parameter);
		Map where = JSON.parseObject(parameter, Map.class);

		String startTime = JSON.toJSONString(where.get("startTime"))
				.replaceAll("\"", "");
		String endTime = JSON.toJSONString(where.get("endTime")).replaceAll(
				"\"", "");
		String KindID = JSON.toJSONString(where.get("KindID")).replaceAll(
				"\"", "");

		DateFormatKit format = new DateFormatKit();
		String afterDayDate = startTime;
		// x轴数据
		List<String> times = new ArrayList<String>();
		boolean isMulti = false;
		if(!StrKit.equals(startTime, endTime)){
			times.add("0.00");times.add("0.05");times.add("0.10");times.add("0.15");times.add("0.20");times.add("0.25");times.add("0.30");times.add("0.35");times.add("0.40");times.add("0.45");times.add("0.50");times.add("0.55");
			times.add("1.00");times.add("1.05");times.add("1.10");times.add("1.15");times.add("1.20");times.add("1.25");times.add("1.30");times.add("1.35");times.add("1.40");times.add("1.45");times.add("1.50");times.add("1.55");
			times.add("2.00");times.add("2.05");times.add("2.10");times.add("2.15");times.add("2.20");times.add("2.25");times.add("2.30");times.add("2.35");times.add("2.40");times.add("2.45");times.add("2.50");times.add("2.55");
			times.add("3.00");times.add("3.05");times.add("3.10");times.add("3.15");times.add("3.20");times.add("3.25");times.add("3.30");times.add("3.35");times.add("3.40");times.add("3.45");times.add("3.50");times.add("3.55");
			times.add("4.00");times.add("4.05");times.add("4.10");times.add("4.15");times.add("4.20");times.add("4.25");times.add("4.30");times.add("4.35");times.add("4.40");times.add("4.45");times.add("4.50");times.add("4.55");
			times.add("5.00");times.add("5.05");times.add("5.10");times.add("5.15");times.add("5.20");times.add("5.25");times.add("5.30");times.add("5.35");times.add("5.40");times.add("5.45");times.add("5.50");times.add("5.55");
			times.add("6.00");times.add("6.05");times.add("6.10");times.add("6.15");times.add("6.20");times.add("6.25");times.add("6.30");times.add("6.35");times.add("6.40");times.add("6.45");times.add("6.50");times.add("6.55");
			times.add("7.00");times.add("7.05");times.add("7.10");times.add("7.15");times.add("7.20");times.add("7.25");times.add("7.30");times.add("7.35");times.add("7.40");times.add("7.45");times.add("7.50");times.add("7.55");
			times.add("8.00");times.add("8.05");times.add("8.10");times.add("8.15");times.add("8.20");times.add("8.25");times.add("8.30");times.add("8.35");times.add("8.40");times.add("8.45");times.add("8.50");times.add("8.55");
			times.add("9.00");times.add("9.05");times.add("9.10");times.add("9.15");times.add("9.20");times.add("9.25");times.add("9.30");times.add("9.35");times.add("9.40");times.add("9.45");times.add("9.50");times.add("9.55");
			times.add("10.00");times.add("10.05");times.add("10.10");times.add("10.15");times.add("10.20");times.add("10.25");times.add("10.30");times.add("10.35");times.add("10.40");times.add("10.45");times.add("10.50");times.add("10.55");
			times.add("11.00");times.add("11.05");times.add("11.10");times.add("11.15");times.add("11.20");times.add("11.25");times.add("11.30");times.add("11.35");times.add("11.40");times.add("11.45");times.add("11.50");times.add("11.55");
			times.add("12.00");times.add("12.05");times.add("12.10");times.add("12.15");times.add("12.20");times.add("12.25");times.add("12.30");times.add("12.35");times.add("12.40");times.add("12.45");times.add("12.50");times.add("12.55");
			times.add("13.00");times.add("13.05");times.add("13.10");times.add("13.15");times.add("13.20");times.add("13.25");times.add("13.30");times.add("13.35");times.add("13.40");times.add("13.45");times.add("13.50");times.add("13.55");
			times.add("14.00");times.add("14.05");times.add("14.10");times.add("14.15");times.add("14.20");times.add("14.25");times.add("14.30");times.add("14.35");times.add("14.40");times.add("14.45");times.add("14.50");times.add("14.55");
			times.add("15.00");times.add("15.05");times.add("15.10");times.add("15.15");times.add("15.20");times.add("15.25");times.add("15.30");times.add("15.35");times.add("15.40");times.add("15.45");times.add("15.50");times.add("15.55");
			times.add("16.00");times.add("16.05");times.add("16.10");times.add("16.15");times.add("16.20");times.add("16.25");times.add("16.30");times.add("16.35");times.add("16.40");times.add("16.45");times.add("16.50");times.add("16.55");
			times.add("17.00");times.add("17.05");times.add("17.10");times.add("17.15");times.add("17.20");times.add("17.25");times.add("17.30");times.add("17.35");times.add("17.40");times.add("17.45");times.add("17.50");times.add("17.55");
			times.add("18.00");times.add("18.05");times.add("18.10");times.add("18.15");times.add("18.20");times.add("18.25");times.add("18.30");times.add("18.35");times.add("18.40");times.add("18.45");times.add("18.50");times.add("18.55");
			times.add("19.00");times.add("19.05");times.add("19.10");times.add("19.15");times.add("19.20");times.add("19.25");times.add("19.30");times.add("19.35");times.add("19.40");times.add("19.45");times.add("19.50");times.add("19.55");
			times.add("20.00");times.add("20.05");times.add("20.10");times.add("20.15");times.add("20.20");times.add("20.25");times.add("20.30");times.add("20.35");times.add("20.40");times.add("20.45");times.add("20.50");times.add("20.55");
			times.add("21.00");times.add("21.05");times.add("21.10");times.add("21.15");times.add("21.20");times.add("21.25");times.add("21.30");times.add("21.35");times.add("21.40");times.add("21.45");times.add("21.50");times.add("21.55");
			times.add("22.00");times.add("22.05");times.add("22.10");times.add("22.15");times.add("22.20");times.add("22.25");times.add("22.30");times.add("22.35");times.add("22.40");times.add("22.45");times.add("22.50");times.add("22.55");
			times.add("23.00");times.add("23.05");times.add("23.10");times.add("23.15");times.add("23.20");times.add("23.25");times.add("23.30");times.add("23.35");times.add("23.40");times.add("23.45");times.add("23.50");times.add("23.55");
			isMulti = true;
		}

		Map params = new HashMap();
		Map temp = null;
		params.put("startTime", startTime);
		if(StrKit.notBlank(KindID)) {
			params.put("KindID", KindID);
		}
		List<Map> infoList = null;
		// 日期集合
		List<Object> dates = new ArrayList<Object>();
		// 数据拼接
		String data = "[";
		int count = 0;
		String[] colors = {"4F81BD","C0504D","9BBB59","8064A2","4BACC6","d6131e","fee188","503e06","1895b1","1ed62c","316f36","9044a9","5c1673","73161b","F79646"};
		String legend = null;
		while (afterDayDate.compareTo(endTime) <= 0) {
			LOGGER.info("注册统计->图表数据->afterDayDate：" + afterDayDate);
			if (StrKit.isBlank(afterDayDate)) {
				continue;
			}
			//legend = "{name: '"+afterDayDate+"',icon: 'roundRect'}";
			if(count < 14) {
				legend = "{z:"+count+",name: '"+afterDayDate+"',icon: 'roundRect',textStyle: {color: '#"+colors[count]+"'}}";
			} else {
				legend = "{z:0,name: '"+afterDayDate+"',icon: 'roundRect',textStyle: {color: '#"+colors[14]+"',itemHeight:8}}";
			}
			dates.add(JSON.parse(legend));
			infoList = commonService.getInfoList("charts_online_statistics.new_list_bak", params);
			data += "{name:'"+afterDayDate+"',type: 'line',data:[";
			Long c = 0L;
			if(isMulti) {
				for (String time : times) {
					for (Map list : infoList) {
						String Times = JSON.toJSONString(list.get("Times")).replaceAll("\"", "").replaceAll(":", ".");
						Double d = Double.parseDouble(Times.trim());
						Double td = Double.parseDouble(time.trim());
						Times = d.toString();
						time = td.toString();
						if(StrKit.equals(Times, time)) {
							c = Long.parseLong(JSON.toJSONString(list.get("OnlineCount")));
							break;
						}
					}
					data += c+",";
					c = 0L;
				}
			}else {
				for (Map list : infoList) {
					String CountTime = JSON.toJSONString(list.get("Times")).replaceAll("\"", "").replaceAll(":", ".");
					times.add(CountTime);
					c = Long.parseLong(JSON.toJSONString(list.get("OnlineCount")));
					data += c+",";
				}
			}
			if(count < 14) {
				data += "],smooth: true, itemStyle: {normal:{color: '#"+colors[count]+"'}}},";
			} else {
				data += "],smooth: true, itemStyle: {normal:{color: '#"+colors[14]+"'}}},";
			}
			afterDayDate = format.getSpecifiedDayAfter(afterDayDate);
			params.put("startTime", afterDayDate);
			count++;
		}
		data += "]";
		
		temp = new HashMap();
		temp.put("dates", dates);
		temp.put("times", times);
		temp.put("data", JSON.parseArray(data, Object.class));
		return json(temp);
	}
	
	@SuppressWarnings({ "rawtypes" })
	@Json
	@RequestMapping("/getAllOnlineAxis")
	public AjaxResult getAllOnlineAxis() {
		String parameter = getParameter("where");
		if (parameter.contains("%")) {
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		LOGGER.info("用户在线统计->获取图表数据查询参数：-------------------");
		LOGGER.info(parameter);
		Map where = JSON.parseObject(parameter, Map.class);
		List<Map> infoList = commonService.getInfoList("charts_online_statistics.new_all_list", where);
		return json(infoList);
	}
}
