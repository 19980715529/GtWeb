new_total_list
===
	select 
	totalscore=(select SUM(cast(amount as bigint)) as score from  [QPGameUserDB].dbo.AA_Shop_Prop_UserProp with (nolock) where Prop_Id=1 and User_Id not in (select userid from [QPGameUserDB].dbo.accountsinfo where isRobit=0))
	,xtsy=(select sum(cast(TodayScore as bigint)) as TodayScore  FROM QPServerInfoDB.dbo.AA_Log_GameRoomScore as s WITH (NOLOCK) WHERE DATEDIFF(DAY,LogDate,GETDATE())=0)
	,sendscore=(select SUM(CAST(TotalGold AS BIGINT)) as sendscore from [QPGameUserDB].[dbo].K_PropChange_Log where DATEDIFF(DAY,LogDate,GETDATE())=0)
	,czscore=(select SUM(cast(GetGold as bigint)) as czscore from [QPGameUserDB].[dbo].[AA_Recharge_TotalRecord] with (nolock) where  DATEDIFF(DAY,LogTime,GETDATE())=0 and  User_Id not in (select userid from [QPGameUserDB].dbo.accountsinfo where isRobit=0))
	,totalprizes=(select SUM(cast(amount as bigint)) as prizes from  [QPGameUserDB].dbo.AA_Shop_Prop_UserProp with (nolock) where Prop_Id=2 and  User_Id not in (select userid from [QPGameUserDB].dbo.accountsinfo where isRobit=0))
	,sendprizes=(select SUM(cast(amount as bigint)) as sendprizes from [QPGameUserDB].dbo.AA_ZZ_Log_PropChange
	as a with (nolock) inner join [QPGameUserDB].dbo.AA_ZZ_Log_PropChange_ChangeType as t  with (nolock)
	on a.ChangeType_Id=t.ChangeType_Id and  User_Id not in 
	(select userid from [QPGameUserDB].dbo.accountsinfo where isRobit=0)
	where Prop_Id=2 and t.Remark like '%奖励%' and DATEDIFF(DAY,LogTime,GETDATE())=0)
	,dhprizes=(select abs(SUM(cast(amount as bigint))) as dhprizes from [QPGameUserDB].dbo.AA_ZZ_Log_PropChange
	as a  with (nolock)inner join [QPGameUserDB].dbo.AA_ZZ_Log_PropChange_ChangeType as t  with (nolock)
	on a.ChangeType_Id=t.ChangeType_Id 
	where Prop_Id=2 and t.ChangeType_Id in(18,19,22,23) and  User_Id not in 
	(select userid from [QPGameUserDB].dbo.accountsinfo where isRobit=0) and DATEDIFF(DAY,LogTime,GETDATE())=0)
	,czUserLogin=(select count(distinct(User_Id)) TLoginCount FROM [QPGameUserDB].[dbo].[AA_ZZ_Log_Login])
	,DLoginUserCount=(select count(distinct(User_Id)) DLoginCount FROM [QPGameUserDB].[dbo].[AA_ZZ_Log_Login] where DATEDIFF(DAY,LogTime,GETDATE())=0)
	,totalreg=(select count(userid) as rcount from [QPGameUserDB].dbo.accountsinfo as a with (nolock)  where IsTestUser=0)
	,todayreg=(select count(userid) as rcount from [QPGameUserDB].dbo.accountsinfo as a with (nolock)  where IsTestUser=0 and  DATEDIFF(DAY,RegisterDate,GETDATE())=0)
	,iosRegister=(select iosregcount=count(UserID) from [QPGameUserDB].dbo.accountsinfo as a with (nolock) where  Platform_Id=0 and IsTestUser=0 
	and UserID not in (select UserID from QPTreasureDB.dbo.AndroidUserInfo with (nolock)))
	,andRegister=(select andregcount=count(UserID) from [QPGameUserDB].dbo.accountsinfo as a with (nolock) where  Platform_Id=1 and IsTestUser=0 
	and UserID in (select UserID from QPTreasureDB.dbo.AndroidUserInfo with (nolock)))
	,iosdRegister=(select iosregcount=count(UserID) from [QPGameUserDB].dbo.accountsinfo as a with (nolock) where  Platform_Id=0 and IsTestUser=0 and DATEDIFF(DAY,RegisterDate,GETDATE())=0 
	and UserID not in (select UserID from QPTreasureDB.dbo.AndroidUserInfo with (nolock)))
	,anddRegiste=(select andregcount=count(UserID) from [QPGameUserDB].dbo.accountsinfo as a with (nolock) where  Platform_Id=1 and IsTestUser=0 and DATEDIFF(DAY,RegisterDate,GETDATE())=0 
	and UserID in (select UserID from QPTreasureDB.dbo.AndroidUserInfo with (nolock)))
	,totalmoney=(select SUM(cast(UnitRMB as bigint)) as totalmoney from [QPGameUserDB].[dbo].[AA_Recharge_TotalRecord] with (nolock) where User_Id not in 
	(select userid from [QPGameUserDB].dbo.accountsinfo where isRobit=0))
	,totalRechargeUser=(select COUNT(distinct(User_Id)) RechargeUser from [QPGameUserDB].[dbo].[AA_Recharge_TotalRecord] with (nolock) where User_Id not in 
	(select userid from [QPGameUserDB].dbo.accountsinfo where isRobit=0))
	,todaymoney=(select SUM(cast(UnitRMB as bigint)) as dczmoney from [QPGameUserDB].[dbo].[AA_Recharge_TotalRecord] with (nolock) where User_Id not in 
	(select userid from [QPGameUserDB].dbo.accountsinfo where isRobit=0) and DATEDIFF(DAY,LogTime,GETDATE())=0)
	,todayRechargeUser=(select COUNT(distinct(User_Id)) RechargeUser from [QPGameUserDB].[dbo].[AA_Recharge_TotalRecord] with (nolock) where User_Id not in 
	(select userid from [QPGameUserDB].dbo.accountsinfo where isRobit=0) and DATEDIFF(DAY,LogTime,GETDATE())=0)
	,maxonline=(select MAX(OnlineCount) OnlineCount from QPServerInfoDB.dbo.GameOnlineCountByFromID with (nolock) where DATEDIFF(DAY,CountTime,GETDATE())=0)
	,OnlineUserCount=(select COUNT(UserID) OnlineNow FROM [QPTreasureDB].[dbo].[GameScoreLocker])

new_list
===
	select [r_id] ,[writedate] ,[totalscore] ,[totalprizes] ,[sendscore]
      ,[czscore] ,[sendprizes] ,[dhprizes] ,isnull([totalreg],0) totalreg ,[totalmoney]
      ,[maxonline] ,[xtsy] ,isnull([todayreg],0) todayreg ,[todaymoney] ,[vipscore]
      ,[userscore] ,[BuyScore] ,[SellScore] ,[BloodScore]
      ,[czUserLogin] ,[gameRevenue] ,[jyUser] ,(isnull([insurejyCount],0)+isnull([jyCount],0)) as jyCount,isnull(UserCheateBlood,0) UserCheateBlood
      ,[CheateBlood] ,CAST(ttl as varchar) ttl,isnull([insureSellScore],0) as insureSellScore
      ,isnull([insureBuyScore],0) as insureBuyScore
      ,isnull([insurejyUser],0) as insurejyUser
      ,isnull([insurejyCount],0) as insurejyCount
	,CONVERT(VARCHAR(100), writedate, 23) as SDate,(SellScore-BuyScore) as RS,
	(isnull(insureSellScore,0)-isnull(insureBuyScore,0)) as InsureRS,
	(isnull(insureSellScore,0)+isnull(SellScore,0)-isnull(insureBuyScore,0)-isnull(BuyScore,0)) as TotalRS,
	(case (isnull(insureSellScore,0)+isnull(SellScore,0)) when 0 then '1.000' else CONVERT(decimal(18,3),(cast((isnull(insureBuyScore,0)+isnull(BuyScore,0)) as FLOAT)/cast((isnull(insureSellScore,0)+isnull(SellScore,0)) as FLOAT))) end) as Rate
	FROM [QPGameRecordDB].[dbo].[TotalScoreRecord]
	where 1=1
	@if(!isEmpty(StartTime)){
		 and CONVERT(VARCHAR(100), writedate, 23) >= CONVERT(VARCHAR(100), #{StartTime}, 23)
	  @}
	  @if(!isEmpty(EndTime)){
		 and CONVERT(VARCHAR(100), writedate, 23) <= CONVERT(VARCHAR(100), #{EndTime}, 23)
	  @}
	 order by writedate desc
	 
old_list
===
    	select [r_id] ,[writedate] ,[totalscore] ,[totalprizes] ,[sendscore]
          ,[czscore] ,[sendprizes] ,[dhprizes] ,[totalreg] ,[totalmoney]
          ,[maxonline] ,[xtsy] ,[todayreg] ,[todaymoney] ,[vipscore]
          ,[userscore] ,[BuyScore] ,[SellScore] ,[BloodScore]
          ,[czUserLogin] ,[gameRevenue] ,[jyUser] ,[jyCount]
          ,[CheateBlood] ,CAST(ttl as varchar) ttl
    	,CONVERT(VARCHAR(100), writedate, 23) as SDate,(SellScore-BuyScore) as RS,
    	(case when (SellScore=0) then '1.000' else CONVERT(decimal(18,3),(cast(BuyScore as FLOAT)/cast(SellScore as FLOAT))) end) as Rate
    	FROM [QPGameRecordDB].[dbo].[TotalScoreRecord]
    	where 1=1
    	@if(!isEmpty(StartTime)){
    		 and CONVERT(VARCHAR(100), writedate, 23) >= CONVERT(VARCHAR(100), #{StartTime}, 23)
    	  @}
    	  @if(!isEmpty(EndTime)){
    		 and CONVERT(VARCHAR(100), writedate, 23) <= CONVERT(VARCHAR(100), #{EndTime}, 23)
    	  @}
    	 order by writedate desc

record_list
===
    select CONVERT(VARCHAR(10), createTime, 23) writeTime,*,(turntableAwardCoins+rechargeCoins+ShareRewardCoins+BindPhoneRewards) RewardCoins,
    (case when (recMoney=0) then '0.000' else CONVERT(decimal(18,3),(cast((recMoney-excMoney) as FLOAT)/cast(recMoney as FLOAT))) end) as Proportion
    FROM [QPGameRecordDB].[dbo].[DailyGameStats]
    where 1=1
    @if(!isEmpty(clientType)){
        and clientType=#{clientType}
    @}
    @if(!isEmpty(StartTime)){
        and CONVERT(VARCHAR(100), createTime, 23) >= CONVERT(VARCHAR(100), #{StartTime}, 23)
    @}
    @if(!isEmpty(EndTime)){
        and CONVERT(VARCHAR(100), createTime, 23) <= CONVERT(VARCHAR(100), #{EndTime}, 23)
    @}
    