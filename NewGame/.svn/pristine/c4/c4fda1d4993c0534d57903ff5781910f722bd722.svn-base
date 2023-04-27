package com.smallchill.core.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.smallchill.core.constant.Const;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.kit.ObjectKit;
import com.smallchill.core.toolbox.kit.PropKit;

public class ConfigListener implements ServletContextListener {

	private static Map<String, String> conf = new HashMap<String, String>();

	public static Map<String, String> getConf() {
		return ObjectKit.clone(conf);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		conf.clear();
	}

	@Override
	public void contextInitialized(ServletContextEvent evt) {
		ServletContext sc = evt.getServletContext();
		// 项目路径
		conf.put("realPath", sc.getRealPath("/"));
		conf.put("contextPath", sc.getContextPath());

		Properties prop = PropKit.use(Const.PROPERTY_FILE).getProperties();
		for (Object name : prop.keySet()) {
			conf.put(name.toString(), Func.toStr(prop.get(name)));
		}
	}

}
