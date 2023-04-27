home_list
===
	select dayqhy_ios IOS,dayqhy_and Android,pcdayqhy PC,(dayqhy_ios+dayqhy_and+pcdayqhy) Total, CONVERT(VARCHAR(100), writedate, 23) CollectDate from [QPGameRecordDB].[dbo].[MonitorRecordByDay] with (nolock)
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
	select dayhy_ios IOS,(dayhy_and) Android,(pcdayhy) PC,(pcdayhy+dayhy_ios+dayhy_and) Total,CONVERT(VARCHAR(100), writedate, 23) CollectDate from [QPGameRecordDB].[dbo].[MonitorRecordByDay] with (nolock)
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
	select SUM(weekhy_ios) IOS,SUM(week_and) Android,SUM(pcweekhy) PC,datepart(Wk,writedate) Week,YEAR(writedate) Year 
	  from [QPGameRecordDB].[dbo].[MonitorRecordByDay] with (nolock)
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
	  select SUM(monthhy_ios) IOS,SUM(monthhy_and) Android,datepart(month,writedate) Month,YEAR(writedate) Year 
		from [QPGameRecordDB].[dbo].[MonitorRecordByDay] with (nolock)
	  where 1=1
	  @if(!isEmpty(startTime)){
		 and CONVERT(VARCHAR(100), writedate, 23) >= CONVERT(VARCHAR(100), #{startTime}, 23)
	  @}
	  @if(!isEmpty(endTime)){
		 and CONVERT(VARCHAR(100), writedate, 23) <= CONVERT(VARCHAR(100), #{endTime}, 23)
	  @}
	 group by YEAR(writedate),datepart(month,writedate)