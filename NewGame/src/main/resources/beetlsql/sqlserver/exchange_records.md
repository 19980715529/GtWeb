all_list
===
    select * FROM [RYPlatformManagerDB].[dbo].[Exchange_review]
    where (status = 4 or status = 3)
    @if(!isEmpty(clientType)){
        and sourcePlatform=#{clientType}
    @}
    @if(!isEmpty(StartTime)){
         and CONVERT(VARCHAR(100), createTime, 23) >= CONVERT(VARCHAR(100), #{StartTime}, 23)
      @}
      @if(!isEmpty(EndTime)){
         and CONVERT(VARCHAR(100), createTime, 23) <= CONVERT(VARCHAR(100), #{EndTime}, 23)
      @}
one_list
===
    select * FROM [RYPlatformManagerDB].[dbo].[Exchange_review] where id = #{id}


current_exchange_application_list
===
    select ISNULL(sum(amount),0) as current_money,count(distinct userId) current_total 
    from [RYPlatformManagerDB].[dbo].[Exchange_review] where DateDiff(dd,createTime,getdate())=0 and status in (3,4)
current_exchange_success_list
===
    select ISNULL(sum(amount),0) as current_moneys,count(distinct userId) current_totals 
    from [RYPlatformManagerDB].[dbo].[Exchange_review] where DateDiff(dd,createTime,getdate())=0 and status in (3,4)
exchange_application_list
===
    select ISNULL(sum(amount),0) as money,count(distinct userId) total from [RYPlatformManagerDB].[dbo].[Exchange_review] where status in (3,4)
exchange_success_list
===
    select ISNULL(sum(amount),0) as moneys,count(distinct userId) totals from [RYPlatformManagerDB].[dbo].[Exchange_review] where status in (3,4)
orderNum_One
===
    select * from [RYPlatformManagerDB].[dbo].[Exchange_review] where orderNumber=#{orderNumber}

user_exchange_list
===
    select createTime,feedback,status,amount,gold from [RYPlatformManagerDB].[dbo].[Exchange_review] where userId=#{userId}

find_by_id_records
===
    select * from [RYPlatformManagerDB].[dbo].[Exchange_review] where id =#{id}
