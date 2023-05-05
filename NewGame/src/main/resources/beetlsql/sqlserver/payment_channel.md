all_list
===
    SELECT p.*,c.pid,c.name,c.type,c.mcName,c.channelName,c.code FROM paymentChannel as p INNER JOIN  Channel_copy1 as c ON p.cid=c.id
    where 1=1
    @if(!isEmpty(clientType)){
        and p.clientType=#{clientType}
    @}
    @if(!isEmpty(type)){
        and c.type=#{type}
    @}

find_channel
===
    SELECT id,mcName+'-'+channelName+'-'+(CASE type WHEN 0 THEN '充值' ELSE '兑换' END) as name FROM CHANNEL_copy1 WHERE pid!=0 AND isOpen=1