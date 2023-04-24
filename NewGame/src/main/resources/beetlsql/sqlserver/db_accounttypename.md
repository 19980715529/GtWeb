list
===
	  select clientType,name,ratio,remarks
	  FROM [login].[dbo].[ClientPos]
	  where 1=1
	  @if(!isEmpty(name)){
	  and [name] like '%'+#{name}+'%'
	  @}
	  @if(!isEmpty(clientType)){
	  and clientType=#{clientType}
	  @}