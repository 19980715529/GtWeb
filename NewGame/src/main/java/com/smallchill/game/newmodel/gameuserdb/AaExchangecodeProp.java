package com.smallchill.game.newmodel.gameuserdb;

import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameuserdb")
@Table(name = "AA_ExchangeCode_Prop")
@SuppressWarnings("serial")
public class AaExchangecodeProp extends BaseModel {
	private Integer propId ;
	private Integer amount ;
	
	public AaExchangecodeProp() {
	}
	
	public Integer getPropId(){
		return  propId;
	}
	public void setPropId(Integer propId ){
		this.propId = propId;
	}
	
	public Integer getAmount(){
		return  amount;
	}
	public void setAmount(Integer amount ){
		this.amount = amount;
	}
}