package com.smallchill.core.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import com.smallchill.core.config.BladeConfig;
import com.smallchill.core.plugins.PluginManager;

/**
 * 关闭监听器
 */
@Component
public class StopListener implements ApplicationListener<ContextClosedEvent> {

	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			destroyPlugin();
			afterBladeStop();
		}
	}

	/**
	 * 插件的停用
	 */
	private void destroyPlugin() {
		PluginManager.init().stop();
	}
	
	/**   
	 * 系统关闭后执行
	*/
	private void afterBladeStop(){
		BladeConfig.getConf().afterBladeStop();
	}

}