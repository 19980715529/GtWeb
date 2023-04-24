package com.smallchill.game.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.plugins.dao.Md;
import com.smallchill.game.service.CommonService;

@Service
public class CommonServiceImpl implements CommonService {

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> getInfoList(String source, Object paras) {
		String sqlTemplate = Md.getSql(source);
		List<Map> list = Db.selectList(sqlTemplate,paras);
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> getInfoListByDbname(String dbName,String source, Object paras) {
		String sqlTemplate = null;
		List<Map> list = null;
		if(dbName!=null) {
			 sqlTemplate = Md.init(dbName).getSql(source);
			 list = Db.init(dbName).selectList(sqlTemplate,paras);
		}else {
			sqlTemplate = Md.getSql(source);
			list = Db.selectList(sqlTemplate,paras);
		}
		return list;
	}

	@Override
	public List<String> queryList(String source, Object paras) {
		String sqlTemplate = Md.getSql(source);
		List<String> list = Db.queryListStr(sqlTemplate, paras);
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map getInfoByOne(String source, Object paras) {
		String sqlTemplate = Md.getSql(source);
		Map selectOne = Db.selectOne(sqlTemplate, paras);
		return selectOne;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map getInfoByOneDbName(String dbName,String source, Object paras) {
		String sqlTemplate = null;
		Map selectOne = null;
		if(dbName!=null) {
			 sqlTemplate = Md.init(dbName).getSql(source);
			 selectOne = Db.init(dbName).selectOne(sqlTemplate, paras);
		}else {
			sqlTemplate = Md.getSql(source);
			selectOne = Db.selectOne(sqlTemplate, paras);
		}
		return selectOne;
	}

}
