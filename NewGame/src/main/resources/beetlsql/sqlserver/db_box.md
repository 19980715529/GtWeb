new_list
===
	SELECT boxtype,usemoney,itemnum1,itemnum2
	,(case when boxtype=0 then '铜宝箱' when boxtype=1 then '银宝箱' else '金宝箱' end) typeName
	FROM [QPGameUserDB].[dbo].[AA_BoxItemsConfig]
	where 1=1
	@if(!isEmpty(boxtype)){
	 and boxtype=#{boxtype}
	@}