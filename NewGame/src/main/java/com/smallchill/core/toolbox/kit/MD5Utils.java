package com.smallchill.core.toolbox.kit;

import redis.clients.util.Hashing;

public class MD5Utils {

	public static String getMD5Code(String content) {
		Long hash = Hashing.MD5.hash(content);
        return hash.toString();
    }
}
