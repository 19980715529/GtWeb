package com.smallchill.system.meta.intercept;

import java.util.List;
import java.util.Map;

import com.smallchill.common.tool.SysCache;
import com.smallchill.core.aop.AopContext;
import com.smallchill.core.constant.ConstConfig;
import com.smallchill.core.meta.MetaIntercept;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.grid.BladePage;

public class AttachIntercept extends MetaIntercept {
	
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
			map.put("attachurl", ConstConfig.DOMAIN + map.get("url"));
			map.put("statusname", SysCache.getDictName(902, map.get("status")));
			map.put("creatername", SysCache.getUserName(map.get("creater")));
		}
	}

	/**
	 * 查看转向前操作
	 * 
	 * @param ac
	 */
	public void renderViewBefore(AopContext ac) {
		CMap cmap = (CMap) ac.getObject();
		cmap.set("attachUrl", ConstConfig.DOMAIN + cmap.get("url"))
			.set("statusName", SysCache.getDictName(902, cmap.get("status")))
			.set("createrName", SysCache.getUserName(cmap.get("creater")));
	}
	
	/**
	 * 物理删除前操作(事务内)
	 * 
	 * @param ac
	 */
	public void removeBefore(AopContext ac) {
		/*Map<String, Object> file = Db.findById("BLADE_ATTACH", ac.getId().toString());
		if (Func.isEmpty(file)) {
			throw new RuntimeException("文件不存在!");
		} else {
			String url = file.get("URL").toString();
			File f = new File(Cst.me().getUploadRealPath() + url);
			if(null == f || !f.isFile()){
				throw new RuntimeException("文件不存在!");
			}
			f.delete();
		}*/
	}
	
}