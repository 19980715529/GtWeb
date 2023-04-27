package com.smallchill.game.newskin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.constant.ConstShiro;

@Controller
@RequestMapping("/newskin")
public class NewSkinDemoController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/newweb/";
	private static String CODE = "newskin";
	
	@RequestMapping("/")
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "index.html";
	}

	@RequestMapping("/main")
	public String main(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "home.html";
	}

	@RequestMapping("/Products_List")
	public String Products_List(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "Products_List.html";
	}
	@RequestMapping("/Brand_Manage")
	public String Brand_Manage(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "Brand_Manage.html";
	}
	@RequestMapping("/Category_Manage")
	public String Category_Manage(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "Category_Manage.html";
	}
	@RequestMapping("/productcategoryadd")
	public String productcategoryadd(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "product-category-add.html";
	}
	@RequestMapping("/admininfo")
	public String admininfo(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "admin_info.html";
	}
}
