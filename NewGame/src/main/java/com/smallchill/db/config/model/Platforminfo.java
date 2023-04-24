package com.smallchill.db.config.model;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "nativeWeb")
@Table(name = "PlatformInfo")
@BindID(name = "id")
@SuppressWarnings("serial")
public class Platforminfo extends BaseModel {
	private Integer id ;
	private String platformname ;
	
	public Platforminfo() {
	}
	
	@AutoID
	@SeqID(name = "SEQ_Platforminfo")
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public String getPlatformname(){
		return  platformname;
	}
	public void setPlatformname(String platformname ){
		this.platformname = platformname;
	}
}
