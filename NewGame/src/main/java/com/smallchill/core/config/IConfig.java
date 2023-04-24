package com.smallchill.core.config;

import com.smallchill.core.constant.Cst;
import com.smallchill.core.plugins.IPluginHolder;

/**
 *  Blade配置型接口
 */
public interface IConfig {
	
	/**   
	 * 插件注册
	 * @param plugins 插件集合
	*/
	void registerPlugins(IPluginHolder plugins);	
	
	/**   
	 * 全局变量设置
	*/
	void globalConstants(Cst me);
	
	/**   
	 * 全局设置
	*/
	void globalSettings();
	
	/**   
	 * 程序启动之后执行
	*/
	void afterBladeStart();
	
	/**   
	 * 程序关闭之后执行
	*/
	void afterBladeStop();
	
}
