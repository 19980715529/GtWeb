package com.smallchill.game.newmodel.gameuserdb;

import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameuserdb")
@Table(name = "AA_NewPlayer_Prop")
@SuppressWarnings("serial")
public class AaNewplayerProp extends BaseModel {
	private Integer amount ;
	private Integer Prop_Id ;
	private Integer platid ;
	
	public AaNewplayerProp() {
	}
	
	public Integer getAmount(){
		return  amount;
	}
	public void setAmount(Integer amount ){
		this.amount = amount;
	}
	
	public Integer getPlatid(){
		return  platid;
	}
	public void setPlatid(Integer platid ){
		this.platid = platid;
	}

	public Integer getProp_Id() {
		return Prop_Id;
	}

	public void setProp_Id(Integer prop_Id) {
		Prop_Id = prop_Id;
	}
}