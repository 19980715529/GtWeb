package com.smallchill.core.intercept;

import com.smallchill.core.meta.IQuery;
import com.smallchill.core.meta.ISelect;

/**
 * select查询拦截器工厂类
 */
public class SelectInterceptor implements ISelect{
	
	@Override
	public IQuery userIntercept() {
		return new QueryInterceptor();
	}

	@Override
	public IQuery deptIntercept() {
		return new QueryInterceptor();
	}

	@Override
	public IQuery dictIntercept() {
		return new QueryInterceptor();
	}

	@Override
	public IQuery roleIntercept() {
		return new QueryInterceptor();
	}
	
}
