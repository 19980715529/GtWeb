package com.smallchill.core.shiro;

import java.util.Iterator;
import java.util.Random;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

import com.smallchill.common.vo.ShiroUser;

/**
 * shiro工具类
 *
 * @author dafei, Chill Zhuang
 */
public class ShiroKit {
	private static final String NAMES_DELIMETER = ",";

	/**
	 * 加盐参数
	 */
	final static String hashAlgorithmName = "MD5";

	/**
	 * 循环次数
	 */
	final static int hashIterations = 1024;

	/**
	 * shiro密码加密工具类
	 * 
	 * @param 密码
	 * @param 密码盐
	 * @return
	 */
	public static String md5(String credentials, String saltSource) {
		ByteSource salt = new Md5Hash(saltSource);
		return new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations).toString();
	}

	/**
	 * 获取随机盐值
	 * @param length
	 * @return
	 */
	public static String getRandomSalt(int length) { 
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
	 }  
	
	/**
	 * 获取当前 Subject
	 * 
	 * @return Subject
	 */
	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	/**
	 * 获取封装的 ShiroUser
	 * 
	 * @return ShiroUser
	 */
	public static ShiroUser getUser() {
		if (isGuest()) {
			return null;
		} else {
			return (ShiroUser) getSubject().getPrincipals().getPrimaryPrincipal();
		}
	}

	/**
	 * 从shiro获取session
	 * 
	 */
	public static Session getSession() {
		return getSubject().getSession();
	}

	/**
	 * 获取shiro指定的sessionKey
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getSessionAttr(String key) {
		Session session = getSession();
		return session != null ? (T) session.getAttribute(key) : null;
	}

	/**
	 * 设置shiro指定的sessionKey
	 * 
	 */
	public static void setSessionAttr(String key, Object value) {
		Session session = getSession();
		session.setAttribute(key, value);
	}

	/**
	 * 移除shiro指定的sessionKey
	 */
	public static void removeSessionAttr(String key) {
		Session session = getSession();
		if (session != null)
			session.removeAttribute(key);
	}

	/**
	 * 验证当前用户是否属于该角色？,使用时与lacksRole 搭配使用
	 * 
	 * @param roleName
	 *            角色名
	 * @return 属于该角色：true，否则false
	 */
	public static boolean hasRole(String roleName) {
		return getSubject() != null && roleName != null
				&& roleName.length() > 0 && getSubject().hasRole(roleName);
	}

	/**
	 * 与hasRole标签逻辑相反，当用户不属于该角色时验证通过。
	 * 
	 * @param roleName
	 *            角色名
	 * @return 不属于该角色：true，否则false
	 */
	public static boolean lacksRole(String roleName) {
		return !hasRole(roleName);
	}

	/**
	 * 验证当前用户是否属于以下任意一个角色。
	 * 
	 * @param roleNames
	 *            角色列表
	 * @return 属于:true,否则false
	 */
	public static boolean hasAnyRoles(String roleNames) {
		boolean hasAnyRole = false;
		Subject subject = getSubject();
		if (subject != null && roleNames != null && roleNames.length() > 0) {
			for (String role : roleNames.split(NAMES_DELIMETER)) {
				if (subject.hasRole(role.trim())) {
					hasAnyRole = true;
					break;
				}
			}
		}
		return hasAnyRole;
	}

	/**
	 * 验证当前用户是否属于以下所有角色。
	 * 
	 * @param roleNames
	 *            角色列表
	 * @return 属于:true,否则false
	 */
	public static boolean hasAllRoles(String roleNames) {
		boolean hasAllRole = true;
		Subject subject = getSubject();
		if (subject != null && roleNames != null && roleNames.length() > 0) {
			for (String role : roleNames.split(NAMES_DELIMETER)) {
				if (!subject.hasRole(role.trim())) {
					hasAllRole = false;
					break;
				}
			}
		}
		return hasAllRole;
	}

	/**
	 * 验证当前用户是否拥有指定权限,使用时与lacksPermission 搭配使用
	 * 
	 * @param permission
	 *            权限名
	 * @return 拥有权限：true，否则false
	 */
	public static boolean hasPermission(String permission) {
		return getSubject() != null && permission != null
				&& permission.length() > 0
				&& getSubject().isPermitted(permission);
	}

	/**
	 * 与hasPermission标签逻辑相反，当前用户没有制定权限时，验证通过。
	 * 
	 * @param permission
	 *            权限名
	 * @return 拥有权限：true，否则false
	 */
	public static boolean lacksPermission(String permission) {
		return !hasPermission(permission);
	}

	/**
	 * 已认证通过的用户。不包含已记住的用户，这是与user标签的区别所在。与notAuthenticated搭配使用
	 * 
	 * @return 通过身份验证：true，否则false
	 */
	public static boolean isAuthenticated() {
		return getSubject() != null && getSubject().isAuthenticated();
	}

	/**
	 * 未认证通过用户，与authenticated标签相对应。与guest标签的区别是，该标签包含已记住用户。。
	 * 
	 * @return 没有通过身份验证：true，否则false
	 */
	public static boolean notAuthenticated() {
		return !isAuthenticated();
	}

	/**
	 * 认证通过或已记住的用户。与guset搭配使用。
	 * 
	 * @return 用户：true，否则 false
	 */
	public static boolean isUser() {
		return getSubject() != null && getSubject().getPrincipal() != null;
	}

	/**
	 * 验证当前用户是否为“访客”，即未认证（包含未记住）的用户。用user搭配使用
	 * 
	 * @return 访客：true，否则false
	 */
	public static boolean isGuest() {
		return !isUser();
	}

	/**
	 * 输出当前用户信息，通常为登录帐号信息。
	 * 
	 * @return 当前用户信息
	 */
	public static String principal() {
		if (getSubject() != null) {
			Object principal = getSubject().getPrincipal();
			return principal.toString();
		}
		return "";
	}

	/**
	 * 清理AuthenticationInfo缓存
	 * 参考：http://blog.csdn.net/win7system/article/details/51038131
	 * @param account
	 */
	public static void clearCachedAuthenticationInfo(String account) {

		RealmSecurityManager securityManager =(RealmSecurityManager) SecurityUtils.getSecurityManager();
		ShiroDbRealm userRealm = (ShiroDbRealm) securityManager.getRealms().iterator().next();
		userRealm.getCacheManager().getCache(userRealm.getAuthenticationCacheName()).remove(account);
	}
	
	public void clearCachedAllAuthenticationInfo() {
		RealmSecurityManager securityManager =(RealmSecurityManager) SecurityUtils.getSecurityManager();
		Iterator<Realm> iterator = securityManager.getRealms().iterator();
		ShiroDbRealm userRealm = (ShiroDbRealm)iterator.next();
		while(userRealm!=null) {
			userRealm.getCacheManager().getCache(userRealm.getAuthenticationCacheName()).clear();
			userRealm = (ShiroDbRealm) iterator.next();
		}
	}

	/**清除session(认证信息)
	58
	  * @param JSESSIONID
	59
	  */
	/*
	 * public void clearAuthenticationInfo(Serializable JSESSIONID){
	 * //shiro-activeSessionCache
	 * 为shiro自义cache名，该名在org.apache.shiro.session.mgt.eis.CachingSessionDAO抽象类中定义
	 * Cache<Object, Object> cache =
	 * ehCacheManager.getCache("shiro-activeSessionCache");
	 * cache.remove(JSESSIONID); }
	 */

}
