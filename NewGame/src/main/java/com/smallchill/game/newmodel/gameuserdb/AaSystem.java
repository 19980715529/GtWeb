package com.smallchill.game.newmodel.gameuserdb;

import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameuserdb")
@Table(name = "AA_System")
@BindID(name = "System_Id")
@SuppressWarnings("serial")
public class AaSystem extends BaseModel {
	private Integer System_Id ;
	private String systeminfo ;
	private String systemname ;
	
	public AaSystem() {
	}
	
	public String getSysteminfo(){
		return  systeminfo;
	}
	public void setSysteminfo(String systeminfo ){
		this.systeminfo = systeminfo;
	}
	
	public String getSystemname(){
		return  systemname;
	}
	public void setSystemname(String systemname ){
		this.systemname = systemname;
	}

	public Integer getSystem_Id() {
		return System_Id;
	}

	public void setSystem_Id(Integer system_Id) {
		System_Id = system_Id;
	}
}