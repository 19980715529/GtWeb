new_recharge_log
===
	select * from [RYPlatformManagerDB].[dbo].[Recharge_records] as r where orderStatus=2
    @if(!isEmpty(UserID)){
	 and r.userId =#{UserID}
	@}
	  @if(!isEmpty(OrderID)){
	 and r.orderNumber =#{OrderID}
	@}
	  @if(!isEmpty(ChannelID)){
	 and r.channelPid =#{ChannelID}
	@}
    @if(!isEmpty(moneyMin)){
	 and r.topUpAmount >=#{moneyMin}
	@}
    @if(!isEmpty(moneyMax)){
	    and r.topUpAmount <=#{moneyMax}
	@}
    @if(!isEmpty(recType)){
        and r.isThatTay=#{recType}
    @}
    @if(!isEmpty(clientType)){
        and r.packageName=#{clientType}
    @}
    @if(!isEmpty(createTime)){
		and DATEDIFF(DAY,r.createTime,#{createTime})<=0
	@}
	@if(!isEmpty(createTime)){
        and DATEDIFF(DAY,r.createTime,#{endTime})>=0
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

current_recharge
===
    select count(distinct userId) as count,ISNULL(sum(topUpAmount),0) as money 
    from [RYPlatformManagerDB].[dbo].[Recharge_records] where DateDiff(dd,createTime,getdate())=0 and orderStatus=2
new_recharge
===
    select count(distinct userId) as count,ISNULL(sum(topUpAmount),0) as money 
    from [RYPlatformManagerDB].[dbo].[Recharge_records] where DateDiff(dd,createTime,getdate())=0 and orderStatus=2 and isThatTay=1
all_recharge
===
    select count(distinct userId) as num, ISNULL(sum(topUpAmount),0) as money from [RYPlatformManagerDB].[dbo].[Recharge_records] where orderStatus=2
all_recharges
===
    select count(distinct userId) as num, ISNULL(sum(topUpAmount),0) as money from [RYPlatformManagerDB].[dbo].[Recharge_records] where orderStatus=2

recharge_unstatistics
===
	SELECT [createTime] ,[orderNumber] ,[userId] ,[nickname],[topUpAmount] ,[gold],[orderStatus],[packageName],[channel],[channel_type],[endTime],[msg]
	FROM [RYPlatformManagerDB].[dbo].[Recharge_records]
	where 1=1
	@if(!isEmpty(UserID)){
    	 and userId =#{UserID}
    @}
    @if(!isEmpty(orderStatus)){
    	 and orderStatus =#{orderStatus}
    @}else{
        and orderStatus in (1,3)
    @}
	@if(!isEmpty(ApplyDate_dategt)){
        and DATEDIFF(DAY,createTime,#{ApplyDate_dategt})<=0
    @}
    @if(!isEmpty(ApplyDate_datelt)){
        and DATEDIFF(DAY,createTime,#{ApplyDate_datelt})>=0
    @}
recharge_unstatistics_total
===
	select isnull(SUM([TotalAmount]),0) UnitRMB,COUNT(distinct [User_Id]) UserCount 
	from [QPGameUserDB].[dbo].[AA_Recharge_Buy_No_APPStore] with (nolock)
	where [TradeSerialNo] in ('201801052316466401332','201801061839360631811','201801062216025801909','201801062312557331938','201801062331128031947','201801070118360501985','201801070211509201999','201801070237198172003','201801070304412732013','201801071958187102278','201801072333232802366','201801080415543202429','2017122919293635727','20180104163835823570')

