package com.smallchill.db.config.gameuser.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.smallchill.core.plugins.dao.Db;
import com.smallchill.game.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
import com.smallchill.game.newmodel.gameuserdb.AARechargeThirdPayConfig;

@Controller
@RequestMapping("/payconfig")
public class ThirdPayConfigController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/payconfig/";
	private static String CODE = "payconfig";
	private static String LIST_SOURCE = "db_payconfig.list";
	private static String PREFIX = "payconfig";

	@Autowired
	private CommonService commonService;


	@DoControllerLog(name="进入第三方充值配置列表")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "payconfig.html";
	}
	
	@Json
	@RequestMapping(KEY_LIST)
	@Permission(ADMINISTRATOR)
	public Object list() {
		Object gird = paginateBySelf(LIST_SOURCE);
		return gird;
	}
	@DoControllerLog(name="进入新增第三方充值配置页面")
	@RequestMapping(KEY_ADD)
	@Permission(ADMINISTRATOR)
	public String add(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "payconfig_add.html";
	}
	@DoControllerLog(name="新增第三方充值配置")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		AARechargeThirdPayConfig boxItem = mapping(PREFIX, AARechargeThirdPayConfig.class);
		Date now = new Date();
		boxItem.setClient_create_time(now);
		boxItem.setUpdate_time(now);
		if(Blade.create(AARechargeThirdPayConfig.class).isExist("select [appId] FROM [QPGameUserDB].[dbo].[AA_Recharge_ThirdPayConfig] where [type]="+boxItem.getType(), null)) {
			return error("该支付方式已存在配置, 请前往进行修改.");
		}
		boolean temp = Blade.create(AARechargeThirdPayConfig.class).save(boxItem);
		if (temp) {
			return success(SAVE_SUCCESS_MSG);
		} else {
			return error(SAVE_FAIL_MSG);
		}
	}
	@DoControllerLog(name="进入修改第三方充值配置页面")
	@RequestMapping(KEY_EDIT + "/{id}")
	@Permission(ADMINISTRATOR)
	public String edit(@PathVariable Integer id, ModelMap mm) {
		/*AARechargeThirdPayConfig payconfig = Blade.create(AARechargeThirdPayConfig.class).findById(id);
		System.out.println(payconfig);*/
		Map paras = new HashMap();
		paras.put("id", id);
		Map findwx = commonService.getInfoByOne("db_payconfig.findid",paras);
		mm.put("payconfig", findwx);
		mm.put("code", CODE);
		return BASE_PATH + "payconfig_edit.html";
	}
	@DoControllerLog(name="修改第三方充值配置")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	@Transactional
	public AjaxResult update() {
		AARechargeThirdPayConfig boxItem = mapping(PREFIX, AARechargeThirdPayConfig.class);
		boxItem.setUpdate_time(new Date());
		System.out.println(boxItem);
		boolean temp = Blade.create(AARechargeThirdPayConfig.class).update(boxItem);
		if (temp) {
			System.out.println("修改完成!");
			int a = 1/0;
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}
	
	@RequestMapping(KEY_VIEW + "/{id}")
	@Permission(ADMINISTRATOR)
	public String view(@PathVariable Integer id, ModelMap mm) {
		AARechargeThirdPayConfig payconfig = Blade.create(AARechargeThirdPayConfig.class).findById(id);
		mm.put("payconfig", payconfig);
		mm.put("code", CODE);
		return BASE_PATH + "payconfig_view.html";
	}
	@DoControllerLog(name="删除第三方充值配置")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		boolean temp = Blade.create(AARechargeThirdPayConfig.class).deleteByIds(ids) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}