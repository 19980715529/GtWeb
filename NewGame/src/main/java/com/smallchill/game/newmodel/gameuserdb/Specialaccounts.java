package com.smallchill.game.newmodel.gameuserdb;

import java.util.Date;

import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameuserdb")
@Table(name = "SpecialAccounts")
@SuppressWarnings("serial")
public class Specialaccounts extends BaseModel {
	private Integer id ;
	private Integer userid ;
	private Date updatetime ;
	
	public Specialaccounts() {
	}
	
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public Integer getUserid(){
		return  userid;
	}
	public void setUserid(Integer userid ){
		this.userid = userid;
	}
	
	public Date getUpdatetime(){
		return  updatetime;
	}
	public void setUpdatetime(Date updatetime ){
		this.updatetime = updatetime;
	}
}