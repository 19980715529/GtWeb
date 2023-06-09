package com.smallchill.test.beetlsql;

import com.smallchill.core.beetl.ReportInterceptor;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.kit.CharsetKit;

import org.beetl.sql.core.*;
import org.beetl.sql.core.db.PostgresStyle;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@SuppressWarnings("rawtypes")
public class BeetlTest {
	
	public static void main(String[] args) {
		Map params = new HashMap();
		params.put("startTime", "000");
		params.put("startTime", "000");
		params.put("startTime", "000");
		System.out.println(params);
	}

	//@Test
	public void test() {
		List<Map> list = getSqlManager().execute("select * from blade_notice where id = #{id}", Map.class, CMap.init().set("id", 1), 1, 10);
		System.out.println(list);
	}

	public SQLManager getSqlManager() {
		PostgresStyle style = new PostgresStyle();
		PostgreSqlConnection cs = new PostgreSqlConnection();
		SQLLoader loader = new ClasspathLoader("/beetlsql");
		loader.setCharset(CharsetKit.UTF_8);
		loader.setAutoCheck(true);
		ClassPathResource cpr = new ClassPathResource("beetlsql.properties");
		Properties properties = new Properties();
		InputStream in = null;
        try {
            in = cpr.getInputStream();
            properties.load(in);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (in != null) {
                try { in.close(); } catch (IOException e) {}
                in = null;
            }
        }
		SQLManager sql = new SQLManager(style, loader, cs, new DefaultNameConversion(), new Interceptor[] {new ReportInterceptor()}, null, properties);
		return sql;
	}

}
