list
===
	select serverID,fishScorePercent
	,(select RoomName FROM [QPServerInfoDB].[dbo].[GameRoomItem] where ServerID=l.serverID) RoomName
	from [QPServerInfoDB].[dbo].[L_FishScoreConfig] l
	@if(!isEmpty(serverID)){
	 where serverID = #{serverID}
	@}