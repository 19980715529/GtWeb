new_list
===
	  select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
	  ,a.LastLogonDate,a.BeautifulID
	  from
	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameUserDB].[dbo].[AA_ZZ_Log_PropChange] with (nolock)
	   where Prop_Id=1 and ChangeType_Id=1
	   @if(!isEmpty(ServerId)){
	 	and ServerId =#{ServerId}
	   @}
	    @if(!isEmpty(StartTime_dategt)){
    		 and Time_Id>=#{StartTime_dategt}
    	  @}
    	  @if(!isEmpty(EndTime_datelt)){
    		 and Time_Id<=#{EndTime_datelt}
    	  @}
	  @if(!isEmpty(UserID)){
	 	and User_Id =#{UserID}
	   @}
	   group by User_Id
	   @if(!isEmpty(WinResultStart)){
	 	having SUM(Amount)>=#{WinResultStart}
	 	 @if(!isEmpty(WinResultEnd)){
	 	 	and SUM(Amount)<=#{WinResultEnd}
	      @}
	   @} else if(!isEmpty(WinResultEnd)){
	 	having SUM(Amount)<=#{WinResultEnd}
	   @}
	   ) t
	  ,QPTreasureDB.dbo.GameScoreInfo as g with (nolock),[QPGameUserDB].[dbo].[AccountsInfo] as a with (nolock)
	  where t.UserID=g.UserID and t.UserID=a.UserID

backend_list
===
	  select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,a.GameID
	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
	  from
	  (select SUM(Amount) DayWaste,User_Id UserID FROM [Backend].[dbo].[AA_ZZ_Log_PropChange] with (nolock)
	   where Prop_Id=1 and ChangeType_Id=1
	   @if(!isEmpty(ServerId)){
	 	and ServerId =#{ServerId}
	   @}
	   @if(!isEmpty(UserID)){
	 	and User_Id =#{UserID}
	   @}
	   @if(!isEmpty(StartTime_dategt)){
		 and CONVERT(VARCHAR(100), LogTime, 20) >= CONVERT(VARCHAR(100), #{StartTime_dategt}, 20)
	  @}
	  @if(!isEmpty(EndTime_datelt)){
		 and CONVERT(VARCHAR(100), LogTime, 20) <= CONVERT(VARCHAR(100), #{EndTime_datelt}, 20)
	  @}
	   group by User_Id
	   @if(!isEmpty(WinResultStart)){
	 	having SUM(Amount)>=#{WinResultStart}
	 	 @if(!isEmpty(WinResultEnd)){
	 	 	and SUM(Amount)<=#{WinResultEnd}
	      @}
	   @} else if(!isEmpty(WinResultEnd)){
	 	having SUM(Amount)<=#{WinResultEnd}
	   @}
	   ) t
	  ,QPTreasureDB.dbo.GameScoreInfo as g with (nolock),[QPGameUserDB].[dbo].[AccountsInfo] as a with (nolock)
	  where t.UserID=g.UserID and t.UserID=a.UserID
	  
1_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_1]
    	   where Prop_Id=1 and ChangeType_Id=1
    	   @if(!isEmpty(ServerId)){
    	 	and ServerId =#{ServerId}
    	   @}
    	   @if(!isEmpty(StartTime_dategt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) >= CONVERT(VARCHAR(100), #{StartTime_dategt}, 20)
    	  @}
    	  @if(!isEmpty(EndTime_datelt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) <= CONVERT(VARCHAR(100), #{EndTime_datelt}, 20)
    	  @}
    	  @if(!isEmpty(UserID)){
    	 	and User_Id =#{UserID}
    	   @}
    	   group by User_Id
    	   @if(!isEmpty(WinResultStart)){
    	 	having SUM(Amount)>=#{WinResultStart}
    	 	 @if(!isEmpty(WinResultEnd)){
    	 	 	and SUM(Amount)<=#{WinResultEnd}
    	      @}
    	   @} else if(!isEmpty(WinResultEnd)){
    	 	having SUM(Amount)<=#{WinResultEnd}
    	   @}
    	   ) t
    	  ,QPTreasureDB.dbo.GameScoreInfo as g with (nolock),[QPGameUserDB].[dbo].[AccountsInfo] as a with (nolock)
    	  where t.UserID=g.UserID and t.UserID=a.UserID
7_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_7]
    	   where Prop_Id=1 and ChangeType_Id=1
    	   @if(!isEmpty(ServerId)){
    	 	and ServerId =#{ServerId}
    	   @}
    	   @if(!isEmpty(StartTime_dategt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) >= CONVERT(VARCHAR(100), #{StartTime_dategt}, 20)
    	  @}
    	  @if(!isEmpty(EndTime_datelt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) <= CONVERT(VARCHAR(100), #{EndTime_datelt}, 20)
    	  @}
    	  @if(!isEmpty(UserID)){
    	 	and User_Id =#{UserID}
    	   @}
    	   group by User_Id
    	   @if(!isEmpty(WinResultStart)){
    	 	having SUM(Amount)>=#{WinResultStart}
    	 	 @if(!isEmpty(WinResultEnd)){
    	 	 	and SUM(Amount)<=#{WinResultEnd}
    	      @}
    	   @} else if(!isEmpty(WinResultEnd)){
    	 	having SUM(Amount)<=#{WinResultEnd}
    	   @}
    	   ) t
    	  ,QPTreasureDB.dbo.GameScoreInfo as g with (nolock),[QPGameUserDB].[dbo].[AccountsInfo] as a with (nolock)
    	  where t.UserID=g.UserID and t.UserID=a.UserID        	  
11_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_11]
    	   where Prop_Id=1 and ChangeType_Id=1
    	   @if(!isEmpty(ServerId)){
    	 	and ServerId =#{ServerId}
    	   @}
    	   @if(!isEmpty(StartTime_dategt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) >= CONVERT(VARCHAR(100), #{StartTime_dategt}, 20)
    	  @}
    	  @if(!isEmpty(EndTime_datelt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) <= CONVERT(VARCHAR(100), #{EndTime_datelt}, 20)
    	  @}
    	  @if(!isEmpty(UserID)){
    	 	and User_Id =#{UserID}
    	   @}
    	   group by User_Id
    	   @if(!isEmpty(WinResultStart)){
    	 	having SUM(Amount)>=#{WinResultStart}
    	 	 @if(!isEmpty(WinResultEnd)){
    	 	 	and SUM(Amount)<=#{WinResultEnd}
    	      @}
    	   @} else if(!isEmpty(WinResultEnd)){
    	 	having SUM(Amount)<=#{WinResultEnd}
    	   @}
    	   ) t
    	  ,QPTreasureDB.dbo.GameScoreInfo as g with (nolock),[QPGameUserDB].[dbo].[AccountsInfo] as a with (nolock)
    	  where t.UserID=g.UserID and t.UserID=a.UserID 
    	  
15_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_15]
    	   where Prop_Id=1 and ChangeType_Id=1
    	   @if(!isEmpty(ServerId)){
    	 	and ServerId =#{ServerId}
    	   @}
    	   @if(!isEmpty(StartTime_dategt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) >= CONVERT(VARCHAR(100), #{StartTime_dategt}, 20)
    	  @}
    	  @if(!isEmpty(EndTime_datelt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) <= CONVERT(VARCHAR(100), #{EndTime_datelt}, 20)
    	  @}
    	  @if(!isEmpty(UserID)){
    	 	and User_Id =#{UserID}
    	   @}
    	   group by User_Id
    	   @if(!isEmpty(WinResultStart)){
    	 	having SUM(Amount)>=#{WinResultStart}
    	 	 @if(!isEmpty(WinResultEnd)){
    	 	 	and SUM(Amount)<=#{WinResultEnd}
    	      @}
    	   @} else if(!isEmpty(WinResultEnd)){
    	 	having SUM(Amount)<=#{WinResultEnd}
    	   @}
    	   ) t
    	  ,QPTreasureDB.dbo.GameScoreInfo as g with (nolock),[QPGameUserDB].[dbo].[AccountsInfo] as a with (nolock)
    	  where t.UserID=g.UserID and t.UserID=a.UserID    	     	  
16_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_16]
    	   where Prop_Id=1 and ChangeType_Id=1
    	   @if(!isEmpty(ServerId)){
    	 	and ServerId =#{ServerId}
    	   @}
    	   @if(!isEmpty(StartTime_dategt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) >= CONVERT(VARCHAR(100), #{StartTime_dategt}, 20)
    	  @}
    	  @if(!isEmpty(EndTime_datelt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) <= CONVERT(VARCHAR(100), #{EndTime_datelt}, 20)
    	  @}
    	  @if(!isEmpty(UserID)){
    	 	and User_Id =#{UserID}
    	   @}
    	   group by User_Id
    	   @if(!isEmpty(WinResultStart)){
    	 	having SUM(Amount)>=#{WinResultStart}
    	 	 @if(!isEmpty(WinResultEnd)){
    	 	 	and SUM(Amount)<=#{WinResultEnd}
    	      @}
    	   @} else if(!isEmpty(WinResultEnd)){
    	 	having SUM(Amount)<=#{WinResultEnd}
    	   @}
    	   ) t
    	  ,QPTreasureDB.dbo.GameScoreInfo as g with (nolock),[QPGameUserDB].[dbo].[AccountsInfo] as a with (nolock)
    	  where t.UserID=g.UserID and t.UserID=a.UserID
19_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_19]
    	   where Prop_Id=1 and ChangeType_Id=1
    	   @if(!isEmpty(ServerId)){
    	 	and ServerId =#{ServerId}
    	   @}
    	   @if(!isEmpty(StartTime_dategt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) >= CONVERT(VARCHAR(100), #{StartTime_dategt}, 20)
    	  @}
    	  @if(!isEmpty(EndTime_datelt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) <= CONVERT(VARCHAR(100), #{EndTime_datelt}, 20)
    	  @}
    	  @if(!isEmpty(UserID)){
    	 	and User_Id =#{UserID}
    	   @}
    	   group by User_Id
    	   @if(!isEmpty(WinResultStart)){
    	 	having SUM(Amount)>=#{WinResultStart}
    	 	 @if(!isEmpty(WinResultEnd)){
    	 	 	and SUM(Amount)<=#{WinResultEnd}
    	      @}
    	   @} else if(!isEmpty(WinResultEnd)){
    	 	having SUM(Amount)<=#{WinResultEnd}
    	   @}
    	   ) t
    	  ,QPTreasureDB.dbo.GameScoreInfo as g with (nolock),[QPGameUserDB].[dbo].[AccountsInfo] as a with (nolock)
    	  where t.UserID=g.UserID and t.UserID=a.UserID
22_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_22_1]
    	   where Prop_Id=1 and ChangeType_Id=1
    	   @if(!isEmpty(ServerId)){
    	 	and ServerId =#{ServerId}
    	   @}
    	   @if(!isEmpty(StartTime_dategt)){
    		 and Time_Id>=#{StartTime_dategt}
    	  @}
    	  @if(!isEmpty(EndTime_datelt)){
    		 and Time_Id<=#{EndTime_datelt}
    	  @}
    	  @if(!isEmpty(UserID)){
    	 	and User_Id =#{UserID}
    	   @}
    	   group by User_Id
    	   @if(!isEmpty(WinResultStart)){
    	 	having SUM(Amount)>=#{WinResultStart}
    	 	 @if(!isEmpty(WinResultEnd)){
    	 	 	and SUM(Amount)<=#{WinResultEnd}
    	      @}
    	   @} else if(!isEmpty(WinResultEnd)){
    	 	having SUM(Amount)<=#{WinResultEnd}
    	   @}
    	   ) t
    	  ,QPTreasureDB.dbo.GameScoreInfo as g with (nolock),[QPGameUserDB].[dbo].[AccountsInfo] as a with (nolock)
    	  where t.UserID=g.UserID and t.UserID=a.UserID
28_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_28]
    	   where Prop_Id=1 and ChangeType_Id=1
    	   @if(!isEmpty(ServerId)){
    	 	and ServerId =#{ServerId}
    	   @}
    	   @if(!isEmpty(StartTime_dategt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) >= CONVERT(VARCHAR(100), #{StartTime_dategt}, 20)
    	  @}
    	  @if(!isEmpty(EndTime_datelt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) <= CONVERT(VARCHAR(100), #{EndTime_datelt}, 20)
    	  @}
    	  @if(!isEmpty(UserID)){
    	 	and User_Id =#{UserID}
    	   @}
    	   group by User_Id
    	   @if(!isEmpty(WinResultStart)){
    	 	having SUM(Amount)>=#{WinResultStart}
    	 	 @if(!isEmpty(WinResultEnd)){
    	 	 	and SUM(Amount)<=#{WinResultEnd}
    	      @}
    	   @} else if(!isEmpty(WinResultEnd)){
    	 	having SUM(Amount)<=#{WinResultEnd}
    	   @}
    	   ) t
    	  ,QPTreasureDB.dbo.GameScoreInfo as g with (nolock),[QPGameUserDB].[dbo].[AccountsInfo] as a with (nolock)
    	  where t.UserID=g.UserID and t.UserID=a.UserID
29_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_29]
    	   where Prop_Id=1 and ChangeType_Id=1
    	   @if(!isEmpty(ServerId)){
    	 	and ServerId =#{ServerId}
    	   @}
    	   @if(!isEmpty(StartTime_dategt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) >= CONVERT(VARCHAR(100), #{StartTime_dategt}, 20)
    	  @}
    	  @if(!isEmpty(EndTime_datelt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) <= CONVERT(VARCHAR(100), #{EndTime_datelt}, 20)
    	  @}
    	  @if(!isEmpty(UserID)){
    	 	and User_Id =#{UserID}
    	   @}
    	   group by User_Id
    	   @if(!isEmpty(WinResultStart)){
    	 	having SUM(Amount)>=#{WinResultStart}
    	 	 @if(!isEmpty(WinResultEnd)){
    	 	 	and SUM(Amount)<=#{WinResultEnd}
    	      @}
    	   @} else if(!isEmpty(WinResultEnd)){
    	 	having SUM(Amount)<=#{WinResultEnd}
    	   @}
    	   ) t
    	  ,QPTreasureDB.dbo.GameScoreInfo as g with (nolock),[QPGameUserDB].[dbo].[AccountsInfo] as a with (nolock)
    	  where t.UserID=g.UserID and t.UserID=a.UserID

3_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_3]
    	   where Prop_Id=1 and ChangeType_Id=1
    	   @if(!isEmpty(ServerId)){
    	 	and ServerId =#{ServerId}
    	   @}
    	   @if(!isEmpty(StartTime_dategt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) >= CONVERT(VARCHAR(100), #{StartTime_dategt}, 20)
    	  @}
    	  @if(!isEmpty(EndTime_datelt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) <= CONVERT(VARCHAR(100), #{EndTime_datelt}, 20)
    	  @}
    	  @if(!isEmpty(UserID)){
    	 	and User_Id =#{UserID}
    	   @}
    	   group by User_Id
    	   @if(!isEmpty(WinResultStart)){
    	 	having SUM(Amount)>=#{WinResultStart}
    	 	 @if(!isEmpty(WinResultEnd)){
    	 	 	and SUM(Amount)<=#{WinResultEnd}
    	      @}
    	   @} else if(!isEmpty(WinResultEnd)){
    	 	having SUM(Amount)<=#{WinResultEnd}
    	   @}
    	   ) t
    	  ,QPTreasureDB.dbo.GameScoreInfo as g with (nolock),[QPGameUserDB].[dbo].[AccountsInfo] as a with (nolock)
    	  where t.UserID=g.UserID and t.UserID=a.UserID
32_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_32]
    	   where Prop_Id=1 and ChangeType_Id=1
    	   @if(!isEmpty(ServerId)){
    	 	and ServerId =#{ServerId}
    	   @}
    	   @if(!isEmpty(StartTime_dategt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) >= CONVERT(VARCHAR(100), #{StartTime_dategt}, 20)
    	  @}
    	  @if(!isEmpty(EndTime_datelt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) <= CONVERT(VARCHAR(100), #{EndTime_datelt}, 20)
    	  @}
    	  @if(!isEmpty(UserID)){
    	 	and User_Id =#{UserID}
    	   @}
    	   group by User_Id
    	   @if(!isEmpty(WinResultStart)){
    	 	having SUM(Amount)>=#{WinResultStart}
    	 	 @if(!isEmpty(WinResultEnd)){
    	 	 	and SUM(Amount)<=#{WinResultEnd}
    	      @}
    	   @} else if(!isEmpty(WinResultEnd)){
    	 	having SUM(Amount)<=#{WinResultEnd}
    	   @}
    	   ) t
    	  ,QPTreasureDB.dbo.GameScoreInfo as g with (nolock),[QPGameUserDB].[dbo].[AccountsInfo] as a with (nolock)
    	  where t.UserID=g.UserID and t.UserID=a.UserID
33_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_33]
    	   where Prop_Id=1 and ChangeType_Id=1
    	   @if(!isEmpty(ServerId)){
    	 	and ServerId =#{ServerId}
    	   @}
    	   @if(!isEmpty(StartTime_dategt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) >= CONVERT(VARCHAR(100), #{StartTime_dategt}, 20)
    	  @}
    	  @if(!isEmpty(EndTime_datelt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) <= CONVERT(VARCHAR(100), #{EndTime_datelt}, 20)
    	  @}
    	  @if(!isEmpty(UserID)){
    	 	and User_Id =#{UserID}
    	   @}
    	   group by User_Id
    	   @if(!isEmpty(WinResultStart)){
    	 	having SUM(Amount)>=#{WinResultStart}
    	 	 @if(!isEmpty(WinResultEnd)){
    	 	 	and SUM(Amount)<=#{WinResultEnd}
    	      @}
    	   @} else if(!isEmpty(WinResultEnd)){
    	 	having SUM(Amount)<=#{WinResultEnd}
    	   @}
    	   ) t
    	  ,QPTreasureDB.dbo.GameScoreInfo as g with (nolock),[QPGameUserDB].[dbo].[AccountsInfo] as a with (nolock)
    	  where t.UserID=g.UserID and t.UserID=a.UserID
35_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_35]
    	   where Prop_Id=1 and ChangeType_Id=1
    	   @if(!isEmpty(ServerId)){
    	 	and ServerId =#{ServerId}
    	   @}
    	   @if(!isEmpty(StartTime_dategt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) >= CONVERT(VARCHAR(100), #{StartTime_dategt}, 20)
    	  @}
    	  @if(!isEmpty(EndTime_datelt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) <= CONVERT(VARCHAR(100), #{EndTime_datelt}, 20)
    	  @}
    	  @if(!isEmpty(UserID)){
    	 	and User_Id =#{UserID}
    	   @}
    	   group by User_Id
    	   @if(!isEmpty(WinResultStart)){
    	 	having SUM(Amount)>=#{WinResultStart}
    	 	 @if(!isEmpty(WinResultEnd)){
    	 	 	and SUM(Amount)<=#{WinResultEnd}
    	      @}
    	   @} else if(!isEmpty(WinResultEnd)){
    	 	having SUM(Amount)<=#{WinResultEnd}
    	   @}
    	   ) t
    	  ,QPTreasureDB.dbo.GameScoreInfo as g with (nolock),[QPGameUserDB].[dbo].[AccountsInfo] as a with (nolock)
    	  where t.UserID=g.UserID and t.UserID=a.UserID
45_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_45]
    	   where Prop_Id=1 and ChangeType_Id=1
    	   @if(!isEmpty(ServerId)){
    	 	and ServerId =#{ServerId}
    	   @}
    	   @if(!isEmpty(StartTime_dategt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) >= CONVERT(VARCHAR(100), #{StartTime_dategt}, 20)
    	  @}
    	  @if(!isEmpty(EndTime_datelt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) <= CONVERT(VARCHAR(100), #{EndTime_datelt}, 20)
    	  @}
    	  @if(!isEmpty(UserID)){
    	 	and User_Id =#{UserID}
    	   @}
    	   group by User_Id
    	   @if(!isEmpty(WinResultStart)){
    	 	having SUM(Amount)>=#{WinResultStart}
    	 	 @if(!isEmpty(WinResultEnd)){
    	 	 	and SUM(Amount)<=#{WinResultEnd}
    	      @}
    	   @} else if(!isEmpty(WinResultEnd)){
    	 	having SUM(Amount)<=#{WinResultEnd}
    	   @}
    	   ) t
    	  ,QPTreasureDB.dbo.GameScoreInfo as g with (nolock),[QPGameUserDB].[dbo].[AccountsInfo] as a with (nolock)
    	  where t.UserID=g.UserID and t.UserID=a.UserID

51_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_51]
    	   where Prop_Id=1 and ChangeType_Id=1
    	   @if(!isEmpty(ServerId)){
    	 	and ServerId =#{ServerId}
    	   @}
    	   @if(!isEmpty(StartTime_dategt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) >= CONVERT(VARCHAR(100), #{StartTime_dategt}, 20)
    	  @}
    	  @if(!isEmpty(EndTime_datelt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) <= CONVERT(VARCHAR(100), #{EndTime_datelt}, 20)
    	  @}
    	  @if(!isEmpty(UserID)){
    	 	and User_Id =#{UserID}
    	   @}
    	   group by User_Id
    	   @if(!isEmpty(WinResultStart)){
    	 	having SUM(Amount)>=#{WinResultStart}
    	 	 @if(!isEmpty(WinResultEnd)){
    	 	 	and SUM(Amount)<=#{WinResultEnd}
    	      @}
    	   @} else if(!isEmpty(WinResultEnd)){
    	 	having SUM(Amount)<=#{WinResultEnd}
    	   @}
    	   ) t
    	  ,QPTreasureDB.dbo.GameScoreInfo as g with (nolock),[QPGameUserDB].[dbo].[AccountsInfo] as a with (nolock)
    	  where t.UserID=g.UserID and t.UserID=a.UserID       	  

53_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_53]
    	   where Prop_Id=1 and ChangeType_Id=1
    	   @if(!isEmpty(ServerId)){
    	 	and ServerId =#{ServerId}
    	   @}
    	   @if(!isEmpty(StartTime_dategt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) >= CONVERT(VARCHAR(100), #{StartTime_dategt}, 20)
    	  @}
    	  @if(!isEmpty(EndTime_datelt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) <= CONVERT(VARCHAR(100), #{EndTime_datelt}, 20)
    	  @}
    	  @if(!isEmpty(UserID)){
    	 	and User_Id =#{UserID}
    	   @}
    	   group by User_Id
    	   @if(!isEmpty(WinResultStart)){
    	 	having SUM(Amount)>=#{WinResultStart}
    	 	 @if(!isEmpty(WinResultEnd)){
    	 	 	and SUM(Amount)<=#{WinResultEnd}
    	      @}
    	   @} else if(!isEmpty(WinResultEnd)){
    	 	having SUM(Amount)<=#{WinResultEnd}
    	   @}
    	   ) t
    	  ,QPTreasureDB.dbo.GameScoreInfo as g with (nolock),[QPGameUserDB].[dbo].[AccountsInfo] as a with (nolock)
    	  where t.UserID=g.UserID and t.UserID=a.UserID
61_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_61]
    	   where Prop_Id=1 and ChangeType_Id=1
    	   @if(!isEmpty(ServerId)){
    	 	and ServerId =#{ServerId}
    	   @}
    	   @if(!isEmpty(StartTime_dategt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) >= CONVERT(VARCHAR(100), #{StartTime_dategt}, 20)
    	  @}
    	  @if(!isEmpty(EndTime_datelt)){
    		 and CONVERT(VARCHAR(100), LogTime, 20) <= CONVERT(VARCHAR(100), #{EndTime_datelt}, 20)
    	  @}
    	  @if(!isEmpty(UserID)){
    	 	and User_Id =#{UserID}
    	   @}
    	   group by User_Id
    	   @if(!isEmpty(WinResultStart)){
    	 	having SUM(Amount)>=#{WinResultStart}
    	 	 @if(!isEmpty(WinResultEnd)){
    	 	 	and SUM(Amount)<=#{WinResultEnd}
    	      @}
    	   @} else if(!isEmpty(WinResultEnd)){
    	 	having SUM(Amount)<=#{WinResultEnd}
    	   @}
    	   ) t
    	  ,QPTreasureDB.dbo.GameScoreInfo as g with (nolock),[QPGameUserDB].[dbo].[AccountsInfo] as a with (nolock)
    	  where t.UserID=g.UserID and t.UserID=a.UserID        	      	  