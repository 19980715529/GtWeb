package com.smallchill.game.tool;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.beetl.sql.core.OnConnection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.StrKit;

@Controller
@RequestMapping("/statistics")
public class ServerDayStatistics extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/tools/";
	private static String CODE = "statistics";
	
	@DoControllerLog(name="进入游戏流失统计页面")
	@RequestMapping("/serverflow/")
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "serverdaystatistics.html";
	}

	@Json
	@RequestMapping(KEY_LIST)
	public Object list() {
		Object gird = new Object();
		String parameter = getRequest().getParameter("where");
		if(StrKit.isBlank(parameter)) {
			return gird;
		}
		gird = paginateBySelf("tools.lost_list");
		return gird;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Json
	@RequestMapping("/getFlowStatis")
	public AjaxResult getFlowStatis() {
		final String PlatformID = getParameter("PlatformID");
		final String ServerId = getParameter("ServerId");
		final String starttime = getParameter("startTime");
		final String endtime = getParameter("endTime");
		Map executeCall = Db.executeCall(new OnConnection<Map>(){
			@Override
			public Map call(Connection conn) throws SQLException {
				CallableStatement cstmt = conn.prepareCall("{call [QPGameUserDB].[dbo].[Bg_Platform_UserFlow]( ?,?,?,? ) }");
				ResultSet rs = null;
				//设置输入参数
				if(StrKit.notBlank(PlatformID)) {
					cstmt.setInt(1, Integer.parseInt(PlatformID));
				} else {
					cstmt.setInt(1, 0);
				}
				if(StrKit.notBlank(ServerId)) {
					cstmt.setInt(2,Integer.parseInt(ServerId));
				} else {
					cstmt.setInt(2,0);
				}
				if(StrKit.notBlank(starttime)) {
					cstmt.setDate(3, Date.valueOf(starttime));
				} else {
					cstmt.setDate(3, new Date((new java.util.Date()).getTime()));
				}
				if(StrKit.notBlank(endtime)) {
					cstmt.setDate(4, Date.valueOf(endtime));
				} else {
					cstmt.setDate(4, new Date((new java.util.Date()).getTime()));
				}
				
				//发送参数
				rs = cstmt.executeQuery(); //注意： 所有调用存储过程的sql语句都是使用executeQuery方法执行！！！
				
				Map ret = new HashMap();
				//遍历结果
				while(rs.next()){
	                // 通过字段检索
	                ret.put("regtotal", rs.getLong("regtotal"));//总注册人数
	                ret.put("flowtotal", rs.getInt("flowtotal"));//次日流失人数
	                ret.put("losenum", rs.getInt("losenum"));//输人数
	                ret.put("winnum", rs.getInt("winnum"));//赢人数
	                ret.put("normalnum", rs.getInt("normalnum"));//平局人数
	                ret.put("unenternum", rs.getInt("unenternum"));//未进房间人数
	                ret.put("nextonline", rs.getInt("nextonline"));//次日在线人数
	            }
				// 其他代码
				return ret;
			}
		});
		return json(executeCall);
	}
}
