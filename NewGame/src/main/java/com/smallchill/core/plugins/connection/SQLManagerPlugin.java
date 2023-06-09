package com.smallchill.core.plugins.connection;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.beetl.sql.core.IDAutoGen;
import org.beetl.sql.core.SQLManager;

import com.smallchill.core.config.BladeConfig;
import com.smallchill.core.plugins.IPlugin;
import com.smallchill.core.toolbox.kit.LogKit;

/**
 * SQLManager插件
 */
public class SQLManagerPlugin implements IPlugin {
	private static Map<String, SQLManager> sqlManagerPool = new ConcurrentHashMap<String, SQLManager>();
	
	public String MASTER = "master";
	
	public Map<String, SQLManager> getSqlManagerPool(){
		return sqlManagerPool;
	}
	
	private SQLManagerPlugin() { }
	
	private static SQLManagerPlugin me = new SQLManagerPlugin();
	
	public static SQLManagerPlugin init(){
		return me;
	}
	
	public void start() {
		try {
			//注入sqlmanager
			for(String key : BladeConfig.getSqlManagerPool().keySet()){
				SQLManager sm = BladeConfig.getSqlManagerPool().get(key);
				//增加自定义@AssignID注解的值, 使用方式: @Assign("uuid")
				sm.addIdAutonGen("uuid", new IDAutoGen<String>() {
					public String nextID(String arg0) {
						return UUID.randomUUID().toString();
					}
				});
				sqlManagerPool.put(key, sm);
			}
			if(!sqlManagerPool.containsKey(MASTER)){
				throw new RuntimeException("BladeConfig必须注入key值为master的sqlManager!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		sqlManagerPool.clear();
		LogKit.println("SQLManagerPlugin关闭成功");
	}

}
