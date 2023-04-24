package com.smallchill.core.utils;

//用户权限
public class UserRightUtil {
	// 玩家权限
	private static Long UR_CANNOT_PLAY = 0x00000001L;
	private static Long UR_CANNOT_LOOKON = 0x00000002L;
	private static Long UR_CANNOT_WISPER = 0x00000004L;
	private static Long UR_CANNOT_ROOM_CHAT = 0x00000008L;
	private static Long UR_CANNOT_GAME_CHAT = 0x00000010L;
	private static Long UR_CAN_SEND_TRUMPET = 0x00000020L;
	private static Long UR_CANNOT_GAME_TOP = 0x00000040L;
	private static Long UR_CANNOT_GAME_TRADE = 0x00000080L;
	private static Long UR_GAME_ENTER_VIP_ROOM = 0x00000400L;
	private static Long UR_CAN_SEND_GIFT = 0x00000800L;
	private static Long UR_CAN_ONLINE_NOTICE = 0x00001000L;
	private static Long UR_GAME_DOUBLE_SCORE = 0x00000100L;
	private static Long UR_GAME_KICK_OUT_USER = 0x00000200L;
	private static Long UR_GAME_MATCH_USER = 0x10000000L;
	private static Long UR_GAME_CHEAT_USER;

	// 游戏权限
	public static boolean CanPlay(Integer dwUserRight) {
		return (dwUserRight & UR_CANNOT_PLAY) != 0;
	}
	// 添加游戏权限
	public static Integer unlockPlay(Integer dwUserRight) {
		return (int) (dwUserRight | UR_CANNOT_PLAY);
	}
	// 剔除游戏权限
	public static Integer lockPlay(Integer dwUserRight) {
		return (int) (~dwUserRight & UR_CANNOT_PLAY);
	}

	// 旁观权限
	public static boolean CanLookon(Integer dwUserRight) {
		return (dwUserRight & UR_CANNOT_LOOKON) != 0;
	}
	// 添加旁观权限
	public static Integer unlockLookon(Integer dwUserRight) {
		return (int) (dwUserRight | UR_CANNOT_LOOKON);
	}
	// 剔除旁观权限
	public static Integer lockLookon(Integer dwUserRight) {
		return (int) (~dwUserRight & UR_CANNOT_LOOKON);
	}

	// 私聊权限
	public static boolean CanWisper(Integer dwUserRight) {
		return (dwUserRight & UR_CANNOT_WISPER) != 0;
	}
	// 添加私聊权限
	public static Integer unlockWisper(Integer dwUserRight) {
		return (int) (dwUserRight | UR_CANNOT_WISPER);
	}
	// 剔除私聊权限
	public static Integer lockWisper(Integer dwUserRight) {
		return (int) (~dwUserRight & UR_CANNOT_WISPER);
	}

	// 大厅聊天
	public static boolean CanRoomChat(Integer dwUserRight) {
		return (dwUserRight & UR_CANNOT_ROOM_CHAT) != 0;
	}
	// 添加大厅聊天
	public static Integer unlockRoomChat(Integer dwUserRight) {
		return (int) (dwUserRight | UR_CANNOT_ROOM_CHAT);
	}
	// 剔除大厅聊天
	public static Integer lockRoomChat(Integer dwUserRight) {
		return (int) (~dwUserRight & UR_CANNOT_ROOM_CHAT);
	}

	// 游戏聊天
	public static boolean CanGameChat(Integer dwUserRight) {
		return (dwUserRight & UR_CANNOT_GAME_CHAT) != 0;
	}
	// 添加游戏聊天
	public static Integer unlockGameChat(Integer dwUserRight) {
		return (int) (dwUserRight | UR_CANNOT_GAME_CHAT);
	}
	// 剔除游戏聊天
	public static Integer lockGameChat(Integer dwUserRight) {
		return (int) (~dwUserRight & UR_CANNOT_GAME_CHAT);
	}
	
	// 上榜
	public static boolean CanGameTop(Integer dwUserRight) {
		return (dwUserRight & UR_CANNOT_GAME_TOP) != 0;
	}
	// 允许上榜
	public static Integer unlockGameTop(Integer dwUserRight) {
		return (int) (dwUserRight | UR_CANNOT_GAME_TOP);
	}
	// 剔除上榜
	public static Integer cantGameTop(Integer dwUserRight) {
		return (int) (~dwUserRight & UR_CANNOT_GAME_TOP);
	}

	// 交易
	public static boolean CanGameTrade(Integer dwUserRight) {
		return (dwUserRight & UR_CANNOT_GAME_TRADE) != 0;
	}

	// 进入VIP房
	public static boolean CanEnterVipRoom(Integer dwUserRight) {
		return (dwUserRight & UR_GAME_ENTER_VIP_ROOM) != 0;
	}

	// 会员权限
	// 双倍积分
	public static boolean CanDoubleScore(Integer dwUserRight) {
		return (dwUserRight & UR_GAME_DOUBLE_SCORE) != 0;
	}

	// 踢出用户
	public static boolean CanKillOutUser(Integer dwUserRight) {
		return (dwUserRight & UR_GAME_KICK_OUT_USER) != 0;
	}

	// 特殊权限
	// 比赛用户
	public static boolean IsGameMatchUser(Integer dwUserRight) {
		return (dwUserRight & UR_GAME_MATCH_USER) != 0;
	}

	// 作弊用户
	public static boolean IsGameCheatUser(Integer dwUserRight) {
		return (dwUserRight & UR_GAME_CHEAT_USER) != 0;
	}

	// 禁止交易
	public static Integer lockGameTrade(Integer dwUserRight) {
		return (int) (~dwUserRight & UR_CANNOT_GAME_TRADE);
	}
	
	// 允许交易
	public static Integer allowGameTrade(Integer dwUserRight) {
		return (int) (dwUserRight | UR_CANNOT_GAME_TRADE);
	}
	
	// 发送小喇叭
	public static boolean CanTrumpet(Integer dwUserRight) {
		return (dwUserRight & UR_CAN_SEND_TRUMPET) != 0;
	}
	// 添加发送小喇叭
	public static Integer unlockTrumpet(Integer dwUserRight) {
		return (int) (dwUserRight | UR_CAN_SEND_TRUMPET);
	}
	// 剔除发送小喇叭
	public static Integer lockTrumpet(Integer dwUserRight) {
		return (int) (~dwUserRight & UR_CAN_SEND_TRUMPET);
	}

	// 赠送礼物
	public static boolean CanSendGift(Integer dwUserRight) {
		return (dwUserRight & UR_CAN_SEND_GIFT) != 0;
	}
	// 添加赠送礼物
	public static Integer unlockSendGift(Integer dwUserRight) {
		return (int) (dwUserRight | UR_CAN_SEND_GIFT);
	}
	// 剔除赠送礼物
	public static Integer lockSendGift(Integer dwUserRight) {
		return (int) (~dwUserRight & UR_CAN_SEND_GIFT);
	}

	// 上线公告
	public static boolean CanOnlineNotice(Integer dwUserRight) {
		return (dwUserRight & UR_CAN_ONLINE_NOTICE) != 0;
	}
	// 添加上线公告
	public static Integer unlockOnlineNotice(Integer dwUserRight) {
		return (int) (dwUserRight | UR_CAN_ONLINE_NOTICE);
	}
	// 剔除上线公告
	public static Integer lockOnlineNotice(Integer dwUserRight) {
		return (int) (~dwUserRight & UR_CAN_ONLINE_NOTICE);
	}
}
