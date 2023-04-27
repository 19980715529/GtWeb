list
===
	SELECT [ServerID] ,[ServerName] ,[TotalTax] ,[TotalScore] ,[CheatScore] ,[GameUserNum] 
	,CONVERT(VARCHAR(100), [LogTime], 23) LogTime
	FROM [QPGameRecordDB].[dbo].[RoomScoreRecord] with (nolock) where 1=1
	@if(!isEmpty(ServerID)){
		and ServerID = #{ServerID}
	@}
	@if(!isEmpty(ServerName)){
		and ServerName like '%'+#{ServerName}+'%'
	@}
	@if(!isEmpty(StartTime)){
		and CONVERT(VARCHAR(100), LogTime, 23) >= CONVERT(VARCHAR(100), #{StartTime}, 23)
	@}
	@if(!isEmpty(EndTime)){
		and CONVERT(VARCHAR(100), LogTime, 23) <= CONVERT(VARCHAR(100), #{EndTime}, 23)
	@}