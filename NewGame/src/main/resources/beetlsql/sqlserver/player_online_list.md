online_total
===
	select count(UserID) UserCount,isnull(sum(RechargeAmount),0) RechargeAmount,isnull(sum(UserMedal),0) UserMedal,isnull(sum(TScore),0) TScore,isnull(sum(RealScore),0) RealScore from (SELECT b.ServerID,
	(SELECT g.RoomName FROM [QPServerInfoDB].[dbo].[GameRoomItem] as g WHERE b.ServerID=g.ServerID)AS ServerName,
	a.userID as UserID,a.ClientType, a.Businessman
	,(SELECT c.Amount FROM QPGameUserDB.dbo.AA_Shop_Prop_UserProp as c WHERE b.userID = c.user_id and c.Prop_Id = 1) AS TScore
	,(SELECT c.Amount FROM QPGameUserDB.dbo.AA_Shop_Prop_UserProp as c WHERE b.userID = c.user_id and c.Prop_Id = 2) AS UserMedal,
	a.RegisterDate, c.RealScore,
	c.CheatRate,
	isnull((SELECT d.name FROM QPGameUserDB.dbo.AccountTypeName as d where a.clientType = d.clientType),(case when a.ClientType=0 then '大厅' else '包'+cast(a.ClientType as varchar) end)) as PackageName,
	(select SUM(UnitRMB) as GetGold from [QPGameUserDB].[dbo].[AA_Recharge_TotalRecord] r where r.User_Id=a.UserID) as RechargeAmount
	FROM QPGameUserDB.dbo.AccountsInfo AS a,
	QPTreasureDB.dbo.GameScoreLocker as b,
	QPTreasureDB.dbo.GameScoreInfo as c
	WHERE a.userid = b.userid and b.userid = c.userid and a.isRobit=0
	) t
	where 1=1
	  @if(!isEmpty(ServerName)){
	  	@if(ServerName=='大厅'){
		 	and t.ServerID=0
	  	@} else {
		   and t.ServerName like '%'+#{ServerName}+'%'
	  	@}
	  @}
	  @if(!isEmpty(showVip)){
	  	@if(showVip=='false'){
		   and (t.Businessman=0 or t.Businessman is null)
	  	@}
	  @} else {
		   and (t.Businessman=0 or t.Businessman is null)
	  	@}
	  @if(!isEmpty(ServerID)){
		 and t.ServerID =#{ServerID}
	  @}
	  @if(!isEmpty(PackageName)){
		 and t.clientType =#{PackageName}
	  @}
	  @if(!isEmpty(RegisterDate_dategt)){
	  	and DATEDIFF(day, #{RegisterDate_dategt}, t.RegisterDate)>=0
	  @}
	  @if(!isEmpty(RegisterDate_datelte)){
	  	and DATEDIFF(day, #{RegisterDate_datelte}, t.RegisterDate)<=0
	  @}
	  @if(!isEmpty(IsControll)){
	  	@if(IsControll=='1'){
		 	and t.CheatRate<>0
	  	@} else {
	  		and t.CheatRate=0
	  	@}
	  @}


new_list
===
	select t.* from (SELECT b.ServerID,
	(SELECT g.RoomName FROM [QPServerInfoDB].[dbo].[GameRoomItem] as g WHERE b.ServerID=g.ServerID)AS ServerName,
	a.userID as UserID, a.GameID, a.vipLevel, a.NickName, a.tipsName,a.clientType,a.BeautifulID
	,(case when (Businessman=0 or Businessman is null) then (case when vipLevel=0 then '普通用户' else '特权用户:VIP'+cast(vipLevel as varchar) end) else '至尊VIP' end) as TypeName
      ,(case when isInnerMember=1 then '(内部员工)' else '' end) isInnerMemberName
	,(SELECT c.Amount FROM QPGameUserDB.dbo.AA_Shop_Prop_UserProp as c WHERE b.userID = c.user_id and c.Prop_Id = 1) AS TScore
	,(SELECT c.Amount FROM QPGameUserDB.dbo.AA_Shop_Prop_UserProp as c WHERE b.userID = c.user_id and c.Prop_Id = 2) AS UserMedal,
	a.RegisterDate, a.LastLoginTime, c.RealScore,d.todayScore,
	(select isnull(sum(r.topUpAmount),0) from RYPlatformManagerDB.dbo.Recharge_records as r where r.userId=a.userid and orderStatus=2) AS totalRecharge, 
    c.out_BussniessCount as SendScore,
	(c.out_BussniessCount-c.js_BussniessCount) as RS,
	c.CheatRate, c.LimitScore, a.Businessman,
	(case when (DATEDIFF(day,'1900-01-01',c.lastEndCheatTime)=0) then '无' else CONVERT(VARCHAR(100), c.lastEndCheatTime, 120) end) lastEndCheatTime,
	isnull((SELECT d.name FROM QPGameUserDB.dbo.AccountTypeName as d where a.clientType = d.clientType),(case when a.ClientType=0 then '大厅' else '包'+cast(a.ClientType as varchar) end)) as PackageName,
	(select isnull(SUM(amount),0) as GetGold from RYPlatformManagerDB.dbo.Exchange_review r where r.userId=a.UserID and r.status in (3,4)) as ExchangeAmount,
	(SELECT insurescore FROM QPTreasureDB.dbo.GameScoreInfo where UserID = a.UserID) as InsureScore, d.TotalScore as TotalWin
	FROM QPGameUserDB.dbo.AccountsInfo AS a,
	QPTreasureDB.dbo.GameScoreLocker as b,
	QPTreasureDB.dbo.GameScoreInfo as c,
    QPGameUserDB.dbo.PlayerSocreInfo as d
	WHERE a.userid = b.userid and b.userid = c.userid and d.Userid=a.userid and a.isRobit=0 
	) t
	where 1=1
	  @if(!isEmpty(ServerName)){
	  	@if(ServerName=='大厅'){
		 	and t.ServerID=0
	  	@} else {
		   and t.ServerName like '%'+#{ServerName}+'%'
	  	@}
	  @}
	  @if(!isEmpty(showVip)){
	  	@if(showVip=='false'){
		   and (t.Businessman=0 or t.Businessman is null)
	  	@}
	  @} else {
		   and (t.Businessman=0 or t.Businessman is null)
	  	@}
	  @if(!isEmpty(ServerID)){
		 and t.ServerID =#{ServerID}
	  @}
	  @if(!isEmpty(PackageName)){
		 and t.clientType =#{PackageName}
	  @}
	  @if(!isEmpty(RegisterDate_dategt)){
	  	and DATEDIFF(day, #{RegisterDate_dategt}, t.RegisterDate)>=0
	  @}
	  @if(!isEmpty(RegisterDate_datelte)){
	  	and DATEDIFF(day, #{RegisterDate_datelte}, t.RegisterDate)<=0
	  @}
	  @if(!isEmpty(IsControll)){
	  	@if(IsControll=='1'){
		 	and t.CheatRate<>0
	  	@} else {
	  		and t.CheatRate=0
	  	@}
	  @}
	  
online_list
===
	select t.*
	,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName 
	from (
	select r.UserID,COUNT(1) EnterNum,Convert(decimal(10,3),cast(SUM(OnLineTimeCount) AS float)/60) OnlineNum,CONVERT(VARCHAR(100), LeaveTime, 23) LeaveTime 
	from QPGameRecordDB.dbo.RecordUserLeave r where 1=1
	@if(!isEmpty(ServerID)){
		and r.ServerID =#{ServerID}
	@}
	@if(!isEmpty(ServerName)){
		and r.ServerID in (select ServerID from QPServerInfoDB.dbo.GameRoomItem where RoomName like '%'+#{ServerName}+'%')
	@}
	@if(!isEmpty(KindID)){
		and r.KindID =#{KindID}
	@}
	@if(!isEmpty(RegisterDate_dategt)){
	  	and DATEDIFF(day,r.LeaveTime,#{RegisterDate_dategt})<=0
	@}
	@if(!isEmpty(RegisterDate_datelte)){
	  	and DATEDIFF(day,r.LeaveTime,#{RegisterDate_datelte})>=0
	@}
	@if(!isEmpty(UserID)){
		and r.UserID =#{UserID}
	@}
	group by r.UserID,CONVERT(VARCHAR(100), LeaveTime, 23) 
	) t
	left join
	QPGameUserDB.dbo.AccountsInfo a
	on t.UserID=a.UserID