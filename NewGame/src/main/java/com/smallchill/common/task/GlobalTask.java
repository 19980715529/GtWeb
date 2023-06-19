package com.smallchill.common.task;

import com.smallchill.core.plugins.dao.Db;
import org.beetl.sql.core.OnConnection;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component()
public class GlobalTask {
	// 定时器去每天统计数据,已经更新到数据库中执行
	@Scheduled(cron = "0 30 0 1/1 * ? ")
	public void StatisticsEveryDay(){
		// 查询包id
		List<Integer> list = Db.queryListInt("select clientType from [login].[dbo].ClientPos", null);
		for (int i = 1;i<=30;i++){
			for (int j:list){
				newRechargeDetailsStatics(i,j);
			}
		}
	}

	//
	public void newRechargeDetailsStatics(int days,int clientType){
		Db.executeCall(new OnConnection<Object>() {
			@Override
			public Object call(Connection conn) {
				try {
					CallableStatement statement = conn.prepareCall("{call [QPGameRecordDB].[dbo].[newRechargeDetailsStatics](?,?) }");
					statement.setInt(1,days);
					statement.setInt(1,clientType);
					statement.execute();
					return null;
				}catch (Exception e){
					return null;
				}
			}
		});
	}
}
