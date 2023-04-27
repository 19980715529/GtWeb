package com.smallchill.game.model;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "platform")
@Table(name = "UserConTrolScoreRecord")
@BindID(name = "id")
@SuppressWarnings("serial")
public class UserConTrolScoreRecord extends BaseModel {
	private Integer id ;
	private Integer cheatingratenew ;
	private Integer cheatingrateold ;
	private Integer controlscoreid ;
	private Integer userid ;
	private String adminuser ;
	private Long cheatingscorenow ;
	private Long cheatingscoreset ;
	private String remark ;
	private Date cheationgtime ;
	
	public UserConTrolScoreRecord() {
	}
	@AutoID //mysql自增
	@SeqID(name = "SEQ_UserConTrolScoreRecord") //oracle sequence自增
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public Integer getCheatingratenew(){
		return  cheatingratenew;
	}
	public void setCheatingratenew(Integer cheatingratenew ){
		this.cheatingratenew = cheatingratenew;
	}
	
	public Integer getCheatingrateold(){
		return  cheatingrateold;
	}
	public void setCheatingrateold(Integer cheatingrateold ){
		this.cheatingrateold = cheatingrateold;
	}
	
	public Integer getControlscoreid(){
		return  controlscoreid;
	}
	public void setControlscoreid(Integer controlscoreid ){
		this.controlscoreid = controlscoreid;
	}
	
	public Integer getUserid(){
		return  userid;
	}
	public void setUserid(Integer userid ){
		this.userid = userid;
	}
	
	public String getAdminuser(){
		return  adminuser;
	}
	public void setAdminuser(String adminuser ){
		this.adminuser = adminuser;
	}
	
	public Long getCheatingscorenow(){
		return  cheatingscorenow;
	}
	public void setCheatingscorenow(Long cheatingscorenow ){
		this.cheatingscorenow = cheatingscorenow;
	}
	
	public Long getCheatingscoreset(){
		return  cheatingscoreset;
	}
	public void setCheatingscoreset(Long cheatingscoreset ){
		this.cheatingscoreset = cheatingscoreset;
	}
	
	public String getRemark(){
		return  remark;
	}
	public void setRemark(String remark ){
		this.remark = remark;
	}
	
	public Date getCheationgtime(){
		return  cheationgtime;
	}
	public void setCheationgtime(Date cheationgtime ){
		this.cheationgtime = cheationgtime;
	}
}
