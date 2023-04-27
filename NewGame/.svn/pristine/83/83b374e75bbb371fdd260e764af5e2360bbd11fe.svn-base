package com.smallchill.db.config.treasure.controller;

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
import com.smallchill.game.newmodel.LBloodpoolconfig;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/lbloodpoolconfig")
public class LBloodpoolconfigController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/lbloodpoolconfig/";
	private static String CODE = "lbloodpoolconfig";
	private static String LIST_SOURCE = "db_lbloodpoolconfig.list";
	private static String PREFIX = "lbloodpoolconfig";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入血池配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "lbloodpoolconfig.html";
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
		return BASE_PATH + "lbloodpoolconfig_add.html";
	}
	
	@DoControllerLog(name="新增血池配置")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		LBloodpoolconfig boxItem = mapping(PREFIX, LBloodpoolconfig.class);
		LBloodpoolconfig lBloodpoolconfig = Blade.create(LBloodpoolconfig.class).findFirstBy("serverID="+boxItem.getServerid(), null);
		if(lBloodpoolconfig != null) {
			return error("该房间已配置过血池.请前往修改.");
		}
		boolean temp = Blade.create(LBloodpoolconfig.class).save(boxItem);
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
		map.put("serverID", id);
		Map lbloodpoolconfig = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("lbloodpoolconfig", lbloodpoolconfig);
		mm.put("code", CODE);
		return BASE_PATH + "lbloodpoolconfig_edit.html";
	}
	
	@DoControllerLog(name="修改血池配置")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		LBloodpoolconfig boxItem = mapping(PREFIX, LBloodpoolconfig.class);
		StringBuilder builder = new StringBuilder();
		builder.append("zeroLine="+boxItem.getZeroline());
		builder.append(",highLine="+boxItem.getHighline());
		builder.append(",status="+boxItem.getStatus());
		builder.append(",minLine="+boxItem.getMinline());
		builder.append(",maxLine="+boxItem.getMaxline());
		builder.append(",normalEatCheat="+boxItem.getNormaleatcheat());
		builder.append(",normalOutCheat="+boxItem.getNormaloutcheat());
		builder.append(",maxOutCheat="+boxItem.getMaxoutcheat());
		builder.append(",maxFailOutCheat="+boxItem.getMaxfailoutcheat());
		builder.append(",failMinLine="+boxItem.getFailminline());
		builder.append(",failMaxLine="+boxItem.getFailmaxline());
		builder.append(",minEatCheat="+boxItem.getMineatcheat());
		builder.append(",minFailEatCheat="+boxItem.getMinfaileatcheat());
		
		boolean temp = Blade.create(LBloodpoolconfig.class).updateBy(builder.toString(), "serverID="+boxItem.getServerid(), null);
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
		map.put("serverID", id);
		Map bloodpondconfig = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("lbloodpoolconfig", bloodpondconfig);
		mm.put("code", CODE);
		return BASE_PATH + "lbloodpoolconfig_view.html";
	}
	
	@DoControllerLog(name="删除血池配置")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		boolean temp = Blade.create(LBloodpoolconfig.class).deleteBy("serverID in ("+ids+")", null) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}