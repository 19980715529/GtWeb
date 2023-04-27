list
===
	SELECT l.userID ,[reson] ,c.RealScore,c.js_BussniessCount,c.out_BussniessCount
	  ,a.RegisterDate,a.LastLogonDate,a.BeautifulID
	  ,(case when (DATEDIFF(DAY,c.changeScoreTime,GETDATE())>0) then 0 else c.todayScore end) as todayScore
	  ,(SELECT c.Amount FROM QPGameUserDB.dbo.AA_Shop_Prop_UserProp as c WHERE l.userID = c.user_id and c.Prop_Id = 1) AS TScore
	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
	  ,isnull((SELECT d.name FROM QPGameUserDB.dbo.AccountTypeName as d where a.clientType = d.clientType),(case when a.ClientType=0 then '大厅' else '包'+cast(a.ClientType as varchar) end)) as PackageName
	FROM [QPServerInfoDB].[dbo].[L_QuestionUser] l,
	[QPGameUserDB].[dbo].[AccountsInfo] a,
	[QPTreasureDB].[dbo].[GameScoreInfo] c
	where l.userID=a.UserID and l.userID=c.UserID
	@if(!isEmpty(UserID)){
	 	and l.userID =#{UserID}
	@}