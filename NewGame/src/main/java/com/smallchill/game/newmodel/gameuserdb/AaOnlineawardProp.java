package com.smallchill.game.newmodel.gameuserdb;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameuserdb")
@Table(name = "AA_OnlineAward_Prop")
@BindID(name = "id")
@SuppressWarnings("serial")
public class AaOnlineawardProp extends BaseModel {
	private Integer id ;
	private Integer Time_Id ;
	private Integer Prop_Id ;
	private Integer amount ;
	
	public AaOnlineawardProp() {
	}
	
	@AutoID
	@SeqID(name = "SEQ_AA_OnlineAward_Prop")
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
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

	public Integer getTime_Id() {
		return Time_Id;
	}

	public void setTime_Id(Integer time_Id) {
		Time_Id = time_Id;
	}
}
