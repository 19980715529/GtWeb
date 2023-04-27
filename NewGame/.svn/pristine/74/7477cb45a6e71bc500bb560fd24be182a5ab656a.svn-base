list
===
	select User_Id,SUM(Amount) Amount,ServerId
	,(select COUNT(1) from [QPGameUserDB].[dbo].[AA_ZZ_Log_Login] where USER_ID=p.User_Id and ServerId=p.ServerId
	@if(!isEmpty(startTime)){
		and CONVERT(VARCHAR(100), LogTime, 120) >= CONVERT(VARCHAR(100), #{startTime}, 120)
	@}
	@if(!isEmpty(endTime)){
		and CONVERT(VARCHAR(100), LogTime, 120) <= CONVERT(VARCHAR(100), #{endTime}, 120)
	@}
	) CountNum
	from QPGameUserDB.dbo.[AA_ZZ_Log_PropChange] p where 1=1
	@if(!isEmpty(ServerId)){
		and ServerId=#{ServerId}
	@}
	@if(!isEmpty(startTime)){
		and CONVERT(VARCHAR(100), LogTime, 120) >= CONVERT(VARCHAR(100), #{startTime}, 120)
	@}
	@if(!isEmpty(endTime)){
		and CONVERT(VARCHAR(100), LogTime, 120) <= CONVERT(VARCHAR(100), #{endTime}, 120)
	@}
	group by User_Id,ServerId
	
lost_list
===
	SELECT [UserID] ,[Accounts] ,[RegisterDate] ,[LastLogonDate] ,[IsBindPhone]
	,[IsCharged] ,[RealScore] ,[ClientType] ,[RcvScore] ,[SendScore]
	,[DayWinLost] ,[score] ,[InsureScore] ,[RS] ,[PackageName]
	,[UsedExchangeCard] ,[OneOnline] ,[ThreeOnline] ,[SevenOnline] ,[OvenSevenOnline]
	,(case when (tipsName is null or tipsName='') then NickName else (NickName+'<span class="text-red">['+tipsName+']</span>') end) NickName
	FROM [QPGameUserDB].[dbo].[ViewLostSearch] where 1=1
	@if(!isEmpty(PlatformID)){
		and ClientType=#{PlatformID}
	@}
	@if(!isEmpty(MemberTypeID)){
		@if(MemberTypeID=='1') {
			and IsBindPhone=0
		@} else if(MemberTypeID=='2') {
			and (IsBindPhone=1 and UsedExchangeCard=0)
		@} else if(MemberTypeID=='3') {
			and (IsBindPhone=1 and UsedExchangeCard>0 and IsCharged=0)
		@} else if(MemberTypeID=='4') {
			and (IsBindPhone=1 and RealScore<0 and IsCharged=1)
		@} else if(MemberTypeID=='5') {
			and (IsBindPhone=1 and RealScore>0 and IsCharged=1)
		@}
	@}
	@if(!isEmpty(LostType)){
		@if(LostType=='1') {
			and OneOnline=0
		@} else if(LostType=='3') {
			and ThreeOnline=0
		@} else if(LostType=='7') {
			and SevenOnline=0
		@} else if(LostType=='8') {
			and OvenSevenOnline=0
		@}
	@}
	@if(!isEmpty(StartRegistTime)){
		and CONVERT(VARCHAR(100), RegisterDate, 120) >= CONVERT(VARCHAR(100), #{StartRegistTime}, 120)
	@}
	@if(!isEmpty(EndRegistTime)){
		and CONVERT(VARCHAR(100), RegisterDate, 120) <= CONVERT(VARCHAR(100), #{EndRegistTime}, 120)
	@}