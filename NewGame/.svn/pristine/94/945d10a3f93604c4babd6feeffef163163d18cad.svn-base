new_list
===
	  select Goods_Id,GoodsName,Ticket,RelationAmount,ShowSort,Remark,IsOnLine,GoodsType
	  ,(case when GoodsType=1 then '普通实物' when GoodsType=2 then '话费卡' else '无类型' end) TypeName
	  ,(case when IsOnLine=0 then '下线' else '在线' end) IsOnLineName
	  FROM [QPGameUserDB].[dbo].[AA_Shop_Goods] where 1=1
	  @if(!isEmpty(CardTypeID)){
	  and Goods_Id=#{CardTypeID}
	  @}
	  @if(!isEmpty(GoodsName)){
	  and GoodsName like '%'+#{GoodsName}+'%'
	  @}