new_recharge_log
===
	select t.*,c.CheatRate from 
	(select r.User_Id,r.Transaction_id,r.GetGold,r.UnitRMB,r.LogTime,r.Amount,a.GameID,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName,b.BuyTypeName,'成功' as NullityName,a.vipLevel,a.Businessman,r.BuyPlantID as BuyType_Id,a.ClientType
	,isnull((SELECT name FROM QPGameUserDB.dbo.AccountTypeName where clientType=a.ClientType),(case when a.ClientType=0 then '大厅' else '包'+cast(a.ClientType as varchar) end)) as AccountTypeName 
	FROM [QPGameUserDB].[dbo].[AA_Recharge_TotalRecord] r,
	[QPGameUserDB].[dbo].[AccountsInfo] a,[QPGameUserDB].[dbo].[AA_Recharge_BuyType] b
	where r.User_Id=a.UserID and r.BuyPlantID=b.BuyType_Id
	union all
	select g.userid User_Id, '0' as Transaction_id,(g.score+g.sendScore) GetGold,g.buymoney UnitRMB,g.writedate LogTime,1 as Amount,a.GameID,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName,'GM充值' as BuyTypeName,'成功' as NullityName,a.vipLevel,a.Businessman,'3' as BuyType_Id,a.ClientType
	,isnull((SELECT name FROM QPGameUserDB.dbo.AccountTypeName where clientType=a.ClientType),(case when a.ClientType=0 then '大厅' else '包'+cast(a.ClientType as varchar) end)) as AccountTypeName 
	FROM [QPGameUserDB].[dbo].[UserByScoreByGM] g,[QPGameUserDB].[dbo].[AccountsInfo] a where g.userid=a.UserID
	) t,
	QPTreasureDB.dbo.GameScoreInfo as c
	where t.User_Id=c.userid
	@if(!isEmpty(UserID)){
	 and t.User_Id =#{UserID}
	@}
	  @if(!isEmpty(GameID)){
	 and t.GameID =#{GameID}
	@}
	  @if(!isEmpty(OrderID)){
	 and t.Transaction_id =#{OrderID}
	@}
	  @if(!isEmpty(ShareID)){
	 and t.BuyType_Id =#{ShareID}
	@}
	@if(!isEmpty(PlatformID)){
	 and t.ClientType =#{PlatformID}
	@}
	@if(!isEmpty(MemberTypeID)){
		@if(MemberTypeID=='-1'){
			and t.vipLevel>0 and a.vipLevel<=6
		@} else if(MemberTypeID=='-2'){ 
			and t.Businessman=1
		@} else {
			and t.vipLevel=#{MemberTypeID}
		@}
	@}
	@if(!isEmpty(ApplyDate_dategt)){
		and DATEDIFF(DAY,t.LogTime,#{ApplyDate_dategt})<=0
	@}
	@if(!isEmpty(ApplyDate_datelt)){
	  and DATEDIFF(DAY,t.LogTime,#{ApplyDate_datelt})>=0
	@}
	
recharge_total_RMB
===
	select SUM(t.UnitRMB) UnitRMB from
	(select isnull(SUM([UnitRMB]),0) UnitRMB 
	FROM [QPGameUserDB].[dbo].[AA_Recharge_TotalRecord] g
	,[QPGameUserDB].[dbo].[AccountsInfo] a
	where g.User_Id=a.UserID
		@if(!isEmpty(UserID)){
	 and g.User_Id =#{UserID}
	@}
	  @if(!isEmpty(OrderID)){
	 and g.Transaction_id =#{OrderID}
	@}
	
	  @if(!isEmpty(ShareID)){
	 and g.BuyPlantID =#{ShareID}
	@}
	@if(!isEmpty(PlatformID)){
	 and a.ClientType =#{PlatformID}
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
	@if(!isEmpty(ApplyDate_dategt)){
		and DATEDIFF(DAY,g.LogTime,#{ApplyDate_dategt})<=0
	@}
	@if(!isEmpty(ApplyDate_datelt)){
	  and DATEDIFF(DAY,g.LogTime,#{ApplyDate_datelt})>=0
	@}
	union all
	select isnull(SUM([buymoney]),0) UnitRMB 
	FROM [QPGameUserDB].[dbo].[UserByScoreByGM] u
	,[QPGameUserDB].[dbo].[AccountsInfo] a
	where u.userid=a.UserID
		@if(!isEmpty(UserID)){
	 and u.userid =#{UserID}
	@}
	  @if(!isEmpty(OrderID)){
	 and u.rid is null
	@}
	@if(!isEmpty(PlatformID)){
	 and a.ClientType =#{PlatformID}
	@}
	@if(!isEmpty(ShareID)){
	 	@if(ShareID=='3'){
			and u.rid is not null
		@}else{
			and u.rid is null
		@}
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
	@if(!isEmpty(ApplyDate_dategt)){
		and DATEDIFF(DAY,u.writedate,#{ApplyDate_dategt})<=0
	@}
	@if(!isEmpty(ApplyDate_datelt)){
	  and DATEDIFF(DAY,u.writedate,#{ApplyDate_datelt})>=0
	@}
	) t

recharge_user_count
===
	  select COUNT(distinct User_Id) UserCount from
	  (select distinct User_Id 
	  from [QPGameUserDB].[dbo].[AA_Recharge_TotalRecord] g
	  ,[QPGameUserDB].[dbo].[AccountsInfo] a
	where g.User_Id=a.UserID
		@if(!isEmpty(UserID)){
	 and g.User_Id =#{UserID}
	@}
	  @if(!isEmpty(OrderID)){
	 and g.Transaction_id =#{OrderID}
	@}
	  @if(!isEmpty(ShareID)){
	 and g.BuyPlantID =#{ShareID}
	@}
	@if(!isEmpty(PlatformID)){
	 and a.ClientType =#{PlatformID}
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
	@if(!isEmpty(ApplyDate_dategt)){
		and DATEDIFF(DAY,g.LogTime,#{ApplyDate_dategt})<=0
	@}
	@if(!isEmpty(ApplyDate_datelt)){
	  and DATEDIFF(DAY,g.LogTime,#{ApplyDate_datelt})>=0
	@}
	  union all
	  select distinct u.userid User_Id 
	  from [QPGameUserDB].[dbo].[UserByScoreByGM] u
	  ,[QPGameUserDB].[dbo].[AccountsInfo] a
	where u.userid=a.UserID
		@if(!isEmpty(UserID)){
	 and u.userid =#{UserID}
	@}
	  @if(!isEmpty(OrderID)){
	 and u.rid is null
	@}
	@if(!isEmpty(PlatformID)){
	 and a.ClientType =#{PlatformID}
	@}
	@if(!isEmpty(ShareID)){
	 	@if(ShareID=='3'){
			and u.rid is not null
		@}else{
			and u.rid is null
		@}
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
	@if(!isEmpty(ApplyDate_dategt)){
		and DATEDIFF(DAY,u.writedate,#{ApplyDate_dategt})<=0
	@}
	@if(!isEmpty(ApplyDate_datelt)){
	  and DATEDIFF(DAY,u.writedate,#{ApplyDate_datelt})>=0
	@}
	  ) t

recharge_unstatistics
===
	SELECT [TradeSerialNo] Transaction_id ,[ThirdSerialNo] ,[User_Id] ,[Buy_Id]
	,[Amount] ,[BuyType],[TradeTime] LogTime,[PayTime] ,[ValidateTime]
	,[id] ,[InsertTime] ,[CloseTime]
	,[TotalAmount] UnitRMB,[ReceiptAmount] ,[InvoiceAmount]
	,[BuyerPayAmount] ,[BuyGold] GetGold,[isNotice]
	,(case [TradeState] when 1 then '成功' when 0 then '失败' end) as NullityName
	,(case [BuyType] when 1 then '微信' when 2 then '支付宝' else '其他' end) BuyTypeName
	,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
	FROM [QPGameUserDB].[dbo].[AA_Recharge_Buy_No_APPStore] r,
	[QPGameUserDB].[dbo].[AccountsInfo] a
	where r.User_Id=a.UserID
	@if(!isEmpty(UserID)){
    	 and r.User_Id =#{UserID}
    	@}
	@if(!isEmpty(ApplyDate_dategt)){
    		and DATEDIFF(DAY,TradeTime,#{ApplyDate_dategt})<=0
    	@}
    	@if(!isEmpty(ApplyDate_datelt)){
    	  and DATEDIFF(DAY,TradeTime,#{ApplyDate_datelt})>=0
    	@}
recharge_unstatistics_total
===
	select isnull(SUM([TotalAmount]),0) UnitRMB,COUNT(distinct [User_Id]) UserCount 
	from [QPGameUserDB].[dbo].[AA_Recharge_Buy_No_APPStore] with (nolock)
	where [TradeSerialNo] in ('201801052316466401332','201801061839360631811','201801062216025801909','201801062312557331938','201801062331128031947','201801070118360501985','201801070211509201999','201801070237198172003','201801070304412732013','201801071958187102278','201801072333232802366','201801080415543202429','2017122919293635727','20180104163835823570')
	
