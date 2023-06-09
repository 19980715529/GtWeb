package com.smallchill.game.statistics.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.CharsetKit;
import com.smallchill.core.toolbox.kit.CollectionKit;
import com.smallchill.core.toolbox.kit.DateFormatKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.kit.URLKit;
import com.smallchill.core.utils.WeekUtils;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/statistics")
public class UserActivityStatisticsController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/statistics/";
	private static String CODE = "statistics";
	
	@Autowired
	private CommonService commonService;

	@DoControllerLog(name="进入用户活跃统计页面")
	@RequestMapping("/useractivity/")
	//@Permission({ ADMINISTRATOR, ADMIN })
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "user_activity_statistics.html";
	}
	
	//	@SystemControllerLog(description = "用户活跃统计")
	@SuppressWarnings({ "rawtypes" })
	@Json
	@RequestMapping("/ualist")
	public Object ualist() {
		Object gird = null;
		String parameter = getParameter("where");
		String type = getParameter("type");
		Map<String,String> params = parseParameter(parameter,type);
		
		String startTime = params.get("startTime").replaceAll("\"", "");
		String endTime = params.get("endTime").replaceAll("\"", "");
		List<Map> gridInfo = null;
		params.put("orderBy", "desc");
		switch(params.get("type")) {
		case "home":
		case "day":
			gird = commonService.getInfoList("charts_activity_statistics." + params.get("type") + "_list", params);
			break;
		case "week":
		case "month":
			gridInfo = genTableDataByOther(params,endTime,startTime);
			gird = JSON.toJSON(gridInfo);
			break;
		default:
			gird = commonService.getInfoList("charts_activity_statistics." + params.get("type") + "_list", params);
			break;
		}
		return gird;
	}
	
	// 获取活跃统计图表信息
	@SuppressWarnings({ "rawtypes" })
	@Json
	@RequestMapping("/getActivityAxis")
	public AjaxResult getActivityAxis() {
		String parameter = getParameter("where");
		String type = getParameter("type");
		Map<String,String> params = parseParameter(parameter,type);
		
		String startTime = params.get("startTime").replaceAll("\"", "");
		String endTime = params.get("endTime").replaceAll("\"", "");
		Map result = null;
		switch(params.get("type")) {
			case "home":
			case "day":
				result = genChartsDataByNormal(params,startTime,endTime);
				break;
			case "week":
			case "month":
				result = genChartsDataByOther(params,startTime,endTime);
				break;
			default:
				result = genChartsDataByNormal(params,startTime,endTime);
				break;
		}
		return json(result);
	}
	
	@SuppressWarnings("unchecked")
	private Map<String,String> parseParameter(String parameter,String type) {
		Map<String,String> params = new HashMap<String,String>();
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		LOGGER.info("用户活跃统计->查询参数：-------------------");
		LOGGER.info(parameter);
		params = JSON.parseObject(parameter, Map.class);
		if(StrKit.notBlank(type)) {
			params.put("type", type);
		} else {
			params.put("type", "home");
		}
		return params;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Map> genTableDataByOther(Map<String,String> params, String afterDayDate, String startTime) {
		LOGGER.info("用户活跃统计->周/月统计->afterDayDate:" + afterDayDate);
		LOGGER.info("用户活跃统计->周/月统计->startTime:" + startTime);
		DateFormatKit format = new DateFormatKit();
		List<Map> infoList = commonService.getInfoList("charts_activity_statistics." + params.get("type") + "_list", params);
		Map temp = null;
		String type = params.get("type");
		List<Map> gridInfo = new ArrayList<Map>();
		Map<String, Integer> nextYearWeek = null;
		Collection<Entry<String, Integer>> sort = null;
		if(StrKit.equals(type, "week")) {
			nextYearWeek = WeekUtils.getWeekByCanlender(format.parseDate(startTime), format.parseDate(afterDayDate));
			sort = CollectionKit.sort(nextYearWeek.entrySet(), new Comparator<Map.Entry<String, Integer>>() {   
		    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {      
		        return (o2.getValue() - o1.getValue()); 
		        //return (o1.getKey()).toString().compareTo(o2.getKey());
		    }
			});
		} else {
			nextYearWeek = WeekUtils.getMonthByCanlender(format.parseDate(startTime), format.parseDate(afterDayDate));
			sort = nextYearWeek.entrySet();
		}
		for (Entry<String, Integer> map : sort) {
			String key = map.getKey(); // 年份-周
			temp= new HashMap();
			temp.put("CollectDate", key+((type.equals("week")?"周":"月")));
			
			String tempYW = ""; // 年份-周
			int IOS = 0;
			int Android = 0; // 统计用户数
			int PC = 0; // 统计用户数
			for (Map m : infoList) {
				String week = JSON.toJSONString(m.get(StrKit.upperFirst(type))).replaceAll("\"", "");
				String year = JSON.toJSONString(m.get("Year")).replaceAll("\"", "");
				tempYW = year + "-" + week;
				if(StrKit.equals(key, tempYW)) {
					if(StrKit.notBlank(JSON.toJSONString(m.get("IOS")))) {
						IOS = Integer.parseInt(JSON.toJSONString(m.get("IOS")).replaceAll("\"", ""));
					}
					if(StrKit.notBlank(JSON.toJSONString(m.get("Android")))) {
						Android = Integer.parseInt(JSON.toJSONString(m.get("Android")).replaceAll("\"", ""));
					}
					if(StrKit.notBlank(JSON.toJSONString(m.get("PC")))) {
						PC = Integer.parseInt(JSON.toJSONString(m.get("PC")).replaceAll("\"", ""));
					}
					break;
				}
			}
			temp.put("IOS", IOS);
			temp.put("Android", Android);
			temp.put("PC", PC);
			temp.put("Total", (IOS+Android+PC));
			gridInfo.add(temp);
		}
		return gridInfo;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map genChartsDataByNormal(Map params, String afterDayDate, String endTime) {
		String data = "[";
		String data1 = "[";
		String data2 = "[";
		String data3 = "[";
		List<Map> infoList = commonService.getInfoList("charts_activity_statistics." + params.get("type") + "_list", params);

		DateFormatKit format = new DateFormatKit();
		while(afterDayDate.compareTo(endTime) <= 0) {
			LOGGER.info("用户活跃统计->afterDayDate:" + afterDayDate);
			if(StrKit.isBlank(afterDayDate)) {
				continue;
			}
			String d = format.getDay(format.parseDate(afterDayDate));
			int IOS = 0;
			int Android = 0;
			int PC = 0;
			int Total = 0;
			for (Map m : infoList) {
				if(StrKit.equals(d, JSON.toJSONString(m.get("CollectDate")).replaceAll("\"", ""))) {
					if(StrKit.notBlank(JSON.toJSONString(m.get("IOS")))) {
						IOS = Integer.parseInt(JSON.toJSONString(m.get("IOS")));
					}
					if(StrKit.notBlank(JSON.toJSONString(m.get("Android")))) {
						Android = Integer.parseInt(JSON.toJSONString(m.get("Android")));
					}
					if(StrKit.notBlank(JSON.toJSONString(m.get("PC")))) {
						PC = Integer.parseInt(JSON.toJSONString(m.get("PC")));
					}
					if(StrKit.notBlank(JSON.toJSONString(m.get("Total")))) {
						Total = Integer.parseInt(JSON.toJSONString(m.get("Total")));
					}
					break;
				}
			}
			data += "[\"" + d + "\"," + (Total) + "],";
			data1 += "[\"" + d + "\"," + IOS + "],";
			data2 += "[\"" + d + "\","+Android+"],";
			data3 += "[\"" + d + "\","+PC+"],";
			afterDayDate = format.getSpecifiedDayAfter(afterDayDate);
		}
		data += "]";
		data1 += "]";
		data2 += "]";
		data3 += "]";
		List<Object> parseArray = JSON.parseArray(data,Object.class);
		List<Object> parseArray1 = JSON.parseArray(data1,Object.class);
		List<Object> parseArray2 = JSON.parseArray(data2,Object.class);
		List<Object> parseArray3 = JSON.parseArray(data3,Object.class);
		Map map = new HashMap();
		map.put("data", parseArray);
		map.put("data1", parseArray1);
		map.put("data2", parseArray2);
		map.put("data3", parseArray3);
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map genChartsDataByOther(Map<String,String> params, String afterDayDate, String endTime) {
		LOGGER.info("用户活跃统计->周/月统计->afterDayDate:" + afterDayDate);
		LOGGER.info("用户活跃统计->周/月统计->endTime:" + endTime);
		List<Map> infoList = commonService.getInfoList("charts_activity_statistics." + params.get("type") + "_list", params);
		String type = params.get("type");
		String data = "[";
		String data1 = "[";
		String data2 = "[";
		String data3 = "[";

		DateFormatKit format = new DateFormatKit();
		Map<String, Integer> nextYearWeek = null;
		Collection<Entry<String, Integer>> sort = null;
		if(StrKit.equals(type, "week")) {
			nextYearWeek = WeekUtils.getWeekByCanlender(format.parseDate(afterDayDate), format.parseDate(endTime));
			sort = nextYearWeek.entrySet();
		} else {
			nextYearWeek = WeekUtils.getMonthByCanlender(format.parseDate(afterDayDate), format.parseDate(endTime));
			sort = CollectionKit.sort(nextYearWeek.entrySet(), new Comparator<Map.Entry<String, Integer>>() {   
		    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {      
		        return (o1.getValue() - o2.getValue()); 
		        //return (o1.getKey()).toString().compareTo(o2.getKey());
		    }
			});
		}
		for (Entry<String, Integer> map : sort) {
			String key = map.getKey(); // 年份-周
			Integer value = map.getValue(); // 周
			String tempYW = ""; // 年份-周
			int IOS = 0;
			int Android = 0; // 统计用户数
			int PC = 0; // 统计用户数
			for (Map m : infoList) {
				String week = JSON.toJSONString(m.get(StrKit.upperFirst(type))).replaceAll("\"", "");
				String year = JSON.toJSONString(m.get("Year")).replaceAll("\"", "");
				tempYW = year + "-" + week;
				if(StrKit.equals(key, tempYW)) {
					if(StrKit.notBlank(JSON.toJSONString(m.get("IOS")))) {
						IOS = Integer.parseInt(JSON.toJSONString(m.get("IOS")).replaceAll("\"", ""));
					}
					if(StrKit.notBlank(JSON.toJSONString(m.get("Android")))) {
						Android = Integer.parseInt(JSON.toJSONString(m.get("Android")).replaceAll("\"", ""));
					}
					if(StrKit.notBlank(JSON.toJSONString(m.get("PC")))) {
						PC = Integer.parseInt(JSON.toJSONString(m.get("PC")).replaceAll("\"", ""));
					}
					break;
				}
			}
			data += "[\"" + value + "\"," + (IOS+Android+PC) + "],";
			data1 += "[\"" + value + "\"," + IOS + "],";
			data2 += "[\"" + value + "\"," + Android + "],";
			data3 += "[\"" + value + "\"," + PC + "],";
			afterDayDate = format.getSpecifiedDayAfter(afterDayDate);
		}
		data += "]";
		data1 += "]";
		data2 += "]";
		data3 += "]";
		List<Object> parseArray = JSON.parseArray(data,Object.class);
		List<Object> parseArray1 = JSON.parseArray(data1,Object.class);
		List<Object> parseArray2 = JSON.parseArray(data2,Object.class);
		List<Object> parseArray3 = JSON.parseArray(data3,Object.class);
		Map map = new HashMap();
		map.put("data", parseArray);
		map.put("data1", parseArray1);
		map.put("data2", parseArray2);
		map.put("data3", parseArray3);
		return map;
	}
}
