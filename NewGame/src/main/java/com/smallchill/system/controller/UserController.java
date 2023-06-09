package com.smallchill.system.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smallchill.common.base.BaseController;
import com.smallchill.common.tool.SysCache;
import com.smallchill.common.vo.ShiroUser;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.aop.AopContext;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.meta.IQuery;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.cache.ILoader;
import com.smallchill.core.toolbox.kit.CollectionKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.game.service.CommonService;
import com.smallchill.system.meta.intercept.PasswordValidator;
import com.smallchill.system.meta.intercept.UserIntercept;
import com.smallchill.system.meta.intercept.UserValidator;
import com.smallchill.system.meta.intercept.UserValidator2;
import com.smallchill.system.model.RoleExt;
import com.smallchill.system.model.User;
import com.smallchill.system.model.UserOnline;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController implements ConstShiro{
	private static String LIST_SOURCE = "user.list";
	private static String BASE_PATH = "/system/user/";
	private static String CODE = "user";
	private static String PREFIX = "blade_user";
	
	@Autowired
	private CommonService commonService;
	@Autowired
	private SessionDAO sessionDAO;
    
	@DoControllerLog(name="进入管理员列表页面")
	@RequestMapping("/")
	//@Permission({ ADMINISTRATOR, ADMIN })
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "user.html";
	}
	@DoControllerLog(name="进入管理员列表页面")
	@RequestMapping("/goOnline")
	//@Permission({ ADMINISTRATOR, ADMIN })
	public String goOnline(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "online.html";
	}
	
	/**
	 * 分页aop
	 * 普通用法
	 */
	@Json
	@RequestMapping(KEY_LIST)
	//@Permission({ ADMINISTRATOR, ADMIN })
	public Object list() {
		Object gird = paginate(LIST_SOURCE, new UserIntercept());
		return gird;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Json
	@RequestMapping("/rulist")
	//@Permission({ ADMINISTRATOR, ADMIN })
	public Object rulist(@RequestParam(name="id", required=false) String id) {
		Map paras = new HashMap();
		paras.put("roles", id);
		Object gird = commonService.getInfoList("user.role_user_list", paras);
		Map map = new HashMap();
		map.put("rows", gird);
		return map;
	}
	@DoControllerLog(name="进入新增管理员页面")
	@RequestMapping(KEY_ADD)
	@Permission({ ADMINISTRATOR, ADMIN })
	public String add(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "user_add.html";
	}
	@DoControllerLog(name="进入修改管理员页面")
	@RequestMapping(KEY_EDIT + "/{id}")
	@Permission({ ADMINISTRATOR, ADMIN })
	public String edit(@PathVariable Integer id, ModelMap mm) {
		User user = Blade.create(User.class).findById(id);
		CMap cmap = CMap.parse(user);
		cmap.set("roleName", SysCache.getRoleName(user.getRoleid()));
		mm.put("user", cmap);
		mm.put("code", CODE);
		return BASE_PATH + "user_edit.html";
	}
	@DoControllerLog(name="进入修改管理员页面")
	@RequestMapping("/editMySelf/{id}")
	@Permission({ ADMINISTRATOR, ADMIN })
	public String editMySelf(@PathVariable Integer id, ModelMap mm) {
		User user = Blade.create(User.class).findById(id);
		CMap cmap = CMap.parse(user);
		cmap.set("roleName", SysCache.getRoleName(user.getRoleid()));
		mm.put("user", cmap);
		mm.put("code", CODE);
		mm.put("methodName", "editMySelf");
		return BASE_PATH + "user_edit.html";
	}
	@DoControllerLog(name="进入修改密码页面")
	@RequestMapping("/editPassword/{id}")
	@Permission({ ADMINISTRATOR, ADMIN })
	public String editPassword(@PathVariable Integer id, ModelMap mm){
		User user = Blade.create(User.class).findById(id);
		mm.put("user", user);
		mm.put("code", CODE);
		return BASE_PATH + "user_edit_password.html";
	}
	@DoControllerLog(name="修改管理员密码")
	@Json
	@Before(PasswordValidator.class)
	@RequestMapping("/updatePassword")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult updatePassword(){
		Blade blade = Blade.create(User.class);
		String userId = getParameter("user.id");
		String password = getParameter("user.newPassword");
		User user = blade.findById(userId);
		String salt = user.getSalt();
		user.setPassword(ShiroKit.md5(password, salt));
		Integer version = user.getVersion() != null ? user.getVersion() : 0;
		user.setVersion(version + 1);
		boolean temp = blade.update(user);
		if (temp) {
			ShiroKit.clearCachedAuthenticationInfo(user.getAccount());
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}

	@RequestMapping(KEY_VIEW + "/{id}")
	@Permission({ ADMINISTRATOR, ADMIN })
	public String view(@PathVariable Integer id, ModelMap mm) {
		User user = Blade.create(User.class).findById(id);
		CMap cmap = CMap.parse(user);
		cmap.set("deptName", SysCache.getDeptName(user.getDeptid()))
			.set("roleName", SysCache.getRoleName(user.getRoleid()))
			.set("sexName", SysCache.getDictName(101, user.getSex()));
		mm.put("user", cmap);
		mm.put("code", CODE);
		return BASE_PATH + "user_view.html";
	}

	@Json
	@RequestMapping("/getUserByJson")
	public AjaxResult getUserByJson() {
		final String id = getParameter("id");
		User user = Blade.create(User.class).findById(id);
		return json(user);
	}

	@DoControllerLog(name="新增管理员")
	@Json
	@Before(UserValidator.class)
	@RequestMapping(KEY_SAVE)
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult save() {
		User user = mapping(PREFIX, User.class);
		String pwd = user.getPassword();
		String salt = ShiroKit.getRandomSalt(5);
		String pwdMd5 = ShiroKit.md5(pwd, salt);
		user.setPassword(pwdMd5);
		user.setSalt(salt);
		user.setStatus(1);
		user.setRoleid("3");
		user.setCreatetime(new Date());
		user.setVersion(1);
		user.setName(user.getAccount());
		boolean temp = Blade.create(User.class).save(user);
		if (temp) {
			CacheKit.removeAll(SYS_CACHE);
			return success(SAVE_SUCCESS_MSG);
		} else {
			return error(SAVE_FAIL_MSG);
		}
	}

	@DoControllerLog(name="修改管理员信息")
	@Json
	@Before(UserValidator.class)
	@RequestMapping(KEY_UPDATE)
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult update() {
		User user = mapping(PREFIX, User.class);
		if(StrKit.notBlank(PREFIX + "PASSWORD")){
			String pwd = user.getPassword();
			User oldUser = Blade.create(User.class).findById(user.getId());
			if(!pwd.equals(oldUser.getPassword())){
				String salt = oldUser.getSalt();
				String pwdMd5 = ShiroKit.md5(pwd, salt);
				user.setPassword(pwdMd5);
			}
		}
		boolean temp = Blade.create(User.class).update(user);
		if (temp) {
			CacheKit.removeAll(SYS_CACHE);
            ShiroKit.clearCachedAuthenticationInfo(user.getAccount());
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}
	
	@DoControllerLog(name="修改管理员信息")
	@Json
	@Before(UserValidator2.class)
	@RequestMapping("/updateinfo")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult updateinfo() {
		User user = mapping(PREFIX, User.class);
		boolean temp = Blade.create(User.class).update(user);
		if (temp) {
			CacheKit.removeAll(SYS_CACHE);
			ShiroKit.clearCachedAuthenticationInfo(user.getAccount());
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}

	@DoControllerLog(name="删除管理员")
	@Json
	@RequestMapping(KEY_DEL)
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult del() {
		boolean temp = Blade.create(User.class).updateBy("status = #{status}", "id in (#{join(ids)})", CMap.init().set("status", 5).set("ids", Convert.toIntArray(getParameter("ids"))));
		if (temp) {
			return success(DEL_SUCCESS_MSG);
		} else {
			return error(DEL_FAIL_MSG);
		}
	}

	@DoControllerLog(name="重置用户密码")
	@Json
	@RequestMapping("/reset")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult reset() {
		String ids = getParameter("ids");
		Blade blade = Blade.create(User.class);
		Integer[] idArr = Convert.toIntArray(ids);
		int cnt = 0;
		for(Integer id : idArr){
			User user = blade.findById(id);
			String pwd = "111111";
			String salt = user.getSalt();
			String pwdMd5 = ShiroKit.md5(pwd, salt);
			user.setVersion(((user.getVersion() == null) ? 0 : user.getVersion()) + 1);
			user.setPassword(pwdMd5);
			boolean temp = blade.update(user);
			if(temp){
				cnt++;
			}
		}
		if (cnt == idArr.length) {
			return success("重置密码成功");
		} else {
			return error("重置密码失败");
		}
	}

	@DoControllerLog(name="审核管理员")
	@Json
	@RequestMapping("/auditOk")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult auditOk() {
		String ids = getParameter("ids");
		Blade blade = Blade.create(User.class);
		CMap countMap = CMap.init().set("ids", Convert.toIntArray(ids));
		int cnt = blade.count("id in (#{join(ids)}) and (roleId='' or roleId is null)", countMap);
		if (cnt > 0) {
			return warn("存在没有分配角色的账号!");
		}
		CMap updateMap = CMap.init().set("status", 1).set("ids", Convert.toIntArray(ids));
		boolean temp = blade.updateBy("status = #{status}", "id in (#{join(ids)})", updateMap);
		if (temp) {
			return success("审核成功!");
		} else {
			return error("审核失败!");
		}
	}

	@DoControllerLog(name="解锁管理员")
	@Json
	@RequestMapping("/unlockOk")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult unlockOk() {
		String ids = getParameter("ids");
		Blade blade = Blade.create(User.class);
		CMap countMap = CMap.init().set("ids", Convert.toIntArray(ids));
		int cnt = blade.count("id in (#{join(ids)}) and (roleId='' or roleId is null)", countMap);
		if (cnt > 0) {
			return warn("存在没有分配角色的账号!");
		}
		CMap updateMap = CMap.init().set("status", 1).set("ids", Convert.toIntArray(ids));
		boolean temp = blade.updateBy("status = #{status}", "id in (#{join(ids)})", updateMap);
		if (temp) {
			return success("解锁成功!");
		} else {
			return error("解锁失败!");
		}
	}

	@DoControllerLog(name="审核拒绝")
	@Json
	@RequestMapping("/auditRefuse")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult auditRefuse() {
		String ids = getParameter("ids");
		CMap updateMap = CMap.init().set("status", 4).set("ids", Convert.toIntArray(ids));
		boolean temp = Blade.create(User.class).updateBy("status = #{status}", "id in (#{join(ids)})", updateMap);
		if (temp) {
			return success("审核拒绝成功!");
		} else {
			return error("审核拒绝失败!");
		}
	}
	
	@Json
	@RequestMapping("/ban")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult ban() {
		String ids = getParameter("ids");
		CMap updateMap = CMap.init().set("ids", Convert.toIntArray(ids));
		boolean temp = Blade.create(User.class).updateBy("status = (CASE WHEN STATUS=2 THEN 3 ELSE 2 END)", "id in (#{join(ids)})", updateMap);
		if (temp) {
			return success("操作成功!");
		} else {
			return error("操作失败!");
		}
	}
	
	@Json
	@RequestMapping("/restore")
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult restore() {
		String ids = getParameter("ids");
		CMap updateMap = CMap.init().set("status", 3).set("ids", Convert.toIntArray(ids));
		boolean temp = Blade.create(User.class).updateBy("status = #{status}", "id in (#{join(ids)})", updateMap);
		if (temp) {
			return success("还原成功!");
		} else {
			return error("还原失败!");
		}
	}

	@DoControllerLog(name="删除管理员")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission({ ADMINISTRATOR, ADMIN })
	public AjaxResult remove() {
		String ids = getParameter("ids");
		boolean temp = Blade.create(User.class).deleteByIds(ids) > 0;
		if (temp) {
			CacheKit.removeAll(SYS_CACHE);
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
	
	@RequestMapping("/extrole/{id}/{roleName}")
	public String extrole(@PathVariable Integer id, @PathVariable String roleName, ModelMap mm) {
		User user = Blade.create(User.class).findById(id);
		String roleId = user.getRoleid();
		mm.put("userId", id);
		mm.put("roleId", roleId);
		mm.put("roleName", Func.decodeUrl(roleName));
		return BASE_PATH + "user_extrole.html";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Json
	@RequestMapping("/menuTreeIn")
	public AjaxResult menuTreeIn() {
		Integer userId = getParameterToInt("userId");
		Map<String, Object> roleIn = Db.selectOne("select ROLEIN from blade_role_ext where userId = #{userId}", CMap.init().set("userId",userId));
		List<Integer> ids = Db.queryListInt("select MENUID from blade_relation where ROLEID in (#{join(roles)})", CMap.init().set("roles", ShiroKit.getUser().getRoleList()));
		String inId = "0";
		if (!Func.isEmpty(roleIn)) {
			inId = Func.toStr(roleIn.get("ROLEIN"));
		}
		Object[] all = CollectionKit.addAll(ids.toArray(), Convert.toIntArray(inId));
		StringBuilder sb = Func.builder(
				"select m.id \"id\",(select id from blade_menu  where code=m.pCode) \"pId\",name \"name\",(case when m.levels=1 then 'true' else 'false' end) \"open\",(case when r.id is not null then 'true' else 'false' end) \"checked\"",
				" from blade_menu m",
				" left join (select id from blade_menu where id in (#{join(inId)})) r",
				" on m.id=r.id",
				" where m.status=1 and m.id in (#{join(all)}) order by m.levels,m.num asc"
				);
		
		List<Map> menu = Db.selectList(sb.toString(), CMap.init().set("inId", Convert.toIntArray(inId)).set("all", all));
		return json(menu);
	}
	
	@SuppressWarnings("rawtypes")
	@Json
	@RequestMapping("/menuTreeOut")
	public AjaxResult menuTreeOut() {
		Integer userId = getParameterToInt("userId");
		Map roleOut = Db.selectOne("select ROLEOUT from blade_role_ext where userId = #{userId}", CMap.init().set("userId",userId));
		List<Integer> ids = Db.queryListInt("select MENUID from blade_relation where ROLEID in (#{join(roles)})", CMap.init().set("roles", ShiroKit.getUser().getRoleList()));
		String outId = "0";
		if (!Func.isEmpty(roleOut)) {
			outId = Func.toStr(roleOut.get("ROLEOUT"));
		}
		Object[] all = CollectionKit.addAll(ids.toArray(), Convert.toIntArray(outId));
		StringBuilder sb = Func.builder(
				"select m.id \"id\",(select id from blade_menu  where code=m.pCode) \"pId\",name \"name\",(case when m.levels=1 then 'true' else 'false' end) \"open\",(case when r.id is not null then 'true' else 'false' end) \"checked\"",
				" from blade_menu m",
				" left join (select id from blade_menu where id in (#{join(outId)})) r",
				" on m.id=r.id",
				" where m.status=1 and m.id in (#{join(all)}) order by m.levels,m.num asc"
				);
		
		List<Map> menu = Db.selectList(sb.toString(), CMap.init().set("outId", Convert.toIntArray(outId)).set("all", all));
		return json(menu);
	}
	
	@Json
	@RequestMapping("/saveRoleExt")
	public AjaxResult saveRoleExt() {
		Blade blade = Blade.create(RoleExt.class);
		Integer userId = getParameterToInt("userId");
		String roleIn = getParameter("idsIn", "0");
		String roleOut = getParameter("idsOut", "0");
		RoleExt roleExt = blade.findFirstBy("userId = #{userId}", CMap.init().set("userId", userId));	
		boolean flag = false;
		if (Func.isEmpty(roleExt)) {
			roleExt = new RoleExt();
			flag = true;
		}
		roleExt.setUserid(userId);  
		roleExt.setRolein((StrKit.equals(roleIn, "")) ? "0" : roleIn); 
		roleExt.setRoleout((StrKit.equals(roleOut, "")) ? "0" : roleOut); 
		if (flag) {
			blade.save(roleExt);
		} else {
			blade.update(roleExt);
		}
		CacheKit.removeAll(SYS_CACHE);
		return success("配置成功!"); 
	}
	
	@RequestMapping("/roleAssign/{id}/{name}/{roleId}")
	public String roleAssign(@PathVariable String id, @PathVariable String name, @PathVariable String roleId, ModelMap mm) {
		mm.put("id", id);
		mm.put("roleId", roleId);
		mm.put("name", Func.decodeUrl(name));
		return BASE_PATH + "user_roleassign.html";
	}
	
	@Json
	@RequestMapping("/saveRole")
	public AjaxResult saveRole() {
		String id = getParameter("id");
		String roleIds = getParameter("roleIds");
		CMap cmap = CMap.init();
		cmap.set("roleIds", roleIds).set("id", Convert.toIntArray(id));
		boolean temp = Blade.create(User.class).updateBy("ROLEID = #{roleIds}", "id in (#{join(id)})", cmap);
		if (temp) {
			CacheKit.removeAll(SYS_CACHE);
			return success("配置成功!");
		} else {
			return error("配置失败!");
		}
	}
	
	@Json
	@RequestMapping("/userTreeList")
	public AjaxResult userTreeList() {
		List<Map<String, Object>> dept = CacheKit.get(SYS_CACHE, USER_TREE_ALL,
				new ILoader() {
					public Object load() {
						return Db.selectList("select id \"id\",pId \"pId\",simpleName as \"name\",(case when (pId=0 or pId is null) then 'true' else 'false' end) \"open\" from  BLADE_DEPT order by pId,num asc", CMap.init(), new AopContext(), new IQuery() {
							
							@Override
							public void queryBefore(AopContext ac) {
								
							}
							
							@SuppressWarnings("unchecked")
							@Override
							public void queryAfter(AopContext ac) {
								List<Map<String, Object>> list = (List<Map<String, Object>>) ac.getObject();
								List<Map<String, Object>> _list = new ArrayList<>(); 
								for (Map<String, Object> map : list) {
									Integer[] deptIds = Convert.toIntArray(map.get("id").toString());
									List<User> users = Blade.create(User.class).findBy("DEPTID in (#{join(deptId)})", CMap.init().set("deptId", deptIds));
									for (User user : users) {
										for (Integer deptId : deptIds) {
											Map<String, Object> userMap = CMap.createHashMap();
											userMap.put("id", user.getId() + 9999);
											userMap.put("pId", deptId);
											userMap.put("name", user.getName());
											userMap.put("open", "false");
											userMap.put("iconSkin", "iconPerson");
											_list.add(userMap);
										}
									}
								}
								list.addAll(_list);
							}
						});
					}
				});

		return json(dept);
	}
	
	@DoControllerLog(name="获取所有在线管理员信息")
	@Json
	@RequestMapping("/online")
	@Permission({ ADMINISTRATOR, ADMIN })
	public Object online() {
		 List<UserOnline> list = new ArrayList<>();
	        Collection<Session> sessions = sessionDAO.getActiveSessions();
	        for (Session session : sessions) {
	            UserOnline userOnline = new UserOnline();
	            ShiroUser user = null;
	            SimplePrincipalCollection principalCollection = new SimplePrincipalCollection();
	            if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
	                continue;
	            } else {
	                principalCollection = (SimplePrincipalCollection) session
	                        .getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
	                user = (ShiroUser) principalCollection.getPrimaryPrincipal();
	                userOnline.setUsername(user.getLoginName());
	                userOnline.setUserId(user.getId().toString());
	            }
	            userOnline.setId((String) session.getId());
	            userOnline.setHost(session.getHost());
	            userOnline.setStartTimestamp(session.getStartTimestamp());
	            userOnline.setLastAccessTime(session.getLastAccessTime());
	            Long timeout = session.getTimeout();
	            if (timeout == 0L) {
	                userOnline.setStatus("0");
	            } else {
	                userOnline.setStatus("1");
	            }
	            userOnline.setTimeout(timeout);
	            list.add(userOnline);
	        }
	        Map map = new HashMap();
			map.put("rows", list);
		return map;
	}
	
	@DoControllerLog(name="剔除在线管理员信息")
	@Json
	@Permission({ ADMINISTRATOR, ADMIN })
	@RequestMapping("/forceLogout/{id}")
	public AjaxResult forceLogout(@PathVariable String id) {
		try {
			ShiroKit.removeSessionAttr(id);
			/*
			 * Session session = sessionDAO.readSession(id); session.setTimeout(0);
			 * session.stop(); sessionDAO.delete(session);
			 */
			return success("剔除用户成功");
		} catch (Exception e) {
			e.printStackTrace();
			return error("踢出用户失败");
		}
	}
}
