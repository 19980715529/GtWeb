package com.smallchill.core.toolbox.check;

import com.smallchill.core.constant.Cst;

/**
 * 权限检查工厂
 */
public class PermissionCheckManager {
	private final static PermissionCheckManager me = new PermissionCheckManager();

	private ICheck defaultCheckFactory = Cst.me().getDefaultCheckFactory();

	public static PermissionCheckManager me() {
		return me;
	}

	private PermissionCheckManager() {
	}

	public PermissionCheckManager(ICheck checkFactory) {
		this.defaultCheckFactory = checkFactory;
	}

	public void setDefaultCheckFactory(ICheck defaultCheckFactory) {
		this.defaultCheckFactory = defaultCheckFactory;
	}

	public static boolean check(Object[] permissions) {
		return me.defaultCheckFactory.check(permissions);
	}
	
	public static boolean checkAll() {
		return me.defaultCheckFactory.checkAll();
	}
}
