list
===
		SELECT [User_Id] ,aeu.Exchange_Id ,[ExchangeTime]
        	,ae.ExchangeCode,ae.ExchangeState,ae.MakeTime
        	,isnull((select Amount from [QPGameUserDB].[dbo].[AA_ExchangeCode_Prop] where Prop_Id=1),0) GoldAmount
        	,isnull((select Amount from [QPGameUserDB].[dbo].[AA_ExchangeCode_Prop] where Prop_Id=2),0) ModelAmount
        	,(select (case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
        	from [QPGameUserDB].[dbo].[AccountsInfo] a where a.UserID=aeu.User_Id) NickName
        	,(select eg.UserID from [QPGameUserDB].[dbo].[AA_ExchangeCode_Generate] eg where eg.ExchangeBatch = ae.ExchangeBatch) VIPID
        	,ae.ExchangeBatch
        	FROM [QPGameUserDB].[dbo].[AA_ExchangeCode_UserExchange] aeu,
        	[QPGameUserDB].[dbo].[AA_ExchangeCode] ae where aeu.Exchange_Id=ae.Exchange_Id
    	@if(!isEmpty(UserID)){
    	 and User_Id=#{UserID}
    	@}
    	@if(!isEmpty(ExchangeCode)){
    	 and ae.ExchangeCode=#{ExchangeCode}
    	@}
    	@if(!isEmpty(startTime_gt)){
    	  	and ExchangeTime >= #{startTime_gt}
    	  @}
    	  @if(!isEmpty(endTime_lt)){
    		 and ExchangeTime <= #{endTime_lt}
    	  @}

Generate_list
===
    SELECT [id],[UserID],g.[ExchangeBatch],[ExchangeNum],[ExchangeGenerate]
          ,(select NickName FROM [QPGameUserDB].[dbo].[AccountsInfo] a where a.UserID = g.UserId and a.Businessman = 1) NickName
    	  FROM [QPGameUserDB].[dbo].[AA_ExchangeCode_Generate] g
          where g.ExchangeBatch != 0
    @if(!isEmpty(UserID)){
    	 and g.UserID=#{UserID}
    	@}
    @if(!isEmpty(startTime_gt)){
        and DATEDIFF(DAY,g.ExchangeGenerate,#{startTime_gt})<=0
        @}
    @if(!isEmpty(endTime_lt)){
     	and DATEDIFF(DAY,g.ExchangeGenerate,#{endTime_lt})>=0
     	 @}
     	 
Exdetails_list
===
	SELECT     code.ExchangeCode, code.ExchangeState, code.MakeTime, code.ExchangeBatch, code.Exchange_Id
	FROM         [QPGameUserDB].dbo.AA_ExchangeCode AS code
    	where ExchangeBatch = #{ExchangeBatch} 

batch_card_list
===
	SELECT code.ExchangeCode, code.ExchangeState, code.MakeTime, code.Exchange_Id
	, null as ExchangeTime,null as User_Id,null as UpScore,null as DownScore
	FROM [QPGameUserDB].[dbo].AA_ExchangeCode AS code
	where code.ExchangeBatch = #{ExchangeBatch}
	@if(!isEmpty(ExchangeCode)){
    	 and ExchangeCode=#{ExchangeCode}
    @}

card_used_list
===
	select u.ExchangeTime, u.User_Id, u.UpScore, u.DownScore,u.Exchange_Id
	from [QPGameUserDB].[dbo].AA_ExchangeCode_UserExchange AS u 
	where u.Exchange_Id in (SELECT Exchange_Id FROM [QPGameUserDB].[dbo].AA_ExchangeCode where ExchangeBatch = #{ExchangeBatch})
	@if(!isEmpty(UserID)){
	 and User_Id=#{UserID}
	@}