package com.smallchill.core.utils;

import com.smallchill.core.toolbox.kit.StrKit;

public class SerialNumber {
	
	// 两位前缀+ 13位时间戳 + 6位随机数=21位
	private static int stampTimeLength = 13;
	private static int randomLength = 6;
	
	public static String genSerialNumber(String prefix) {
		StringBuilder builder = new StringBuilder();
		if(StrKit.notBlank(prefix)) {
			builder.append(prefix);
		}
		builder.append(SerialNumberGeneratorCommonUtil.truncateStampTime(stampTimeLength));
		builder.append(SerialNumberGeneratorCommonUtil.generateRandom(randomLength));
		return builder.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(genSerialNumber("YB"));
	}
}
