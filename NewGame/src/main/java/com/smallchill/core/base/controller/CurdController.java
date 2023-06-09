package com.smallchill.core.base.controller;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.aop.AopContext;
import com.smallchill.core.meta.IMeta;
import com.smallchill.core.meta.MetaIntercept;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.plugins.dao.Md;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.ClassKit;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.system.service.CurdService;

public abstract class CurdController<M> extends BaseController {
	
	@Autowired
	CurdService service;

	private final BladeController ctrl = this;
	/** 自定义拦截器 **/
	private MetaIntercept intercept = null;
	private Class<M> modelClass;
	private IMeta metaFactory;
	private String controllerKey;
	private String paraPrefix;
	private Map<String, Object> switchMap;
	private Map<String, String> renderMap;
	private Map<String, String> sourceMap;

	@SuppressWarnings("unchecked")
	private Class<M> getClazz() {
		Type t = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) t).getActualTypeArguments();
		return (Class<M>) params[0];
	}

	private void init() {
		this.modelClass = getClazz();
		this.metaFactory = ClassKit.newInstance(metaFactoryClass());
		this.controllerKey = metaFactory.controllerKey();
		this.paraPrefix = metaFactory.paraPrefix();
		this.switchMap = metaFactory.switchMap();
		this.renderMap = metaFactory.renderMap();
		this.sourceMap = metaFactory.sourceMap();
		this.intercept = ClassKit.newInstance(metaFactory.intercept());
	}

	public CurdController() {
		this.init();
	}

	protected abstract Class<? extends IMeta> metaFactoryClass();

	/**
	 * 前端字段混淆map翻转
	 * 
	 * @return Map<String,String>
	 */
	protected Map<String, Object> reverseMap() {
		if (Func.isEmpty(switchMap)) {
			return null;
		}
		Map<String, Object> map = new HashMap<>();
		for (String key : switchMap.keySet()) {
			map.put((String) switchMap.get(key), key);
		}
		return map;
	}
	@DoControllerLog(name="进入管理员操作日志列表页面")
	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView view = new ModelAndView(renderMap.get(KEY_INDEX));
		view.addObject("code", controllerKey);
		if (null != intercept) {
			AopContext ac = new AopContext(ctrl, view);
			intercept.renderIndexBefore(ac);
		}
		return view;
	}

	@RequestMapping(KEY_ADD)
	public ModelAndView add() {
		ModelAndView view = new ModelAndView(renderMap.get(KEY_ADD));
		view.addObject("code", controllerKey);
		if (null != intercept) {
			AopContext ac = new AopContext(ctrl, view);
			intercept.renderAddBefore(ac);
		}
		return view;
	}

	@RequestMapping(KEY_EDIT + "/{id}")
	public ModelAndView edit(@PathVariable Integer id) {
		ModelAndView view = new ModelAndView(renderMap.get(KEY_EDIT));
		CMap cmap = CMap.init();
		if (StrKit.isBlank(sourceMap.get(KEY_EDIT))) {
			M model = Blade.create(modelClass).findById(id);
			cmap.parseBean(model);
		} else {
			Map<String, Object> map = new HashMap<>();
			map.put("id", id);
			Map<String, Object> model = this.find(sourceMap.get(KEY_EDIT), map);
			cmap.parseMap(model);
		}
		if (null != intercept) {
			AopContext ac = new AopContext(ctrl, cmap, view);
			intercept.renderEditBefore(ac);
		}
		view.addObject("code", controllerKey);
		view.addObject("model", JsonKit.toJson(cmap));
		return view;
	}

	@RequestMapping(KEY_VIEW + "/{id}")
	public ModelAndView view(@PathVariable Integer id) {
		ModelAndView view = new ModelAndView(renderMap.get(KEY_VIEW));
		CMap cmap = CMap.init();
		if (StrKit.isBlank(sourceMap.get(KEY_VIEW))) {
			M model = Blade.create(modelClass).findById(id);
			cmap.parseBean(model);
		} else {
			Map<String, Object> map = new HashMap<>();
			map.put("id", id);
			Map<String, Object> model = this.find(sourceMap.get(KEY_VIEW), map);
			cmap.parseMap(model);
		}
		if (null != intercept) {
			AopContext ac = new AopContext(ctrl, cmap, view);
			intercept.renderViewBefore(ac);
		}
		view.addObject("code", controllerKey);
		view.addObject("model", JsonKit.toJson(cmap));
		return view;
	}

	@Json
	@RequestMapping(KEY_SAVE)
	public AjaxResult save() {
		M model = autoMapping();
		boolean temp = service.save(ctrl, model, modelClass, intercept);
		if (temp) {
			if (null != intercept) {
				AopContext ac = new AopContext(ctrl, model);
				AjaxResult result = intercept.saveSucceed(ac);
				return result;
			}
			return success(SAVE_SUCCESS_MSG);
		} else {
			return error(SAVE_FAIL_MSG);
		}
	}

	@Json
	@RequestMapping(KEY_UPDATE)
	public AjaxResult update() {
		M model = autoMapping();
		boolean temp = service.update(ctrl, model, modelClass, intercept);
		if (temp) {
			if (null != intercept) {
				AopContext ac = new AopContext(ctrl, model);
				AjaxResult result = intercept.updateSucceed(ac);
				return result;
			}
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}

	@Json
	@RequestMapping(KEY_REMOVE)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		boolean temp = service.deleteByIds(ctrl, ids, modelClass, intercept);
		if (temp) {
			if (null != intercept) {
				AopContext ac = new AopContext(ctrl);
				ac.setId(ids);
				AjaxResult result = intercept.removeSucceed(ac);
				return result;
			}
			return success(DEL_SUCCESS_MSG);
		} else {
			return error(DEL_FAIL_MSG);
		}
	}
	
	@Json
	@RequestMapping(KEY_DEL)
	public AjaxResult del() {
		String ids = getParameter("ids");
		boolean temp = service.delByIds(ctrl, ids, modelClass, intercept);
		if (temp) {
			if (null != intercept) {
				AopContext ac = new AopContext(ctrl);
				ac.setId(ids);
				AjaxResult result = intercept.delSucceed(ac);
				return result;
			}
			return success(DEL_SUCCESS_MSG);
		} else {
			return error(DEL_FAIL_MSG);
		}
	}
	
	@Json
	@RequestMapping(KEY_RESTORE)
	public AjaxResult restore() {
		String ids = getParameter("ids");
		boolean temp = service.restoreByIds(ctrl, ids, modelClass, intercept);
		if (temp) {
			if (null != intercept) {
				AopContext ac = new AopContext(ctrl);
				ac.setId(ids);
				AjaxResult result = intercept.restoreSucceed(ac);
				return result;
			}
			return success(RESTORE_SUCCESS_MSG);
		} else {
			return error(RESTORE_FAIL_MSG);
		}
	}

//	@SystemControllerLog(description = "获取操作日志记录")
	@Json
	@RequestMapping(KEY_LIST)
	public Object list() {
		Integer page = getParameterToInt("page", 1);
		Integer rows = getParameterToInt("rows", 10);
		String where = getParameter("where", StrKit.EMPTY);
		String sidx =  getParameter("sidx", StrKit.EMPTY);
		String sord =  getParameter("sord", StrKit.EMPTY);
		String sort =  getParameter("sort", StrKit.EMPTY);
		String order =  getParameter("order", StrKit.EMPTY);
		if (StrKit.notBlank(sidx)) {
			sort = sidx + " " + sord
					+ (StrKit.notBlank(sort) ? ("," + sort) : StrKit.EMPTY);
		}
		if (StrKit.isBlank(sourceMap.get(KEY_INDEX))) {
			throw new RuntimeException(modelClass.getName() + "没有配置数据源！");
		}
		Object grid = service.paginate(page, rows,
				sourceMap.get(KEY_INDEX), where, sort, order,
				intercept, ctrl);
		return grid;
	}

	/**
	 * 根据子类的paraPrefix,switchMap实现主表的自动映射
	 * 
	 * @return M
	 */
	protected M autoMapping() {
		if (Func.isAllEmpty(paraPrefix, switchMap)) {
			return mapping(modelClass);
		}else if (Func.isEmpty(switchMap) && !Func.isEmpty(paraPrefix)) {
			return mapping(paraPrefix, modelClass);
		} else {
			return null;
		}
	}

	private Map<String, Object> find(String source, Map<String, Object> map) {
		if (source.indexOf("select") == -1) {
			return findOneById(source, map);
		} else {
			return findOneBySql(source, map);
		}
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> findOneBySql(String sql, Map<String, Object> map) {
		Map<String, Object> model = Db.selectOne(sql, map);
		return Func.caseInsensitiveMap(model);
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> findOneById(String sqlId, Map<String, Object> map) {
		Map<String, Object> model =  Md.selectOne(sqlId, map, Map.class); //Db.selectOneBySqlId(sqlId, map);
		return Func.caseInsensitiveMap(model);
	}

}
