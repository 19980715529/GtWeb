new_coupon_log
===
	select p.*
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b WHERE b.ChangeType_Id = p.ChangeType_Id) as TypeRemark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p.ServerId) as RoomName
	from [QPGameUserDB].[dbo].[AA_ZZ_Log_PropChange] p
	where p.Prop_Id=2
	@if(!isEmpty(UserID)){
	 	and p.User_Id =#{UserID}
	@}
	@if(!isEmpty(BuyDate_dategt)){
	 	and p.LogTime>=#{BuyDate_dategt}
	@}
	@if(!isEmpty(BuyDate_datelt)){
		and p.LogTime<=#{BuyDate_datelt}
	@}