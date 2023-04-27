package com.smallchill.game.newmodel;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gamerecorddb")
@Table(name = "GMChangeCheatRecord")
@BindID(name = "rid")
@SuppressWarnings("serial")
public class Gmchangecheatrecord extends BaseModel {
	private Integer rid ;
	private Integer usernommber ;
	private Integer gid ;
	private Integer userid ;
	private String memo ;
	private Long newlcheatlimit ;
	private Long newlcheatrate ;
	private Long oldlcheatlimit ;
	private Long oldlcheatrate ;
	private Long winscore ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date writedate ;
	
	public Gmchangecheatrecord() {
	}
	@AutoID
	@SeqID(name = "SEQ_Gmchangecheatrecord")
	public Integer getRid(){
		return  rid;
	}
	public void setRid(Integer rid ){
		this.rid = rid;
	}
	
	public Integer getUsernommber(){
		return  usernommber;
	}
	public void setUsernommber(Integer usernommber ){
		this.usernommber = usernommber;
	}
	
	public Integer getGid(){
		return  gid;
	}
	public void setGid(Integer gid ){
		this.gid = gid;
	}
	
	public Integer getUserid(){
		return  userid;
	}
	public void setUserid(Integer userid ){
		this.userid = userid;
	}
	
	public String getMemo(){
		return  memo;
	}
	public void setMemo(String memo ){
		this.memo = memo;
	}
	
	public Long getNewlcheatlimit(){
		return  newlcheatlimit;
	}
	public void setNewlcheatlimit(Long newlcheatlimit ){
		this.newlcheatlimit = newlcheatlimit;
	}
	
	public Long getNewlcheatrate(){
		return  newlcheatrate;
	}
	public void setNewlcheatrate(Long newlcheatrate ){
		this.newlcheatrate = newlcheatrate;
	}
	
	public Long getOldlcheatlimit(){
		return  oldlcheatlimit;
	}
	public void setOldlcheatlimit(Long oldlcheatlimit ){
		this.oldlcheatlimit = oldlcheatlimit;
	}
	
	public Long getOldlcheatrate(){
		return  oldlcheatrate;
	}
	public void setOldlcheatrate(Long oldlcheatrate ){
		this.oldlcheatrate = oldlcheatrate;
	}
	
	public Long getWinscore(){
		return  winscore;
	}
	public void setWinscore(Long winscore ){
		this.winscore = winscore;
	}
	
	public Date getWritedate(){
		return  writedate;
	}
	public void setWritedate(Date writedate ){
		this.writedate = writedate;
	}
}
