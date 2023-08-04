package com.smallchill.game.newmodel.treasuredb;

import lombok.Data;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "treasuredb")
@Table(name = "KillBugUser")
@BindID(name = "id")
@SuppressWarnings("serial")
public class Killbuguser extends BaseModel {
	private Integer id ;
	private Integer maxRecharge ;
	private Integer CheatValue ;
	private Integer pointControlNum ;
	private Integer isOpen ;
	private Integer cheatRate ;
	private Long cheatValuePercentMin ;
	private Long cheatValuePercentMax ;
	private Long maxExchange=0L ;
	private Long minRecharge ;

	private Long ToDayScore ; // 当日输赢

	private Long TotalControlNum;
	private Long TotalScore;
	private Long TodayMinRechange;

	public Integer getCheatValue() {
		return CheatValue;
	}

	public void setCheatValue(Integer cheatValue) {
		CheatValue = cheatValue;
	}

	public Long getTodayMinRechange() {
		return TodayMinRechange;
	}

	public void setTodayMinRechange(Long todayMinRechange) {
		TodayMinRechange = todayMinRechange;
	}

	public Long getToDayScore() {
		return ToDayScore;
	}

	public void setToDayScore(Long toDayScore) {
		ToDayScore = toDayScore;
	}

	public Long getTotalControlNum() {
		return TotalControlNum;
	}

	public void setTotalControlNum(Long totalControlNum) {
		TotalControlNum = totalControlNum;
	}

	public Long getTotalScore() {
		return TotalScore;
	}

	public void setTotalScore(Long totalScore) {
		TotalScore = totalScore;
	}

	public Killbuguser() {
	}
	
	@AutoID
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}

	public Integer getMaxRecharge() {
		return maxRecharge;
	}

	public void setMaxRecharge(Integer maxRecharge) {
		this.maxRecharge = maxRecharge;
	}


	public Integer getPointControlNum() {
		return pointControlNum;
	}

	public void setPointControlNum(Integer pointControlNum) {
		this.pointControlNum = pointControlNum;
	}

	public Integer getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}

	public Integer getCheatRate() {
		return cheatRate;
	}

	public void setCheatRate(Integer cheatRate) {
		this.cheatRate = cheatRate;
	}

	public Long getCheatValuePercentMin() {
		return cheatValuePercentMin;
	}

	public void setCheatValuePercentMin(Long cheatValuePercentMin) {
		this.cheatValuePercentMin = cheatValuePercentMin;
	}

	public Long getCheatValuePercentMax() {
		return cheatValuePercentMax;
	}

	public void setCheatValuePercentMax(Long cheatValuePercentMax) {
		this.cheatValuePercentMax = cheatValuePercentMax;
	}

	public Long getMaxExchange() {
		return maxExchange;
	}

	public void setMaxExchange(Long maxExchange) {
		this.maxExchange = maxExchange;
	}

	public Long getMinRecharge() {
		return minRecharge;
	}

	public void setMinRecharge(Long minRecharge) {
		this.minRecharge = minRecharge;
	}
}