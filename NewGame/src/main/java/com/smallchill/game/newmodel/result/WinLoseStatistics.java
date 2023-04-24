package com.smallchill.game.newmodel.result;

import com.smallchill.core.base.model.BaseModel;

@SuppressWarnings("serial")
public class WinLoseStatistics extends BaseModel {
	private Long DayWaste ;
	private Integer userId ;
	
	public WinLoseStatistics() {
	}

	public Long getDayWaste() {
		return DayWaste;
	}

	public void setDayWaste(Long dayWaste) {
		DayWaste = dayWaste;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}