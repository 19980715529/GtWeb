package com.smallchill.core.beetl.tag;

import java.io.IOException;
import java.util.Map;

import org.beetl.core.Tag;

import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.kit.DateKit;

public class FootTag extends Tag {

	public static String company = "gameweb@163.com";
	public static String customer = "gameweb";
	
	@Override
	@SuppressWarnings("unchecked")
	public void render() {
		try {
			if(args.length > 1){
				Map<String, String> param = (Map<String, String>) args[1];
			    company = (Func.isEmpty(param.get("tonbusoft"))) ? company : param.get("tonbusoft");
			    customer = (Func.isEmpty(param.get("customer"))) ? customer : param.get("customer");
			}
			String year = DateKit.getYear();

			StringBuilder sb = new StringBuilder();
			
			sb.append("<div class=\"footer\">");
			sb.append("	<div class=\"footer-inner\">");
			sb.append("		<div class=\"footer-content\" style=\"height:30px;background-color:#fbfbfb;\">");
			sb.append("			<span class=\"bigger-110\">技术支持 :</span>");
			sb.append("			<span class=\"bigger-110\" id=\"support_tonbusoft\">" + company + "</span>");
			sb.append("			<span class=\"bigger-110\"  style=\"padding-left:15px;\">");
			sb.append("				© " + year);
			sb.append("			</span>");
			sb.append("		</div>");
			sb.append("	</div>");
			sb.append("</div>");
			sb.append("<a href=\"#\" id=\"btn-scroll-up\" class=\"btn-scroll-up btn btn-sm btn-inverse\">");
			sb.append(" <i class=\"ace-icon fa fa-angle-double-up icon-only bigger-110\">");
			sb.append("  顶部");
			sb.append(" </i>");
			sb.append("</a>");
			
			ctx.byteWriter.writeString(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
