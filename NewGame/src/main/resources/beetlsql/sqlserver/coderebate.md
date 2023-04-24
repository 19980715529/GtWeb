new_list
===
    select * from [QPServerInfoDB].[dbo].[ActiveList] where ActiveID = 2
one_active
===
    select * from [QPServerInfoDB].[dbo].[ActiveList] where id = #{id}