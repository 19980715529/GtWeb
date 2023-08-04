new_list
===
    select id,amount,gold,give_gold giveNum,type,sort,skuId from [RYPlatformManagerDB].[dbo].[First_charge_config] where isDel=1
one_firstrechage
===
    select id, amount,gold,give_gold,type,sort,skuId from [RYPlatformManagerDB].[dbo].[First_charge_config] where id = #{id}