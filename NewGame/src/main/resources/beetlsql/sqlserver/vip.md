new_list
===
	  select UserID
      	  ,(SELECT c.Amount FROM QPGameUserDB.dbo.AA_Shop_Prop_UserProp as c WHERE a.UserID = c.user_id and c.Prop_Id = 1) AS Score
      	  ,(SELECT insurescore FROM QPTreasureDB.dbo.GameScoreInfo where UserID = a.UserID) as InsureScore
      	  ,(select SUM(SellCount) SendNum FROM [QPGameUserDB].[dbo].[User_SendGoosRecord] u where Vip_userid=0 and u.UserID=a.UserID) SendNum
      	  ,isnull((select SUM(SellScore) FROM [QPGameUserDB].[dbo].[User_SendGoosRecord] u where u.UserID=a.UserID and DATEDIFF(DAY,RecordDate,DATEADD(DAY,-7,GETDATE()))<=0),0) WeekSendScore
      	  ,isnull((case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end),'无') NickName,a.BeautifulID,a.IsWarehouse
      	  ,(case a.IsWarehouse when 1 then '是'  else '否'  end) IsWarehouseName 
      	  FROM [QPGameUserDB].[dbo].[AccountsInfo] a where Businessman=1
	  @if(!isEmpty(UserID)){
	  	and a.UserID =#{UserID}
	  @}
	  @if(!isEmpty(BeautifulID)){
	  	and a.BeautifulID =#{BeautifulID}
	  @}
	  @if(!isEmpty(Accounts)){
	 	and a.NickName like '%'+#{Accounts}+'%'
	  @}
  
new_vip_gift
===
	select t.TNum,t.TRNum,t.TRcvScore,t.TSR,t.TSendScore,t.TradeUserNum,t.UserID
	,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName,a.BeautifulID,g.InsureScore,p.Amount
	from
	(select SUM(SellScore) TSendScore,SUM(BuyScore) TRcvScore
	,SUM(SellScore-BuyScore) TSR, SUM(SellCount) TNum , SUM(BuyCount) TRNum
	,u.UserID 
	,(select COUNT(distinct s.UserID) from [QPGameUserDB].[dbo].[User_SendGoosRecord] s 
	where s.Vip_userid=u.UserID
	@if(!isEmpty(StartTime)){
		and DATEDIFF(DAY,RecordDate,#{StartTime})<=0
	@}
	@if(!isEmpty(EndTime)){
		and DATEDIFF(DAY,RecordDate,#{EndTime})>=0
	@}
	) TradeUserNum
	FROM [QPGameUserDB].[dbo].[User_SendGoosRecord] u
	where Vip_userid=0
	@if(!isEmpty(UserID)){
		and u.UserID =#{UserID}
	@}
	@if(!isEmpty(StartTime)){
		and DATEDIFF(DAY,RecordDate,#{StartTime})<=0
	@}
	@if(!isEmpty(EndTime)){
		and DATEDIFF(DAY,RecordDate,#{EndTime})>=0
	@}
	group by u.UserID) t,
	[QPGameUserDB].[dbo].[AccountsInfo] a
	,QPTreasureDB.dbo.GameScoreInfo g
	,QPGameUserDB.dbo.AA_Shop_Prop_UserProp p
	where t.UserID=a.UserID and t.UserID=g.UserID and t.UserID=p.User_Id and p.Prop_Id=1
	@if(!isEmpty(BeautifulID)){
    	 	and a.BeautifulID = #{BeautifulID}
    	  @}
	@if(!isEmpty(Accounts)){
		and a.NickName like '%'+#{Accounts}+'%'
	@}

new_vip_giftDay
===
	select t.TNum,t.TRNum,t.TRcvScore,t.TSR,t.TSendScore,t.TradeUserNum,t.UserID
	,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName,a.BeautifulID,g.InsureScore,p.Amount
	from
	(select SUM(SellScore) TSendScore,SUM(BuyScore) TRcvScore
	,SUM(SellScore-BuyScore) TSR, SUM(SellCount) TNum , SUM(BuyCount) TRNum
	,u.UserID 
	,(select COUNT(distinct s.UserID) from [QPGameUserDB].[dbo].[User_SendGoosRecord] s 
	where s.Vip_userid=u.UserID
	@if(!isEmpty(StartTime)){
		and DATEDIFF(DAY,RecordDate,#{StartTime})<=0
	@}
	@if(!isEmpty(EndTime)){
		and DATEDIFF(DAY,RecordDate,#{EndTime})>=0
	@}
	) TradeUserNum
	FROM [QPGameUserDB].[dbo].[User_SendGoosRecord] u
	where Vip_userid=0
	@if(!isEmpty(UserID)){
		and u.UserID =#{UserID}
	@}
	@if(!isEmpty(StartTime)){
		and DATEDIFF(DAY,RecordDate,#{StartTime})<=0
	@}
	@if(!isEmpty(EndTime)){
		and DATEDIFF(DAY,RecordDate,#{EndTime})>=0
	@}
	group by u.UserID) t,
	[QPGameUserDB].[dbo].[AccountsInfo] a
	,QPTreasureDB.dbo.GameScoreInfo g
	,QPGameUserDB.dbo.AA_Shop_Prop_UserProp p
	where t.UserID=a.UserID and t.UserID=g.UserID and t.UserID=p.User_Id and p.Prop_Id=1
	@if(!isEmpty(BeautifulID)){
    	 	and a.BeautifulID = #{BeautifulID}
    	  @}
	@if(!isEmpty(Accounts)){
		and a.NickName like '%'+#{Accounts}+'%'
	@}

all_score
===
	SELECT SUM(SellScore) AS TSendScore, SUM(BuyScore) AS TRcvScore
						, SUM(SellScore - BuyScore) AS TSR
						, SUM(SellCount) AS TNum
					FROM [QPGameUserDB].[dbo].[User_SendGoosRecord] u
					WHERE Vip_userid = 0
						@if(!isEmpty(UserID)){
							and u.UserID =#{UserID}
						@}
						@if(!isEmpty(StartTime)){
							and DATEDIFF(DAY,u.RecordDate,#{StartTime})<=0
						@}
						@if(!isEmpty(EndTime)){
							and DATEDIFF(DAY,u.RecordDate,#{EndTime})>=0
						@}
							@if(!isEmpty(BeautifulID)){
					    	 	and u.UserID in (select UserID from [QPGameUserDB].[dbo].AccountsInfo a where a.BeautifulID=#{BeautifulID})
					    	  @}
						@if(!isEmpty(Accounts)){
						and u.UserID in (select UserID from [QPGameUserDB].[dbo].AccountsInfo a where a.NickName like '%'+#{Accounts}+'%')
						@}
						
all_score_user
===
	SELECT COUNT(DISTINCT u.UserID) AS TradeUserNum
	FROM [QPGameUserDB].[dbo].[User_SendGoosRecord] u
	WHERE u.Vip_userid<>0
	@if(!isEmpty(UserID)){
		and u.Vip_userid =#{UserID}
	@}
	@if(!isEmpty(StartTime)){
		and DATEDIFF(DAY,u.RecordDate,#{StartTime})<=0
	@}
	@if(!isEmpty(EndTime)){
		and DATEDIFF(DAY,u.RecordDate,#{EndTime})>=0
	@}
		@if(!isEmpty(BeautifulID)){
    	 	and u.Vip_userid in (select UserID from [QPGameUserDB].[dbo].AccountsInfo a where a.BeautifulID=#{BeautifulID})
    	  @}
	@if(!isEmpty(Accounts)){
	and u.Vip_userid in (select UserID from [QPGameUserDB].[dbo].AccountsInfo a where a.NickName like '%'+#{Accounts}+'%')
	@}
					
total_list
===  
	select isnull(SUM(SellScore),0) DaySendScore, isnull(SUM(SellCount),0) DaySendNum 
	,isnull((SELECT SUM(Amount) FROM QPGameUserDB.[dbo].[AA_Shop_Prop_UserProp] u with (nolock) 
	where Prop_Id=0 and User_Id in (SELECT UserID from QPGameUserDB.dbo.AccountsInfo where Businessman=1)),0) as ownScore
	,isnull((SELECT SUM(InsureScore) FROM QPTreasureDB.[dbo].[GameScoreInfo] u with (nolock) 
	where u.UserID  in (SELECT UserID from QPGameUserDB.dbo.AccountsInfo where Businessman=1)),0) as InsureScore 
	FROM [QPGameUserDB].[dbo].[User_SendGoosRecord] s with (nolock) 
	where DATEDIFF(DAY,RecordDate,GETDATE())=0 and Vip_userid=0
	  @if(!isEmpty(UserID)){
	  	and UserID =#{UserID}
	  @}
	  @if(!isEmpty(BeautifulID)){
	  	and UserID in (select UserID from [QPGameUserDB].[dbo].AccountsInfo a where a.BeautifulID =#{BeautifulID})
	  @}
	  @if(!isEmpty(Accounts)){
	 	and UserID in (select UserID from [QPGameUserDB].[dbo].AccountsInfo a where a.NickName like '%'+#{Accounts}+'%')
	  @}
svip_list
===  
	select t.*,CONVERT(VARCHAR(100), ai.LastLogonDate, 120) LastLogonDate,ai.Accounts
	,(case when (ai.tipsName is null or ai.tipsName='') then ai.NickName else (ai.NickName+'<span class="text-red">['+ai.tipsName+']</span>') end) NickName
	from (
	SELECT UserID,SUM(ISNULL(SellScore, 0)) SellScore,
	SUM(ISNULL(BuyScore, 0)) BuyScore FROM QPGameUserDB.dbo.User_SendGoosRecord sr
	where sr.Vip_userid<>0
	@if(!isEmpty(startTime_gt)){
		and DATEDIFF(DAY,sr.RecordDate,#{startTime_gt})<=0
	@}
	@if(!isEmpty(endTime_lt)){
		and DATEDIFF(DAY,sr.RecordDate,#{endTime_lt})>=0
	@}

	GROUP BY UserID) t
	LEFT JOIN QPGameUserDB.dbo.AccountsInfo ai on ai.UserID=t.UserID