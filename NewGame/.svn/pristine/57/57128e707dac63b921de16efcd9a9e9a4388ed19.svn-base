package com.smallchill.game.model;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "platform")
@Table(name = "BloodPondFinishedRecord")
@BindID(name = "id")
@SuppressWarnings("serial")
public class BloodPondFinishedRecord extends BaseModel {
	private Integer bloodpondid ;
	private Integer controlnullity ;
	private Integer id ;
	private Integer serverid ;
	private String bloodpondname ;
	private Long bloodpondnowcontrolcount ;
	private Long bloodpondnowcount ;
	private String servername ;
	private String remark ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date begintime ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date endtime ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date inserttime ;
	
	public BloodPondFinishedRecord() {
	}
	
	public Integer getBloodpondid(){
		return  bloodpondid;
	}
	public void setBloodpondid(Integer bloodpondid ){
		this.bloodpondid = bloodpondid;
	}
	
	public Integer getControlnullity(){
		return  controlnullity;
	}
	public void setControlnullity(Integer controlnullity ){
		this.controlnullity = controlnullity;
	}
	@AutoID
	@SeqID(name = "SEQ_BloodPondFinishedRecord")
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public Integer getServerid(){
		return  serverid;
	}
	public void setServerid(Integer serverid ){
		this.serverid = serverid;
	}
	
	public String getBloodpondname(){
		return  bloodpondname;
	}
	public void setBloodpondname(String bloodpondname ){
		this.bloodpondname = bloodpondname;
	}
	
	public Long getBloodpondnowcontrolcount(){
		return  bloodpondnowcontrolcount;
	}
	public void setBloodpondnowcontrolcount(Long bloodpondnowcontrolcount ){
		this.bloodpondnowcontrolcount = bloodpondnowcontrolcount;
	}
	
	public Long getBloodpondnowcount(){
		return  bloodpondnowcount;
	}
	public void setBloodpondnowcount(Long bloodpondnowcount ){
		this.bloodpondnowcount = bloodpondnowcount;
	}
	
	public String getServername(){
		return  servername;
	}
	public void setServername(String servername ){
		this.servername = servername;
	}
	
	public String getRemark(){
		return  remark;
	}
	public void setRemark(String remark ){
		this.remark = remark;
	}
	
	public Date getBegintime(){
		return  begintime;
	}
	public void setBegintime(Date begintime ){
		this.begintime = begintime;
	}
	
	public Date getEndtime(){
		return  endtime;
	}
	public void setEndtime(Date endtime ){
		this.endtime = endtime;
	}
	
	public Date getInserttime(){
		return  inserttime;
	}
	public void setInserttime(Date inserttime ){
		this.inserttime = inserttime;
	}
}
