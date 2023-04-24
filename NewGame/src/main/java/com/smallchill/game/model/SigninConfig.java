package com.smallchill.game.model;

import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "platform")
@Table(name = "SigninConfig")
@BindID(name = "dayid")
@SuppressWarnings("serial")
public class SigninConfig extends BaseModel {
	private Integer dayid ;
	private Long rewardgold ;
	
	public SigninConfig() {
	}
	@AssignID
	public Integer getDayid(){
		return  dayid;
	}
	public void setDayid(Integer dayid ){
		this.dayid = dayid;
	}
	
	public Long getRewardgold(){
		return  rewardgold;
	}
	public void setRewardgold(Long rewardgold ){
		this.rewardgold = rewardgold;
	}
}
