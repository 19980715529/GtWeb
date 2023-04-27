package com.smallchill.game.player.controller;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstConfig;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.game.model.UserRightType;
import com.smallchill.game.newmodel.AaGiverecord;
import com.smallchill.game.newmodel.Accountsinfo;
import com.smallchill.game.newmodel.Gamescoreinfo;
import com.smallchill.game.newmodel.serverinfodb.Changeviplevel;

@Controller
@RequestMapping("/player")
public class PlayerOperateController2  extends BaseController implements ConstShiro {

	@Json
	@RequestMapping("/getMaxLevelVip")
	public AjaxResult getMaxLevelVip() {
		String id = getParameter("id");
		Accountsinfo accountsinfo = Blade.create(Accountsinfo.class).findById(id);
		Changeviplevel changeviplevel = Blade.create(Changeviplevel.class).findFirstBy("account='"+accountsinfo.getAccounts()+"'", null);
		if(changeviplevel!=null) {
			return json(changeviplevel.getParam1());
		} else {
			return json(0);
		}
	}
	/*
	 * 更新玩家关联备注
	 */
	@SuppressWarnings("unchecked")
	@Json
	@RequestMapping("/updateRelationTipName")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult updateRelationTipName() {
		String where = getParameter("where");
		String tipName = getParameter("tipName");
		Map<String,String> parseObject = JSON.parseObject(where, Map.class);
		
		String sql = "";
		for (Entry<String, String> it : parseObject.entrySet()) {
			System.out.println(it.getKey());
			System.out.println(it.getValue());
			if(StrKit.equals("LastLoginMachine", it.getKey())) {
				sql += "LOWER("+it.getKey()+") like '%"+it.getValue().toLowerCase()+"%'";
			} else {
				sql += it.getKey()+"='"+it.getValue()+"'";
			}
		}
		
		// 更新关联账号备注
		boolean updateBy = Blade.create(Accountsinfo.class).updateBy("tipsName='"+tipName+"'",sql, null);
		
		if (updateBy) {
			//doLogByMessage(request.getUserid().toString(),UserRightType.UR_CHANGE_NICKNAME.code(),old.getNickname(),request.getNickName());
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}
	
	// 设置为仓库号
    @Json
    @RequestMapping("/setToWareHouse/{id}")
    @Transactional
    public AjaxResult setToWareHouse(@PathVariable int id) {
        Accountsinfo fromUser = Blade.create(Accountsinfo.class).findById(id);
        if (fromUser == null) {
            return error("查无此人");
        }
        fromUser.setIswarehouse(1);
        boolean update = Blade.create(Accountsinfo.class).update(fromUser);
        if (update) {
			doLogByMessage(fromUser.getUserid().toString(),UserRightType.UPDATE_WAREHOUSE.code(),"","");
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
    }

    // 取消仓库号
    @Json
    @RequestMapping("/cancelToWareHouse/{id}")
    @Transactional
    public AjaxResult cancelToWareHouse(@PathVariable int id) {
    	Accountsinfo fromUser = Blade.create(Accountsinfo.class).findById(id);
    	if (fromUser == null) {
    		return error("查无此人");
    	}
    	fromUser.setIswarehouse(0);
    	boolean update = Blade.create(Accountsinfo.class).update(fromUser);
    	if (update) {
    		doLogByMessage(fromUser.getUserid().toString(),UserRightType.UPDATE_WAREHOUSE_CANCEL.code(),"","");
    		return success(UPDATE_SUCCESS_MSG);
    	} else {
    		return error(UPDATE_FAIL_MSG);
    	}
    }
}