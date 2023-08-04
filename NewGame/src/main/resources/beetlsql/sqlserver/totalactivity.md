all_list
===
    select id,CONVERT(char(10),createTime,23) createTime,giveTotalAmount,firstRechargeAmount,firstRecUser,checkInAmount,
    checkInUser,turntableAmount,turntableUser,DailyFeedbackAmount,DailyFeedbackUser,
    noviceTaskAmount,noviceTaskUser from QPGameRecordDB.dbo.ActivityStatistics where clientType = #{clientType}
    @if(!isEmpty(StartTime)){
        and CONVERT(VARCHAR(100), createTime, 23) >= CONVERT(VARCHAR(100), #{StartTime}, 23)
    @}
    @if(!isEmpty(EndTime)){
        and CONVERT(VARCHAR(100), createTime, 23) <= CONVERT(VARCHAR(100), #{EndTime}, 23)
    @}