package com.smallchill.system.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.common.vo.ShiroUser;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.Const;
import com.smallchill.core.constant.ConstConfig;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.core.toolbox.kit.LogKit;
import com.smallchill.core.toolbox.kit.MD5Utils;
import com.smallchill.core.toolbox.kit.RandomKit;
import com.smallchill.core.toolbox.kit.XmlKit;
import com.smallchill.core.toolbox.log.BladeLogManager;
import com.smallchill.game.model.PlayerOperationLog;
import com.smallchill.game.model.UserRightType;
import com.smallchill.system.meta.intercept.LoginValidator;
import com.smallchill.system.model.User;

@Controller
public class LoginController extends BaseController implements Const {
	@Autowired
	private HttpServletRequest request;
	private static String PREFIX = "blade_user";

	@RequestMapping("/")
	public String index() {
		return INDEX_REALPATH;
	}

	/**
	 * 登陆地址
	 */
	@GetMapping("/login")
	public String login() {
		if (ShiroKit.isAuthenticated()) {
			LOGGER.info("/////");
			return redirect("/");
		}
		if(ConstConfig.VALIDATE_BY.equalsIgnoreCase("sms")) {
			LOGGER.info("sms");
			return SMS_LOGIN_REALPATH;
		} else if(ConstConfig.VALIDATE_BY.equalsIgnoreCase("google")) {
			LOGGER.info("google");
			return GOOGLE_LOGIN_REALPATH;
		} else {
			LOGGER.info("login.html");
			return LOGIN_REALPATH;
		}
	}

	/**
	 * 短信登陆请求
	 */
	/*@Json
	@Before(LoginValidator.class)
	@ResponseBody
	@RequestMapping("/login")
	public AjaxResult login(HttpServletRequest request,
			HttpServletResponse response) {
		String account = getParameter("account");
		String password = getParameter("password");
		String usmsCode = getParameter("smsCode");
		String sCode = String.valueOf(request.getSession().getAttribute("smsCode"));
		if(!usmsCode.equals(sCode)){
			request.getSession().removeAttribute("smsCode");
			return error("验证码错误");
		}
		request.getSession().removeAttribute("smsCode");
		request.getSession();
		Subject currentUser = ShiroKit.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(account,
				password.toCharArray());
		token.setRememberMe(true);
		try {
			currentUser.login(token);
			Session session = ShiroKit.getSession();
			LogKit.println("\nsessionID	: {} ", session.getId());
			LogKit.println("sessionHost	: {}", session.getHost());
			LogKit.println("sessionTimeOut	: {}", session.getTimeout());
		} catch (UnknownAccountException e) {
			LOGGER.error("账号不存在!", e);
			return error("账号不存在");
		} catch (DisabledAccountException e) {
			LOGGER.error("账号未启用!", e);
			return error("账号未启用");
		} catch (IncorrectCredentialsException e) {
			LOGGER.error("密码错误!", e);
			return error("密码错误");
		} catch (RuntimeException e) {
			LOGGER.error("未知错误,请联系管理员!", e);
			return error("未知错误,请联系管理员");
		}
		doLog(ShiroKit.getSession(), "登录"); //寫登錄日誌
		updateUserLoginInfo(); // 更新最後登錄信息
		return success("登录成功");
	}*/


	/**
	 * 登陆请求
	 */
	@Json
	@Before(LoginValidator.class)
	@PostMapping("/login")
	public AjaxResult login(HttpServletRequest request,
							HttpServletResponse response) {
		String account = getParameter("account");
		String password = getParameter("password");
		String imgCode = getParameter("imgCode");
		if (!validateCaptcha(response, imgCode)) {
			return error("验证码错误");
		}
		Subject currentUser = ShiroKit.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(account,
				password.toCharArray());
		token.setRememberMe(true);
		try {
			currentUser.login(token);
			Session session = ShiroKit.getSession();
			LogKit.println("\nsessionID	: {} ", session.getId());
			LogKit.println("sessionHost	: {}", session.getHost());
			LogKit.println("sessionTimeOut	: {}", session.getTimeout());
		} catch (UnknownAccountException e) {
			LOGGER.error("账号不存在!", e);
			return error("账号不存在");
		} catch (DisabledAccountException e) {
			LOGGER.error("账号未启用!", e);
			return error("账号未启用");
		} catch (IncorrectCredentialsException e) {
			LOGGER.error("密码错误!", e);
			return error("密码错误");
		} catch (RuntimeException e) {
			LOGGER.error("未知错误,请联系管理员!", e);
			return error("未知错误,请联系管理员");
		}
		doLog(ShiroKit.getSession(), "登录"); //登录日志
		updateUserLoginInfo(); // 更新最后登录消息
		return success("登录成功");
	}
	/**
	 * SMS登陆请求
	 */
	@Json
	@Before(LoginValidator.class)
	@PostMapping("/smsLogin")
	public AjaxResult smsLogin(HttpServletRequest request,
			HttpServletResponse response) {
		String account = getParameter("account");
		String password = getParameter("password");
		String smsCode = getParameter("smsCode");
		String ohash = getParameter("hash");
		String tamp = getParameter("tamp");
		
		User user = Blade.create(User.class).findFirstBy(" where account=#{account}", CMap.init().set("account", account));
		if(user==null) {
			return error("无此账号!");
		}
		String hash = MD5Utils.getMD5Code(user.getPhone() + "@" + tamp + "@" + smsCode);
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, 0);
		String currentTime = sf.format(c.getTime());// 生成5分钟后时间，用户校验是否过期
		// 1. 验证短信验证码
		if (tamp.compareTo(currentTime) > 0) {
			if (hash.equalsIgnoreCase(ohash)){
			}else {
				return error("短信验证码或手机号不正确.");
			}
		} else {
			return error("验证超时，短信验证码已过期.");
		}
		
		Subject currentUser = ShiroKit.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(account, password.toCharArray());
		token.setRememberMe(true);
		try {
			currentUser.login(token);
			Session session = ShiroKit.getSession();
			LogKit.println("\nsessionID	: {} ", session.getId());
			LogKit.println("sessionHost	: {}", session.getHost());
			LogKit.println("sessionTimeOut	: {}", session.getTimeout());
		} catch (UnknownAccountException e) {
			LOGGER.error("账号不存在!", e);
			return error("账号不存在");
		} catch (DisabledAccountException e) {
			LOGGER.error("账号未启用!", e);
			return error("账号未启用");
		} catch (IncorrectCredentialsException e) {
			LOGGER.error("密码错误!", e);
			return error("密码错误");
		} catch (RuntimeException e) {
			LOGGER.error("未知错误,请联系管理员!", e);
			return error("未知错误,请联系管理员");
		}
		doLog(ShiroKit.getSession(), "登录"); //寫登錄日誌
		updateUserLoginInfo(); // 更新最後登錄信息
		return success("登录成功");
	}

	@RequestMapping("/logout")
	public String logout() {
		doLog(ShiroKit.getSession(), "登出");
		Subject currentUser = ShiroKit.getSubject();
		currentUser.logout();
		return redirect("/login");
	}

	@RequestMapping("/unauth")
	public String unauth() {
		if (ShiroKit.notAuthenticated()) {
			return redirect("/login");
		}
		return NOPERMISSION_PATH;
	}

	/*图形验证*/
	@RequestMapping("/captcha")
	public void captcha(HttpServletResponse response) {
		makeCaptcha(response);
	}

	/*短信验证*/
	@Json
	@ResponseBody
	@RequestMapping("/smscha")
	public AjaxResult smscha(HttpServletResponse response,HttpSession session){
		String account = getParameter("account");
		User user = Blade.create(User.class).findFirstBy(" where account=#{account}", CMap.init().set("account", account));
		if(user == null){
			return error("账户不存在");
		}else if(user.getPhone()==null){
			return error("账户未绑定手机号");
		}
		String smsCode = RandomKit.randomNumbers(5);
		session.setAttribute("smsCode",smsCode);
		HashMap<String, Object> params = new HashMap<>();
		params.put("action","send");
		params.put("userid",ConstConfig.SMS_USERID);
		params.put("account",ConstConfig.SMS_ACCOUNT);
		params.put("password",ConstConfig.SMS_PASSWORD);
		params.put("mobile",user.getPhone());
		params.put("content","【管理后台】您的验证码是："+ smsCode +"。请不要把验证码泄露给其他人。");
		params.put("sendTime","");
		params.put("extno","");
		LogKit.println("验证码	: {}", smsCode);
		try {
			String smscode = HttpKit.post("http://dc.28inter.com/sms.aspx", params);
			Map<String, String> map = XmlKit.xmlToMap(smscode);
			if(map.get("returnstatus").equalsIgnoreCase("Faild")){
				return error("短信发送失败!"+map.get("message"));
			}
			
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MINUTE, 15);
			String currentTime = sf.format(c.getTime());// 生成5分钟后时间，用户校验是否过期
			
			String hash =  MD5Utils.getMD5Code(user.getPhone() + "@" + currentTime + "@" + smsCode);//生成MD5值
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("hash", hash);
			resultMap.put("tamp", currentTime);
			return json(resultMap);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return error("短信发送失败");
	}

	public void doLog(Session session, String type) {
		if (!BladeLogManager.isDoLog()) {
			return;
		}
		try {
			ShiroUser user = ShiroKit.getUser();
			PlayerOperationLog log = new PlayerOperationLog();

			log.setOperatorid(Func.toInt(user.getId()));
			log.setOperatorname(Func.toStr(user.getName()));
			log.setOperateip(HttpKit.getRequest().getRemoteAddr());
			log.setOperatereasontype(type);
			log.setDescription("管理员<span class=\"text-red\">【"+Func.toStr(user.getName())+"】</span>登录进入系统");
			log.setStatus(1);
			log.setType(UserRightType.UR_ACCESS_LOG.code());
			log.setInserttime(new Date());
			if(!log.getOperatorname().equalsIgnoreCase("KF2131")){
				Blade.create(PlayerOperationLog.class).save(log);
			}
		} catch (Exception ex) {
			LogKit.logNothing(ex);
		}
	}

	public boolean updateUserLoginInfo() {
		User oldUser = Blade.create(User.class).findById(
				ShiroKit.getUser().getId());
		oldUser.setLastlogintime(new Date());
		oldUser.setLastloginip(request.getRemoteAddr());
		if(oldUser.getVersion() != null) {
			oldUser.setVersion(oldUser.getVersion()+1);
		} else {
			oldUser.setVersion(1);
		}
		boolean temp = Blade.create(User.class).update(oldUser);
		if (temp) {
			CacheKit.removeAll(SYS_CACHE);
			ShiroKit.clearCachedAuthenticationInfo(oldUser.getAccount());
			return true;
		} else {
			return false;
		}
	}
}
