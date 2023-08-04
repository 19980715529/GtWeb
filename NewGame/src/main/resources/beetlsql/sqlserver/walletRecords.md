all_list
===
    SELECT 
    (SELECT top 1 collectRate FROM Pay_ChannelPool as e JOIN Pay_Channel as f on e.cid=f.id WHERE f.cname=b.channelName AND e.type=1 AND e.mcId=1 AND e.isDel=0) collectRate,
    (SELECT top 1 paymentRate FROM Pay_ChannelPool as e JOIN Pay_Channel as f on e.cid=f.id WHERE f.cname=b.channelName AND e.type=1 AND e.mcId=1 AND e.isDel=0) paymentRate,
    (SELECT top 1 fee FROM Pay_ChannelPool as e JOIN Pay_Channel as f on e.cid=f.id WHERE f.cname=b.channelName AND e.type=1 AND e.mcId=1 AND e.isDel=0) payFee,
    channelName,(select a.mcName from Pay_MCPool as a where a.mcId=b.mcId) mcName, 
    SUM(collectAmount) collectAmount,SUM(collectFee) collectFee,SUM(sucNum) sucNum,
    SUM(paymentAmount) paymentAmount,SUM(paymentFee) paymentFee,SUM(paymentNum) paymentNum,
    SUM(netProfit) netProfit,SUM(totalFee) totalFee,SUM(collectNum) collectNum
    FROM Pay_walletRecords as b where 1=1
    @if(!isEmpty(clientType)){
        and b.clientType=#{clientType}
    @}
    @if(!isEmpty(startTime)){
        and CONVERT(VARCHAR(100), b.createTime, 23) >= CONVERT(VARCHAR(100), #{startTime}, 23)
    @}
    @if(!isEmpty(endTime)){
        and CONVERT(VARCHAR(100), b.createTime, 23) <= CONVERT(VARCHAR(100), #{endTime}, 23)
    @}
    GROUP BY channelName,mcId


all_list1
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