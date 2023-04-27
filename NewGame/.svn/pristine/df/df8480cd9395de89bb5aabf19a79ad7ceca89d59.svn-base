package com.smallchill.game.config.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.game.service.CommonService;

/**
 * 充值类型：GM充值，App Store充值等
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/config")
public class GlobalShareInfoController extends BaseController implements ConstShiro {

	@Autowired
	private CommonService commonService;
	
	@SuppressWarnings("rawtypes")
	@Json
	@RequestMapping("/getGlobalShareInfo")
	public AjaxResult getMemberType() {
		List<Map> memberTypeList = commonService.getInfoList("sys_config.new_GlobalShareInfo",null);
		return json(memberTypeList);
	}
}
