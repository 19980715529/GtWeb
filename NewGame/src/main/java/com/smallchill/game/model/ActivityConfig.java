package com.smallchill.game.model;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "nativeWeb")
@Table(name = "ActivityConfig")
@BindID(name = "id")
@SuppressWarnings("serial")
public class ActivityConfig extends BaseModel {
	private Integer id ;
	private Integer freecounts ;
	private Integer nullity ;
	private String activityname ;
	private Long givescore ;
	private String imgurl ;
	private Long price ;
	private String remarks ;
	
	public ActivityConfig() {
	}
	@AutoID
	@SeqID(name = "SEQ_ActivityConfig")
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public Integer getFreecounts(){
		return  freecounts;
	}
	public void setFreecounts(Integer freecounts ){
		this.freecounts = freecounts;
	}
	
	public Integer getNullity(){
		return  nullity;
	}
	public void setNullity(Integer nullity ){
		this.nullity = nullity;
	}
	
	public String getActivityname(){
		return  activityname;
	}
	public void setActivityname(String activityname ){
		this.activityname = activityname;
	}
	
	public Long getGivescore(){
		return  givescore;
	}
	public void setGivescore(Long givescore ){
		this.givescore = givescore;
	}
	
	public String getImgurl(){
		return  imgurl;
	}
	public void setImgurl(String imgurl ){
		this.imgurl = imgurl;
	}
	
	public Long getPrice(){
		return  price;
	}
	public void setPrice(Long price ){
		this.price = price;
	}
	
	public String getRemarks(){
		return  remarks;
	}
	public void setRemarks(String remarks ){
		this.remarks = remarks;
	}
}
