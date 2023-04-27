package com.smallchill.system.nativeweb.model;

public enum NewsType {
	
	PC_NEWS((short)1, "电脑端新闻"),
	
	PC_NOTICE((short)2, "电脑端公告"),

	MOBILE_NOTICE((short)3, "移动端公告"),
	
	ROOM_NOTICE((short)4, "房卡公告"),
	
	POP_NOTICE((short)5, "弹出公告"),
	
	LOGIN_NOTICE((short)6, "登录公告");

	// others

	private final Short code;

	private final String description;

	private NewsType(Short code, String description) {
		this.code = code;
		this.description = description;
	}

	public Short code() {
		return this.code;
	}

	public String getDescription() {
		return this.description;
	}
}
