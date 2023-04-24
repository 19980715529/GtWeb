package com.smallchill.game.model;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "account")
@Table(name = "MemberType")
@BindID(name = "typeid")
@SuppressWarnings("serial")
public class MemberType extends BaseModel {
	private Integer typeid ;
	private String typename ;
	
	public MemberType() {
	}
	@AutoID
	@SeqID(name = "SEQ_MEMBERTYPE")
	public Integer getTypeid(){
		return  typeid;
	}
	public void setTypeid(Integer typeid ){
		this.typeid = typeid;
	}
	
	public String getTypename(){
		return  typename;
	}
	public void setTypename(String typename ){
		this.typename = typename;
	}
}
