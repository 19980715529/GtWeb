package com.smallchill.game.statistics.controller;

import java.util.ArrayList;
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
import com.smallchill.core.toolbox.kit.CharsetKit;
import com.smallchill.core.toolbox.kit.DateFormatKit;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.kit.URLKit;
import com.smallchill.game.service.CommonService;

import javax.annotation.Resource;

@Controller
@RequestMapping("/statistics")
public class RetationRateStatisticsController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/statistics/";
	private static String CODE = "statistics";
	private static String LIST_SOURCE = "charts_retation_rate_statistics.new_list";
	
	@Resource
	private CommonService commonService;

	@DoControllerLog(name="进入留存率统计页面")
	@RequestMapping("/retationrate/")
	//@Permission({ ADMINISTRATOR, ADMIN })
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "retation_rate_statistics.html";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Json
	@RequestMapping("/rrslist")
	public Object rrslist() {
		String parameter = getParameter("where");
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		LOGGER.info("留存率统计->获取表格数据查询参数：-------------------");
		LOGGER.info(parameter);
		Map where = JSON.parseObject(parameter, Map.class);
		String startTime = JSON.toJSONString(where.get("startTime")).replaceAll("\"", "");
		String endTime = JSON.toJSONString(where.get("endTime")).replaceAll("\"", "");
		String afterDayDate = startTime;
		List<Map> infoList = commonService.getInfoList("charts_retation_rate_statistics.all_list", where);
		Map temp = null;
		List<Map> gridInfo = new ArrayList<Map>();
		// 时间格式转换工具
		DateFormatKit format = new DateFormatKit();
		while(afterDayDate.compareTo(endTime) <= 0) {
			LOGGER.info("留存率统计->表格数据->afterDayDate：" + afterDayDate);
			String d = format.getDay(format.parseDate(afterDayDate));
			boolean flag = true;
			
			temp= new HashMap();
			temp.put("CollectDate", afterDayDate);
			int RegisterUserCount = 0;
			int RetationUserCount1 = 0;
			int RetationUserCount2 = 0;
			int RetationUserCount3 = 0;
			int RetationUserCount7 = 0;
			int RetationUserCount15 = 0;
			int RetationUserCount30 = 0;
			for (Map m : infoList) {
				if(StrKit.equals(d, JSON.toJSONString(m.get("RegisterDate")).replaceAll("\"", ""))) {
					RegisterUserCount = Integer.parseInt(JSON.toJSONString(m.get("RegisterUserCount")));
					RetationUserCount1 = Integer.parseInt(JSON.toJSONString(m.get("RegisterUserCount1")));
					RetationUserCount2 = Integer.parseInt(JSON.toJSONString(m.get("RegisterUserCount2")));
					RetationUserCount3 = Integer.parseInt(JSON.toJSONString(m.get("RegisterUserCount3")));
					RetationUserCount7 = Integer.parseInt(JSON.toJSONString(m.get("RegisterUserCount7")));
					RetationUserCount15 = Integer.parseInt(JSON.toJSONString(m.get("RegisterUserCount15")));
					RetationUserCount30 = Integer.parseInt(JSON.toJSONString(m.get("RegisterUserCount30")));
					temp.put("RegisterUserCount", (RegisterUserCount));
					temp.put("RetationUserCount1", (RetationUserCount1/RegisterUserCount));
					temp.put("RetationUserCount2", (RetationUserCount2/RegisterUserCount));
					temp.put("RetationUserCount3", (RetationUserCount3/RegisterUserCount));
					temp.put("RetationUserCount7", (RetationUserCount7/RegisterUserCount));
					temp.put("RetationUserCount15", (RetationUserCount15/RegisterUserCount));
					temp.put("RetationUserCount30", (RetationUserCount30/RegisterUserCount));
					flag = false;
					break;
				}
			}
			if(flag) {
				temp.put("RegisterUserCount", 0);
				temp.put("RetationUserCount1", 0);
				temp.put("RetationUserCount2", 0);
				temp.put("RetationUserCount3", 0);
				temp.put("RetationUserCount7", 0);
				temp.put("RetationUserCount15", 0);
				temp.put("RetationUserCount30", 0);
			}
			gridInfo.add(temp);
			afterDayDate = format.getSpecifiedDayAfter(afterDayDate);
		}
		Object gird = JSON.toJSON(gridInfo);
		return gird;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Json
	@RequestMapping("/trrslist")
	public Object trrslist() {
		Object gird = new Object();
		String parameter = HttpKit.getRequest().getParameter("where");
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		LOGGER.info("留存率统计->获取表格数据查询参数：-------------------");
		LOGGER.info(parameter);
		// 解析查询条件
		Map paras = JSON.parseObject(parameter, Map.class);
		gird = commonService.getInfoList(LIST_SOURCE, paras);
		Map map = new HashMap();
		map.put("rows", gird);
		return gird;
	}
	@Json
	@RequestMapping("/trrslist1")
	public Object trrslist1() {
		Object gird = new Object();
		String parameter = HttpKit.getRequest().getParameter("where");
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		LOGGER.info("留存率统计->获取表格数据查询参数：-------------------");
		LOGGER.info(parameter);
		// 解析查询条件
		Map paras = JSON.parseObject(parameter, Map.class);
		gird = commonService.getInfoList("charts_retation_rate_statistics.new_list1", paras);
		Map map = new HashMap();
		map.put("rows", gird);
		return gird;
	}
	@Json
	@RequestMapping("/trrslist2")
	public Object trrslist2() {
		Object gird = new Object();
		String parameter = HttpKit.getRequest().getParameter("where");
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		LOGGER.info("留存率统计->获取表格数据查询参数：-------------------");
		LOGGER.info(parameter);
		// 解析查询条件
		Map paras = JSON.parseObject(parameter, Map.class);
		gird = commonService.getInfoList("charts_retation_rate_statistics.new_list2", paras);
		Map map = new HashMap();
		map.put("rows", gird);
		return gird;
	}
}
