package com.smallchill.db.config.model;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "treasure")
@Table(name = "TreasureBoxItem")
@BindID(name = "id")
@SuppressWarnings("serial")
public class TreasureBoxItem extends BaseModel {
	private Integer id ;
	private String free ;
	private String treasureboxtype ;
	
	public TreasureBoxItem() {
	}
	
	@AutoID
	@SeqID(name = "SEQ_TreasureBoxItem")
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public String getFree(){
		return  free;
	}
	public void setFree(String free ){
		this.free = free;
	}
	
	public String getTreasureboxtype(){
		return  treasureboxtype;
	}
	public void setTreasureboxtype(String treasureboxtype ){
		this.treasureboxtype = treasureboxtype;
	}
}
