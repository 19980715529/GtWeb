all_list
===
    SELECT p.*,(select mcName from Pay_MCPool a where a.id=b.mcId) mcName,(select cname from Pay_Channel a where a.id=b.cid) cname,
    b.currencyType,b.formalitiesCost,b.type,b.name
    FROM Pay_ChannelList p join Pay_ChannelPool b on p.chPoolId=b.id
    where 1=1
    @if(!isEmpty(clientType)){
        and p.clientType=#{clientType}
    @}
    @if(!isEmpty(type)){
        and b.type=#{type}
    @}

find_channel
===
    select a.id,(SELECT mcName from Pay_MCPool b where a.mcId=b.id)+'-'+(SELECT cname FROM Pay_Channel c WHERE a.cid=c.id)+'-'+(CASE a.type WHEN 0 THEN '充值' ELSE '兑换' END) name 
    from Pay_ChannelPool a
find_one
===
    select * from Pay_ChannelList where 1=1
    @if(!isEmpty(id)){
        and p.id=#{id}
    @}

new_list
===
    SELECT a.id,a.chPoolId pid,minLimit min,MaxLimit max,channelTax channelTaxRate,exchangeRatio goldProportion,giveRatio give,winConf,currencyType unit,a.fee,name,
    (select mcName FROM Pay_MCPool c WHERE c.id=b.cid) mcName,isLabel
    from Pay_ChannelList a JOIN Pay_ChannelPool b ON a.chPoolId=b.id where b.type=#{type} and clientType=#{clientType} and b.cid=#{cid} and a.isOpen=1 and b.isOpen=1 ORDER BY sort

param_max
===
    SELECT ISNULL(MAX(minLimit), 0) min,ISNULL(MAX(maxLimit), 0) max,ISNULL(MAX(channelTax), 0) channelTaxRate,
    ISNULL(MAX(exchangeRatio), 0) goldProportion,ISNULL(MAX(winConf), 0) winConf,ISNULL(MAX(a.fee), 0) fee
    FROM Pay_ChannelList a JOIN Pay_ChannelPool b ON a.chPoolId = b.id WHERE a.isOpen=1 and b.isOpen=1
    @if(!isEmpty(cid)){
        and b.cid=#{cid}
    @}
    @if(!isEmpty(type)){
        and type=#{type}
    @}
    @if(!isEmpty(clientType)){
        and clientType=#{clientType}
    @}

param_one
===
    SELECT b.currencyType unit,b.name,(select mcName from Pay_MCPool a where a.id=b.mcId) mcName,isLabel
    FROM Pay_ChannelList a JOIN Pay_ChannelPool b ON a.chPoolId = b.id WHERE a.isOpen=1 and b.isOpen=1
    @if(!isEmpty(cid)){
        and b.cid=#{cid}
    @}
    @if(!isEmpty(type)){
        and type=#{type}
    @}
    @if(!isEmpty(clientType)){
        and clientType=#{clientType}
    @}