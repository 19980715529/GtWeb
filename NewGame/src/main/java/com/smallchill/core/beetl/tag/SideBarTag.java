package com.smallchill.core.beetl.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.beetl.core.Tag;

import com.smallchill.common.vo.TreeNode;
import com.smallchill.core.constant.ConstCache;
import com.smallchill.core.constant.ConstCacheKey;
import com.smallchill.core.constant.Cst;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.support.Convert;

public class SideBarTag extends Tag {
	
	private List<TreeNode> nodeList = new ArrayList<TreeNode>();
	
	@Override
	@SuppressWarnings("unchecked")
	public void render() {
		try {
			Map<String, String> param = (Map<String, String>) args[1];

			final Integer userId = Convert.toInt(param.get("userId"));
			final String roleId = param.get("roleId");
			String ctxPath = Cst.me().getContextPath();

			Map<String, Object> userRole = Db.selectOneByCache(ConstCache.SYS_CACHE, ConstCacheKey.ROLE_EXT + userId, "select * from BLADE_ROLE_EXT where USERID=#{userId}", CMap.init().set("userId", userId));

			String roleIn = "0";
			String roleOut = "0";
			if (!Func.isEmpty(userRole)) {
				CMap cmap = CMap.parse(userRole);
				roleIn = cmap.getStr("ROLEIN");
				roleOut = cmap.getStr("ROLEOUT");
			}
			final StringBuilder sql = new StringBuilder();
			
			sql.append("select * from BLADE_MENU  ");
			sql.append(" where ( ");
			sql.append("	 (status=1)");
			/*if(!ShiroKit.getUser().getIsSysRole()) {
				sql.append("	 and rolelevel <> '1'");
			}*/
			sql.append("	 and (icon is not null and icon not LIKE '%btn%' and icon not LIKE '%icon%') ");
			sql.append("	 and (id in (select menuId from BLADE_RELATION where roleId in (#{join(roleId)})) or id in (#{join(roleIn)}))");
			sql.append("	 and id not in(#{join(roleOut)})");
			sql.append("	)");
			sql.append(" order by levels,pCode,num");
			
			@SuppressWarnings("rawtypes")
			List<Map> sideBar = Db.selectListByCache(ConstCache.SYS_CACHE, ConstCacheKey.SIDEBAR + userId, sql.toString(),
					CMap.init()
					.set("roleId", Convert.toIntArray(roleId))
					.set("roleIn", Convert.toIntArray(roleIn))
					.set("roleOut", Convert.toIntArray(roleOut)));
			
			for (Map<String, Object> side : sideBar) {
				TreeNode node = new TreeNode();
				CMap cmap = CMap.parse(side);
				node.setId(cmap.getStr("CODE"));
				node.setParentId(cmap.getStr("PCODE"));
				node.setName(cmap.getStr("NAME"));
				node.setIcon(cmap.getStr("ICON"));
				node.setIsParent(false);
				nodeList.add(node);
			}

			new TreeNode().buildNodes(nodeList);

			StringBuilder sb = new StringBuilder();

			for (Map<String, Object> side : sideBar) {
				if (Func.toInt(side.get("LEVELS")) == 1) {
					String firstMenu = "";
					String subMenu = "";
					String href = StrKit.isBlank(Convert.toStr(side.get("URL"), "").trim()) ? "#" : ctxPath + side.get("URL") + "";
					String addtabs = StrKit.isBlank(Convert.toStr(side.get("URL"), "").trim()) ? "" : "data-addtabs=\"" + side.get("CODE") + "\"";

					firstMenu += "<li >";
					firstMenu += "	<a data-url=\"" + href + "\" " + addtabs + " data-title=\"" + side.get("NAME") + "\" data-icon=\"fa " + side.get("ICON") + "\" class=\"" + getDropDownClass(Func.toStr(side.get("CODE")),"dropdown-toggle") + " blade-pointer\">";
					firstMenu += "		<i class=\"menu-icon fa " + side.get("ICON") + "\"></i>";
					firstMenu += "		<span class=\"menu-text\">" + side.get("NAME") + "</span>";
					firstMenu += "		<b class=\"arrow " + getDropDownClass(Func.toStr(side.get("CODE")),"fa fa-angle-down") + "\"></b>";
					firstMenu += "	</a>";
					firstMenu += "	<b class=\"arrow\"></b>";

					subMenu = this.reloadMenu(sideBar, Func.toStr(side.get("CODE")), firstMenu, 1, ctxPath);// 寻找子菜单

					sb.append(subMenu);
				}
			}
			
			//版权校验
			/*
			sb.append("<script type=\"text/javascript\">");
			sb.append(" $(function(){");
			sb.append("  setTimeout(function(){");
			sb.append("  var $supporter = $(\"#support_tonbusoft\");");
			sb.append("  $supporter.addClass('bigger-110');");
			sb.append("  var name = $supporter.html();");
			sb.append("  var index = layer;");
			sb.append("  if(index == undefined){");
			sb.append("    alert(\"该产品版权归 " + FootTag.company + " 所有，请勿盗版！\");");
			sb.append("    return;");
			sb.append("  }");
			sb.append("  if(name == undefined){");
			sb.append("    layer.alert(\"该产品版权归 " + FootTag.company + " 所有，请勿盗版！\", {icon: 2,title:\"侵权警告\"});");
			sb.append("    return;");
			sb.append("  } else if(!(name.indexOf(\"" + FootTag.company + "\") >= 0 && $supporter.is(\"span\") && !$supporter.is(\":hidden\"))){");
			sb.append("    layer.alert(\"该产品版权归 " + FootTag.company + " 所有，请勿盗版！\", {icon: 2,title:\"侵权警告\"});");
			sb.append("    return;");
			sb.append("  }");
			sb.append("  }, 1800);");
			sb.append(" });");
			sb.append("</script>");
			*/
			
			ctx.byteWriter.writeString(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加载子菜单
	 * 
	 * @param sideBar
	 *            菜单集合
	 * @param pCode
	 *            父编号
	 * @param pStr
	 *            父HTML
	 * @param levels
	 *            层级
	 * @param ctxPath
	 *            ctxPath
	 * @return String 返回子菜单HTML集
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String reloadMenu(List<Map> sideBar, String pCode, String pStr, int levels, String ctxPath) {
		String Str = "";
		String subStr = "";
		for (Map subside : sideBar) {
			CMap cmap = CMap.parse(subside);
			int _levels = cmap.getInt("LEVELS");
			String _code = cmap.getStr("CODE");
			String _pCode = cmap.getStr("PCODE");
			String _url = cmap.getStr("URL");
			String _icon = cmap.getStr("ICON");
			String _name = cmap.getStr("NAME");
			if ((_pCode.equals(pCode) && _levels > levels)) {
				String href = StrKit.isBlank(_url.trim()) ? "#" : ctxPath + _url + "";
				String addtabs = StrKit.isBlank(_url.trim()) ? "" : "data-addtabs=\"" + _code + "\"";

				Str += "<li>";
				Str += "	<a href=\"javascript:void(0)\" name=\"" + href + "\" data-url=\"" + href + "\" " + addtabs + " data-title=\"" + _name + "\" title=\"" + _name + "\" data-icon=\"fa " + _icon + "\" class=\"" + getDropDownClass(_code, "dropdown-toggle") + " blade-pointer iframeurl\">";
				Str += "		<i class=\"menu-icon fa " + _icon + "\"></i>";
				Str += _name;
				Str += "		<b class=\"arrow " + getDropDownClass(_code,"fa fa-angle-down") + "\"></b>";
				Str += "	</a>";
				Str += "	<b class=\"arrow\"></b>";

				subStr = this.reloadMenu(sideBar, _code, Str, _levels, ctxPath);// 递归寻找子菜单

				Str = Func.isEmpty(subStr) ? Str : subStr;
			}

		}
		if (Str.length() > 0) {
			pStr += (Func.isEmpty(pStr)) ? Str : "<ul class=\"submenu\">" + Str + "</ul>";
			pStr += "</li>";
			return pStr;
		} else {
			return "";
		}

	}
	
	public String getDropDownClass(String code,String dropdownclass){
		Iterator<TreeNode> it = nodeList.iterator();
		while (it.hasNext()) {
			TreeNode n = (TreeNode) it.next();
			if(n.getId().equals(code)&&n.isParent()){
				return dropdownclass;
			}
		}
		return "";
	}

}
