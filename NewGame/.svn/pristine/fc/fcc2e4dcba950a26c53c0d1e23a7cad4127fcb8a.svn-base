list
===
	select UserID,Note,NextIPIndex FROM [QPGameUserDB].[dbo].[K_LoginWhiteList]
	where 1 = 1  
	@if(!isEmpty(Note)){
	 and Note like '%'+#{Note}+'%'
	@}
	@if(!isEmpty(UserID)){
	 and UserID=#{UserID}
	@}