package com.smallchill.db.config.gameuser.controller;

import java.util.Date;

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
import com.smallchill.game.newmodel.gameuserdb.Rebateaccount;

@Controller
@RequestMapping("/raccount")
public class RebateaccountController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/raccount/";
	private static String CODE = "raccount";
	private static String LIST_SOURCE = "db_raccount.list";
	private static String PREFIX = "raccount";
	
	@DoControllerLog(name="进入返税账号设置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "raccount.html";
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
		return BASE_PATH + "raccount_add.html";
	}
	
	@DoControllerLog(name="新增返税账号设置")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		Rebateaccount boxItem = mapping(PREFIX, Rebateaccount.class);
		boxItem.setUpdatetime(new Date());
		Rebateaccount acc = Blade.create(Rebateaccount.class).findFirstBy("type="+boxItem.getType(), null);
		if(acc!=null) {
			return error("该类型账号已存在!");
		}
		boolean temp = Blade.create(Rebateaccount.class).save(boxItem);
		if (temp) {
			return success(SAVE_SUCCESS_MSG);
		} else {
			return error(SAVE_FAIL_MSG);
		}
	}
	
	@RequestMapping(KEY_EDIT + "/{id}")
	@Permission(ADMINISTRATOR)
	public String edit(@PathVariable Integer id, ModelMap mm) {
		Rebateaccount raccount2 = Blade.create(Rebateaccount.class).findById(id);
		mm.put("raccount", raccount2);
		mm.put("code", CODE);
		return BASE_PATH + "raccount_edit.html";
	}
	
	@DoControllerLog(name="修改返税账号设置")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		Rebateaccount boxItem = mapping(PREFIX, Rebateaccount.class);
		boxItem.setUpdatetime(new Date());
		boolean temp = Blade.create(Rebateaccount.class).update(boxItem);
		if (temp) {
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}
	
	@DoControllerLog(name="删除返税账号设置")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		boolean temp = Blade.create(Rebateaccount.class).deleteByIds(ids) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}