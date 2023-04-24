package com.smallchill.system.nativeweb.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.DateFormatKit;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.game.service.CommonService;
import com.smallchill.system.nativeweb.model.News;
import com.smallchill.system.nativeweb.model.NewsRequest;
import com.smallchill.system.nativeweb.model.NewsType;

@Controller
@RequestMapping("/gamenotice")
public class GameNoticeController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/gamenotice/";
	private static String CODE = "gamenotice";
	private static String LIST_SOURCE = "db_gamenotice.list";
	private static String PREFIX = "gamenotice";
	
	@Autowired
	private CommonService commonService;
	
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "gamenotice.html";
	}
	// 新闻公告
	@RequestMapping("/news")
	@Permission(ADMINISTRATOR)
	public String news(ModelMap mm) {
		mm.put("type", NewsType.PC_NEWS.code());
		mm.put("code", CODE);
		return BASE_PATH + "news.html";
	}
	// 移动端公告
	@RequestMapping("/mobile")
	@Permission(ADMINISTRATOR)
	public String mobile(ModelMap mm) {
		mm.put("type", NewsType.MOBILE_NOTICE.code());
		mm.put("code", CODE);
		return BASE_PATH + "mobile.html";
	}
	// 房卡公告
	@RequestMapping("/roomcard")
	@Permission(ADMINISTRATOR)
	public String roomcard(ModelMap mm) {
		mm.put("type", NewsType.ROOM_NOTICE.code());
		mm.put("code", CODE);
		return BASE_PATH + "roomcard.html";
	}
	// 弹出公告
	@RequestMapping("/pop")
	@Permission(ADMINISTRATOR)
	public String pop(ModelMap mm) {
		mm.put("type", NewsType.POP_NOTICE.code());
		mm.put("code", CODE);
		return BASE_PATH + "pop.html";
	}
	// 登录公告
	@RequestMapping("/login")
	@Permission(ADMINISTRATOR)
	public String login(ModelMap mm) {
		mm.put("type", NewsType.LOGIN_NOTICE.code());
		mm.put("code", CODE);
		return BASE_PATH + "login.html";
	}
	
	@Json
	@RequestMapping(KEY_LIST)
	@Permission(ADMINISTRATOR)
	public Object list() {
		Object gird = paginateBySelf(LIST_SOURCE);
		return gird;
	}
	
	@RequestMapping(KEY_ADD)
	@Permission(ADMINISTRATOR)
	public String add(@RequestParam(name="type", required=false) Integer type,ModelMap mm) {
		mm.put("type", type);
		mm.put("code", CODE);
		return BASE_PATH + "gamenotice_add.html";
	}
	
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		News news = new News();
		NewsRequest newsRequest = mapping(PREFIX, NewsRequest.class);
		DateFormatKit dateFormat = new DateFormatKit();
		if(newsRequest.getType() != null && newsRequest.getType() == 5) {
			String format = dateFormat.format(newsRequest.getDeployTime(), "yyyy-MM-dd");
			String startTime = format + " " + newsRequest.getStartHour() + ":00:00";
			String endTime = format + " " + newsRequest.getEndHour() + ":00:00";
			news.setStartTime(dateFormat.parseTime(startTime));
			news.setEndTime(dateFormat.parseTime(endTime));
			news.setInterval(newsRequest.getInterval());
		}
		news.setSubject(newsRequest.getSubject());
		news.setBody(newsRequest.getBody());
		news.setPtypeid(newsRequest.getPtypeid());
		
		Date now = new Date();
		news.setClassID(Short.valueOf(newsRequest.getType().toString()));
		news.setFormattedBody(news.getBody());
		news.setGameRange("");
		news.setHighLight("");
		news.setImageUrl("");
		news.setIsDelete((short)0);
		news.setIsElite((short)0);
		news.setIsHot((short)0);
		news.setIsLinks((short)0);
		news.setIsLock((short)0);
		news.setIssueDate(now);
		String ip = HttpKit.getRequest().getRemoteAddr();
		news.setIssueIP(ip);
		news.setLastModifyDate(now);
		news.setLastModifyIP(ip);
		news.setLinkUrl("");
		news.setOnTop((short)0);
		news.setOnTopAll((short)0);
		news.setPopId(0);
		news.setSubject1(news.getSubject());
		Object id = ShiroKit.getUser().getId();
		String jsonString = JSON.toJSONString(id);
		news.setUserId(Integer.valueOf(jsonString));
		boolean temp = Blade.create(News.class).save(news);
		if (temp) {
			return success(SAVE_SUCCESS_MSG);
		} else {
			return error(SAVE_FAIL_MSG);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(KEY_EDIT + "/{id}")
	@Permission(ADMINISTRATOR)
	public String edit(@PathVariable Integer id, ModelMap mm) {
		Map map = new HashMap();
		map.put("NewsID", id);
		Map gamenotice = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("gamenotice", gamenotice);
		mm.put("code", CODE);
		return BASE_PATH + "gamenotice_edit.html";
	}
	
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		News boxItem = mapping(PREFIX, News.class);
		boxItem.setSubject1(boxItem.getSubject());
		boxItem.setFormattedBody(boxItem.getBody());
		boolean temp = Blade.create(News.class).update(boxItem);
		if (temp) {
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(KEY_VIEW + "/{id}")
	@Permission(ADMINISTRATOR)
	public String view(@PathVariable Integer id, ModelMap mm) {
		Map map = new HashMap();
		map.put("NewsID", id);
		Map bloodpondconfig = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("gamenotice", bloodpondconfig);
		mm.put("code", CODE);
		return BASE_PATH + "gamenotice_view.html";
	}
	
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		boolean temp = Blade.create(News.class).deleteByIds(ids) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
	// 禁用
	@Json
	@RequestMapping("/locked")
	@Permission(ADMINISTRATOR)
	public AjaxResult locked() {
		String ids = getParameter("ids");
		String type = getParameter("type");
		CMap updateMap = CMap.init().set("ids", Convert.toIntArray(ids));
		updateMap.set("type", type);
		boolean temp = Blade.create(News.class).updateBy("IsLock = 0", "NewsID in (#{join(ids)}) and ClassID=#{type}", updateMap);
		if (temp) {
			return success("锁定成功!");
		} else {
			return error("锁定失败!");
		}
	}

	// 启用
	@Json
	@RequestMapping("/unlocked")
	@Permission(ADMINISTRATOR)
	public AjaxResult unlocked() {
		String ids = getParameter("ids");
		String type = getParameter("type");
		CMap updateMap = CMap.init().set("ids", Convert.toIntArray(ids));
		updateMap.set("type", type);
		//置其他条目为禁用
		Blade.create(News.class).updateBy("IsLock = 0","ClassID="+type, null);
		// 启用当前条
		boolean temp = Blade.create(News.class).updateBy("IsLock = 1", "NewsID in (#{join(ids)}) and ClassID=#{type}", updateMap);
		if (temp) {
			return success("启用成功!");
		} else {
			return error("启用失败!");
		}
	}
}