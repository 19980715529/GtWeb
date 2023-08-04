list
===
	SELECT [minRecharge] ,[maxRecharge] ,[pointControlNum],[TotalControlNum],[TotalScore],[ToDayScore]
	      ,[cheatRate] ,[cheatValuePercentMin] ,[cheatValuePercentMax],[TodayMinRechange]
	      ,[isOpen] ,[maxExchange] ,[CheatValue] ,[id]
	      ,(case when isOpen=0 then '否' else '是' end) isOpenName
	FROM [QPTreasureDB].[dbo].[KillBugUser]
	@if(!isEmpty(serverID)){
	 where [id]=#{serverID}
	@}