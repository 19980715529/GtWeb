new_list
===
    select * from [QPServerInfoDB].[dbo].[ActiveList] where isOpen=1
one_active
===
    select * from [QPServerInfoDB].[dbo].[ActiveList] where id = #{id}