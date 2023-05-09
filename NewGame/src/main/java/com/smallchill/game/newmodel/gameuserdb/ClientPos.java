package com.smallchill.game.newmodel.gameuserdb;

import java.math.BigDecimal;
import java.util.Date;

import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "logindb")
@Table(name = "ClientPos")
@BindID(name = "clientType")
@SuppressWarnings("serial")
public class ClientPos extends BaseModel {
	private String name ;
	private BigDecimal ratio ;
	private Integer clientType ;
	private String remarks;
	private Integer isLog;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getRatio() {
		return ratio;
	}

	public void setRatio(BigDecimal ratio) {
		this.ratio = ratio;
	}

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getIsLog() {
		return isLog;
	}

	public void setIsLog(Integer isLog) {
		this.isLog = isLog;
	}
}