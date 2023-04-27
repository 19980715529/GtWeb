all_list
===
    select e.id,e.createTime,e.orderNumber,e.userId,e.nickname,e.phone,e.cardholder,e.bankNumber,
    e.bank,isnull((select c.name from [RYPlatformManagerDB].[dbo].[Channel] as c where e.channelId=c.id and c.id!=19),'') as channel,
    e.sourcePlatform,e.amount,e.gold,e.consumptionCode,e.status,e.feedback,e.operator,e.msg,e.tipsName
    FROM [RYPlatformManagerDB].[dbo].[Exchange_review] as e where 1=1
    @if(!isEmpty(StartTime)){
         and CONVERT(VARCHAR(100), e.createTime, 23) >= CONVERT(VARCHAR(100), #{StartTime}, 23)
      @}
      @if(!isEmpty(EndTime)){
         and CONVERT(VARCHAR(100), e.createTime, 23) <= CONVERT(VARCHAR(100), #{EndTime}, 23)
      @}
one_list
===
    select e.id,e.createTime,e.orderNumber,e.userId,e.nickname,e.phone,e.cardholder,e.bankNumber,
    e.bank,isnull((select c.name from [RYPlatformManagerDB].[dbo].[Channel] as c where e.channelId=c.id),'') as channel,
    e.sourcePlatform,e.amount,e.gold,e.consumptionCode,e.status,e.feedback,e.operator,e.msg
    FROM [RYPlatformManagerDB].[dbo].[Exchange_review] as e where e.id = #{id} 
