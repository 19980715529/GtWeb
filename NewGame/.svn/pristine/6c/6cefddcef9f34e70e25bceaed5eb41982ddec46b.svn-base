package com.smallchill.game.model;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "platform")
@Table(name = "GiftInfoConfig")
@BindID(name = "id")
@SuppressWarnings("serial")
public class GiftInfoConfig extends BaseModel {
	private Integer id ;
	private Integer condition ;
	private Integer nullity ;
	private Integer revenue ;
	private String giftzname ;
	private Long givescore ;
	
	public GiftInfoConfig() {
	}
	@AutoID
	@SeqID(name = "SEQ_GiftInfoConfig")
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public Integer getNullity(){
		return  nullity;
	}
	public void setNullity(Integer nullity ){
		this.nullity = nullity;
	}
	
	public Integer getRevenue(){
		return  revenue;
	}
	public void setRevenue(Integer revenue ){
		this.revenue = revenue;
	}
	
	public String getGiftzname(){
		return  giftzname;
	}
	public void setGiftzname(String giftzname ){
		this.giftzname = giftzname;
	}
	
	public Long getGivescore(){
		return  givescore;
	}
	public void setGivescore(Long givescore ){
		this.givescore = givescore;
	}
	public Integer getCondition() {
		return condition;
	}
	public void setCondition(Integer condition) {
		this.condition = condition;
	}
}
