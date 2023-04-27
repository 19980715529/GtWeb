package com.smallchill.game.statistics.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.CharsetKit;
import com.smallchill.core.toolbox.kit.DateFormatKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.kit.URLKit;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/statistics")
public class GoldCouponStatisticsController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/statistics/";
	private static String CODE = "statistics";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入金币奖券统计页面")
	@RequestMapping("/goldcoupon/")
	//@Permission({ ADMINISTRATOR, ADMIN })
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "gold_coupon_statistics.html";
	}
	
	//	@SystemControllerLog(description = "金币统计")
	@SuppressWarnings({ "rawtypes" })
	@Json
	@RequestMapping("/glist")
	public Object glist() {
		String parameter = getParameter("where");
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		LOGGER.info("金币统计->获取表格数据查询参数：-------------------");
		LOGGER.info(parameter);
		Map paras = JSON.parseObject(parameter, Map.class);
		Object gird = commonService.getInfoList("charts_gold_statistics.new_list", paras);
		return gird;
	}

	//	@SystemControllerLog(description = "奖券统计")
	@SuppressWarnings({ "rawtypes" })
	@Json
	@RequestMapping("/clist")
	public Object clist() {
		String parameter = getParameter("where");
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		LOGGER.info("奖券统计->获取表格数据查询参数：-------------------");
		LOGGER.info(parameter);
		Map paras = JSON.parseObject(parameter, Map.class);
		Object gird = commonService.getInfoList("charts_gold_statistics.new_coupon_list", paras);
		return gird;
	}
	// 获取金币图表统计图表信息
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Json
	@RequestMapping("/getGoldAxis")
	public AjaxResult getGoldAxis() {
		String parameter = getParameter("where");
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		LOGGER.info("金币统计->获取图表数据查询参数：-------------------");
		LOGGER.info(parameter);
		Map where = JSON.parseObject(parameter, Map.class);

		DateFormatKit format = new DateFormatKit();
		String startTime = JSON.toJSONString(where.get("startTime")).replaceAll("\"", "");
		String endTime = JSON.toJSONString(where.get("endTime")).replaceAll("\"", "");
		String afterDayDate = startTime;
		String data = "[";
		String data1 = "[";
		String data2 = "[";
		Map params = new HashMap();
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		List<Map> infoList = commonService.getInfoList("charts_gold_statistics.new_list", params);
		while(afterDayDate.compareTo(endTime) <= 0) {
			LOGGER.info("金币统计->图表数据->afterDayDate：" + afterDayDate);
			if(StrKit.isBlank(afterDayDate)) {
				continue;
			}
			String d = format.getDay(format.parseDate(afterDayDate));
			double TCurrency = 0;
			double TPresentScore = 0;
			double TGold = 0;
			for (Map m : infoList) {
				if(StrKit.equals(d, JSON.toJSONString(m.get("ApplyDate")).replaceAll("\"", ""))) {
					if(m.get("TGold") != null && StrKit.notBlank(JSON.toJSONString(m.get("TGold")))) {
						TGold = Double.parseDouble(JSON.toJSONString(m.get("TGold")));
					}
					if(m.get("TPresentScore") != null && StrKit.notBlank(JSON.toJSONString(m.get("TPresentScore")))) {
						TPresentScore = Double.parseDouble(JSON.toJSONString(m.get("TPresentScore")));
					}
					if(m.get("TCurrency") != null && StrKit.notBlank(JSON.toJSONString(m.get("TCurrency")))) {
						TPresentScore = Double.parseDouble(JSON.toJSONString(m.get("TCurrency")));
					}
					break;
				}
			}
			data += "[\"" + d + "\"," + TGold + "],";
			data1 += "[\"" + d + "\"," + TPresentScore + "],";
			data2 += "[\"" + d + "\"," + TCurrency + "],";
			afterDayDate = format.getSpecifiedDayAfter(afterDayDate);
		}
		data += "]";
		data1 += "]";
		data2 += "]";
		List<Object> parseArray = JSON.parseArray(data,Object.class);
		List<Object> parseArray1 = JSON.parseArray(data1,Object.class);
		List<Object> parseArray2 = JSON.parseArray(data2,Object.class);
		Map map = new HashMap();
		map.put("data", parseArray);
		map.put("data1", parseArray1);
		map.put("data2", parseArray2);
		return json(map);
	}
	//获取奖券统计图表信息
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Json
	@RequestMapping("/getCouponAxis")
	public AjaxResult getCouponAxis() {
		String parameter = getParameter("where");
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		LOGGER.info("奖券统计->获取图表数据查询参数：-------------------");
		LOGGER.info(parameter);
		Map where = JSON.parseObject(parameter, Map.class);
		
		String startTime = JSON.toJSONString(where.get("startTime")).replaceAll("\"", "");
		String endTime = JSON.toJSONString(where.get("endTime")).replaceAll("\"", "");

		DateFormatKit format = new DateFormatKit();
		String afterDayDate = startTime;
		String data = "[";
		String data1 = "[";
		String data2 = "[";
		Map params = new HashMap();
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		List<Map> infoList = commonService.getInfoList("charts_gold_statistics.new_coupon_list", params);
		while(afterDayDate.compareTo(endTime) <= 0) {
			LOGGER.info("奖券统计->图表数据->afterDayDate：" + afterDayDate);
			if(StrKit.isBlank(afterDayDate)) {
				continue;
			}
			String d = format.getDay(format.parseDate(afterDayDate));
			double TTreasureScore = 0;
			double TPresentScore = 0;
			double TCoupon = 0;
			for (Map m : infoList) {
				if(StrKit.equals(d, JSON.toJSONString(m.get("ApplyDate")).replaceAll("\"", ""))) {
					if(StrKit.notBlank(JSON.toJSONString(m.get("TCoupon")))) {
						TCoupon = Double.parseDouble(JSON.toJSONString(m.get("TCoupon")));
					}
					if(StrKit.notBlank(JSON.toJSONString(m.get("TPresentScore")))) {
						TPresentScore = Double.parseDouble(JSON.toJSONString(m.get("TPresentScore")));
					}
					if(StrKit.notBlank(JSON.toJSONString(m.get("TTreasureScore")))) {
						TTreasureScore = Double.parseDouble(JSON.toJSONString(m.get("TTreasureScore")));
					}
					break;
				}
			}
			data += "[\"" + d + "\"," + TCoupon + "],";
			data1 += "[\"" + d + "\"," + TPresentScore + "],";
			data2 += "[\"" + d + "\"," + TTreasureScore + "],";
			afterDayDate = format.getSpecifiedDayAfter(afterDayDate);
		}
		data += "]";
		data1 += "]";
		data2 += "]";
		List<Object> parseArray = JSON.parseArray(data,Object.class);
		List<Object> parseArray1 = JSON.parseArray(data1,Object.class);
		List<Object> parseArray2 = JSON.parseArray(data2,Object.class);
		Map map = new HashMap();
		map.put("data", parseArray);
		map.put("data1", parseArray1);
		map.put("data2", parseArray2);
		return json(map);
	}
}
