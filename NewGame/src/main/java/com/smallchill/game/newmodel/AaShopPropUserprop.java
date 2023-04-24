package com.smallchill.game.newmodel;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameuserdb")
@Table(name = "AA_Shop_Prop_UserProp")
@SuppressWarnings("serial")
public class AaShopPropUserprop extends BaseModel {
	private Integer Prop_Id ;
	private Integer User_Id ;
	private Integer isuse ;
	private Integer remaintime ;
	private Long amount ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date starttime ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date stoptime ;
	
	public AaShopPropUserprop() {
	}
	
	public Integer getIsuse(){
		return  isuse;
	}
	public void setIsuse(Integer isuse ){
		this.isuse = isuse;
	}
	
	public Integer getRemaintime(){
		return  remaintime;
	}
	public void setRemaintime(Integer remaintime ){
		this.remaintime = remaintime;
	}
	
	public Long getAmount(){
		return  amount;
	}
	public void setAmount(Long amount ){
		this.amount = amount;
	}
	
	public Date getStarttime(){
		return  starttime;
	}
	public void setStarttime(Date starttime ){
		this.starttime = starttime;
	}
	
	public Date getStoptime(){
		return  stoptime;
	}
	public void setStoptime(Date stoptime ){
		this.stoptime = stoptime;
	}
	@AssignID
	public Integer getProp_Id() {
		return Prop_Id;
	}

	public void setProp_Id(Integer prop_Id) {
		Prop_Id = prop_Id;
	}
	@AssignID
	public Integer getUser_Id() {
		return User_Id;
	}

	public void setUser_Id(Integer user_Id) {
		User_Id = user_Id;
	}
}
