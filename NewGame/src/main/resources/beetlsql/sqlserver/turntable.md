new_list
===
    select * from [QPServerInfoDB].[dbo].[TurntableRewardTypeConfig]
one_turntable
===
    select * from [QPServerInfoDB].[dbo].[TurntableRewardTypeConfig] where Id = #{id}
