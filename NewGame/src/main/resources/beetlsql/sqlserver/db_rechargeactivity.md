list
===
	select [index] as id,beginTime,endTime,productID,extraGold,extraTicket,buyTimes,pid,[clientType]
	,isnull((SELECT d.name FROM QPGameUserDB.dbo.AccountTypeName as d where a.clientType = d.clientType),(case when a.clientType=0 then '包0' else '包'+cast(a.clientType as varchar) end)) as PackageName
	FROM [QPGameUserDB].[dbo].[AA_Recharge_Activity] a
	where 1=1
	@if(!isEmpty(id)){
		and [index]=#{id}
	@}
	
max_id
===
	select MAX([index]) max_id FROM [QPGameUserDB].[dbo].[AA_Recharge_Activity]