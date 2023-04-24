package com.smallchill.core.meta;

import java.util.Map;

import com.smallchill.core.meta.MetaIntercept;

/**
 *  定义CURD元数据接口
 */
public interface IMeta {
	
	/**
	 * 对应的业务拦截器
	 * 
	 * @return String
	 */
	Class<? extends MetaIntercept> intercept();
	
	/**
	 * 对应的controllerKey
	 * 
	 * @return String
	 */
	String controllerKey();
	
	/**
	 * 前端字段的表名前缀
	 * 
	 * @return String
	 */
	String paraPrefix();

	/**
	 * 前端字段混淆<br>
	 * map.put("前端字段","数据库字段");
	 * 
	 * @return Map<String,Object>
	 */
	Map<String, Object> switchMap();

	/**
	 * 增改查页面转向<br>
	 * Map<String, String> renderMap = new HashMap<>();<br>
	 * renderMap.put(ConstCurd.KEY_INDEX, "/demo/demo.html");<br>
	 * renderMap.put(ConstCurd.KEY_ADD, "/demo/demo_add.html");<br>
	 * renderMap.put(ConstCurd.KEY_EDIT, "/demo/demo_edit.html");<br>
	 * renderMap.put(ConstCurd.KEY_VIEW, "/demo/demo_view.html");<br>
	 * 
	 * @return Map<String,String>
	 */
	Map<String, String> renderMap();

	/**
	 * 列表页的数据源<br>
	 * Map<String, String> sourceMap = new HashMap<>();<br>
	 * sourceMap.put(ConstCurd.KEY_INDEX,"DemoMapper.list");<br>
	 * sourceMap.put(ConstCurd.KEY_EDIT, "DemoMapper.edit");<br>
	 * sourceMap.put(ConstCurd.KEY_VIEW, "DemoMapper.view");<br>
	 * 
	 * @return Map<String,String>
	 */
	Map<String, String> sourceMap();

}
