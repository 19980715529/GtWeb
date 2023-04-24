package com.smallchill.db.config.model;

public enum SupporTypeStatus {
	SP_TREASURE(1, "金币类型"),
	
	SP_SCORE(2, "点值类型"),

	SP_TIMING_MATCH(4, "定时比赛"),
	
	SP_EXERCISE(8, "训练类型"),
	
	SP_IMMEDIATELY_MATCH(16, "即时比赛");

	// others

	private final Integer code;

	private final String description;

	private SupporTypeStatus(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public Integer code() {
		return this.code;
	}

	public String getDescription() {
		return this.description;
	}
}
