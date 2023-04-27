package com.smallchill.game.player.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.beetl.sql.core.OnConnection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.game.model.UserRightType;
import com.smallchill.game.newmodel.Accountsinfo;
import com.smallchill.game.player.meta.intercept.GiveTransferValidator;
import com.smallchill.game.request.model.GiveTransferRequest;

@Controller
@RequestMapping("/givetransfer")
public class GiveTransferController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/gameplayer/";
	private static String CODE = "givetransfer";
	private static String LIST_SOURCE = "player_givetransfer.list";
	private static String PREFIX = "player";
	
	@DoControllerLog(name="进入赠送转移列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "player_give_transfer.html";
	}
	
	@Json
	@RequestMapping(KEY_LIST)
	@Permission(ADMINISTRATOR)
	public Object list() {
		Object gird = paginateBySelf(LIST_SOURCE);
		return gird;
	}
	
	@RequestMapping(KEY_ADD)
	@Permission(ADMINISTRATOR)
	public String add(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "player_edit_givetransfer.html";
	}
	
	@DoControllerLog(name="新增赠送转移信息")
	@Json
	@Before(GiveTransferValidator.class)
	@RequestMapping("/givetransfer")
	@Permission(ADMINISTRATOR)
	public AjaxResult givetransfer() {
		final GiveTransferRequest boxItem = mapping(PREFIX, GiveTransferRequest.class);
		final Accountsinfo accountsinfo = Blade.create(Accountsinfo.class).findById(boxItem.getUserid());
		String ret = "";
		if(accountsinfo != null ) {
			ret = Db.executeCall(new OnConnection<String>(){
				@Override
				public String call(Connection conn) throws SQLException {
					CallableStatement cstmt = null;
					if(boxItem.getType()==0) {
						System.out.println("0-金币转移");
						cstmt = conn.prepareCall("{?=call [QPGameUserDB].[dbo].[testGive]( ?,? ) }");
					} else {
						System.out.println("1-银行金币转移");
						cstmt = conn.prepareCall("{?=call [QPGameUserDB].[dbo].[testInsureGive]( ?,?,? ) }");
					}
					ResultSet rs = null;
					//设置输入参数
					cstmt.registerOutParameter(1, Types.INTEGER);
					cstmt.setInt(2, boxItem.getUserid());
					cstmt.setInt(3, boxItem.getScore().intValue());
					if(boxItem.getType()==1) {
						cstmt.setString(4, HttpKit.getRequest().getRemoteAddr());
					}
					
					//发送参数
					rs = cstmt.executeQuery(); //注意： 所有调用存储过程的sequel语句都是使用executeQuery方法执行！！！
					
					String ret = "";
					//遍历结果
					while(rs.next()){
						try {  
							if (rs.findColumn("ret") > 0 ) { 
								ret = rs.getString("ret");
								System.out.println("返回结果"+ret);
								break;
							}   
						}  
						catch (SQLException e) {  
							continue;
						} 
					}
					int count = 0;
					while(cstmt.getMoreResults()) {
						count++;
						rs = cstmt.getResultSet();
						while(rs.next()){
							try {  
								if (rs.findColumn("ret") > 0 ) { 
									ret = rs.getString("ret");
									System.out.println("返回结果"+ret);
									break;
								}   
							}  
							catch (SQLException e) {  
								continue;
							} 
						}
						if(StrKit.notBlank(ret)) {
							break;
						}
					}
					Object o = null;
					if(count >1) {
						o = new Integer(1);
					}else {
						o = cstmt.getObject(1);
						if(o != null && JSON.toJSONString(o).equals("0")) {
							System.out.println("返回结果1:"+o);
						}
					}
					ret = o+","+ret;
					// 其他代码
					return ret;
				}
			});
			String[] result = ret.split(",");
			if(result[0] != null && result[0].equals("0")) {
				if(result.length>1) {
					return error(result[1]);
				}else {
					return error("未知错误.");
				}
			} else {
				doLogByGold(boxItem.getUserid(),UserRightType.GIVE_TRANSFER.code(),boxItem.getScore(),0L,"","");
				return success(result[1]);
			}
		} else {
			return error("用户信息不存在");
		}
	}
}
