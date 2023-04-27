package com.smallchill.system.treasure.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallchill.core.toolbox.kit.MD5Utils;
import com.smallchill.core.utils.MD5;
import com.smallchill.system.service.RechargeRecordsService;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.Utils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.game.newmodel.Userbuyscoretype;
import com.smallchill.game.service.CommonService;
import com.smallchill.system.treasure.meta.intercept.RechargeValidator;

@Controller
@RequestMapping("/recharge")
public class RechargeController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/system/gamesystem/recharge/";
	private static String CODE = "recharge";
	private static String LIST_SOURCE = "recharge.new_list";
	private static String PREFIX = "recharge";
	
	@Autowired
	private CommonService commonService;
	@Autowired
	private RechargeRecordsService service;
	@DoControllerLog(name="进入充值配置列表页面")
	@RequestMapping("/")
	//@Permission({ ADMINISTRATOR, ADMIN })
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "recharge.html";
	}
	
	//	@SystemControllerLog(description = "获取充值配置列表")
	@Json
	@RequestMapping(KEY_LIST)
	//@Permission({ ADMINISTRATOR, ADMIN })
	public Object list() {
		Object gird = paginate(LIST_SOURCE);
		return gird;
	}
	
	@RequestMapping(KEY_ADD)
	@Permission({ ADMINISTRATOR, ADMIN })
	public String add(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "recharge_add.html";
	}
	
	@DoControllerLog(name="添加充值配置")
	@Json
	@Before(RechargeValidator.class)
	@RequestMapping(KEY_SAVE)
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult save() {
		Userbuyscoretype recharge = mapping(PREFIX, Userbuyscoretype.class);
		recharge.setWritedate(new Date());
		boolean temp = Blade.create(Userbuyscoretype.class).save(recharge);
		if (temp) {
			CacheKit.removeAll(SYS_CACHE);
			return success(SAVE_SUCCESS_MSG);
		} else {
			return error(SAVE_FAIL_MSG);
		}
	}

	@DoControllerLog(name="删除充值配置")
	@Json
	@RequestMapping(KEY_DEL)
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult del() {
		String ids = getParameter("ids");
		boolean temp = Blade.create(Userbuyscoretype.class).deleteByIds(ids) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
	
	@SuppressWarnings({ "rawtypes" })
	@Json
	@RequestMapping("/getRechargeList")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult getRechargeList() {
		List<Map> list = commonService.getInfoList("recharge.new_all_list",null);
		return json(list);
	}

}
