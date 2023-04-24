package com.smallchill.system.treasure.model;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "treasure")
@Table(name = "GlobalShareInfo")
@BindID(name = "shareid")
@SuppressWarnings("serial")
//充值类型表
public class GlobalShareInfo extends BaseModel {
	private Integer shareid ;
	private String sharealias ;
	private String sharename ;
	private String sharenote ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date collectdate ;
	
	public GlobalShareInfo() {
	}
	
	@AssignID
	public Integer getShareid(){
		return  shareid;
	}
	public void setShareid(Integer shareid ){
		this.shareid = shareid;
	}
	
	public String getSharealias(){
		return  sharealias;
	}
	public void setSharealias(String sharealias ){
		this.sharealias = sharealias;
	}
	
	public String getSharename(){
		return  sharename;
	}
	public void setSharename(String sharename ){
		this.sharename = sharename;
	}
	
	public String getSharenote(){
		return  sharenote;
	}
	public void setSharenote(String sharenote ){
		this.sharenote = sharenote;
	}
	
	public Date getCollectdate(){
		return  collectdate;
	}
	public void setCollectdate(Date collectdate ){
		this.collectdate = collectdate;
	}
}