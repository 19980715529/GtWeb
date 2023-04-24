package com.smallchill.game.model;

import java.math.BigDecimal;
import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "treasure")
@Table(name = "GlobalLivcard")
@BindID(name = "cardtypeid")
@SuppressWarnings("serial")
public class GlobalLivcard extends BaseModel {
	private Integer cardtypeid ;
	private Integer gold ;
	private String cardname ;
	private BigDecimal cardprice ;
	private BigDecimal currency ;
	private Date inputdate ;
	
	public GlobalLivcard() {
	}
	@AutoID
	@SeqID(name = "SEQ_GlobalLivcard")
	public Integer getCardtypeid(){
		return  cardtypeid;
	}
	public void setCardtypeid(Integer cardtypeid ){
		this.cardtypeid = cardtypeid;
	}
	
	public Integer getGold(){
		return  gold;
	}
	public void setGold(Integer gold ){
		this.gold = gold;
	}
	
	public String getCardname(){
		return  cardname;
	}
	public void setCardname(String cardname ){
		this.cardname = cardname;
	}
	
	public BigDecimal getCardprice(){
		return  cardprice;
	}
	public void setCardprice(BigDecimal cardprice ){
		this.cardprice = cardprice;
	}
	
	public BigDecimal getCurrency(){
		return  currency;
	}
	public void setCurrency(BigDecimal currency ){
		this.currency = currency;
	}
	
	public Date getInputdate(){
		return  inputdate;
	}
	public void setInputdate(Date inputdate ){
		this.inputdate = inputdate;
	}
}
