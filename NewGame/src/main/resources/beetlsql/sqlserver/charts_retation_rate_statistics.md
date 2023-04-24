new_list
===
	  select 
	  '平均' as StatisticsDate,
	  '-1' as logincount,
	  isnull(Convert(decimal(10,5),(lcl1/(case when CountNum=0 then 1 else CountNum end))*100),0) lcl1,
	  isnull(Convert(decimal(10,5),(lcl2/(case when CountNum=0 then 1 else CountNum end))*100),0) lcl2,
	  isnull(Convert(decimal(10,5),(lcl3/(case when CountNum=0 then 1 else CountNum end))*100),0) lcl3,
	  isnull(Convert(decimal(10,5),(lcl7/(case when CountNum=0 then 1 else CountNum end))*100),0) lcl7,
	  isnull(Convert(decimal(10,5),(lcl15/(case when CountNum=0 then 1 else CountNum end))*100),0) lcl15,
	  isnull(Convert(decimal(10,5),(lcl30/(case when CountNum=0 then 1 else CountNum end))*100),0) lcl30
	  from
	  (select SUM(lcl1) lcl1,SUM(lcl2) lcl2,SUM(lcl3) lcl3,
	  SUM(lcl7) lcl7,SUM(lcl15) lcl15,SUM(lcl30) lcl30,COUNT(r_id) CountNum 
	  FROM [QPGameRecordDB].[dbo].[RetentionRateRecord1]
	  @if(!isEmpty(PlatformID)){
		 where ClientType = #{PlatformID}
	  @}
	  @if(!isEmpty(startTime)){
	  	and DATEDIFF(DAY,writedate,#{startTime})<=0
	  @}
	  @if(!isEmpty(endTime)){
		 and DATEDIFF(DAY,writedate,#{endTime})>=0
	  @}
	  ) t
	  union all
	  select CONVERT(VARCHAR(100), writedate, 23) StatisticsDate,logincount,lcl1*100,lcl2*100,lcl3*100,lcl7*100,lcl15*100,lcl30*100 
	  FROM [QPGameRecordDB].[dbo].[RetentionRateRecord1]
	   @if(!isEmpty(PlatformID)){
		 where ClientType = #{PlatformID}
	  @}
	  @if(!isEmpty(startTime)){
	  	and DATEDIFF(DAY,writedate,#{startTime})<=0
	  @}
	  @if(!isEmpty(endTime)){
		 and DATEDIFF(DAY,writedate,#{endTime})>=0
	  @}
	  order by StatisticsDate desc
  
new_list_bak
===
	select *,CONVERT(VARCHAR(100), writedate, 23) StatisticsDate from [QPGameRecordDB].[dbo].[RetentionRateRecord1]
	where 1=1
	  @if(!isEmpty(PlatformID)){
		 and ClientType = #{PlatformID}
	  @}
	  @if(!isEmpty(startTime)){
	  	and DATEDIFF(DAY,writedate,#{startTime})<=0
	  @}
	  @if(!isEmpty(endTime)){
		 and DATEDIFF(DAY,writedate,#{endTime})>=0
	  @}
	  order by writedate desc


new_list1
===
	  select 
	  '平均' as StatisticsDate,
	  '-1' as logincount,
	  isnull(Convert(decimal(10,5),(lcl1/(case when CountNum=0 then 1 else CountNum end))*100),0) lcl1,
	  isnull(Convert(decimal(10,5),(lcl2/(case when CountNum=0 then 1 else CountNum end))*100),0) lcl2,
	  isnull(Convert(decimal(10,5),(lcl3/(case when CountNum=0 then 1 else CountNum end))*100),0) lcl3,
	  isnull(Convert(decimal(10,5),(lcl7/(case when CountNum=0 then 1 else CountNum end))*100),0) lcl7,
	  isnull(Convert(decimal(10,5),(lcl15/(case when CountNum=0 then 1 else CountNum end))*100),0) lcl15,
	  isnull(Convert(decimal(10,5),(lcl30/(case when CountNum=0 then 1 else CountNum end))*100),0) lcl30
	  from
	  (select SUM(lcl1) lcl1,SUM(lcl2) lcl2,SUM(lcl3) lcl3,
	  SUM(lcl7) lcl7,SUM(lcl15) lcl15,SUM(lcl30) lcl30,COUNT(r_id) CountNum 
	  FROM [QPGameRecordDB].[dbo].[RechargeRetainRecord]
	  @if(!isEmpty(PlatformID)){
		 where ClientType = #{PlatformID}
	  @}
	  @if(!isEmpty(startTime)){
	  	and DATEDIFF(DAY,writedate,#{startTime})<=0
	  @}
	  @if(!isEmpty(endTime)){
		 and DATEDIFF(DAY,writedate,#{endTime})>=0
	  @}
	  ) t
	  union all
	  select CONVERT(VARCHAR(100), writedate, 23) StatisticsDate,logincount,lcl1*100,lcl2*100,lcl3*100,lcl7*100,lcl15*100,lcl30*100 
	  FROM [QPGameRecordDB].[dbo].[RechargeRetainRecord]
	   @if(!isEmpty(PlatformID)){
		 where ClientType = #{PlatformID}
	  @}
	  @if(!isEmpty(startTime)){
	  	and DATEDIFF(DAY,writedate,#{startTime})<=0
	  @}
	  @if(!isEmpty(endTime)){
		 and DATEDIFF(DAY,writedate,#{endTime})>=0
	  @}
	  order by StatisticsDate desc


new_list2
===
	  select 
	  '平均' as StatisticsDate,
	  '-1' as logincount,
	  isnull(Convert(decimal(10,5),(lcl1/(case when CountNum=0 then 1 else CountNum end))*100),0) lcl1,
	  isnull(Convert(decimal(10,5),(lcl2/(case when CountNum=0 then 1 else CountNum end))*100),0) lcl2,
	  isnull(Convert(decimal(10,5),(lcl3/(case when CountNum=0 then 1 else CountNum end))*100),0) lcl3,
	  isnull(Convert(decimal(10,5),(lcl7/(case when CountNum=0 then 1 else CountNum end))*100),0) lcl7,
	  isnull(Convert(decimal(10,5),(lcl15/(case when CountNum=0 then 1 else CountNum end))*100),0) lcl15,
	  isnull(Convert(decimal(10,5),(lcl30/(case when CountNum=0 then 1 else CountNum end))*100),0) lcl30
	  from
	  (select SUM(lcl1) lcl1,SUM(lcl2) lcl2,SUM(lcl3) lcl3,
	  SUM(lcl7) lcl7,SUM(lcl15) lcl15,SUM(lcl30) lcl30,COUNT(r_id) CountNum 
	  FROM [QPGameRecordDB].[dbo].[RegisterRetainRecord]
	  @if(!isEmpty(PlatformID)){
		 where ClientType = #{PlatformID}
	  @}
	  @if(!isEmpty(startTime)){
	  	and DATEDIFF(DAY,writedate,#{startTime})<=0
	  @}
	  @if(!isEmpty(endTime)){
		 and DATEDIFF(DAY,writedate,#{endTime})>=0
	  @}
	  ) t
	  union all
	  select CONVERT(VARCHAR(100), writedate, 23) StatisticsDate,logincount,lcl1*100,lcl2*100,lcl3*100,lcl7*100,lcl15*100,lcl30*100 
	  FROM [QPGameRecordDB].[dbo].[RegisterRetainRecord]
	   @if(!isEmpty(PlatformID)){
		 where ClientType = #{PlatformID}
	  @}
	  @if(!isEmpty(startTime)){
	  	and DATEDIFF(DAY,writedate,#{startTime})<=0
	  @}
	  @if(!isEmpty(endTime)){
		 and DATEDIFF(DAY,writedate,#{endTime})>=0
	  @}
	  order by StatisticsDate desc