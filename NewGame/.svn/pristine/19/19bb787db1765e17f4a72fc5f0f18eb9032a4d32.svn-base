package com.smallchill.common.task;

import com.smallchill.core.plugins.dao.Db;
import org.beetl.sql.core.OnConnection;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component()
public class GlobalTask {
	// 定时器去每天统计数据
	@Scheduled(cron = "0 30 2 * * ? ")
	public void StatisticsEveryDay(){
		Db.executeCall(new OnConnection<Object>() {
			@Override
			public Object call(Connection conn) throws SQLException {
				CallableStatement statement = conn.prepareCall("{call [QPGameRecordDB].[dbo].[DailyGameStatistics]() }");
				statement.execute();
				return null;
			}
		});
	}
}
