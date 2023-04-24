package com.smallchill.game.newmodel.treasuredb;

import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "treasuredb")
@Table(name = "L_BloodServerConnect")
@BindID(name = "id")
@SuppressWarnings("serial")
public class LBloodserverconnect extends BaseModel {
	private Integer id ;
	private Integer curserverid ;
	private Integer mainserverid ;
	
	public LBloodserverconnect() {
	}
	
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public Integer getCurserverid(){
		return  curserverid;
	}
	public void setCurserverid(Integer curserverid ){
		this.curserverid = curserverid;
	}
	
	public Integer getMainserverid(){
		return  mainserverid;
	}
	public void setMainserverid(Integer mainserverid ){
		this.mainserverid = mainserverid;
	}
}