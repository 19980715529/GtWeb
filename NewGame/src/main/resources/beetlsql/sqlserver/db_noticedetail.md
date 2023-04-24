list
===
	SELECT [clientID] ,[content] ,[id] ,[name],[version] FROM [login].[dbo].[NoticeDetail] 
	where 1=1
	@if(!isEmpty(id)){
	 and id = #{id}
	@}
	@if(!isEmpty(clientID)){
	 and [clientID] = #{clientID}
	@}
