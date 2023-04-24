getGold
===
    SELECT Amount FROM [QPGameUserDB].[dbo].[AA_Shop_Prop_UserProp] where User_Id = #{UserId} and Prop_Id = 1
saveGold
===
    update [QPGameUserDB].[dbo].[AA_Shop_Prop_UserProp] set Amount=#{gold} where User_Id = #{UserId} and Prop_Id = 1
first_list
===
    select amount,gold,give_gold from [RYPlatformManagerDB].[dbo].[First_charge_config]
one_user
===
    SELECT User_Id FROM [QPGameUserDB].[dbo].[AA_Shop_Prop_UserProp] where User_Id = #{UserId}