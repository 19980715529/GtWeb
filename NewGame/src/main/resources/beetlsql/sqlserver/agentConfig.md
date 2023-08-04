all_list
===
    select * from QPGameUserDB.dbo.config where id in (8,9,14,15)
one_config
===
    select * from QPGameUserDB.dbo.config where id=#{id}