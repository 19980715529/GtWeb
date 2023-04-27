package com.smallchill.system.meta.intercept;

import java.util.List;
import java.util.Map;

import com.smallchill.common.tool.SysCache;
import com.smallchill.core.aop.AopContext;
import com.smallchill.core.constant.ConstCache;
import com.smallchill.core.meta.MetaIntercept;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.grid.BladePage;

public class LogIntercept extends MetaIntercept {
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
			String succeedName = (Func.toInt(map.get("succeed"), 1) == 1) ? "成功" : "失败";
			map.put("succeedname", succeedName);
			map.put("username", SysCache.getUserName(map.get("userid")));
		}
	}

	/**
	 * 查看转向前操作
	 * 
	 * @param ac
	 */
	public void renderViewBefore(AopContext ac) {
		CMap cmap = (CMap) ac.getObject();
		String succeedName = (cmap.getInt("succeed") == 1) ? "成功" : "失败";
		cmap.set("succeedName", succeedName).set("userName", SysCache.getUserName(cmap.get("userid")));
	}
	
	
	
	/**
	 * 主表新增后操作(事务内)
	 * 
	 * @param ac
	 */
	public boolean saveAfter(AopContext ac) {
		CacheKit.removeAll(ConstCache.SYS_CACHE);
		return true;
	}
	
	/**
	 * 主表修改后操作(事务内)
	 * 
	 * @param ac
	 */
	public boolean updateAfter(AopContext ac) {
		CacheKit.removeAll(ConstCache.SYS_CACHE);
		return true;
	}

	
	/**
	 * 物理删除后操作(事务内)
	 * 
	 * @param ac
	 */
	public boolean removeAfter(AopContext ac) {
		CacheKit.removeAll(ConstCache.SYS_CACHE);
		return true;
	}
	
}
