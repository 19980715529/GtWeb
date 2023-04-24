old_list
===
	SELECT [id] ,sa.[UserID] ,[UpdateTime],a.LastLoginTime,a.RegisterDate,b.InsureScore,b.js_BussniessCount,b.js_bankScoreCount,b.out_BussniessCount,b.out_bankScoreCount
	      ,c.Amount AS Score
	      ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
	  FROM [QPGameUserDB].[dbo].[SpecialAccounts] sa with (nolock) left join
	  [QPGameUserDB].[dbo].[AccountsInfo] a with (nolock)
	  on sa.UserID=a.UserID left join
	  [QPTreasureDB].[dbo].[GameScoreInfo] b with (nolock)
	  on sa.UserID=b.UserID left join
	  QPGameUserDB.dbo.AA_Shop_Prop_UserProp as c with (nolock)
	  on sa.UserID=c.User_Id

list
===  
	 
      SELECT [id],[NowHasID], B.[NickName],[OnceID],[UpdateDate]
       FROM [QPTreasureDB].[dbo].[SpecialAccounts] A
       LEFT JOIN [QPGameUserDB].[dbo].[AccountsInfo] B ON A.NowHasID = B.BeautifulID
blist
===
    SELECT [id]
          ,[BeautifulID]
          ,(CASE WHEN ([UserID] IS NULL OR [UserID] = 0) THEN '未绑定' else LTRIM([UserID]) END) UserID
      FROM [QPGameUserDB].[dbo].[ReserveBeautifulID]
      where 1 = 1
      @if(!isEmpty(UserID)){
      	 and BeautifulID = #{UserID}
      @}
      
find_list
===
    SELECT [id],[NowHasID], B.[NickName],[OnceID],[UpdateDate]
                    FROM [QPTreasureDB].[dbo].[SpecialAccounts] A
                    LEFT JOIN [QPGameUserDB].[dbo].[AccountsInfo] B ON A.NowHasID = B.UserID
                     WHERE  A.[NowHasID] = #{UserID}
          	 
find_Robit
===
     SELECT A.UserID, A.[NickName] , A.[LastLoginTime] , A.[LastLoginMachine] ,B.[InsureScore]
              	 FROM [QPGameUserDB].[dbo].[AccountsInfo] A 
              	 LEFT JOIN [QPTreasureDB].[dbo].[GameScoreInfo] B ON A.UserID = B.UserID
              	 WHERE A.[isRobit] = 1 and A.[UserID] = #{RobitID}