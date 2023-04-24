list
===
	SELECT [Prop_Id] ,[Amount]
	,(case Prop_Id when 1 then '金币' else '奖券' end) PropName
	FROM [QPGameUserDB].[dbo].[AA_ExchangeCode_Prop]