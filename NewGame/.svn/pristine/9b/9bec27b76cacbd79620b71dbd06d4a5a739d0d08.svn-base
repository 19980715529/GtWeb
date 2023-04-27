list
===
       select [User_ID]
      ,[Withdrawal_State]
      ,[Withdrawal_Type]
      ,[Money]
      ,[UserGold]
      ,[Create_time]
      ,[Over_time]
      ,[ID]
      ,(SELECT NickName FROM [QPGameUserDB].[dbo].[AccountsInfo] where a.User_ID = [UserID] ) NickName 
      ,(SELECT ClientType FROM [QPGameUserDB].[dbo].[AccountsInfo] where a.User_ID = [UserID]) ClientType
      ,(SELECT BankOfDeposit FROM [QPGameUserDB].[dbo].[AA_PRO_Bank_Of_Deposit_Bind_Bind] WHERE [User_id] = a.User_ID and Bindtype = a.Withdrawal_Type) BankOfDeposit
      ,(SELECT name FROM [QPGameUserDB].[dbo].[AA_PRO_Bank_Of_Deposit_Bind_Bind] WHERE [User_id] = a.User_ID and Bindtype = a.Withdrawal_Type) name
      ,(SELECT bankNum FROM [QPGameUserDB].[dbo].[AA_PRO_Bank_Of_Deposit_Bind_Bind] WHERE [User_id] = a.User_ID and Bindtype = a.Withdrawal_Type) bankNum
      from [QPGameUserDB].[dbo].[AA_Recharge_By_WithdrawalRecord] a 
       where 1 = 1
            @if(!isEmpty(ID)){
            	 and ID = #{ID}
            @}