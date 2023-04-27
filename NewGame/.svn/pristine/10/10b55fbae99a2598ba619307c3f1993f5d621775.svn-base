list
===
	select t1.*
	,(select BeautifulID from [QPGameUserDB].[dbo].[AccountsInfo] a where t1.UserID = a.UserID) send_BeautifulID
    	,(select BeautifulID from [QPGameUserDB].[dbo].[AccountsInfo] a where t1.TargetUserID = a.UserID) tar_BeautifulID
	,(case when (t1.Status=1) then '成功' else '失败' end) OperateResultName
	 from [RYPlatformManagerDB].[dbo].[player_operation_log] t1
	  where 1=1
		@if(!isEmpty(Type)){
		  and t1.Type = #{Type}
		 @}
		@if(!isEmpty(TargetUserID)){
		  and t1.TargetUserID = #{TargetUserID}
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
		@if(!isEmpty(startTime)){
	  	and DATEDIFF(DAY,t1.SendTime,#{startTime})<=0
	  @}
	  @if(!isEmpty(endTime)){
		 and DATEDIFF(DAY,t1.SendTime,#{endTime})>=0
	  @}
		@if(!isEmpty(OperatorName)){
		 and t1.OperatorName like '%'+#{OperatorName}+'%'
		@}
		@if(!isEmpty(Description)){
		 and t1.Description like '%'+#{Description}+'%'
		@}