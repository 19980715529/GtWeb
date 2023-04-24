package com.smallchill.game.newmodel.gameuserdb;

import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameuserdb")
@Table(name = "K_LoginWhiteList")
@BindID(name = "userid")
@SuppressWarnings("serial")
public class KLoginwhitelist extends BaseModel {
	private Integer userid ;
	private Integer nextipindex ;
	private String note ;
	
	public KLoginwhitelist() {
	}
	@AssignID
	public Integer getUserid(){
		return  userid;
	}
	public void setUserid(Integer userid ){
		this.userid = userid;
	}
	
	public Integer getNextipindex(){
		return  nextipindex;
	}
	public void setNextipindex(Integer nextipindex ){
		this.nextipindex = nextipindex;
	}
	
	public String getNote(){
		return  note;
	}
	public void setNote(String note ){
		this.note = note;
	}
}