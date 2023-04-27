list
===
	  select Prop_Id,Amount
	  ,(case when Prop_Id=1 then '金币' else '奖券' end) PropName
	  FROM [QPGameUserDB].[dbo].[AA_PhoneNumber_Prop]
	where 1=1
	@if(!isEmpty(Prop_Id)){
	 and Prop_Id = #{Prop_Id}
	@}