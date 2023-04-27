new_list
===
    select id,isOpen,sort,state,typeSort,gameId,name,clientType,(select name from blade_dict where a.type=id) as type
    from [RYPlatformManagerDB].[dbo].[game_conf] as a where 1=1
    @if(!isEmpty(clientType)){
       and clientType = #{clientType}
    @}
one_list
===
    select * from [RYPlatformManagerDB].[dbo].[game_conf] where id =#{id}
game_list
===
    SELECT KindID,KindName FROM [QPServerInfoDB].[dbo].[GameKindItem]