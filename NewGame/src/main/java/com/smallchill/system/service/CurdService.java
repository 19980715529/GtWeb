package com.smallchill.system.service;

import com.smallchill.core.base.controller.BladeController;
import com.smallchill.core.meta.MetaIntercept;

public interface CurdService {
	/**
	 * 通用新增
	 * 
	 * @param model
	 *            实体类
	 * @return boolean
	 */
	public boolean save(BladeController ctrl, Object model, Class<?> modelClass, MetaIntercept intercept);

	/**
	 * 通用修改(null的不入库)
	 * 
	 * @param model
	 *            实体类
	 * @return boolean
	 */
	public boolean update(BladeController ctrl,Object model, Class<?> modelClass, MetaIntercept intercept);

	/**
	 * 通用删除多条数据(物理)
	 * 
	 * @param ids
	 *            主键值集合
	 * @return int 删除条数
	 */
	public boolean deleteByIds(BladeController ctrl,String ids, Class<?> modelClass, MetaIntercept intercept);
	
	/**
	 * 通用删除多条数据(逻辑)
	 * 
	 * @param ids
	 *            主键值集合
	 * @return int 删除条数
	 */
	public boolean delByIds(BladeController ctrl,String ids, Class<?> modelClass, MetaIntercept intercept);
	
	/**
	 * 通用恢复多条数据
	 * 
	 * @param ids
	 *            主键值集合
	 * @return int 删除条数
	 */
	public boolean restoreByIds(BladeController ctrl,String ids, Class<?> modelClass, MetaIntercept intercept);
	
	/**
	 * 通用分页
	 * 
	 * @param page
	 *            当前页号
	 * @param rows
	 *            每页的数量
	 * @param sql
	 *            数据源
	 * @param para
	 *            额外条件 {"id":1,"title":"test"}
	 * @param sort
	 *            排序列名 (id)
	 * @param order
	 *            排序方式 (desc)
	 * @param intercept
	 *            业务拦截器
	 * @param ctrl
	 *            控制器
	 * @return Object
	 */
	public Object paginate(Integer page, Integer rows, String source, String para, String sort, String order, MetaIntercept intercept, BladeController ctrl);
}
