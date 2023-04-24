package com.smallchill.system.nativeweb.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.DateFormatKit;
import com.smallchill.modules.platform.service.NoticeService;
import com.smallchill.system.nativeweb.meta.intercept.NewsValidator;
import com.smallchill.system.nativeweb.model.News;
import com.smallchill.system.nativeweb.model.NewsRequest;
import com.smallchill.system.nativeweb.model.NewsType;

@Controller
@RequestMapping("/news")
public class NewsController extends BaseController {
	private static String CODE = "news";
	private static String LIST_SOURCE = "news.list";
	private static String BASE_PATH = "/system/gamesystem/news/";
	private static String PREFIX = "news";
	
	@Autowired
	NoticeService service;
	
	@Autowired
	private HttpServletRequest request;
	
	@RequestMapping(KEY_MAIN)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		mm.put("type", NewsType.POP_NOTICE.code());
		return BASE_PATH + "news.html";
	}
	//	@SystemControllerLog(description = "获取公告列表列表")
	@Json
	@RequestMapping(KEY_LIST)
	public Object list() {
		//Object grid = paginate("nativeWeb",LIST_SOURCE);
		Object grid = paginateBySelf(LIST_SOURCE);
		return grid;
	}
	
	@RequestMapping(KEY_ADD)
	public String add(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "news_add.html";
	}
	
	//	@SystemControllerLog(description = "新增公告")
	@Json
	@Before(NewsValidator.class)
	@RequestMapping(KEY_SAVE)
	public AjaxResult save() {
		News news = new News();
		DateFormatKit dateFormat = new DateFormatKit();
		NewsRequest newsRequest = mapping(PREFIX, NewsRequest.class);
		String format = dateFormat.format(newsRequest.getDeployTime(), "yyyy-MM-dd");
		String startTime = format + " " + newsRequest.getStartHour() + ":00:00";
		String endTime = format + " " + newsRequest.getEndHour() + ":00:00";
		news.setStartTime(dateFormat.parseTime(startTime));
		news.setEndTime(dateFormat.parseTime(endTime));
		news.setSubject(newsRequest.getSubject());
		news.setBody(newsRequest.getBody());
		news.setInterval(newsRequest.getInterval());
		news.setPtypeid(newsRequest.getPtypeid());
		
		Date now = new Date();
		news.setClassID(NewsType.POP_NOTICE.code());
		news.setFormattedBody(news.getBody());
		news.setGameRange("");
		news.setHighLight("");
		news.setImageUrl("");
		news.setIsDelete((short)0);
		news.setIsElite((short)0);
		news.setIsHot((short)0);
		news.setIsLinks((short)0);
		news.setIsLock((short)1);
		news.setIssueDate(now);
		news.setIssueIP(request.getRemoteAddr());
		news.setLastModifyDate(now);
		news.setLastModifyIP(request.getRemoteAddr());
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
	
	@RequestMapping(KEY_VIEW + "/{id}")
	public String view(@PathVariable Integer id, ModelMap mm) {
		News news = Blade.create(News.class).findById(id);
		CMap cmap = CMap.parse(news);
		mm.put("news", cmap);
		mm.put("code", CODE);
		return BASE_PATH + "news_view.html";
	}
	
	@Json
	@RequestMapping(KEY_REMOVE)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		int cnt = Blade.create(News.class).deleteByIds(ids);
		if (cnt > 0) {
			return success(DEL_SUCCESS_MSG);
		} else {
			return error(DEL_FAIL_MSG);
		}
	}
}
