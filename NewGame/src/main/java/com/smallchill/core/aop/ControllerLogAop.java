package com.smallchill.core.aop;

import java.lang.reflect.Method;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.smallchill.common.vo.ShiroUser;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.core.toolbox.kit.ObjectKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.log.BladeLogManager;

/**
 * AOP 日志
 */
@Aspect
@Component
public class ControllerLogAop {
	private static Logger log = LogManager.getLogger(ControllerLogAop.class);

	//@Pointcut("within(@org.springframework.stereotype.Controller *)")
	@Pointcut("execution(* com.smallchill.*..controller.*.*(..))")
	public void cutController() {
	}

	@Around("cutController()")
	public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {
		if(!BladeLogManager.isDoLog()){
			return point.proceed();
		}
		MethodSignature ms = (MethodSignature) point.getSignature();
		Method method = ms.getMethod();
		String methodName = ms.getName();
		DoControllerLog doLog = method.getAnnotation(DoControllerLog.class);
		if (!isWriteLog(methodName) && null == doLog) {
			return point.proceed();
		}
		ShiroUser user = ShiroKit.getUser();
		if(null == user){
			return point.proceed();
		}
		String className = point.getTarget().getClass().getName();
		Object[] params = point.getArgs();
		StringBuilder sb = new StringBuilder();
		Enumeration<String> paraNames = null;
		HttpServletRequest request = HttpKit.getRequest();
		if (params != null && params.length > 0) {
			paraNames = request.getParameterNames();
			String key;
			String value;
			while (paraNames.hasMoreElements()) {
				key = paraNames.nextElement();
				value = request.getParameter(key);
				Func.builder(sb, key, "=", value, "&");
			}
			if (Func.isEmpty(sb)) {
				Func.builder(sb, request.getQueryString());
			}
		}
		try {
			String msg = Func.format("[类名]:{}  [方法]:{}  [参数]:{}", className, methodName, StrKit.removeSuffix(sb.toString(), "&"));
			if(doLog != null) {
				BladeLogManager.doControllerLog((ObjectKit.isNull(doLog) ? getLogName(methodName) : doLog.name()), msg, true,doLog.name());
			}
			log.info(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return point.proceed();
	}

	private boolean isWriteLog(String method) {
		String[] pattern = BladeLogManager.logPatten();
		for (String s : pattern) {
			if (method.indexOf(s) > -1) {
				return true;
			}
		}
		return false;
	}
	
	private String getLogName(String method){
		String[] pattern = BladeLogManager.logPatten();
		for (String s : pattern) {
			if (method.indexOf(s) > -1) {
				return BladeLogManager.logMaps().getStr(s);
			}
		}
		return "";
	}
	
}
