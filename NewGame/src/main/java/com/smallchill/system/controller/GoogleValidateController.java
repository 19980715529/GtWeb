package com.smallchill.system.controller;

import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.smallchill.common.base.BaseController;
import com.smallchill.common.vo.ShiroUser;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.core.toolbox.kit.LogKit;
import com.smallchill.core.toolbox.kit.google.QRCodeUtil;
import com.smallchill.core.toolbox.log.BladeLogManager;
import com.smallchill.game.model.PlayerOperationLog;
import com.smallchill.game.model.UserRightType;
import com.smallchill.system.model.User;
import com.smallchill.system.model.google.GoogleDTO;
import com.smallchill.system.service.UserService;

@Controller
@RequestMapping("/google")
public class GoogleValidateController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private HttpServletRequest request;
	
	@GetMapping("/bind")
	public String bind() {
		if (ShiroKit.isAuthenticated()) {
			return redirect("/");
		}
		return "/bindGoogle.html";
	}
	
	 @GetMapping("/generateGoogleSecretWithoutPassword")
	 @ResponseBody 
	 public AjaxResult generateGoogleSecretWithoutPassword()throws Exception{
		String account = getParameter("account");
		return userService.generateGoogleSecretWithoutPassword(account); 
	 }
	 @GetMapping("/generateGoogleSecret")
	 @ResponseBody 
	 public AjaxResult generateGoogleSecret()throws Exception{
		 String account = getParameter("account");
		 User user = Blade.create(User.class).findFirstBy(" where account=#{account}", CMap.init().set("account", account));
		 if(user==null) {
			 return error("用户不存在.");
		 }
		 String password = getParameter("password");
		 String pwdMd5 = ShiroKit.md5(password, user.getSalt());
		 if(!pwdMd5.equalsIgnoreCase(user.getPassword())) {
			 return error("密码错误.");
		 }
		 return userService.generateGoogleSecret(user); 
	 }
	 

	/**
	 * 注意：这个需要地址栏请求,因为返回的是一个流 注意：这个需要地址栏请求,因为返回的是一个流 注意：这个需要地址栏请求,因为返回的是一个流
	 * 显示一个二维码图片
	 * 
	 * @param secretQrCode generateGoogleSecret接口返回的：secretQrCode
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("/genQrCode")
	public void genQrCode(String secretQrCode, HttpServletResponse response) throws Exception {
		response.setContentType("image/png");
		OutputStream stream = response.getOutputStream();
		QRCodeUtil.encode(secretQrCode, stream);
	}

	 @GetMapping("/bindGoogle")
	 @ResponseBody 
	 public AjaxResult bindGoogle(GoogleDTO dto)throws Exception{
		 String account = getParameter("account");
		 User user = Blade.create(User.class).findFirstBy(" where account=#{account}", CMap.init().set("account", account));
		 return userService.bindGoogle(dto,user,this.getRequest()); 
	 }
	 
	
	 @PostMapping("/googleLogin")
	 @ResponseBody 
	 public AjaxResult googleLogin() throws Exception{
		String account = getParameter("account");
		String password = getParameter("password");
		Long smsCode = getParameterToLong("smsCode");
		if(smsCode==null) {
			return error("请输入验证码!");
		}
		User user = Blade.create(User.class).findFirstBy(" where account=#{account}", CMap.init().set("account", account));
		if(user==null) {
			return error("无此账号!");
		}
		 AjaxResult googleLogin = userService.googleLogin(smsCode,user,this.getRequest()); 
		 if(googleLogin.getCode()!=0) {
			 return error(googleLogin.getMessage());
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
	 

	@GetMapping("/getData")
	@ResponseBody
	public AjaxResult getData() throws Exception {
		return userService.getData();
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
