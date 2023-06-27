all_list
===
    select e.id,e.createTime,e.orderNumber,e.userId,e.phone,e.cardholder,e.bankNumber,e.channelName,e.auditMethod,
    e.bank,isnull((select c.mcName from [RYPlatformManagerDB].[dbo].[Pay_MCPool] as c where e.channelId=c.mcId and c.id!=19),'') as channel,
    e.sourcePlatform,e.amount,e.gold,e.consumptionCode,e.status,e.feedback,e.operator,e.msg,b.tipsName,b.NickName nickname
    FROM [RYPlatformManagerDB].[dbo].[Exchange_review] as e join QPGameUserDB.dbo.AccountsInfo as b on e.userId=b.UserID where 1=1
    @if(!isEmpty(userId)){
         and userId = #{userId}
      @}
    @if(!isEmpty(auto)){
         and auditMethod = #{auto}
      @}
    @if(!isEmpty(orderNum)){
         and orderNumber = #{orderNum}
      @}
    @if(!isEmpty(clientType)){
         and sourcePlatform = #{clientType}
      @}
    @if(!isEmpty(orderType)){
         and status = #{orderType}
      @}
    @if(!isEmpty(StartTime)){
         and CONVERT(VARCHAR(100), e.createTime, 23) >= CONVERT(VARCHAR(100), #{StartTime}, 23)
      @}
      @if(!isEmpty(EndTime)){
         and CONVERT(VARCHAR(100), e.createTime, 23) <= CONVERT(VARCHAR(100), #{EndTime}, 23)
      @}
one_list
===
    select e.id,e.createTime,e.orderNumber,e.userId,e.phone,e.cardholder,e.bankNumber,e.channelName,
    e.bank,isnull((select c.mcName from [RYPlatformManagerDB].[dbo].[Pay_MCPool] as c where e.channelId=c.mcId),'') as channel,
    e.sourcePlatform,e.amount,e.gold,e.consumptionCode,e.status,e.feedback,e.operator,e.msg
    FROM [RYPlatformManagerDB].[dbo].[Exchange_review] as e where e.id = #{id} 
