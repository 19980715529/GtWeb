package com.smallchill.core.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.smallchill.common.vo.ShiroUser;
import com.smallchill.core.toolbox.Func;
import com.smallchill.system.model.User;

public class ShiroDbRealm extends AuthorizingRealm {
	private static Logger log = LogManager.getLogger(ShiroDbRealm.class);
	
	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		log.info("Shiro登录认证启动");
		
		IShiro shiroFactory = ShiroManager.me().getDefaultShiroFactory();
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		
		User user = shiroFactory.user(token.getUsername());

		ShiroUser shiroUser = shiroFactory.shiroUser(user);

		SimpleAuthenticationInfo info = shiroFactory.info(shiroUser, user, getName());

		log.info("Shiro登录认证完毕");
		return info;
	}

	/**
	 * 权限认证
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		IShiro shiroFactory = ShiroManager.me().getDefaultShiroFactory();
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		Object userId = shiroUser.getId();
		List<Integer> roleList = shiroUser.getRoleList();
		Set<String> urlSet = new HashSet<>();
		Set<String> roleNameSet = new HashSet<>();
		for (Integer roleId : roleList) {
			List<Map> permissions = shiroFactory.findPermissionsByRoleId(userId, roleId);
			if (null != permissions) {
				for (Map map : permissions) {
					if (!Func.isEmpty(map.get("URL"))) {
						urlSet.add(Func.toStr(map.get("URL")));
					}
				}
			}
			String roleName = shiroFactory.findRoleNameByRoleId(roleId);
			if(roleName != null && !roleName.trim().equals("")) {
				roleNameSet.add(roleName);
			}
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermissions(urlSet);
		info.addRoles(roleNameSet);
		return info;
	}

	/**
	 * 设置认证加密方式
	 */
	@PostConstruct
	public void setCredentialMatcher() {
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName(ShiroKit.hashAlgorithmName);
		credentialsMatcher.setHashIterations(ShiroKit.hashIterations);
		setCredentialsMatcher(credentialsMatcher);
	}

}
