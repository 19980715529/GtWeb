package com.smallchill.game.newmodel.gameuserdb;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameuserdb")
@Table(name = "AA_ExchangeCode")
@BindID(name = "Exchange_Id")
@SuppressWarnings("serial")
public class AaExchangecode extends BaseModel {
	private Integer exchangeId ;
	private Integer exchangebatch ;
	private Integer exchangestate ;
	private Integer type ;
	private String exchangecode ;
	private Date maketime ;
	
	public AaExchangecode() {
	}
	
	@AutoID
	@SeqID(name = "SEQ_AA_ExchangeCode")
	public Integer getExchangeId(){
		return  exchangeId;
	}
	public void setExchangeId(Integer exchangeId ){
		this.exchangeId = exchangeId;
	}
	
	public Integer getExchangebatch(){
		return  exchangebatch;
	}
	public void setExchangebatch(Integer exchangebatch ){
		this.exchangebatch = exchangebatch;
	}
	
	public Integer getExchangestate(){
		return  exchangestate;
	}
	public void setExchangestate(Integer exchangestate ){
		this.exchangestate = exchangestate;
	}
	
	public Integer getType(){
		return  type;
	}
	public void setType(Integer type ){
		this.type = type;
	}
	
	public String getExchangecode(){
		return  exchangecode;
	}
	public void setExchangecode(String exchangecode ){
		this.exchangecode = exchangecode;
	}
	
	public Date getMaketime(){
		return  maketime;
	}
	public void setMaketime(Date maketime ){
		this.maketime = maketime;
	}
}
