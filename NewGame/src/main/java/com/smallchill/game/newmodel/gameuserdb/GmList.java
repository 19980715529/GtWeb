package com.smallchill.game.newmodel.gameuserdb;

import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameuserdb")
@Table(name = "GM_List")
@SuppressWarnings("serial")
public class GmList extends BaseModel {
	private Integer User_Id ;
	private Integer level ;
	private String desc ;
	
	public GmList() {
	}
	
	public Integer getLevel(){
		return  level;
	}
	public void setLevel(Integer level ){
		this.level = level;
	}
	
	public String getDesc(){
		return  desc;
	}
	public void setDesc(String desc ){
		this.desc = desc;
	}

	public Integer getUser_Id() {
		return User_Id;
	}

	public void setUser_Id(Integer user_Id) {
		User_Id = user_Id;
	}
}