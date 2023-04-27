package com.smallchill.db.config.login.controller;

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
import com.smallchill.game.newmodel.logindb.Clientpos;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/clientpos")
public class ClientposController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/clientpos/";
	private static String CODE = "clientpos";
	private static String LIST_SOURCE = "db_clientpos.list";
	private static String PREFIX = "clientpos";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入包开发游戏配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "clientpos.html";
	}
	
	@Json
	@RequestMapping(KEY_LIST)
	@Permission(ADMINISTRATOR)
	public Object list() {
		Object gird = paginateBySelfByName("logindb",LIST_SOURCE);
		return gird;
	}
	
	@RequestMapping(KEY_ADD)
	@Permission(ADMINISTRATOR)
	public String add(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "clientpos_add.html";
	}
	
	@DoControllerLog(name="新增包开发游戏配置")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		Clientpos boxItem = mapping(PREFIX, Clientpos.class);
		Clientpos clientpos = Blade.create(Clientpos.class).findFirstBy("clientType="+boxItem.getClienttype(), null);
		if(clientpos != null) {
			return error("包ID已存在.");
		}
		boolean temp = Blade.create(Clientpos.class).save(boxItem);
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
		map.put("clientType", id);
		Map clientpos = commonService.getInfoByOneDbName("logindb",LIST_SOURCE, map);
		mm.put("clientpos", clientpos);
		mm.put("code", CODE);
		return BASE_PATH + "clientpos_edit.html";
	}
	
	@DoControllerLog(name="修改包开发游戏配置")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		Clientpos boxItem = mapping(PREFIX, Clientpos.class);
		StringBuilder builder = new StringBuilder();
		builder.append("pos1="+boxItem.getPos1());
		builder.append(",pos2="+boxItem.getPos2());
		builder.append(",pos3="+boxItem.getPos3());
		builder.append(",pos4="+boxItem.getPos4());
		builder.append(",pos5="+boxItem.getPos5());
		builder.append(",pos6="+boxItem.getPos6());
		builder.append(",pos7="+boxItem.getPos7());
		builder.append(",pos8="+boxItem.getPos8());
		builder.append(",pos9="+boxItem.getPos9());
		builder.append(",pos10="+boxItem.getPos10());
		builder.append(",pos11="+boxItem.getPos11());
		builder.append(",pos12="+boxItem.getPos12());
		builder.append(",pos13="+boxItem.getPos13());
		builder.append(",pos14="+boxItem.getPos14());
		builder.append(",pos15="+boxItem.getPos15());
		builder.append(",pos16="+boxItem.getPos16());
		builder.append(",pos17="+boxItem.getPos17());
		builder.append(",pos18="+boxItem.getPos18());
		builder.append(",pos19="+boxItem.getPos19());
		builder.append(",pos20="+boxItem.getPos20());
		builder.append(",pos21="+boxItem.getPos21());
		builder.append(",pos22="+boxItem.getPos22());
		builder.append(",pos23="+boxItem.getPos23());
		builder.append(",pos24="+boxItem.getPos24());
		builder.append(",pos25="+boxItem.getPos25());
		builder.append(",pos26="+boxItem.getPos26());
		builder.append(",pos27="+boxItem.getPos27());
		builder.append(",pos28="+boxItem.getPos28());
		builder.append(",pos29="+boxItem.getPos29());
		builder.append(",pos30="+boxItem.getPos30());
		
		boolean temp = Blade.create(Clientpos.class).updateBy(builder.toString(), "clientType="+boxItem.getClienttype(), null);
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
		map.put("clientType", id);
		Map bloodpondconfig = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("clientpos", bloodpondconfig);
		mm.put("code", CODE);
		return BASE_PATH + "clientpos_view.html";
	}
	
	@DoControllerLog(name="删除包开发游戏配置")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		boolean temp = Blade.create(Clientpos.class).deleteBy("clientType in ("+ids+")", null) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}