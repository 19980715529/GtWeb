list
===
	SELECT [id] ,[UserID] ,[type] ,[UpdateTime]
	,(case [type] when 0 then '赠送金币返利账号' else '银行金币返利账号' end) TypeName
	,isnull((select (case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) from [QPGameUserDB].[dbo].[AccountsInfo] a where a.UserID=g.UserID),'无昵称') NickName
	 FROM [QPGameUserDB].[dbo].[RebateAccount] g