package com.smallchill.game.newmodel.logindb;

import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "logindb")
@Table(name = "NoticeDetail")
@BindID(name = "id")
@SuppressWarnings("serial")
public class Noticedetail extends BaseModel {
	private Integer id ;
	private Integer clientid ;
	private Integer version ;
	private String content ;
	private String name ;
	
	public Noticedetail() {
	}
	
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
	
	public String getName(){
		return  name;
	}
	public void setName(String name ){
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}