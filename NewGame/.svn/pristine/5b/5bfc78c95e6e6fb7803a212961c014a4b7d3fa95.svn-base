package com.smallchill.game.newmodel.gameuserdb;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameuserdb")
@Table(name = "RebateAccount")
@BindID(name = "id")
@SuppressWarnings("serial")
public class Rebateaccount extends BaseModel {
	private Integer id ;
	private Integer userid ;
	private Integer type ;
	private Date updatetime ;
	
	public Rebateaccount() {
	}
	
	@AutoID
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
	
	public Integer getType(){
		return  type;
	}
	public void setType(Integer type ){
		this.type = type;
	}
	
	public Date getUpdatetime(){
		return  updatetime;
	}
	public void setUpdatetime(Date updatetime ){
		this.updatetime = updatetime;
	}
}