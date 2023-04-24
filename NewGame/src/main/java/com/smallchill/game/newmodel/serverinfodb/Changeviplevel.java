package com.smallchill.game.newmodel.serverinfodb;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameroomitemdb")
@Table(name = "ChangeVipLevel")
@BindID(name = "id")
@SuppressWarnings("serial")
public class Changeviplevel extends BaseModel {
	private Integer addtype ;
	private Integer id ;
	private Integer isaction ;
	private Integer lastip ;
	private Integer param1 ;
	private Integer param2 ;
	private Integer param3 ;
	private String account ;
	private Date addtime ;
	private Date lastaddtime ;
	
	public Changeviplevel() {
	}
	
	public Integer getAddtype(){
		return  addtype;
	}
	public void setAddtype(Integer addtype ){
		this.addtype = addtype;
	}
	@AutoID
	@SeqID(name = "SEQ_ChangeVipLevel")
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public Integer getIsaction(){
		return  isaction;
	}
	public void setIsaction(Integer isaction ){
		this.isaction = isaction;
	}
	
	public Integer getLastip(){
		return  lastip;
	}
	public void setLastip(Integer lastip ){
		this.lastip = lastip;
	}
	
	public Integer getParam1(){
		return  param1;
	}
	public void setParam1(Integer param1 ){
		this.param1 = param1;
	}
	
	public Integer getParam2(){
		return  param2;
	}
	public void setParam2(Integer param2 ){
		this.param2 = param2;
	}
	
	public Integer getParam3(){
		return  param3;
	}
	public void setParam3(Integer param3 ){
		this.param3 = param3;
	}
	
	public String getAccount(){
		return  account;
	}
	public void setAccount(String account ){
		this.account = account;
	}
	
	public Date getAddtime(){
		return  addtime;
	}
	public void setAddtime(Date addtime ){
		this.addtime = addtime;
	}
	
	public Date getLastaddtime(){
		return  lastaddtime;
	}
	public void setLastaddtime(Date lastaddtime ){
		this.lastaddtime = lastaddtime;
	}
}