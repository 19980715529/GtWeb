package com.smallchill.db.config.controller;

import com.smallchill.db.config.model.ActiveList;
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
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.game.newmodel.gameuserdb.AaOnlineawardProp;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/onlinegold")
public class OnlineGoldController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/onlinegold/";
	private static String CODE = "onlinegold";
	private static String LIST_SOURCE = "db_onlinegold.new_list";
	private static String PREFIX = "onlinegold";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入在线领金币配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "onlinegold.html";
	}
	
	@Json
	@RequestMapping(KEY_LIST)
	@Permission(ADMINISTRATOR)
	public Object list() {
		Object gird = paginate(LIST_SOURCE);
		return gird;
	}
	
	@RequestMapping(KEY_ADD)
	@Permission(ADMINISTRATOR)
	public String add(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "onlinegold_add.html";
	}
	
	@DoControllerLog(name="新增在线领金币配置")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		AaOnlineawardProp boxItem = mapping(PREFIX, AaOnlineawardProp.class);
		int temp = Db.insert("insert into [QPGameUserDB].[dbo].[AA_OnlineAward_Prop](Time_Id,Prop_Id,Amount) values(#{Time_Id},#{Prop_Id},#{amount})", boxItem);
		if (temp>0) {
			return success(SAVE_SUCCESS_MSG);
		} else {
			return error(SAVE_FAIL_MSG);
		}
	}
	
	@RequestMapping(KEY_EDIT + "/{id}")
	@Permission(ADMINISTRATOR)
	public String edit(@PathVariable Integer id, ModelMap mm) {
		ActiveList onlinegold = Blade.create(ActiveList.class).findFirstBy("id="+id, null);
		mm.put("onlinegold", onlinegold);
		mm.put("code", CODE);
		return BASE_PATH + "onlinegold_edit.html";
	}
	
	@DoControllerLog(name="修改在线领金币配置")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		ActiveList boxItem = mapping(PREFIX, ActiveList.class);
		boolean temp = Blade.create(ActiveList.class).updateBy("Param1="+boxItem.getParam1()+",Param2="+boxItem.getParam2()+
				",Reward="+boxItem.getReward()+",RewardType="+boxItem.getRewardType(), "id="+boxItem.getId(), null);
		if (temp) {
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}
	
	@RequestMapping(KEY_VIEW + "/{id}")
	@Permission(ADMINISTRATOR)
	public String view(@PathVariable Integer id, ModelMap mm) {
		AaOnlineawardProp onlinegold = Blade.create(AaOnlineawardProp.class).findFirstBy("id="+id, null);
		mm.put("onlinegold", onlinegold);
		mm.put("code", CODE);
		return BASE_PATH + "onlinegold_view.html";
	}
	
	@DoControllerLog(name="删除在线领金币配置")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		boolean temp = Blade.create(AaOnlineawardProp.class).deleteByIds(ids) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}