package com.smallchill.core.aop;  
  
import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.smallchill.common.vo.ShiroUser;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.log.BladeLogManager;
import com.smallchill.system.model.OperationLog;
  
/** 
 * 切点类 
 * @author tiangai 
 * @since 2014-08-05 Pm 20:35 
 * @version 1.0 
 */  
@Aspect  
@Component  
public  class SystemLogAspect { 
    //本地异常日志记录对象  
     private  static  final Logger logger = LoggerFactory.getLogger(SystemLogAspect. class);  
  
    //Controller层切点  
    @Pointcut("@annotation(com.smallchill.core.aop.SystemControllerLog)")  
     public  void controllerAspect() {  
    }  
  
    /** 
     * 前置通知 用于拦截Controller层记录用户的操作 
     * 
     * @param joinPoint 切点 
     */  
    @After("controllerAspect()")  
     public  void doBefore(JoinPoint joinPoint) {  
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();  
        //读取session中的用户  
        ShiroUser user = ShiroKit.getUser();
        //请求的IP  
        String ip = request.getRemoteAddr();  
         try {  
            //*========控制台输出=========*//  
            System.out.println("=====前置通知开始=====");  
            System.out.println("请求方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));  
            System.out.println("方法描述:" + getControllerMethodDescription(joinPoint));  
            System.out.println("请求人:" + user.getName());  
            System.out.println("请求IP:" + ip);  
            //*========数据库日志=========*//  
            String logName = getLogName(joinPoint.getSignature().getName());
            String msg = (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()");
            String desc = getControllerMethodDescription(joinPoint);
            //保存数据库  
            doLog(logName, msg, true, desc,ip); 
            System.out.println("=====前置通知结束=====");  
        }  catch (Exception e) {  
            //记录本地异常日志  
            logger.error("==前置通知异常==");  
            logger.error("异常信息:{}", e.getMessage());  
        }  
    }  
  
    /** 
     * 获取注解中对方法的描述信息 用于Controller层注解 
     * 
     * @param joinPoint 切点 
     * @return 方法描述 
     * @throws Exception 
     */  
     public  static String getControllerMethodDescription(JoinPoint joinPoint)  throws Exception {  
        String targetName = joinPoint.getTarget().getClass().getName();  
        String methodName = joinPoint.getSignature().getName();  
        Object[] arguments = joinPoint.getArgs();  
        Class targetClass = Class.forName(targetName);  
        Method[] methods = targetClass.getMethods();  
        String description = "";  
         for (Method method : methods) {  
             if (method.getName().equals(methodName)) {  
                Class[] clazzs = method.getParameterTypes();  
                 if (clazzs.length == arguments.length) {  
                    description = method.getAnnotation(SystemControllerLog. class).description();  
                     break;  
                }  
            }  
        }  
         return description;  
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
     
     private boolean doLog(String logName, String msg, boolean succeed,String desc,String ip) {
 		ShiroUser user = ShiroKit.getUser();
 		if (null == user) {
 			return true;
 		}
 		try {
 			OperationLog log = new OperationLog();
 			log.setMethod(msg);
 			log.setCreatetime(new Date());
 			log.setSucceed((succeed) ? "1" : "0");
 			log.setUserid(Func.toStr(user.getId()));
 			log.setLogname(logName);
 			log.setOperaip(ip);
 			log.setMessage(desc);
 			boolean temp = Blade.create(OperationLog.class).save(log);
 			return temp;
 		} catch (Exception ex) {
 			return false;
 		}
 	}
}  