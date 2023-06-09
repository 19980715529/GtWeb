package com.smallchill.system.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.aop.AopContext;
import com.smallchill.core.constant.Cst;
import com.smallchill.core.meta.IQuery;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.plugins.dao.Md;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.cache.ILoader;
import com.smallchill.core.toolbox.kit.ClassKit;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.core.toolbox.kit.StrKit;

@Controller
@RequestMapping("/combotree")
public class ComboTreeController extends BaseController{

	@Json
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getTreeList")
	public AjaxResult getTreeList(@RequestParam String type, @RequestParam String source, @RequestParam String where, @RequestParam String intercept, @RequestParam String ext, @RequestParam String val, @RequestParam String treeId) {	
		type = getTypeName(type, source);
		String sqlSource = getSql(type, source);
		
		Map<String, Object> params = CMap.createHashMap();
		if(!where.equals("0") && StrKit.notBlank(where)){
			params = JsonKit.parse(where, Map.class);
		}
		
		Map<String, Object> modelOrMap = params;
		
		IQuery _intercept = getIntercept(type);
		if(StrKit.notBlank(intercept) && !Func.equals(intercept, "0")){
			_intercept = ClassKit.newInstance(intercept);
		}
		
		final AopContext ac = new AopContext();
		ac.setObject(ext);
		ac.setTips("ztree");
		
		String search = getParameter("search", "");
		if (StrKit.notBlank(search)) {
			sqlSource = "select * from (" + sqlSource + ") search where name like CONCAT(CONCAT('%', #{search}),'%')";
			modelOrMap.put("search", search);
		}
		
		final String f_sqlSource = sqlSource;
		final Map<String, Object> f_modelOrMap = modelOrMap;
		final IQuery f_intercept = _intercept;
		List<Map> list = CacheKit.get(SYS_CACHE, GET_COMBOTREE + type + val + source + where + intercept + ext + treeId + search, new ILoader() {
			public Object load() {
				return Db.selectList(f_sqlSource, f_modelOrMap, ac, f_intercept);
			}
		});

		String key = (StrKit.notBlank(treeId) && !Func.equals(treeId, "0")) ? treeId : ((type.indexOf("dict") >= 0) ? "num" : "id");

		String [] arr = val.split(",");
		for(Map<String, Object> map : list){
			for(String v : arr){
				if(Func.toStr(map.get(key)).equals(v)){
					map.put("checked", "true");
				}
			}
		}
		
		return json(list);
	}
	
	@Json
	@RequestMapping("/getTreeListName")
	@SuppressWarnings("unchecked")
	public AjaxResult getTreeListName(@RequestParam String type, @RequestParam String source, @RequestParam String where, @RequestParam String val, @RequestParam String treeId){
		
		type = getTypeName(type, source);
		
		final String sqlSource = getSql(type, source);
		
		Map<String, Object> params = CMap.createHashMap();
		if(StrKit.notBlank(where)){
			params = JsonKit.parse(where, Map.class);
		}
		
		final Map<String, Object> modelOrMap = params;
		
		List<Map<String, Object>> list = CacheKit.get(SYS_CACHE, DICT_ZTREE_LIST + type,
				new ILoader() {
					public Object load() {
						return Db.selectList(sqlSource, modelOrMap);
					}
				});
		
		String name = "";
		
		String key = (StrKit.notBlank(treeId) && !Func.equals(treeId, "0")) ? treeId : ((type.contains("dict")) ? "num" : "id");

		String [] arr = val.split(",");
		for(Map<String, Object> map : list){
			for(String v : arr){
				if(Func.toStr(map.get(key)).equals(v)){
					name += Func.toStr(map.get("name")) + ",";
				}
			}
		}

		name = StrKit.removeSuffix(name, ",");
		
		return json(name);
	}
	
	private String getTypeName(String type, String source){
		if(type.contains("combotreeUser")){
			type = "user";
		} else if(type.contains("combotreeDept")){
			type = "dept";
		} else if(type.contains("combotreeRole")){
			type = "role";
		} else if(type.contains("combotree_") || type.contains("combotreeDict")){
			type = "dict_" + type.replace("combotree_", "").replace("combotreeDict", "");
		} else {
			type = "diy_" + source;
		}
		return type;
	}
	
	private String getSql(String type, String source){
		String sql = "";
		if (type.contains("dict_")) {
			String code = type.replace("dict_", "");
			sql = "select NUM as \"num\",ID as \"id\",PID as \"pId\",NAME as \"name\",(case when (pId=0 or pId is null) then 'true' else 'false' end) \"open\" from  BLADE_DICT where code= '" + code + "'";;
		} else if (type.equals("user")) {
			sql = "select ID as \"id\",0 as \"pId\",NAME as \"name\",'true' as \"open\" from  BLADE_USER where status=1";
		} else if (type.equals("dept")) {
			sql = "select ID as \"id\",PID as \"pId\",SIMPLENAME as \"name\",(case when (pId=0 or pId is null) then 'true' else 'false' end) \"open\" from  BLADE_DEPT";
		} else if (type.equals("role")) {
			sql = "select ID as \"id\",PID as \"pId\",NAME as \"name\",(case when (pId=0 or pId is null) then 'true' else 'false' end) \"open\" from  BLADE_ROLE";
		} else {
			sql = Md.getSql(source);
		}
		return sql;
	}
	
	private IQuery getIntercept(String type) {
		IQuery intercept = Cst.me().getDefaultQueryFactory();
		if (type.contains("dict")) {
			intercept = Cst.me().getDefaultSelectFactory().dictIntercept();
		} else if (type.equals("user")) {
			intercept = Cst.me().getDefaultSelectFactory().userIntercept();
		} else if (type.equals("dept")) {
			intercept = Cst.me().getDefaultSelectFactory().deptIntercept();
		} else if (type.equals("role")) {
			intercept = Cst.me().getDefaultSelectFactory().roleIntercept();
		}  
		return intercept;
	}
	
}
