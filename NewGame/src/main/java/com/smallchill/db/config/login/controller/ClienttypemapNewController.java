package com.smallchill.db.config.login.controller;

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
import com.smallchill.game.newmodel.logindb.ClienttypemapNew;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/clienttypemapnew")
public class ClienttypemapNewController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/clienttypemapnew/";
	private static String CODE = "clienttypemapnew";
	private static String LIST_SOURCE = "db_clienttypemapnew.list";
	private static String PREFIX = "clienttypemapnew";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入包开发游戏访问配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "clienttypemapnew.html";
	}
	
	@Json
	@RequestMapping(KEY_LIST)
	@Permission(ADMINISTRATOR)
	public Object list() {
		Object gird = paginateBySelfByName("logindb",LIST_SOURCE);
		return gird;
	}
	
	@RequestMapping(KEY_ADD)
	@Permission(ADMINISTRATOR)
	public String add(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "clienttypemapnew_add.html";
	}
	
	@DoControllerLog(name="新增包开发游戏访问配置")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		ClienttypemapNew boxItem = mapping(PREFIX, ClienttypemapNew.class);
		ClienttypemapNew clientpos = Blade.create(ClienttypemapNew.class).findFirstBy("NewType="+boxItem.getNewtype(), null);
		if(clientpos != null) {
			return error("新包ID记录已存在，请前往修改.");
		}
		boolean temp = Blade.create(ClienttypemapNew.class).save(boxItem);
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
		map.put("NewType", id);
		Map clienttypemapnew = commonService.getInfoByOneDbName("logindb",LIST_SOURCE, map);
		mm.put("clienttypemapnew", clienttypemapnew);
		mm.put("code", CODE);
		return BASE_PATH + "clienttypemapnew_edit.html";
	}
	
	@DoControllerLog(name="修改包开发游戏访问配置")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		ClienttypemapNew boxItem = mapping(PREFIX, ClienttypemapNew.class);
		StringBuilder builder = new StringBuilder();
		builder.append("IsOnline="+boxItem.getIsonline());
		builder.append(",IsCanSeeAll="+boxItem.getIscanseeall());
		builder.append(",IsForbidActivity="+boxItem.getIsforbidactivity());
		builder.append(",IsForbidGift="+boxItem.getIsforbidgift());
		builder.append(",IsForbidSign="+boxItem.getIsforbidsign());
		builder.append(",canSeeCustomHead="+boxItem.getCanseecustomhead());
		builder.append(",RechargeGift="+boxItem.getRechargegift());
//		builder.append(",IsUserThirdRecharge="+boxItem.getIsuserthirdrecharge());
		builder.append(",IsUserThirdRecharge='"+boxItem.getIsuserthirdrecharge()+"'");
		builder.append(",OldType="+boxItem.getOldtype());
		
		boolean temp = Blade.create(ClienttypemapNew.class).updateBy(builder.toString(), "NewType="+boxItem.getNewtype(), null);
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
		map.put("NewType", id);
		Map bloodpondconfig = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("clienttypemapnew", bloodpondconfig);
		mm.put("code", CODE);
		return BASE_PATH + "clienttypemapnew_view.html";
	}
	
	@DoControllerLog(name="删除包开发游戏访问配置")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		boolean temp = Blade.create(ClienttypemapNew.class).deleteBy("NewType in ("+ids+")", null) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}