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
@Table(name = "LivcardAssociator")
@BindID(name = "cardid")
@SuppressWarnings("serial")
public class LivcardAssociator extends BaseModel {
	private Integer cardid ;
	private Integer buildid ;
	private Integer cardtypeid ;
	private Integer gold ;
	private Integer nullity ;
	private Integer userange ;
	private BigDecimal cardprice ;
	private BigDecimal currency ;
	private String password ;
	private String salesperson ;
	private String serialid ;
	private Date applydate ;
	private Date builddate ;
	private Date validdate ;
	
	public LivcardAssociator() {
	}
	@AutoID
	@SeqID(name = "SEQ_LivcardAssociator")
	public Integer getCardid(){
		return  cardid;
	}
	public void setCardid(Integer cardid ){
		this.cardid = cardid;
	}
	
	public Integer getBuildid(){
		return  buildid;
	}
	public void setBuildid(Integer buildid ){
		this.buildid = buildid;
	}
	
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
	
	public Integer getNullity(){
		return  nullity;
	}
	public void setNullity(Integer nullity ){
		this.nullity = nullity;
	}
	
	public Integer getUserange(){
		return  userange;
	}
	public void setUserange(Integer userange ){
		this.userange = userange;
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
	
	public String getPassword(){
		return  password;
	}
	public void setPassword(String password ){
		this.password = password;
	}
	
	public String getSalesperson(){
		return  salesperson;
	}
	public void setSalesperson(String salesperson ){
		this.salesperson = salesperson;
	}
	
	public String getSerialid(){
		return  serialid;
	}
	public void setSerialid(String serialid ){
		this.serialid = serialid;
	}
	
	public Date getApplydate(){
		return  applydate;
	}
	public void setApplydate(Date applydate ){
		this.applydate = applydate;
	}
	
	public Date getBuilddate(){
		return  builddate;
	}
	public void setBuilddate(Date builddate ){
		this.builddate = builddate;
	}
	
	public Date getValiddate(){
		return  validdate;
	}
	public void setValiddate(Date validdate ){
		this.validdate = validdate;
	}
}
