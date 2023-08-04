package com.smallchill.game.player.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.smallchill.game.player.meta.intercept.BindPhoneValidator;
import com.smallchill.game.player.util.utils;
import com.smallchill.system.treasure.utils.RechargeExchangeCommon;
import com.smallchill.system.treasure.utils.SendHttp;
import org.beetl.sql.core.OnConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.common.vo.ShiroUser;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.cache.ILoader;
import com.smallchill.core.toolbox.kit.CollectionKit;
import com.smallchill.core.toolbox.kit.DateKit;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.core.utils.MD5;
import com.smallchill.core.utils.UserRightUtil;
import com.smallchill.game.model.UserMeliProp;
import com.smallchill.game.model.UserRightType;
import com.smallchill.game.newmodel.AaGiverecord;
import com.smallchill.game.newmodel.AaShopPropUserprop;
import com.smallchill.game.newmodel.Accountsinfo;
import com.smallchill.game.newmodel.Gamescoreinfo;
import com.smallchill.game.newmodel.Gamescorelocker;
import com.smallchill.game.newmodel.Gmchangecheatrecord;
import com.smallchill.game.newmodel.Userbuyscoretype;
import com.smallchill.game.newmodel.Userbyscorebygm;
import com.smallchill.game.newmodel.Userchange;
import com.smallchill.game.newmodel.gameuserdb.AAChangeRecordData;
import com.smallchill.game.newmodel.gameuserdb.AaZzLogPropchange;
import com.smallchill.game.newmodel.gameuserdb.Accountstipnamedesc;
import com.smallchill.game.newmodel.gameuserdb.UserSendgoosrecord;
import com.smallchill.game.newmodel.logindb.Loginipforvip;
import com.smallchill.game.newmodel.serverinfodb.Changeviplevel;
import com.smallchill.game.newmodel.treasuredb.Recordinsure;
import com.smallchill.game.player.meta.intercept.PlayerPasswordValidator;
import com.smallchill.game.player.meta.intercept.PointControlValidator;
import com.smallchill.game.player.meta.intercept.UpdateGoldValidator;
import com.smallchill.game.request.model.GameScoreInfoRequest;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/player")
public class PlayerOperateController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/gameplayer/";
	private static String CODE = "player";
	private static String PREFIX = "player";

	@Autowired
	private CommonService commonService;

	// 更新金币页面跳转
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/editGold/{id}")
	@Permission({ ADMINISTRATOR, ADMIN })
	public String editGold(@PathVariable Integer id, ModelMap mm) {
		ShiroUser checkadmin = ShiroKit.getUser();
		final Integer admin = Convert.toInt(checkadmin.getId());
		Map<String, String> rolemap = getRole(admin);
		String roleout = rolemap.get("roleOut");
		if (roleout.contains("438")) {
			return "/error/permission.html";
		}
		Map map = new HashMap();
		map.put("UserID", id);
		map.put("Type", 1);
		Map user = commonService.getInfoByOne("player_operate.new_info", map);
		mm.put("player", user);
		mm.put("code", CODE);
		return BASE_PATH + "player_edit_gold.html";
	}

	// 更新银行金币页面跳转
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/editBank/{id}")
	@Permission({ ADMINISTRATOR, ADMIN })
	public String editBank(@PathVariable Integer id, ModelMap mm) {
		ShiroUser checkadmin = ShiroKit.getUser();
		final Integer admin = Convert.toInt(checkadmin.getId());
		Map<String, String> rolemap = getRole(admin);
		String roleout = rolemap.get("roleOut");
		if (roleout.contains("439")) {
			return "/error/permission.html";
		}
		Map map = new HashMap();
		map.put("UserID", id);
		map.put("Type", 2);
		Map user = commonService.getInfoByOne("player_operate.new_info", map);
		mm.put("player", user);
		mm.put("code", CODE);
		return BASE_PATH + "player_edit_insure_gold.html";
	}

	// 更新奖券页面跳转
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/editCoupon/{id}")
	@Permission({ ADMINISTRATOR, ADMIN })
	public String editCoupon(@PathVariable Integer id, ModelMap mm) {
		Map map = new HashMap();
		map.put("UserID", id);
		map.put("Type", 2);
		Map user = commonService.getInfoByOne("player_operate.new_info", map);
		mm.put("player", user);
		mm.put("code", CODE);
		return BASE_PATH + "player_edit_coupon.html";
	}

	// 更新输赢作弊率页面跳转
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/editWin/{id}")
	@Permission({ ADMINISTRATOR, ADMIN })
	public String editWin(@PathVariable Integer id, ModelMap mm) {
		ShiroUser checkadmin = ShiroKit.getUser();
		final Integer admin = Convert.toInt(checkadmin.getId());
		Map<String, String> rolemap = getRole(admin);
		String roleout = rolemap.get("roleOut");
		if (roleout.contains("459")) {
			return "/error/permission.html";
		}
		Map map = new HashMap();
		map.put("UserID", id);
		Map user = commonService.getInfoByOne("point_control_record.new_control_score_info", map);
		if (user == null) {
			user = commonService.getInfoByOne("player_operate.new_info", map);
		}
		mm.put("player", user);
		mm.put("code", CODE);
		return BASE_PATH + "player_edit_win.html";
	}

	// 更新输赢作弊率2页面跳转
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/editWin2/{id}")
	@Permission({ ADMINISTRATOR, ADMIN })
	public String editWin2(@PathVariable Integer id, ModelMap mm) {
		ShiroUser checkadmin = ShiroKit.getUser();
		final Integer admin = Convert.toInt(checkadmin.getId());
		Map<String, String> rolemap = getRole(admin);
		String roleout = rolemap.get("roleOut");
		if (roleout.contains("466")) {
			return "/error/permission.html";
		}
		Map map = new HashMap();
		map.put("UserID", id);
		Map user = commonService.getInfoByOne("point_control_record.new_control_score_info", map);
		if (user == null) {
			user = commonService.getInfoByOne("player_operate.new_info", map);
		}
		mm.put("player", user);
		mm.put("code", CODE);
		return BASE_PATH + "player_edit_win2.html";
	}

	// 更改密码页面跳转
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/editPassword/{id}")
	@Permission({ ADMINISTRATOR, ADMIN })
	public String editPassword(@PathVariable Integer id, ModelMap mm) {
		Map map = new HashMap();
		map.put("UserID", id);
		Map user = commonService.getInfoByOne("player_operate.new_info", map);
		mm.put("player", user);
		mm.put("code", CODE);
		return BASE_PATH + "player_edit_password.html";
	}

	// 更改玩家昵称页面跳转
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/editNickName/{id}")
	@Permission({ ADMINISTRATOR, ADMIN })
	public String editNickName(@PathVariable Integer id, ModelMap mm) {
		Map map = new HashMap();
		map.put("UserID", id);
		Map user = commonService.getInfoByOne("player_operate.new_info", map);
		mm.put("player", user);
		mm.put("code", CODE);
		return BASE_PATH + "player_edit_nickname.html";
	}

	// 更换修改玩家魅力卡页面跳转
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/updateNickName/{id}")
	@Permission({ ADMINISTRATOR, ADMIN })
	public String updateNickName(@PathVariable Integer id, ModelMap mm) {
		Map map = new HashMap();
		map.put("UserID", id);
		Map user = commonService.getInfoByOne("player_operate.new_info", map);
		mm.put("player", user);
		mm.put("code", CODE);
		Map findmeili = commonService.getInfoByOne("player_userMeliProp.new_finduser", map);
		if (findmeili != null) {
			mm.put("meili", findmeili);
		} else {
			Map nonemeli = new HashMap();
			nonemeli.put("meilipropcount", "");
			nonemeli.put("tadyusecount", "");
			nonemeli.put("changetime", "");
			mm.put("meili", nonemeli);
		}
		return BASE_PATH + "player_edit_usermeliprop.html";
	}

	// 更改用户类型页面跳转
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/editUserType/{id}")
	@Permission({ ADMINISTRATOR, ADMIN })
	public String editUserType(@PathVariable Integer id, ModelMap mm) {
		Map map = new HashMap();
		map.put("UserID", id);
		Map user = commonService.getInfoByOne("player_operate.new_info", map);
		mm.put("player", user);
		mm.put("code", CODE);
		return BASE_PATH + "player_edit_usertype.html";
	}

	// 设置用户是否引流
	@RequestMapping("/edisIsDrain/{id}")
	@Permission({ ADMINISTRATOR, ADMIN })
	public String edisIsDrain(@PathVariable Integer id, ModelMap mm){
		Map map = new HashMap();
		map.put("UserID", id);
		Map user = commonService.getInfoByOne("player_operate.new_info", map);
		mm.put("player", user);
		mm.put("code", CODE);
		return BASE_PATH + "player_edit_IsDrain.html";
	}
	// 绑定手机
	@RequestMapping("/bindPhone/{id}")
	@Permission({ ADMINISTRATOR, ADMIN })
	public String edbindPhone(@PathVariable Integer id, ModelMap mm){
		Map map = new HashMap();
		map.put("UserID", id);
		Map user = commonService.getInfoByOne("player_operate.new_info", map);
		mm.put("player", user);
		mm.put("code", CODE);
		return BASE_PATH + "player_edit_bindPhone.html";
	}
	//修改代理等级
	@RequestMapping("/agent/{id}")
	@Permission({ ADMINISTRATOR, ADMIN })
	public String editAgent(@PathVariable Integer id, ModelMap mm){
		Map map = new HashMap();
		map.put("UserID", id);
		Map user = commonService.getInfoByOne("player_operate.new_info", map);
		mm.put("player", user);
		mm.put("code", CODE);
		return BASE_PATH + "player_edit_Agent.html";
	}
	@Json
	@RequestMapping("/updateAgent")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult updateAgent() {
		String agentLevel = HttpKit.getRequest().getParameter("player.AgentLevel");
		String UserID = HttpKit.getRequest().getParameter("player.UserID");
		int temp = Db.update("[QPGameUserDB].[dbo].[PlayerShare]", "UserID", CMap.init().set("AgentLevel", agentLevel).set("UserID", UserID));
		if (temp>0){
			return success(UPDATE_SUCCESS_MSG);
		}
		return error(UPDATE_FAIL_MSG);
	}

	// GM充值页面跳转
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/editRecharge/{id}")
	@Permission({ ADMINISTRATOR, ADMIN })
	public String editRecharge(@PathVariable Integer id, ModelMap mm) {
		Map map = new HashMap();
		map.put("UserID", id);
		Map user = commonService.getInfoByOne("player_operate.new_info", map);
		mm.put("player", user);
		mm.put("code", CODE);
		return BASE_PATH + "player_edit_gmrecharge.html";
	}

	@RequestMapping("/relation")
	@Permission({ ADMINISTRATOR, ADMIN })
	public String relation(@RequestParam(name = "id", required = false) Integer id,
			@RequestParam(name = "type", required = false) String type,
			@RequestParam(name = "locktype", required = false) String locktype, ModelMap mm) {
		mm.put("code", CODE);
		mm.put("id", id);
		mm.put("type", type);
		mm.put("locktype", locktype);
		if (StrKit.equals(type, "single")) {
			if (StrKit.equals(locktype, "lock")) {
				mm.put("url", "locked");
			} else {
				mm.put("url", "locktrade");
			}
		} else {
			if (StrKit.equals(locktype, "lock")) {
				mm.put("url", "relationlocked");
			} else {
				mm.put("url", "relationlocktrade");
			}
		}
		return BASE_PATH + "player_relation.html";
	}

	/*
	 * 扣除玩家金币
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Json
	@Before(UpdateGoldValidator.class)
	@RequestMapping("/updateGold")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult updateGold() {
		GameScoreInfoRequest request = mapping(PREFIX, GameScoreInfoRequest.class);
		Map paras = new HashMap();
		paras.put("UserID", request.getUserid());
		// 1. 更新玩家拥有金币
		AaShopPropUserprop oldScoreUser = Blade.create(AaShopPropUserprop.class)
				.findFirstBy(" User_Id=" + request.getUserid() + " and Prop_Id=1", null);

		AaShopPropUserprop info = new AaShopPropUserprop();
		Long nScore = request.getOldScore() - request.getScore();
		if (nScore < 0) {
			nScore = 0L;
			request.setScore(request.getOldScore());
		}
		info.setAmount(nScore);
		boolean temp = false;
		Date now = new Date();
		if (oldScoreUser != null) {
			temp = Blade.create(AaShopPropUserprop.class).updateBy(
					" Amount=" + nScore + ",StopTime='" + DateKit.getTime() + "'",
					" User_Id=" + request.getUserid() + " and Prop_Id=1", null);
		} else {
			info.setIsuse(0);
			info.setProp_Id(1);
			info.setRemaintime(0);
			info.setStarttime(now);
			info.setStoptime(now);
			info.setUser_Id(request.getUserid());
			temp = Blade.create(AaShopPropUserprop.class).save(info);
		}
		// 2. 更新金币变动记录表AA_ZZ_Log_PropChange,更新类型为24，web管理员添加金币
		AaZzLogPropchange aaZzLogPropchange = new AaZzLogPropchange();
		aaZzLogPropchange.setUser_Id(request.getUserid());
		aaZzLogPropchange.setProp_Id(1);
		aaZzLogPropchange.setPreamount(request.getOldScore());
		aaZzLogPropchange.setAmount(-request.getScore());
		aaZzLogPropchange.setAftamount(nScore);
		aaZzLogPropchange.setIsfromsystem(0);
		aaZzLogPropchange.setKindid(0);
		aaZzLogPropchange.setServerid(0);
		aaZzLogPropchange.setTableid(0);
		aaZzLogPropchange.setNo(0);
		aaZzLogPropchange.setChangeType_Id(24);
		aaZzLogPropchange.setLogtime(now);
		aaZzLogPropchange.setRemark("管理员扣除金币,操作类型：" + request.getType() + ",操作理由：" + request.getRemark());
		boolean ltemp = Blade.create(AaZzLogPropchange.class).save(aaZzLogPropchange);

		if (temp && ltemp) {
			Accountsinfo accountsinfo = Blade.create(Accountsinfo.class).findById(request.getUserid());
			if (accountsinfo.getIsRobit() != 1) {// 为机器人加金币不记录
				doLogByGold(request.getUserid(), UserRightType.UR_CHANGE_GOLD.code(), (-request.getScore()),
						request.getOldScore(), request.getType(), request.getRemark());
			}
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}

	/*
	 * 扣除玩家银行金币
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Json
	@Before(UpdateGoldValidator.class)
	@RequestMapping("/updateBankGold")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult updateinsureGold() {
		GameScoreInfoRequest request = mapping(PREFIX, GameScoreInfoRequest.class);
		Map paras = new HashMap();
		paras.put("UserID", request.getUserid());
		// 更新玩家拥有银行金币
		Gamescoreinfo oldScoreUser = Blade.create(Gamescoreinfo.class).findFirstBy(" UserID=" + request.getUserid(),
				null);

		Long nScore = request.getOldScore() - request.getScore(); // 变动后银行金币
		if (nScore < 0) {
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
			if (ainfo.getIsRobit() != 1) {// 为机器人加金币不记录
				doLogByGold(request.getUserid(), UserRightType.UR_CHANGE_INSURE_GOLD.code(), (-request.getScore()),
						request.getOldScore(), request.getType(), request.getRemark());
			}
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}

	/*
	 * 扣除玩家奖券
	 */
	@Json
	@Before(UpdateGoldValidator.class)
	@RequestMapping("/updateCoupon")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult updateCoupon() {
		GameScoreInfoRequest request = mapping(PREFIX, GameScoreInfoRequest.class);
		// 更新玩家奖券
		AaShopPropUserprop oldScoreUser = Blade.create(AaShopPropUserprop.class)
				.findFirstBy(" User_Id=" + request.getUserid() + " and Prop_Id=2", null);
		Long nScore = request.getOldUserMedal() - request.getUserMedal();
		if (nScore < 0) {
			nScore = 0L;
		}
		boolean temp = false;
		Date now = new Date();
		if (oldScoreUser != null) {
			temp = Blade.create(AaShopPropUserprop.class).updateBy(
					" Amount=" + nScore + ",StopTime='" + DateKit.getTime() + "'",
					" User_Id=" + request.getUserid() + " and Prop_Id=2", null);
		} else {
			AaShopPropUserprop info = new AaShopPropUserprop();
			info.setAmount(nScore);
			info.setIsuse(0);
			info.setProp_Id(2);
			info.setRemaintime(0);
			info.setStarttime(now);
			info.setStoptime(now);
			info.setUser_Id(request.getUserid());
			temp = Blade.create(AaShopPropUserprop.class).save(info);
		}

		// 2. 更新金币变动记录表AA_ZZ_Log_PropChange,更新类型为38，web管理员添加金币
		AaZzLogPropchange aaZzLogPropchange = new AaZzLogPropchange();
		aaZzLogPropchange.setUser_Id(request.getUserid());
		aaZzLogPropchange.setProp_Id(2);
		aaZzLogPropchange.setPreamount(request.getOldUserMedal());
		aaZzLogPropchange.setAmount(-request.getUserMedal());
		aaZzLogPropchange.setAftamount(nScore);
		aaZzLogPropchange.setIsfromsystem(0);
		aaZzLogPropchange.setKindid(0);
		aaZzLogPropchange.setServerid(0);
		aaZzLogPropchange.setTableid(0);
		aaZzLogPropchange.setNo(0);
		aaZzLogPropchange.setChangeType_Id(38);
		aaZzLogPropchange.setLogtime(now);
		aaZzLogPropchange.setRemark("管理员扣除奖券,操作类型：" + request.getType() + ",操作理由：" + request.getRemark());
		boolean ltemp = Blade.create(AaZzLogPropchange.class).save(aaZzLogPropchange);

		if (temp && ltemp) {
			// 记录日志
			Accountsinfo accountsinfo = Blade.create(Accountsinfo.class).findById(request.getUserid());
			if (accountsinfo.getUsertype() != 1) {// 为机器人加奖券不记录
				doLogByGold(request.getUserid(), UserRightType.UR_CHANGE_COUPON.code(),
						(-Long.parseLong(request.getUserMedal().toString())),
						Long.parseLong(request.getOldUserMedal().toString()), request.getType(), request.getRemark());
			}
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}

	/*
	 * 更新玩家作弊率
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Before(PointControlValidator.class)
	@Json
	@RequestMapping("/updateWin")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult updateWin() {
		GameScoreInfoRequest request = mapping(PREFIX, GameScoreInfoRequest.class);
		Map paras = new HashMap();
		paras.put("UserID", request.getUserid());
		// 1. 更新作弊率设置Gamescoreinfo表
		Gamescoreinfo config = Blade.create(Gamescoreinfo.class).findFirstBy(" where UserID=#{UserID}", paras);
		Gmchangecheatrecord record = new Gmchangecheatrecord();
		boolean isNew = false;
		if (config == null) {
			record.setOldlcheatlimit(0L);
			record.setOldlcheatrate(0L);
			isNew = true;
			config = new Gamescoreinfo();
		} else {
			Long rate = Long.parseLong(config.getCheatrate().toString());
			record.setOldlcheatrate(rate);
			record.setOldlcheatlimit(config.getLimitscore());
		}
		if (request.getCheatingrateset() == -3000) {
			config.setIsCanWinBigScore(1);
		}
		config.setUserid(request.getUserid());
		config.setCheatrate(request.getCheatingrateset());
		config.setLimitscore(request.getCheatingscoreset());
		config.setBloodscore(0L);
		boolean temp = false;
		int id = 0;
		Date now = new Date();
		if (isNew) {
			config.setChangescoretime(now);
			config.setAlllogontimes(0);
			config.setBloodscore(0L);
			config.setConsecration(0L);
			config.setControltimes(0);
			config.setDrawcount(0);
			config.setFleecount(0);
			config.setInsurescore(0L);
			config.setIsbuyscore(0);
			config.setJs_BussniessCount(0L);
			config.setJs_userCount(0L);
			config.setLastaddcheattime(now);
			config.setLastendcheattime(now);
			Accountsinfo accountsinfo = Blade.create(Gamescoreinfo.class).findById(request.getUserid());
			config.setLastlogondate(accountsinfo.getLastlogondate());
			config.setLastlogonip(accountsinfo.getLastlogonip());
			config.setLcheatlimit(0);
			config.setLcheatrate(0);
			config.setLcurcheatcount(0);
			config.setLostcount(0);
			config.setLtotalscore(0L);
			config.setLtotlescore(0L);
			config.setMasterorder(0);
			config.setMasterright(0);
			config.setOnlinetimecount(0);
			config.setOut_BussniessCount(0L);
			config.setOut_userCount(0L);
			config.setPlaytimecount(0);
			config.setPlaytimeslottery(0);
			config.setPlaytimesonlineaward(0);
			config.setPrizes(0);
			config.setRealscore(0L);
			config.setRegisterdate(accountsinfo.getRegisterdate());
			config.setRevenue(0);
			config.setScore(0L);
			config.setSetautocheatflag(0);
			config.setSpread_Count(0);
			config.setTodayscore(0L);
			config.setUser_Grade("");
			config.setTransaction_Count(0);
			config.setUserright(0);
			config.setWincount(0);
			id = Blade.create(Gamescoreinfo.class).saveRtId(config);
			if (id > 0)
				temp = true;
		} else {
			temp = Blade.create(Gamescoreinfo.class).update(config);
		}

		// 2. 若玩家在线，判断Userchange表是否有记录，无记录，则新增
		if (Blade.create(Gamescorelocker.class).isExist(
				"select UserID FROM [QPTreasureDB].[dbo].[GameScoreLocker] where UserID=" + request.getUserid(),
				null)) {
			if (!Blade.create(Userchange.class)
					.isExist("select userID FROM [QPTreasureDB].[dbo].[UserChange] where userID=" + request.getUserid()
							+ " and changeType=1", null)) {
				Userchange userchange = new Userchange();
				userchange.setUserid(request.getUserid());
				userchange.setChangetype(1);
				Blade.create(Userchange.class).save(userchange);
			}
		}
		if (temp) {
			String remark = "修改前作弊率：" + request.getCheatingratenow() + ",修改前作弊分数：" + request.getCheatingscorenow()
					+ ",修改后作弊率：" + request.getCheatingrateset() + ",修改后作弊分数：" + request.getCheatingscoreset();
			doLogByGold(request.getUserid(), UserRightType.UR_CONTROL_CHEAT.code(), request.getCheatingscoreset(),
					request.getCheatingscorenow(), request.getType(), remark);
			// 3. 新增作弊记录表
			record.setNewlcheatrate(Long.parseLong(request.getCheatingrateset().toString()));
			record.setNewlcheatlimit(request.getCheatingscoreset());
			record.setWritedate(now);
			record.setGid(Integer.parseInt(JSON.toJSONString(ShiroKit.getUser().getId())));
			record.setMemo("修改玩家【" + request.getUserid() + "】作弊率,作弊限额由【" + request.getCheatingscorenow() + "】改为【"
					+ request.getCheatingscoreset() + "】");
			record.setUserid(request.getUserid());
			record.setUsernommber(0);
			record.setWinscore(config.getRealscore());
			Blade.create(Gmchangecheatrecord.class).save(record);
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}

	/*
	 * 更新玩家作弊率
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Before(PointControlValidator.class)
	@Json
	@RequestMapping("/updateWin2")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult updateWin2() {
		GameScoreInfoRequest request = mapping(PREFIX, GameScoreInfoRequest.class);
		Map paras = new HashMap();
		paras.put("UserID", request.getUserid());
		// 1. 更新作弊率设置Gamescoreinfo表
		Gamescoreinfo config = Blade.create(Gamescoreinfo.class).findFirstBy(" where UserID=#{UserID}", paras);
		// 玩家点控记录
		Gmchangecheatrecord record = new Gmchangecheatrecord();
		boolean isNew = false;
		if (config == null) {
			record.setOldlcheatlimit(0L);
			record.setOldlcheatrate(0L);
			isNew = true;
			config = new Gamescoreinfo();
		} else {
			Long rate = Long.parseLong(config.getCheatrate2().toString());
			record.setOldlcheatrate(rate);
			record.setOldlcheatlimit(Long.parseLong(config.getLimitscore2().toString()));
		}
		/*
		 * if(request.getCheatingrateset() == -3000){ config.setIsCanWinBigScore(1); }
		 */
		config.setUserid(request.getUserid());
		config.setCheatrate2(request.getCheatingrateset());
		config.setLimitscore2(request.getCheatingscoreset().intValue());
		boolean temp = false;
		int id = 0;
		Date now = new Date();
		if (isNew) {
			config.setChangescoretime(now);
			config.setBloodscore(0L);
			config.setAlllogontimes(0);
			config.setBloodscore(0L);
			config.setConsecration(0L);
			config.setControltimes(0);
			config.setDrawcount(0);
			config.setFleecount(0);
			config.setInsurescore(0L);
			config.setIsbuyscore(0);
			config.setJs_BussniessCount(0L);
			config.setJs_userCount(0L);
			config.setLastaddcheattime(now);
			config.setLastendcheattime(now);
			Accountsinfo accountsinfo = Blade.create(Gamescoreinfo.class).findById(request.getUserid());
			config.setLastlogondate(accountsinfo.getLastlogondate());
			config.setLastlogonip(accountsinfo.getLastlogonip());
			config.setLcheatlimit(0);
			config.setLcheatrate(0);
			config.setLcurcheatcount(0);
			config.setLostcount(0);
			config.setLtotalscore(0L);
			config.setLtotlescore(0L);
			config.setMasterorder(0);
			config.setMasterright(0);
			config.setOnlinetimecount(0);
			config.setOut_BussniessCount(0L);
			config.setOut_userCount(0L);
			config.setPlaytimecount(0);
			config.setPlaytimeslottery(0);
			config.setPlaytimesonlineaward(0);
			config.setPrizes(0);
			config.setRealscore(0L);
			config.setRegisterdate(accountsinfo.getRegisterdate());
			config.setRevenue(0);
			config.setScore(0L);
			config.setSetautocheatflag(0);
			config.setSpread_Count(0);
			config.setTodayscore(0L);
			config.setUser_Grade("");
			config.setTransaction_Count(0);
			config.setUserright(0);
			config.setWincount(0);
			id = Blade.create(Gamescoreinfo.class).saveRtId(config);
			if (id > 0)
				temp = true;
		} else {
			temp = Blade.create(Gamescoreinfo.class).update(config);
		}

		if (temp) {
			String remark = "修改前作弊率：" + request.getCheatingratenow() + ",修改前作弊分数：" + request.getCheatingscorenow()
					+ ",修改后作弊率：" + request.getCheatingrateset() + ",修改后作弊分数：" + request.getCheatingscoreset();
			doLogByGold(request.getUserid(), UserRightType.UR_CONTROL_CHEAT.code(), request.getCheatingscoreset(),
					request.getCheatingscorenow(), request.getType(), remark);
			// 3. 新增作弊记录表
			record.setNewlcheatrate(Long.parseLong(request.getCheatingrateset().toString()));
			record.setNewlcheatlimit(request.getCheatingscoreset());
			record.setWritedate(now);
			record.setGid(Integer.parseInt(JSON.toJSONString(ShiroKit.getUser().getId())));
			record.setMemo("修改玩家【" + request.getUserid() + "】作弊率,作弊限额由【" + request.getCheatingscorenow() + "】改为【"
					+ request.getCheatingscoreset() + "】");
			record.setUserid(request.getUserid());
			record.setUsernommber(0);
			record.setWinscore(config.getRealscore());
			Blade.create(Gmchangecheatrecord.class).save(record);
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}

	/**
	 * 向玩家发送邮件
	 * @param userId
	 * @param mm
	 * @return
	 */
	@RequestMapping("/send/{id}")
	public String send(@PathVariable Integer id, ModelMap mm){
		mm.put("code",CODE);
		mm.put("userId",id);
		return BASE_PATH +"player_send_email.html";
	}

	/*
	 * 更新玩家密码
	 */
	@Json
	@Before(PlayerPasswordValidator.class)
	@RequestMapping("/updatePassword")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult updatePassword() {
		GameScoreInfoRequest request = mapping(PREFIX, GameScoreInfoRequest.class);
		Accountsinfo info = new Accountsinfo();
		info.setUserid(request.getUserid());
		try {
			info.setLogonpass(MD5.EncoderByMd5(request.getNewPassword()));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			return error(UPDATE_FAIL_MSG);
		}
		boolean temp = Blade.create(Accountsinfo.class).update(info);
		if (temp) {
			doLogByMessage(request.getUserid().toString(), UserRightType.UR_CHANGE_PASSWORD.code(), "",
					request.getNewPassword());
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}

	/*
	 * 更新玩家备注
	 */
	@Json
	@RequestMapping("/updateNickName")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult updateNickName() {
		GameScoreInfoRequest request = mapping(PREFIX, GameScoreInfoRequest.class);
		Accountsinfo info = new Accountsinfo();
		Accountsinfo old = Blade.create(Accountsinfo.class).findById(request.getUserid());
		info.setUserid(request.getUserid());
		info.setTipsname(request.getNickName());
		boolean temp = Blade.create(Accountsinfo.class).update(info);

		Accountstipnamedesc desc = Blade.create(Accountstipnamedesc.class).findFirstBy("UserID=" + request.getUserid(),
				null);
		if (StrKit.notBlank(request.getRemark())) {
			if (desc == null) {
				desc = new Accountstipnamedesc();
				desc.setInserttime(new Date());
				desc.setUserid(request.getUserid());
				desc.setBody(request.getRemark());
				Blade.create(Accountstipnamedesc.class).save(desc);
			} else {
				desc.setUpdatetime(new Date());
				desc.setBody(request.getRemark());
				Blade.create(Accountstipnamedesc.class).update(desc);
			}
		}

		if (temp) {
			doLogByMessage(request.getUserid().toString(), UserRightType.UR_CHANGE_NICKNAME.code(), old.getNickname(),
					request.getNickName());
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}

	/*
	 * 修改玩家魅力卡
	 *
	 */
	@Json
	@RequestMapping("/updateUserMeliProp")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult updateUserMeliProp(HttpServletRequest req) throws ParseException {
		final GameScoreInfoRequest request = mapping(PREFIX, GameScoreInfoRequest.class);
		Map map = new HashMap();
		map.put("UserID", request.getUserid());
		boolean temp = false;
		String old = null;
		Map findmeili = commonService.getInfoByOne("player_userMeliProp.new_finduser", map);
		if (findmeili != null) {
			old = String.valueOf(findmeili.get("meilipropcount"));
			Map meili = new HashMap();
			meili.put("userid", request.getUserid());
			meili.put("meilipropcount", req.getParameter("meilipropnum"));
			String melinum;
			if (req.getParameter("meilipropnum") == null || req.getParameter("meilipropnum") == "") {
				melinum = old;
			} else {
				melinum = req.getParameter("meilipropnum");
			}
			temp = Blade.create(UserMeliProp.class).updateBy(
					" meilipropcount='" + melinum + "',tadyusecount='" + req.getParameter("tadymelicount")
							+ "',changetime='" + req.getParameter("changetime") + "'",
					"userid=" + request.getUserid(), null);
		} else {
			UserMeliProp ml = new UserMeliProp();
			ml.setUserid(request.getUserid());
			ml.setMeilipropcount(Integer.valueOf(req.getParameter("meilipropnum")));
			ml.setTadyusecount(Integer.valueOf(req.getParameter("tadymelicount")));
			String newtime = req.getParameter("changetime");
			SimpleDateFormat oneam = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date days = oneam.parse(newtime);
			ml.setChangetime(days);
			temp = Blade.create(UserMeliProp.class).save(ml);
		}

		if (temp) {
			doLogByMessage(request.getUserid().toString(), UserRightType.UR_CHANGE_NICKNAME.code(),
					req.getParameter("meilipropnum"), old);
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}

	/**
	 * 设置是否引流量updateIsDrain
	 * @return
	 */
	@Json
	@RequestMapping("/updateIsDrain")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult updateIsDrain() {
		Accountsinfo mapping = mapping(PREFIX, Accountsinfo.class);
		boolean res = Blade.create(Accountsinfo.class).update(mapping);
		if (res){
			return success(UPDATE_SUCCESS_MSG);
		}
		return error(UPDATE_FAIL_MSG);
	}

	/**
	 *  修改手机号
	 * @return
	 */
	@Json
	@RequestMapping("/updateBindPhone")
	@Before(BindPhoneValidator.class)
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult updateBindPhone() {
		Accountsinfo mapping = mapping(PREFIX, Accountsinfo.class);
		Map user = commonService.getInfoByOne("player_operate.new_info", CMap.init().set("UserID",mapping.getUserid()));
		if (user==null){
			return fail("用户不存在");
		}
		// 判断用户是否绑定手机
		if ("".equals(user.get("bindPhone").toString())){
			// 执行存储过程
			Integer integer = utils.SendBindPhoneEmail(mapping.getUserid(), mapping.getBindphone(),mapping.getEmail());
			LOGGER.error("执行存储过程:"+integer);
			if (integer==0){
				return success("修改成功");
			}else {
				return fail("修改失败");
			}
		}
		mapping.setAccounts(mapping.getBindphone());
		// 重置登录密码
		mapping.setLogonpass("e10adc3949ba59abbe56e057f20f883e");
		boolean res = Blade.create(Accountsinfo.class).update(mapping);
		if (res){
			return success(UPDATE_SUCCESS_MSG);
		}
		return error(UPDATE_FAIL_MSG);
	}

	/**
	 * 更新玩家类型
	 */
	@Json
	@RequestMapping("/updateUserType")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult updateUserType() {
		GameScoreInfoRequest request = mapping(PREFIX, GameScoreInfoRequest.class);
		Accountsinfo info = new Accountsinfo();
		Accountsinfo user = Blade.create(Accountsinfo.class).findById(request.getUserid());
		info.setUserid(request.getUserid());
		info.setBeautifulID(user.getBeautifulID());
		info.setBusinessman(request.getMembertypeId());
		boolean temp = Blade.create(Accountsinfo.class).update(info);
		if (temp) {
			int type = (user.getBusinessman() == null) ? 0 : user.getBusinessman();
			String tn = (type == 0) ? "普通用户" : "至尊VIP";
			String ntn = (request.getMembertypeId() == 0) ? "普通用户" : "至尊VIP";
			doLogByMessage(request.getUserid().toString(), UserRightType.UR_CHANGE_MEMBER_TYPE.code(), tn, ntn);
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}

	/*
	 * 设置内部员工
	 */
	@Json
	@RequestMapping("/setInnerMember")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult setInnerMember() {
		String id = getParameter("id");
		Accountsinfo accountsinfo = Blade.create(Accountsinfo.class).findById(id);
		if (accountsinfo == null) {
			return error("账号信息为空!");
		} else {
			accountsinfo.setIsInnerMember(1);
			boolean temp = Blade.create(Accountsinfo.class).update(accountsinfo);
			if (temp) {
				doLog(id, UserRightType.ROOM_UPDATE_INNER_MEMBER.code());
				return success("设置成功!");
			} else {
				return error("设置失败!");
			}
		}
	}

	/**
	 * 充值记录 玩家金币增加
	 * 
	 * @return
	 */
	@Json
	@RequestMapping("/updateRecharge")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult updateRecharge() {
		String userId = getParameter("order.User_Id");
		String webid = getParameter("webid");
		Date now = new Date();

		// 1. 更新GM充值订单表
		Userbyscorebygm info = new Userbyscorebygm();
		// 充值类型
		Userbuyscoretype webInfo = Blade.create(Userbuyscoretype.class).findById(webid);
		// 价格(RMB)
		info.setBuymoney(webInfo.getBuymoney());
		// 获得金币
		info.setScore(webInfo.getScore());
		info.setSendscore(webInfo.getSendscore());
		info.setUserid(Integer.parseInt(userId));
		info.setWritedate(now);
		info.setCzip(getRequest().getRemoteAddr());
		info.setGid(Integer.parseInt(JsonKit.toJson(ShiroKit.getUser().getId())));
		// 新增订单
		boolean infoTemp = Blade.create(Userbyscorebygm.class).save(info);

		// 2. 更新玩家拥有金币
		AaShopPropUserprop scoreInfo = Blade.create(AaShopPropUserprop.class)
				.findFirstBy(" User_Id=" + info.getUserid() + " and Prop_Id=1", null);
		Long oscore = 0L;
		Long oldscore = 0L;
		boolean scoreTemp = false;
		if (scoreInfo != null) { // 如果存在金币信息记录，则更新
			oldscore = scoreInfo.getAmount();
			oscore = scoreInfo.getAmount() + info.getScore() + info.getSendscore();
			scoreTemp = Blade.create(AaShopPropUserprop.class).updateBy(
					" Amount=" + (oscore) + ",StopTime='" + DateKit.getTime() + "'",
					" User_Id=" + info.getUserid() + " and Prop_Id=1", null);
		} else { // 如果不存在金币信息记录，则新增
			scoreInfo = new AaShopPropUserprop();
			scoreInfo.setAmount(Long.getLong(new Integer(info.getScore() + info.getSendscore()).toString()));
			scoreInfo.setIsuse(0);
			scoreInfo.setProp_Id(1);
			scoreInfo.setRemaintime(0);
			scoreInfo.setStarttime(now);
			scoreInfo.setStoptime(now);
			scoreInfo.setUser_Id(info.getUserid());
			scoreTemp = Blade.create(AaShopPropUserprop.class).save(info);
		}

		// 3. 更新金币变动记录表AA_ZZ_Log_PropChange,更新类型为24，web管理员添加金币
		AaZzLogPropchange aaZzLogPropchange = new AaZzLogPropchange();
		aaZzLogPropchange.setUser_Id(Integer.parseInt(userId));
		aaZzLogPropchange.setProp_Id(1);
		aaZzLogPropchange.setPreamount(oldscore);
		Integer changeVal = info.getScore() + info.getSendscore();
		aaZzLogPropchange.setAmount(Long.parseLong(changeVal.toString()));
		aaZzLogPropchange.setAftamount(oscore);
		aaZzLogPropchange.setIsfromsystem(0);
		aaZzLogPropchange.setKindid(0);
		aaZzLogPropchange.setServerid(0);
		aaZzLogPropchange.setTableid(0);
		aaZzLogPropchange.setNo(0);
		aaZzLogPropchange.setChangeType_Id(102);
		aaZzLogPropchange.setLogtime(now);
		aaZzLogPropchange.setRemark("WEB管理员GM充值");
		boolean ltemp = Blade.create(AaZzLogPropchange.class).save(aaZzLogPropchange);

		if (infoTemp && scoreTemp && ltemp) {
			// 3. 记录日志
			doLogByGold(Integer.parseInt(userId), UserRightType.UR_GM_RECHARGE.code(),
					new Integer(info.getScore() + info.getSendscore()).longValue(), oscore, "", "");
			return success("充值成功!");
		} else {
			return error("充值失败!");
		}
	}

	// 锁定用户
	@Json
	@Before(UpdateGoldValidator.class)
	@RequestMapping("/locked")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult locked() {
		GameScoreInfoRequest request = mapping(PREFIX, GameScoreInfoRequest.class);
		Integer id = request.getUserid();
		Accountsinfo user = new Accountsinfo();
		user.setUserid(id);
		user.setLimitlogin(1);
		user.setLimitgive(1);
		user.setLimitsend(1);
		boolean temp = Blade.create(Accountsinfo.class).update(user);

		// 判断当前用户是否在线, 若在线，更新Userchange表
		Gamescorelocker gamescorelocker = Blade.create(Gamescorelocker.class).findFirstBy("UserID=" + id, null);
		// 判断Userchange表中，类型changeType为2的是否有记录，无记录，则新增
		if (gamescorelocker != null) {
			Userchange userchange = Blade.create(Userchange.class).findFirstBy(" userID=" + id + " and changeType=2",
					null);
			if (userchange == null) {
				userchange = new Userchange();
				userchange.setUserid(user.getUserid());
				userchange.setChangetype(2);
				Blade.create(Userchange.class).save(userchange);
			}
			if (!Blade.create(Accountsinfo.class)
					.isExist("select userID FROM [QPTreasureDB].[dbo].[UserChange] where userID=" + user.getUserid()
							+ " and changeType=3", null)) {
				userchange = new Userchange();
				userchange.setUserid(user.getUserid());
				userchange.setChangetype(3);
				Blade.create(Userchange.class).save(userchange);
			}
		}

		if (temp) {
			doLogByGold(id, UserRightType.UR_LOCKED.code(), 0L, 0L, request.getType(), request.getRemark());
			doLogByGold(id, UserRightType.UR_LOCK_TRADE.code(), 0L, 0L, "禁止交易", request.getRemark());
			return success("锁定成功!");
		} else {
			return error("锁定失败!");
		}
	}

	// 玩家搜索批量锁定用户
	@Json
	@RequestMapping("/searchLock")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult searchLock() {
		String ids = getParameter("ids");
		String[] uids = ids.split(",");
		List<Userchange> ucs = new ArrayList<Userchange>();
		Userchange userchange = null;
		for (String id : uids) {
			// 1. 若在线则修改userchange
			if (Blade.create(Gamescorelocker.class)
					.isExist("select UserID FROM [QPTreasureDB].[dbo].[GameScoreLocker] where UserID=" + id, null)) {
				if (!Blade.create(Userchange.class).isExist(
						"select userID FROM [QPTreasureDB].[dbo].[UserChange] where userID=" + id + " and changeType=2",
						null)) {
					userchange = new Userchange();
					userchange.setUserid(Integer.parseInt(id));
					userchange.setChangetype(2);
					ucs.add(userchange);
				}
			}
		}
		// 2. 批量锁定用户
		boolean temp = Blade.create(Accountsinfo.class).updateBy("LimitLogin=1", "UserID in (" + ids + ")", null);
		if (temp) {
			// 3. 批量新增Userchange记录
			if (ucs.size() > 0) {
				Blade.create(Userchange.class).saveBatch(ucs);
			}
			return success("批量锁定成功!");
		} else {
			return error("批量锁定失败!");
		}
	}

	// 升级VIP
	@Json
	@RequestMapping("/upgradeVIPbak")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult upgradeVIPbak() {
		String id = getParameter("id");
		final String cvlid = getParameter("cvlid");
		final String level = getParameter("level");
		final Accountsinfo accountsinfo = Blade.create(Accountsinfo.class).findById(id);
		String ret = "";
		if (accountsinfo != null) {
			ret = Db.executeCall(new OnConnection<String>() {
				@Override
				public String call(Connection conn) throws SQLException {
					CallableStatement cstmt = conn
							.prepareCall("{?=call [QPServerInfoDB].[dbo].[AA_Pro_Add_VIP_Login_IP_Action]( ?,? ) }");
					ResultSet rs = null;
					// 设置输入参数
					cstmt.registerOutParameter(1, Types.INTEGER);
					cstmt.setInt(2, Integer.parseInt(cvlid));
					cstmt.setInt(3, Integer.parseInt(level));

					// 发送参数
					rs = cstmt.executeQuery(); // 注意： 所有调用存储过程的sql语句都是使用executeQuery方法执行！！！

					String ret = "";
					// 遍历结果
					while (rs.next()) {
						ret = rs.getString("ret");
						System.out.println("返回结果" + ret);
					}
					Object o = cstmt.getObject(1);
					if (o != null && JSON.toJSONString(o).equals("0")) {
						System.out.println("返回结果1:" + o);
					}
					ret = o + "," + ret;
					// 其他代码
					return ret;
				}
			});
			String[] result = ret.split(",");
			if (result[0] != null && result[0].equals("0")) {
				return error(result[1]);
			} else {
				doLog(id, UserRightType.ROOM_UPGRADE_VIP.code());
				return success(result[1]);
			}
		} else {
			return error("用户信息不存在");
		}
	}

	// 升级VIP
	@Json
	@RequestMapping("/upgradeVIP")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult upgradeVIP() {
		String id = getParameter("id");
		String level = getParameter("level");
		Accountsinfo a = Blade.create(Accountsinfo.class).findById(id);

		if (a != null) {
			Loginipforvip vip = Blade.create(Loginipforvip.class).findById(a.getAccounts());
			if (vip == null) {
				vip = new Loginipforvip();
				vip.setAccount(a.getAccounts());
				vip.setViplevel(Integer.parseInt(level));
				vip.setDesc("后台管理员设置VIP");
				vip.setAddtime(new Date());
				vip.setVipip(0);
				Blade.create(Loginipforvip.class).save(vip);
			} else {
				vip.setViplevel(Integer.parseInt(level));
				Blade.create(Loginipforvip.class).update(vip);
			}
			doLog(id, UserRightType.ROOM_UPGRADE_VIP.code());
			
			a.setViplevel(Integer.parseInt(level));
			Blade.create(Accountsinfo.class).update(a);
			return success("设置成功");
		} else {
			return error("用户信息不存在");
		}
	}

	// 删除VIP
	@Json
	@RequestMapping("/deleteVIP")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult deleteVIP() {
		String id = getParameter("id");
		final Accountsinfo accountsinfo = Blade.create(Accountsinfo.class).findById(id);
		String ret = "";
		if (accountsinfo != null) {
			ret = Db.executeCall(new OnConnection<String>() {
				@Override
				public String call(Connection conn) throws SQLException {
					CallableStatement cstmt = conn
							.prepareCall("{?=call [QPServerInfoDB].[dbo].[AA_Pro_Clean_VIP_Login_IP]( ? ) }");
					ResultSet rs = null;
					// 设置输入参数
					cstmt.registerOutParameter(1, Types.INTEGER);
					cstmt.setString(2, accountsinfo.getAccounts());

					// 发送参数
					rs = cstmt.executeQuery(); // 注意： 所有调用存储过程的sql语句都是使用executeQuery方法执行！！！
					String ret = "";
					// 遍历结果
					while (rs.next()) {
						ret = rs.getString("ret");
					}
					Object o = cstmt.getObject(1);
					ret = o + "," + ret;
					// 其他代码
					return ret;
				}
			});
			String[] result = ret.split(",");
			if (StrKit.notBlank(result[1])) {
				return error(result[1]);
			} else {
				doLog(id, UserRightType.ROOM_DELETE_VIP.code());
				return success("删除成功");
			}
		} else {
			return error("用户信息不存在");
		}
	}

	// 拒绝VIP
	@Json
	@RequestMapping("/refuseVIP")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult refuseVIP() {
		String id = getParameter("id");
		final Accountsinfo accountsinfo = Blade.create(Accountsinfo.class).findById(id);
		String ret = "";
		if (accountsinfo != null) {
			Changeviplevel changeviplevel = Blade.create(Changeviplevel.class)
					.findFirstBy("account='" + accountsinfo.getAccounts() + "'", null);
			if (changeviplevel != null) {
				changeviplevel.setIsaction(2);
				Blade.create(Changeviplevel.class).update(changeviplevel);
			}
			ret = Db.executeCall(new OnConnection<String>() {
				@Override
				public String call(Connection conn) throws SQLException {
					CallableStatement cstmt = conn
							.prepareCall("{?=call [QPServerInfoDB].[dbo].[AA_Pro_Clean_VIP_Login_IP]( ? ) }");
					ResultSet rs = null;
					// 设置输入参数
					cstmt.registerOutParameter(1, Types.INTEGER);
					cstmt.setString(2, accountsinfo.getAccounts());

					// 发送参数
					rs = cstmt.executeQuery(); // 注意： 所有调用存储过程的sql语句都是使用executeQuery方法执行！！！
					String ret = "";
					// 遍历结果
					while (rs.next()) {
						ret = rs.getString("ret");
						System.out.println("返回结果" + ret);
					}
					Object o = cstmt.getObject(1);
					ret = o + "," + ret;
					// 其他代码
					return ret;
				}
			});
			String[] result = ret.split(",");
			if (StrKit.notBlank(result[1])) {
				return error(result[1]);
			} else {
				doLog(id, UserRightType.ROOM_REFUSE_VIP.code());
				return success("拒绝成功");
			}
		} else {
			return error("用户信息不存在");
		}
	}

	// 玩家搜索批量锁定用户交易
	@Json
	@RequestMapping("/searchLockTrade")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult searchLockTrade() {
		String ids = getParameter("ids");
		String[] uids = ids.split(",");
		List<Userchange> ucs = new ArrayList<Userchange>();
		Userchange userchange = null;
		for (String id : uids) {
			// 1. 若在线则修改userchange
			if (Blade.create(Gamescorelocker.class)
					.isExist("select UserID FROM [QPTreasureDB].[dbo].[GameScoreLocker] where UserID=" + id, null)) {
				if (!Blade.create(Userchange.class).isExist(
						"select userID FROM [QPTreasureDB].[dbo].[UserChange] where userID=" + id + " and changeType=3",
						null)) {
					userchange = new Userchange();
					userchange.setUserid(Integer.parseInt(id));
					userchange.setChangetype(3);
					ucs.add(userchange);
				}
			}
		}
		// 2. 批量禁止交易
		boolean temp = Blade.create(Accountsinfo.class).updateBy("limitGive=1", "UserID in (" + ids + ")", null);
		if (temp) {
			// 3. 批量新增Userchange记录
			if (ucs.size() > 0) {
				Blade.create(Userchange.class).saveBatch(ucs);
			}
			return success("批量锁定交易成功!");
		} else {
			return error("批量锁定交易失败!");
		}
	}

	// 关联锁定用户
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Json
	@Before(UpdateGoldValidator.class)
	@RequestMapping("/relationlocked")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult relationlocked() {
		GameScoreInfoRequest request = mapping(PREFIX, GameScoreInfoRequest.class);
		Accountsinfo info = Blade.create(Accountsinfo.class).findById(request.getUserid());
		Map map = new HashMap();
		if (request.getRelationType().equals("LastLogonIP")) {
			map.put("ip", info.getLastlogonip());
		} else {
			map.put("sendNum", info.getSendnum());
		}
		List<Map> infoList = commonService.getInfoList("player_search.new_list", map);
		Integer[] ids = new Integer[infoList.size()];
		int i = 0;
		List<Userchange> ucs = new ArrayList<Userchange>();
		Userchange userchange = null;
		for (Map user : infoList) {
			ids[i] = Integer.parseInt(JSON.toJSONString(user.get("UserID")));

			// 判断当前用户是否在线, 若在线，更新Userchange表
			Gamescorelocker gamescorelocker = Blade.create(Gamescorelocker.class).findFirstBy("UserID=" + ids[i], null);
			if (gamescorelocker != null) {
				// 判断Userchange表中，类型changeType为2的是否有记录，无记录，则新增
				userchange = Blade.create(Userchange.class).findFirstBy(" userID=" + ids[i] + " and changeType=2",
						null);
				if (userchange == null) {
					userchange = new Userchange();
					userchange.setUserid(ids[i]);
					userchange.setChangetype(2);
					// Blade.create(Userchange.class).save(userchange);
					ucs.add(userchange);
				}
			}
			i++;
		}
		boolean temp = Blade.create(Accountsinfo.class).updateBy("LimitLogin=1",
				"UserID in (" + CollectionKit.join(ids, ",") + ")", null);
		if (temp) {
			// 批量新增Userchange记录
			if (ucs.size() > 0) {
				Blade.create(Userchange.class).saveBatch(ucs);
			}
			for (Integer id : ids) {
				doLogByGold(id, UserRightType.ROOM_RELATION_LOCK.code(), 0L, 0L, request.getType(),
						request.getRemark());
			}
			return success("关联锁定成功!");
		} else {
			return error("关联锁定失败!");
		}
	}

	// 关联禁止交易
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Json
	@Before(UpdateGoldValidator.class)
	@RequestMapping("/relationlocktrade")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult relationlocktrade() {
		GameScoreInfoRequest request = mapping(PREFIX, GameScoreInfoRequest.class);
		Accountsinfo info = Blade.create(Accountsinfo.class).findById(request.getUserid());
		Map map = new HashMap();
		if (request.getRelationType().equals("LastLogonIP")) {
			map.put("ip", info.getLastlogonip());
		} else {
			map.put("sendNum", info.getSendnum());
		}
		// 1. 获取所有关联用户
		List<Map> infoList = commonService.getInfoList("player_search.new_list", map);
		List<Accountsinfo> users = new ArrayList<Accountsinfo>();
		Accountsinfo user = null;
		Integer[] ids = new Integer[infoList.size()];
		int i = 0;
		List<Userchange> ucs = new ArrayList<Userchange>();
		Userchange userchange = null;
		for (Map u : infoList) {
			ids[i] = Integer.parseInt(JSON.toJSONString(u.get("UserID")));
			user = Blade.create(Accountsinfo.class).findById(JSON.toJSONString(u.get("UserID")));
			user.setLimitgive(1);
			user.setLimitsend(1);
			users.add(user);
			// 2. 判断当前用户是否在线, 若在线，更新Userchange表
			if (Blade.create(Accountsinfo.class).isExist(
					"select UserID FROM [QPTreasureDB].[dbo].[GameScoreLocker] where UserID=" + user.getUserid(),
					null)) {
				if (!Blade.create(Accountsinfo.class)
						.isExist("select userID FROM [QPTreasureDB].[dbo].[UserChange] where userID=" + user.getUserid()
								+ " and changeType=3", null)) {
					userchange = new Userchange();
					userchange.setUserid(user.getUserid());
					userchange.setChangetype(3);
					ucs.add(userchange);
				}
			}
			i++;
		}
		// 3. 批量禁止交易
		boolean temp = Blade.create(Accountsinfo.class).updateBy("limitGive=1",
				"UserID in (" + CollectionKit.join(ids, ",") + ")", null);
		if (temp) {
			// 4. 批量新增Userchange记录
			if (ucs.size() > 0) {
				Blade.create(Userchange.class).saveBatch(ucs);
			}
			for (Accountsinfo u : users) {
				doLogByGold(u.getUserid(), UserRightType.ROOM_RELATION_LOCK_TRADE.code(), 0L, 0L, request.getType(),
						request.getRemark());
			}
			return success("关联禁止交易成功!");
		} else {
			return error("关联禁止交易失败!");
		}
	}

	// 解锁用户
	@Json
	@RequestMapping("/unlocked")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult unlocked() {
		String id = getParameter("id");
		Accountsinfo user = new Accountsinfo();
		user.setUserid(Integer.parseInt(id));
		user.setLimitlogin(0);
		boolean temp = Blade.create(Accountsinfo.class).update(user);
		if (temp) {
			// 判断Userchange表中，类型changeType为2的是否有记录，有记录，则删除
			Userchange userchange = Blade.create(Userchange.class).findFirstBy(" userID=" + id + " and changeType=2",
					null);
			if (userchange != null) {
				Blade.create(Userchange.class).deleteBy(" userID=" + id + " and changeType=2", null);
			}
			doLog(id, UserRightType.UR_UNLOCKED.code());
			return success("解锁成功!");
		} else {
			return error("解锁失败!");
		}
	}

	// 禁言用户
	@Json
	@RequestMapping("/lockspeak")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult lockspeak() {
		return success("禁言成功!");
	}

	// 禁止上榜
	@Json
	@RequestMapping("/locktop")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult locktop() {
		String sid = getParameter("id");
		Integer id = Integer.parseInt(sid);
		Accountsinfo user = new Accountsinfo();
		user.setUserid(id);
		user.setLimitrank(1);
		boolean temp = Blade.create(Accountsinfo.class).update(user);

		if (temp) {
			doLog(sid, UserRightType.UR_ALLOW_TOP.code());
			return success("禁止上榜成功!");
		} else {
			return error("禁止上榜失败!");
		}
	}

	// 允许上榜
	@Json
	@RequestMapping("/allowtop")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult allowtop() {
		String id = getParameter("id");
		Accountsinfo user = new Accountsinfo();
		user.setUserid(Integer.parseInt(id));
		user.setLimitrank(0);
		boolean temp = Blade.create(Accountsinfo.class).update(user);
		if (temp) {
			doLog(id, UserRightType.UR_ALLOW_TOP.code());
			return success("允许上榜成功!");
		} else {
			return error("允许上榜失败!");
		}
	}

	// 禁止交易
	@Json
	@Before(UpdateGoldValidator.class)
	@RequestMapping("/locktrade")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult locktrade() {
		GameScoreInfoRequest request = mapping(PREFIX, GameScoreInfoRequest.class);
		Integer id = request.getUserid();
		Accountsinfo user = new Accountsinfo();
		user.setUserid(id);
		user.setLimitgive(1);
		user.setLimitsend(1);
		boolean temp = Blade.create(Accountsinfo.class).update(user);

		// 判断当前用户是否在线, 若在线，更新Userchange表
		// 判断Userchange表中，类型changeType为2的是否有记录，无记录，则新增
		Userchange userchange = null;
		if (Blade.create(Accountsinfo.class).isExist(
				"select UserID FROM [QPTreasureDB].[dbo].[GameScoreLocker] where UserID=" + user.getUserid(), null)) {
			if (!Blade.create(Accountsinfo.class)
					.isExist("select userID FROM [QPTreasureDB].[dbo].[UserChange] where userID=" + user.getUserid()
							+ " and changeType=3", null)) {
				userchange = new Userchange();
				userchange.setUserid(user.getUserid());
				userchange.setChangetype(3);
				Blade.create(Userchange.class).save(userchange);
			}
		}
		if (temp) {
			doLogByGold(id, UserRightType.UR_LOCK_TRADE.code(), 0L, 0L, request.getType(), request.getRemark());
			return success("禁止交易成功!");
		} else {
			return error("禁止交易失败!");
		}
	}

	// 允许交易
	@Json
	@RequestMapping("/allowtrade")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult allowtrade() {
		String id = getParameter("id");
		Accountsinfo user = new Accountsinfo();
		user.setUserid(Integer.parseInt(id));
		user.setLimitgive(0);
		user.setLimitsend(0);
		boolean temp = Blade.create(Accountsinfo.class).update(user);
		if (temp) {
			// 判断Userchange表中，类型changeType为3的是否有记录，有记录，则删除
			Userchange userchange = Blade.create(Userchange.class).findFirstBy(" userID=" + id + " and changeType=3",
					null);
			if (userchange != null) {
				Blade.create(Userchange.class).deleteBy(" userID=" + id + " and changeType=3", null);
			}
			doLog(id, UserRightType.UR_ALLOW_TRADE.code());
			return success("允许交易成功!");
		} else {
			return error("允许交易失败!");
		}
	}

	// 禁止发送小喇叭
	@Json
	@RequestMapping("/lockTrumpet")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult lockTrumpet() {
		String id = getParameter("id");
		Accountsinfo user = new Accountsinfo();
		Accountsinfo olduser = Blade.create(Accountsinfo.class).findById(id);
		user.setUserid(Integer.parseInt(id));
		user.setUserright(UserRightUtil.unlockTrumpet(olduser.getUserright()));
		boolean temp = Blade.create(Accountsinfo.class).update(user);
		if (temp) {
			doLog(id, UserRightType.UR_LOCK_TRADE.code());
			return success("禁止发送小喇叭成功!");
		} else {
			return error("禁止发送小喇叭失败!");
		}
	}

	// 允许发送小喇叭
	@Json
	@RequestMapping("/allowTrumpet")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult allowTrumpet() {
		String id = getParameter("id");
		Accountsinfo user = new Accountsinfo();
		Accountsinfo olduser = Blade.create(Accountsinfo.class).findById(id);
		user.setUserid(Integer.parseInt(id));
		user.setUserright(UserRightUtil.lockTrumpet(olduser.getUserright()));
		boolean temp = Blade.create(Accountsinfo.class).update(user);
		if (temp) {
			doLog(id, UserRightType.UR_ALLOW_TRADE.code());
			return success("允许发送小喇叭成功!");
		} else {
			return error("允许发送小喇叭失败!");
		}
	}

	// 退回礼物
	@Json
	@RequestMapping("/refund")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult refund() {
		String id = getParameter("ids");
		AaGiverecord giverecord = Blade.create(AaGiverecord.class).findById(id);

		// 1. 赠送金币数返还给赠送者
		AaShopPropUserprop sourceUser = Blade.create(AaShopPropUserprop.class)
				.findFirstBy(" User_Id=" + giverecord.getFrom_user() + " and Prop_Id=1", null);
		if (sourceUser == null) {
			return error("礼物退回失败!<br/>赠送者资产记录为空。");
		}

		Long bscore = sourceUser.getAmount();
		sourceUser.setAmount(sourceUser.getAmount() + giverecord.getProp_count());
		boolean update = Blade.create(AaShopPropUserprop.class).updateBy(
				" Amount=" + sourceUser.getAmount() + ",StopTime='" + DateKit.getTime() + "'",
				" User_Id=" + giverecord.getFrom_user() + " and Prop_Id=1", null);

		// 赠送者和接受者信息
		Accountsinfo fromUser = Blade.create(Accountsinfo.class).findById(giverecord.getFrom_user());
		Accountsinfo toUser = Blade.create(Accountsinfo.class).findById(giverecord.getTo_user());
		boolean isUpdate = false;
		if ((((fromUser.getBusinessman() != null && fromUser.getBusinessman() == 1)
				&& (toUser.getBusinessman() == null || toUser.getBusinessman() == 0)))
				|| (((fromUser.getBusinessman() == null || fromUser.getBusinessman() == 0)
						&& (toUser.getBusinessman() != null && toUser.getBusinessman() == 1)))) {
			isUpdate = true;
		}
		Gamescoreinfo sourceScoreinfo = Blade.create(Gamescoreinfo.class)
				.findFirstBy(" UserID=" + giverecord.getFrom_user(), null);
		Gamescoreinfo targetScoreinfo = Blade.create(Gamescoreinfo.class)
				.findFirstBy(" UserID=" + giverecord.getTo_user(), null);
		// 1.1. 赠送者卖分减少
		if (sourceScoreinfo != null && isUpdate) {
			Long send = sourceScoreinfo.getOut_BussniessCount() - giverecord.getProp_count();
			if (send < 0) {
				send = 0L;
			}
			Blade.create(Gamescoreinfo.class).updateBy(" out_BussniessCount=" + (send),
					" UserID=" + giverecord.getFrom_user(), null);
		}
		// 1.2. 接受者买分减少
		if (targetScoreinfo != null && isUpdate) {
			Long rcv = targetScoreinfo.getJs_BussniessCount() - giverecord.getProp_count();
			if (rcv < 0) {
				rcv = 0L;
			}
			Blade.create(Gamescoreinfo.class).updateBy(" js_BussniessCount=" + (rcv),
					" UserID=" + giverecord.getTo_user(), null);
		}

		// 2. 删除赠送记录
		boolean temp = Blade.create(AaGiverecord.class).deleteByIds(id) > 0;
		// 3. 更新金币变动记录表AA_ZZ_Log_PropChange,更新类型为2，web管理员添加金币
		AaZzLogPropchange aaZzLogPropchange = new AaZzLogPropchange();
		aaZzLogPropchange.setUser_Id(giverecord.getFrom_user());
		aaZzLogPropchange.setProp_Id(1);
		aaZzLogPropchange.setPreamount(bscore);
		aaZzLogPropchange.setAmount(giverecord.getProp_count());
		aaZzLogPropchange.setAftamount(sourceUser.getAmount());
		aaZzLogPropchange.setIsfromsystem(0);
		aaZzLogPropchange.setKindid(0);
		aaZzLogPropchange.setServerid(0);
		aaZzLogPropchange.setTableid(0);
		aaZzLogPropchange.setNo(0);
		aaZzLogPropchange.setChangeType_Id(35);
		aaZzLogPropchange.setLogtime(new Date());
		aaZzLogPropchange.setRemark("web管理员退回礼物");
		boolean ltemp = Blade.create(AaZzLogPropchange.class).save(aaZzLogPropchange);

		// 4. 更新交易列表，只有交易双方有一方是至尊VIP，一方是普通用户才会更改这个表
		// 判断更新条件
		// Accountsinfo fromUser =
		// Blade.create(Accountsinfo.class).findById(giverecord.getFrom_user());
		// Accountsinfo toUser =
		// Blade.create(Accountsinfo.class).findById(giverecord.getTo_user());
		// 4.1. 赠送者是至尊VIP，接受者是普通玩家
		if (((fromUser.getBusinessman() != null && fromUser.getBusinessman() == 1)
				&& (toUser.getBusinessman() == null || toUser.getBusinessman() == 0))) {
			// 更新赠送者交易赠送金币
			UserSendgoosrecord sendgoosrecord = Blade.create(UserSendgoosrecord.class).findFirstBy(
					"UserID=" + giverecord.getFrom_user() + " and Vip_userid=0 and DATEDIFF(DAY,RecordDate,'"
							+ DateKit.getDay(giverecord.getGivetime()) + "')=0",
					null);
			if (sendgoosrecord != null) {
				sendgoosrecord.setSellscore(sendgoosrecord.getSellscore() - giverecord.getProp_count());
				sendgoosrecord.setSellcount(sendgoosrecord.getSellcount() - 1);
				Blade.create(UserSendgoosrecord.class).update(sendgoosrecord);
			}
			// 更新接受者接收金币
			sendgoosrecord = Blade.create(UserSendgoosrecord.class)
					.findFirstBy("UserID=" + giverecord.getTo_user() + " and DATEDIFF(DAY,RecordDate,'"
							+ DateKit.getDay(giverecord.getGivetime()) + "')=0 and Vip_userid="
							+ giverecord.getFrom_user(), null);
			if (sendgoosrecord != null) {
				sendgoosrecord.setBuyscore(sendgoosrecord.getBuyscore() - giverecord.getProp_count());
				sendgoosrecord.setBuycount(sendgoosrecord.getBuycount() - 1);
				Blade.create(UserSendgoosrecord.class).update(sendgoosrecord);
			}
		}
		// 4.2. 接受者是至尊VIP，赠送者是普通玩家
		else if (((fromUser.getBusinessman() == null || fromUser.getBusinessman() == 0)
				&& (toUser.getBusinessman() != null && toUser.getBusinessman() == 1))) {
			// 更新赠送者交易赠送金币
			UserSendgoosrecord sendgoosrecord = Blade.create(UserSendgoosrecord.class)
					.findFirstBy("UserID=" + giverecord.getFrom_user() + " and DATEDIFF(DAY,RecordDate,'"
							+ DateKit.getDay(giverecord.getGivetime()) + "')=0 and Vip_userid="
							+ giverecord.getTo_user(), null);
			if (sendgoosrecord != null) {
				sendgoosrecord.setSellscore(sendgoosrecord.getSellscore() - giverecord.getProp_count());
				sendgoosrecord.setSellcount(sendgoosrecord.getSellcount() - 1);
				Blade.create(UserSendgoosrecord.class).update(sendgoosrecord);
			}
			// 更新接受者接收金币
			sendgoosrecord = Blade.create(UserSendgoosrecord.class)
					.findFirstBy("UserID=" + giverecord.getTo_user() + " and Vip_userid=0 and DATEDIFF(DAY,RecordDate,'"
							+ DateKit.getDay(giverecord.getGivetime()) + "')=0", null);
			if (sendgoosrecord != null) {
				sendgoosrecord.setBuyscore(sendgoosrecord.getBuyscore() - giverecord.getProp_count());
				sendgoosrecord.setBuycount(sendgoosrecord.getBuycount() - 1);
				Blade.create(UserSendgoosrecord.class).update(sendgoosrecord);
			}
		}

		// 5. 新增AAChangeRecordData记录
		AAChangeRecordData recordData = new AAChangeRecordData();
		recordData.setId(giverecord.getId());
		recordData.setAreaId(0);
		recordData.setGiveOrRev(2);
		Blade.create(AAChangeRecordData.class).save(recordData);

		if (temp && update && ltemp) {
			// 记录操作日志
			Accountsinfo su = Blade.create(Accountsinfo.class).findById(giverecord.getFrom_user());
			Accountsinfo tu = Blade.create(Accountsinfo.class).findById(giverecord.getTo_user());
			String sourceNickName = su.getNickname();
			if (StrKit.notBlank(su.getTipsname())) {
				sourceNickName = sourceNickName + "[" + su.getTipsname() + "]";
			}
			String targetNickName = tu.getNickname();
			if (StrKit.notBlank(tu.getTipsname())) {
				targetNickName = targetNickName + "[" + tu.getTipsname() + "]";
			}
			doLogByBackGold(giverecord.getFrom_user(), sourceNickName, giverecord.getTo_user(), targetNickName,
					giverecord.getGivetime(), UserRightType.UR_GIFT_REFUND.code(), giverecord.getProp_count(), bscore);
			return success("礼物退回成功!");
		} else {
			return error("礼物退回失败!");
		}
	}

	public static Map<String, String> getRole(final int userId) {

		Map<String, Object> userRole = CacheKit.get(SYS_CACHE, ROLE_EXT + userId, new ILoader() {
			@Override
			public Object load() {
				return Db.selectOne("select * from BLADE_ROLE_EXT where USERID = #{userId}",
						CMap.init().set("userId", userId));
			}
		});

		String roleIn = "0";
		String roleOut = "0";
		if (!Func.isEmpty(userRole)) {
			CMap cmap = CMap.parse(userRole);
			roleIn = cmap.getStr("ROLEIN");
			roleOut = cmap.getStr("ROLEOUT");
		}
		Map<String, String> map = new HashMap<>();
		map.put("roleIn", roleIn);
		map.put("roleOut", roleOut);
		return map;
	}
}