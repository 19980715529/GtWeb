package com.smallchill.common.task;

import com.smallchill.core.plugins.dao.Db;
import org.beetl.sql.core.OnConnection;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;

@Component()
public class GlobalTask {
	// 定时器去每天统计数据,已经更新到数据库中执行
	//	@Scheduled(cron = "0 03 11 * * *")
	@Scheduled(cron = "0 30 0 1/1 * ? ")
	public void StatisticsEveryDay(){
		// 查询包id
		List<Integer> list = Db.queryListInt("select clientType from [login].[dbo].ClientPos", null);
		list.add(-1);
		for (int i = 1;i<=30;i++){
			for (int j:list){
				newRechargeDetailsStatics(i, j);
			}
		}
	}

	//
	public Integer newRechargeDetailsStatics(int days,int clientType){
		return Db.executeCall(new OnConnection<Integer>() {
			@Override
			public Integer call(Connection conn) {
				try {
					CallableStatement statement = conn.prepareCall("{? = call [QPGameRecordDB].[dbo].[newRechargeDetailsStatics](?,?) }");
					// 注册输出参数
					statement.registerOutParameter(1, Types.INTEGER);
					statement.setInt(2,days);
					statement.setInt(3,clientType);
					statement.execute();
					return statement.getInt(1);
				}catch (Exception e){
					System.out.println(e.getMessage());
					return -1;
				}
			}
		});
	}
}
