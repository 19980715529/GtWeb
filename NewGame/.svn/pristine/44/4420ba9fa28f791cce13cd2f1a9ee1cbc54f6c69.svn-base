new_list_bak
===
	  select t.OnlineCount,left(t.Times,5) Times from
	  (select convert(char(10),CountTime,108) Times,sum(OnlineCount) OnlineCount 
	  FROM [QPServerInfoDB].[dbo].[GameOnlineCountByFromID]
	   where DATEDIFF(day,#{startTime},CountTime)=0
	   @if(!isEmpty(KindID)){
		 and KindID =#{KindID}
	  @}
	  @if(!isEmpty(ServerID)){
		 and ServerID =#{ServerID}
	  @}
	   group by CountTime) t
	   order by t.Times asc

new_list
===
	select SUM(OnlineCount) OnlineCount,Convert(decimal(18,2),Datename(hour,CountTime)+'.'+Datename(minute,CountTime)) CountTime 
	   FROM [QPServerInfoDB].[dbo].[GameOnlineCountByFromID]
	   where DATEDIFF(day,#{startTime},CountTime)=0
	   @if(!isEmpty(KindID)){
		 and KindID =#{KindID}
	  @}
	  @if(!isEmpty(ServerID)){
		 and ServerID =#{ServerID}
	  @}
	   group by CountTime
	   order by CountTime asc

new_all_list
===
	SELECT COUNT(OnlineCount) OnlineCount, CONVERT(VARCHAR(100), CountTime, 120) CountTime
	  FROM [QPServerInfoDB].[dbo].[GameOnlineCountByFromID]
	  where 1=1
	  @if(!isEmpty(startTime)){
	  	and DATEDIFF(day, #{startTime}, CountTime)>=0
	  @}
	  @if(!isEmpty(endTime)){
	  	and DATEDIFF(day, #{endTime}, CountTime)<=0
	  @}
	  @if(!isEmpty(KindID)){
		 and KindID =#{KindID}
	  @}
	  group by CountTime
	  order by CountTime asc
	
new_gs_list
===
	  select KindID,
	  isnull((select KindName FROM [QPServerInfoDB].[dbo].[GameKindItem] where KindID=g.KindID),'大厅') KindName,
	  COUNT(1) OnlineCount FROM [QPTreasureDB].[dbo].[GameScoreLocker]  g
	  where g.UserID not in (SELECT UserID FROM QPGameUserDB.dbo.AccountsInfo WHERE Businessman=1)
	  @if(!isEmpty(KindID)){
		 and KindID =#{KindID}
	  @}
	  @if(!isEmpty(ServerID)){
		 and ServerID =#{ServerID}
	  @}
	  group by KindID

new_gsr_list
===
	  select
	  (select RoomName FROM [QPServerInfoDB].[dbo].[GameRoomItem] where ServerID=g.ServerID) ServerName,
	  COUNT(*) OnlineCount FROM [QPTreasureDB].[dbo].[GameScoreLocker] g
	  where g.UserID not in (SELECT UserID FROM QPGameUserDB.dbo.AccountsInfo WHERE Businessman=1)
	  @if(!isEmpty(KindID)){
	  	and KindID=#{KindID}
	  @}
	  group by ServerID

new_day_avg_list
===
	  select CONVERT(decimal(10,3),(SUM(cast(OnlineCount as FLOAT))/count(1))) OnlineCount,CONVERT(VARCHAR(100), CountTime, 23) ApplyDate
	  from
	  (select SUM(OnlineCount) OnlineCount,CountTime 
	  from [QPServerInfoDB].[dbo].[GameOnlineCountByFromID]
	  where 1=1
	  @if(!isEmpty(startTime)){
		  and DATEDIFF(day, #{startTime}, CountTime)>=0
	  @}
	  @if(!isEmpty(endTime)){
		  and DATEDIFF(day, #{endTime}, CountTime)<=0
	  @}
	  @if(!isEmpty(KindID)){
		 and KindID =#{KindID}
	  @}
	  @if(!isEmpty(ServerID)){
		 and ServerID =#{ServerID}
	  @}
	  group by CountTime
	  ) t
	  group by CONVERT(VARCHAR(100), CountTime, 23)
	  order by CONVERT(VARCHAR(100), CountTime, 23) asc

new_day_high_list
===
	  select MAX(OnlineCount) OnlineCount,CONVERT(VARCHAR(100), CountTime, 23) ApplyDate
	  from
	  (select SUM(OnlineCount) OnlineCount,CountTime 
	  from [QPServerInfoDB].[dbo].[GameOnlineCountByFromID]
	  where 1=1
	  @if(!isEmpty(startTime)){
	  and DATEDIFF(day, #{startTime}, CountTime)>=0
	@}
	@if(!isEmpty(endTime)){
	  and DATEDIFF(day, #{endTime}, CountTime)<=0
	@}
	@if(!isEmpty(KindID)){
		 and KindID =#{KindID}
	  @}
	  @if(!isEmpty(ServerID)){
		 and ServerID =#{ServerID}
	  @}
	  group by CountTime
	  ) t
	  group by CONVERT(VARCHAR(100), CountTime, 23)
	  order by CONVERT(VARCHAR(100), CountTime, 23) asc
	  
online_week_list、
===
	SELECT Convert(decimal(10,3),(cast(SUM(OnlineCount) as FLOAT)/count(1))) OnlineCount,datepart(Wk,ApplyDate) Week,YEAR(ApplyDate) Year FROM
	(select CONVERT(decimal(10,3),(SUM(cast(OnlineCount as FLOAT))/count(1))) OnlineCount,CONVERT(VARCHAR(100), CountTime, 23) ApplyDate
	  from
	  (select SUM(OnlineCount) OnlineCount,CountTime 
	  from [QPServerInfoDB].[dbo].[GameOnlineCountByFromID]
	  where 1=1
	  @if(!isEmpty(startTime)){
		  and DATEDIFF(day, #{startTime}, CountTime)>=0
	  @}
	  @if(!isEmpty(endTime)){
		  and DATEDIFF(day, #{endTime}, CountTime)<=0
	  @}
	  @if(!isEmpty(KindID)){
		 and KindID =#{KindID}
	  @}
	  @if(!isEmpty(ServerID)){
		 and ServerID =#{ServerID}
	  @}
	  group by CountTime
	  ) t
	  group by CONVERT(VARCHAR(100), CountTime, 23)
	  ) t
	group BY YEAR(ApplyDate),datepart(Wk,ApplyDate)
	order BY YEAR(ApplyDate) ASC,datepart(Wk,ApplyDate) ASC