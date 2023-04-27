package com.smallchill.test.beetlsql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.beetl.sql.core.ClasspathLoader;
import org.beetl.sql.core.ConnectionSource;
import org.beetl.sql.core.ConnectionSourceHelper;
import org.beetl.sql.core.DefaultNameConversion;
import org.beetl.sql.core.Interceptor;
import org.beetl.sql.core.NameConversion;
import org.beetl.sql.core.OnConnection;
import org.beetl.sql.core.SQLLoader;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.UnderlinedNameConversion;
import org.beetl.sql.core.db.DBStyle;
import org.beetl.sql.core.db.SqlServerStyle;
import org.beetl.sql.ext.DebugInterceptor;

import com.smallchill.game.vip.model.Accountsinfo;
import com.smallchill.system.model.User;

public class App {
	private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//	private static String url = "jdbc:sqlserver://192.168.2.99:1433;DatabaseName=RYPlatformManagerDB;integratedSecurity=false";
//	private static String url = "jdbc:sqlserver://47.108.87.176:1433;DatabaseName=QPGameUserDB;integratedSecurity=false";
//	private static String url = "jdbc:sqlserver://192.168.2.99:1433;DatabaseName=QPTreasureDB;integratedSecurity=false";
//	private static String url = "jdbc:sqlserver://192.168.2.99:1433;DatabaseName=QPGameRecordDB;integratedSecurity=false";
//	private static String url = "jdbc:sqlserver://192.168.2.99:1433;DatabaseName=QPServerInfoDB;integratedSecurity=false";
	private static String url = "jdbc:sqlserver://192.168.10.159:1433;DatabaseName=QPGameUserDB;integratedSecurity=false";
	private static String userName = "sa";
	private static String password = "sfd4532##$9";

	public static void main(String[] args) {
		ConnectionSource source = ConnectionSourceHelper.getSimple(driver, url,
				userName, password);
		DBStyle mysql = new SqlServerStyle();
		// sql语句放在classpagth的/sql 目录下
		SQLLoader loader = new ClasspathLoader("/beetlsql");
		// 数据库命名跟java命名一样，所以采用DefaultNameConversion，还有一个是UnderlinedNameConversion，下划线风格的，
		UnderlinedNameConversion nc = new UnderlinedNameConversion();
//		NameConversion nc = new  DefaultNameConversion();
		// 最后，创建一个SQLManager,DebugInterceptor 不是必须的，但可以通过它查看sql执行情况
		SQLManager sqlManager = new SQLManager(mysql, loader, source, nc,
				new Interceptor[] { new DebugInterceptor() });
//		insert(sqlManager);
		genPOJO(sqlManager);
		//call(sqlManager);
		//searchByMd(sqlManager);
	}

	public static void genPOJO(SQLManager sqlManager) {
		// 使用内置的生成的sql 新增用户，如果需要获取主键，可以传入KeyHolder
		try {
			sqlManager.genPojoCodeToConsole("AA_ExchangeCode");
//			sqlManager.genSQLTemplateToConsole("RecordInsure");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void insert(SQLManager sqlManager) {
		// 使用内置的生成的sql 新增用户，如果需要获取主键，可以传入KeyHolder
		User user = new User();
//		user.setAge(19);
		user.setName("xiandafu");
		int insert = sqlManager.insert(user);
		System.out.println(insert);
	}

	public static void get(SQLManager sqlManager) {
		User user = new User();
		// 使用内置sql查询用户
		int id = 1;
		user = sqlManager.unique(User.class, id);
		System.out.println(user.getName());
	}

	public static void update(SQLManager sqlManager) {
		// 模板更新,仅仅根据id更新值不为null的列
		User newUser = new User();
		newUser.setId(1);
//		newUser.setAge(20);
		int updateTemplateById = sqlManager.updateTemplateById(newUser);
		System.out.println(updateTemplateById);
	}

	public static void search(SQLManager sqlManager) {
		// 模板查询
		User query = new User();
		query.setName("xiandafu");
		List<User> list = sqlManager.template(query);
		for (User user : list) {
			System.out.println(user.getName());
		}
	}

	public static void searchByMd(SQLManager sqlManager) {
		// 使用user.md 文件里的select语句，参考下一节。
		Accountsinfo query2 = new Accountsinfo();
//		query2.set
		query2.setMemberorder(11);
		List<Accountsinfo> list2 = sqlManager.select("vip_demo.sample", Accountsinfo.class, query2);
		for (Accountsinfo user : list2) {
			System.out.println(user.getAccounts());
		}
	}
	
	public static void call(SQLManager sqlManager) {
		String md5 = sqlManager.executeOnConnection(new OnConnection<String>(){
			@Override
			public String call(Connection conn) throws SQLException {
				CallableStatement cstmt = conn.prepareCall("{call [QPGameUserDB].[dbo].[VIP_Test]( ? ) }");
				ResultSet rs = null;
				//设置输入参数
				cstmt.setInt(1, 79049);
	            
	            //发送参数
	            rs = cstmt.executeQuery(); //注意： 所有调用存储过程的sql语句都是使用executeQuery方法执行！！！
	            
	            //遍历结果
	            while(rs.next()){
	                String name = rs.getString("Accounts");
	                String gender = rs.getString("NickName");
	                System.out.println(name+","+gender);
	            }
				// 其他代码
				return "";
			}
		});
		System.out.println("============:"+md5);
	}
}
