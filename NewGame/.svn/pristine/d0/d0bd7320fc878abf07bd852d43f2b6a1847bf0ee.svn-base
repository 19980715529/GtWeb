package com.smallchill.core.toolbox.cache;

import java.util.ArrayList;
import java.util.List;

import com.smallchill.core.plugins.dao.Redis;
import com.smallchill.core.toolbox.redis.IJedis;

/**
 * Redis缓存工厂
 */
public class RedisCacheFactory extends BaseCacheFactory {
	
	private IJedis jedis;
	
	private String redisName;
	
	public String getRedisName() {
		return redisName;
	}

	public void setRedisName(String redisName) {
		this.redisName = redisName;
	}
	
	private IJedis getCacheManager() {
		if (jedis == null) {
			synchronized (RedisCacheFactory.class) {
				if (jedis == null) {
					jedis = Redis.init(getRedisName());
				}
			}
		}
		return jedis;
	}
	
	public RedisCacheFactory() {
		this.redisName = "cache";
	}
	
	public RedisCacheFactory(String redisName) {
		this.redisName = redisName;
	}
	
	public void put(String cacheName, Object key, Object value) {
		getCacheManager().hset(cacheName, key, value);
	}

	public <T> T get(String cacheName, Object key) {
		return getCacheManager().hget(cacheName, key);
	}

	@SuppressWarnings("rawtypes")
	public List getKeys(String cacheName) {
		return new ArrayList<>(getCacheManager().hkeys(cacheName));
	}

	public void remove(String cacheName, Object key) {
		getCacheManager().hdel(cacheName, key);
	}

	public void removeAll(String cacheName) {
		getCacheManager().del(cacheName);
	}

}
