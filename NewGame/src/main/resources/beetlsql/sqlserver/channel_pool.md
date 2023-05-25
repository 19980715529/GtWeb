all_list
===
    select id,(select mcName from Pay_MCPool a where a.id=b.mcId) mcName,name,
    (select cname from Pay_Channel as a where a.id=b.cid) cname,type,isOpen,param,currencyType,formalitiesCost,fee
    from Pay_ChannelPool as b
mc_list
===
    select id,mcName from Pay_MCPool