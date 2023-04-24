list
===
	SELECT [minRecharge] ,[maxRecharge] ,[pointControlNum]
	      ,[cheatRate] ,[cheatValuePercentMin] ,[cheatValuePercentMax]
	      ,[isOpen] ,[maxExchange] ,[winScoreMax] ,[id]
	      ,(case when isOpen=0 then '否' else '是' end) isOpenName
	FROM [QPTreasureDB].[dbo].[KillBugUser]
	@if(!isEmpty(serverID)){
	 where [id]=#{serverID}
	@}