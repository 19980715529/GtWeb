package com.smallchill.core.toolbox.log;

import com.smallchill.core.toolbox.CMap;

/**
 * 日志记录接口
 */
public interface ILog {

	/**
	 * 定义日志拦截的方法
	 */
	String[] logPatten();

	/**
	 * 定义日志拦截的方法名
	 */
	CMap logMaps();

	/**
	 * 是否需要记录日志
	 */
	boolean isDoLog();

	/**
	 * 日志记录
	 * 
	 * @param logName
	 *            日志名称
	 * @param msg
	 *            返回消息
	 * @return boolean
	 */
	boolean doLog(String logName, String msg, boolean succeed);

	/**
	 * @param logName
	 * @param msg
	 * @param succeed
	 * @param desc
	 * @return
	 */
	boolean doControllerLog(String logName, String msg, boolean succeed,
			String desc);
}
