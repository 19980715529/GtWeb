package com.smallchill.core.toolbox.log;

import com.smallchill.core.constant.Cst;
import com.smallchill.core.toolbox.CMap;

/**
 * 日志工厂
 */
public class BladeLogManager {
	private final static BladeLogManager me = new BladeLogManager();

	private ILog defaultLogFactory = Cst.me().getDefaultLogFactory();

	public static BladeLogManager me() {
		return me;
	}

	private BladeLogManager() {
	}

	public BladeLogManager(ILog checkFactory) {
		this.defaultLogFactory = checkFactory;
	}

	public ILog getDefaultLogFactory() {
		return defaultLogFactory;
	}

	public void setDefaultLogFactory(ILog defaultLogFactory) {
		this.defaultLogFactory = defaultLogFactory;
	}

	public static String[] logPatten() {
		return me.defaultLogFactory.logPatten();
	}

	public static CMap logMaps() {
		return me.defaultLogFactory.logMaps();
	}

	public static boolean isDoLog() {
		return me.defaultLogFactory.isDoLog();
	}

	public static void doLog(String logName, String msg, boolean succeed) {
		if (isDoLog())
			me.defaultLogFactory.doLog(logName, msg, succeed);
	}

	public static void doControllerLog(String logName, String msg, boolean succeed, String desc) {
		if (isDoLog())
			me.defaultLogFactory.doControllerLog(logName, msg, succeed,desc);
	}
}
