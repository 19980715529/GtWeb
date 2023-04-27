package com.smallchill.common.beetl;

import java.net.URLEncoder;
import java.text.DecimalFormat;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.beetl.core.GroupTemplate;

import com.alibaba.fastjson.JSON;
import com.smallchill.core.toolbox.kit.CharsetKit;
import com.smallchill.core.toolbox.kit.URLKit;

/**
 * 模板注册
 */
public class BeetlRegister {

	public static void registerTemplate(GroupTemplate groupTemplate) {
		groupTemplate.registerFunction("numberHandle", new Function() {
			@Override
			public Object call(Object[] paras, Context ctx) {
				DecimalFormat df = new DecimalFormat("#,###");
				String format = df.format(paras[0]);
				return format;
			}
		});
		groupTemplate.registerFunction("whereHandle", new Function() {
			@Override
			public Object call(Object[] paras, Context ctx) {
				String t = JSON.toJSONString(paras[0]).replaceAll("\"", "").replaceAll("'", "\"");
				String format = URLKit.encode(t, CharsetKit.UTF_8);
				return format;
			}
		});
	}
	
}
