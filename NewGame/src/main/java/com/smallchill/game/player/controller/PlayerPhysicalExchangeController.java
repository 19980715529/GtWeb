package com.smallchill.game.player.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.aop.SystemControllerLog;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.DateKit;
import com.smallchill.game.newmodel.gameuserdb.AaShopGoodsUsergoods;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/player")
public class PlayerPhysicalExchangeController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/gameplayer/";
	private static String CODE = "player";
	private static String PREFIX = "award_order";
	
	@Autowired
	private CommonService commonService;

	// 实物兑换记录
	@DoControllerLog(name="进入实物兑换记录列表页面")
	@RequestMapping(KEY_PLAYER_PEL_LOG)
	public String pelog(@RequestParam(name="id", required=false) Integer id,ModelMap mm) {
		mm.put("code", CODE);
		mm.put("id", id);
		return BASE_PATH + "player_physicalexchange_log.html";
	}
	
	//	@SystemControllerLog(description = "获取实物兑换记录列表")
	@Json
	@RequestMapping("/pellist")
	public Object pellist() {
		Object gird = new Object();
		gird = paginateBySelf("player_physical_exchange_log.new_physical_exchange");
		return gird;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(KEY_PEEDIT + "/{id}")
	@Permission({ ADMINISTRATOR, ADMIN })
	public String edit(@PathVariable String id, ModelMap mm) {
		Map paras = new HashMap();
		paras.put("OrderID", id);
		Map order = commonService.getInfoByOne("player_physical_exchange_log.new_physical_exchange", paras);
		mm.put(PREFIX, order);
		mm.put("code", CODE);
		return BASE_PATH + "player_physicalexchange_edit.html";
	}
	
	@Json
	@RequestMapping("/peupdate")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult peupdate(){
		AaShopGoodsUsergoods rquestOrder = mapping(PREFIX, AaShopGoodsUsergoods.class);
		
		boolean temp = Blade.create(AaShopGoodsUsergoods.class).update(rquestOrder);
		if (temp) {
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}

	@SystemControllerLog(description = "发货")
	@Json
	@RequestMapping("/deliver")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult deliver(){
		String ids = getParameter("ids");
		AaShopGoodsUsergoods order = Blade.create(AaShopGoodsUsergoods.class).findById(ids);
		order.setCurrentstate(1);
		order.setHandletime(new Date());
		order.setExpressinformation("<br/>【" + DateKit.getTime() + "确认货已发出】<br/>【操作人：" + ShiroKit.getUser().getName() + "】");
		boolean temp = Blade.create(AaShopGoodsUsergoods.class).update(order);
		if (temp) {
			return success(DELIVER_SUCCESS_MSG);
		} else {
			return error(DELIVER_FAIL_MSG);
		}
	}
}