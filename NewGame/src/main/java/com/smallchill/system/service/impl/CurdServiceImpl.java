package com.smallchill.system.service.impl;

import org.springframework.stereotype.Service;

import com.smallchill.core.aop.AopContext;
import com.smallchill.core.base.controller.BladeController;
import com.smallchill.core.meta.MetaIntercept;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.grid.GridManager;
import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.system.service.CurdService;

@Service
public class CurdServiceImpl implements CurdService {

	public boolean save(BladeController ctrl, Object model, Class<?> modelClass,
			MetaIntercept intercept) {
		AopContext ac = null;
		if (null != intercept) {
			ac = new AopContext(ctrl, model);
			intercept.saveBefore(ac);
		}
		String rtid = Blade.create(modelClass).saveRtStrId(model);
		boolean tempAfter = true;
		if (null != intercept && rtid.length() > 0) {
			ac.setId(rtid);
			tempAfter = intercept.saveAfter(ac);
		}
		return (rtid.length() > 0 && tempAfter);
	}

	public boolean update(BladeController ctrl, Object model,
			Class<?> modelClass, MetaIntercept intercept) {
		AopContext ac = null;
		if (null != intercept) {
			ac = new AopContext(ctrl, model);
			intercept.updateBefore(ac);
		}
		boolean temp = Blade.create(modelClass).update(model);
		boolean tempAfter = true;
		if (null != intercept && temp) {
			tempAfter = intercept.updateAfter(ac);
		}
		return (temp && tempAfter);
	}

	public boolean deleteByIds(BladeController ctrl, String ids,
			Class<?> modelClass, MetaIntercept intercept) {
		AopContext ac = null;
		if (null != intercept) {
			ac = new AopContext(ctrl);
			ac.setId(ids);
			intercept.removeBefore(ac);
		}
		int n = Blade.create(modelClass).deleteByIds(ids);
		boolean tempAfter = true;
		if (null != intercept && n > 0) {
			tempAfter = intercept.removeAfter(ac);
		}
		return (n > 0 && tempAfter);
	}

	public boolean delByIds(BladeController ctrl, String ids,
			Class<?> modelClass, MetaIntercept intercept) {
		AopContext ac = null;
		if (null != intercept) {
			ac = new AopContext(ctrl);
			ac.setId(ids);
			intercept.delBefore(ac);
		}
		boolean temp = Blade.create(modelClass).updateBy("status = #{status}", "id in (#{join(ids)})", CMap.init().set("status", 5).set("ids", Convert.toIntArray(ids)));
		boolean tempAfter = true;
		if (null != intercept && temp) {
			tempAfter = intercept.delAfter(ac);
		}
		return (temp && tempAfter);
	}

	public boolean restoreByIds(BladeController ctrl, String ids,
			Class<?> modelClass, MetaIntercept intercept) {
		AopContext ac = null;
		if (null != intercept) {
			ac = new AopContext(ctrl);
			ac.setId(ids);
			intercept.restoreBefore(ac);
		}
		boolean temp = Blade.create(modelClass).updateBy("status = #{status}", "id in (#{join(ids)})", CMap.init().set("status", 1).set("ids", Convert.toIntArray(ids)));
		boolean tempAfter = true;
		if (null != intercept && temp) {
			tempAfter = intercept.restoreAfter(ac);
		}
		return (temp && tempAfter);
	}

	public Object paginate(Integer page, Integer rows, String source,
			String para, String sort, String order, MetaIntercept intercept,
			BladeController ctrl) {
		Object list = GridManager.paginate(null, page, rows, source, para, sort, order, intercept, ctrl);
		return list;
	}

}
