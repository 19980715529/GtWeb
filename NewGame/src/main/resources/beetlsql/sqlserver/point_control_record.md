new_list
===
	  select t.* from (
	  select g.userid,oldlCheatRate,newlCheatRate,newlCheatLimit,writedate,memo
	  ,g.winScore
	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
	  ,isnull((select account from [RYPlatformManagerDB].[dbo].[blade_user] b where g.gid=b.id),'未知用户或系统自动点控') AdminUser
	  FROM [QPGameRecordDB].[dbo].[GMChangeCheatRecord] g 
	  ,[QPGameUserDB].[dbo].[AccountsInfo] a
	  where g.userid=a.UserID
	   @if(!isEmpty(UserID)){
	  and g.userid = #{UserID}
	 @}
	  @if(!isEmpty(MemberTypeID)){
	  	@if(MemberTypeID=='1'){
	  		and a.LimitLogin = 1
	  	@}else{
	  		and a.LimitLogin != 1
	  	@}
	 @}
	  @if(!isEmpty(CheatingRateNew)){
	  	@if(CheatingRateNew=='1'){
	  		and g.newlCheatRate>=0
	  	@}else{
	  		and g.newlCheatRate<0
	  	@}
	 @}
	 @if(!isEmpty(NickName)){
	  and a.NickName like '%'+#{NickName}+'%'
	 @}
	 @if(!isEmpty(startTime_gt)){
	  	and DATEDIFF(DAY,g.writedate,#{startTime_gt})<=0
	  @}
	  @if(!isEmpty(endTime)){
		 and DATEDIFF(DAY,g.writedate,#{endTime})>=0
	  @}
	  ) t
	  @if(!isEmpty(AdminUser)){
	  where t.AdminUser like '%'+#{AdminUser}+'%'
	  @}
	
win_point
===
	SELECT isnull(SUM(newlCheatLimit),0) as winPoint FROM [QPGameRecordDB].[dbo].[GMChangeCheatRecord] g 
	where newlCheatRate<0
		   @if(!isEmpty(UserID)){
	  and g.userid = #{UserID}
	 @}
	  @if(!isEmpty(MemberTypeID)){
	  	@if(MemberTypeID=='1'){
	  		and a.LimitLogin = 1
	  	@}else{
	  		and a.LimitLogin != 1
	  	@}
	 @}
	  @if(!isEmpty(CheatingRateNew)){
	  	@if(CheatingRateNew=='1'){
	  		and g.newlCheatRate>=0
	  	@}else{
	  		and g.newlCheatRate<0
	  	@}
	 @}
	 @if(!isEmpty(NickName)){
	  and g.userid in (select UserID from [QPGameUserDB].[dbo].[AccountsInfo] a where a.NickName like '%'+#{NickName}+'%')
	 @}
	 @if(!isEmpty(startTime_gt)){
	  	and DATEDIFF(DAY,g.writedate,#{startTime_gt})<=0
	  @}
	  @if(!isEmpty(endTime)){
		 and DATEDIFF(DAY,g.writedate,#{endTime})>=0
	  @}
	  @if(!isEmpty(AdminUser)){
	  and g.gid in (select id from [RYPlatformManagerDB].[dbo].[blade_user] b where b.account like '%'+#{AdminUser}+'%')
	  @}
lose_point
===
	SELECT isnull(SUM(newlCheatLimit),0) as losePoint FROM [QPGameRecordDB].[dbo].[GMChangeCheatRecord] g 
	where newlCheatRate>0
		   @if(!isEmpty(UserID)){
	  and g.userid = #{UserID}
	 @}
	  @if(!isEmpty(MemberTypeID)){
	  	@if(MemberTypeID=='1'){
	  		and a.LimitLogin = 1
	  	@}else{
	  		and a.LimitLogin != 1
	  	@}
	 @}
	  @if(!isEmpty(CheatingRateNew)){
	  	@if(CheatingRateNew=='1'){
	  		and g.newlCheatRate>=0
	  	@}else{
	  		and g.newlCheatRate<0
	  	@}
	 @}
	 @if(!isEmpty(NickName)){
	  and g.userid in (select UserID from [QPGameUserDB].[dbo].[AccountsInfo] a where a.NickName like '%'+#{NickName}+'%')
	 @}
	 @if(!isEmpty(startTime_gt)){
	  	and DATEDIFF(DAY,g.writedate,#{startTime_gt})<=0
	  @}
	  @if(!isEmpty(endTime)){
		 and DATEDIFF(DAY,g.writedate,#{endTime})>=0
	  @}
	  @if(!isEmpty(AdminUser)){
	  and g.gid in (select id from [RYPlatformManagerDB].[dbo].[blade_user] b where b.account like '%'+#{AdminUser}+'%')
	  @}

getWinAdnLose
===
	  select t1.WinScore,t2.LoseScore from
	  (select SUM(Amount) WinScore from [QPGameUserDB].[dbo].[AA_ZZ_Log_PropChange] 
	  where Prop_Id=1 and ChangeType_Id=1 and Amount>0
	  @if(!isEmpty(ids)){
	  and User_Id in (#{join(ids)})
	  @}
	  ) t1
	  left join
	  (select SUM(Amount) LoseScore from [QPGameUserDB].[dbo].[AA_ZZ_Log_PropChange] 
	  where Prop_Id=1 and ChangeType_Id=1 and Amount<0
	  @if(!isEmpty(ids)){
	  and User_Id in (#{join(ids)})
	  @}
	  ) t2
	  on 1=1

new_control_score_info
===
	select g.*,a.NickName,a.GameID FROM [QPTreasureDB].[dbo].[GameScoreInfo] g,
	[QPGameUserDB].[dbo].[AccountsInfo] a
	where g.UserID=a.UserID
	@if(!isEmpty(UserID)){
	  and g.UserID = #{UserID}
	 @}