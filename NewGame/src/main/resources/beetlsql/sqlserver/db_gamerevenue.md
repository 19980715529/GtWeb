list
===
	SELECT [wRevenue] ,[gameid],gk.KindName
	FROM [QPTreasureDB].[dbo].[GameRevenue] gr,
	[QPServerInfoDB].[dbo].[GameKindItem] gk
	where gr.gameid=gk.KindID
	  @if(!isEmpty(KindName)){
	  and gk.KindName like '%'+#{KindName}+'%'
	  @}
	  @if(!isEmpty(gameid)){
	  and gr.gameid=#{gameid}
	  @}