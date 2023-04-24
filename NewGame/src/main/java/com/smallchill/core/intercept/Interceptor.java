package com.smallchill.core.intercept;

import com.smallchill.core.aop.Invocation;

/**
 * 自定义拦截器接口
 */
public interface Interceptor {
	
	/**   
	 * 拦截逻辑
	 * @param inv
	 * @return Object
	*/
	Object intercept(Invocation inv);
}
