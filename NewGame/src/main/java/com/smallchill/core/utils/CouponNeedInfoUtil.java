package com.smallchill.core.utils;

/**
 * 奖券商品配置
 * 购买是需要填写的信息
 * (用2进制的位来配置，0为不需要，1为需要。)
 * 位从右至左配置的项分别为真实姓名，手机号，QQ号，收货地址及邮编
 * @author Administrator
 *
 */
//用户权限
public class CouponNeedInfoUtil {
	
	private static Long CG_NEED_REAL_NAME = 0x00000001L;// 真实姓名
	private static Long CG_NEED_MOBILE = 0x00000002L;// 手机号
	private static Long CG_NEED_QQ = 0x00000004L;// QQ
	private static Long CG_NEED_ADDR = 0x00000008L;// 收货地址及邮编

	// 是否需要真实姓名
	public static boolean isNeedRealName(Integer dwUserRight) {
		return (dwUserRight & CG_NEED_REAL_NAME) == 0;
	}

	// 是否需要手机号
	public static boolean isNeedMobile(Integer dwUserRight) {
		return (dwUserRight & CG_NEED_MOBILE) == 0;
	}

	// 是否需要QQ
	public static boolean isNeedQQ(Integer dwUserRight) {
		return (dwUserRight & CG_NEED_QQ) == 0;
	}

	// 是否需要收货地址及邮编
	public static boolean isNeedAddr(Integer dwUserRight) {
		return (dwUserRight & CG_NEED_ADDR) == 0;
	}
	
	// 需要真实姓名
	public static Integer needRealName(Integer dwUserRight) {
		return (int) (dwUserRight | CG_NEED_REAL_NAME);
	}

	// 不需要真实姓名
	public static Integer unneedRealName(Integer dwUserRight) {
		return (int) (~dwUserRight & CG_NEED_REAL_NAME);
	}

	// 需要手机号
	public static Integer needMobile(Integer dwUserRight) {
		return (int) (dwUserRight | CG_NEED_MOBILE);
	}
	
	// 不需要手机号
	public static Integer unneedMobile(Integer dwUserRight) {
		return (int) (~dwUserRight & CG_NEED_MOBILE);
	}

	// 需要QQ
	public static Integer needQQ(Integer dwUserRight) {
		return (int) (dwUserRight | CG_NEED_QQ);
	}
	
	// 不需要QQ
	public static Integer unneedQQ(Integer dwUserRight) {
		return (int) (~dwUserRight & CG_NEED_QQ);
	}
	
	// 需要收货地址及邮编
	public static Integer needAddr(Integer dwUserRight) {
		return (int) (dwUserRight | CG_NEED_ADDR);
	}
	
	// 不需要收货地址及邮编
	public static Integer unneedAddr(Integer dwUserRight) {
		return (int) (~dwUserRight & CG_NEED_ADDR);
	}
}
