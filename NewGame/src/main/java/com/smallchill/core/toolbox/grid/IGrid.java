package com.smallchill.core.toolbox.grid;

import com.smallchill.core.base.controller.BladeController;
import com.smallchill.core.meta.IQuery;

/**
 *  定义Grid分页接口
 */
public interface IGrid {
	/**
	 * 封装grid返回数据类型
	 * 
	 * @param dbName
	 *            数据库别名
	 * @param page
	 *            当前页号
	 * @param rows
	 *            每页的数量
	 * @param source
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
	 * @return String
	 */
	Object paginate(String dbName, Integer page, Integer rows, String source, String para, String sort, String order, IQuery intercept, BladeController ctrl);

	Object paginateBySelf(String dbName, Integer page, Integer rows, String source, String para, String sort, String order, IQuery intercept, BladeController ctrl);
}
