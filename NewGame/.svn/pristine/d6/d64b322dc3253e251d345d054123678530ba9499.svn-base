package com.smallchill.game.newmodel.logindb;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "logindb")
@Table(name = "LoginIpForVip")
@BindID(name = "account")
@SuppressWarnings("serial")
public class Loginipforvip extends BaseModel {
	private String account ;
	private Integer vipip;
	private Integer viplevel ;
	private String desc ;
	private Date addtime ;
	
	public Loginipforvip() {
	}

	@AssignID
	public String getAccount(){
		return  account;
	}
	public void setAccount(String account ){
		this.account = account;
	}
	
	public Integer getVipip(){
		return  vipip;
	}
	public void setVipip(Integer vipip ){
		this.vipip = vipip;
	}
	
	public Integer getViplevel(){
		return  viplevel;
	}
	public void setViplevel(Integer viplevel ){
		this.viplevel = viplevel;
	}
	
	public String getDesc(){
		return  desc;
	}
	public void setDesc(String desc ){
		this.desc = desc;
	}
	
	public Date getAddtime(){
		return  addtime;
	}
	public void setAddtime(Date addtime ){
		this.addtime = addtime;
	}
}