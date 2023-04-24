package com.smallchill.system.game.model;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "platform")
@Table(name = "BloodPondRecord")
@BindID(name = "id")
@SuppressWarnings("serial")
public class BloodPondRecord extends BaseModel {
	private Integer id ;
	private Integer bloodpondid ;
	private Integer serverid ;
	private Integer state ;
	private String bloodpondname ;
	private Long bloodpondscore ;
	private String servername ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date inserttime ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date starttime ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date updatetime ;
	
	public BloodPondRecord() {
	}
	
	@AutoID
	@SeqID(name = "SEQ_BloodPondRecord")
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public Integer getBloodpondid(){
		return  bloodpondid;
	}
	public void setBloodpondid(Integer bloodpondid ){
		this.bloodpondid = bloodpondid;
	}
	
	public Integer getServerid(){
		return  serverid;
	}
	public void setServerid(Integer serverid ){
		this.serverid = serverid;
	}
	
	public Integer getState(){
		return  state;
	}
	public void setState(Integer state ){
		this.state = state;
	}
	
	public String getBloodpondname(){
		return  bloodpondname;
	}
	public void setBloodpondname(String bloodpondname ){
		this.bloodpondname = bloodpondname;
	}
	
	public Long getBloodpondscore(){
		return  bloodpondscore;
	}
	public void setBloodpondscore(Long bloodpondscore ){
		this.bloodpondscore = bloodpondscore;
	}
	
	public String getServername(){
		return  servername;
	}
	public void setServername(String servername ){
		this.servername = servername;
	}
	
	public Date getInserttime(){
		return  inserttime;
	}
	public void setInserttime(Date inserttime ){
		this.inserttime = inserttime;
	}
	
	public Date getStarttime(){
		return  starttime;
	}
	public void setStarttime(Date starttime ){
		this.starttime = starttime;
	}
	
	public Date getUpdatetime(){
		return  updatetime;
	}
	public void setUpdatetime(Date updatetime ){
		this.updatetime = updatetime;
	}
}
