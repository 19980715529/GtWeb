package com.smallchill.game.player.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstConfig;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.kit.CharsetKit;
import com.smallchill.core.toolbox.kit.OkHttpUtil;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.kit.URLKit;
import com.smallchill.game.model.request.AaZzLogPropchangeRequest;
import com.smallchill.game.newmodel.Gameroomitem;
import com.smallchill.game.newmodel.result.WinLoseStatistics;
import com.smallchill.game.service.CommonService;
import com.smallchill.game.service.PlayerElasticService;

@Controller
@RequestMapping("/player")
public class PlayerLoseWinController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/gameplayer/";
	private static String CODE = "player";
	@Autowired
	private PlayerElasticService playerElasticService;
	@Autowired
	private CommonService commonService;

	//	@SystemControllerLog(description = "用户输赢搜索")
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Json
	@RequestMapping("/wllist")
	public Object wllist() throws IOException {
		Object gird = new Object();
		String parameter = getParameter("where");
		if(StrKit.isBlank(parameter)) {
			return gird;
		}
		if (parameter.contains("%")) {
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		Map parse = JSON.parseObject(parameter, Map.class);
		String ServerId = String.valueOf(parse.get("ServerId")).replaceAll("\"","");
		if(ServerId!=null&&ServerId!=""&&!ServerId.isEmpty()) {
			Gameroomitem room = Blade.create(Gameroomitem.class).findFirstBy(" where ServerID="+ServerId, null);
			String sqlname = room.getKindid() + "_list";
			if(room!=null)
				if(ConstConfig.SYS_ISONSEARCH.equalsIgnoreCase("false")) {
					gird = paginateBySelf("player_lose_win_search."+sqlname);
				} else {
			    	Integer page = getParameterToInt("page", 1);
					Integer rows = getParameterToInt("rows", 20);
					String sort =  getParameter("sort", StrKit.EMPTY);
					String order =  getParameter("order", StrKit.EMPTY);
					String cursor =  CacheKit.get(LONG_CACHE, "cursor"+this.getSession().getId());
					if(page==1) {
						cursor =  "";
						CacheKit.remove(LONG_CACHE, "cursor"+this.getSession().getId());
					} else {
						cursor =  CacheKit.get(LONG_CACHE, "cursor"+this.getSession().getId());
					}
					
					Map result = new HashMap();
					// 总条数
					Map<String, Object> countParams = new HashMap<>();
					/*if(!StrKit.isBlank(cursor)) {
						countParams.put("cursor",cursor);
					} else {*/
						StringBuilder counts = new StringBuilder();
						counts.append("SELECT count(distinct user_id) as totalCount FROM goldlogs_1 WHERE prop_id=1");
						counts.append(this.buildReq(parse).toString());
						if(parse.get("WinResultStart")!=null) {
							String WinResultStart = JSON.toJSONString(parse.get("WinResultStart")).replaceAll("\"", "");
							counts.append(" having sum(amount) >= " + Long.parseLong(WinResultStart));
						}
						if(parse.get("WinResultEnd")!=null) {
							String WinResultEnd = JSON.toJSONString(parse.get("WinResultEnd")).replaceAll("\"", "");
							counts.append(" having sum(amount) <= " + Long.parseLong(WinResultEnd));
						}
						countParams.put("query",counts.toString());
					/* } */
					String postCountsJsonParams = OkHttpUtil.postJsonParams("http://"+ConstConfig.ELASTIC_SEARCH_URL+":9200/_sql?format=json", JSON.toJSONString(countParams));
					Map<String,Object> countsResult = JSON.parseObject(postCountsJsonParams, Map.class);
					JSONArray countRows = (JSONArray) countsResult.get("rows");
					for (Object object3 : countRows) {
						JSONArray o = (JSONArray) object3;
						int records = o.getInteger(0);
						result.put("records", records);
						result.put("page", page);
						if(records%rows==0) {
							result.put("total", records/rows);
						} else {
							result.put("total", ((records/rows)+1));
						}
					}
					
					// 详情获取
					Map<String, Object> params = new HashMap<>();
					if(!StrKit.isBlank(cursor)) {
						params.put("cursor",cursor);
					} else {
						StringBuilder builder = new StringBuilder();
						builder.append("SELECT user_id,sum(amount) as totalAmount FROM goldlogs_1 WHERE prop_id=1");
						builder.append(this.buildReq(parse).toString());
						builder.append(" group by user_id");
						if(parse.get("WinResultStart")!=null) {
							String WinResultStart = JSON.toJSONString(parse.get("WinResultStart")).replaceAll("\"", "");
							builder.append(" having sum(amount) >= " + Long.parseLong(WinResultStart));
						}
						if(parse.get("WinResultEnd")!=null) {
							String WinResultEnd = JSON.toJSONString(parse.get("WinResultEnd")).replaceAll("\"", "");
							builder.append(" having sum(amount) <= " + Long.parseLong(WinResultEnd));
						}
						/*
						 * if(!StrKit.isBlank(sort)) {
						 * builder.append(" order by "+sort.toLowerCase()+" "+(order==null?"desc":order.
						 * toLowerCase())); }
						 */
						
						params.put("query",builder.toString());
						params.put("fetch_size",rows);
					}
					
					String postJsonParams = OkHttpUtil.postJsonParams("http://"+ConstConfig.ELASTIC_SEARCH_URL+":9200/_sql?format=json", JSON.toJSONString(params));
					Map<String,Object> object = JSON.parseObject(postJsonParams, Map.class);
					JSONArray object2 = (JSONArray) object.get("rows");
					Map ccursor = new HashMap();
					ccursor.put("cursor", object.get("cursor"));
					result.put("userdata", JSON.toJSONString(ccursor));
					CacheKit.put(LONG_CACHE, "cursor"+this.getSession().getId(), object.get("cursor"));
					
					List<Integer> userIds = new ArrayList<Integer>();
					List<WinLoseStatistics> searchWinLoseStatistics = new ArrayList<>();
					WinLoseStatistics w = null;
					for (Object object3 : object2) {
						JSONArray o = (JSONArray) object3;
						w = new WinLoseStatistics();
						w.setUserId(o.getInteger(0));
						w.setDayWaste(o.getLong(1));
						searchWinLoseStatistics.add(w);
						userIds.add(w.getUserId());
					}
					w = null;
					
					Map paras = new HashMap();
					paras.put("ids", userIds);
					List<Map> infoList = commonService.getInfoList("player_lose_win_search.22_list_", paras);
					String UserID = null;
					for (Map gameroomitem2 : infoList) {
						UserID = JSON.toJSONString(gameroomitem2.get("UserID")).replaceAll("\"", "");
						if(UserID!=null && !UserID.equalsIgnoreCase("null")) {
							for (WinLoseStatistics s : searchWinLoseStatistics) {
								if(s.getUserId()==Integer.parseInt(UserID)) {
									gameroomitem2.put("DayWaste",s.getDayWaste());
									break;
								}
							}
						}
					}
					result.put("rows", infoList);
					return result;
				}
			return gird;
		}else{
			return gird;
		}
	}
	@DoControllerLog(name="进入用户输赢搜索页面")
	@RequestMapping(KEY_PLAYER_WINLOSE_SEARCH)
	public String winlosesearch(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "player_winlose_search.html";
	}
	@Json
	@RequestMapping("/wbllist")
	public Object wbllist() {
		Object gird = new Object();
		String parameter = getParameter("where");
		if(StrKit.isBlank(parameter)) {
			return gird;
		}
		gird = paginateBySelf("player_lose_win_search.backend_list");
		return gird;
	}
	@DoControllerLog(name="进入用户输赢搜索页面")
	@RequestMapping("/wlbearch")
	public String wlbearch(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "player_winlose_search2.html";
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

	private StringBuilder buildReq(Map paras) {
		StringBuilder builder = new StringBuilder();
		if(paras.get("GameID")!=null) {
			String GameID = JSON.toJSONString(paras.get("GameID")).replaceAll("\"", "");
			builder.append(" and user_id = "+(Integer.parseInt(GameID)-10000));
		}
		if(paras.get("UserID")!=null) {
			String UserID = JSON.toJSONString(paras.get("UserID")).replaceAll("\"", "");
			builder.append(" and user_id = "+(Integer.parseInt(UserID)));
		}
		if(paras.get("ServerId")!=null) {
			String ServerID = JSON.toJSONString(paras.get("ServerId")).replaceAll("\"", "");
			builder.append(" and serverid = "+(Integer.parseInt(ServerID)));
		}
		if(paras.get("kindid")!=null) {
			String kindid = JSON.toJSONString(paras.get("kindid")).replaceAll("\"", "");
			builder.append(" and kindid = "+(Integer.parseInt(kindid)));
		}
		if(paras.get("ChangeType_Id")!=null) {
			String ChangeType_Id = JSON.toJSONString(paras.get("ChangeType_Id")).replaceAll("\"", "");
			builder.append(" and changetype_id = "+(Integer.parseInt(ChangeType_Id)));
		}
		
		if(paras.get("StartTime_dategt")!=null) {
			String InsertTime_dategt = JSON.toJSONString(paras.get("StartTime_dategt")).replaceAll("\"", "");
			builder.append(" and time_id >= "+Long.parseLong(InsertTime_dategt));
		}
		if(paras.get("EndTime_datelt")!=null) {
			String InsertTime_datelt = JSON.toJSONString(paras.get("EndTime_datelt")).replaceAll("\"", "");
			builder.append(" and time_id <= "+Long.parseLong(InsertTime_datelt));
		}
		
		return builder;
	}
}