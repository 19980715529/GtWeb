list
===
	  SELECT [nIndex] ,[UserID] ,[Score] ,[InsureScore]
    	 ,(case g.UserID when 0 then '管理员' else (isnull((select (case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) from [QPGameUserDB].[dbo].[AccountsInfo] a where a.UserID=g.UserID),'无昵称')) end) SourceUserNicName
    	 ,[GameScore]
    	 ,[BankScore]
    	 ,[Memo]
         ,[ChangeDate]
    	FROM [QPGameRecordDB].[dbo].[ScoreChangeDetail] g where 1=1
	@if(!isEmpty(UserID)){
	 and (g.UserID=#{UserID} or g.UserID=#{UserID})
	@}
	@if(!isEmpty(TradeType)){
	 and g.Type=#{TradeType}
	@}
	@if(!isEmpty(startTime_gt)){
	  	and DATEDIFF(DAY,g.ChangeDate,#{startTime_gt})<=0
	  @}
	  @if(!isEmpty(endTime_lt)){
		 and DATEDIFF(DAY,g.ChangeDate,#{endTime_lt})>=0
	  @}