list
===
	select Prop_Id,Amount,platID
	,(case when Prop_Id=1 then '金币' else '奖券' end) PropName
	,(case when platID=0 then '苹果' when platID=1 then '安卓' else 'PC' end) PlatName
	FROM [QPGameUserDB].[dbo].[AA_NewPlayer_Prop]
	where 1=1
	@if(!isEmpty(Prop_Id)){
	 and Prop_Id = #{Prop_Id}
	@}
	@if(!isEmpty(platID)){
	 and platID = #{platID}
	@}