package com.smallchill.system.model.request;

import java.util.Date;

import com.smallchill.core.base.model.BaseModel;

@SuppressWarnings("serial")
//角色表
public class BloodpondconfigRequest extends BaseModel {
	private Integer id ;
	private Integer bloodpondid ;
	private Integer serverid ;
	private Long bloodpondnowcontrolcount ;
	private Integer bloodchange ;
	private Date begintime ;
	
	public BloodpondconfigRequest() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBloodpondid() {
		return bloodpondid;
	}

	public void setBloodpondid(Integer bloodpondid) {
		this.bloodpondid = bloodpondid;
	}

	public Integer getServerid() {
		return serverid;
	}

	public void setServerid(Integer serverid) {
		this.serverid = serverid;
	}

	public Long getBloodpondnowcontrolcount() {
		return bloodpondnowcontrolcount;
	}

	public void setBloodpondnowcontrolcount(Long bloodpondnowcontrolcount) {
		this.bloodpondnowcontrolcount = bloodpondnowcontrolcount;
	}

	public Date getBegintime() {
		return begintime;
	}

	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}

	public Integer getBloodchange() {
		return bloodchange;
	}

	public void setBloodchange(Integer bloodchange) {
		this.bloodchange = bloodchange;
	}
}
