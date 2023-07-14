new_list(Has js_bankScoreCount)
===
	select * from (
	SELECT a.userID as UserID, a.GameID, a.Accounts, a.tipsName,a.LastLoginMachine,
	(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName,
	a.NickName as FirstName,a.LastLogonIP,
	(SELECT c.Amount FROM QPGameUserDB.dbo.AA_Shop_Prop_UserProp as c with (nolock) WHERE a.userID = c.user_id and c.Prop_Id = 1) AS Score,
	(SELECT c.Amount FROM QPGameUserDB.dbo.AA_Shop_Prop_UserProp as c with (nolock) WHERE a.userID = c.user_id and c.Prop_Id = 2) AS UserMedal,
	a.RegisterDate,a.LastLogonDate,a.BindPhone,
	isnull((select SUM(UnitRMB) FROM QPGameUserDB.dbo.AA_Recharge_TotalRecord with (nolock) WHERE User_Id = a.userID),0) AS RechargeNum,
	(select count(*) FROM QPGameUserDB.dbo.AA_Recharge_TotalRecord with (nolock) WHERE User_Id = a.userID) AS RechargeCountNum,
	isnull(b.RealScore,0) as TotalWaste,b.InsureScore,
	(case when (DATEDIFF(DAY,b.changeScoreTime,GETDATE())>0) then 0 else isnull(b.todayScore,0) end) as DayWaste,
	(isnull(b.js_BussniessCount,0)+isnull(b.js_bankScoreCount,0)) as RcvScore,
	(isnull(b.out_BussniessCount,0)+isnull(b.out_bankScoreCount,0)) as SendScore,
	(isnull(b.js_BussniessCount,0)+isnull(b.js_bankScoreCount,0)-isnull(b.out_BussniessCount,0)-isnull(b.out_bankScoreCount,0)) as RS,
	isnull((select (case when ServerID=0 then '大厅' else (select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] where ServerID=l.ServerID) end) FROM [QPTreasureDB].[dbo].[GameScoreLocker] l with (nolock) where l.UserID=a.UserID),'离线') as OnlineServerName,
	(case when a.FirstServerId=0 then '无' else (select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] where ServerID=a.FirstServerId) end) as FirstEnterKindName
	,(case when (Businessman=0 or Businessman is null) then (case when vipLevel=0 then '普通用户' else '特权用户:VIP'+cast(vipLevel as varchar) end) else '至尊VIP' end) as TypeName
      ,(case when isInnerMember=1 then '(内部员工)' else '' end) isInnerMemberName
	,(case when a.LimitLogin=0 then '正常' else '锁定' end) as NullityName,
	a.sendNum, a.clientType,isnull(b.CheatRate,0) CheatRate,
	(case when (DATEDIFF(day,'1900-01-01',b.lastEndCheatTime)=0) then '无' else CONVERT(VARCHAR(100), b.lastEndCheatTime, 120) end) lastEndCheatTime
	FROM QPGameUserDB.dbo.AccountsInfo AS a with (nolock)
	left join
	(select * from [QPTreasureDB].[dbo].[GameScoreInfo] with (nolock)) as  b
	on a.UserID=b.UserID where a.isRobit=0
	@if(!isEmpty(UserID)){
	 and a.UserID =#{UserID}
	@}
	@if(!isEmpty(BindCode)){
	 and a.UserID in (select [userid] from [QPGameUserDB].[dbo].[InviteCode] where [Code]=#{BindCode})
	@}
	  @if(!isEmpty(GameID)){
	 and a.GameID =#{GameID}
	@}
	  @if(!isEmpty(Accounts)){
	 and a.Accounts =#{Accounts}
	@}
	  @if(!isEmpty(SecretMobilePhone)){
	 and a.Accounts =#{SecretMobilePhone}
	@}
	  @if(!isEmpty(PlatformID)){
	 and a.clientType =#{PlatformID}
	@}
	@if(!isEmpty(sendNum)){
	 and a.sendNum =#{sendNum}
	@}
	@if(!isEmpty(NickName)){
	 and a.NickName like '%'+#{NickName}+'%'
	@}
	@if(!isEmpty(NickName)){
	 and a.NickName like '%'+#{NickName}+'%'
	@}
	@if(!isEmpty(isBindMobile)){
		@if(isBindMobile=='1'){
	 		and a.Accounts like '1[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'
		@} else {
			and a.Accounts not like '1[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'
		@}
	@}
	@if(!isEmpty(tipsNames)){
	 and a.tipsName=#{tipsNames}
	@}
	@if(!isEmpty(KindID)){
		and a.FirstKindId=#{KindID}
	@}
	@if(!isEmpty(LastLogonMachine)){
		and LOWER(a.LastLoginMachine) like '%'+LOWER(#{LastLogonMachine})+'%'
	@}
	@if(!isEmpty(Nullity)){
		@if(Nullity=='0'){
			and a.LimitLogin=#{Nullity}
		@} else if(Nullity=='1') {
			and a.LimitLogin=#{Nullity}
		@} else if(Nullity=='3') {
			and a.limitRank=0
		@} else if(Nullity=='2') {
			and a.limitSend=1
		@}
	@}
	@if(!isEmpty(ip)){
	 and (a.LastLogonIP =#{ip} or a.RegisterIP =#{ip})
	@}
	@if(!isEmpty(StartLoginTime)){
		 and CONVERT(VARCHAR(100), a.LastLogonDate, 20) >= CONVERT(VARCHAR(100), #{StartLoginTime}, 20)
	  @}
	  @if(!isEmpty(EndLoginTime)){
		 and CONVERT(VARCHAR(100), a.LastLogonDate, 20) <= CONVERT(VARCHAR(100), #{EndLoginTime}, 20)
	  @}
	@if(!isEmpty(StartLogoutTime)){
		 and CONVERT(VARCHAR(100), a.LastLogoutTime, 20) >= CONVERT(VARCHAR(100), #{StartLogoutTime}, 20)
	  @}
	  @if(!isEmpty(EndLogoutTime)){
		 and CONVERT(VARCHAR(100), a.LastLogoutTime, 20) <= CONVERT(VARCHAR(100), #{EndLogoutTime}, 20)
	  @}
	@if(!isEmpty(StartRegistTime)){
		 and CONVERT(VARCHAR(100), a.RegisterDate, 20) >= CONVERT(VARCHAR(100), #{StartRegistTime}, 20)
	  @}
	  @if(!isEmpty(EndRegistTime)){
		 and CONVERT(VARCHAR(100), a.RegisterDate, 20) <= CONVERT(VARCHAR(100), #{EndRegistTime}, 20)
	  @}
	@if(!isEmpty(RegisterMobile)){
	 and a.bindPhone like '%'+#{RegisterMobile}+'%'
	@}
	@if(!isEmpty(MemberTypeID)){
	 	@if(MemberTypeID=='-1'){
			and a.vipLevel>0 and a.vipLevel<=6
		@} else if(MemberTypeID=='-2'){
			and a.Businessman=1
		@} else {
			and a.vipLevel=#{MemberTypeID}
		@}
	@}
	) t
	where 1=1
	@if(!isEmpty(CoinStart)){
	 and Score >= #{CoinStart}
	 @}
	  @if(!isEmpty(CoinEnd)){
	 and Score <= #{CoinEnd}
	 @}
	  @if(!isEmpty(CouponStart)){
	 and UserMedal >= #{CouponStart}
	 @}
	  @if(!isEmpty(CouponEnd)){
	 and UserMedal <= #{CouponEnd}
	 @}
	  @if(!isEmpty(RechargeStart)){
	    and RechargeNum >= #{RechargeStart}
	 @}
	  @if(!isEmpty(RechargeEnd)){
	 and RechargeNum <= #{RechargeEnd}
	 @}
	  @if(!isEmpty(RSStart)){
	 and RS >= #{RSStart}
	 @}
	  @if(!isEmpty(RSEnd)){
	 and RS <= #{RSEnd}
	 @}
	  @if(!isEmpty(RcvScoreStart)){
	 and RcvScore >= #{RcvScoreStart}
	 @}
	  @if(!isEmpty(RcvScoreEnd)){
	 and RcvScore <= #{RcvScoreEnd}
	 @}
	  @if(!isEmpty(SendScoreStart)){
	 and SendScore >= #{SendScoreStart}
	 @}
	  @if(!isEmpty(SendScoreEnd)){
	 and SendScore <= #{SendScoreEnd}
	 @}
	  @if(!isEmpty(WinStart)){
	 and TotalWaste >= #{WinStart}
	 @}
	  @if(!isEmpty(WinEnd)){
	 and TotalWaste <= #{WinEnd}
	 @}

new_detail(Has js_bankScoreCount)
===
	SELECT a.userID as UserID, a.GameID, a.RegisterIP,a.LogonPass,a.InsurePass, a.LastLogonIP,a.vipLevel,a.Businessman,a.isInnerMember,
	(case when a.Businessman=1 then '至尊VIP' else '普通用户' end) BusinessmanName,
	a.TotalOnlineTime, a.NickName, a.Accounts, a.tipsName,
	(SELECT c.Amount FROM QPGameUserDB.dbo.AA_Shop_Prop_UserProp as c with (nolock) WHERE c.user_id=a.UserID and c.Prop_Id = 1) AS Score,
	a.RegisterDate, a.LastLogonDate,SUBSTRING(a.LastLoginMachine,1,32) as LastLoginMachine,
	(select SUM(buymoney) FROM [QPGameUserDB].[dbo].[UserByScoreByGM] with (nolock) where userid=a.UserID) as GMRechargeNum,
	(select SUM(UnitRMB) FROM QPGameUserDB.dbo.AA_Recharge_TotalRecord with (nolock) where User_Id=a.UserID) as APPRechargeNum,
	(SELECT  COUNT(1) FROM QPGameUserDB.dbo.AA_Recharge_TotalRecord with (nolock) WHERE User_Id = a.UserID) as RechargeCountNum,
	b.RealScore as TotalWaste,
	(case when (DATEDIFF(DAY,b.changeScoreTime,GETDATE())>0) then 0 else b.todayScore end) as DayWaste
	, isnull(b.js_BussniessCount,0) as js_BussniessCount, isnull(b.out_BussniessCount,0) as out_BussniessCount,
	isnull(b.js_bankScoreCount,0) as js_bankScoreCount,
	isnull(b.out_bankScoreCount,0) as out_bankScoreCount,
	b.CheatRate, b.LimitScore,b.CheatRate2, b.LimitScore2, b.BloodScore,a.limitSend as limitGive,
	(case when (DATEDIFF(day,'1900-01-01',b.lastEndCheatTime)=0) then '无' else CONVERT(VARCHAR(100), b.lastEndCheatTime, 120) end) lastEndCheatTime
	, a.LimitLogin, '后面实现' AS '是否禁止交易',
	a.sendNum, a.limitRank,a.bindPhone,
	(case when a.vipLevel=0 then '普通用户' else '特权用户' end) as TypeName,
	(case when a.FirstServerId=0 then '无' else (select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] where ServerID=a.FirstServerId) end) as FirstEnterServerName,
	isnull((select (case when ServerID=0 then '大厅' else (select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] where ServerID=l.ServerID) end) FROM [QPTreasureDB].[dbo].[GameScoreLocker] l with (nolock) where l.UserID=a.UserID),'离线') as OnlineServerName,
		(select Amount FROM [QPGameUserDB].[dbo].[AA_Shop_Prop_UserProp] pu with (nolock) where pu.User_Id=a.UserID and pu.Prop_Id=2) as UserMedal,
	(select SUM(Amount) FROM [QPGameUserDB].[dbo].[AA_ZZ_Log_PropChange] with (nolock)
	where Prop_Id=1 and ChangeType_Id=1 and DATEDIFF(DAY,LogTime,DATEADD(DAY,-3,GETDATE()))<0 and 	DATEDIFF(DAY,LogTime,GETDATE())>=0 and User_Id=a.UserID) Day3Waste,
	(select SUM(Amount) FROM [QPGameUserDB].[dbo].[AA_ZZ_Log_PropChange] with (nolock)
	where Prop_Id=1 and ChangeType_Id=1 and DATEDIFF(DAY,LogTime,DATEADD(DAY,-3,GETDATE()))<=0 and 	DATEDIFF(DAY,LogTime,GETDATE())>0 and User_Id=a.UserID) Day33Waste
	,isnull((select SUM(prop_count) FROM [QPGameUserDB].[dbo].[AA_GiveRecord] with (nolock) where to_user=a.UserID and [open]=0),0) UnRcvGold
	,isnull((SELECT name FROM QPGameUserDB.dbo.AccountTypeName where clientType=a.ClientType),(case when a.ClientType=0 then '大厅' else '包'+cast(a.ClientType as varchar) end)) as AccountTypeName
	,isnull((select top 1 [Body] FROM [QPGameUserDB].[dbo].[AccountsTipNameDesc] d where d.UserID=a.UserID),'') TipDesc
	,b.InsureScore
	,(select SUM(Amount) from (SELECT a2.Amount from [QPGameUserDB].[dbo].[AA_ExchangeCode_UserExchange] a1
	LEFT JOIN [QPGameUserDB].[dbo].[AA_ExchangeCode_Prop] a2
	on a1.User_Id is not null where a1.User_Id=a.UserID AND a2.Prop_Id=1) t) ExchangeScore
	,(case Platform_Id when 0 then '苹果' when 1 then '安卓' else 'PC' end)  PlatformName,a.IsDrain,a.IsDrain,
	FROM QPGameUserDB.dbo.AccountsInfo AS a with (nolock),
	QPTreasureDB.dbo.GameScoreInfo as b with (nolock)
	WHERE a.UserID = b.userid
	@if(!isEmpty(UserID)){
	 and a.UserID =#{UserID}
	@}
new_list
===
	select * from (
	SELECT a.userID as UserID, a.GameID, a.Accounts, a.tipsName,a.LastLoginMachine,
	(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName,
	a.NickName as FirstName,
	(SELECT c.Amount FROM QPGameUserDB.dbo.AA_Shop_Prop_UserProp as c with (nolock) WHERE a.userID = c.user_id and c.Prop_Id = 1) AS Score,
	(SELECT c.Amount FROM QPGameUserDB.dbo.AA_Shop_Prop_UserProp as c with (nolock) WHERE a.userID = c.user_id and c.Prop_Id = 2) AS UserMedal,
	a.RegisterDate,a.LastLoginTime,a.LastLogoutTime,
    (SELECT TotalRecharge FROM QPGameUserDB.dbo.PlayerSocreInfo as c with (nolock) WHERE a.userID = c.Userid) AS TotalRecharge,
    (SELECT count(*) FROM QPGameUserDB.dbo.PlayerRechargeRecord as c with (nolock) WHERE a.userID = c.UserID and c.Type=0) AS RechargeCount,
    (SELECT TotalWithDraw FROM QPGameUserDB.dbo.PlayerSocreInfo as c with (nolock) WHERE a.userID = c.Userid) AS TotalExchange,
    (SELECT count(*) FROM QPGameUserDB.dbo.PlayerRechargeRecord as c with (nolock) WHERE a.userID = c.UserID and c.Type=1) AS ExchangeCount,
    (SELECT TotalRecharge-TotalWithDraw FROM QPGameUserDB.dbo.PlayerSocreInfo as c with (nolock) WHERE a.userID = c.Userid) as RE,
    (SELECT TotalScore FROM QPGameUserDB.dbo.PlayerSocreInfo as c with (nolock) WHERE a.userID = c.Userid) as TotalScore,
	b.RealScore as TotalWaste,
	(case when (DATEDIFF(DAY,b.changeScoreTime,GETDATE())>0) then 0 else b.todayScore end) as DayWaste,
	b.js_BussniessCount as RcvScore,
	b.out_BussniessCount as SendScore,
	(b.js_BussniessCount-b.out_BussniessCount) as RS,
	isnull((select (case when ServerID=0 then '大厅' else (select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] where ServerID=l.ServerID) end) FROM [QPTreasureDB].[dbo].[GameScoreLocker] l with (nolock) where l.UserID=a.UserID),'离线') as OnlineServerName,
	(case when a.FirstServerId=0 then '无' else (select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] where ServerID=a.FirstServerId) end) as FirstEnterKindName
	,(case when (Businessman=0 or Businessman is null) then (case when vipLevel=0 then '普通用户' else '特权用户:VIP'+cast(vipLevel as varchar) end) else '至尊VIP' end) as TypeName
      ,(case when isInnerMember=1 then '(内部员工)' else '' end) isInnerMemberName
	,(case when isnull(a.LimitLogin,0) = 0 then '正常' else '锁定' end) as NullityName,
	a.sendNum, a.clientType,b.CheatRate,b.InsureScore,
	(case when (DATEDIFF(day,'1900-01-01',b.lastEndCheatTime)=0) then '无' else CONVERT(VARCHAR(100), b.lastEndCheatTime, 120) end) lastEndCheatTime
	FROM QPGameUserDB.dbo.AccountsInfo AS a with (nolock),
	[QPTreasureDB].[dbo].[GameScoreInfo] b with (nolock)
	WHERE a.UserID=b.UserID and a.isRobit=0
	@if(!isEmpty(UserID)){
	 and a.UserID =#{UserID}
	@}
	@if(!isEmpty(GameID)){
	 and a.GameID =#{GameID}
	@}
	@if(!isEmpty(BeautifulID)){
	 and a.BeautifulID =#{BeautifulID}
	@}
	  @if(!isEmpty(Accounts)){
	 and a.bindPhone = #{Accounts}
	@}
	  @if(!isEmpty(SecretMobilePhone)){
	 and a.Accounts =#{SecretMobilePhone}
	@}
	  @if(!isEmpty(PlatformID)){
	 and a.clientType =#{PlatformID}
	@}
	@if(!isEmpty(sendNum)){
	 and a.sendNum =#{sendNum}
	@}
	@if(!isEmpty(NickName)){
	 and a.NickName like '%'+#{NickName}+'%'
	@}
	@if(!isEmpty(tipsName)){
	 and a.tipsName like '%'+#{tipsName}+'%'
	@}
	@if(!isEmpty(tipsNames)){
	 and a.tipsName=#{tipsNames}
	@}
	@if(!isEmpty(KindID)){
		and a.FirstKindId=#{KindID}
	@}
	@if(!isEmpty(LastLogonMachine)){
		and LOWER(a.LastLoginMachine) like '%'+LOWER(#{LastLogonMachine})+'%'
	@}
	@if(!isEmpty(Nullity)){
		@if(Nullity=='0'){
			and a.LimitLogin=#{Nullity}
		@} else if(Nullity=='1') {
			and a.LimitLogin=#{Nullity}
		@} else if(Nullity=='3') {
			and a.limitRank=0
		@} else if(Nullity=='2') {
			and a.limitSend=1
		@}
	@}
	@if(!isEmpty(ip)){
	 and (a.LastLogonIP =#{ip} or a.RegisterIP =#{ip})
	@}
	@if(!isEmpty(StartLoginTime)){
		 and CONVERT(VARCHAR(100), a.LastLogonDate, 20) >= CONVERT(VARCHAR(100), #{StartLoginTime}, 20)
	  @}
	  @if(!isEmpty(EndLoginTime)){
		 and CONVERT(VARCHAR(100), a.LastLogonDate, 20) <= CONVERT(VARCHAR(100), #{EndLoginTime}, 20)
	  @}
	@if(!isEmpty(StartLogoutTime)){
		 and CONVERT(VARCHAR(100), a.LastLogoutTime, 20) >= CONVERT(VARCHAR(100), #{StartLogoutTime}, 20)
	  @}
	  @if(!isEmpty(EndLogoutTime)){
		 and CONVERT(VARCHAR(100), a.LastLogoutTime, 20) <= CONVERT(VARCHAR(100), #{EndLogoutTime}, 20)
	  @}
	@if(!isEmpty(StartRegistTime)){
		 and CONVERT(VARCHAR(100), a.RegisterDate, 20) >= CONVERT(VARCHAR(100), #{StartRegistTime}, 20)
	  @}
	  @if(!isEmpty(EndRegistTime)){
		 and CONVERT(VARCHAR(100), a.RegisterDate, 20) <= CONVERT(VARCHAR(100), #{EndRegistTime}, 20)
	  @}
	@if(!isEmpty(RegisterMobile)){
	 and a.bindPhone like '%'+#{RegisterMobile}+'%'
	@}
	@if(!isEmpty(MemberTypeID)){
	 	@if(MemberTypeID=='-1'){
			and a.vipLevel>0 and a.vipLevel<=6
		@} else if(MemberTypeID=='-2'){
			and a.Businessman=1
		@} else if(MemberTypeID=='0'){
			and (a.Businessman=0 or a.Businessman is null)
		@} else {
			and a.vipLevel=#{MemberTypeID}
		@}
	@}
	) t
	where 1=1
	@if(!isEmpty(CoinStart)){
	 and Score >= #{CoinStart}
	 @}
	  @if(!isEmpty(CoinEnd)){
	 and Score <= #{CoinEnd}
	 @}
	@if(!isEmpty(BankCoinStart)){
	 and InsureScore >= #{BankCoinStart}
	 @}
	  @if(!isEmpty(BankCoinEnd)){
	 and InsureScore <= #{BankCoinEnd}
	 @}
	  @if(!isEmpty(CouponStart)){
	 and UserMedal >= #{CouponStart}
	 @}
	  @if(!isEmpty(CouponEnd)){
	 and UserMedal <= #{CouponEnd}
	 @}
	  @if(!isEmpty(RechargeStart)){
	 and TotalRecharge >= #{RechargeStart}
	 @}
	  @if(!isEmpty(RechargeEnd)){
	 and TotalRecharge <= #{RechargeEnd}
	 @}
	  @if(!isEmpty(RSStart)){
	 and RS >= #{RSStart}
	 @}
	  @if(!isEmpty(RSEnd)){
	 and RS <= #{RSEnd}
	 @}
	  @if(!isEmpty(RcvScoreStart)){
	 and RcvScore >= #{RcvScoreStart}
	 @}
	  @if(!isEmpty(RcvScoreEnd)){
	 and RcvScore <= #{RcvScoreEnd}
	 @}
	  @if(!isEmpty(SendScoreStart)){
	 and SendScore >= #{SendScoreStart}
	 @}
	  @if(!isEmpty(SendScoreEnd)){
	 and SendScore <= #{SendScoreEnd}
	 @}
	  @if(!isEmpty(WinStart)){
	 and TotalWaste >= #{WinStart}
	 @}
	  @if(!isEmpty(WinEnd)){
	 and TotalWaste <= #{WinEnd}
	 @}

new_detail
===
	SELECT a.userID as UserID, a.GameID, a.RegisterIP, a.LastLogonIP,a.vipLevel,a.Businessman,a.isInnerMember,a.IsDrain,a.LastLoginTime,
	(case when a.Businessman=1 then '至尊VIP' else '普通用户' end) BusinessmanName,a.BeautifulID,
	a.TotalOnlineTime, a.NickName, a.Accounts, a.LogonPass, a.tipsName,
	(SELECT c.Amount FROM QPGameUserDB.dbo.AA_Shop_Prop_UserProp as c with (nolock) WHERE c.user_id=a.UserID and c.Prop_Id = 1) AS Score,
	a.RegisterDate, a.LastLogonDate,SUBSTRING(a.LastLoginMachine,1,32) as LastLoginMachine,
	(select SUM(topUpAmount) FROM [RYPlatformManagerDB].[dbo].[Recharge_records] with (nolock) where userId=a.UserID and orderStatus = 2) as GMRechargeMoney,
	(select SUM(topUpAmount) FROM [RYPlatformManagerDB].[dbo].[Recharge_records] with (nolock) where userId=a.UserID and orderStatus =2 and DateDiff(dd,createTime,getdate())=0) as TodayRechargeMoney, 
	(SELECT  COUNT(id) FROM [RYPlatformManagerDB].[dbo].[Recharge_records] with (nolock) WHERE userId = a.UserID and orderStatus = 2) as RechargeCountNum,
	(SELECT  isnull(SUM(amount),0)  FROM [RYPlatformManagerDB].[dbo].[Exchange_review] with (nolock) WHERE userId = a.UserID and (status =3 or status=4)) as ExchangeMoney,
	(SELECT  isnull(SUM(amount),0)  FROM [RYPlatformManagerDB].[dbo].[Exchange_review] with (nolock) WHERE status in (3,4)) as ExchangeTotalMoney,
	(SELECT  COUNT(id)  FROM [RYPlatformManagerDB].[dbo].[Exchange_review] with (nolock) WHERE userId = a.UserID and status in (3,4)) as ExchangeCountNum,
	(SELECT  COUNT(id)  FROM [RYPlatformManagerDB].[dbo].[Exchange_review] with (nolock) WHERE userId = a.UserID and status in (1,2,8)) as ExchangeAuditNum,
	(SELECT  TotalScore FROM [QPGameUserDB].[dbo].[PlayerSocreInfo] with (nolock) WHERE Userid = a.UserID) as TotalWaste,
    (SELECT  TotalWin FROM [QPGameUserDB].[dbo].[PlayerSocreInfo] with (nolock) WHERE Userid = a.UserID) as TotalWin,
	(SELECT  isnull(SUM(Daya),0) FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_TurntableClaimHistory] with (nolock) WHERE Userid = a.UserID and ClaimType =1) as RotaryReward,
	(SELECT  isnull(sum(Data),0) FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_CodeRebateHistory] with (nolock) WHERE UserId = a.UserID and DataType =1) as CodeReward,
	(case when (DATEDIFF(DAY,b.changeScoreTime,GETDATE())>0) then 0 else b.todayScore end) as DayWaste
	, b.js_BussniessCount, b.out_BussniessCount,
	b.CheatRate, b.LimitScore,b.CheatRate2, b.LimitScore2, b.BloodScore,isnull(a.limitSend,0) limitGive,b.InsureScore,
	(case when (DATEDIFF(day,'1900-01-01',b.lastEndCheatTime)=0) then '无' else CONVERT(VARCHAR(100), b.lastEndCheatTime, 120) end) lastEndCheatTime
	, a.LimitLogin, '后面实现' AS '是否禁止交易',
	a.sendNum, a.limitRank,a.bindPhone,
	(case when a.vipLevel=0 then '普通用户' else '特权用户' end) as TypeName,
	(case when a.FirstServerId=0 then '无' else (select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] where ServerID=a.FirstServerId) end) as FirstEnterServerName,
	isnull((select (case when ServerID=0 then '大厅' else (select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] where ServerID=l.ServerID) end) FROM [QPTreasureDB].[dbo].[GameScoreLocker] l with (nolock) where l.UserID=a.UserID),'离线') as OnlineServerName,
		(select Amount FROM [QPGameUserDB].[dbo].[AA_Shop_Prop_UserProp] pu with (nolock) where pu.User_Id=a.UserID and pu.Prop_Id=2) as UserMedal,
	(select SUM(Amount) FROM [QPGameUserDB].[dbo].[AA_ZZ_Log_PropChange] with (nolock)
	where Prop_Id=1 and ChangeType_Id=1 and DATEDIFF(DAY,LogTime,DATEADD(DAY,-3,GETDATE()))<0 and 	DATEDIFF(DAY,LogTime,GETDATE())>=0 and User_Id=a.UserID) Day3Waste
	,isnull((select SUM(prop_count) FROM [QPGameUserDB].[dbo].[AA_GiveRecord] with (nolock) where to_user=a.UserID and [open]=0),0) UnRcvGold
	,isnull((SELECT name FROM QPGameUserDB.dbo.AccountTypeName where clientType=a.ClientType),(case when a.ClientType=0 then '大厅' else '包'+cast(a.ClientType as varchar) end)) as AccountTypeName
	,isnull((select top 1 [Body] FROM [QPGameUserDB].[dbo].[AccountsTipNameDesc] d where d.UserID=a.UserID),'') TipDesc
	FROM QPGameUserDB.dbo.AccountsInfo AS a with (nolock),
	QPTreasureDB.dbo.GameScoreInfo as b with (nolock)
	WHERE a.UserID = b.userid
	@if(!isEmpty(UserID)){
	 and a.UserID =#{UserID}
	@}
	
new_findsvip
===
	SELECT * FROM [QPGameUserDB].[dbo].[AccountInfo_SuperVIP] WHERE [UserID] = #{UserID}

new_upsvip
===
	UPDATE [QPGameUserDB].[dbo].[AccountInfo_SuperVIP] SET [SuperVIP] = 10 WHERE [UserID] = #{UserID}

new_list1
===
	select * from (
	SELECT a.userID as UserID, a.GameID, a.Accounts, a.tipsName,a.LastLoginMachine,a.BindPhone,
	(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName,
	a.NickName as FirstName,
	(SELECT c.Amount FROM QPGameUserDB.dbo.AA_Shop_Prop_UserProp as c with (nolock) WHERE a.userID = c.user_id and c.Prop_Id = 1) AS Score,
	a.RegisterDate,d.TotalRecharge,a.LastLoginTime,a.LastLogoutTime,
    (SELECT count(*) FROM QPGameUserDB.dbo.PlayerRechargeRecord as c with (nolock) WHERE a.userID = c.UserID and c.Type=0) AS RechargeCount,
    d.TotalWithDraw AS TotalExchange,
    (SELECT count(*) FROM QPGameUserDB.dbo.PlayerRechargeRecord as c with (nolock) WHERE a.userID = c.UserID and c.Type=1) AS ExchangeCount,
    (d.TotalRecharge-d.TotalWithDraw) as RE,
    (SELECT TotalScore FROM QPGameUserDB.dbo.PlayerSocreInfo as c with (nolock) WHERE a.userID = c.Userid) as TotalScore,
    (SELECT TodayScore FROM QPGameUserDB.dbo.PlayerSocreInfo as c with (nolock) WHERE a.userID = c.Userid) as TodayScore,
	(case when (DATEDIFF(DAY,b.changeScoreTime,GETDATE())>0) then 0 else b.todayScore end) as DayWaste,
	b.js_BussniessCount as RcvScore,
	b.out_BussniessCount as SendScore,
	(b.js_BussniessCount-b.out_BussniessCount) as RS,
    ((select isnull(sum(Data),0) from [QPGameRecordDB].[dbo].[AA_ZZ_Log_CodeRebateHistory] c with (nolock) where c.UserId=a.userID and DataType=1)+
    (select isnull(sum(Daya),0) gold from [QPGameRecordDB].[dbo].[AA_ZZ_Log_TurntableClaimHistory] c with (nolock) where c.Userid=a.userID and UseType=1 and ClaimType=1)+
    (select isnull(sum(Daya),0) gold from [QPGameRecordDB].[dbo].[AA_ZZ_Log_TurntableClaimHistory] c with (nolock) where c.Userid=a.userID and UseType=2 and ClaimType=1)+
    (select isnull(sum(Daya),0) gold from [QPGameRecordDB].[dbo].[AA_ZZ_Log_TurntableClaimHistory] c with (nolock) where c.Userid=a.userID and UseType=3 and ClaimType=1)) as rewardGold,
	isnull((select (case when ServerID=0 then '大厅' else (select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] where ServerID=l.ServerID) end) FROM [QPTreasureDB].[dbo].[GameScoreLocker] l with (nolock) where l.UserID=a.UserID),'离线') as OnlineServerName,
	(case when a.FirstServerId=0 then '无' else (select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] where ServerID=a.FirstServerId) end) as FirstEnterKindName
	,(case when (Businessman=0 or Businessman is null) then (case when vipLevel=0 then '普通用户' else '特权用户:VIP'+cast(vipLevel as varchar) end) else '至尊VIP' end) as TypeName
      ,(case when isInnerMember=1 then '(内部员工)' else '' end) isInnerMemberName
      ,(case when a.IsDrain=1 then '引流' else '非引流' end) isDrain
	,(case when isnull(a.LimitLogin,0) = 0 then '正常' else '锁定' end) as NullityName,a.clientType,b.CheatRate,
	(case when (DATEDIFF(day,'1900-01-01',b.lastEndCheatTime)=0) then '无' else CONVERT(VARCHAR(100), b.lastEndCheatTime, 120) end) lastEndCheatTime
	FROM QPGameUserDB.dbo.AccountsInfo AS a with (nolock),
    [QPTreasureDB].[dbo].[GameScoreInfo] b with (nolock),
    [QPGameUserDB].[dbo].[PlayerSocreInfo] d with (nolock)
	WHERE a.UserID=b.UserID and d.Userid=a.UserID and a.isRobit=0
	@if(!isEmpty(UserID)){
	 and a.UserID =#{UserID}
	@}
    @if(!isEmpty(UserID)){
        and a.UserID =#{UserID}
    @}
    @if(!isEmpty(isDrain)){
	 and a.isDrain =#{isDrain}
	@}
	@if(!isEmpty(GameID)){
	 and a.GameID =#{GameID}
	@}
    @if(!isEmpty(isBindMobile)){
		@if(isBindMobile=='1'){
	 		and a.bindPhone!=''
		@} else {
			and a.bindPhone==''
		@}
	@}
	@if(!isEmpty(BeautifulID)){
	 and a.BeautifulID =#{BeautifulID}
	@}
    @if(!isEmpty(Accounts)){
	    and a.bindPhone = #{Accounts}
	@}
    @if(!isEmpty(PlatformID)){
	    and a.clientType =#{PlatformID}
	@}
	@if(!isEmpty(sendNum)){
	 and a.sendNum =#{sendNum}
	@}
	@if(!isEmpty(NickName)){
	 and a.NickName like '%'+#{NickName}+'%'
	@}
	@if(!isEmpty(tipsName)){
	 and a.tipsName like '%'+#{tipsName}+'%'
	@}
	@if(!isEmpty(tipsNames)){
	 and a.tipsName=#{tipsNames}
	@}
	@if(!isEmpty(KindID)){
		and a.FirstKindId=#{KindID}
	@}
	@if(!isEmpty(LastLogonMachine)){
		and LOWER(a.LastLoginMachine) like '%'+LOWER(#{LastLogonMachine})+'%'
	@}
	@if(!isEmpty(Nullity)){
		@if(Nullity=='0'){
			and a.LimitLogin=#{Nullity}
		@} else if(Nullity=='1') {
			and a.LimitLogin=#{Nullity}
		@} else if(Nullity=='3') {
			and a.limitRank=0
		@} else if(Nullity=='2') {
			and a.limitSend=1
		@}
	@}
	@if(!isEmpty(ip)){
	 and (a.LastLogonIP =#{ip} or a.RegisterIP =#{ip})
	@}
	@if(!isEmpty(StartLoginTime)){
		 and CONVERT(VARCHAR(100), a.LastLogonDate, 20) >= CONVERT(VARCHAR(100), #{StartLoginTime}, 20)
	  @}
	  @if(!isEmpty(EndLoginTime)){
		 and CONVERT(VARCHAR(100), a.LastLogonDate, 20) <= CONVERT(VARCHAR(100), #{EndLoginTime}, 20)
	  @}
	@if(!isEmpty(StartLogoutTime)){
		 and CONVERT(VARCHAR(100), a.LastLogoutTime, 20) >= CONVERT(VARCHAR(100), #{StartLogoutTime}, 20)
	  @}
	  @if(!isEmpty(EndLogoutTime)){
		 and CONVERT(VARCHAR(100), a.LastLogoutTime, 20) <= CONVERT(VARCHAR(100), #{EndLogoutTime}, 20)
	  @}
	@if(!isEmpty(StartRegistTime)){
		 and CONVERT(VARCHAR(100), a.RegisterDate, 20) >= CONVERT(VARCHAR(100), #{StartRegistTime}, 20)
	  @}
	  @if(!isEmpty(EndRegistTime)){
		 and CONVERT(VARCHAR(100), a.RegisterDate, 20) <= CONVERT(VARCHAR(100), #{EndRegistTime}, 20)
	  @}
    @if(!isEmpty(BindPhone)){
	 and a.bindPhone like '%'+#{BindPhone}+'%'
	@}
	@if(!isEmpty(MemberTypeID)){
	 	@if(MemberTypeID=='-1'){
			and a.vipLevel>0 and a.vipLevel<=6
		@} else if(MemberTypeID=='-2'){
			and a.Businessman=1
		@} else if(MemberTypeID=='0'){
			and (a.Businessman=0 or a.Businessman is null)
		@} else {
			and a.vipLevel=#{MemberTypeID}
		@}
	@}
	) t
	where 1=1
	@if(!isEmpty(CoinStart)){
	 and Score >= #{CoinStart}
	 @}
	  @if(!isEmpty(CoinEnd)){
	 and Score <= #{CoinEnd}
	 @}
	  @if(!isEmpty(GoldStart)){
	 and rewardGold >= #{GoldStart}
	 @}
	  @if(!isEmpty(GoldEnd)){
	 and rewardGold <= #{GoldEnd}
	 @}
	  @if(!isEmpty(RechargeStart)){
	 and TotalRecharge >= #{RechargeStart}
	 @}
	  @if(!isEmpty(RechargeEnd)){
	 and TotalRecharge <= #{RechargeEnd}
	 @}
	  @if(!isEmpty(ReStart)){
	 and RE >= #{ReStart}
	 @}
	  @if(!isEmpty(ReEnd)){
	 and RE <= #{ReEnd}
	 @}
	  @if(!isEmpty(WinStart)){
	    and TotalScore >= #{WinStart}
	 @}
	  @if(!isEmpty(WinEnd)){
	    and TotalScore <= #{WinEnd}
	 @}
    @if(!isEmpty(ExchangeStart)){
        and TotalExchange >= #{ExchangeStart}
    @}
    @if(!isEmpty(ExchangeEnd)){
        and TotalExchange <= #{ExchangeEnd}
    @}