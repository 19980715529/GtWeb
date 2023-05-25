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
5_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_5]
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

6_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_6]
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
8_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_8]
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
9_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_9]
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
13_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_13]
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
14_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_14]
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
17_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_17]
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
18_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_18]
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
20_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_20]
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
21_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_21]
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
22_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_22]
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
22_list_
===
      select g.UserID,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from QPTreasureDB.dbo.GameScoreInfo as g with (nolock),[QPGameUserDB].[dbo].[AccountsInfo] as a with (nolock)
    	  where g.UserID=a.UserID
			and g.UserID in (#{join(ids)})
    	  
25_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_25]
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
27_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_27]
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
39_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_39]
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
40_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_40]
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
41_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_41]
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
42_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_42]
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
43_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_43]
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
44_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_44]
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
49_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_49]
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
52_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_52]
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

52_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_52]
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

54_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_54]
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

55_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_55]
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
57_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_57]
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

58_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_58]
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
59_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_59]
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

62_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_62]
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


63_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_63]
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


65_list
===
      select t.UserID,t.DayWaste,g.RealScore TotalWaste,g.CheatRate CheatingRateSet,g.LimitScore CheatingScoreSet,g.BloodScore RemainScore,g.js_BussniessCount RcvScore,g.out_BussniessCount SendScore,a.GameID
    	  ,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName
    	  ,a.LastLogonDate,a.BeautifulID
    	  from
    	  (select SUM(Amount) DayWaste,User_Id UserID FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_65]
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