list
===
	SELECT [id] ,[serverUrl] ,[appId] ,[partner_private_key]
	,[third_public_key] ,[format] ,[charset]
	,[sign_type] ,[type],[payon]
	,(case when [type]=1 then '支付宝' when [type]=2 then '微信' else '其他' end) TypeName
	FROM [QPGameUserDB].[dbo].[AA_Recharge_ThirdPayConfig]
findtpye
===
    select ISNULL(a.[payon],0) payon From [QPGameUserDB].[dbo].[AA_Recharge_ThirdPayConfig] a  where [id] = #{id}
    
findid
===
     select * From [QPGameUserDB].[dbo].[AA_Recharge_ThirdPayConfig] a  where [id] = #{id}
     
moneylist
===
    SELECT [PayRMB] FROM [QPGameUserDB].[dbo].[AA_Recharge] where type_id = 1