new_gift_log
===
	SELECT [id] ,[from_user] ,[to_user] ,[prop_id] ,[prop_count] ,[time]
	      ,[giveTime] ,[receiveTime] ,[isReceive] ,[SourceUserNicName]
	      ,[TargetUserName] ,[SendType] ,[CollectNote] ,[TradeType]
	      ,(case [SendType] when 0 then '金币赠送' else '银行赠送' end) SendTypeName
	  FROM [QPGameUserDB].[dbo].[ViewPresentSendScoreRecord] g
	  where 1=1
	  @if(!isEmpty(SendUserID)){
	 and g.from_user=#{SendUserID}
	@}
	 @if(!isEmpty(id)){
	 and g.id=#{id}
	@}
	@if(!isEmpty(RcvUserID)){
	 and g.to_user=#{RcvUserID}
	@}
	@if(!isEmpty(UserID)){
	 and (g.to_user=#{UserID} or g.from_user=#{UserID})
	@}
	@if(!isEmpty(startTime_gt)){
	  	and DATEDIFF(DAY,g.giveTime,#{startTime_gt})<=0
	  @}
	  @if(!isEmpty(endTime_lt)){
		 and DATEDIFF(DAY,g.giveTime,#{endTime_lt})>=0
	  @}
	@if(!isEmpty(SwapScore_numgt)){
	 and g.prop_count >= #{SwapScore_numgt}
	@}
	@if(!isEmpty(SwapScore_numlt)){
	 and g.prop_count <= #{SwapScore_numlt}
	@}

old_gift_log
===
	  select [id] ,[from_user] ,[to_user] ,[prop_id]
      ,[prop_count] ,[time] ,[giveTime] ,[receiveTime]
	  ,[open] as isReceive
	  ,(select BeautifulID from [QPGameUserDB].[dbo].[AccountsInfo] a where g.from_user = a.UserID) from_user_BeautifulID
      ,(select BeautifulID from [QPGameUserDB].[dbo].[AccountsInfo] a where g.[to_user] = a.UserID) to_user_BeautifulID
      ,isnull((select (case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) from [QPGameUserDB].[dbo].[AccountsInfo] a where a.UserID=g.from_user),'无昵称') SourceUserNicName
      ,isnull((select (case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) from [QPGameUserDB].[dbo].[AccountsInfo] a where a.UserID=g.to_user),'无昵称') TargetUserName
	FROM [QPGameUserDB].[dbo].[AA_GiveRecord] g
	  where 1=1
	  @if(!isEmpty(SendUserID)){
	 and g.from_user=#{SendUserID}
	@}
	 @if(!isEmpty(id)){
	 and g.id=#{id}
	@}
	@if(!isEmpty(RcvUserID)){
	 and g.to_user=#{RcvUserID}
	@}
	@if(!isEmpty(UserID)){
	 and (g.to_user=#{UserID} or g.from_user=#{UserID})
	@}
	@if(!isEmpty(startTime_gt)){
	  	and DATEDIFF(DAY,g.giveTime,#{startTime_gt})<=0
	  @}
	  @if(!isEmpty(endTime_lt)){
		 and DATEDIFF(DAY,g.giveTime,#{endTime_lt})>=0
	  @}
	@if(!isEmpty(SwapScore_numgt)){
	 and g.prop_count >= #{SwapScore_numgt}
	@}
	@if(!isEmpty(SwapScore_numlt)){
	 and g.prop_count <= #{SwapScore_numlt}
	@}

all_prop_count
===
    select  isnull(SUM([prop_count]),0) allcount
    	FROM [QPGameUserDB].[dbo].[AA_GiveRecord] g
    where 1=1
         @if(!isEmpty(UserID)){
    	 and (g.from_user=#{UserID})
    	@}
	  @if(!isEmpty(SendUserID)){
	 and g.from_user=#{SendUserID}
	@}
	@if(!isEmpty(RcvUserID)){
	 and g.from_user=#{RcvUserID}
	@}
    	@if(!isEmpty(startTime_gt)){
    	  	and DATEDIFF(DAY,g.giveTime,#{startTime_gt})<=0
    	  @}
    	  @if(!isEmpty(endTime_lt)){
    		 and DATEDIFF(DAY,g.giveTime,#{endTime_lt})>=0
    	  @}
all_prop_receive_count
===
    select  isnull(SUM([prop_count]),0) allcount
    	FROM [QPGameUserDB].[dbo].[AA_GiveRecord] g
    where 1=1
         @if(!isEmpty(UserID)){
    	 and (g.to_user=#{UserID})
    	@}
    		  @if(!isEmpty(SendUserID)){
	 and g.to_user=#{SendUserID}
	@}
	@if(!isEmpty(RcvUserID)){
	 and g.to_user=#{RcvUserID}
	@}
    	@if(!isEmpty(startTime_gt)){
    	  	and DATEDIFF(DAY,g.giveTime,#{startTime_gt})<=0
    	  @}
    	  @if(!isEmpty(endTime_lt)){
    		 and DATEDIFF(DAY,g.giveTime,#{endTime_lt})>=0
    	  @}