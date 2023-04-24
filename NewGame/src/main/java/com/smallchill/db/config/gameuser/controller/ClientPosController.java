package com.smallchill.db.config.gameuser.controller;

import java.util.HashMap;
import java.util.Map;

import com.smallchill.core.annotation.Before;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.db.config.meta.intercept.ClientPosValidator;
import com.smallchill.db.config.model.ClientPos;
import org.bouncycastle.asn1.cms.CMSAlgorithmProtection;
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
import com.smallchill.core.toolbox.kit.DateKit;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/accounttypename")
public class ClientPosController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/accounttypename/";
	private static String CODE = "accounttypename";
	private static String LIST_SOURCE = "db_accounttypename.list";
	private static String PREFIX = "accounttypename";

	@Autowired
	private CommonService commonService;

	@DoControllerLog(name="进入包配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "accounttypename.html";
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
		return BASE_PATH + "accounttypename_add.html";
	}

	@DoControllerLog(name="新增包信息")
	@Json
	@RequestMapping(KEY_SAVE)
	@Before(ClientPosValidator.class)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		ClientPos boxItem = mapping(PREFIX, ClientPos.class);
		int temp = Db.insert("insert into [login].[dbo].[ClientPos] (clientType,ratio,name,remarks) values (#{clientType},#{ratio},#{name},#{remarks})",
				boxItem);
		if (temp>0) {
			return success(SAVE_SUCCESS_MSG);
		} else {
			return error(SAVE_FAIL_MSG);
		}
	}

	@RequestMapping(KEY_EDIT + "/{id}")
	@Permission(ADMINISTRATOR)
	public String edit(@PathVariable Integer id, ModelMap mm) {
		Object firstBy = Blade.create(ClientPos.class).findFirstBy("clientType=#{clientType}", CMap.init().set("clientType", id));
		mm.put("accounttypename", firstBy);
		mm.put("code", CODE);
		return BASE_PATH + "accounttypename_edit.html";
	}

	@DoControllerLog(name="修改包信息")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Before(ClientPosValidator.class)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		ClientPos boxItem = mapping(PREFIX, ClientPos.class);
		boolean temp = Blade.create(ClientPos.class).update(boxItem);
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
		mm.put("accounttypename", bloodpondconfig);
		mm.put("code", CODE);
		return BASE_PATH + "accounttypename_view.html";
	}

	@DoControllerLog(name="删除包信息")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		boolean temp = Blade.create(ClientPos.class).deleteByIds(ids) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}