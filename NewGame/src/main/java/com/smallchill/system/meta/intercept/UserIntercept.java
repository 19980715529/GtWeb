package com.smallchill.system.meta.intercept;

import java.util.List;
import java.util.Map;

import com.smallchill.common.tool.SysCache;
import com.smallchill.core.aop.AopContext;
import com.smallchill.core.meta.PageIntercept;
import com.smallchill.core.toolbox.grid.BladePage;

public class UserIntercept extends PageIntercept {

	/**
	 * 查询后操作 字典项、部门不通过数据库查询,通过缓存附加,减轻数据库压力,提高分页效率
	 * 
	 * @param ac
	 */
	@SuppressWarnings("unchecked")
	public void queryAfter(AopContext ac) {
		BladePage<Map<String, Object>> page = (BladePage<Map<String, Object>>) ac.getObject();
		List<Map<String, Object>> list = page.getRows();
		for (Map<String, Object> map : list) {
			map.put("rolename", SysCache.getRoleName(map.get("roleid")));
			map.put("statusname", SysCache.getDictName(901, map.get("status")));
			map.put("sexname", SysCache.getDictName(101, map.get("sex")));
			map.put("deptname", SysCache.getDeptName(map.get("deptid")));
		}
	}
}
