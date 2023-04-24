package com.smallchill.tools.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;

@Controller
@RequestMapping("/serverexcel")
public class ServerTools extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/tools/";
	private static String CODE = "serverexcel";
	
	@RequestMapping("/")
	public String index(ModelMap mm) {
		HttpServletRequest http = getRequest();
		String source = http.getParameter("source");
		String type = http.getParameter("type");
		mm.put("code", CODE);
		mm.put("source", source);
		mm.put("type", type);
		mm.put("code", CODE);
		return BASE_PATH + "serverexcel.html";
	}
	
	@RequestMapping("/gift/")
	public String gift(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "vip.html";
	}
	@Json
	@RequestMapping(KEY_LIST)
	public Object list() {
		Object gird = new Object();
		gird = paginateBySelf("vip.svip_list");
		return gird;
	}
}
