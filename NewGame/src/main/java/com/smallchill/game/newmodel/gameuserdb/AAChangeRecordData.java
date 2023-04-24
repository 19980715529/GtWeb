package com.smallchill.game.newmodel.gameuserdb;

import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameuserdb")
@Table(name = "AA_Change_Record_Data")
@SuppressWarnings("serial")
public class AAChangeRecordData extends BaseModel {
	private Integer areaId ;
	private Integer giveOrRev ;
	private Integer id ;
	
	public AAChangeRecordData() {
	}
	
	public Integer getAreaId(){
		return  areaId;
	}
	public void setAreaId(Integer areaId ){
		this.areaId = areaId;
	}
	
	public Integer getGiveOrRev(){
		return  giveOrRev;
	}
	public void setGiveOrRev(Integer giveOrRev ){
		this.giveOrRev = giveOrRev;
	}
	
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
}