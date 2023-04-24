package com.smallchill.db.config.model;

import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "treasure")
@Table(name = "TreasureBoxConfig")
@BindID(name = "id")
@SuppressWarnings("serial")
public class TreasureBoxConfig extends BaseModel {
	private Integer id ;
	private Integer itemid ;
	private Integer itemrate ;
	private Long flasemaxmedalconut ;
	private Long flaseminmedalconut ;
	private Long maxmedalconut ;
	private Long minmedalconut ;
	
	public TreasureBoxConfig() {
	}
	
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public Integer getItemid(){
		return  itemid;
	}
	public void setItemid(Integer itemid ){
		this.itemid = itemid;
	}
	
	public Integer getItemrate(){
		return  itemrate;
	}
	public void setItemrate(Integer itemrate ){
		this.itemrate = itemrate;
	}
	
	public Long getFlasemaxmedalconut(){
		return  flasemaxmedalconut;
	}
	public void setFlasemaxmedalconut(Long flasemaxmedalconut ){
		this.flasemaxmedalconut = flasemaxmedalconut;
	}
	
	public Long getFlaseminmedalconut(){
		return  flaseminmedalconut;
	}
	public void setFlaseminmedalconut(Long flaseminmedalconut ){
		this.flaseminmedalconut = flaseminmedalconut;
	}
	
	public Long getMaxmedalconut(){
		return  maxmedalconut;
	}
	public void setMaxmedalconut(Long maxmedalconut ){
		this.maxmedalconut = maxmedalconut;
	}
	
	public Long getMinmedalconut(){
		return  minmedalconut;
	}
	public void setMinmedalconut(Long minmedalconut ){
		this.minmedalconut = minmedalconut;
	}
}
