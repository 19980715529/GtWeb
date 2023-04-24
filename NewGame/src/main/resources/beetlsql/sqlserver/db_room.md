new_list
===
	select * FROM [QPServerInfoDB].[dbo].[GameRoomItem] where 1=1
	@if(!isEmpty(ServerID)){
	 and ServerID = #{ServerID}
	@}
	@if(!isEmpty(RoomName)){
	 and RoomName like '%'+#{RoomName}+'%'
	@}