package com.smallchill.system.meta.intercept;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.smallchill.common.tool.SysCache;
import com.smallchill.core.aop.AopContext;
import com.smallchill.core.base.controller.BladeController;
import com.smallchill.core.constant.ConstCache;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.meta.MetaIntercept;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.grid.BladePage;
import com.smallchill.system.model.Parameter;

public class ParameterIntercept extends MetaIntercept {

	/**
	 * 列表转向前操作 只有超管才能访问参数管理
	 * 
	 * @param ac
	 */
	public void renderIndexBefore(AopContext ac) {
		if(ShiroKit.lacksRole(ConstShiro.ADMINISTRATOR)){
			ModelAndView view = ac.getView();
			view.setViewName("redirect:/unauth");
		}
	}
	
	/**
	 * 查询后操作
	 * 
	 * @param ac
	 */
	@SuppressWarnings("unchecked")
	public void queryAfter(AopContext ac) {
		BladePage<Map<String, Object>> page = (BladePage<Map<String, Object>>) ac.getObject();
		List<Map<String, Object>> list = page.getRows();
		for (Map<String, Object> map : list) {
			map.put("statusname", SysCache.getDictName(901, map.get("status")));
		}
	}
	
	/**
	 * 主表新增前操作
	 * 
	 * @param ac
	 */
	public void saveBefore(AopContext ac) {
		BladeController ctrl = ac.getCtrl();
		String code = ctrl.getParameter("blade_parameter.code");
		int cnt = Blade.create(Parameter.class).count("code = #{code}", CMap.init().set("code", code));
		if(cnt > 0){
			throw new RuntimeException("参数编号已存在!");
		}
	}
	
	/**
	 * 主表新增后操作(事务内)
	 * 
	 * @param ac
	 */
	public boolean saveAfter(AopContext ac) {
		CacheKit.remove(ConstCache.SYS_CACHE, "parameter_log");
		return super.saveAfter(ac);
	}
	
	/**
	 * 主表修改后操作(事务内)
	 * 
	 * @param ac
	 */
	public boolean updateAfter(AopContext ac) {
		CacheKit.remove(ConstCache.SYS_CACHE, "parameter_log");
		return super.updateAfter(ac);
	}
	
	/**
	 * 删除全部完毕后操作(事务外)
	 * 
	 * @param ac
	 */
	public AjaxResult removeSucceed(AopContext ac) {
		CacheKit.remove(ConstCache.SYS_CACHE, "parameter_log");
		return super.removeSucceed(ac);
	}
	
	/**
	 * 逻辑删除后操作(事务外)
	 * 
	 * @param ac
	 */
	public AjaxResult delSucceed(AopContext ac) {
		CacheKit.remove(ConstCache.SYS_CACHE, "parameter_log");
		return super.delSucceed(ac);
	}
	
	/**
	 * 还原全部完毕后操作(事务外)
	 * 
	 * @param ac
	 */
	public AjaxResult restoreSucceed(AopContext ac) {
		CacheKit.remove(ConstCache.SYS_CACHE, "parameter_log");
		return super.restoreSucceed(ac);
	}
	
}
