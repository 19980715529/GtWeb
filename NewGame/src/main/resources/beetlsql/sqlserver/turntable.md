new_list
===
    select * from [QPServerInfoDB].[dbo].[Turntable_TurntableConfig]
one_turntable
===
    select * from [QPServerInfoDB].[dbo].[Turntable_TurntableConfig] where Id = #{id}
