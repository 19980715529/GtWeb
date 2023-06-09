package com.smallchill.core.beetl.func;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.smallchill.common.tool.SysCache;
import com.smallchill.core.constant.ConstCache;
import com.smallchill.core.constant.ConstCacheKey;
import com.smallchill.core.constant.ConstConfig;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.cache.ILoader;
import com.smallchill.core.toolbox.kit.CharsetKit;
import com.smallchill.core.toolbox.kit.DateKit;
import com.smallchill.core.toolbox.kit.DateTimeKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.kit.URLKit;
import com.smallchill.core.toolbox.support.Convert;

/**
 * beetl注册工具类
 * 高频使用方法
 */
public class BeetlExt {

	/**
	 * 比较两个对象是否相等。<br>
	 * 相同的条件有两个，满足其一即可：<br>
	 * 1. obj1 == null && obj2 == null; 2. obj1.equals(obj2)
	 * 
	 * @param obj1
	 *            对象1
	 * @param obj2
	 *            对象2
	 * @return 是否相等
	 */
	public boolean equals(Object obj1, Object obj2) {
		return (obj1 != null) ? (obj1.equals(obj2)) : (obj2 == null);
	}

	/**
	 * 计算对象长度，如果是字符串调用其length函数，集合类调用其size函数，数组调用其length属性，其他可遍历对象遍历计算长度
	 * 
	 * @param obj
	 *            被计算长度的对象
	 * @return 长度
	 */
	public int length(Object obj) {
		if (obj == null) {
			return 0;
		}
		if (obj instanceof CharSequence) {
			return ((CharSequence) obj).length();
		}
		if (obj instanceof Collection) {
			return ((Collection<?>) obj).size();
		}
		if (obj instanceof Map) {
			return ((Map<?, ?>) obj).size();
		}

		int count;
		if (obj instanceof Iterator) {
			Iterator<?> iter = (Iterator<?>) obj;
			count = 0;
			while (iter.hasNext()) {
				count++;
				iter.next();
			}
			return count;
		}
		if (obj instanceof Enumeration) {
			Enumeration<?> enumeration = (Enumeration<?>) obj;
			count = 0;
			while (enumeration.hasMoreElements()) {
				count++;
				enumeration.nextElement();
			}
			return count;
		}
		if (obj.getClass().isArray() == true) {
			return Array.getLength(obj);
		}
		return -1;
	}

	/**
	 * 对象中是否包含元素
	 * 
	 * @param obj
	 *            对象
	 * @param element
	 *            元素
	 * @return 是否包含
	 */
	public boolean contains(Object obj, Object element) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof String) {
			if (element == null) {
				return false;
			}
			return ((String) obj).contains(element.toString());
		}
		if (obj instanceof Collection) {
			return ((Collection<?>) obj).contains(element);
		}
		if (obj instanceof Map) {
			return ((Map<?, ?>) obj).values().contains(element);
		}

		if (obj instanceof Iterator) {
			Iterator<?> iter = (Iterator<?>) obj;
			while (iter.hasNext()) {
				Object o = iter.next();
				if (equals(o, element)) {
					return true;
				}
			}
			return false;
		}
		if (obj instanceof Enumeration) {
			Enumeration<?> enumeration = (Enumeration<?>) obj;
			while (enumeration.hasMoreElements()) {
				Object o = enumeration.nextElement();
				if (equals(o, element)) {
					return true;
				}
			}
			return false;
		}
		if (obj.getClass().isArray() == true) {
			int len = Array.getLength(obj);
			for (int i = 0; i < len; i++) {
				Object o = Array.get(obj, i);
				if (equals(o, element)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 对象是否为空
	 * 
	 * @param obj
	 *            String,List,Map,Object[],int[],long[]
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean isEmpty(Object o) {
		if (o == null) {
			return true;
		}
		if (o instanceof String) {
			if (o.toString().trim().equals("")) {
				return true;
			}
		} else if (o instanceof List) {
			if (((List) o).size() == 0) {
				return true;
			}
		} else if (o instanceof Map) {
			if (((Map) o).size() == 0) {
				return true;
			}
		} else if (o instanceof Set) {
			if (((Set) o).size() == 0) {
				return true;
			}
		} else if (o instanceof Object[]) {
			if (((Object[]) o).length == 0) {
				return true;
			}
		} else if (o instanceof int[]) {
			if (((int[]) o).length == 0) {
				return true;
			}
		} else if (o instanceof long[]) {
			if (((long[]) o).length == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 对象组中是否存在 Empty Object
	 * 
	 * @param os
	 *            对象组
	 * @return
	 */
	public boolean isOneEmpty(Object... os) {
		for (Object o : os) {
			if (isEmpty(o)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 对象组中是否全是 Empty Object
	 * 
	 * @param os
	 * @return
	 */
	public boolean isAllEmpty(Object... os) {
		for (Object o : os) {
			if (!isEmpty(o)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否为数字
	 * 
	 * @param obj
	 * @return
	 */
	public boolean isNum(Object obj) {
		try {
			Integer.parseInt(obj.toString());
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 格式化文本
	 * 
	 * @param template
	 *            文本模板，被替换的部分用 {} 表示
	 * @param values
	 *            参数值
	 * @return 格式化后的文本
	 */
	public String format(String template, Object... values) {
		return StrKit.format(template, values);
	}

	/**
	 * 格式化文本
	 * 
	 * @param template
	 *            文本模板，被替换的部分用 {key} 表示
	 * @param map
	 *            参数值对
	 * @return 格式化后的文本
	 */
	public String format(String template, Map<?, ?> map) {
		return StrKit.format(template, map);
	}

	/**
	 * 格式化字符串 去掉前后空格
	 * 
	 * @param str
	 * @return
	 */
	public String toStr(Object str) {
		if (null == str) {
			return null;
		}
		return str.toString().trim();
	}

	/**
	 * 强转->int
	 * 
	 * @param obj
	 * @return
	 */
	public int toInt(Object value) {
		return toInt(value, -1);
	}

	/**
	 * 强转->int
	 * 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public int toInt(Object value, int defaultValue) {
		return Convert.toInt(value, defaultValue);
	}

	/**
	 * 强转->long
	 * 
	 * @param obj
	 * @return
	 */
	public long toLong(Object value) {
		return toLong(value, -1);
	}

	/**
	 * 强转->long
	 * 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public long toLong(Object value, long defaultValue) {
		return Convert.toLong(value, defaultValue);
	}

	public String encodeUrl(String url) {
		return URLKit.encode(url, CharsetKit.UTF_8);
	}

	public String decodeUrl(String url) {
		return URLKit.decode(url, CharsetKit.UTF_8);
	}

	/**
	 * 创建StringBuilder对象
	 * 
	 * @return StringBuilder对象
	 */
	public StringBuilder builder(String... strs) {
		final StringBuilder sb = new StringBuilder();
		for (String str : strs) {
			sb.append(str);
		}
		return sb;
	}

	/**
	 * 判断是否包含
	 * 
	 * @param type
	 * @param _type
	 * @return boolean
	 */
	public boolean like(String type, String _type) {
		if (type.indexOf(_type) >= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 创建StringBuilder对象
	 * 
	 * @return StringBuilder对象
	 */
	public void builder(StringBuilder sb, String... strs) {
		for (String str : strs) {
			sb.append(str);
		}
	}

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     *
     * @return
     */
    public String getTime() {
        return DateKit.getTime();
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss.SSS格式
     *
     * @return
     */
    public String getMsTime() {
        return DateKit.getMsTime();
    }

    /**
     * 获取YYYY-MM-DD格式
     *
     * @return
     */
    public String getDay() {
        return DateKit.getDay();
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public String formatNow(String pattern) {
        return DateKit.format(new Date(), pattern);
    }

    /**
     * 格式化日期
     * @param date yyyy-MM-dd HH:mm:ss
     * @return
     */
    public String formatTime(Date date) {
        if(isEmpty(date)) return "";
        return DateKit.format(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 格式化日期
     * @return
     */
    public String formatTime(Date date, String pattern) {
        return DateKit.format(date, pattern);
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public String formatTime(String dateStr, String pattern) {
        return DateKit.format(DateTimeKit.parse(dateStr).toDate(), pattern);
    }
	
	/**
	 * 获取grid右键菜单
	 * 
	 * @param roleId
	 * @param code
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	public String getRightMenu(final Object userId, Object roleId, final String code, boolean isFresh, boolean isExport) {
		Map<String, Object> userRole = CacheKit.get(ConstCache.SYS_CACHE, ConstCacheKey.ROLE_EXT + userId,
				new ILoader() {
					public Object load() {
						return Db.selectOne("select * from BLADE_ROLE_EXT where userId=#{userId}", CMap.init().set("userId", userId));
					}
				});

		String roleIn = "0";
		String roleOut = "0";
		if (!Func.isEmpty(userRole)) {
			roleIn = Func.toStr(userRole.get("ROLEIN"));
			roleOut = Func.toStr(userRole.get("ROLEOUT"));
		}
		
		final StringBuilder sql = new StringBuilder();
		sql.append("select BLADE_MENU.* ,(select name from BLADE_MENU where code=#{code}) as pname  from BLADE_MENU");
		sql.append(" where ( ");
		sql.append("	 (status=1)");
		sql.append("	 and (icon is not null and (icon like '%btn%' or icon like '%icon%' ) ) ");
		sql.append("	 and (url like '%add%' or url like '%edit%' or url like '%remove%'  or url like '%del%' or url like '%view%' ) ");
		sql.append("	 and (pCode=#{code})");
		sql.append("	 and (id in (select menuId from BLADE_RELATION where roleId in (#{join(roleId)})) or id in (#{join(roleIn)}))");
		sql.append("	 and id not in(#{join(roleOut)})");
		sql.append("	)");
		sql.append(" order by num");

		List<Map> btnList = Db.selectListByCache(ConstCache.SYS_CACHE, ConstCacheKey.RIGHT_MENU + code + "_" + userId, sql.toString(), CMap.init().set("code", code)
					.set("roleId", Convert.toIntArray(roleId.toString()))
					.set("roleIn", Convert.toIntArray(roleIn))
					.set("roleOut", Convert.toIntArray(roleOut)));

		StringBuilder rightsb = new StringBuilder();
		rightsb.append("<ul style=\"width: 200px;\">");
		for (Map btn : btnList) {
			rightsb.append("	<li id=\"rightMenu_" + Func.toStr(btn.get("CODE")).split("_")[1] + "\"> ");
			rightsb.append("		<i class=\"ace-icon " + Func.toStr(btn.get("ICON")).split("\\|")[1].replace("bigger-120", "") + "\" style=\"width:15px;\"></i> ");
			rightsb.append("		<span style=\"font-size:12px; font-family:Verdana;color:#777;padding-left:5px; \">" + Func.toStr(btn.get("NAME")) + "</span>");
			rightsb.append("	</li>");
		}
		rightsb.append("<span style=\"padding:0px;margin:1px auto;display:block;clean:both;height:1px;border-top:1px dotted #B6C0C9;\"></span>");
		if (isFresh) {
			rightsb.append("	<li id=\"rightMenu_refresh\"> ");
			rightsb.append("		<i class=\"ace-icon fa fa-refresh\" style=\"width:15px;\"></i> ");
			rightsb.append("		<span style=\"font-size:12px; font-family:Verdana;color:#777;padding-left:5px;  \">刷新</span>");
			rightsb.append("	</li>");
		}
		
		if (isExport) {
			rightsb.append("	<li id=\"rightMenu_excel\"> ");
			rightsb.append("		<i class=\"ace-icon fa fa-file-excel-o\" style=\"width:15px;\"></i> ");
			rightsb.append("		<span style=\"font-size:12px; font-family:Verdana;color:#777;padding-left:5px; \">导出 excel</span>");
			rightsb.append("	</li>");
		}
		rightsb.append("</ul>");
		return rightsb.toString();
	}
	
	
	public String getDictName(final Object code, final Object num) {
		return SysCache.getDictName(code, num);
	}

	public String getRoleName(final Object roleId) {
		return SysCache.getRoleName(roleId);
	}

	public String getUserName(final Object userId) {
		return SysCache.getUserName(userId);
	}

	public String getDeptName(final Object deptId) {
		return SysCache.getDeptName(deptId);
	}
	
	public boolean isOracle() {
		return (ConstConfig.DBTYPE.equals("oracle"));
	}

	public boolean isMySql() {
		return (ConstConfig.DBTYPE.equals("mysql"));
	}

	
}
