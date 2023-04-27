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
 * 用户类型：普通用户，内部员工，至尊VIP等
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/config")
public class MemberTypeController extends BaseController implements ConstShiro {

	@Autowired
	private CommonService commonService;
	
	@SuppressWarnings("rawtypes")
	@Json
	@RequestMapping("/getMemberType")
	public AjaxResult getMemberType() {
		List<Map> memberTypeList = commonService.getInfoList("sys_config.MemberType",null);
		return json(memberTypeList);
	}
}
