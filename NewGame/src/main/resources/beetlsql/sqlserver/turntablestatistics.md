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