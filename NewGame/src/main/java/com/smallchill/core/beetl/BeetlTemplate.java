package com.smallchill.core.beetl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;

import com.smallchill.core.beetl.func.AceExt;
import com.smallchill.core.beetl.func.BeetlExt;
import com.smallchill.core.beetl.func.ShiroExt;
import com.smallchill.core.beetl.tag.DropDownTag;
import com.smallchill.core.beetl.tag.FootTag;
import com.smallchill.core.beetl.tag.HotBlogsTag;
import com.smallchill.core.beetl.tag.SelectTag;
import com.smallchill.core.beetl.tag.SideBarTag;
import com.smallchill.core.constant.ConstConfig;
import com.smallchill.core.toolbox.CMap;

/**
 * Beetl模板绑值
 */
public class BeetlTemplate {
	private static GroupTemplate gt;
	
	public static GroupTemplate getGt() {
		return gt;
	}

	static {
		if (gt == null) {
			StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
			Configuration cfg = null;
			try {
				cfg = Configuration.defaultConfiguration();
			} catch (IOException e) {
				e.printStackTrace();
			}
			gt = new GroupTemplate(resourceLoader, cfg);
			registerTemplate(gt);
		}
	}

	public static void registerTemplate(GroupTemplate groupTemplate){
		Map<String, Object> sharedVars = new HashMap<String, Object>();
		sharedVars.put("startTime", new Date());
		sharedVars.put("domain", ConstConfig.DOMAIN);
		sharedVars.put("sysTitle", ConstConfig.SYS_TITLE);
		sharedVars.put("platform", ConstConfig.SYS_PLATFORM);
		sharedVars.put("isSearchOn", ConstConfig.SYS_ISONSEARCH);
		//sharedVars.put("newPath", Cst.me().getContextPath()+"/ceshi");
		groupTemplate.setSharedVars(sharedVars);

		groupTemplate.registerTag("hot", HotBlogsTag.class);
		groupTemplate.registerTag("select", SelectTag.class);
		groupTemplate.registerTag("sidebar", SideBarTag.class);
		groupTemplate.registerTag("dropdown", DropDownTag.class);
		groupTemplate.registerTag("foot", FootTag.class);

		groupTemplate.registerFunctionPackage("ace", new AceExt());
		groupTemplate.registerFunctionPackage("func", new BeetlExt());
		groupTemplate.registerFunctionPackage("shiro", new ShiroExt());
	}
	
	public static String build(String str, Map<String, Object> paras) {
		Template t = gt.getTemplate(str);
		if (null == paras) {
			paras = CMap.createHashMap();
		}
		for (String o : paras.keySet()) {
			t.binding(o, paras.get(o));
		}
		return t.render();
	}

	public static void buildTo(String str, Map<String, Object> paras, PrintWriter pw) {
		Template t = gt.getTemplate(str);
		if (null == paras) {
			paras = CMap.createHashMap();
		}
		for (String o : paras.keySet()) {
			t.binding(o, paras.get(o));
		}
		t.renderTo(pw);
	}
}
