list
===
	select l.*
	,(case when status=0 then '<span class="text-red">吃分</span>' else '<span class="text-green">吐分</span>' end) StatusName
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] g where g.ServerID=l.serverID) ServerName
	FROM [QPTreasureDB].[dbo].[L_BloodPoolConfig] l where 1=1
	@if(!isEmpty(serverID)){
	 and serverID = #{serverID}
	@}
	@if(!isEmpty(ServerName)){
	 and serverID in (select ServerID from [QPServerInfoDB].[dbo].[GameRoomItem] g where g.RoomName like '%'+#{ServerName}+'%')
	@}