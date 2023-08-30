new_list
===
	  select czmoney_ios IOS,czmoney_and Android,
	  czcount_ios IOSCount,czcount_and AndroidCount,
	  czrc_ios IOSUser,czrc_and AndroidUser,
	  (czmoney_ios+czmoney_and+Gm_Money+pcczmoney) TAmount,
	  (czcount_ios+czcount_and+pcczcount) TCount,
	  (czrc_ios+czrc_and+pcczrc) TUser,
	  Gm_Money, pcczmoney,pcczcount,pcczrc,
	  CONVERT(VARCHAR(100), writedate, 23) CollectDate 
	  from [QPGameRecordDB].[dbo].[MonitorRecordByDay]
	  where 1=1
	  @if(!isEmpty(startTime)){
		 and CONVERT(VARCHAR(100), writedate, 23) >= CONVERT(VARCHAR(100), #{startTime}, 23)
	  @}
	  @if(!isEmpty(endTime)){
		 and CONVERT(VARCHAR(100), writedate, 23) <= CONVERT(VARCHAR(100), #{endTime}, 23)
	  @}
	  @if(!isEmpty(PlatformID)){
	 	and clientType =#{PlatformID}
	  @}
	  order by writedate desc

new_ARPU_list
===
	  select regcount,(czmoney_ios+czmoney_and+Gm_Money+pcczmoney) TAmount ,CONVERT(VARCHAR(100), writedate, 23) as writedate,Convert(decimal(10,3),(case when regcount=0 then 0 else ((czmoney_ios+czmoney_and+Gm_Money+pcczmoney)/regcount) end )) ARPU 
	  FROM [QPGameRecordDB].[dbo].[MonitorRecordByDay]
	  where 1=1
	  @if(!isEmpty(startTime)){
		 and CONVERT(VARCHAR(100), writedate, 23) >= CONVERT(VARCHAR(100), #{startTime}, 23)
	  @}
	  @if(!isEmpty(endTime)){
		 and CONVERT(VARCHAR(100), writedate, 23) <= CONVERT(VARCHAR(100), #{endTime}, 23)
	  @}
	   @if(!isEmpty(PlatformID)){
	 	and clientType =#{PlatformID}
	  @}
	  order by writedate desc

day_recharge
===
	  select SUM(MoneyRMB) MoneyRMB from
	  (select isnull(SUM(UnitRMB),0) MoneyRMB from [QPGameUserDB].[dbo].[AA_Recharge_TotalRecord]
	  where DATEDIFF(day,GETDATE(),LogTime)=0
	  @if(!isEmpty(PlatformID)){
	   and User_Id in (select UserID from [QPGameUserDB].[dbo].[AccountsInfo] 
	   where ClientType=#{PlatformID})
	  @}
	  union all
	  select isnull(SUM(buymoney),0) MoneyRMB from [QPGameUserDB].[dbo].[UserByScoreByGM] 
	  where DATEDIFF(day,GETDATE(),writedate)=0
	   @if(!isEmpty(PlatformID)){
	   and [userid] in (select UserID from [QPGameUserDB].[dbo].[AccountsInfo] 
	   where ClientType=#{PlatformID})
	  @}
	  ) t

new_list1
===
        select id,CONVERT(VARCHAR(10), writedate, 23) writedate,recAmount,newRecAmount,recNum,newRecNum,recCount,newRecCount,excAmount,excNum,excCount,RE,
        (case when (recAmount=0) then '0.000' else CONVERT(decimal(18,3),(cast((recAmount-excAmount) as FLOAT)/cast(recAmount as FLOAT))) end) as Proportion
        from [QPGameRecordDB].[dbo].[DailyDataMonitorRecords]
        where 1=1
        @if(!isEmpty(startTime)){
            and CONVERT(VARCHAR(100), writedate, 23) >= CONVERT(VARCHAR(100), #{startTime}, 23)
        @}
        @if(!isEmpty(endTime)){
            and CONVERT(VARCHAR(100), writedate, 23) <= CONVERT(VARCHAR(100), #{endTime}, 23)
        @}
        @if(!isEmpty(PlatformID)){
            and clientType =#{PlatformID}
        @}
        order by writedate desc

new_ARPU_list1
===
	  select id,CONVERT(VARCHAR(10), writedate, 23) writedate,recAmount,newRecAmount,recNum,newRecNum,ARPU,newArpu,totalActivity
	  FROM [QPGameRecordDB].[dbo].[DailyDataMonitorRecords]
	  where 1=1
	  @if(!isEmpty(startTime)){
		 and CONVERT(VARCHAR(100), writedate, 23) >= CONVERT(VARCHAR(100), #{startTime}, 23)
	  @}
	  @if(!isEmpty(endTime)){
		 and CONVERT(VARCHAR(100), writedate, 23) <= CONVERT(VARCHAR(100), #{endTime}, 23)
	  @}
	   @if(!isEmpty(PlatformID)){
	 	and clientType =#{PlatformID}
	  @}
	  order by writedate desc