package com.smallchill.core.constant;

import java.util.Map;

import com.smallchill.core.listener.ConfigListener;

public interface ConstConfig {

	Map<String, String> pool = ConfigListener.getConf();
	
	String DBTYPE = pool.get("master.dbType");
	String DRIVER = pool.get("master.driver");
	String URL = pool.get("master.url");
	String USERNAME = pool.get("master.username");
	String PASSWORD = pool.get("master.password");
	String INITIALSIZE = pool.get("druid.initialSize");
	String MAXACTIVE = pool.get("druid.maxActive");
	String MINIDLE = pool.get("druid.minIdle");
	String MAXWAIT = pool.get("druid.maxWait");

	String REAL_PATH = pool.get("realPath");
	String CONTEXT_PATH = pool.get("contextPath");
	String DOMAIN = pool.get("config.domain");
	String REMOTE_MODE = pool.get("config.remoteMode");
	String REMOTE_PATH = pool.get("config.remotePath");
	String UPLOAD_PATH = pool.get("config.uploadPath");
	String DOWNLOAD_PATH = pool.get("config.downloadPath");
	String COMPRESS = pool.get("config.compress");
	String COMPRESS_SCALE = pool.get("config.compressScale");
	String COMPRESS_FLAG = pool.get("config.compressFlag");
	String STATICC_IP = pool.get("config.staticIP");
	String CHECK_SIGN = pool.get("config.checkSign");
	String RECHARGE_TEST = pool.get("config.rechargeTest");
	String PERMIT_DAY = pool.get("config.permitday");
	
	String SYS_TITLE = pool.get("config.title");
	String SYS_ISBOLE = pool.get("config.isBole");
	String SYS_PLATFORM = pool.get("config.platform");
	String SYS_ISBACKTAX = pool.get("config.isBackTax");
	String SYS_ISONSEARCH = pool.get("config.isSearchOn");
	String SYS_GAMES = pool.get("config.games");
	

	// elastic配置
	String ELASTIC_URL = pool.get("config.elastic.url");
	String ELASTIC_PORT = pool.get("config.elastic.port");
	String ELASTIC_POTOCOL = pool.get("config.elastic.protocol");
	String ELASTIC_INDICES = pool.get("config.elastic.indices");
	String ELASTIC_SEARCH_URL = pool.get("config.elastic.search");
	
	// 短信配置
	String SMS_URL = pool.get("sms.url");
	String SMS_USERID = pool.get("sms.userid");
	String SMS_ACCOUNT = pool.get("sms.account");
	String SMS_PASSWORD = pool.get("sms.password");

	// 谷歌配置
	String GOOGLE_URL = pool.get("google.url");
	String VALIDATE_BY = pool.get("config.validateBy");
	// 获取游戏服务地址
	String GAME_URL = pool.get("game.url");
	String CALLBACK_IP = pool.get("callback.ip");
	String META_Pay_URL = pool.get("MetaPay.url");

}
