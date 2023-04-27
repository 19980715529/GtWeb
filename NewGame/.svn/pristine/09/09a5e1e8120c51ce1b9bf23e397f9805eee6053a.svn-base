new_login_log
===
	  select USER_ID UserID,LoginGold Score,LogTime EnterTime,IP ClientIP,l.MachineNo MachineSerial,a.GameID,a.BeautifulID,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
	  ,(case when l.ServerId=0 then '大厅' else (select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] where ServerID=l.ServerId) end) ServerName
	  FROM [QPGameUserDB].[dbo].[AA_ZZ_Log_Login] l,
	  [QPGameUserDB].[dbo].[AccountsInfo] a
	  where l.User_Id=a.UserID
	@if(!isEmpty(UserID)){
	 and l.USER_ID =#{UserID}
	@}
	@if(!isEmpty(GameID)){
	 and a.GameID =#{GameID}
	@}
	@if(!isEmpty(EnterTime_dategt)){
		and DATEDIFF(DAY,l.LogTime,#{EnterTime_dategt})<=0
	@}
	@if(!isEmpty(EnterTime_datelt)){
		and DATEDIFF(DAY,l.LogTime,#{EnterTime_datelt})>=0
	@}
	@if(!isEmpty(EnterMachine)){
	 and l.MachineNo like '%'+#{EnterMachine}+'%'
	@}

new_login_ip_log
===
	select IP as EnterClientIP,COUNT(l.User_Id) as UserNum 
	FROM [QPGameUserDB].[dbo].[AA_ZZ_Log_Login] l,
	[QPGameUserDB].[dbo].[AccountsInfo] a
	where l.User_Id=a.UserID
	@if(!isEmpty(UserID)){
	 and l.USER_ID =#{UserID}
	@}
	@if(!isEmpty(GameID)){
	 and a.GameID =#{GameID}
	@}
	@if(!isEmpty(EnterTime_dategt)){
		and DATEDIFF(DAY,l.LogTime,#{EnterTime_dategt})<=0
	@}
	@if(!isEmpty(EnterTime_datelt)){
		and DATEDIFF(DAY,l.LogTime,#{EnterTime_datelt})>=0
	@}
	@if(!isEmpty(EnterMachine)){
	 and l.MachineNo like '%'+#{EnterMachine}+'%'
	@}
	group by l.IP