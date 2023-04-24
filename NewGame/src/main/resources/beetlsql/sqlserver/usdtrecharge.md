all_list
===
    select * from [RYPlatformManagerDB].[dbo].[Recharge_records] as r where r.channelPid=17
    @if(!isEmpty(userId)){
	    and r.userId =#{userId}
	@}
    @if(!isEmpty(orderNum)){
	    and r.orderNumber =#{orderNum}
	@}
    @if(!isEmpty(orderStatus)){
        and r.orderStatus =#{orderStatus}
    @}
    @if(!isEmpty(start_money)){
	 and r.topUpAmount >=#{start_money}
	@}
    @if(!isEmpty(end_money)){
	 and r.topUpAmount <=#{end_money}
	@}
    @if(!isEmpty(createTime)){
		and DATEDIFF(DAY,r.createTime,#{createTime})<=0
	@}
	@if(!isEmpty(createTime)){
        and DATEDIFF(DAY,r.createTime,#{endTime})>=0
	@}