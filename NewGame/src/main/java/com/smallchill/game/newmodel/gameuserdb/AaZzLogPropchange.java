package com.smallchill.game.newmodel.gameuserdb;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameuserdb")
@Table(name = "AA_ZZ_Log_PropChange")
@BindID(name = "Log_Id")
@SuppressWarnings("serial")
public class AaZzLogPropchange extends BaseModel {
	private Long Log_Id ;
	private Long aftamount ;
	private Integer ChangeType_Id ;
	private Integer isfromsystem ;
	private Integer kindid ;
	private Integer no ;
	private Long preamount ;
	private Integer Prop_Id ;
	private Integer serverid ;
	private Integer tableid ;
	private Integer User_Id ;
	private Long amount ;
	private String remark ;
	private String CurChip;
	private Date logtime ;
	
	public AaZzLogPropchange() {
	}
	@AutoID
	@SeqID(name = "SEQ_AA_ZZ_Log_PropChange")
	public Long getLog_Id() {
		return Log_Id;
	}

	public void setLog_Id(Long log_Id) {
		Log_Id = log_Id;
	}

	public Integer getChangeType_Id() {
		return ChangeType_Id;
	}

	public void setChangeType_Id(Integer changeType_Id) {
		ChangeType_Id = changeType_Id;
	}

	public Integer getIsfromsystem() {
		return isfromsystem;
	}

	public void setIsfromsystem(Integer isfromsystem) {
		this.isfromsystem = isfromsystem;
	}

	public Integer getKindid() {
		return kindid;
	}

	public void setKindid(Integer kindid) {
		this.kindid = kindid;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public Integer getProp_Id() {
		return Prop_Id;
	}

	public void setProp_Id(Integer prop_Id) {
		Prop_Id = prop_Id;
	}

	public Integer getServerid() {
		return serverid;
	}

	public void setServerid(Integer serverid) {
		this.serverid = serverid;
	}

	public Integer getTableid() {
		return tableid;
	}

	public void setTableid(Integer tableid) {
		this.tableid = tableid;
	}

	public Integer getUser_Id() {
		return User_Id;
	}

	public void setUser_Id(Integer user_Id) {
		User_Id = user_Id;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCurChip() {
		return CurChip;
	}

	public void setCurChip(String curChip) {
		CurChip = curChip;
	}

	public Date getLogtime() {
		return logtime;
	}

	public void setLogtime(Date logtime) {
		this.logtime = logtime;
	}
	public Long getAftamount() {
		return aftamount;
	}
	public void setAftamount(Long aftamount) {
		this.aftamount = aftamount;
	}
	public Long getPreamount() {
		return preamount;
	}
	public void setPreamount(Long preamount) {
		this.preamount = preamount;
	}
}