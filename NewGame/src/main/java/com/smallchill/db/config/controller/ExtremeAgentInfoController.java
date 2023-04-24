package com.smallchill.db.config.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.CharsetKit;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.core.toolbox.kit.URLKit;
import com.smallchill.db.config.model.request.ExtremeAgentInfoRequest;
import com.smallchill.game.model.ExtremeAgentInfo;
import com.smallchill.game.service.CommonService;
import com.smallchill.game.vip.model.Accountsinfo;

@Controller
@RequestMapping("/revenuebill")
public class ExtremeAgentInfoController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/revenuebill/";
	private static String CODE = "revenuebill";
	private static String LIST_SOURCE = "db_revenuebill.list";
	private static String PREFIX = "revenuebill";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入交易税收账单列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "revenuebill.html";
	}
	
	@Json
	@RequestMapping(KEY_LIST)
	@Permission(ADMINISTRATOR)
	public Object list() {
		Object gird = paginateBySelf(LIST_SOURCE);
		return gird;
	}
	
	@DoControllerLog(name="")
	@RequestMapping(KEY_ADD)
	@Permission(ADMINISTRATOR)
	public String add(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "revenuebill_add.html";
	}
	
	@DoControllerLog(name="新增至尊VIP税收汇集账户")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		ExtremeAgentInfoRequest infoRequest = mapping(PREFIX, ExtremeAgentInfoRequest.class);
		ExtremeAgentInfo agentInfo = null;
		String[] split = infoRequest.getBranchuserid().split(",");
		List<ExtremeAgentInfo> list = new ArrayList<ExtremeAgentInfo>();
		for (String string : split) {
			agentInfo = new ExtremeAgentInfo();
			agentInfo.setExtremeuserid(infoRequest.getExtremeuserid());
			agentInfo.setBranchuserid(Integer.parseInt(string));
			
			list.add(agentInfo);
		}
		try {
			Blade.create(ExtremeAgentInfo.class).saveBatch(list);
			return success(SAVE_SUCCESS_MSG);
		}catch(Exception e) {
			return error(SAVE_FAIL_MSG);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(KEY_EDIT + "/{id}")
	@Permission(ADMINISTRATOR)
	public String edit(@PathVariable Integer id, ModelMap mm) {
		Map map = new HashMap();
		map.put("ExtremeUserID", id);
		Map revenuebill = commonService.getInfoByOne(LIST_SOURCE, map);
		// 下属VIP
		Object subVip = commonService.getInfoList("db_revenuebill.sub_list", map);
		mm.put("revenuebill", revenuebill);
		mm.put("subVipStr", JSON.toJSONString(subVip));
		mm.put("subVip", subVip);
		mm.put("code", CODE);
		return BASE_PATH + "revenuebill_edit.html";
	}
	
	@DoControllerLog(name="修改至尊VIP税收汇集账户")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		ExtremeAgentInfoRequest infoRequest = mapping(PREFIX, ExtremeAgentInfoRequest.class);
		ExtremeAgentInfo agentInfo = null;
		String[] split = infoRequest.getBranchuserid().split(",");
		List<ExtremeAgentInfo> list = new ArrayList<ExtremeAgentInfo>();
		for (String string : split) {
			agentInfo = new ExtremeAgentInfo();
			agentInfo.setExtremeuserid(infoRequest.getExtremeuserid());
			agentInfo.setBranchuserid(Integer.parseInt(string));
			
			list.add(agentInfo);
		}
		try {
			Blade.create(ExtremeAgentInfo.class).saveBatch(list);
			return success(UPDATE_SUCCESS_MSG);
		}catch(Exception e) {
			return error(UPDATE_FAIL_MSG);
		}
	}
	
	@RequestMapping(KEY_VIEW + "/{id}")
	@Permission(ADMINISTRATOR)
	public String view(@PathVariable Integer id, ModelMap mm) {
		mm.put("ReturnUserID", id);
		mm.put("code", CODE);
		return BASE_PATH + "revenuebill_view.html";
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Json
	@RequestMapping("/subrecord")
	@Permission({ ADMINISTRATOR, ADMIN })
	public Object subrecord() {
		String parameter = HttpKit.getRequest().getParameter("where");
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		Map paras = JSON.parseObject(parameter, Map.class);
		Object gird = commonService.getInfoList("db_revenuebill.bill_record_list", paras);
		Map map = new HashMap();
		map.put("rows", gird);
		return map;
	}
	
	@DoControllerLog(name="删除至尊VIP税收汇集账户")
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ExtremeUserID = getParameter("ids");
		Map paras = new HashMap();
		paras.put("ExtremeUserID", ExtremeUserID);
		int deleteBy = Blade.create(ExtremeAgentInfo.class).deleteBy(" ExtremeUserID=#{ExtremeUserID}", paras);
		if (deleteBy > 0) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
	
	@DoControllerLog(name="删除至尊VIP税收汇集账户的子账户")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Json
	@RequestMapping("/delSub")
	@Permission(ADMINISTRATOR)
	public AjaxResult delSub() {
		String ExtremeUserID = getParameter("ExtremeUserID");
		String BranchUserID = getParameter("BranchUserID");
		Map paras = new HashMap();
		paras.put("ExtremeUserID", ExtremeUserID);
		paras.put("BranchUserID", BranchUserID);
		int deleteBy = Blade.create(ExtremeAgentInfo.class).deleteBy(" ExtremeUserID=#{ExtremeUserID} and BranchUserID=#{BranchUserID}", paras);
		if (deleteBy > 0) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Json
	@RequestMapping("/getExtremeAgentInfo")
	@Permission(ADMINISTRATOR)
	public AjaxResult getExtremeAgentInfo() {
		List<Map> list = commonService.getInfoList(LIST_SOURCE,null);
		return json(list);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Json
	@RequestMapping("/checkSubVIP")
	@Permission(ADMINISTRATOR)
	public AjaxResult checkSubVIP() {
		String subVip = getParameter("subVip");
		Map paras = new HashMap();
		paras.put("subVip", subVip);
		Map result = new HashMap();
		// 检查该账号是否存在
		Accountsinfo user = Blade.create(Accountsinfo.class).findFirstBy(" where GameID=#{subVip}", paras);
		if(user == null) {// 账号不存在
			result.put("isExist", 0);
			return json(result);
		}
		paras.put("UserID", user.getUserid());
		// 检查该账号是否被设置过为上级账号
		ExtremeAgentInfo infoExtreme = Blade.create(ExtremeAgentInfo.class).findFirstBy(" where ExtremeUserID=#{UserID}", paras);
		if(infoExtreme != null) {
			result.put("isExist", 1);
			return json(result);
		}
		infoExtreme = Blade.create(ExtremeAgentInfo.class).findFirstBy(" where BranchUserID=#{UserID}", paras);
		if(infoExtreme != null) {
			result.put("isExist", 2);
			return json(result);
		}
		result.put("isExist", -1);
		result.put("user", user);
		return json(result);
	}
	// 获取下属VIP列表
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Json
	@RequestMapping("/subvlist")
	@Permission({ ADMINISTRATOR, ADMIN })
	public Object rulist(@RequestParam(name="id", required=false) String id) {
		Map paras = new HashMap();
		paras.put("ExtremeUserID", id);
		Object gird = commonService.getInfoList("db_revenuebill.sub_list", paras);
		Map map = new HashMap();
		map.put("rows", gird);
		return map;
	}
}