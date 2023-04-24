package com.smallchill.game.model;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "platform")
@Table(name = "PlatformActiveNullityConfig")
@BindID(name = "id")
@SuppressWarnings("serial")
public class PlatformActiveNullityConfig extends BaseModel {
	private Integer id ;
	private Integer nullity ;
	private String platformactivename ;
	
	public PlatformActiveNullityConfig() {
	}
	@AutoID //mysql自增
	@SeqID(name = "SEQ_PlatformActiveNullityConfig") //oracle sequence自增
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
	
	public String getPlatformactivename(){
		return  platformactivename;
	}
	public void setPlatformactivename(String platformactivename ){
		this.platformactivename = platformactivename;
	}
}
