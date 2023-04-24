package com.smallchill.core.toolbox.redis;

/**
 *  回调用接口
 */
public interface ICallBack {
	<T> T call(Object obj);
}
