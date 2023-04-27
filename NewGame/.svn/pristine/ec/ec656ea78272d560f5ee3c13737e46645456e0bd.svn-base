package com.smallchill.game.player.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.DateKit;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.game.model.RecordInsure;
import com.smallchill.game.model.UserRightType;
import com.smallchill.game.newmodel.AaShopPropUserprop;
import com.smallchill.game.newmodel.Accountsinfo;
import com.smallchill.game.newmodel.Gamescoreinfo;
import com.smallchill.game.newmodel.gameuserdb.AaZzLogPropchange;
import com.smallchill.game.newmodel.treasuredb.Recordinsure;
import com.smallchill.game.player.meta.intercept.UpdateGoldValidator;
import com.smallchill.game.request.model.GameScoreInfoRequest;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/player")
public class RecordinsureController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/gameplayer/";
	private static String CODE = "player";
	private static String PREFIX = "player";
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private HttpServletRequest request;
	@DoControllerLog(name="进入银行金币变动记录列表页面")
	@RequestMapping("/insure")
	public String insure(@RequestParam(name="id", required=false) Integer id, ModelMap mm) {
		mm.put("code", CODE);
		mm.put("id", id);
		return BASE_PATH + "player_insure_list.html";
	}

	@Json
	@RequestMapping("/insurellist")
	public Object insurellist() {
		Object gird = new Object();
		String parameter = request.getParameter("where");
		if(StrKit.isBlank(parameter)) {
			return gird;
		}
		gird = paginateBySelf("player_insure_log.list");
		return gird;
	}

	// 更新金币页面跳转
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/editInsureGold/{id}")
	@Permission({ ADMINISTRATOR, ADMIN })
	public String editInsureGold(@PathVariable Integer id, ModelMap mm) {
		Map map = new HashMap();
		map.put("UserID", id);
		Map user = commonService.getInfoByOne("player_operate.new_info", map);
		mm.put("player", user);
		mm.put("code", CODE);
		return BASE_PATH + "player_edit_insure_gold.html";
	}
	
	/*
	 * 扣除玩家银行金币
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Json
	@Before(UpdateGoldValidator.class)
	@RequestMapping("/updateinsureGold")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult updateinsureGold() {
		GameScoreInfoRequest request = mapping(PREFIX, GameScoreInfoRequest.class);
		Map paras = new HashMap();
		paras.put("UserID", request.getUserid());
		// 1. 更新玩家拥有银行金币
		Gamescoreinfo oldScoreUser = Blade.create(Gamescoreinfo.class).findFirstBy(" UserID="+request.getUserid(), null);
		
		Long nScore = request.getOldScore() - request.getScore(); // 变动后银行金币
		if(nScore<0) {
			nScore = 0L;
		}
		oldScoreUser.setInsurescore(nScore);
		boolean temp = Blade.create(Gamescoreinfo.class).update(oldScoreUser);
		
		Date now = new Date();
		// 2. 更新银行金币变动记录表[RecordInsure],更新类型为3，web管理员添加银行金币
		Accountsinfo ainfo = Blade.create(Accountsinfo.class).findById(request.getUserid());
		Recordinsure ri = new Recordinsure();
		HttpServletRequest http = HttpKit.getRequest();
		ri.setClientip(http.getRemoteAddr());
		ri.setCollectdate(now);
		ri.setCollectnote("后台管理员扣除/添加");
		ri.setDwtimer(0);
		ri.setInsurescore(nScore);
		ri.setKindid(0);
		ri.setRevenue(0L);
		ri.setServerid(0);
		ri.setSourcenikename("管理员");
		ri.setSourceuserid(0);
		ri.setSwapscore(-request.getScore());
		ri.setTargetnikename(ainfo.getNickname());
		ri.setTargetuserid(request.getUserid());
		ri.setTradetype(3);
		boolean ltemp = Blade.create(Recordinsure.class).save(ri);
		if (temp && ltemp) {
			if(ainfo.getUsertype() != 1) {// 为机器人加金币不记录
				doLogByGold(request.getUserid(),UserRightType.UR_CHANGE_INSURE_GOLD.code(),(-request.getScore()),request.getOldScore(),request.getType(),request.getRemark());
			}
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}
}