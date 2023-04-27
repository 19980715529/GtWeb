list
===
	  select clientType,pos1,pos2,pos3,pos4,pos5,pos6,pos7,pos8,pos9,pos10,pos11,pos12,pos13,pos14,pos15,pos16,pos17,pos18,pos19,pos20
	  ,(select name from [QPGameUserDB].[dbo].[AccountTypeName] a where a.clientType=c.clientType) PackageName  
	  FROM [login].[dbo].[ClientPos] c where 1=1
	@if(!isEmpty(clientType)){
	 and clientType = #{clientType}
	@}
	@if(!isEmpty(PackageName)){
	 and clientType in (select clientType from [QPGameUserDB].[dbo].[AccountTypeName] a where a.name like '%'+#{PackageName}+'%')
	@}