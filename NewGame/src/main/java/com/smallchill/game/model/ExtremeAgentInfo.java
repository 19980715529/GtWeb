package com.smallchill.game.model;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "treasure")
@Table(name = "ExtremeAgentInfo")
@BindID(name = "id")
@SuppressWarnings("serial")
public class ExtremeAgentInfo extends BaseModel {
	private Integer id ;
	private Integer branchuserid ;
	private Integer extremeuserid ;
	
	public ExtremeAgentInfo() {
	}
	@AutoID
	@SeqID(name = "SEQ_ExtremeAgentInfo")
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public Integer getBranchuserid(){
		return  branchuserid;
	}
	public void setBranchuserid(Integer branchuserid ){
		this.branchuserid = branchuserid;
	}
	
	public Integer getExtremeuserid(){
		return  extremeuserid;
	}
	public void setExtremeuserid(Integer extremeuserid ){
		this.extremeuserid = extremeuserid;
	}
}
