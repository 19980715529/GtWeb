package com.smallchill.db.config.controller;

import java.util.Date;
import java.util.HashMap;
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
import com.smallchill.core.utils.CouponNeedInfoUtil;
import com.smallchill.game.model.AwardInfo;
import com.smallchill.game.model.request.AwardInfoRequest;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/awardinfo")
public class AwardInfoController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/awardinfo/";
	private static String CODE = "awardinfo";
	private static String LIST_SOURCE = "db_awardinfo.list";
	private static String PREFIX = "awardinfo";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入奖券商品配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "awardinfo.html";
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
		return BASE_PATH + "awardinfo_add.html";
	}
	
	@DoControllerLog(name="新增奖券商品")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		AwardInfoRequest request = mapping(PREFIX, AwardInfoRequest.class);
		String[] needinfo = request.getNeedinfo().split(",");
		int initInfo = 0;
		if(CollectionKit.contains(needinfo, "1")) {
			initInfo = CouponNeedInfoUtil.needRealName(initInfo);
		}
		if(CollectionKit.contains(needinfo, "2")) {
			initInfo = CouponNeedInfoUtil.needMobile(initInfo);
		}
		if(CollectionKit.contains(needinfo, "4")) {
			initInfo = CouponNeedInfoUtil.needQQ(initInfo);
		}
		if(CollectionKit.contains(needinfo, "8")) {
			initInfo = CouponNeedInfoUtil.needAddr(initInfo);
		}
		AwardInfo awardInfo = new AwardInfo();
		awardInfo.setAwardname(request.getAwardname());
		awardInfo.setBigimage(request.getBigimage());
		awardInfo.setBuycount(request.getBuycount());
		awardInfo.setDescription(request.getDescription());
		awardInfo.setInventory(request.getInventory());
		awardInfo.setIsreturn(request.getIsreturn());
		awardInfo.setNullity(request.getNullity());
		awardInfo.setPrice(request.getPrice());
		awardInfo.setSmallimage(request.getSmallimage());
		awardInfo.setSortid(request.getSortid());
		awardInfo.setTypeid(request.getTypeid());
		awardInfo.setIsmember(0);
		awardInfo.setNeedinfo(initInfo);
		awardInfo.setCollectdate(new Date());
		boolean temp = Blade.create(AwardInfo.class).save(awardInfo);
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
		map.put("AwardID", id);
		Map awardinfo = commonService.getInfoByOne(LIST_SOURCE, map);
		int initInfo = Integer.parseInt(JSON.toJSONString(awardinfo.get("NeedInfo")));
		if(!CouponNeedInfoUtil.isNeedRealName(initInfo)) {
			awardinfo.put("isNeedRealName", 1);
		} else {
			awardinfo.put("isNeedRealName", 0);
		}
		if(!CouponNeedInfoUtil.isNeedMobile(initInfo)) {
			awardinfo.put("isNeedMobile", 1);
		} else {
			awardinfo.put("isNeedMobile", 0);
		}
		if(!CouponNeedInfoUtil.isNeedQQ(initInfo)) {
			awardinfo.put("isNeedQQ", 1);
		} else {
			awardinfo.put("isNeedQQ", 0);
		}
		if(!CouponNeedInfoUtil.isNeedAddr(initInfo)) {
			awardinfo.put("isNeedAddr", 1);
		} else {
			awardinfo.put("isNeedAddr", 0);
		}
		mm.put("awardinfo", awardinfo);
		mm.put("code", CODE);
		return BASE_PATH + "awardinfo_edit.html";
	}
	
	@DoControllerLog(name="修改奖券商品")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		AwardInfoRequest request = mapping(PREFIX, AwardInfoRequest.class);
		String[] needinfo = request.getNeedinfo().split(",");
		int initInfo = 0;
		if(CollectionKit.contains(needinfo, "1")) {
			initInfo = CouponNeedInfoUtil.needRealName(initInfo);
		}
		if(CollectionKit.contains(needinfo, "2")) {
			initInfo = CouponNeedInfoUtil.needMobile(initInfo);
		}
		if(CollectionKit.contains(needinfo, "4")) {
			initInfo = CouponNeedInfoUtil.needQQ(initInfo);
		}
		if(CollectionKit.contains(needinfo, "8")) {
			initInfo = CouponNeedInfoUtil.needAddr(initInfo);
		}
		AwardInfo awardInfo = new AwardInfo();
		awardInfo.setAwardid(request.getAwardid());
		awardInfo.setAwardname(request.getAwardname());
		awardInfo.setBigimage(request.getBigimage());
		awardInfo.setBuycount(request.getBuycount());
		awardInfo.setDescription(request.getDescription());
		awardInfo.setInventory(request.getInventory());
		awardInfo.setIsreturn(request.getIsreturn());
		awardInfo.setNullity(request.getNullity());
		awardInfo.setPrice(request.getPrice());
		awardInfo.setSmallimage(request.getSmallimage());
		awardInfo.setSortid(request.getSortid());
		awardInfo.setTypeid(request.getTypeid());
		awardInfo.setNeedinfo(initInfo);
		
		boolean temp = Blade.create(AwardInfo.class).update(awardInfo);
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
		map.put("AwardID", id);
		Map awardinfo = commonService.getInfoByOne(LIST_SOURCE, map);
		int initInfo = Integer.parseInt(JSON.toJSONString(awardinfo.get("NeedInfo")));
		if(!CouponNeedInfoUtil.isNeedRealName(initInfo)) {
			awardinfo.put("isNeedRealName", 1);
		} else {
			awardinfo.put("isNeedRealName", 0);
		}
		if(!CouponNeedInfoUtil.isNeedMobile(initInfo)) {
			awardinfo.put("isNeedMobile", 1);
		} else {
			awardinfo.put("isNeedMobile", 0);
		}
		if(!CouponNeedInfoUtil.isNeedQQ(initInfo)) {
			awardinfo.put("isNeedQQ", 1);
		} else {
			awardinfo.put("isNeedQQ", 0);
		}
		if(!CouponNeedInfoUtil.isNeedAddr(initInfo)) {
			awardinfo.put("isNeedAddr", 1);
		} else {
			awardinfo.put("isNeedAddr", 0);
		}
		mm.put("awardinfo", awardinfo);
		mm.put("code", CODE);
		return BASE_PATH + "awardinfo_view.html";
	}
	
	@DoControllerLog(name="删除奖券商品")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		boolean temp = Blade.create(AwardInfo.class).deleteByIds(ids) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}