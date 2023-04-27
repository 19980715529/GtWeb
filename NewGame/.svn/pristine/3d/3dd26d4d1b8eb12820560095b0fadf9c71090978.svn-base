package com.smallchill.core.shiro;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.SimpleAuthenticationInfo;

import com.smallchill.common.vo.ShiroUser;
import com.smallchill.system.model.User;

/**
 * 定义shirorealm所需数据的接口
 *
 */
public interface IShiro {
	User user(String account);

	ShiroUser shiroUser(User user);

	@SuppressWarnings("rawtypes")
	List<Map> findPermissionsByRoleId(Object userId, Integer roleId);

	String findRoleNameByRoleId(Integer roleId);
	
	SimpleAuthenticationInfo info(ShiroUser shiroUser, User user, String realmName);
}
