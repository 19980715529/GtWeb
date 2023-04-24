package com.smallchill.game.player.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.game.newmodel.serverinfodb.LQuestionuser;
import com.smallchill.game.player.meta.intercept.LQuestionuserValidator;

@Controller
@RequestMapping("/lquestionuser")
public class LQuestionuserController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/gameplayer/lquestionuser/";
	private static String CODE = "lquestionuser";
	private static String LIST_SOURCE = "player_lquestionuser.list";
	private static String PREFIX = "lquestionuser";
	
	@DoControllerLog(name="进入关注列表页面")
	@RequestMapping("/")
	@Permission({ ADMINISTRATOR, ADMIN })
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "player_lquestionuser_log.html";
	}
	
	@Json
	@RequestMapping(KEY_LIST)
	@Permission({ ADMINISTRATOR, ADMIN })
	public Object list() {
		Object gird = paginateBySelf(LIST_SOURCE);
		return gird;
	}
	
	@RequestMapping(KEY_ADD)
	@Permission({ ADMINISTRATOR, ADMIN })
	public String add(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "player_lquestionuser_add.html";
	}
	
	@DoControllerLog(name="新增关注列表信息")
	@Json
	@Before(LQuestionuserValidator.class)
	@RequestMapping(KEY_SAVE)
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult save() {
		LQuestionuser boxItem = mapping(PREFIX, LQuestionuser.class);
		if(Db.isExist("select userID from [QPServerInfoDB].[dbo].[L_QuestionUser] where [userID]="+boxItem.getUserid(), null)) {
			return error("该玩家ID已经存在.");
		}
		boolean temp = Blade.create(LQuestionuser.class).save(boxItem);
		if (temp) {
			return success(SAVE_SUCCESS_MSG);
		} else {
			return error(SAVE_FAIL_MSG);
		}
	}
	
	@DoControllerLog(name="删除关注列表信息")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult remove() {
		String ids = getParameter("ids");
		boolean temp = Blade.create(LQuestionuser.class).deleteByStrIds(ids)>0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}