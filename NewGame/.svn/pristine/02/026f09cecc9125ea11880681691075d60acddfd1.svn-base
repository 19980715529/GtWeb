package com.smallchill.core.beetl.func;

import java.util.HashMap;
import java.util.Map;

import com.smallchill.core.constant.ConstCache;
import com.smallchill.core.constant.ConstCacheKey;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.cache.ILoader;


/**
 * ace工具类
 */
public class AceExt {

	public String theme() {
		if (null == ShiroKit.getUser()) {
			return "ace-dark.css";
		}
		Map<String, String> theme = CacheKit.get(ConstCache.SYS_CACHE, ConstCacheKey.ACE_THEME + ShiroKit.getUser().getId(), new ILoader() {
			public Object load() {
				Map<String, String> map = new HashMap<String, String>();
				map.put("ace", "ace-dark.css");
				return map;
			}
		});
		return theme.get("ace");
	}

}
