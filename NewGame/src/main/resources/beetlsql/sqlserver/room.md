new_list
===
	  	select g.ServerID,g.RoomName,g.RealScore,g.totalCheatBlood,g.totalTaxScore,g.BloodScore
	  	,isnull((select SUM(OutScore) from [QPServerInfoDB].[dbo].[AA_Log_GameRoomScore]
	  where DATEDIFF(day,getdate(),LogDate)=0 and ServerID=g.ServerID),0) OutScore
	  	,isnull((select SUM(EatScore) from [QPServerInfoDB].[dbo].[AA_Log_GameRoomScore]
	  where DATEDIFF(day,getdate(),LogDate)=0 and ServerID=g.ServerID),0) MaxEatScore
		,isnull((select (case when status=0 then '<span class="text-red">系统吃分</span>' else '<span class="text-green">系统吐分</span>' end) from QPTreasureDB.dbo.L_BloodPoolConfig where ServerID=g.ServerID),'--') State
		,(select highLine from QPTreasureDB.dbo.L_BloodPoolConfig where ServerID=g.ServerID) highLine
		,isnull((SELECT top 1 Convert(decimal(18,4),(convert(float,isnull(c.OutScore, 0))/convert(float,(case when (c.EatScore=0 or c.EatScore is null) then 1 else c.EatScore end))))
			FROM QPServerInfoDB.dbo.AA_Log_GameRoomScore AS c 
			WHERE c.serverID = g.serverID AND DATEDIFF(day, LogDate, GETDATE())=0),0) AS OutCheatRate
			,(case when (DATEDIFF(DAY,g.LastUpdateTime,GETDATE())>0) then 0 else g.todayCheatBlood end) todayCheatBlood 
         ,(case when (DATEDIFF(DAY,g.LastUpdateTime,GETDATE())>0) then 0 else g.todayTaxScore end) todayTaxScore 
         ,(case when (DATEDIFF(DAY,g.LastUpdateTime,GETDATE())>0) then 0 else g.TodayScore end) TodayScore 
	,ISNULL(gsl.OnlineNum, 0) OnlineNum
			from [QPServerInfoDB].[dbo].[GameRoomItem] g
	LEFT JOIN (SELECT ServerID,COUNT(1) as OnlineNum FROM QPTreasureDB.[dbo].[GameScoreLocker] where ServerID<>0 GROUP BY ServerID) as gsl
	on g.ServerID=gsl.ServerID
	where 1=1
	  @if(!isEmpty(ServerID)){
	  	and g.ServerID = #{ServerID}
	  @}
	  @if(!isEmpty(KindID)){
	  	and g.KindID = #{KindID}
	  @}
	  @if(!isEmpty(ServerName)){
	  	and g.RoomName like '%'+#{ServerName}+'%'
	  @}
	   @if(!isEmpty(Waste)){
	  	@if(Waste=='1'){
	  		and g.RealScore>0
	  	@} else {
	  		and g.RealScore<0
	  	@}
	  @}

new_history_list
===
	 SELECT g.*,CONVERT(VARCHAR(100), LogDate, 23) CollectTime ,
	(SELECT RoomName FROM QPServerInfoDB.dbo.GameRoomItem WHERE serverid = g.ServerID) AS ServerName,
	 Round(convert(float,isnull(OutScore, 0))/convert(float,(case when EatScore=0 then 1 when EatScore is null then 1 else EatScore end)),4) AS Rate
	FROM QPServerInfoDB.dbo.AA_Log_GameRoomScore g
	where 1=1
	  @if(!isEmpty(ServerID)){
	  	and ServerID = #{ServerID}
	  @}
	  @if(!isEmpty(startTime_gt)){
	  	and DATEDIFF(DAY,g.LogDate,#{startTime_gt})<=0
	  @}
	  @if(!isEmpty(endTime_lt)){
		 and DATEDIFF(DAY,g.LogDate,#{endTime_lt})>=0
	  @}

all_list
===
	SELECT ServerID,KindID,RoomName as ServerName
	  FROM [QPServerInfoDB].[dbo].[GameRoomItem]
	  @if(!isEmpty(KindID)){
	  	where KindID = #{KindID}
	  @}
	  
new_control_list
===
	  SELECT b.*,
	 (SELECT RoomName FROM QPServerInfoDB.dbo.GameRoomItem WHERE serverid = b.ServerID) AS ServerName
	FROM QPServerInfoDB.dbo.AA_BloodChangePlan b
	where 1=1 
	  @if(!isEmpty(ServerID)){
	  	and b.ServerID = #{ServerID}
	  @}
	  @if(!isEmpty(startTime_gt)){
	  	and DATEDIFF(DAY,b.RunTime,#{startTime_gt})<=0
	  @}
	  @if(!isEmpty(endTime_lt)){
		 and DATEDIFF(DAY,b.RunTime,#{endTime_lt})>=0
	  @}