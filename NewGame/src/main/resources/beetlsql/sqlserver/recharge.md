new_list
===
	SELECT u.rid,u.buymoney,u.score,u.sendscore,'GM大额充值' as ProductName FROM [QPGameUserDB].[dbo].[UserBuyScoreType] u
new_all_list
===
	SELECT rid,buymoney FROM [QPGameUserDB].[dbo].[UserBuyScoreType]
query_orderNum
===
    SELECT * FROM [RYPlatformManagerDB].[dbo].[Recharge_records] where orderNumber=#{orderNum}
orderNum_One
===
    select * from [RYPlatformManagerDB].[dbo].[Recharge_records] where orderNumber = #{orderNumber}
user_recharge_list
===
    select createTime,topUpAmount as amount,gold,orderStatus from [RYPlatformManagerDB].[dbo].[Recharge_records] where userId= #{userId}
get_unPay_recharge_list
===
    select * from [RYPlatformManagerDB].[dbo].[Recharge_records] where orderStatus = 1