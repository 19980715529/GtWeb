package com.smallchill.game.model;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "treasure")
@Table(name = "UserMedalChangeInfo")
@BindID(name = "id")
@SuppressWarnings("serial")
public class UserMedalChangeInfo extends BaseModel {
	private Integer id ;
	private Integer operationuserid ;
	private Integer userid ;
	private Long beforemedal ;
	private String clientip ;
	private Long entermedal ;
	private Long medalchange ;
	private String nickname ;
	private String operationname ;
	private String remark ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date inserttime ;
	
	public UserMedalChangeInfo() {
	}
	@AutoID
	@SeqID(name = "SEQ_UserMedalChangeInfo")
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public Integer getOperationuserid(){
		return  operationuserid;
	}
	public void setOperationuserid(Integer operationuserid ){
		this.operationuserid = operationuserid;
	}
	
	public Integer getUserid(){
		return  userid;
	}
	public void setUserid(Integer userid ){
		this.userid = userid;
	}
	
	public Long getBeforemedal(){
		return  beforemedal;
	}
	public void setBeforemedal(Long beforemedal ){
		this.beforemedal = beforemedal;
	}
	
	public String getClientip(){
		return  clientip;
	}
	public void setClientip(String clientip ){
		this.clientip = clientip;
	}
	
	public Long getEntermedal(){
		return  entermedal;
	}
	public void setEntermedal(Long entermedal ){
		this.entermedal = entermedal;
	}
	
	public Long getMedalchange(){
		return  medalchange;
	}
	public void setMedalchange(Long medalchange ){
		this.medalchange = medalchange;
	}
	
	public String getNickname(){
		return  nickname;
	}
	public void setNickname(String nickname ){
		this.nickname = nickname;
	}
	
	public String getOperationname(){
		return  operationname;
	}
	public void setOperationname(String operationname ){
		this.operationname = operationname;
	}
	
	public String getRemark(){
		return  remark;
	}
	public void setRemark(String remark ){
		this.remark = remark;
	}
	
	public Date getInserttime(){
		return  inserttime;
	}
	public void setInserttime(Date inserttime ){
		this.inserttime = inserttime;
	}
}
