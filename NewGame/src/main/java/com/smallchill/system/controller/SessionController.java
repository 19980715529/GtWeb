package com.smallchill.system.controller;

import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.system.meta.intercept.UserIntercept;

@Controller
@RequestMapping("/session")
public class SessionController extends BaseController implements ConstShiro{
	private static String LIST_SOURCE = "user.list";
	private static String BASE_PATH = "/system/user/";
	private static String CODE = "user";
	private static String PREFIX = "blade_user";
	
	@DoControllerLog(name="进入session管理列表页面")
	@RequestMapping("/")
	//@Permission({ ADMINISTRATOR, ADMIN })
	public String index(ModelMap mm) {
		Subject subject = ShiroKit.getSubject();
		mm.put("code", CODE);
		return BASE_PATH + "user.html";
	}
	
	/**
	 * 分页aop
	 * 普通用法
	 */
	@Json
	@RequestMapping(KEY_LIST)
	public Object list() {
		Object gird = paginate(LIST_SOURCE, new UserIntercept());
		return gird;
	}
}
