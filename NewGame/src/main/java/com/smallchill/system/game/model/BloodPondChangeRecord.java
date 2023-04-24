package com.smallchill.system.game.model;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gamerecorddb")
@Table(name = "BloodPondChangeRecord")
@BindID(name = "id")
@SuppressWarnings("serial")
public class BloodPondChangeRecord extends BaseModel {
	private Integer bloodpondid ;
	private Integer id ;
	private Integer serverid ;
	private Integer type ;
	private String bloodpondname ;
	private Long bloodpondscore ;
	private Long bloodpondafterscore ;
	private Long bloodpondbeforescore ;
	private String servername ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date inserttime ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date updatetime ;
	private String remark ;
	
	public BloodPondChangeRecord() {
	}
	
	public Integer getBloodpondid(){
		return  bloodpondid;
	}
	public void setBloodpondid(Integer bloodpondid ){
		this.bloodpondid = bloodpondid;
	}
	
	@AutoID
	@SeqID(name = "SEQ_BloodPondChangeRecord")
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
	
	public Integer getType(){
		return  type;
	}
	public void setType(Integer type ){
		this.type = type;
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
	
	public Date getUpdatetime(){
		return  updatetime;
	}
	public void setUpdatetime(Date updatetime ){
		this.updatetime = updatetime;
	}

	public Long getBloodpondafterscore() {
		return bloodpondafterscore;
	}

	public void setBloodpondafterscore(Long bloodpondafterscore) {
		this.bloodpondafterscore = bloodpondafterscore;
	}

	public Long getBloodpondbeforescore() {
		return bloodpondbeforescore;
	}

	public void setBloodpondbeforescore(Long bloodpondbeforescore) {
		this.bloodpondbeforescore = bloodpondbeforescore;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
