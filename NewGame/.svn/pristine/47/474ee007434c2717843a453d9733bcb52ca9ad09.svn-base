package com.smallchill.core.toolbox.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.smallchill.core.toolbox.kit.ClassKit;
import com.smallchill.core.toolbox.kit.StrKit;

/**
 * 单例对象集合
 */
public class Singleton {
	private static Map<Class<?>, Object> pool = new ConcurrentHashMap<Class<?>, Object>();
	private static Map<String, Object> poolName = new ConcurrentHashMap<String, Object>();

	private final static Singleton me = new Singleton();

	public static Singleton me() {
		return me;
	}

	private Singleton() {

	}

	/**
	 * 创建单例对象.放入缓冲池中
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T create(Class<T> clazz) {
		if (null == clazz) {
			return null;
		}
		T obj = (T) pool.get(clazz);
		if (null == obj) {
			synchronized (Singleton.class) {
				obj = (T) pool.get(clazz);
				if (null == obj) {
					obj = ClassKit.newInstance(clazz);
					pool.put(clazz, obj);
				}
			}
		}
		return obj;
	}
	
	/**
	 * 创建单例对象.放入缓冲池中
	 * @param className
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T create(String className) {
		if (StrKit.isBlank(className)) {
			return null;
		}
		T obj = (T) poolName.get(className);
		if (null == obj) {
			synchronized (Singleton.class) {
				obj = (T) poolName.get(className);
				if (null == obj) {
					obj = ClassKit.newInstance(className);
					poolName.put(className, obj);
				}
			}
		}
		return obj;
	}


	/**
	 * 移除指定缓冲池中对象
	 * 
	 * @param clazz  类
	 */
	public static void remove(Class<?> clazz) {
		pool.remove(clazz);
	}

	/**
	 * 清除所有缓冲池中对象
	 */
	public static void destroy() {
		pool.clear();
	}

}
