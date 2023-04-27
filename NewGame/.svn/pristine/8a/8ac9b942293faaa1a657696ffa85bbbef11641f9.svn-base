package com.smallchill.db.config.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.CollectionKit;
import com.smallchill.core.utils.UserRightUtil;
import com.smallchill.db.config.model.request.VipMemberPropertyRequest;
import com.smallchill.game.model.VipMemberProperty;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/viptype")
public class VipMemberPropertyController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/viptype/";
	private static String CODE = "viptype";
	private static String LIST_SOURCE = "db_viptype.list";
	private static String PREFIX = "viptype";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入VIP用户配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "viptype.html";
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
		return BASE_PATH + "viptype_add.html";
	}
	
	@DoControllerLog(name="新增VIP")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		VipMemberPropertyRequest request = mapping(PREFIX, VipMemberPropertyRequest.class);
		int dwUserRight = 0;
		String[] userright = {"0"};
		if(request.getUserright() != null) {
			userright = request.getUserright().split(",");
		}
		for (String string : userright) {
			dwUserRight += Integer.parseInt(string);
		}
		
		VipMemberProperty property = new VipMemberProperty();
		property.setCollectdate(new Date());
		property.setCollectnote(request.getCollectnote());
		property.setDaygiftid(request.getDaygiftid());
		property.setDaypresent(request.getDaypresent());
		property.setGift(request.getGift());
		property.setInsurerate(request.getInsurerate());
		property.setLittlelama(request.getLittlelama());
		property.setMembername(request.getMembername());
		property.setNotice(request.getNotice());
		property.setOnlinereward(request.getOnlinereward());
		property.setRelieffund(request.getRelieffund());
		property.setScorecount(request.getScorecount());
		property.setShoprate(request.getShoprate());
		property.setSign(request.getSign());
		property.setTaskrate(request.getTaskrate());
		property.setUserright(dwUserRight);
		property.setVisiting(request.getVisiting());
		boolean temp = Blade.create(VipMemberProperty.class).save(property);
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
		map.put("MemberOrder", id);
		Map viptype = commonService.getInfoByOne(LIST_SOURCE, map);
		Integer dwUserRight = Integer.parseInt(JSON.toJSONString(viptype.get("UserRight")));
		mm = getUserRight(mm,dwUserRight);
		mm.put("viptype", viptype);
		mm.put("code", CODE);
		return BASE_PATH + "viptype_edit.html";
	}
	
	@DoControllerLog(name="修改VIP")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		VipMemberPropertyRequest request = mapping(PREFIX, VipMemberPropertyRequest.class);
		int dwUserRight = 0;
		String[] userright = {"0"};
		if(request.getUserright() != null) {
			userright = request.getUserright().split(",");
		}
		for (String string : userright) {
			dwUserRight += Integer.parseInt(string);
		}
		
		VipMemberProperty property = new VipMemberProperty();
		property.setMemberorder(request.getMemberorder());
		property.setCollectnote(request.getCollectnote());
		property.setDaygiftid(request.getDaygiftid());
		property.setDaypresent(request.getDaypresent());
		property.setGift(request.getGift());
		property.setInsurerate(request.getInsurerate());
		property.setLittlelama(request.getLittlelama());
		property.setMembername(request.getMembername());
		property.setNotice(request.getNotice());
		property.setOnlinereward(request.getOnlinereward());
		property.setRelieffund(request.getRelieffund());
		property.setScorecount(request.getScorecount());
		property.setShoprate(request.getShoprate());
		property.setSign(request.getSign());
		property.setTaskrate(request.getTaskrate());
		property.setUserright(dwUserRight);
		property.setVisiting(request.getVisiting());
		boolean temp = Blade.create(VipMemberProperty.class).update(property);
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
		map.put("MemberOrder", id);
		Map bloodpondconfig = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("viptype", bloodpondconfig);
		mm.put("code", CODE);
		return BASE_PATH + "viptype_view.html";
	}
	
	@DoControllerLog(name="删除VIP")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		String[] split = ids.split(",");
		String[] strs ={"1","2","3","4","5","6","7","8","9","10","11"};
		for (String string : split) {
			if(CollectionKit.contains(strs, string)) {
				return error("<span class=\"text-red\">ID为" + string + "的VIP类型为固定设定，不能删除!</span>");
			}
		}
		boolean temp = Blade.create(VipMemberProperty.class).deleteByIds(ids) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/vipRight/{id}")
	@Permission(ADMINISTRATOR)
	public String vipRight(@PathVariable Integer id, ModelMap mm) {
		Map map = new HashMap();
		map.put("MemberOrder", id);
		Map viptype = commonService.getInfoByOne(LIST_SOURCE, map);
		Integer dwUserRight = Integer.parseInt(JSON.toJSONString(viptype.get("UserRight")));
		mm = getUserRight(mm,dwUserRight);
		mm.put("viptype", viptype);
		mm.put("code", CODE);
		return BASE_PATH + "userright.html";
	}
	
	private ModelMap getUserRight(ModelMap mm,Integer dwUserRight){
		if(UserRightUtil.CanPlay(dwUserRight)) {
			mm.put("CanPlay", 1);
		} else {
			mm.put("CanPlay", 0);
		}
		if(UserRightUtil.CanLookon(dwUserRight)) {
			mm.put("CanLookon", 1);
		} else {
			mm.put("CanLookon", 0);
		}
		if(UserRightUtil.CanWisper(dwUserRight)) {
			mm.put("CanWisper", 1);
		} else {
			mm.put("CanWisper", 0);
		}
		if(UserRightUtil.CanRoomChat(dwUserRight)) {
			mm.put("CanRoomChat", 1);
		} else {
			mm.put("CanRoomChat", 0);
		}
		if(UserRightUtil.CanGameChat(dwUserRight)) {
			mm.put("CanGameChat", 1);
		} else {
			mm.put("CanGameChat", 0);
		}
		if(UserRightUtil.CanGameTop(dwUserRight)) {
			mm.put("CanGameTop", 1);
		} else {
			mm.put("CanGameTop", 0);
		}
		if(UserRightUtil.CanGameTrade(dwUserRight)) {
			mm.put("CanGameTrade", 1);
		} else {
			mm.put("CanGameTrade", 0);
		}
		if(UserRightUtil.CanDoubleScore(dwUserRight)) {
			mm.put("CanDoubleScore", 1);
		} else {
			mm.put("CanDoubleScore", 0);
		}
		if(UserRightUtil.CanKillOutUser(dwUserRight)) {
			mm.put("CanKillOutUser", 1);
		} else {
			mm.put("CanKillOutUser", 0);
		}
		if(UserRightUtil.CanEnterVipRoom(dwUserRight)) {
			mm.put("CanEnterVipRoom", 1);
		} else {
			mm.put("CanEnterVipRoom", 0);
		}
		if(UserRightUtil.IsGameMatchUser(dwUserRight)) {
			mm.put("IsGameMatchUser", 1);
		} else {
			mm.put("IsGameMatchUser", 0);
		}
		if(UserRightUtil.CanTrumpet(dwUserRight)) {
			mm.put("CanTrumpet", 1);
		} else {
			mm.put("CanTrumpet", 0);
		}
		if(UserRightUtil.CanSendGift(dwUserRight)) {
			mm.put("CanSendGift", 1);
		} else {
			mm.put("CanSendGift", 0);
		}
		if(UserRightUtil.CanOnlineNotice(dwUserRight)) {
			mm.put("CanOnlineNotice", 1);
		} else {
			mm.put("CanOnlineNotice", 0);
		}
		return mm;
	}
	
	@SuppressWarnings("rawtypes")
	@Json
	@RequestMapping("/getVipMemberProperty")
	@Permission(ADMINISTRATOR)
	public AjaxResult getVipMemberProperty() {
		List<Map> list = commonService.getInfoList(LIST_SOURCE,null);
		return json(list);
	}
}