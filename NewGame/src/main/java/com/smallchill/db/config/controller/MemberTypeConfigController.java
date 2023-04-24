package com.smallchill.db.config.controller;

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
import com.smallchill.core.toolbox.kit.CollectionKit;
import com.smallchill.game.model.MemberType;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/membertype")
public class MemberTypeConfigController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/membertype/";
	private static String CODE = "membertype";
	private static String LIST_SOURCE = "db_membertype.list";
	private static String PREFIX = "membertype";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入用户类型配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "membertype.html";
	}
	
	@Json
	@RequestMapping(KEY_LIST)
	@Permission(ADMINISTRATOR)
	public Object list() {
		Object gird = paginateBySelf(LIST_SOURCE);
		return gird;
	}
	
	@RequestMapping(KEY_ADD)
	@Permission({ ADMINISTRATOR, ADMIN })
	public String add(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "membertype_add.html";
	}
	
	@DoControllerLog(name="新增用户类型")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		MemberType memberType = mapping(PREFIX, MemberType.class);
		boolean temp = Blade.create(MemberType.class).save(memberType);
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
		map.put("TypeID", id);
		Map membertype = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("membertype", membertype);
		mm.put("code", CODE);
		return BASE_PATH + "membertype_edit.html";
	}
	
	@DoControllerLog(name="修改用户类型")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		MemberType memberType = mapping(PREFIX, MemberType.class);
		boolean temp = Blade.create(MemberType.class).update(memberType);
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
		map.put("TypeID", id);
		Map memberType = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("membertype", memberType);
		mm.put("code", CODE);
		return BASE_PATH + "membertype_view.html";
	}
	
	@DoControllerLog(name="删除用户类型")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		String[] split = ids.split(",");
		String[] strs ={"1","2","3","4","5","6"};
		for (String string : split) {
			if(CollectionKit.contains(strs, string)) {
				return error("<span class=\"text-red\">ID为" + string + "的类型为固定设定，不能删除!</span>");
			}
		}
		boolean temp = Blade.create(MemberType.class).deleteByIds(ids) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}