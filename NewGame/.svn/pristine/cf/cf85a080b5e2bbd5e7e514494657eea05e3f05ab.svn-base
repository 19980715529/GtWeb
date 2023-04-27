package com.smallchill.db.config.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.DateKit;
import com.smallchill.db.config.meta.intercept.RechargeActivityValidator;
import com.smallchill.game.newmodel.gameuserdb.AaRecharge;
import com.smallchill.game.newmodel.gameuserdb.AaRechargeActivity;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/rechargeactivity")
public class RechargeActivityController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/rechargeactivity/";
	private static String CODE = "rechargeactivity";
	private static String LIST_SOURCE = "db_rechargeactivity.list";
	private static String PREFIX = "rechargeactivity";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入充值双倍活动配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "rechargeactivity.html";
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
		return BASE_PATH + "rechargeactivity_add.html";
	}
	
	@SuppressWarnings("rawtypes")
	@Before(RechargeActivityValidator.class)
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		Map rechargeactivity = commonService.getInfoByOne("db_rechargeactivity.max_id", null);
		Integer maxId = Integer.parseInt(JSON.toJSONString(rechargeactivity.get("max_id")));
		maxId++;
		AaRechargeActivity boxItem = mapping(PREFIX, AaRechargeActivity.class);
		AaRecharge recharge = Blade.create(AaRecharge.class).findById(boxItem.getProductid());
		boxItem.setIndex(maxId);
		boxItem.setPid(recharge.getIOS_Product_Id());
		boolean temp = Blade.create(AaRechargeActivity.class).save(boxItem);
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
		map.put("id", id);
		Map rechargeactivity = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("rechargeactivity", rechargeactivity);
		mm.put("code", CODE);
		return BASE_PATH + "rechargeactivity_edit.html";
	}
	
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		AaRechargeActivity boxItem = mapping(PREFIX, AaRechargeActivity.class);
		AaRecharge recharge = Blade.create(AaRecharge.class).findById(boxItem.getProductid());
		StringBuilder builder = new StringBuilder();
		builder.append("beginTime='"+DateKit.getTime(boxItem.getBegintime())+"'");
		builder.append(",endTime='"+DateKit.getTime(boxItem.getEndtime())+"'");
		builder.append(",extraGold="+boxItem.getExtragold());
		builder.append(",extraTicket="+boxItem.getExtraticket());
		builder.append(",buyTimes="+boxItem.getBuytimes());
		builder.append(",pid='"+recharge.getIOS_Product_Id()+"'");
		builder.append(",productID="+boxItem.getProductid());
		builder.append(",clientType="+boxItem.getClienttype());
		String set = builder.toString();
		String where = "[index]="+boxItem.getIndex();
		boolean temp = Blade.create(AaRechargeActivity.class).updateBy(set, where, null);
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
		map.put("id", id);
		Map bloodpondconfig = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("rechargeactivity", bloodpondconfig);
		mm.put("code", CODE);
		return BASE_PATH + "rechargeactivity_view.html";
	}
	
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		boolean temp = Blade.create(AaRechargeActivity.class).deleteBy("[index] in ("+ids+")", null) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}