list
===
	SELECT id,curServerID,mainServerID,g.RoomName as CurRoomName,g1.RoomName as MainRoomName 
	FROM QPTreasureDB.[dbo].[L_BloodServerConnect] lb with (nolock)
	LEFT JOIN QPServerInfoDB.dbo.GameRoomItem g on lb.curServerID=g.ServerID
	LEFT JOIN QPServerInfoDB.dbo.GameRoomItem g1 on lb.mainServerID=g1.ServerID