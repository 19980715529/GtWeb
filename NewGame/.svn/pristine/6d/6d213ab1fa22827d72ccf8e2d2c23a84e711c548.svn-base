package com.smallchill.game.newmodel;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "master")
@Table(name = "RoomDesc")
@BindID(name = "id")
@SuppressWarnings("serial")
public class Roomdesc extends BaseModel {
	private Integer id ;
	private String description ;
	
	public Roomdesc() {
	}
	@AutoID
	@SeqID(name = "SEQ_RoomDesc")
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public String getDescription(){
		return  description;
	}
	public void setDescription(String description ){
		this.description = description;
	}
}
