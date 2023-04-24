list
===
	select t1.*,t2.GameID,
	(case when (t1.Status=1) then '成功' else '失败' end) OperateResultName
	 from [RYPlatformManagerDB].[dbo].[player_operation_log] t1,[QPGameUserDB].[dbo].[AccountsInfo] t2
	where t1.UserID=t2.UserID
	@if(!isEmpty(Type)){
		@if(Type=='-1'){
		  and t1.Type in (2,22)
		@}else{
		  and t1.Type =#{Type}
		@}
	 @}
	@if(!isEmpty(UserID)){
	  and t1.UserID = #{UserID}
	 @}
	@if(!isEmpty(GameID)){
	  and t2.GameID = #{GameID}
	 @}
	 @if(!isEmpty(InsertTime_dategt)){
		 and CONVERT(VARCHAR(100), t1.InsertTime, 20) >= CONVERT(VARCHAR(100), #{InsertTime_dategt}, 20)
	  @}
	  @if(!isEmpty(InsertTime_datelt)){
		 and CONVERT(VARCHAR(100), t1.InsertTime, 20) <= CONVERT(VARCHAR(100), #{InsertTime_datelt}, 20)
	  @}
	@if(!isEmpty(OperatorName)){
	 and t1.OperatorName like '%'+#{OperatorName}+'%'
	@}
	@if(!isEmpty(Description)){
	 and t1.Description like '%'+#{Description}+'%'
	@}
	@if(!isEmpty(OperateReasonType)){
	 and t1.OperateReasonType like '%'+#{OperateReasonType}+'%'
	@}

new_all_list
===
	select t.* from
	(select t1.ID,t1.UserID,t1.OperatorID,t1.OperatorName,t1.InsertTime,t1.OperateReasonType,t1.OperateReasonID
	,t1.Description,t1.Gold,t1.Type,t1.BeforeGold,t1.AfterGold,t1.UserTypeID,t1.UserTypeName,t1.Status,t1.OperateIP,t1.remark
	  ,t1.TargetUserID,t1.TargetUserName,t1.SendTime,
	(select (case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName from QPGameUserDB.dbo.AccountsInfo AS a where a.UserID = t1.UserID) NickName,
	(case when (t1.Status=1) then '成功' else '失败' end) OperateResultName
	 from [RYPlatformManagerDB].[dbo].[player_operation_log] t1
	where 1=1
	@if(!isEmpty(Type)){
		@if(Type=='-1'){
		  and t1.Type in (2,22)
		@}else{
		  and t1.Type =#{Type}
		@}
	 @}
	@if(!isEmpty(UserID)){
	  and t1.UserID = #{UserID}
	 @}
	@if(!isEmpty(InsertTime_dategt)){
		 and CONVERT(VARCHAR(100), t1.InsertTime, 20) >= CONVERT(VARCHAR(100), #{InsertTime_dategt}, 20)
	  @}
	  @if(!isEmpty(InsertTime_datelt)){
		 and CONVERT(VARCHAR(100), t1.InsertTime, 20) <= CONVERT(VARCHAR(100), #{InsertTime_datelt}, 20)
	  @}
	@if(!isEmpty(OperatorName)){
	 and t1.OperatorName like '%'+#{OperatorName}+'%'
	@}
	@if(!isEmpty(Description)){
	 and t1.Description like '%'+#{Description}+'%'
	@}
	) t
	where 1=1
	@if(!isEmpty(NickName)){
	 and t.NickName like '%'+#{NickName}+'%'
	@}
	
all_list
===
	select t1.*,t2.GameID from (select t1.*,
	(case when (t1.Status=1) then '成功' else '失败' end) OperateResultName
	 from [RYPlatformManagerDB].[dbo].[player_operation_log] t1
	where 1=1
	@if(!isEmpty(Type)){
	  and t1.Type = #{Type}
	 @}
	@if(!isEmpty(UserID)){
	  and t1.UserID = #{UserID}
	 @}
	 @if(!isEmpty(InsertTime_dategt)){
	 	and t1.InsertTime>=#{InsertTime_dategt}
	@}
	@if(!isEmpty(InsertTime_datelt)){
		and t1.InsertTime<=#{InsertTime_datelt}
	@}
	@if(!isEmpty(OperatorName)){
	 and t1.OperatorName like '%'+#{OperatorName}+'%'
	@}
	@if(!isEmpty(Description)){
	 and t1.Description like '%'+#{Description}+'%'
	@}
	) t1
	left join
	(select * from [RYAccountsDB].[dbo].[AccountsInfo]) t2
	on t1.UserID=t2.UserID