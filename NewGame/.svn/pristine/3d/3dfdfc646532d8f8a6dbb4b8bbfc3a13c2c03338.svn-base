package com.smallchill.db.config.gameuser.controller;

import java.util.Date;
import java.util.List;

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
import com.smallchill.game.newmodel.gameuserdb.AaActivitycore;
import com.smallchill.game.newmodel.logindb.Notice;
import com.smallchill.game.newmodel.logindb.Noticedetail;

@Controller
@RequestMapping("/aaActivitycore")
public class AaActivitycoreController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/aaActivitycore/";
	private static String CODE = "aaActivitycore";
	private static String LIST_SOURCE = "db_aaActivitycore.list";
	private static String PREFIX = "aaActivitycore";
	
	@DoControllerLog(name="进入公告详情配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "aaActivitycore.html";
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
		return BASE_PATH + "aaActivitycore_add.html";
	}
	
	@DoControllerLog(name="新增公告详情配置")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		AaActivitycore boxItem = mapping(PREFIX, AaActivitycore.class);
		boxItem.setStarttime(new Date());
		boxItem.setStoptime(new Date());
		boolean save = Blade.create(AaActivitycore.class).save(boxItem);
		if (save) {
			List<AaActivitycore> findBy = Blade.create(AaActivitycore.class).findBy("Title as name,Remark as content", "[IsOnline]=1", null);
			String jsonString = JSON.toJSONString(findBy);
			Notice notice = Blade.create(Notice.class).findFirstBy("", null);
			if(notice==null) {
				Db.insert("insert into [login].[dbo].[Notice] (clientID,notice) values(5,'"+jsonString+"')", null);
			} else {
				Blade.create(Notice.class).updateBy("notice='"+jsonString+"'", "", null);
			}
			return success(SAVE_SUCCESS_MSG);
		} else {
			return error(SAVE_FAIL_MSG);
		}
	}
	
	@RequestMapping(KEY_EDIT + "/{id}")
	@Permission(ADMINISTRATOR)
	public String edit(@PathVariable Integer id, ModelMap mm) {
		AaActivitycore aaActivitycore = Blade.create(AaActivitycore.class).findById(id);
		mm.put("aaActivitycore", aaActivitycore);
		mm.put("code", CODE);
		return BASE_PATH + "aaActivitycore_edit.html";
	}
	
	@DoControllerLog(name="修改公告详情配置")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		AaActivitycore boxItem = mapping(PREFIX, AaActivitycore.class);
		if(boxItem.getIsonline()==0) {
			boxItem.setStoptime(new Date());
		}
		boolean temp = Blade.create(AaActivitycore.class).update(boxItem);
		if (temp) {
			List<AaActivitycore> findBy = Blade.create(AaActivitycore.class).findBy("Title,Remark", "[IsOnline]=1", null);
			String jsonString = JSON.toJSONString(findBy).replaceAll("title", "name").replaceAll("remark", "content");
			System.out.println("==========="+findBy.size());
			System.out.println(jsonString);
			System.out.println("===========");
			Notice notice = Blade.create(Notice.class).findFirstBy("", null);
			if(notice==null) {
				Db.insert("insert into [login].[dbo].[Notice] (clientID,notice) values(5,'"+jsonString+"')", null);
			} else {
				Blade.create(Notice.class).updateBy("notice='"+jsonString+"'", "id>0", null);
			}
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}
	
	@DoControllerLog(name="删除公告详情配置")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		boolean temp = Blade.create(AaActivitycore.class).deleteByIds(ids) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}