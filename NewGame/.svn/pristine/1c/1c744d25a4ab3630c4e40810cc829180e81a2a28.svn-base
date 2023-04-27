package com.smallchill.core.plugins;

import java.util.ArrayList;
import java.util.List;

public class PluginFactory implements IPluginHolder {
	private static List<IPlugin> plugins = new ArrayList<>();
	
	private static PluginFactory me = new PluginFactory();
	
	public static PluginFactory init(){
		return me;
	}
	
	private PluginFactory(){}
	
	public void register(IPlugin plugin) {
		plugins.add(plugin);
	}

	public List<IPlugin> getPlugins() {
		return plugins;
	}

}
