package com.smallchill.core.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.smallchill.core.annotation.Permission;
import com.smallchill.core.exception.NoPermissionException;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.check.PermissionCheckManager;

/**
 * AOP 权限自定义检查
 */
@Aspect
@Component
public class PermissionAop {

	@Pointcut(value = "@annotation(com.smallchill.core.annotation.Permission)")
	private void cutPermission() {

	}

	@Around("cutPermission()")
	public Object doPermission(ProceedingJoinPoint point) throws Throwable {
		MethodSignature ms = (MethodSignature) point.getSignature();
		Method method = ms.getMethod();
		Permission permission = method.getAnnotation(Permission.class);
		Object[] permissions = permission.value();
		if ((permissions.length == 1 && Func.toStr(permissions[0]).equals("ALL"))
				|| permissions == null || permissions.length == 0) {
			//检查全体角色
			boolean result = PermissionCheckManager.checkAll();
			if (result) {
				return point.proceed();
			} else {
				throw new NoPermissionException();
			}
		} else {
			//检查指定角色
			boolean result = PermissionCheckManager.check(permissions);
			if (result) {
				return point.proceed();
			} else {
				throw new NoPermissionException();
			}
		}

	}

}
