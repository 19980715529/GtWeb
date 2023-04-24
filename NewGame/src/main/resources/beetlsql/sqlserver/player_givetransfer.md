list
===
	SELECT b.userID as userID
	,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
	,(SELECT c.Amount FROM QPGameUserDB.dbo.AA_Shop_Prop_UserProp as c WHERE b.userID = c.user_id and c.Prop_Id = 1) AS Amount,
	a.RegisterDate,
	a.LastLogonDate,
	c.RealScore,
	(case when (DATEDIFF(DAY,c.changeScoreTime,GETDATE())>0) then 0 else c.todayScore end) as todayScore,
	c.js_BussniessCount,
	c.out_BussniessCount
	,isnull((select SUM(BuyScore) FROM [QPGameUserDB].[dbo].[User_SendGoosRecord] as u where u.UserID=a.UserID and DATEPART(YYYY,getdate())=DATEPART(YYYY,u.RecordDate) and DATEPART(m,getdate())=DATEPART(m,u.RecordDate)),0) MonthBuyScore
	FROM QPGameUserDB.dbo.AccountsInfo AS a,
	QPGameUserDB.dbo.AutoUserList as b,
	QPTreasureDB.dbo.GameScoreInfo as c
	WHERE a.userid = b.userid and b.userid = c.userid
	@if(!isEmpty(UserID)){
	 	and a.UserID =#{UserID}
	@}