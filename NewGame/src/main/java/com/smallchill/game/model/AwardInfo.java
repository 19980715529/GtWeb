package com.smallchill.game.model;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "nativeWeb")
@Table(name = "AwardInfo")
@BindID(name = "awardid")
@SuppressWarnings("serial")
public class AwardInfo extends BaseModel {
	private Integer awardid ;
	private Integer buycount ;
	private Integer inventory ;
	private Integer ismember ;
	private Integer isreturn ;
	private Integer needinfo ;
	private Integer nullity ;
	private Integer price ;
	private Integer sortid ;
	private Integer typeid ;
	private String awardname ;
	private String bigimage ;
	private String description ;
	private String smallimage ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date collectdate ;
	
	public AwardInfo() {
	}
	@AutoID
	@SeqID(name = "SEQ_AwardInfo")
	public Integer getAwardid(){
		return  awardid;
	}
	public void setAwardid(Integer awardid ){
		this.awardid = awardid;
	}
	
	public Integer getBuycount(){
		return  buycount;
	}
	public void setBuycount(Integer buycount ){
		this.buycount = buycount;
	}
	
	public Integer getInventory(){
		return  inventory;
	}
	public void setInventory(Integer inventory ){
		this.inventory = inventory;
	}
	
	public Integer getIsmember(){
		return  ismember;
	}
	public void setIsmember(Integer ismember ){
		this.ismember = ismember;
	}
	
	public Integer getIsreturn(){
		return  isreturn;
	}
	public void setIsreturn(Integer isreturn ){
		this.isreturn = isreturn;
	}
	
	public Integer getNeedinfo(){
		return  needinfo;
	}
	public void setNeedinfo(Integer needinfo ){
		this.needinfo = needinfo;
	}
	
	public Integer getNullity(){
		return  nullity;
	}
	public void setNullity(Integer nullity ){
		this.nullity = nullity;
	}
	
	public Integer getPrice(){
		return  price;
	}
	public void setPrice(Integer price ){
		this.price = price;
	}
	
	public Integer getSortid(){
		return  sortid;
	}
	public void setSortid(Integer sortid ){
		this.sortid = sortid;
	}
	
	public Integer getTypeid(){
		return  typeid;
	}
	public void setTypeid(Integer typeid ){
		this.typeid = typeid;
	}
	
	public String getAwardname(){
		return  awardname;
	}
	public void setAwardname(String awardname ){
		this.awardname = awardname;
	}
	
	public String getBigimage(){
		return  bigimage;
	}
	public void setBigimage(String bigimage ){
		this.bigimage = bigimage;
	}
	
	public String getDescription(){
		return  description;
	}
	public void setDescription(String description ){
		this.description = description;
	}
	
	public String getSmallimage(){
		return  smallimage;
	}
	public void setSmallimage(String smallimage ){
		this.smallimage = smallimage;
	}
	
	public Date getCollectdate(){
		return  collectdate;
	}
	public void setCollectdate(Date collectdate ){
		this.collectdate = collectdate;
	}
}
