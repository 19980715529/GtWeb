new_list
===
SELECT t.*
FROM (
	SELECT a.GameID,a.BeautifulID
		, CASE 
			WHEN a.tipsName IS NULL
			OR a.tipsName = '' THEN a.NickName
			ELSE a.NickName + '<span class="text-red">[' + a.tipsName + ']</span>'
		END AS NickName, a.LastLogonDate, t.*, g.js_BussniessCount + g.js_userCount AS TCurrency
		, g.out_userCount + g.out_BussniessCount AS SendScore, g.CheatRate AS CheatingRateSet, g.LimitScore AS CheatingScoreSet
		, CASE 
			WHEN DATEDIFF(day, '1900-01-01', g.lastEndCheatTime) = 0 THEN '无'
			ELSE CONVERT(VARCHAR(100), g.lastEndCheatTime, 120)
		END AS FinishTime, g.RealScore AS TotalWaste
		, (
			SELECT c.Amount
			FROM QPGameUserDB.dbo.AA_Shop_Prop_UserProp c
			WHERE t.UserID = c.user_id
				AND c.Prop_Id = 1
		) AS Score
		, (
			SELECT CASE 
					WHEN ServerID = 0 THEN '大厅'
					ELSE (
						SELECT RoomName
						FROM [QPServerInfoDB].[dbo].[GameRoomItem]
						WHERE ServerID = l.ServerID
					)
				END
			FROM [QPTreasureDB].[dbo].[GameScoreLocker] l
			WHERE l.UserID = t.UserID
		) AS ServerName
	FROM (
		SELECT isnull(t1.UserID, t2.UID5) AS UserID, t1.Type1, t1.Type2
			, t1.Type3, t1.Type4, t2.Type5
		FROM (
			SELECT isnull(t1.UserID, t2.UID4) AS UserID, t1.Type1, t1.Type2
				, t1.Type3, t2.Type4
			FROM (
				SELECT isnull(t1.UserID, t2.UID3) AS UserID, t1.Type1, t1.Type2
					, t2.Type3
				FROM (
					SELECT isnull(t1.UserID, t2.UID2) AS UserID, t1.Type1, t2.Type2
					FROM (
						SELECT a.UserID AS UserID, '1' AS Type1
						FROM [QPGameUserDB].[dbo].[AccountsInfo] a
						WHERE (
								SELECT RealScore
								FROM [QPTreasureDB].[dbo].[GameScoreInfo]
								WHERE UserID = a.UserID
							) < -200000
							AND (
								SELECT SUM(BuyScore)
								FROM QPGameUserDB.dbo.User_SendGoosRecord
								WHERE DATEDIFF(DAY, GETDATE(), DATEADD(DAY, 3, a.FristTradingTime)) >= 0
									AND UserID = a.UserID
							) > 300000
							AND ISNULL(a.Businessman,0) != 1
					) t1
						FULL JOIN (
							SELECT a.UserID AS UID2, '2' AS Type2
							FROM [QPGameUserDB].[dbo].[AccountsInfo] a
							WHERE (
									SELECT RealScore
									FROM [QPTreasureDB].[dbo].[GameScoreInfo]
									WHERE UserID = a.UserID
								) < -400000
								AND (
									SELECT SUM(BuyScore)
									FROM QPGameUserDB.dbo.User_SendGoosRecord
									WHERE DATEDIFF(DAY, GETDATE(), DATEADD(DAY, 7, a.FristTradingTime)) >= 0
										AND UserID = a.UserID
								) > 900000
								AND ISNULL(a.Businessman,0) != 1
						) t2
						ON t1.UserID = t2.UID2
				) t1
					FULL JOIN (
						SELECT a.UserID AS UID3, '3' AS Type3
						FROM [QPGameUserDB].[dbo].[AccountsInfo] a
						WHERE (
								SELECT RealScore
								FROM [QPTreasureDB].[dbo].[GameScoreInfo]
								WHERE UserID = a.UserID
							) < -900000
							AND (
								SELECT SUM(BuyScore)
								FROM QPGameUserDB.dbo.User_SendGoosRecord
								WHERE DATEDIFF(DAY, GETDATE(), DATEADD(DAY, 10, a.FristTradingTime)) >= 0
									AND UserID = a.UserID
							) > 2000000
							AND ISNULL(a.Businessman,0) != 1
					) t2
					ON t1.UserID = t2.UID3
			) t1
				FULL JOIN (
					SELECT a.UserID AS UID4, '4' AS Type4
					FROM [QPGameUserDB].[dbo].[AccountsInfo] a WITH (nolock)
					WHERE DATEDIFF(DAY, GETDATE(), DATEADD(DAY, 10, a.LastLogonDate)) >= 0
						AND (
							SELECT js_BussniessCount + js_userCount
							FROM [QPTreasureDB].[dbo].[GameScoreInfo]
							WHERE UserID = a.UserID
						) > 500000
						AND (
							SELECT out_userCount + out_BussniessCount
							FROM [QPTreasureDB].[dbo].[GameScoreInfo]
							WHERE UserID = a.UserID
						) = 0
						AND ISNULL(a.Businessman,0) != 1
				) t2
				ON t1.UserID = t2.UID4
		) t1
			FULL JOIN (
				SELECT a.UserID AS UID5, '5' AS Type5
				FROM [QPGameUserDB].[dbo].[AccountsInfo] a
				WHERE (
						SELECT SUM(BuyCount)
						FROM QPGameUserDB.dbo.User_SendGoosRecord
						WHERE DATEDIFF(day, RecordDate, GETDATE()) = 0
							AND UserID = a.UserID
							AND Vip_userid <> 0
					) >= 5
					AND (
						SELECT SUM(SellCount)
						FROM QPGameUserDB.dbo.User_SendGoosRecord
						WHERE DATEDIFF(day, RecordDate, GETDATE()) = 0
							AND UserID = a.UserID
							AND Vip_userid <> 0
					) <= 0
					AND ISNULL(a.Businessman,0) != 1
			) t2
			ON t1.UserID = t2.UID5
	) t, QPGameUserDB.dbo.AccountsInfo a, QPTreasureDB.dbo.GameScoreInfo g
	WHERE 
		t.UserID = a.UserID
		AND t.UserID = g.UserID
) t
 where 1=1
	  @if(!isEmpty(IsOnline)){
	  	@if(IsOnline=='1'){
		 	and t.ServerName is not null
	  	@} else {
	  		and t.ServerName is null
	  	@}
	  @}
	  @if(!isEmpty(IsControll)){
	  	@if(IsControll=='1'){
		 	and t.CheatingRateSet<>0
	  	@} else {
	  		and t.CheatingRateSet=0
	  	@}
	  @}
	  @if(!isEmpty(ServerName)){
	 	and t.ServerName like '%'+#{ServerName}+'%'
	  @}
	  @if(!isEmpty(endTime)){
	 	and DATEDIFF(DAY,FinishTime,#{endTime})>=0
	  @}

new_list_5
===
SELECT t.*
FROM (
	SELECT a.GameID,a.BeautifulID,a.UserID
		, CASE 
			WHEN a.tipsName IS NULL
			OR a.tipsName = '' THEN a.NickName
			ELSE a.NickName + '<span class="text-red">[' + a.tipsName + ']</span>'
		END AS NickName, a.LastLogonDate, g.js_BussniessCount + g.js_userCount AS TCurrency
		, g.out_userCount + g.out_BussniessCount AS SendScore, g.CheatRate AS CheatingRateSet, g.LimitScore AS CheatingScoreSet
		, CASE 
			WHEN DATEDIFF(day, '1900-01-01', g.lastEndCheatTime) = 0 THEN '无'
			ELSE CONVERT(VARCHAR(100), g.lastEndCheatTime, 120)
		END AS FinishTime, g.RealScore AS TotalWaste
		, (
			SELECT c.Amount
			FROM QPGameUserDB.dbo.AA_Shop_Prop_UserProp c
			WHERE a.UserID = c.user_id
				AND c.Prop_Id = 1
		) AS Score
		, (
			SELECT CASE 
					WHEN ServerID = 0 THEN '大厅'
					ELSE (
						SELECT RoomName
						FROM [QPServerInfoDB].[dbo].[GameRoomItem]
						WHERE ServerID = l.ServerID
					)
				END
			FROM [QPTreasureDB].[dbo].[GameScoreLocker] l
			WHERE l.UserID = a.UserID
		) AS ServerName
	FROM QPGameUserDB.dbo.AccountsInfo a, QPTreasureDB.dbo.GameScoreInfo g
	WHERE a.UserID = g.UserID
		and (
						SELECT SUM(BuyCount)
						FROM QPGameUserDB.dbo.User_SendGoosRecord
						WHERE DATEDIFF(day, RecordDate, GETDATE()) = 0
							AND UserID = a.UserID
							AND Vip_userid <> 0
					) >= 5
					AND (
						SELECT SUM(SellCount)
						FROM QPGameUserDB.dbo.User_SendGoosRecord
						WHERE DATEDIFF(day, RecordDate, GETDATE()) = 0
							AND UserID = a.UserID
							AND Vip_userid <> 0
					) <= 0
					AND ISNULL(a.Businessman,0) != 1
) t
 where 1=1
	  @if(!isEmpty(IsOnline)){
	  	@if(IsOnline=='1'){
		 	and t.ServerName is not null
	  	@} else {
	  		and t.ServerName is null
	  	@}
	  @}
	  @if(!isEmpty(IsControll)){
	  	@if(IsControll=='1'){
		 	and t.CheatingRateSet<>0
	  	@} else {
	  		and t.CheatingRateSet=0
	  	@}
	  @}
	  @if(!isEmpty(ServerName)){
	 	and t.ServerName like '%'+#{ServerName}+'%'
	  @}
	  @if(!isEmpty(endTime)){
	 	and DATEDIFF(DAY,FinishTime,#{endTime})>=0
	  @}
	  
new_list_4
===
SELECT t.*
FROM (
	SELECT a.GameID,a.BeautifulID,a.UserID
		, CASE 
			WHEN a.tipsName IS NULL
			OR a.tipsName = '' THEN a.NickName
			ELSE a.NickName + '<span class="text-red">[' + a.tipsName + ']</span>'
		END AS NickName, a.LastLogonDate, g.js_BussniessCount + g.js_userCount AS TCurrency
		, g.out_userCount + g.out_BussniessCount AS SendScore, g.CheatRate AS CheatingRateSet, g.LimitScore AS CheatingScoreSet
		, CASE 
			WHEN DATEDIFF(day, '1900-01-01', g.lastEndCheatTime) = 0 THEN '无'
			ELSE CONVERT(VARCHAR(100), g.lastEndCheatTime, 120)
		END AS FinishTime, g.RealScore AS TotalWaste
		, (
			SELECT c.Amount
			FROM QPGameUserDB.dbo.AA_Shop_Prop_UserProp c
			WHERE a.UserID = c.user_id
				AND c.Prop_Id = 1
		) AS Score
		, (
			SELECT CASE 
					WHEN ServerID = 0 THEN '大厅'
					ELSE (
						SELECT RoomName
						FROM [QPServerInfoDB].[dbo].[GameRoomItem]
						WHERE ServerID = l.ServerID
					)
				END
			FROM [QPTreasureDB].[dbo].[GameScoreLocker] l
			WHERE l.UserID = a.UserID
		) AS ServerName
	FROM QPGameUserDB.dbo.AccountsInfo a, QPTreasureDB.dbo.GameScoreInfo g
	WHERE a.UserID = g.UserID
		and DATEDIFF(DAY, GETDATE(), DATEADD(DAY, 10, a.LastLogonDate)) >= 0
						AND (
							SELECT js_BussniessCount + js_userCount
							FROM [QPTreasureDB].[dbo].[GameScoreInfo]
							WHERE UserID = a.UserID
						) > 500000
						AND (
							SELECT out_userCount + out_BussniessCount
							FROM [QPTreasureDB].[dbo].[GameScoreInfo]
							WHERE UserID = a.UserID
						) = 0
						AND ISNULL(a.Businessman,0) != 1
) t
 where 1=1
	  @if(!isEmpty(IsOnline)){
	  	@if(IsOnline=='1'){
		 	and t.ServerName is not null
	  	@} else {
	  		and t.ServerName is null
	  	@}
	  @}
	  @if(!isEmpty(IsControll)){
	  	@if(IsControll=='1'){
		 	and t.CheatingRateSet<>0
	  	@} else {
	  		and t.CheatingRateSet=0
	  	@}
	  @}
	  @if(!isEmpty(ServerName)){
	 	and t.ServerName like '%'+#{ServerName}+'%'
	  @}
	  @if(!isEmpty(endTime)){
	 	and DATEDIFF(DAY,FinishTime,#{endTime})>=0
	  @}
	  
new_list_3
===
SELECT t.*
FROM (
	SELECT a.GameID,a.BeautifulID,a.UserID
		, CASE 
			WHEN a.tipsName IS NULL
			OR a.tipsName = '' THEN a.NickName
			ELSE a.NickName + '<span class="text-red">[' + a.tipsName + ']</span>'
		END AS NickName, a.LastLogonDate, g.js_BussniessCount + g.js_userCount AS TCurrency
		, g.out_userCount + g.out_BussniessCount AS SendScore, g.CheatRate AS CheatingRateSet, g.LimitScore AS CheatingScoreSet
		, CASE 
			WHEN DATEDIFF(day, '1900-01-01', g.lastEndCheatTime) = 0 THEN '无'
			ELSE CONVERT(VARCHAR(100), g.lastEndCheatTime, 120)
		END AS FinishTime, g.RealScore AS TotalWaste
		, (
			SELECT c.Amount
			FROM QPGameUserDB.dbo.AA_Shop_Prop_UserProp c
			WHERE a.UserID = c.user_id
				AND c.Prop_Id = 1
		) AS Score
		, (
			SELECT CASE 
					WHEN ServerID = 0 THEN '大厅'
					ELSE (
						SELECT RoomName
						FROM [QPServerInfoDB].[dbo].[GameRoomItem]
						WHERE ServerID = l.ServerID
					)
				END
			FROM [QPTreasureDB].[dbo].[GameScoreLocker] l
			WHERE l.UserID = a.UserID
		) AS ServerName
	FROM QPGameUserDB.dbo.AccountsInfo a, QPTreasureDB.dbo.GameScoreInfo g
	WHERE a.UserID = g.UserID
		and (
						SELECT RealScore
								FROM [QPTreasureDB].[dbo].[GameScoreInfo]
								WHERE UserID = a.UserID
							) < -900000
							AND (
								SELECT SUM(BuyScore)
								FROM QPGameUserDB.dbo.User_SendGoosRecord
								WHERE DATEDIFF(DAY, GETDATE(), DATEADD(DAY, 10, a.FristTradingTime)) >= 0
									AND UserID = a.UserID
							) > 2000000
							AND ISNULL(a.Businessman,0) != 1
) t
 where 1=1
	  @if(!isEmpty(IsOnline)){
	  	@if(IsOnline=='1'){
		 	and t.ServerName is not null
	  	@} else {
	  		and t.ServerName is null
	  	@}
	  @}
	  @if(!isEmpty(IsControll)){
	  	@if(IsControll=='1'){
		 	and t.CheatingRateSet<>0
	  	@} else {
	  		and t.CheatingRateSet=0
	  	@}
	  @}
	  @if(!isEmpty(ServerName)){
	 	and t.ServerName like '%'+#{ServerName}+'%'
	  @}
	  @if(!isEmpty(endTime)){
	 	and DATEDIFF(DAY,FinishTime,#{endTime})>=0
	  @}
	  
new_list_2
===
SELECT t.*
FROM (
	SELECT a.GameID,a.BeautifulID,a.UserID
		, CASE 
			WHEN a.tipsName IS NULL
			OR a.tipsName = '' THEN a.NickName
			ELSE a.NickName + '<span class="text-red">[' + a.tipsName + ']</span>'
		END AS NickName, a.LastLogonDate, g.js_BussniessCount + g.js_userCount AS TCurrency
		, g.out_userCount + g.out_BussniessCount AS SendScore, g.CheatRate AS CheatingRateSet, g.LimitScore AS CheatingScoreSet
		, CASE 
			WHEN DATEDIFF(day, '1900-01-01', g.lastEndCheatTime) = 0 THEN '无'
			ELSE CONVERT(VARCHAR(100), g.lastEndCheatTime, 120)
		END AS FinishTime, g.RealScore AS TotalWaste
		, (
			SELECT c.Amount
			FROM QPGameUserDB.dbo.AA_Shop_Prop_UserProp c
			WHERE a.UserID = c.user_id
				AND c.Prop_Id = 1
		) AS Score
		, (
			SELECT CASE 
					WHEN ServerID = 0 THEN '大厅'
					ELSE (
						SELECT RoomName
						FROM [QPServerInfoDB].[dbo].[GameRoomItem]
						WHERE ServerID = l.ServerID
					)
				END
			FROM [QPTreasureDB].[dbo].[GameScoreLocker] l
			WHERE l.UserID = a.UserID
		) AS ServerName
	FROM QPGameUserDB.dbo.AccountsInfo a, QPTreasureDB.dbo.GameScoreInfo g
	WHERE a.UserID = g.UserID
		and (
					SELECT RealScore
									FROM [QPTreasureDB].[dbo].[GameScoreInfo]
									WHERE UserID = a.UserID
								) < -400000
								AND (
									SELECT SUM(BuyScore)
									FROM QPGameUserDB.dbo.User_SendGoosRecord
									WHERE DATEDIFF(DAY, GETDATE(), DATEADD(DAY, 7, a.FristTradingTime)) >= 0
										AND UserID = a.UserID
								) > 900000
								AND ISNULL(a.Businessman,0) != 1
) t
 where 1=1
	  @if(!isEmpty(IsOnline)){
	  	@if(IsOnline=='1'){
		 	and t.ServerName is not null
	  	@} else {
	  		and t.ServerName is null
	  	@}
	  @}
	  @if(!isEmpty(IsControll)){
	  	@if(IsControll=='1'){
		 	and t.CheatingRateSet<>0
	  	@} else {
	  		and t.CheatingRateSet=0
	  	@}
	  @}
	  @if(!isEmpty(ServerName)){
	 	and t.ServerName like '%'+#{ServerName}+'%'
	  @}
	  @if(!isEmpty(endTime)){
	 	and DATEDIFF(DAY,FinishTime,#{endTime})>=0
	  @}
	  
new_list_1
===
SELECT t.*
FROM (
	SELECT a.GameID,a.BeautifulID,a.UserID
		, CASE 
			WHEN a.tipsName IS NULL
			OR a.tipsName = '' THEN a.NickName
			ELSE a.NickName + '<span class="text-red">[' + a.tipsName + ']</span>'
		END AS NickName, a.LastLogonDate, g.js_BussniessCount + g.js_userCount AS TCurrency
		, g.out_userCount + g.out_BussniessCount AS SendScore, g.CheatRate AS CheatingRateSet, g.LimitScore AS CheatingScoreSet
		, CASE 
			WHEN DATEDIFF(day, '1900-01-01', g.lastEndCheatTime) = 0 THEN '无'
			ELSE CONVERT(VARCHAR(100), g.lastEndCheatTime, 120)
		END AS FinishTime, g.RealScore AS TotalWaste
		, (
			SELECT c.Amount
			FROM QPGameUserDB.dbo.AA_Shop_Prop_UserProp c
			WHERE a.UserID = c.user_id
				AND c.Prop_Id = 1
		) AS Score
		, (
			SELECT CASE 
					WHEN ServerID = 0 THEN '大厅'
					ELSE (
						SELECT RoomName
						FROM [QPServerInfoDB].[dbo].[GameRoomItem]
						WHERE ServerID = l.ServerID
					)
				END
			FROM [QPTreasureDB].[dbo].[GameScoreLocker] l
			WHERE l.UserID = a.UserID
		) AS ServerName
	FROM QPGameUserDB.dbo.AccountsInfo a, QPTreasureDB.dbo.GameScoreInfo g
	WHERE a.UserID = g.UserID
		and (
								SELECT RealScore
								FROM [QPTreasureDB].[dbo].[GameScoreInfo]
								WHERE UserID = a.UserID
							) < -200000
							AND (
								SELECT SUM(BuyScore)
								FROM QPGameUserDB.dbo.User_SendGoosRecord
								WHERE DATEDIFF(DAY, GETDATE(), DATEADD(DAY, 3, a.FristTradingTime)) >= 0
									AND UserID = a.UserID
							) > 300000
							AND ISNULL(a.Businessman,0) != 1
) t
 where 1=1
	  @if(!isEmpty(IsOnline)){
	  	@if(IsOnline=='1'){
		 	and t.ServerName is not null
	  	@} else {
	  		and t.ServerName is null
	  	@}
	  @}
	  @if(!isEmpty(IsControll)){
	  	@if(IsControll=='1'){
		 	and t.CheatingRateSet<>0
	  	@} else {
	  		and t.CheatingRateSet=0
	  	@}
	  @}
	  @if(!isEmpty(ServerName)){
	 	and t.ServerName like '%'+#{ServerName}+'%'
	  @}
	  @if(!isEmpty(endTime)){
	 	and DATEDIFF(DAY,FinishTime,#{endTime})>=0
	  @}