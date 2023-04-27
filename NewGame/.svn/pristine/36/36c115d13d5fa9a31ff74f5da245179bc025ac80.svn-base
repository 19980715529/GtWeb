package com.smallchill.core.plugins.connection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import com.smallchill.core.config.BladeConfig;
import com.smallchill.core.plugins.IPlugin;
import com.smallchill.core.toolbox.kit.LogKit;
import com.smallchill.core.toolbox.redis.IJedis;
import com.smallchill.core.toolbox.redis.IKeyNamingPolicy;
import com.smallchill.core.toolbox.redis.RedisCluster;
import com.smallchill.core.toolbox.redis.RedisSingle;
import com.smallchill.core.toolbox.redis.serializer.JdkSerializer;

/**
 * Redis插件
 */
public class RedisPlugin implements IPlugin {
	private static Map<String, IJedis> redisCachePool = new ConcurrentHashMap<String, IJedis>();
	
	public String MASTER = "master";
	
	public Map<String, IJedis> getRedisCachePool(){
		return redisCachePool;
	}
	
	private RedisPlugin() { }
	
	private static RedisPlugin me = new RedisPlugin();
	
	public static RedisPlugin init(){
		return me;
	}
	
	public void start() {
		try {
			//注入redisSingle
			for(String key : BladeConfig.getJedisPool().keySet()){
				JedisPool jedisPool = BladeConfig.getJedisPool().get(key);
				//创建redis单机操作类
				RedisSingle rs = new RedisSingle(key, jedisPool, JdkSerializer.me, IKeyNamingPolicy.defaultKeyNamingPolicy);
				redisCachePool.put(key, rs);
			}
			//注入redisCluster
			for(String key : BladeConfig.getJedisCluster().keySet()){
				JedisCluster jedisCluster = BladeConfig.getJedisCluster().get(key);
				//创建redis集群操作类
				RedisCluster rc = new RedisCluster(key, jedisCluster, JdkSerializer.me, IKeyNamingPolicy.defaultKeyNamingPolicy);
				redisCachePool.put(key, rc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		for (IJedis jedis : redisCachePool.values()) {
			jedis.close();
		}
		redisCachePool.clear();
		LogKit.println("RedisPlugin关闭成功");
	}

}
