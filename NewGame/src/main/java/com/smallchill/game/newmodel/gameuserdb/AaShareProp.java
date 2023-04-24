package com.smallchill.game.newmodel.gameuserdb;

import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameuserdb")
@Table(name = "AA_Share_Prop")
@SuppressWarnings("serial")
public class AaShareProp extends BaseModel {
	private Integer Day_Id ;
	private Integer Prop_Id ;
	private Integer amount ;
	
	public AaShareProp() {
	}
	
	public Integer getAmount(){
		return  amount;
	}
	public void setAmount(Integer amount ){
		this.amount = amount;
	}
	@AssignID
	public Integer getDay_Id() {
		return Day_Id;
	}

	public void setDay_Id(Integer day_Id) {
		Day_Id = day_Id;
	}
	@AssignID
	public Integer getProp_Id() {
		return Prop_Id;
	}

	public void setProp_Id(Integer prop_Id) {
		Prop_Id = prop_Id;
	}
}