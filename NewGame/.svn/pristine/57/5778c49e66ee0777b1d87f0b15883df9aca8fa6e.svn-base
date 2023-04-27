package com.smallchill.game.newmodel.logindb;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "logindb")
@Table(name = "Notice")
@BindID(name = "id")
@SuppressWarnings("serial")
public class Notice extends BaseModel {
	private Integer id ;
	private Integer clientid ;
	private String notice ;
	
	public Notice() {
	}

	@AutoID
	@SeqID(name = "SEQ_Notice")
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	public Integer getClientid(){
		return  clientid;
	}
	public void setClientid(Integer clientid ){
		this.clientid = clientid;
	}
	
	public String getNotice(){
		return  notice;
	}
	public void setNotice(String notice ){
		this.notice = notice;
	}
}