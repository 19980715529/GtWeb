package com.smallchill.db.config.login.controller;
//package com.smallchill.db.config.controller.login;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.smallchill.common.base.BaseController;
//import com.smallchill.core.annotation.DoControllerLog;
//import com.smallchill.core.annotation.Json;
//import com.smallchill.core.annotation.Permission;
//import com.smallchill.core.constant.ConstShiro;
//import com.smallchill.core.plugins.dao.Blade;
//import com.smallchill.core.plugins.dao.Db;
//import com.smallchill.core.toolbox.ajax.AjaxResult;
//import com.smallchill.game.newmodel.logindb.Notice;
//import com.smallchill.game.service.CommonService;
//
//@Controller
//@RequestMapping("/newnotice")
//public class NewNoticeController extends BaseController implements ConstShiro {
//	private static String BASE_PATH = "/db/newnotice/";
//	private static String CODE = "newnotice";
//	private static String LIST_SOURCE = "db_newnotice.list";
//	private static String PREFIX = "newnotice";
//	
//	@Autowired
//	private CommonService commonService;
//	
//	@DoControllerLog(name="进入公告配置列表页面")
//	@RequestMapping("/")
//	@Permission(ADMINISTRATOR)
//	public String index(ModelMap mm) {
//		mm.put("code", CODE);
//		return BASE_PATH + "newnotice.html";
//	}
//	
//	@Json
//	@RequestMapping(KEY_LIST)
//	@Permission(ADMINISTRATOR)
//	public Object list() {
//		Object gird = paginateBySelf(LIST_SOURCE);
//		return gird;
//	}
//	
//	@RequestMapping(KEY_ADD)
//	@Permission(ADMINISTRATOR)
//	public String add(ModelMap mm) {
//		mm.put("code", CODE);
//		return BASE_PATH + "newnotice_add.html";
//	}
//	
//	@DoControllerLog(name="新增公告配置")
//	@Json
//	@RequestMapping(KEY_SAVE)
//	@Permission(ADMINISTRATOR)
//	public AjaxResult save() {
//		Notice boxItem = mapping(PREFIX, Notice.class);
//		/*try{
//			String str = boxItem.getNotice();
//			str = str.substring(str.indexOf(">")+1, str.lastIndexOf("<"));
//			List<Map> json = JSON.parseObject(str, List.class);
//		}catch(Exception e){
//			return error("公告内容为json格式，请输入正确的字符串格式。<br/>正确格式如下：[{\"name\":\"xxx\",\"content\":\"xxx\"},{\"name\":\"xxx\",\"content\":\"xxx\"}]");
//		}*/
//		int temp = Db.insert("insert into [login].[dbo].[Notice] (clientID,notice) values("+boxItem.getClientid()+",'"+boxItem.getNotice()+"')", null);
//		if (temp>0) {
//			return success(SAVE_SUCCESS_MSG);
//		} else {
//			return error(SAVE_FAIL_MSG);
//		}
//	}
//	
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@RequestMapping(KEY_EDIT + "/{id}")
//	@Permission(ADMINISTRATOR)
//	public String edit(@PathVariable Integer id, ModelMap mm) {
//		Map map = new HashMap();
//		map.put("id", id);
//		Map newnotice = commonService.getInfoByOne(LIST_SOURCE, map);
//		mm.put("newnotice", newnotice);
//		mm.put("code", CODE);
//		return BASE_PATH + "newnotice_edit.html";
//	}
//	
//	@DoControllerLog(name="修改公告配置")
//	@Json
//	@RequestMapping(KEY_UPDATE)
//	@Permission(ADMINISTRATOR)
//	public AjaxResult update() {
//		Notice boxItem = mapping(PREFIX, Notice.class);
//		/*try{
//			String str = boxItem.getNotice();
//			str = str.substring(str.indexOf(">")+1, str.lastIndexOf("<"));
//			List<Map> json = JSON.parseObject(str, List.class);
//		}catch(Exception e){
//			return error("公告内容为json格式，请输入正确的字符串格式。<br/>正确格式如下：[{\"name\":\"xxx\",\"content\":\"xxx\"},{\"name\":\"xxx\",\"content\":\"xxx\"}]");
//		}*/
//
//		StringBuilder builder = new StringBuilder();
//		builder.append("clientID="+boxItem.getClientid());
//		builder.append(",notice='"+boxItem.getNotice()+"'");
//		boolean temp = Blade.create(Notice.class).updateBy(builder.toString(), "id="+boxItem.getId(), null);
//		if (temp) {
//			return success(UPDATE_SUCCESS_MSG);
//		} else {
//			return error(UPDATE_FAIL_MSG);
//		}
//	}
//	
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@RequestMapping(KEY_VIEW + "/{id}")
//	@Permission(ADMINISTRATOR)
//	public String view(@PathVariable Integer id, ModelMap mm) {
//		Map map = new HashMap();
//		map.put("id", id);
//		Map bloodpondconfig = commonService.getInfoByOne(LIST_SOURCE, map);
//		mm.put("newnotice", bloodpondconfig);
//		mm.put("code", CODE);
//		return BASE_PATH + "newnotice_view.html";
//	}
//	
//	@DoControllerLog(name="删除公告配置")
//	@Json
//	@RequestMapping(KEY_REMOVE)
//	@Permission(ADMINISTRATOR)
//	public AjaxResult remove() {
//		String ids = getParameter("ids");
//		boolean temp = Blade.create(Notice.class).deleteByIds(ids) > 0;
//		if (temp) {
//			return success("删除成功!");
//		} else {
//			return error("删除失败!");
//		}
//	}
//}