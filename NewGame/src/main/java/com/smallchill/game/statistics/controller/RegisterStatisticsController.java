package com.smallchill.game.statistics.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.smallchill.common.vo.ShiroUser;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.system.model.UserPack;
import org.beetl.sql.core.OnConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.CharsetKit;
import com.smallchill.core.toolbox.kit.URLKit;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/statistics")
public class RegisterStatisticsController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/statistics/";
	private static String CODE = "statistics";
	
	@Autowired
	private CommonService commonService;

	@DoControllerLog(name="进入日注册统计页面")
	@RequestMapping("/register/")
	//@Permission({ ADMINISTRATOR, ADMIN })
	public String index(ModelMap mm) {
		ShiroUser user = ShiroKit.getUser();
		Integer id =(Integer) user.getId();
		// 查询包id
		Blade blade = Blade.create(UserPack.class);
		UserPack pack = blade.findFirstBy("uid=#{uid}", CMap.init().set("uid", id));
		if (pack!=null){
			String clientType = pack.getClientType();
			Integer[] ids = Convert.toIntArray(clientType);
			mm.put("clientType", ids[0]);
		}else {
			mm.put("clientType", -9);
		}
		mm.put("code", CODE);
		return BASE_PATH + "register_statistics.html";
	}
	
	//	@SystemControllerLog(description = "日注册统计")
	@SuppressWarnings({ "rawtypes" })
	@Json
	@RequestMapping("/dglist")
	public Object dglist() {
		String parameter = getParameter("where");
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		LOGGER.info("注册统计->获取表格数据查询参数：-------------------");
		LOGGER.info(parameter);

		Map paras = JSON.parseObject(parameter, Map.class);
		Object gird = commonService.getInfoList("charts_register_statistics.new_list1", paras);
		return gird;
	}

	//	@SystemControllerLog(description = "当日注册统计")
	@SuppressWarnings("rawtypes")
	@Json
	@RequestMapping("/nglist")
	public Object nglist() {
		String parameter = getParameter("where");
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		LOGGER.info("注册统计->获取当日注册数据查询参数：-------------------");
		LOGGER.info(parameter);
		Map paras = JSON.parseObject(parameter, Map.class);
		Object gird = commonService.getInfoList("charts_register_statistics.new_package_list1", paras);
		return gird;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Json
	@RequestMapping("/getRegisterAxis")
	public Object getRegisterAxis() {
		String parameter = getParameter("where");
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		LOGGER.info("注册统计->获取表格数据查询参数：-------------------");
		LOGGER.info(parameter);
		Map paras = JSON.parseObject(parameter, Map.class);
		paras.put("orderBy", "asc");
		Object gird = commonService.getInfoList("charts_register_statistics.new_list1", paras);
		return gird;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Json
	@RequestMapping("/getDayReg")
	public AjaxResult getDayReg() {
		final String PlatformID = getParameter("PlatformID");
		/*Map m = new HashMap();
		if(parameter != null && StrKit.equals(parameter, "-1")) {
			
		}else if(parameter != null){
			m.put("PlatformID", parameter);
		}
		Map list = commonService.getInfoByOne("charts_register_statistics.day_reg", m);
		return json(list);
		*/
		Map executeCall = Db.executeCall(new OnConnection<Map>(){
			@Override
			public Map call(Connection conn) throws SQLException {
				CallableStatement cstmt = conn.prepareCall("{call [QPGameUserDB].[dbo].[Statistics_DayRegister]( ? ) }");
				ResultSet rs = null;
				//设置输入参数
				cstmt.setInt(1, Integer.parseInt(PlatformID));
				
				//发送参数
				rs = cstmt.executeQuery(); //注意： 所有调用存储过程的sql语句都是使用executeQuery方法执行！！！
				
				Map ret = new HashMap();
				//遍历结果
				while(rs.next()){
	                // 通过字段检索
	                ret.put("totalreg", rs.getInt("totalreg"));
	                ret.put("iosregcount", rs.getInt("iosregcount"));
	                ret.put("andregcount", rs.getInt("andregcount"));
//	                ret.put("newUserUpman", rs.getInt("newUserUpman"));
					ret.put("newUserUpman", 0);
//	                ret.put("newUserDownman", rs.getInt("newUserDownman"));
	                ret.put("newUserDownman", 0);
	                ret.put("pcregcount", rs.getInt("pcregcount"));
	                ret.put("iosfj", rs.getInt("iosfj"));
	                ret.put("unenter", rs.getInt("unenter"));
	                ret.put("rate", rs.getFloat("rate"));
	                ret.put("writedate", rs.getString("writedate"));
	            }
				// 其他代码
				return ret;
			}
		});
		
		return json(executeCall);
	}
}
