package com.smallchill.db.config.model;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "treasure")
@Table(name = "ReceiveScoreConfig")
@BindID(name = "id")
@SuppressWarnings("serial")
public class ReceiveScoreConfig extends BaseModel {
	private Integer id ;
	private Integer allowcounts ;
	private Integer nullity ;
	private Integer scoreinterval ;
	private Long scorecount ;
	
	public ReceiveScoreConfig() {
	}
	
	@AutoID
	@SeqID(name = "SEQ_ReceiveScoreConfig")
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public Integer getAllowcounts(){
		return  allowcounts;
	}
	public void setAllowcounts(Integer allowcounts ){
		this.allowcounts = allowcounts;
	}
	
	public Integer getNullity(){
		return  nullity;
	}
	public void setNullity(Integer nullity ){
		this.nullity = nullity;
	}
	
	public Integer getScoreinterval(){
		return  scoreinterval;
	}
	public void setScoreinterval(Integer scoreinterval ){
		this.scoreinterval = scoreinterval;
	}
	
	public Long getScorecount(){
		return  scorecount;
	}
	public void setScorecount(Long scorecount ){
		this.scorecount = scorecount;
	}
}
