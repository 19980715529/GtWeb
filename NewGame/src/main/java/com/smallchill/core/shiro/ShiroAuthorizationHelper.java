/*
 * package com.smallchill.core.shiro; import java.io.Serializable; import
 * java.util.Iterator; import java.util.Set;
 * 
 * import org.apache.shiro.SecurityUtils; import org.apache.shiro.cache.Cache;
 * import org.apache.shiro.cache.ehcache.EhCacheManager; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Component;
 * 
 * import com.smallchill.common.vo.ShiroUser;
 * 
 * @Component public class ShiroAuthorizationHelper {
 * 
 *//**
	* 
	*/
/*
 * @Autowired private EhCacheManager ehCacheManager;
 * 
 * //private static Logger log =
 * LoggerFactory.getLogger(ShiroAuthorizationHelper.class);
 * 
 * 
 *//**
	 * 清除用户的授权信息
	 * 
	 * @param username
	 */
/*
 * public void clearAuthorizationInfo(String username){
 * 
 * 
 * Cache<Object, Object> cache =
 * ehCacheManager.getCache(ShiroDbRealm.class.getName()+".authorizationCache");
 * //cache.remove(username); Set<Object> keys=cache.keys(); Iterator<Object>
 * it=keys.iterator(); System.out.println(keys); while(it.hasNext()){ Object
 * key=it.next(); System.out.println(key.getClass());
 * if(username.equals(key.toString())){ cache.remove(key); } }
 * //System.out.println(keys.contains(username)); //System.out.println(ss);
 * 
 * }
 * 
 *//**
	 * 清除session(认证信息)
	 * 
	 * @param JSESSIONID
	 *//*
		 * public void clearAuthenticationInfo(Serializable JSESSIONID){
		 * 
		 * //shiro-activeSessionCache
		 * 为shiro自义cache名，该名在org.apache.shiro.session.mgt.eis.CachingSessionDAO抽象类中定义
		 * //如果要改变此名，可以将名称注入到sessionDao中，
		 * 例如注入到CachingSessionDAO的子类EnterpriseCacheSessionDAO类中 Cache<Object, Object>
		 * cache = ehCacheManager.getCache("shiro-activeSessionCache");
		 * cache.remove(JSESSIONID); }
		 * 
		 * public EhCacheManager getEhCacheManager() { return ehCacheManager; }
		 * 
		 * public void setEhCacheManager(EhCacheManager ehCacheManager) {
		 * this.ehCacheManager = ehCacheManager; }
		 * 
		 * 
		 * 
		 * 
		 * }
		 */