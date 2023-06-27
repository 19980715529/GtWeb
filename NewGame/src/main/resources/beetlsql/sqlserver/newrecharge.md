all_list
===
    select * from [QPGameRecordDB].dbo.NewRechargeRecord where clientType=#{clientType} 
    @if(!isEmpty(StartTime)){
        and CONVERT(VARCHAR(100), createTime, 23) >= CONVERT(VARCHAR(100), #{StartTime}, 23)
    @}
    @if(!isEmpty(EndTime)){
        and CONVERT(VARCHAR(100), createTime, 23) <= CONVERT(VARCHAR(100), #{EndTime}, 23)
    @}

details_list
===
    select * from [QPGameRecordDB].[dbo].newRechargeDetails where pid=#{pid}
    @if(!isEmpty(StartTime)){
        and CONVERT(VARCHAR(100), createTime, 23) >= CONVERT(VARCHAR(100), #{StartTime}, 23)
    @}
    @if(!isEmpty(EndTime)){
        and CONVERT(VARCHAR(100), createTime, 23) <= CONVERT(VARCHAR(100), #{EndTime}, 23)
    @}

find_one
===
    select id,CONVERT(VARCHAR(10), createTime, 23) createTime,newUserNum,registerNum,recUserNum,newRecUser,newRecNum,newRecAmount,newExcUser,
    newExcAmount,newRE,averageRecharge,cost,deliveryCost,clientType
    from [QPGameRecordDB].dbo.NewRechargeRecord where id=#{id}