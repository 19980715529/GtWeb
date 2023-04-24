new_list
===
	select CONVERT(VARCHAR(100), writedate, 23) as ApplyDate,totalscore TGold,sendscore TPresentScore,czscore TCurrency 
	FROM [QPGameRecordDB].[dbo].[TotalScoreRecord]
	where 1=1
	@if(!isEmpty(startTime)){
	  and DATEDIFF(DAY,writedate,#{startTime})<=0
	@}
	@if(!isEmpty(endTime)){
	  and DATEDIFF(DAY,writedate,#{endTime})>=0
	@}
	@if(!isEmpty(PlatformID)){
	  and clientType=#{PlatformID}
	@}
	order by writedate desc

new_coupon_list
===	 
	select CONVERT(VARCHAR(100), writedate, 23) as  ApplyDate,totalprizes TCoupon,sendprizes TPresentScore,dhprizes TTreasureScore 
	FROM [QPGameRecordDB].[dbo].[TotalScoreRecord]
	where 1=1
	@if(!isEmpty(startTime)){
	  and DATEDIFF(DAY,writedate,#{startTime})<=0
	@}
	@if(!isEmpty(endTime)){
	  and DATEDIFF(DAY,writedate,#{endTime})>=0
	@}
	@if(!isEmpty(PlatformID)){
	  and clientType=#{PlatformID}
	@} 
	order by writedate desc