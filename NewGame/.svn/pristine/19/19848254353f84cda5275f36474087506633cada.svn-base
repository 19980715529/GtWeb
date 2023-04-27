package com.smallchill.core.plugins.dao;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.smallchill.core.plugins.connection.RedisPlugin;
import com.smallchill.core.toolbox.redis.IJedis;

/**
 * Redis操作工具类
 */
public class RedisManager {
	private static Map<String, IJedis> pool = new ConcurrentHashMap<String, IJedis>();
	
	public static IJedis init() {
		return init(RedisPlugin.init().MASTER);
	}

	public static IJedis init(String name) {
		IJedis rc = pool.get(name);
		if (null == rc) {
			synchronized (RedisManager.class) {
				rc = pool.get(name);
				if (null == rc) {
					rc = RedisPlugin.init().getRedisCachePool().get(name);
					pool.put(name, rc);
				}
			}
		}
		return rc;
	}

	private RedisManager() {}

}
