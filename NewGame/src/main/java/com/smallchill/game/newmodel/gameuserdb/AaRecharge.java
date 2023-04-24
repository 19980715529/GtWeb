package com.smallchill.game.newmodel.gameuserdb;

import java.math.BigDecimal;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameuserdb")
@Table(name = "AA_Recharge")
@BindID(name = "Buy_Id")
@SuppressWarnings("serial")
public class AaRecharge extends BaseModel {
	private Integer Buy_Id ;
	private String IOS_Product_Id ;
	private Integer gold ;
	private BigDecimal payrmb ;
	private Integer clienttype ;
	private Integer type_id ;
	
	public AaRecharge() {
	}
	
	public Integer getGold(){
		return  gold;
	}
	public void setGold(Integer gold ){
		this.gold = gold;
	}
	
	public Integer getClienttype(){
		return  clienttype;
	}
	public void setClienttype(Integer clienttype ){
		this.clienttype = clienttype;
	}
	
	public BigDecimal getPayrmb(){
		return  payrmb;
	}
	public void setPayrmb(BigDecimal payrmb ){
		this.payrmb = payrmb;
	}
	@AutoID
	@SeqID(name = "SEQ_AA_Recharge")
	public Integer getBuy_Id() {
		return Buy_Id;
	}
	public void setBuy_Id(Integer buy_Id) {
		Buy_Id = buy_Id;
	}
	public Integer getType_id() {
		return type_id;
	}
	public void setType_id(Integer type_id) {
		this.type_id = type_id;
	}
	public String getIOS_Product_Id() {
		return IOS_Product_Id;
	}
	public void setIOS_Product_Id(String iOS_Product_Id) {
		IOS_Product_Id = iOS_Product_Id;
	}
}
