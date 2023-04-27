package com.smallchill.game.model;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "treasure")
@Table(name = "StreamPresentInfo")
@BindID(name = "dateid")
@SuppressWarnings("serial")
public class StreamPresentInfo extends BaseModel {
	private Integer dateid ;
	private Integer typeid ;
	private Integer userid ;
	private Integer presentcount ;
	private Long presentscore ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date firstdate ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date lastdate ;
	
	public StreamPresentInfo() {
	}
	@AssignID
	public Integer getDateid(){
		return  dateid;
	}
	public void setDateid(Integer dateid ){
		this.dateid = dateid;
	}
	
	public Integer getTypeid(){
		return  typeid;
	}
	public void setTypeid(Integer typeid ){
		this.typeid = typeid;
	}
	
	public Integer getUserid(){
		return  userid;
	}
	public void setUserid(Integer userid ){
		this.userid = userid;
	}
	
	public Integer getPresentcount(){
		return  presentcount;
	}
	public void setPresentcount(Integer presentcount ){
		this.presentcount = presentcount;
	}
	
	public Date getFirstdate(){
		return  firstdate;
	}
	public void setFirstdate(Date firstdate ){
		this.firstdate = firstdate;
	}
	
	public Date getLastdate(){
		return  lastdate;
	}
	public void setLastdate(Date lastdate ){
		this.lastdate = lastdate;
	}
	public Long getPresentscore() {
		return presentscore;
	}
	public void setPresentscore(Long presentscore) {
		this.presentscore = presentscore;
	}
}