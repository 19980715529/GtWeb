new_list
===
    select * from [RYPlatformManagerDB].[dbo].[game_conf]
one_list
===
    select * from [RYPlatformManagerDB].[dbo].[game_conf] where id =#{id}
game_list
===
    SELECT KindID,KindName FROM [QPServerInfoDB].[dbo].[GameKindItem]