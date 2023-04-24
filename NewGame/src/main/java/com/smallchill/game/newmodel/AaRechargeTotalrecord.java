package com.smallchill.game.newmodel;

import java.math.BigDecimal;
import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameuserdb")
@Table(name = "AA_Recharge_TotalRecord")
@BindID(name = "id")
@SuppressWarnings("serial")
public class AaRechargeTotalrecord extends BaseModel {
	private Long id ;
	private Integer amount ;
	private Integer BuyType_Id ;
	private Integer Buy_Id ;
	private Integer getgold ;
	private Integer unitgold ;
	private Integer User_Id ;
	private String Transaction_id ;
	private BigDecimal unitrmb ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date logtime ;
	
	public AaRechargeTotalrecord() {
	}
	@AutoID
	@SeqID(name = "SEQ_AA_Recharge_TotalRecord")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getBuyType_Id() {
		return BuyType_Id;
	}

	public void setBuyType_Id(Integer buyType_Id) {
		BuyType_Id = buyType_Id;
	}

	public Integer getBuy_Id() {
		return Buy_Id;
	}

	public void setBuy_Id(Integer buy_Id) {
		Buy_Id = buy_Id;
	}

	public Integer getGetgold() {
		return getgold;
	}

	public void setGetgold(Integer getgold) {
		this.getgold = getgold;
	}

	public Integer getUnitgold() {
		return unitgold;
	}

	public void setUnitgold(Integer unitgold) {
		this.unitgold = unitgold;
	}

	public Integer getUser_Id() {
		return User_Id;
	}

	public void setUser_Id(Integer user_Id) {
		User_Id = user_Id;
	}

	public String getTransaction_id() {
		return Transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		Transaction_id = transaction_id;
	}

	public BigDecimal getUnitrmb() {
		return unitrmb;
	}

	public void setUnitrmb(BigDecimal unitrmb) {
		this.unitrmb = unitrmb;
	}

	public Date getLogtime() {
		return logtime;
	}

	public void setLogtime(Date logtime) {
		this.logtime = logtime;
	}
}
