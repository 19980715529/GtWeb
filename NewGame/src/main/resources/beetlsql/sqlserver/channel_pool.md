all_list
===
    select id,(select mcName from Pay_MCPool a where a.id=b.mcId) mcName,name,
    (select cname from Pay_Channel as a where a.id=b.cid) cname,type,isOpen,code,currencyType,collectRate,paymentRate,fee,minLimit,maxLimit,isLabel,
    channelTax,sort,exchangeRatio,giveRatio,winConf,money
    from Pay_ChannelPool as b where 1=1
    @if(!isEmpty(type)){
        and b.type=#{type}
    @}
    @if(!isEmpty(cid)){
        and b.cid=#{cid}
    @}
mc_list
===
    select id,mcName from Pay_MCPool

mc_list1
===
    select mcId ID,mcName from Pay_MCPool

query_mc_list
===
    SELECT (select id FROM Pay_MCPool as c where c.id=b.mcId) id,b.name 
    from Pay_ChannelList as a JOIN Pay_ChannelPool as b on a.chPoolId=b.id 
    WHERE clientType=#{clientType} and chName=#{chName} AND a.isOpen=1 and b.isOpen=1 AND b.type=1