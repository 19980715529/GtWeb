home_list
===
	select id,CONVERT(VARCHAR(10), writedate, 23) writedate,totalActivity,tatolRegActivity,TouritsActivity,RecActivity
    from [QPGameRecordDB].[dbo].[DailyDataMonitorRecords] with (nolock)
	where 1=1
	  @if(!isEmpty(PlatformID)){
		 and clientType=#{PlatformID}
	  @}
	  @if(!isEmpty(startTime)){
		 and CONVERT(VARCHAR(100), writedate, 23) >= CONVERT(VARCHAR(100), #{startTime}, 23)
	  @}
	  @if(!isEmpty(endTime)){
		 and CONVERT(VARCHAR(100), writedate, 23) <= CONVERT(VARCHAR(100), #{endTime}, 23)
	  @}
	  @if(!isEmpty(orderBy)){
		 order by CONVERT(VARCHAR(100), writedate, 23) desc
	  @}else{
	  	order by CONVERT(VARCHAR(100), writedate, 23) asc
	  @}

day_list
===
	select id,totalActivity,tatolRegActivity,TouritsActivity,RecActivity,CONVERT(VARCHAR(100), writedate, 23) writedate 
    from [QPGameRecordDB].[dbo].[DailyDataMonitorRecords] with (nolock)
	where 1=1
	  @if(!isEmpty(PlatformID)){
		 and clientType=#{PlatformID}
	  @}
	  @if(!isEmpty(startTime)){
		 and CONVERT(VARCHAR(100), writedate, 23) >= CONVERT(VARCHAR(100), #{startTime}, 23)
	  @}
	  @if(!isEmpty(endTime)){
		 and CONVERT(VARCHAR(100), writedate, 23) <= CONVERT(VARCHAR(100), #{endTime}, 23)
	  @}
	  @if(!isEmpty(orderBy)){
		 order by CONVERT(VARCHAR(100), writedate, 23) desc
	  @}else{
	  	order by CONVERT(VARCHAR(100), writedate, 23) asc
	  @}

week_list
===
	select SUM(totalActivity) totalActivity,SUM(tatolRegActivity) tatolRegActivity,SUM(TouritsActivity) TouritsActivity,
    SUM(RecActivity) RecActivity,datepart(Wk,writedate) Week,YEAR(writedate) Year 
	  from [QPGameRecordDB].[dbo].[DailyDataMonitorRecords] with (nolock)
		where 1=1
	  @if(!isEmpty(startTime)){
		 and CONVERT(VARCHAR(100), writedate, 23) >= CONVERT(VARCHAR(100), #{startTime}, 23)
	  @}
	  @if(!isEmpty(endTime)){
		 and CONVERT(VARCHAR(100), writedate, 23) <= CONVERT(VARCHAR(100), #{endTime}, 23)
	  @}
	  group by YEAR(writedate),datepart(Wk,writedate)

month_list
===
	  select SUM(totalActivity) totalActivity,SUM(tatolRegActivity) tatolRegActivity,SUM(TouritsActivity) TouritsActivity,
    SUM(RecActivity) RecActivity,datepart(month,writedate) Month,YEAR(writedate) Year 
		from [QPGameRecordDB].[dbo].[DailyDataMonitorRecords] with (nolock)
	  where 1=1
	  @if(!isEmpty(startTime)){
		 and CONVERT(VARCHAR(100), writedate, 23) >= CONVERT(VARCHAR(100), #{startTime}, 23)
	  @}
	  @if(!isEmpty(endTime)){
		 and CONVERT(VARCHAR(100), writedate, 23) <= CONVERT(VARCHAR(100), #{endTime}, 23)
	  @}
	 group by YEAR(writedate),datepart(month,writedate)