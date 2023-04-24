package com.smallchill.db.config.gameuser.controller;
//package com.smallchill.db.config.controller.gameuser;
//
//import java.util.Date;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.smallchill.common.base.BaseController;
//import com.smallchill.core.annotation.DoControllerLog;
//import com.smallchill.core.annotation.Json;
//import com.smallchill.core.annotation.Permission;
//import com.smallchill.core.constant.ConstShiro;
//import com.smallchill.core.plugins.dao.Blade;
//import com.smallchill.core.toolbox.ajax.AjaxResult;
//import com.smallchill.game.newmodel.gameuserdb.AaRechargeThirdpayplatform;
//
//@Controller
//@RequestMapping("/thirdplatform")
//public class AaRechargeThirdpayplatformController extends BaseController implements ConstShiro {
//	private static String BASE_PATH = "/db/thirdplatform/";
//	private static String CODE = "thirdplatform";
//	private static String LIST_SOURCE = "db_thirdplatform.list";
//	private static String PREFIX = "thirdplatform";
//	
//	@DoControllerLog(name="进入第三方充值配置列表")
//	@RequestMapping("/")
//	@Permission(ADMINISTRATOR)
//	public String index(ModelMap mm) {
//		mm.put("code", CODE);
//		return BASE_PATH + "thirdplatform.html";
//	}
//	
//	@Json
//	@RequestMapping(KEY_LIST)
//	@Permission(ADMINISTRATOR)
//	public Object list() {
//		Object gird = paginateBySelf(LIST_SOURCE);
//		return gird;
//	}
//	@DoControllerLog(name="进入新增第三方充值配置页面")
//	@RequestMapping(KEY_ADD)
//	@Permission(ADMINISTRATOR)
//	public String add(ModelMap mm) {
//		mm.put("code", CODE);
//		return BASE_PATH + "thirdplatform_add.html";
//	}
//	@DoControllerLog(name="新增第三方充值配置")
//	@Json
//	@RequestMapping(KEY_SAVE)
//	@Permission(ADMINISTRATOR)
//	public AjaxResult save() {
//		AaRechargeThirdpayplatform boxItem = mapping(PREFIX, AaRechargeThirdpayplatform.class);
//		Date now = new Date();
//		boxItem.setUpdate_time(now);
//		if(Blade.create(AaRechargeThirdpayplatform.class).isExist("select [appId] FROM [QPGameUserDB].[dbo].[AA_Recharge_ThirdPayConfig] where [type]=", null)) {
//			return error("该支付方式已存在配置, 请前往进行修改.");
//		}
//		boolean temp = Blade.create(AaRechargeThirdpayplatform.class).save(boxItem);
//		if (temp) {
//			return success(SAVE_SUCCESS_MSG);
//		} else {
//			return error(SAVE_FAIL_MSG);
//		}
//	}
//	@DoControllerLog(name="进入修改第三方充值配置页面")
//	@RequestMapping(KEY_EDIT + "/{id}")
//	@Permission(ADMINISTRATOR)
//	public String edit(@PathVariable Integer id, ModelMap mm) {
//		AaRechargeThirdpayplatform thirdplatform = Blade.create(AaRechargeThirdpayplatform.class).findById(id);
//		mm.put("thirdplatform", thirdplatform);
//		mm.put("code", CODE);
//		return BASE_PATH + "thirdplatform_edit.html";
//	}
//	@DoControllerLog(name="修改第三方充值配置")
//	@Json
//	@RequestMapping(KEY_UPDATE)
//	@Permission(ADMINISTRATOR)
//	public AjaxResult update() {
//		AaRechargeThirdpayplatform boxItem = mapping(PREFIX, AaRechargeThirdpayplatform.class);
//		boxItem.setUpdate_time(new Date());
//		boolean temp = Blade.create(AaRechargeThirdpayplatform.class).update(boxItem);
//		if (temp) {
//			return success(UPDATE_SUCCESS_MSG);
//		} else {
//			return error(UPDATE_FAIL_MSG);
//		}
//	}
//	
//	@RequestMapping(KEY_VIEW + "/{id}")
//	@Permission(ADMINISTRATOR)
//	public String view(@PathVariable Integer id, ModelMap mm) {
//		AaRechargeThirdpayplatform thirdplatform = Blade.create(AaRechargeThirdpayplatform.class).findById(id);
//		mm.put("thirdplatform", thirdplatform);
//		mm.put("code", CODE);
//		return BASE_PATH + "thirdplatform_view.html";
//	}
//	@DoControllerLog(name="删除第三方充值配置")
//	@Json
//	@RequestMapping(KEY_REMOVE)
//	@Permission(ADMINISTRATOR)
//	public AjaxResult remove() {
//		String ids = getParameter("ids");
//		boolean temp = Blade.create(AaRechargeThirdpayplatform.class).deleteByIds(ids) > 0;
//		if (temp) {
//			return success("删除成功!");
//		} else {
//			return error("删除失败!");
//		}
//	}
//}