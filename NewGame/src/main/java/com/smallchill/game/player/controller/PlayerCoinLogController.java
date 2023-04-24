package com.smallchill.game.player.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstConfig;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.grid.BladePage;
import com.smallchill.core.toolbox.kit.CharsetKit;
import com.smallchill.core.toolbox.kit.DateFormatKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.kit.URLKit;
import com.smallchill.game.model.request.AaZzLogPropchangeRequest;
import com.smallchill.game.model.request.OrderByRequest;
import com.smallchill.game.newmodel.Gameroomitem;
import com.smallchill.game.newmodel.gameuserdb.AaZzLogPropchangeChangetype;
import com.smallchill.game.service.CommonService;
import com.smallchill.game.service.PlayerElasticService;

@Controller
@RequestMapping("/player")
public class PlayerCoinLogController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/gameplayer/";
    private static String CODE = "player";

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CommonService commonService;
	@Autowired
	private PlayerElasticService playerElasticService;

    @DoControllerLog(name = "进入玩家金币变动记录列表页面")
    @RequestMapping(KEY_PLAYER_COINCHANGE_LOG)
    public String cionchangelog(@RequestParam(name = "id", required = false) Integer id, ModelMap mm) {
        mm.put("code", CODE);
        mm.put("id", id);
        return BASE_PATH + "player_coinchange_log.html";
    }
	// SuppressWarnings：抑制出现黄线
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Json
    @RequestMapping("/ccllist")
    public Object ccllist() throws ParseException {
    	Integer page = getParameterToInt("page", 1);
		Integer rows = getParameterToInt("rows", 10);
		String sort =  getParameter("sort", StrKit.EMPTY);
		String order =  getParameter("order", StrKit.EMPTY);
		
		String parameter = request.getParameter("where");
		if(StrKit.isBlank(parameter)) {
			return null;
		}
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		Object gird = new Object();
		Map paras = JSON.parseObject(parameter, Map.class);
		Object changeType_id = paras.get("ChangeType_Id");
		if (changeType_id==null){
			changeType_id = "";
		}
		LOGGER.error(changeType_id);
		String ServerId = JSON.toJSONString(paras.get("ServerID")).replaceAll("\"", "");
        if (ServerId != null && !ServerId.equals("") && !ServerId.equals("null")) {
            Gameroomitem room = Blade.create(Gameroomitem.class).findFirstBy(" where ServerID=" + ServerId, null);
            if(ConstConfig.SYS_ISONSEARCH.equalsIgnoreCase("false")) {
            	gird = paginateBySelf("player_gold_change_log." + String.valueOf(room.getKindid()) + "_change_log");
            	return gird;
            }
        } else if("-7".equals(changeType_id.toString())){
			// 查询用户的转盘金币奖励
			return paginateBySelf("gold_change_record.rotary_gold_bonus");
		}else {
			gird = paginateBySelf("player_gold_change_log.new_gold_change_log");
			return gird;
		}

		AaZzLogPropchangeRequest request = this.build(paras);
		request.setSize(rows);
		request.setPage(page);
		OrderByRequest orderBy = new OrderByRequest();
		if(!StrKit.isBlank(sort)) {
			if(!StrKit.isBlank(sort)) {
				orderBy.setFieldName(sort.toLowerCase());
				orderBy.setOrder(order.equalsIgnoreCase("asc")?SortOrder.ASC:SortOrder.DESC);
				request.setOrderBy(orderBy);
			}
		}
		paras = null;
		try {
			List<Gameroomitem> rooms = Blade.create(Gameroomitem.class).findAll();
			 List<AaZzLogPropchangeChangetype> types = Blade.create(AaZzLogPropchangeChangetype.class).findAll();
			
			BladePage<Map> byPage = playerElasticService.searchGoldChangeLogByPage(request);
			String ServerID = null;
			String changetype_id = null;
			System.out.println("///////////////////////////////////////////////////////////////////////////////////////////");
			System.out.println(byPage.getRows());
			for (Map gameroomitem2 : byPage.getRows()) {
				ServerID = JSON.toJSONString(gameroomitem2.get("serverid")).replaceAll("\"", "");
				changetype_id = JSON.toJSONString(gameroomitem2.get("changetype_id")).replaceAll("\"", "");
				if(ServerID!=null && !ServerID.equalsIgnoreCase("null")) {
					for (Gameroomitem room : rooms) {
						if(room.getServerid()==Integer.parseInt(ServerID)) {
							gameroomitem2.put("roomname", room.getRoomname());
							break;
						}
					}
				}
				if(changetype_id!=null && !changetype_id.equalsIgnoreCase("null")) {
					for (AaZzLogPropchangeChangetype type : types) {
						if(type.getChangeType_Id()==Integer.parseInt(changetype_id)) {
							gameroomitem2.put("typeremark", type.getRemark());
							break;
						}
					}
				}
				if(gameroomitem2.get("logtime")!=null) {
					String logtime = JSON.toJSONString(gameroomitem2.get("logtime")).replaceAll("\"", "");
					gameroomitem2.put("logtime", DateFormatKit.getMsTimeToString(logtime.replaceAll("T", " ").replaceAll("Z", "")));
				}
			}
			return byPage;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }

    public long timeToStamp(String timers) {
        Date d = new Date();
        long timeStemp = 0;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            d = sf.parse(timers);// 日期转换为时间戳
        } catch (ParseException e) {
            e.printStackTrace();
        }
        timeStemp = d.getTime();
        return timeStemp;
    }

    @Json
    @RequestMapping("/empty")
    public Object empty() {
		Object gird = new Object();
		return gird;
    }

    //	@SystemControllerLog(description = "获取金币变动记录列表->房间统计")
    @SuppressWarnings({"rawtypes"})
    @Json
    @RequestMapping("/crllist")
    public Object crllist() {
    	Object gird = new Object();
    	if(ConstConfig.SYS_ISONSEARCH.equalsIgnoreCase("false")) {
    		return gird;
    	}
		String sort =  getParameter("sort", StrKit.EMPTY);
		String order =  getParameter("order", StrKit.EMPTY);
		String parameter = request.getParameter("where");
		Map paras = null;
		if(StrKit.isBlank(parameter)) {
			return gird;
		}
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		paras = JSON.parseObject(parameter, Map.class);
		/* paras.put("kindid", 22); */
		AaZzLogPropchangeRequest request = this.build(paras);
		
		OrderByRequest orderBy = new OrderByRequest();
		if(!StrKit.isBlank(sort)) {
			if(!StrKit.isBlank(sort)) {
				orderBy.setFieldName(sort.toLowerCase());
				orderBy.setOrder(order.equalsIgnoreCase("asc")?SortOrder.ASC:SortOrder.DESC);
				request.setOrderBy(orderBy);
			}
		}
		paras = null;
		try {
			List<Gameroomitem> list = Blade.create(Gameroomitem.class).findAll(); // 所以房间信息
			List<Gameroomitem> sums = playerElasticService.searchGoldChangeLogByRoom(request); // 统计房间信息
			for (Gameroomitem gameroomitem : sums) {
				for (Gameroomitem gameroomitem2 : list) {
					if(gameroomitem.getServerid().intValue()==gameroomitem2.getServerid().intValue()) {
						gameroomitem.setRoomname(gameroomitem2.getRoomname());
						break;
					}
				}
			}
			return sums;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("rawtypes")
    @Json
    @RequestMapping("/getPresentGold")
    @Permission(ADMINISTRATOR)
    public AjaxResult getPresentGold() {
        String parameter = request.getParameter("where");
        if (parameter.contains("%")) {
            parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
        }
        Map parseObject2 = JSON.parseObject(parameter, Map.class);
        Map m = commonService.getInfoByOne("player_gold_change_log.new_present", parseObject2);
        return json(m);
    }

    @RequestMapping("/coinchangecharts")
    public String coinchangecharts(@RequestParam(name = "where", required = false) String where, ModelMap mm) {
        mm.put("code", CODE);
        mm.put("where", where);
        return BASE_PATH + "player_coinchange_detail.html";
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Json
    @RequestMapping("/ccchartslist")
    public AjaxResult ccchartslist() {
		String parameter = request.getParameter("where");
		Map paras = null;
		if(StrKit.isBlank(parameter)) {
			return null;
		} else {
			LOGGER.info("房间配置->血池变化->获取图表数据查询参数：-------------------");
			LOGGER.info(parameter);
			if(parameter.contains("%")){
				parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
			}
			paras = JSON.parseObject(parameter, Map.class);
			
			String ServerId = JSON.toJSONString(paras.get("ServerID")).replaceAll("\"", "");
	        if (ServerId != null && !ServerId.equals("") && !ServerId.equals("null")) {
	        	Gameroomitem room = Blade.create(Gameroomitem.class).findFirstBy(" where ServerID=" + ServerId, null);
	            if(ConstConfig.SYS_ISONSEARCH.equalsIgnoreCase("false")) {
	            	paras.put("orderBy", "desc");
					List<Map> data  = commonService.getInfoList("player_gold_change_log." + String.valueOf(room.getKindid()) + "_change_log", paras);
					return json(data);
	            }
	        } else {
	        	return null;
	        }
		}
		
		AaZzLogPropchangeRequest request = this.build(paras);
		
		OrderByRequest orderBy = new OrderByRequest();
		orderBy.setFieldName("logtime");
		orderBy.setOrder(SortOrder.ASC);
		request.setOrderBy(orderBy);
		
		paras = null;
		try {
			return json(playerElasticService.searchGoldChangeLogByList(request));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Json
    @RequestMapping("/getUserTotal")
    public AjaxResult getUserTotal() {
        String parameter = request.getParameter("where");
        Map parseObject2 = null;
        if (StrKit.isBlank(parameter)) {
            return json(null);
        } else {
            if (parameter.contains("%")) {
                parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
            }
            parseObject2 = JSON.parseObject(parameter, Map.class);
        }

        //赠送
        Object send = commonService.getInfoByOne("player_gold_change_log.new_send", parseObject2);
        //接收
        Object receive = commonService.getInfoByOne("player_gold_change_log.new_receive", parseObject2);
        //充值
        Object recharge = commonService.getInfoByOne("player_gold_change_log.new_recharge", parseObject2);
		// 兑换统计
        Map exchange = commonService.getInfoByOne("player_gold_change_log.new_exchange", parseObject2);
		//银行取款
        Object withdrawals = commonService.getInfoByOne("player_gold_change_log.bank_withdrawals", parseObject2);
        //银行存款
        Object deposit = commonService.getInfoByOne("player_gold_change_log.bank_deposit", parseObject2);
		// 转盘获得金币
		Map turntable = commonService.getInfoByOne("player_gold_change_log.rotary_give_gold", parseObject2);
		// 邮件金币变动
		Map email = commonService.getInfoByOne("player_gold_change_log.email_give_gold", parseObject2);
		Map m = new HashMap();
        m.put("send", send);//赠送统计
        m.put("receive", receive);//接收统计
        m.put("recharge", recharge);//充值统计
        m.put("exchanges", exchange);//兑换统计
        m.put("withdrawals", withdrawals);//银行取款
        m.put("deposit", deposit);//银行存款
		m.put("turntable",turntable); // 转盘获得金币
		m.put("emailList",email); // 转盘获得金币
        return json(m);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Json
    @RequestMapping("/get777")
    public AjaxResult get777() {
        String parameter = request.getParameter("where");
        Map paras = null;
        if (StrKit.isBlank(parameter)) {
            return null;
        } else {
            if (parameter.contains("%")) {
                parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
            }
            paras = JSON.parseObject(parameter, Map.class);
        }
    	
    	List<Map> xml22= commonService.getInfoListByDbname("morepropchange","gold_change_log.new_room_gold_change", paras);
    	List<Gameroomitem> rooms = Blade.create(Gameroomitem.class).findAll();
        String ServerID = null;
        for (Map gameroomitem2 : xml22) {
        	ServerID = JSON.toJSONString(gameroomitem2.get("ServerId")).replaceAll("\"", "");
        	for (Gameroomitem gameroomitem : rooms) {
				if(gameroomitem.getServerid()==Integer.parseInt(ServerID)) {
					gameroomitem2.put("RoomName", gameroomitem.getRoomname());
					break;
				}
			}
		}
    	return json(xml22);
    }
    
	private AaZzLogPropchangeRequest build(Map paras) {
		AaZzLogPropchangeRequest request = new AaZzLogPropchangeRequest();
		if(paras.get("GameID")!=null) {
			String GameID = JSON.toJSONString(paras.get("GameID")).replaceAll("\"", "");
			request.setUser_Id(Integer.parseInt(GameID)-10000);
		}
		if(paras.get("UserID")!=null) {
			String UserID = JSON.toJSONString(paras.get("UserID")).replaceAll("\"", "");
			request.setUser_Id(Integer.parseInt(UserID));
		}
		if(paras.get("ServerID")!=null) {
			String ServerID = JSON.toJSONString(paras.get("ServerID")).replaceAll("\"", "");
			request.setServerId(Integer.parseInt(ServerID));
		}
		if(paras.get("kindid")!=null) {
			String kindid = JSON.toJSONString(paras.get("kindid")).replaceAll("\"", "");
			request.setKindId(Integer.parseInt(kindid));
		}
		if(paras.get("ChangeType_Id")!=null) {
			String ChangeType_Id = JSON.toJSONString(paras.get("ChangeType_Id")).replaceAll("\"", "");
			request.setChangeType_Id(Integer.parseInt(ChangeType_Id));
		}

		if(paras.get("InsertTime_dategt")!=null) {
			String InsertTime_dategt = JSON.toJSONString(paras.get("InsertTime_dategt")).replaceAll("\"", "");
			request.setStartTimeId(Long.parseLong(InsertTime_dategt));
		}
		if(paras.get("InsertTime_datelt")!=null) {
			String InsertTime_datelt = JSON.toJSONString(paras.get("InsertTime_datelt")).replaceAll("\"", "");
			request.setEndTimeId(Long.parseLong(InsertTime_datelt));
		}
		
		return request;
	}
}