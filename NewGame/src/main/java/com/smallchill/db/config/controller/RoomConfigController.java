package com.smallchill.db.config.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.db.config.model.Gameroominfo;
import com.smallchill.game.newmodel.Gameroomitem;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/roomconfig")
public class RoomConfigController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/room/";
	private static String CODE = "roomconfig";
	private static String LIST_SOURCE = "db_room.new_list";
	private static String PREFIX = "room";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入数据库配置-房间配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "room.html";
	}
	
	@Json
	@RequestMapping(KEY_LIST)
	@Permission(ADMINISTRATOR)
	public Object list() {
		Object gird = paginateBySelf(LIST_SOURCE);
		return gird;
	}
	
	@RequestMapping(KEY_ADD)
	@Permission(ADMINISTRATOR)
	public String add(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "room_add.html";
	}
	
	@DoControllerLog(name="新增房间")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		Gameroomitem gameroomitem = mapping(PREFIX, Gameroomitem.class);
		gameroomitem.setRealscore(0L);gameroomitem.setRevenue(0);
		gameroomitem.setBloodscore(0L);gameroomitem.setLimitscore(0L);
		gameroomitem.setOutscore(0L);gameroomitem.setMaxeatscore(0L);
		gameroomitem.setCheatrate(0);gameroomitem.setOutcheatrate(0);
		gameroomitem.setEatcheatrate(0);gameroomitem.setLtotalscore(0L);
		gameroomitem.setRoomtype(0);gameroomitem.setPresent_20(0);
		gameroomitem.setPresent_40(0);gameroomitem.setPresent_80(0);
		gameroomitem.setBused(0);gameroomitem.setIsused(1);
		gameroomitem.setTodayscore(0L);gameroomitem.setLastupdatetime(new Date());
		gameroomitem.setLessbloodvalue(0);gameroomitem.setMaxbloodvalue(0);
		gameroomitem.setAutoaddblooddailylast(0);gameroomitem.setAutoaddblooddailymax(0);
		gameroomitem.setAutoaddblooddailytimecdinsec(0);gameroomitem.setAutoaddblooddailytimesvalue(0);
		gameroomitem.setAutoaddblooddailylasttime(new Date());;gameroomitem.setCurrencygold(0);
		gameroomitem.setTaxrate(0);gameroomitem.setTaxlimitvalue(0);
		gameroomitem.setNottaxscore(0);gameroomitem.setTotaltaxscore(0L);
		gameroomitem.setTodaytaxscore(0L);gameroomitem.setTotalcheatblood(0L);
		gameroomitem.setTodaycheatblood(0L);gameroomitem.setCheatlowline(0);
		gameroomitem.setBloodscorerate(0);
		
		boolean temp = Blade.create(Gameroomitem.class).save(gameroomitem);
		if (temp) {
			return success(SAVE_SUCCESS_MSG);
		} else {
			return error(SAVE_FAIL_MSG);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(KEY_EDIT + "/{id}")
	@Permission(ADMINISTRATOR)
	public String edit(@PathVariable Integer id, ModelMap mm) {
		Map map = new HashMap();
		map.put("ServerID", id);
		Map room = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("room", room);
		mm.put("code", CODE);
		return BASE_PATH + "room_edit.html";
	}
	
	@DoControllerLog(name="修改房间")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		Gameroomitem bloodpondconfig = mapping(PREFIX, Gameroomitem.class);
		StringBuilder builder = new StringBuilder();
		builder.append("taxRate="+bloodpondconfig.getTaxrate());
		builder.append(",taxLimitValue="+bloodpondconfig.getTaxlimitvalue());
		builder.append(",cheatLowLine="+bloodpondconfig.getCheatlowline());
		builder.append(",lessBloodValue="+bloodpondconfig.getLessbloodvalue());
		builder.append(",autoAddBloodDailyMax="+bloodpondconfig.getAutoaddblooddailymax());
		builder.append(",autoAddBloodDailyLast="+bloodpondconfig.getAutoaddblooddailylast());
		builder.append(",autoAddBloodDailyTimesValue="+bloodpondconfig.getAutoaddblooddailytimesvalue());
		builder.append(",autoAddBloodDailyTimeCDInSec="+bloodpondconfig.getAutoaddblooddailytimecdinsec());
		builder.append(",RoomName='"+bloodpondconfig.getRoomname()+"'");
		boolean temp = Blade.create(Gameroomitem.class).updateBy(builder.toString(), "ServerID="+bloodpondconfig.getServerid(), null);
		if (temp) {
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(KEY_VIEW + "/{id}")
	@Permission(ADMINISTRATOR)
	public String view(@PathVariable Integer id, ModelMap mm) {
		Map map = new HashMap();
		map.put("ServerID", id);
		Map bloodpondconfig = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("room", bloodpondconfig);
		mm.put("code", CODE);
		return BASE_PATH + "room_view.html";
	}
	
	@DoControllerLog(name="删除房间")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		boolean temp = Blade.create(Gameroomitem.class).deleteByIds(ids) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}