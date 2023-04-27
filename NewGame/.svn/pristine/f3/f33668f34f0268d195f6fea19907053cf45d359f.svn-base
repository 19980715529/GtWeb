package com.smallchill.core.config;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.beetl.sql.core.SQLManager;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

/**
 * blade配置
 */
public class BladeConfig {

	private static Map<String, SQLManager> sqlManagerPool = new ConcurrentHashMap<String, SQLManager>();
	private static Map<String, JedisPool> jedisPool = new ConcurrentHashMap<String, JedisPool>();
	private static Map<String, JedisCluster> jedisCluster = new ConcurrentHashMap<String, JedisCluster>();
	
	private BladeConfig(){}
	
	private static IConfig conf = null;

	public static IConfig getConf() {
		if(null == conf){
			throw new RuntimeException("BladeConfig未注入,请在applicationContext.xml中定义bladeConfig!");
		}
		return conf;
	}

	public static Map<String, SQLManager> getSqlManagerPool(){
		if(null == sqlManagerPool){
			throw new RuntimeException("sqlManagerMap未注入,请在applicationContext.xml中定义sqlManagerMap!");
		}
		return sqlManagerPool;
	} 
	
	public static Map<String, JedisPool> getJedisPool(){
		return jedisPool;
	} 
	
	public static Map<String, JedisCluster> getJedisCluster(){
		return jedisCluster;
	} 
	
	/**
	 * 注入自定义config
	 * @param conf
	 */
	public void setConf(IConfig config) {
		conf = config;
	}
	
	/**
	 * 注入sqlManagerMap
	 * @param map
	 */
	public void setSqlManager(Map<String, SQLManager> map){
		sqlManagerPool.putAll(map);
	}
	
	/**
	 * 注入jedisPoolMap
	 * @param map
	 */
	public void setJedisPool(Map<String, JedisPool> map){
		jedisPool.putAll(map);
	}
	
	/**
	 * 注入jedisClusterMap
	 * @param map
	 */
	public void setJedisCluster(Map<String, JedisCluster> map){
		jedisCluster.putAll(map);
	}
	
}
