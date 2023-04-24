package com.smallchill.game.model;
// 用户权限类型
public enum UserRightType {
	
	UR_CHANGE_GOLD(0, "更改金币"),
	
	UR_CHANGE_COUPON(1, "更改奖券"),

	UR_LOCKED(2, "锁定"),
	UR_UNLOCKED(15, "解锁"),
	
	UR_LOCK_SPEAK(3, "禁言"),
	
	UR_LOCK_TOP(4, "禁止上榜"),
	
	UR_LOCK_TRADE(5, "禁止交易"),

	UR_ALLOW_TRADE(6, "允许交易"),
	
	UR_ALLOW_TOP(7, "允许上榜"),
	
	UR_CHANGE_PASSWORD(8, "更新密码"),
	
	UR_CHANGE_NICKNAME(9, "更新昵称"),
	
	UR_CHANGE_MEMBER_TYPE(10, "更新用户类型"),
	
	UR_GM_RECHARGE(11, "GM充值"),
	
	UR_GIFT_REFUND(12, "退回礼物"),
	
	UR_ACCESS_LOG(13, "访问日志"),
	
	UR_CONTROL_CHEAT(14, "修改作弊率"),
	
	ROOM_ADD_CONTROL(16, "添加房间血池控制"),
	
	ROOM_UPDATE_REMAIN_BLOOD(17, "修改剩余血池"),
	ROOM_UPGRADE_VIP(19, "升级VIP"),
	ROOM_DELETE_VIP(20, "删除VIP"),
	ROOM_REFUSE_VIP(21, "拒绝VIP"),
	ROOM_RELATION_LOCK(22, "联合锁定"),
	ROOM_RELATION_LOCK_TRADE(23, "联合禁止交易"),
	GIVE_TRANSFER(24, "赠送转移"),
	UR_CHANGE_INSURE_GOLD(25, "更改银行金币"),
	UPDATE_WAREHOUSE(26, "更改VIP为仓库号"),
	UPDATE_WAREHOUSE_CANCEL(27, "取消VIP仓库号"),
	ROOM_UPDATE_INNER_MEMBER(18, "设置内部员工");

	// others

	private final Integer code;

	private final String description;

	private UserRightType(Integer code, String description) {
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
