package com.smallchill.pay.bpay.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.TreeSet;

public class SortUtils {

	public static String getMapString(Map<String, ? extends Object> map, String key, String link) {
		if (map == null || map.size() == 0) {
			return null;
		}
		if (StringUtils.isEmpty(link)) {
			link = "&";
		}
		StringBuilder sb = new StringBuilder();
		TreeSet<String> keys = new TreeSet<String>(map.keySet());
		for (String pkey : keys) {
			Object pvalue = map.get(pkey);
			if (pvalue != null && pvalue.toString().length() > 0) {
				sb.append(pkey).append("=").append(pvalue).append(link);
			}
		}

		if (StringUtils.isNotEmpty(key)) {
			sb.append("key").append("=").append(key);
			return sb.toString();
		}
		return sb.toString().substring(0, sb.toString().length() - 1);
	}
}
