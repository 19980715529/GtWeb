package com.smallchill.game.vip.controller;

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
import com.smallchill.core.aop.SystemControllerLog;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.kit.CharsetKit;
import com.smallchill.core.toolbox.kit.DateTimeKit;
import com.smallchill.core.toolbox.kit.URLKit;
import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.game.newmodel.Accountsinfo;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/vip")
public class VIPController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/vip/";
	private static String CODE = "vip";
	private static String LIST_SOURCE = "vip.new_list";

	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入至尊VIP列表页面")
	@RequestMapping("/")
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "vip.html";
	}
	
	@DoControllerLog(name="进入金币赠送接收列表页面")
	@RequestMapping("/gift/")
	public String gift(ModelMap mm) {
		mm.put("code", CODE);
		/*mm.put("permitday", ConstConfig.PERMIT_DAY);
		Map map = new HashMap();
		DateFormatKit from = new DateFormatKit();
		map.put("StartTime", from.getDay());
		map.put("EndTime",from.getDay());
		Map total = commonService.getInfoByOne("vip.all_score", map);
		mm.put("totalscore",total);*/
		return BASE_PATH + "vip_gift_log.html";
	}
	
	//	@SystemControllerLog(description = "至尊VIP列表")
	@Json
	@RequestMapping(KEY_LIST)
	public Object list() {
		Object gird = new Object();
		gird = paginateBySelf(LIST_SOURCE);
		return gird;
	}

	//	@SystemControllerLog(description = "至尊VIP赠送礼物列表")
	@SuppressWarnings("rawtypes")
	@Json
	@RequestMapping("/giftlist")
	public Object giftlist() {
		String parameter = getParameter("where");
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		Map where = JSON.parseObject(parameter, Map.class);
		String startTime = JSON.toJSONString(where.get("StartTime")).replaceAll("\"", "");
		Object gird = null;
		if(DateTimeKit.today().equals(startTime)){
			gird = paginateBySelf("vip.new_vip_giftDay");
		}else {
			gird = paginateBySelf("vip.new_vip_gift");
		}
		return gird;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Json
	@RequestMapping("/allscore")
	public AjaxResult allscore(){
		String parameter = getParameter("where");
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		Map paras = JSON.parseObject(parameter, Map.class);
		Map total = commonService.getInfoByOne("vip.all_score", paras);
		Map totalUser = commonService.getInfoByOne("vip.all_score_user", paras);
		total.put("TradeUserNum", totalUser.get("TradeUserNum"));
		return json(total);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Json
	@RequestMapping("/getTotal")
	public AjaxResult getTotal() {
		String parameter = getParameter("where");
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		Map map = JSON.parseObject(parameter, Map.class);
		Map winAdnLose = commonService.getInfoByOne("vip.total_list", map);
		return json(winAdnLose);
	}
	
	@SystemControllerLog(description = "删除至尊VIP用户")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult remove() {
		String ids = getParameter("ids");
		CMap updateMap = CMap.init().set("ids", Convert.toIntArray(ids));
		boolean temp = Blade.create(Accountsinfo.class).updateBy("Businessman=0", "UserID in (#{join(ids)})", updateMap);
		if (temp) {
			CacheKit.removeAll(SYS_CACHE);
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}