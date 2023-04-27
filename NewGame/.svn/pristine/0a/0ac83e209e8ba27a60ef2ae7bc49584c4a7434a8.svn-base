list
===
	select System_Id,SystemName,SystemInfo FROM [QPGameUserDB].[dbo].[AA_System]
	where 1=1
	@if(!isEmpty(SystemName)){
	 and SystemName like '%'+#{SystemName}+'%'
	@}
	@if(!isEmpty(System_Id)){
	 and System_Id=#{System_Id}
	@}