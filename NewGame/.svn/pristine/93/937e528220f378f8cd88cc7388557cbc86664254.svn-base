package com.smallchill.game.config.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.utils.EnumUtil;
import com.smallchill.db.config.model.SupporTypeStatus;

@Controller
@RequestMapping("/config")
public class SupporTypeStatusController extends BaseController implements ConstShiro {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Json
	@RequestMapping("/getSupporTypeStatus")
	public AjaxResult getSupporTypeStatus() {
		SupporTypeStatus[] values = SupporTypeStatus.values();
		List<Map> list = new ArrayList<Map>();
		Map map = null;
		for (SupporTypeStatus supporTypeStatus : values) {
			map = CMap.createHashMap();
			map.put("description", supporTypeStatus.getDescription());
			map.put("code", supporTypeStatus.code());
			list.add(map);
		}
		return json(list);
	}
}
