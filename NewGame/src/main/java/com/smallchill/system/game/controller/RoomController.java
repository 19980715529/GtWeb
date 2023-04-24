package com.smallchill.system.game.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.CharsetKit;
import com.smallchill.core.toolbox.kit.DateKit;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.core.toolbox.kit.URLKit;
import com.smallchill.game.model.BloodPondControlRecord;
import com.smallchill.game.model.BloodPondFinishedRecord;
import com.smallchill.game.model.PlayerOperationLog;
import com.smallchill.game.model.UserRightType;
import com.smallchill.game.newmodel.AaBloodchangeplan;
import com.smallchill.game.newmodel.Gameroomitem;
import com.smallchill.game.newmodel.LBloodpoolconfig;
import com.smallchill.game.newmodel.Roomdesc;
import com.smallchill.game.service.CommonService;
import com.smallchill.system.game.model.BloodPondChangeRecord;
import com.smallchill.system.model.request.BloodpondconfigRequest;

@Controller
@RequestMapping("/room")
public class RoomController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/system/gamesystem/room/";
	private static String CODE = "room";
	private static String LIST_SOURCE = "room.new_list";
	
	@Autowired
	private CommonService commonService;
	@DoControllerLog(name="进入房间配置列表页面")
	@RequestMapping("/")
	//@Permission({ ADMINISTRATOR, ADMIN })
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		Roomdesc roomdesc= Blade.create(Roomdesc.class).findFirstBy("", null);
		if(roomdesc == null) {
			mm.put("Description", "");
		} else {
			mm.put("Description", roomdesc.getDescription());
		}
		return BASE_PATH + "room.html";
	}
	
	//	@SystemControllerLog(description = "获取房间配置列表")
	@Json
	@RequestMapping(KEY_LIST)
	//@Permission({ ADMINISTRATOR, ADMIN })
	public Object list() {
		Object gird = paginateBySelf(LIST_SOURCE);
		return gird;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@DoControllerLog(name="进入修改血池平衡值页面")
	@RequestMapping(KEY_EDIT + "/{id}")
	@Permission({ ADMINISTRATOR, ADMIN })
	public String edit(@PathVariable Integer id, ModelMap mm) {
		Map paras = new HashMap();
		paras.put("ServerID", id);
		Map room = commonService.getInfoByOne(LIST_SOURCE, paras);
		mm.put("room", room);
		mm.put("code", CODE);
		return BASE_PATH + "room_edit.html";
	}
	@DoControllerLog(name="修改血池平衡值")
	@Json
	@RequestMapping("/updateblood")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult updateblood() {
		LBloodpoolconfig gameroomitem = mapping("blood", LBloodpoolconfig.class);
		boolean temp = Blade.create(LBloodpoolconfig.class).updateBy(" highLine="+gameroomitem.getHighline(), " ServerID="+gameroomitem.getServerid(), null);
		if (temp) {
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}

	@DoControllerLog(name="修改当前血池银行剩余血池")
	@Json
	@RequestMapping("/updateremainblood")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult updateremainblood() {
		Gameroomitem gameroomitem = mapping("blood", Gameroomitem.class);
		
		// 血池银行信息
		Gameroomitem bloodbank = Blade.create(Gameroomitem.class).findFirstBy(" where ServerID="+gameroomitem.getServerid()+" and KindID=0", null);
		// 房间剩余血池信息
		//Gameroomitem newitem = Blade.create(Gameroomitem.class).findFirstBy(" where ServerID="+gameroomitem.getServerid()+" and KindID="+gameroomitem.getKindid(), null);
		
		// 更改剩余血池
		//newitem.setBloodscore(gameroomitem.getBloodscore()+newitem.getBloodscore());

		// 更改血池银行-当前血池增加，血池银行增加；当前血池减少，血池银行减少
		bloodbank.setBloodscore(bloodbank.getBloodscore()+gameroomitem.getBloodscore());
		
		//boolean temp = Blade.create(Gameroomitem.class).updateBy(" BloodScore="+newitem.getBloodscore(), " KindID="+newitem.getKindid()+" and ServerID="+newitem.getServerid(), null);
		boolean banktemp = Blade.create(Gameroomitem.class).updateBy(" BloodScore="+bloodbank.getBloodscore(), " KindID=0 and ServerID="+bloodbank.getServerid(), null);
		if (banktemp) {
			// 剩余血池更改数据填充
			BloodPondChangeRecord record = new BloodPondChangeRecord();
			record.setType(0);
			record.setBloodpondid(gameroomitem.getServerid());
			record.setBloodpondscore(gameroomitem.getBloodscore());
			record.setInserttime(new Date());
			record.setUpdatetime(new Date());
			record.setBloodpondname(bloodbank.getRoomname());
			record.setBloodpondbeforescore(bloodbank.getBloodscore()-gameroomitem.getBloodscore());
			record.setBloodpondafterscore(bloodbank.getBloodscore());
			record.setRemark("【" + ShiroKit.getUser().getName() + "】更改了血池银行剩余血池，更改数值：" + gameroomitem.getBloodscore());
			// 剩余血池更改记录表
			Blade.create(BloodPondChangeRecord.class).save(record);
			
			// 添加操作日志
			doLog(UserRightType.ROOM_UPDATE_REMAIN_BLOOD.code(),gameroomitem.getBloodscore(),(bloodbank.getBloodscore() - gameroomitem.getBloodscore()),bloodbank.getBloodscore(),("【" + ShiroKit.getUser().getName() + "】更改了血池银行剩余血池，更改数值：" + gameroomitem.getBloodscore()));
			
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}

	//@SystemControllerLog(description = "更新房间配置信息")
	@Json
	@RequestMapping(KEY_UPDATE)
	public AjaxResult update() {
		return success(UPDATE_SUCCESS_MSG);
	}
	@DoControllerLog(name="进入修改剩余血池页面")
	@RequestMapping(KEY_BLOOD_POOL + "/{id}")
	@Permission({ ADMINISTRATOR, ADMIN })
	public String bloodPool(@PathVariable Integer id, ModelMap mm) {
		Gameroomitem room = Blade.create(Gameroomitem.class).findFirstBy(" where ServerID="+id, null);
		mm.put("room", room);
		mm.put("code", CODE);
		return BASE_PATH + "room_bloodpool.html";
	}

	// 新增血池控制跳转页面
	@DoControllerLog(name="进入新增血池控制页面")
	@RequestMapping("/addbloodcontrol" + "/{id}")
	@Permission({ ADMINISTRATOR, ADMIN })
	public String addbloodcontrol(@PathVariable Integer id,ModelMap mm) {
		Gameroomitem room = Blade.create(Gameroomitem.class).findFirstBy(" where ServerID="+id, null);
		mm.put("room", room);
		mm.put("code", CODE);
		return BASE_PATH + "room_addbloodcontrol.html";
	}

	@DoControllerLog(name="新增血池控制")
	@Json
	@RequestMapping("/saveblood")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult saveblood() {
		BloodpondconfigRequest bloodpondconfig = mapping("blood", BloodpondconfigRequest.class);
		if(bloodpondconfig.getBegintime() == null ) {
			bloodpondconfig.setBegintime(new Date());
		}
		// 1. 新增血池控制计划
		AaBloodchangeplan bloodchangeplan = new AaBloodchangeplan();
		if(bloodpondconfig.getBloodchange()==null) {
			bloodchangeplan.setBloodchange(0);
		} else {
			bloodchangeplan.setBloodchange(bloodpondconfig.getBloodchange());
		}
		bloodchangeplan.setIsrun(0);
		bloodchangeplan.setServerid(bloodpondconfig.getServerid());
		bloodchangeplan.setRuntime(bloodpondconfig.getBegintime());
		boolean controlTemp = Blade.create(AaBloodchangeplan.class).save(bloodchangeplan);
		
		Gameroomitem room = Blade.create(Gameroomitem.class).findFirstBy(" where ServerID="+bloodpondconfig.getServerid(), null);
		/*
		// 2. 更新房间当前剩余血池
		room.setBloodscore(room.getBloodscore()+bloodpondconfig.getBloodchange());
		boolean rtemp = Blade.create(Gameroomitem.class).updateBy("BloodScore="+(room.getBloodscore()), " ServerID="+bloodpondconfig.getServerid(), null);

		// 3. 更新房间吃吐状态。只有捕鱼需要判断，在平衡线上是吐，下是吃，包括李逵劈鱼-4，金银岛捕鱼-12，金蟾捕鱼-13，财神捕鱼-22
		// 捕鱼，当前剩余血池在平衡线上是吐，下是吃，相等时随意
		Integer[] ids = {4,12,13,22};
		boolean temp = true;
		LBloodpoolconfig config = Blade.create(LBloodpoolconfig.class).findFirstBy(" where ServerID="+bloodpondconfig.getServerid(), null);
		if(CollectionKit.contains(ids,bloodpondconfig.getServerid()) && room.getBloodscore()>config.getHighline()) {
			temp = Blade.create(LBloodpoolconfig.class).updateBy(" status=1", " ServerID="+bloodpondconfig.getServerid(), null);
		} else{
			temp = Blade.create(LBloodpoolconfig.class).updateBy(" status=0", " ServerID="+bloodpondconfig.getServerid(), null);
		}
		
		// 4. 更新血池银行
		Gameroomitem bank = Blade.create(Gameroomitem.class).findFirstBy(" where ServerID=6001 and KindID=0", null);
		bank.setBloodscore(bank.getBloodscore()-bloodpondconfig.getBloodchange());
		boolean btemp = Blade.create(Gameroomitem.class).updateBy("BloodScore="+(bank.getBloodscore()), " ServerID=6001 and KindID=0", null);
		 */
		if (controlTemp) {
			// 5. 添加操作记录
			doLog(UserRightType.ROOM_ADD_CONTROL.code(),(Long.parseLong(bloodpondconfig.getBloodchange().toString())),0L,Long.parseLong(bloodpondconfig.getBloodchange().toString()),"<span class=\"text-red\">【" + ShiroKit.getUser().getName() + "】</span>新增了血池控制,控制房间：【"+room.getRoomname()+"】,血池控制数值：<span class=\"text-red\">【" + (bloodpondconfig.getBloodchange()) + "】</span><br/>执行时间：" + DateKit.getTime(bloodpondconfig.getBegintime()));
			
			return success(SAVE_SUCCESS_MSG);
		} else {
			return error(SAVE_FAIL_MSG);
		}
	}
	
	@DoControllerLog(name="进入血池控制列表页面")
	@RequestMapping(KEY_BLOOD_POOL_CONTROL_LIST + "/{id}")
	//@Permission({ ADMINISTRATOR, ADMIN })
	public String controllist(@PathVariable Integer id, ModelMap mm) {
		mm.put("code", CODE);
		mm.put("ServerID", id);
		return BASE_PATH + "room_controllist.html";
	}
	@Json
	@RequestMapping("/bclist")
	//@Permission({ ADMINISTRATOR, ADMIN })
	public Object bclist() {
		Object gird = paginateBySelf("room.new_control_list");
		return gird;
	}

	// 历史吞吐率页面导航
	@DoControllerLog(name="进入历史吞吐率列表页面")
	@RequestMapping(KEY_HISTORY_CONTROL_LIST + "/{id}")
	//@Permission({ ADMINISTRATOR, ADMIN })
	public String hthroughput(@PathVariable Integer id, ModelMap mm) {
		mm.put("code", CODE);
		mm.put("ServerId", id);
		return BASE_PATH + "room_hthroughput.html";
	}
	//	@SystemControllerLog(description = "获取历史吞吐率列表")
	@Json
	@RequestMapping(KEY_HISTORY_LIST)
	//@Permission({ ADMINISTRATOR, ADMIN })
	public Object hlist() {
		Object gird = paginateBySelf("room.new_history_list");
		return gird;
	}

	@DoControllerLog(name="进入血池变化页面")
	@RequestMapping(KEY_BLOOD_POOL_CHANGE + "/{id}")
	//@Permission({ ADMINISTRATOR, ADMIN })
	public String bloodpoolchange(@PathVariable Integer id, ModelMap mm) {
		mm.put("code", CODE);
		mm.put("ServerID", id);
		Gameroomitem room = Blade.create(Gameroomitem.class).findFirstBy(" where ServerID="+id, null);
		mm.put("KindID", room.getKindid());
		return BASE_PATH + "room_bloodpoolchange.html";
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Json
	@RequestMapping("/bcData")
	public AjaxResult bcData() {
		String parameter = getParameter("where");
		if (parameter.contains("%")) {
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		LOGGER.info("房间配置->血池变化->获取图表数据查询参数：-------------------");
		LOGGER.info(parameter);
		Map params = JSON.parseObject(parameter, Map.class);
		params.put("orderBy", "WriteDate");
		Object data = commonService.getInfoList("record_bloodcontrol.new_list",params);
		return json(data);
	}
	@Json
	@RequestMapping("/bcDataList")
	//@Permission({ ADMINISTRATOR, ADMIN })
	public Object bcDataList() {
		Object gird = paginateBySelf("record_bloodcontrol.new_list");
		return gird;
	}
	
	@SuppressWarnings("rawtypes")
	@Json
	@RequestMapping("/getRoomInfo")
	public AjaxResult getRoomInfo(@RequestParam(name="KindID", required=false) Integer KindID) {
		Map paras = new HashMap();
		paras.put("KindID", KindID);
		List<Map> list = commonService.getInfoList("room.all_list",paras);
		return json(list);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Json
	@RequestMapping("/getViewInfo")
	public AjaxResult getViewInfo(ModelMap mm) {
		final String id = getParameter("id");
		Map map = new HashMap();
		map.put("ServerID", id);
		Map bloodpondconfig = commonService.getInfoByOne(LIST_SOURCE, map);
		return json(bloodpondconfig);
	}
	
	@DoControllerLog(name="删除血池控制")
	@Json
	@RequestMapping("/delBloodControl")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult delBloodControl() {
		String id = getParameter("id");
		boolean temp = Blade.create(AaBloodchangeplan.class).deleteByIds(id) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
	@DoControllerLog(name="结束血池控制")
	@Json
	@RequestMapping("/endBloodControl")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult endBloodControl() {
		String id = getParameter("id");
		BloodPondControlRecord controlRecord = Blade.create(BloodPondControlRecord.class).findById(id);
		// 插入血池控制结束记录
		BloodPondFinishedRecord finishedRecord = new BloodPondFinishedRecord();
		finishedRecord.setBegintime(controlRecord.getBegintime());
		finishedRecord.setBloodpondid(controlRecord.getBloodpondid());
		finishedRecord.setBloodpondname(controlRecord.getBloodpondname());
		finishedRecord.setBloodpondnowcontrolcount(controlRecord.getBloodpondnowcontrolcount());
		finishedRecord.setBloodpondnowcount(controlRecord.getBloodpondnowcount());
		finishedRecord.setControlnullity(-1);
		finishedRecord.setEndtime(new Date());
		finishedRecord.setInserttime(new Date());
		finishedRecord.setRemark("后台手动结束血池控制");
		finishedRecord.setServerid(controlRecord.getServerid());
		finishedRecord.setServername(controlRecord.getServername());
		
		boolean endtemp = Blade.create(BloodPondFinishedRecord.class).save(finishedRecord);
		
		boolean temp = Blade.create(BloodPondControlRecord.class).deleteByIds(id) > 0;
		if (endtemp && temp) {
			return success("手动结束成功!");
		} else {
			return error("手动结束失败!");
		}
	}
	
	private void doLog(int type,Long changeVal,Long beforeVal,Long afterVal, String desc) {
		// 添加操作记录
		PlayerOperationLog log = new PlayerOperationLog();
		log.setOperatorid(Integer.parseInt(JSON.toJSONString(ShiroKit.getUser().getId())));
		log.setOperatorname(ShiroKit.getUser().getName());
		log.setInserttime(new Date());
		log.setStatus(1);
		log.setType(type);
		log.setDescription(desc);
		log.setOperateip(HttpKit.getRequest().getRemoteAddr());
		log.setGold(changeVal);
		log.setBeforegold(beforeVal);
		log.setAftergold(afterVal);
		Blade.create(PlayerOperationLog.class).save(log);
	}
	
	//编辑房间说明
	@Json
	@RequestMapping("/editdesc")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult editdesc() {
		String Description= getParameter("Description");
		Roomdesc roomdesc= Blade.create(Roomdesc.class).findFirstBy("", null);
		boolean temp = false;
		if(roomdesc == null) {
			roomdesc = new Roomdesc();
			roomdesc.setDescription(Description);
			temp = Blade.create(Roomdesc.class).save(roomdesc);
		} else {
			roomdesc.setDescription(Description);
			temp = Blade.create(Roomdesc.class).update(roomdesc);
		}
		System.out.println("修改房间配置描述："+temp);
		return json(Description);
	}
}
