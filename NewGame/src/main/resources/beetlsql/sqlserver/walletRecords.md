all_list
===
    select id,channelName,(select a.mcName from Pay_MCPool as a where a.mcId=b.mcId) mcName,collectRate,paymentRate,payFee,collectAmount,collectFee,sucNum,paymentAmount,paymentFee,
    paymentNum,netProfit,totalFee,collectNum,clientType, CONVERT(VARCHAR(10), createTime, 23) createTime
    from Pay_walletRecords as b where 1=1
    @if(!isEmpty(clientType)){
        and clientType=#{clientType}
    @}
    @if(!isEmpty(startTime)){
        and CONVERT(VARCHAR(100), createTime, 23) >= CONVERT(VARCHAR(100), #{startTime}, 23)
    @}
    @if(!isEmpty(endTime)){
        and CONVERT(VARCHAR(100), createTime, 23) <= CONVERT(VARCHAR(100), #{endTime}, 23)
    @}