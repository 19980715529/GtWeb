package com.smallchill.game.newmodel.gameuserdb;

import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameroomitemdb")
@Table(name = "SignEvent")
@SuppressWarnings("serial")
public class SignEvent extends BaseModel {
	private Integer signInDays;// 签到活动天数
	private Integer	reward; //奖励额度
	private Integer reward1;//奖励额度 该单位有值时为奖励最大区间值
	private Integer	rewardType; // 奖励类型1金币2转盘次数3待定
	private Integer id;

	public Integer getSignInDays() {
		return signInDays;
	}

	public void setSignInDays(Integer signInDays) {
		this.signInDays = signInDays;
	}

	public Integer getReward() {
		return reward;
	}

	public void setReward(Integer reward) {
		this.reward = reward;
	}

	public Integer getReward1() {
		return reward1;
	}

	public void setReward1(Integer reward1) {
		this.reward1 = reward1;
	}

	public Integer getRewardType() {
		return rewardType;
	}

	public void setRewardType(Integer rewardType) {
		this.rewardType = rewardType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}