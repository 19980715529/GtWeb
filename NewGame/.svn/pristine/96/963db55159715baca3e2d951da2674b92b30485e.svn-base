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
@Table(name = "GlobalAppInfo")
@BindID(name = "appid")
@SuppressWarnings("serial")
public class GlobalAppInfo extends BaseModel {
	private Integer appid ;
	private Integer platformid ;
	private Integer sortid ;
	private Integer tagid ;
	private BigDecimal attachcurrency ;
	private String description ;
	private BigDecimal presentcurrency ;
	private BigDecimal price ;
	private String productid ;
	private String productname ;
	private Date collectdate ;
	
	public GlobalAppInfo() {
	}
	@AutoID
	@SeqID(name = "SEQ_GlobalAppInfo")
	public Integer getAppid(){
		return  appid;
	}
	public void setAppid(Integer appid ){
		this.appid = appid;
	}
	
	public Integer getPlatformid(){
		return  platformid;
	}
	public void setPlatformid(Integer platformid ){
		this.platformid = platformid;
	}
	
	public Integer getSortid(){
		return  sortid;
	}
	public void setSortid(Integer sortid ){
		this.sortid = sortid;
	}
	
	public Integer getTagid(){
		return  tagid;
	}
	public void setTagid(Integer tagid ){
		this.tagid = tagid;
	}
	
	public BigDecimal getAttachcurrency(){
		return  attachcurrency;
	}
	public void setAttachcurrency(BigDecimal attachcurrency ){
		this.attachcurrency = attachcurrency;
	}
	
	public String getDescription(){
		return  description;
	}
	public void setDescription(String description ){
		this.description = description;
	}
	
	public BigDecimal getPresentcurrency(){
		return  presentcurrency;
	}
	public void setPresentcurrency(BigDecimal presentcurrency ){
		this.presentcurrency = presentcurrency;
	}
	
	public BigDecimal getPrice(){
		return  price;
	}
	public void setPrice(BigDecimal price ){
		this.price = price;
	}
	
	public String getProductid(){
		return  productid;
	}
	public void setProductid(String productid ){
		this.productid = productid;
	}
	
	public String getProductname(){
		return  productname;
	}
	public void setProductname(String productname ){
		this.productname = productname;
	}
	
	public Date getCollectdate(){
		return  collectdate;
	}
	public void setCollectdate(Date collectdate ){
		this.collectdate = collectdate;
	}
}
