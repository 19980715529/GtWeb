new_list
===
	  SELECT CONVERT(VARCHAR(100), writedate, 23) CollectDate ,(regcount) RegisterCount ,(iosreg) IOSReg ,(andreg) AndReg,(pcregcount) PCReg,
            (regcount-dlfj_ios-dlfj_and-pcfj) UnEnterCount
      	  ,Convert(decimal(10,3),(cast((regcount-dlfj_ios-dlfj_and-pcfj) as float)/(case when (regcount)=0 then 1 else (regcount) end))) Rate
      	 ,b.NewUserUpman,b.NewUserDownman
      	  FROM [QPGameRecordDB].[dbo].[MonitorRecordByDay] a,[ReportDB].[Log].[UpDownScore] b
      	  where a.writedate = b.RecordDate
	  @if(!isEmpty(StartTime_datelt)){
	  	and DATEDIFF(day, #{StartTime_datelt}, writedate)>=0
	  @}
	  @if(!isEmpty(EndTime_dategt)){
	  	and DATEDIFF(day, #{EndTime_dategt}, writedate)<=0
	  @}
	  @if(!isEmpty(PlatformID)){
		 and clientType=#{PlatformID}
	  @}
	  @if(!isEmpty(orderBy)){
		 order by CollectDate asc
	  @}else {
		  order by CollectDate desc
	  @}
	  
new_package_list
===
	SELECT (SELECT name FROM login.dbo.ClientPos where clientType=a.ClientType) as AccountTypeName,
	COUNT(userid) AS RegisterCount 
    FROM QPGameUserDB.dbo.AccountsInfo a
	  where isRobit=0
	  @if(!isEmpty(StartTime_datelt)){
	  	and DATEDIFF(day, #{StartTime_datelt}, RegisterDate)>=0
	  @}
	  @if(!isEmpty(EndTime_dategt)){
	  	and DATEDIFF(day, #{EndTime_dategt}, RegisterDate)<=0
	  @}
	  GROUP BY ClientType
	  order by COUNT(userid) desc	

day_reg
===
	SELECT COUNT(userid) AS RegisterCount FROM QPGameUserDB.dbo.AccountsInfo a
	where isRobit=0 and DATEDIFF(day, getdate(), RegisterDate)=0
	@if(!isEmpty(PlatformID)){
		and clientType=#{PlatformID}
	@}

new_list1
===
	  SELECT id,CONVERT(VARCHAR(10), writedate, 120) writedate,totalNewUser,totalNewRec,totalTourist,newRecUser,newExcUser,notRoom,drainCount,undrainCount 
        FROM [QPGameRecordDB].[dbo].[DailyDataMonitorRecords] where 1=1
	  @if(!isEmpty(StartTime_datelt)){
	  	and DATEDIFF(day, #{StartTime_datelt}, writedate)>=0
	  @}
	  @if(!isEmpty(EndTime_dategt)){
	  	and DATEDIFF(day, #{EndTime_dategt}, writedate)<=0
	  @}
	  @if(!isEmpty(PlatformID)){
		 and clientType=#{PlatformID}
	  @}
	  @if(!isEmpty(orderBy)){
		 order by writedate asc
	  @}else {
		  order by writedate desc
	  @}

new_package_list1
===
	select c.name AccountTypeName,
    (SELECT COUNT(userid) FROM QPGameUserDB.dbo.AccountsInfo a where isRobit=0 and a.clientType=c.clientType
	  @if(!isEmpty(StartTime_datelt)){
	  	and DATEDIFF(day, #{StartTime_datelt}, a.RegisterDate)>=0
	  @}
	  @if(!isEmpty(EndTime_dategt)){
	  	and DATEDIFF(day, #{EndTime_dategt}, a.RegisterDate)<=0
	  @}
    ) as RegisterCount,
    (SELECT COUNT(userid) FROM QPGameUserDB.dbo.AccountsInfo a where isRobit=0 and a.clientType=c.clientType and a.BindPhone!=''
	  @if(!isEmpty(StartTime_datelt)){
	  	and DATEDIFF(day, #{StartTime_datelt}, a.RegisterDate)>=0
	  @}
	  @if(!isEmpty(EndTime_dategt)){
	  	and DATEDIFF(day, #{EndTime_dategt}, a.RegisterDate)<=0
	  @}
    ) as bindCount,
    (SELECT COUNT(userid) FROM QPGameUserDB.dbo.AccountsInfo a where isRobit=0 and a.clientType=c.clientType and a.BindPhone=''
	  @if(!isEmpty(StartTime_datelt)){
	  	and DATEDIFF(day, #{StartTime_datelt}, a.RegisterDate)>=0
	  @}
	  @if(!isEmpty(EndTime_dategt)){
	  	and DATEDIFF(day, #{EndTime_dategt}, a.RegisterDate)<=0
	  @}
    ) as touristCount,
    (SELECT COUNT(userid) FROM QPGameUserDB.dbo.AccountsInfo a where isRobit=0 and a.clientType=c.clientType and a.IsDrain=0
	  @if(!isEmpty(StartTime_datelt)){
	  	and DATEDIFF(day, #{StartTime_datelt}, a.RegisterDate)>=0
	  @}
	  @if(!isEmpty(EndTime_dategt)){
	  	and DATEDIFF(day, #{EndTime_dategt}, a.RegisterDate)<=0
	  @}
    ) as undrainCount,
    (SELECT COUNT(userid) FROM QPGameUserDB.dbo.AccountsInfo a where isRobit=0 and a.clientType=c.clientType and a.IsDrain=1
	  @if(!isEmpty(StartTime_datelt)){
	  	and DATEDIFF(day, #{StartTime_datelt}, a.RegisterDate)>=0
	  @}
	  @if(!isEmpty(EndTime_dategt)){
	  	and DATEDIFF(day, #{EndTime_dategt}, a.RegisterDate)<=0
	  @}
    ) as drainCount
    from login.dbo.ClientPos as c