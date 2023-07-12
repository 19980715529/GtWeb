all_list
===
    select id,CONVERT(char(10),createTime,23) createTime,turntableAmount,turntableUser,silverAmount,silverUser,goldAmount,goldUser,
    diamondAmount,diamondUser from QPGameRecordDB.dbo.ActivityStatistics where clientType = #{clientType}
    @if(!isEmpty(StartTime)){
        and CONVERT(VARCHAR(100), createTime, 23) >= CONVERT(VARCHAR(100), #{StartTime}, 23)
    @}
    @if(!isEmpty(EndTime)){
        and CONVERT(VARCHAR(100), createTime, 23) <= CONVERT(VARCHAR(100), #{EndTime}, 23)
    @}

list_details
===
    select a.id,a.UserID,a.Award,a.Type,a.[Time] from [QPGameRecordDB].[dbo].[Turntable_History] as a join [QPGameUserDB].[dbo].[AccountsInfo] as b on a.UserID=b.UserID 
    where Fake=0
    @if(!isEmpty(clientType)){
        @if(clientType != '-1'){
            and b.ClientType=#{clientType}
        @}
    @}
    @if(!isEmpty(StartTime)){
        and CONVERT(VARCHAR(100), [Time], 23) >= CONVERT(VARCHAR(100), #{StartTime}, 23)
    @}
    @if(!isEmpty(EndTime)){
        and CONVERT(VARCHAR(100), [Time], 23) <= CONVERT(VARCHAR(100), #{EndTime}, 23)
    @}