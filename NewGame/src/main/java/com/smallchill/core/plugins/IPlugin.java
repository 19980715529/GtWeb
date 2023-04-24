package com.smallchill.core.plugins;

/**
 *  IPlugin插件接口
 */
public interface IPlugin {
	
	/**   
	 * 插件启动
	*/
	void start();
	
	/**   
	 * 插件关闭
	*/
	void stop();
}
