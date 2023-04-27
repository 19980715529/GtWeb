package com.smallchill.game.newmodel.gameuserdb;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.InsertIgnore;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameuserdb")
@Table(name = "AA_ActivityCore")
@BindID(name = "ActivityCore_Id")
@SuppressWarnings("serial")
public class AaActivitycore extends BaseModel {
	private Integer ActivityCore_Id ;
	private Integer isonline ;
	private Integer sortno ;
	private String imgurl ;
	private String remark ;
	private String title ;
	private Date starttime ;
	private Date stoptime ;

	@InsertIgnore
	private Integer clientid ;
	
	public AaActivitycore() {
	}

	public Integer getIsonline(){
		return  isonline;
	}
	
	@AutoID
	@SeqID(name = "SEQ_AA_ActivityCore")
	public Integer getActivityCore_Id() {
		return ActivityCore_Id;
	}

	public void setActivityCore_Id(Integer activityCore_Id) {
		ActivityCore_Id = activityCore_Id;
	}

	public void setIsonline(Integer isonline ){
		this.isonline = isonline;
	}
	
	public Integer getSortno(){
		return  sortno;
	}
	public void setSortno(Integer sortno ){
		this.sortno = sortno;
	}
	
	public String getImgurl(){
		return  imgurl;
	}
	public void setImgurl(String imgurl ){
		this.imgurl = imgurl;
	}
	
	public String getRemark(){
		return  remark;
	}
	public void setRemark(String remark ){
		this.remark = remark;
	}
	
	public String getTitle(){
		return  title;
	}
	public void setTitle(String title ){
		this.title = title;
	}
	
	public Date getStarttime(){
		return  starttime;
	}
	public void setStarttime(Date starttime ){
		this.starttime = starttime;
	}
	
	public Date getStoptime(){
		return  stoptime;
	}
	public void setStoptime(Date stoptime ){
		this.stoptime = stoptime;
	}

	public Integer getClientid() {
		return clientid;
	}

	public void setClientid(Integer clientid) {
		this.clientid = clientid;
	}
}