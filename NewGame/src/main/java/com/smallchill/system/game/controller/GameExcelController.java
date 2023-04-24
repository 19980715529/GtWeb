package com.smallchill.system.game.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.toolbox.kit.HttpKit;

@Controller
@RequestMapping("/gameExcel")
public class GameExcelController extends BaseController implements ConstShiro{
	private static String BASE_PATH = "/excel/";
	private static String CODE = "coupon";
	
	@RequestMapping("")
	public String cionchangelog(ModelMap mm) {
		HttpServletRequest http = HttpKit.getRequest();
		String where = http.getParameter("where");
		String source = http.getParameter("source");
		String type = http.getParameter("type");
		mm.put("code", CODE);
		mm.put("where", where);
		mm.put("source", source);
		mm.put("type", type);
		return BASE_PATH + "excel.html";
	}
}
