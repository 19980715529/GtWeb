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

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.db.config.model.ConfigInfoInit;
import com.smallchill.db.config.model.Platforminfo;
import com.smallchill.game.model.ConfigInfo;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/newsplatform")
public class PlatformInfoController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/platform/";
	private static String CODE = "newsplatform";
	private static String LIST_SOURCE = "db_platform.new_list";
	private static String PREFIX = "platform";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入平台配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "platform.html";
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
		return BASE_PATH + "platform_add.html";
	}
	
	@DoControllerLog(name="新增平台")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		Platforminfo boxItem = mapping(PREFIX, Platforminfo.class);
		int id = Blade.create(Platforminfo.class).saveRtId(boxItem);
		if (id != 0) {
			// 初始化平台站点配置
			ConfigInfoInit[] values = ConfigInfoInit.values();
			List<ConfigInfo> list = new ArrayList<ConfigInfo>();
			ConfigInfo info = null;
			for (ConfigInfoInit configInfoInit : values) {
				info = new ConfigInfo();
				info.setConfigkey(configInfoInit.code() + id);
				info.setConfigname(configInfoInit.getDescription());
				info.setConfigstring("");
				info.setField1("");
				info.setField2("");
				info.setField3("");
				info.setField4("");
				info.setField5("");
				info.setField6("");
				info.setField7("");
				info.setField8("");
				info.setPlatformid(id);
				info.setSortid(0);
				
				list.add(info);
			}
			
			Blade.create(ConfigInfo.class).saveBatch(list);
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
		map.put("ID", id);
		Map platform = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("platform", platform);
		mm.put("code", CODE);
		return BASE_PATH + "platform_edit.html";
	}
	
	@DoControllerLog(name="修改平台")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		Platforminfo boxItem = mapping(PREFIX, Platforminfo.class);
		boolean temp = Blade.create(Platforminfo.class).update(boxItem);
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
		map.put("ID", id);
		Map bloodpondconfig = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("platform", bloodpondconfig);
		mm.put("code", CODE);
		return BASE_PATH + "platform_view.html";
	}
	
	@DoControllerLog(name="删除平台")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		boolean temp = Blade.create(Platforminfo.class).deleteByIds(ids) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}

	/**
	 *  获取所有包数据
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Json
	@RequestMapping("/getPlatformInfo")
	public AjaxResult getPlatformInfo() {
		List<Map> list = commonService.getInfoList(LIST_SOURCE,null);
		return json(list);
	}
}