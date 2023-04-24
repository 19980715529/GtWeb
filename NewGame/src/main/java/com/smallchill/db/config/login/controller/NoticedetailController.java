package com.smallchill.db.config.login.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.game.newmodel.logindb.Notice;
import com.smallchill.game.newmodel.logindb.Noticedetail;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/newnotice")
public class NoticedetailController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/noticedetail/";
	private static String CODE = "newnotice";
	private static String LIST_SOURCE = "db_noticedetail.list";
	private static String PREFIX = "noticedetail";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入公告详情配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "noticedetail.html";
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
	public String add(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "noticedetail_add.html";
	}
	
	@DoControllerLog(name="新增公告详情配置")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		Noticedetail boxItem = mapping(PREFIX, Noticedetail.class);
		boolean save = Blade.create(Noticedetail.class).save(boxItem);
		if (save) {
			List<Noticedetail> findBy = Blade.create(Noticedetail.class).findBy("name,[content],[version]", "[clientID]="+boxItem.getClientid(), null);
			String jsonString = JSON.toJSONString(findBy);
			Notice notice = Blade.create(Notice.class).findFirstBy("[clientID]="+boxItem.getClientid(), null);
			if(notice==null) {
				Db.insert("insert into [login].[dbo].[Notice] (clientID,notice) values("+boxItem.getClientid()+",'"+jsonString+"')", null);
			} else {
				System.out.println(JSON.toJSONString(notice));
				Blade.create(Notice.class).updateBy("notice='"+jsonString+"'", "id="+notice.getId(), null);
			}
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
		map.put("id", id);
		Map noticedetail = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("noticedetail", noticedetail);
		mm.put("code", CODE);
		return BASE_PATH + "noticedetail_edit.html";
	}
	
	@DoControllerLog(name="修改公告详情配置")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		Noticedetail boxItem = mapping(PREFIX, Noticedetail.class);
		if(boxItem.getVersion()>=10000) {
			boxItem.setVersion(0);
		} else {
			boxItem.setVersion(boxItem.getVersion()+1);
		}
		boolean temp = Blade.create(Noticedetail.class).update(boxItem);
		if (temp) {
			List<Noticedetail> findBy = Blade.create(Noticedetail.class).findBy("name,[content],[version]", "[clientID]="+boxItem.getClientid(), null);
			String jsonString = JSON.toJSONString(findBy);
			Notice notice = Blade.create(Notice.class).findFirstBy("[clientID]="+boxItem.getClientid(), null);
			if(notice==null) {
				Db.insert("insert into [login].[dbo].[Notice] (clientID,notice) values("+boxItem.getClientid()+",'"+jsonString+"')", null);
			} else {
				Blade.create(Notice.class).updateBy("notice='"+jsonString+"'", "id="+notice.getId(), null);
			}
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}
	
	@RequestMapping(KEY_VIEW + "/{id}")
	@Permission(ADMINISTRATOR)
	public String view(@PathVariable Integer id, ModelMap mm) {
		Noticedetail findById = Blade.create(Noticedetail.class).findById(id);
		Notice notice = Blade.create(Notice.class).findFirstBy("[clientID]="+findById.getClientid(), null);
		mm.put("newnotice", notice);
		mm.put("code", CODE);
		return BASE_PATH + "noticedetail_view.html";
	}
	
	@DoControllerLog(name="删除公告详情配置")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		boolean temp = Blade.create(Noticedetail.class).deleteByIds(ids) > 0;
		if (temp) {
			String[] split = ids.split(",");
			Noticedetail detail = null;
			Set<Integer> clients = new HashSet<Integer>();
			for (String id : split) {
				detail = Blade.create(Noticedetail.class).findById(id);
				clients.add(detail.getClientid());
			}
			System.out.println(JSON.toJSONString(clients));
			for (Integer clientid : clients) {
				List<Noticedetail> findBy = Blade.create(Noticedetail.class).findBy("name,[content],[version]", "[clientID]="+clientid, null);
				String jsonString = JSON.toJSONString(findBy);
				Notice notice = Blade.create(Notice.class).findFirstBy("[clientID]="+clientid, null);
				if(notice==null) {
					Db.insert("insert into [login].[dbo].[Notice] (clientID,notice) values("+clientid+",'"+jsonString+"')", null);
				} else {
					System.out.println(JSON.toJSONString(notice));
					Blade.create(Notice.class).updateBy("notice='"+jsonString+"'", "id="+notice.getId(), null);
				}
			}
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}