package com.smallchill.game.newmodel.gameuserdb;

import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameuserdb")
@Table(name = "AA_PhoneNumber_Prop")
@BindID(name = "Prop_Id")
@SuppressWarnings("serial")
public class AaPhonenumberProp extends BaseModel {
	private Integer Prop_Id ;
	private Integer amount ;
	
	public AaPhonenumberProp() {
	}
	
	public Integer getAmount(){
		return  amount;
	}
	public void setAmount(Integer amount ){
		this.amount = amount;
	}

	public Integer getProp_Id() {
		return Prop_Id;
	}

	public void setProp_Id(Integer prop_Id) {
		Prop_Id = prop_Id;
	}
}