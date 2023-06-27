new_list
===
    select id,amount,gold,give_gold giveNum,type,sort from [RYPlatformManagerDB].[dbo].[First_charge_config]
one_firstrechage
===
    select id, amount,gold,give_gold,type,sort from [RYPlatformManagerDB].[dbo].[First_charge_config] where id = #{id}