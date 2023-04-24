list
===
	SELECT * FROM [QPServerInfoDB].[dbo].[GameKindItem] p
	where 1=1
	@if(!isEmpty(KindID)){
	 and p.KindID =#{KindID}
	@}
	@if(!isEmpty(KindName)){
	 and p.KindName like '%'+#{KindName}+'%'
	@}