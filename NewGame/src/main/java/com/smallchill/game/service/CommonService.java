package com.smallchill.game.service;

import java.util.List;
import java.util.Map;

public interface CommonService {
	
	@SuppressWarnings("rawtypes")
	public List<Map> getInfoList(String source,Object paras);
	
	@SuppressWarnings("rawtypes")
	public Map getInfoByOne(String source,Object paras);

	List<String> queryList(String source, Object paras);
	
	@SuppressWarnings("rawtypes")
	public List<Map> getInfoListByDbname(String dbName,String source, Object paras);

	Map getInfoByOneDbName(String dbName, String source, Object paras);
}
