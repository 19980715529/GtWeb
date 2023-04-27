list
===
	select ID,[Desc],Param1,Param1Desc,Param2,Param2Desc,Param3,Param3Desc FROM [QPGameUserDB].[dbo].[AA_System_Int]
	where 1=1
	@if(!isEmpty(Desc)){
	 and [Desc] like '%'+#{Desc}+'%'
	@}
	@if(!isEmpty(ID)){
	 and ID=#{ID}
	@}