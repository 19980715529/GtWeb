new_info
===
	select a.UserID,a.tipsName,a.IsDrain,a.NickName,a.ClientType,a.bindPhone,a.Email,
	(SELECT c.InsureScore FROM [QPTreasureDB].[dbo].[GameScoreInfo] as c WHERE c.UserID=a.UserID) AS InsureScore,
	(SELECT c.Amount FROM QPGameUserDB.dbo.AA_Shop_Prop_UserProp as c WHERE c.user_id=a.UserID and c.Prop_Id = 1) AS Score,
	(SELECT c.Amount FROM QPGameUserDB.dbo.AA_Shop_Prop_UserProp as c WHERE c.user_id=a.UserID and c.Prop_Id = 2) AS UserModel,
	(SELECT  AgentLevel FROM [QPGameUserDB].[dbo].[PlayerShare] with (nolock) WHERE UserID = a.UserID) as AgentLevel
	,isnull((select top 1 [Body] FROM [QPGameUserDB].[dbo].[AccountsTipNameDesc] d where d.UserID=a.UserID),'') TipDesc
	 from [QPGameUserDB].[dbo].[AccountsInfo] a
	  where 1=1
	  @if(!isEmpty(UserID)){
		 and a.UserID =#{UserID}
	  @}