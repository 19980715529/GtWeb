package com.smallchill.core.listener;

import com.smallchill.core.config.BladeConfig;
import com.smallchill.core.constant.Cst;
import com.smallchill.core.plugins.IPluginHolder;
import com.smallchill.core.plugins.PluginFactory;
import com.smallchill.core.plugins.PluginManager;
import com.smallchill.core.plugins.connection.LogoPlugin;
import com.smallchill.core.plugins.connection.RedisPlugin;
import com.smallchill.core.plugins.connection.SQLManagerPlugin;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 启动监听器
 */
@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			globalConstants(Cst.me());
			registerPlugins();
			globalSettings();
			afterBladeStart();
		}
	}
	
	/**   
	 * 全局配置
	*/
	private void globalConstants(Cst me){
		BladeConfig.getConf().globalConstants(me);
	}

	/**
	 * 插件的启用
	 */
	private void registerPlugins() {
		IPluginHolder plugins = PluginFactory.init();
		plugins.register(SQLManagerPlugin.init());
		plugins.register(RedisPlugin.init());
		plugins.register(new LogoPlugin());
		BladeConfig.getConf().registerPlugins(plugins);//自定义配置插件	
		PluginManager.init().start();
	}
	
	/**   
	 * 全局配置
	*/
	private void globalSettings(){
		BladeConfig.getConf().globalSettings();
	}
	
	/**   
	 * 系统启动后执行
	*/
	private void afterBladeStart(){
		BladeConfig.getConf().afterBladeStart();
	}
	
}