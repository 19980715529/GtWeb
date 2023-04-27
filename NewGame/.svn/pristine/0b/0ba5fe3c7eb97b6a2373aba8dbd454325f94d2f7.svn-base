new_list
===
	select [Buy_Id] ,[IOS_Product_Id] ,[Gold] ,[PayRMB] ,[clientType] ,[type_id]
	,isnull((SELECT d.name FROM QPGameUserDB.dbo.AccountTypeName as d where a.clientType = d.clientType),(case when a.clientType=0 then '包0' else '包'+cast(a.clientType as varchar) end)) as PackageName
	FROM [QPGameUserDB].[dbo].[AA_Recharge] a
	where 1=1
	@if(!isEmpty(Buy_Id)){
	 	and Buy_Id=#{Buy_Id}
	  @}
	@if(!isEmpty(IOS_Product_Id)){
	 	and IOS_Product_Id=#{IOS_Product_Id}
	  @}