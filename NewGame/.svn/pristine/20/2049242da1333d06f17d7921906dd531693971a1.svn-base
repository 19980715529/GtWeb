old_trade_list
===
	select t.*,a.GameID,b.js_BussniessCount AS TBuyScore,b.CheatRate,a.BeautifulID,
	(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
	,a.RegisterDate,(t.TRcvScore-t.TSendScore) TRS,
	  (select s.Amount FROM [QPGameUserDB].[dbo].[AA_Shop_Prop_UserProp] s where s.User_Id=t.UserID and s.Prop_Id=1) Score,
	  (select b.RealScore from QPTreasureDB.dbo.GameScoreInfo b where b.UserID=t.UserID) TotalWaste,
	  (select (case when (DATEDIFF(DAY,b.changeScoreTime,GETDATE())>0) then 0 else b.todayScore end) todayScore from QPTreasureDB.dbo.GameScoreInfo b where b.UserID=t.UserID) DayWaste,
	  (select (case when (DATEDIFF(day,'1900-01-01',b.lastEndCheatTime)=0) then '无' else CONVERT(VARCHAR(100), b.lastEndCheatTime, 120) end) lastEndCheatTime from QPTreasureDB.dbo.GameScoreInfo b where b.UserID=t.UserID) FinishTime
	  from
	  (select SUM(BuyCount) TRcvNum,SUM(BuyScore) TRcvScore,SUM(SellScore) TSendScore,u.UserID
	  from [QPGameUserDB].[dbo].[User_SendGoosRecord] u
	  where Vip_userid<>0
	  @if(!isEmpty(SendTime_dategt)){
	  	and DATEDIFF(DAY,u.RecordDate,#{SendTime_dategt})<=0
	  @}
	  @if(!isEmpty(SendTime_datelt)){
		 and DATEDIFF(DAY,u.RecordDate,#{SendTime_datelt})>=0
	  @}
	  group by u.UserID) t,
	  [QPGameUserDB].[dbo].[AccountsInfo] a,
	  QPTreasureDB.dbo.GameScoreInfo as b
	  where t.UserID=a.UserID and t.UserID=b.UserID and (a.Businessman=0 or a.Businessman is null)
	  @if(!isEmpty(UserID)){
	 	and t.UserID =#{UserID}
	  @}
	  @if(!isEmpty(GameID)){
	 	and a.GameID =#{GameID}
	  @}
trade_list
===
     	select t.*,
    	(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName,
    	  (select s.Amount FROM [QPGameUserDB].[dbo].[AA_Shop_Prop_UserProp] s where s.User_Id=t.User_ID and s.Prop_Id=1) Score,
    	  (select b.RealScore from QPTreasureDB.dbo.GameScoreInfo b where b.UserID=t.User_ID) TotalWaste,
    	  (select (case when (DATEDIFF(DAY,b.changeScoreTime,GETDATE())>0) then 0 else b.todayScore end) todayScore from QPTreasureDB.dbo.GameScoreInfo b where b.UserID=t.User_ID) DayWaste,
    	  (select SUM([Money]) FROM [QPGameUserDB].[dbo].[AA_Recharge_By_WithdrawalRecord] w where w.User_Id=t.User_ID ) TRS,
    	  (select SUM([Money]) FROM [QPGameUserDB].[dbo].[AA_Recharge_By_WithdrawalRecord] w where w.User_Id=t.User_ID ) Domwnnum
    	  from
    	  (select SUM([Golds]) GoldsNum,u.User_ID,COUNT(*) Upnum
    	  from [QPGameUserDB].[dbo].[AA_Recharge_By_Record] u
    	   where u.bStatus<>0 
    	  @if(!isEmpty(SendTime_dategt)){
    	  	and DATEDIFF(DAY,u.OverTime,#{SendTime_dategt})<=0
    	  @}
    	  @if(!isEmpty(SendTime_datelt)){
    		 and DATEDIFF(DAY,u.OverTime,#{SendTime_datelt})>=0
    	  @}
    	  @if(!isEmpty(User_ID)){
             and u.User_ID =#{User_ID}
          @}
    	  group by u.User_ID) t,
    	  [QPGameUserDB].[dbo].[AccountsInfo] a
    	  where t.User_ID=a.UserID and (a.Businessman=0 or a.Businessman is null)
    	  
trade_total
===
	select isnull(sum(t.TRcvScore),0) TRcvScore,isnull(sum(t.Score),0) Score, isnull(sum(t.TotalWaste),0) TotalWaste,count(1) UserNum
	from
	(select t.TRcvScore,t.UserID,
	  (select s.Amount FROM [QPGameUserDB].[dbo].[AA_Shop_Prop_UserProp] s where s.User_Id=t.UserID and s.Prop_Id=1) Score,
	  (select s.Amount FROM [QPGameUserDB].[dbo].[AA_Shop_Prop_UserProp] s where s.User_Id=t.UserID and s.Prop_Id=80) InsureScore,
	  b.RealScore TotalWaste
	  from 
	  (select SUM(BuyScore) TRcvScore,u.UserID
	  from [QPGameUserDB].[dbo].[User_SendGoosRecord] u
	  where Vip_userid<>0
	  @if(!isEmpty(SendTime_dategt)){
	  	and DATEDIFF(DAY,u.RecordDate,#{SendTime_dategt})<=0
	  @}
	  @if(!isEmpty(SendTime_datelt)){
		 and DATEDIFF(DAY,u.RecordDate,#{SendTime_datelt})>=0
	  @}
	  group by u.UserID) t,
	  [QPGameUserDB].[dbo].[AccountsInfo] a,
	  QPTreasureDB.dbo.GameScoreInfo as b
	  where t.UserID=a.UserID and t.UserID=b.UserID and (a.MemberOrder=0 or a.MemberOrder is null)
	  @if(!isEmpty(UserID)){
	 	and t.UserID =#{UserID}
	  @}
	  @if(!isEmpty(GameID)){
	 	and a.GameID =#{GameID}
	  @}
	  ) t