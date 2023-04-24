package com.smallchill.game.model;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "nativeWeb")
@Table(name = "VisitingConfig")
@BindID(name = "id")
@SuppressWarnings("serial")
public class VisitingConfig extends BaseModel {
	private Integer id ;
	private Integer nullity ;
	private Integer receivecounts ;
	private Integer receivescore ;
	
	public VisitingConfig() {
	}
	@AutoID
	@SeqID(name = "SEQ_VisitingConfig")
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
	
	public Integer getReceivecounts(){
		return  receivecounts;
	}
	public void setReceivecounts(Integer receivecounts ){
		this.receivecounts = receivecounts;
	}
	
	public Integer getReceivescore(){
		return  receivescore;
	}
	public void setReceivescore(Integer receivescore ){
		this.receivescore = receivescore;
	}
}
