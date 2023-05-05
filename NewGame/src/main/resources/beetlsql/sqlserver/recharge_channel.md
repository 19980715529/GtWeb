all_list
===
    SELECT * FROM [RYPlatformManagerDB].[dbo].[Channel] where pid!=0
    @if(!isEmpty(type)){
	  and type=#{type}
	@}
one_channel
===
    select * FROM [RYPlatformManagerDB].[dbo].[Channel] where id =#{id} and type = 0
by_id_one_channel
===
    select * FROM [RYPlatformManagerDB].[dbo].[Channel] where id =#{id}

typename_list
===
    select id,name,pid,min,max,channelTaxRate,sort,give,fee,goldProportion,isLabel FROM [RYPlatformManagerDB].[dbo].[TypeName] where pid=#{id} and type=#{type} and isOpen =1

get_multiplier
===
    select * FROM [RYPlatformManagerDB].[dbo].[MultiplierConfig] where id=#{id}
get_channel
===
    select * from [RYPlatformManagerDB].[dbo].[Channel] where id=#{id}
first_list
===
    select amount,gold,give_gold,type from [RYPlatformManagerDB].[dbo].[First_charge_config]
all_parent_list
===
    SELECT id,mcName FROM [RYPlatformManagerDB].[dbo].[Channel] where pid = 0 and isOpen=1
all_child_list
===
    SELECT id,name,pid,min,max,channelTaxRate,sort,give,fee,goldProportion FROM [RYPlatformManagerDB].[dbo].[Channel] where pid != 0 and type = #{type}
get_max_sort
===
    select ISNULL(max(sort),0) as sorts from [RYPlatformManagerDB].[dbo].[Channel] where channelName=#{channelName}
all_child
===
    SELECT id,name,pid,min,max,goldProportion,fee,sort,give,channelName,unit,channelTaxRate,isLabel,winConf,mcName FROM Channel 
    where pid!=0 and isOpen=1 and type=#{type} and channelName=#{channelName} order by sort
all_exchange_channel_min
===
    select id,name from [RYPlatformManagerDB].[dbo].[Channel] where pid != 0 and isOpen=1 and type=1
find_all_parent
===
    SELECT id,mcName FROM Channel WHERE pid=0
find_all_max_channel
===
    select channelName FROM Channel GROUP BY channelName HAVING channelName!='' and channelName!='null'
find_one_channel
===
    select MAX(c.min) min,MAX(c.max) max,MAX(c.winConf) winConf,MAX(c.channelTaxRate) as channelTaxRate,MAX(c.goldProportion) as goldProportion FROM Channel as c 
    where c.channelName=#{channelName} and c.pid!=0 and c.type=1 and c.isOpen=1
find_list_count
===
    select count(id) as num FROM Channel as c where c.channelName=#{channelName} and c.pid!=0 and c.type=#{type} and c.isOpen=1
find_all_child
===
    select c.isLabel,c.unit,c.mcName FROM Channel as c where c.channelName=#{channelName} and c.pid!=0 and c.type=#{type} and c.isOpen=1
