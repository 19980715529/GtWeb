package com.smallchill.game.newmodel;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameroomitemdb")
@Table(name = "AA_BloodChangePlan")
@BindID(name = "Plan_Id")
@SuppressWarnings("serial")
public class AaBloodchangeplan extends BaseModel {
	private Integer Plan_Id ;
	private Integer bloodchange ;
	private Integer isrun ;
	private Integer serverid ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date runtime ;
	
	public AaBloodchangeplan() {
	}
	
	public Integer getBloodchange(){
		return  bloodchange;
	}
	public void setBloodchange(Integer bloodchange ){
		this.bloodchange = bloodchange;
	}
	
	public Integer getIsrun(){
		return  isrun;
	}
	public void setIsrun(Integer isrun ){
		this.isrun = isrun;
	}
	
	public Integer getServerid(){
		return  serverid;
	}
	public void setServerid(Integer serverid ){
		this.serverid = serverid;
	}
	
	public Date getRuntime(){
		return  runtime;
	}
	public void setRuntime(Date runtime ){
		this.runtime = runtime;
	}

	@AutoID
	@SeqID(name = "SEQ_AA_BloodChangePlan")
	public Integer getPlan_Id() {
		return Plan_Id;
	}
	public void setPlan_Id(Integer plan_Id) {
		Plan_Id = plan_Id;
	}
}
