package com.smallchill.game.model.request;

import com.smallchill.core.base.model.BaseModel;

@SuppressWarnings("serial")
public class AwardInfoRequest extends BaseModel {
	private Integer awardid ;
	private Integer buycount ;
	private Integer inventory ;
	private Integer ismember ;
	private Integer isreturn ;
	private String needinfo ;
	private Integer nullity ;
	private Integer price ;
	private Integer sortid ;
	private Integer typeid ;
	private String awardname ;
	private String bigimage ;
	private String description ;
	private String smallimage ;
	
	public AwardInfoRequest() {
	}
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
	public String getNeedinfo() {
		return needinfo;
	}
	public void setNeedinfo(String needinfo) {
		this.needinfo = needinfo;
	}
}
