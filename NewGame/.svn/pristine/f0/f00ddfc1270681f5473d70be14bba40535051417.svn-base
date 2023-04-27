package com.smallchill.game.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.elasticsearch.search.SearchHits;

import com.smallchill.core.toolbox.grid.BladePage;
import com.smallchill.game.model.request.AaZzLogPropchangeRequest;
import com.smallchill.game.newmodel.Gameroomitem;
import com.smallchill.game.newmodel.result.WinLoseStatistics;

public interface PlayerElasticService  {
	/**
	* @Title: searchGoldChangeLog 
	* @Description: search user's gold change log 
	* @param @param request
	* @param @return
	* @param @throws IOException
	* @return SearchHits 
	* @throws 
	 */
	public  SearchHits searchGoldChangeLog(AaZzLogPropchangeRequest request) throws IOException;
	/**
	* @Title: searchGoldChangeLogByPage 
	* @Description: search user's glod change log by page
	* @param @param request
	* @param @return
	* @param @throws IOException
	* @return BladePage<Map> 
	* @throws 
	 */
	public  BladePage<Map> searchGoldChangeLogByPage(AaZzLogPropchangeRequest request) throws IOException;
	/**
	* @Title: searchGoldChangeLogByRoom 
	* @Description: statistic room score
	* @param @param request
	* @param @return
	* @param @throws IOException
	* @return List<Gameroomitem> 
	* @throws 
	 */
	public  List<Gameroomitem> searchGoldChangeLogByRoom(AaZzLogPropchangeRequest request) throws IOException;

	public  List<WinLoseStatistics> searchWinLoseStatistics(AaZzLogPropchangeRequest request) throws IOException;
	/**
	* @Title: searchGoldChangeLogByList 
	* @Description: search chart log
	* @param @param request
	* @param @return
	* @param @throws IOException
	* @return List<Map> 
	* @throws 
	 */
	public  List<Map> searchGoldChangeLogByList(AaZzLogPropchangeRequest request) throws IOException;
}
