/******************************************************************
 *
 *    Powered By .
 *
 *    Copyright (c) 
 *    http://
 *
 *    Package:     com.weshop.base.utils
 *
 *    Filename:    SerialNumberGenerator.java
 *
 *    Description: TODO
 *
 *    Copyright:   Copyright (c) 2001-2014
 *
 *    Company:     Digital Telemedia Co.,Ltd
 *
 *    @author:     fih01
 *
 *    @version:    1.0.0
 *
 *    Create at:   2016年8月23日 下午8:29:33
 *
 *    Revision:
 *
 *    2016年8月23日 下午8:29:33
 *        - first revision
 *
 *****************************************************************/
package com.smallchill.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName SerialNumberGenerator
 * @Description TODO
 * @author lihongwang
 * @Date 2016年8月23日 下午8:29:33
 * @version 1.0.0
 */
public class SerialNumberGeneratorCommonUtil {

	// 格式化日期时间
	public static String truncateDate(SimpleDateFormat format) {
		String dateSerial = "";
		Date now = new Date();

		// 时间格式转为字符串
		dateSerial = format.format(now);
		return dateSerial;
	}
	
	// 截取时间戳
	public static String truncateStampTime(int stampTimeLength) {
		String stampTimeSerial = "";

		// get current system time.
		String initStr = new Long(System.currentTimeMillis()).toString();
		int stampLen = initStr.length();

		// if the length of the initStr is smaller than stampTimeLength, fill
		// the initStr with zero.
		if (stampLen < stampTimeLength) {
			stampTimeSerial = fillWithZero(initStr, stampTimeLength);
		}
		// if the length of the initStr is larger than stampTimeLength or equal
		// stampTimeLength, get the result with length stampTimeLength from the
		// end of the initStr.
		else {
			stampTimeSerial = initStr.substring(stampLen - stampTimeLength);
		}
		return stampTimeSerial;
	}

	// 生成随机数
	public static String generateRandom(int randomLength) {
		String randomSerial = "";

		// 获取randomLength位随机数
		Double r = Math.random() * Math.pow(10, randomLength);
		Integer i = r.intValue();

		// 随机数转换为字符串
		randomSerial = fillWithZero(i.toString(), randomLength);
		return randomSerial;
	}

	// 字符串不够位, 则在高位补0
	public static String fillWithZero(String serialNum, int len) {
		int zeroNum = len - serialNum.length();
		for (int i = 0; i < zeroNum; i++) {
			serialNum = "0" + serialNum;
		}
		return serialNum;
	}
}
