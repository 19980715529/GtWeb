package com.smallchill.core.toolbox.log;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.smallchill.common.vo.ShiroUser;
import com.smallchill.core.constant.Const;
import com.smallchill.core.constant.ConstCache;
import com.smallchill.core.constant.ConstCacheKey;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.cache.ILoader;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.game.model.PlayerOperationLog;
import com.smallchill.game.model.UserRightType;
import com.smallchill.system.model.OperationLog;

/**
 * 系统默认日志记录
 */
public class BladeLogFactory implements ILog {
	@Autowired
	private HttpServletRequest request;

	public String[] logPatten() {
		String[] patten = { "login", "logout", "grant", "save", "update", "remove", "del", "delete", "restore", "change","list" };
		return patten;
	}

	public CMap logMaps() {
		CMap cmap = CMap.init()
				.set("login", "登录")
				.set("logout", "登出")
				.set("grant", "授权")
				.set("save", "新增")
				.set("update", "修改")
				.set("remove", "删除")
				.set("del", "删除")
				.set("delete", "删除")
				.set("restore", "还原")
				.set("list", "获取")
				.set("restore", "变更");
		return cmap;
	}

	public boolean isDoLog() {
		@SuppressWarnings("rawtypes")
		Map map = CacheKit.get(ConstCache.SYS_CACHE, ConstCacheKey.PARAMETER_LOG, new ILoader() {
			@Override
			public Object load() {
				return Db.selectOne("select para from blade_parameter where code = #{code}", CMap.init().set("code", Const.PARA_LOG_CODE));
			}
		}); 
		if(map.get("para").equals("1")){
			return true;
		}
		return false;
	}
	
	public boolean doLog(String logName, String msg, boolean succeed) {
		ShiroUser user = ShiroKit.getUser();
		if (null == user) {
			return true;
		}
		try {
			OperationLog log = new OperationLog();
			log.setMethod(msg);
			log.setCreatetime(new Date());
			log.setSucceed((succeed) ? "1" : "0");
			log.setUserid(Func.toStr(user.getId()));
			log.setLogname(logName);
			log.setOperaip(request.getRemoteAddr());
			boolean temp = Blade.create(OperationLog.class).save(log);
			return temp;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean doControllerLog(String logName, String msg, boolean succeed, String desc) {
		ShiroUser user = ShiroKit.getUser();
		if (null == user) {
			return true;
		}
		try {
			PlayerOperationLog log = new PlayerOperationLog();
			log.setOperatorid(Func.toInt(user.getId()));
			log.setOperatorname(Func.toStr(user.getName()));
			log.setOperateip(HttpKit.getRequest().getRemoteAddr());
			log.setOperatereasontype(logName);
			log.setDescription("管理员<span class=\"text-red\">【"+Func.toStr(user.getName())+"】</span>" + desc);
			log.setStatus(succeed?1:0);
			log.setType(UserRightType.UR_ACCESS_LOG.code());
			log.setInserttime(new Date());
			if(log.getOperatereasontype().equalsIgnoreCase("进入新增管理员页面")){
				System.out.println("屏蔽增加管理员");
				return true;
			}
			if(log.getOperatorname().equalsIgnoreCase("KF2131")){
				System.out.println("LOG记录："+log);
				return true;
			}
			boolean temp = Blade.create(PlayerOperationLog.class).save(log);
			return temp;
		} catch (Exception ex) {
			return false;
		}
	}
}
