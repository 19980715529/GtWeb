all_list
===
    select id,CONVERT(char(10),createTime,23) createTime,DailyFeedbackAmount,DailyFeedbackUser,accFeedbackAmount,FeedbackRecAmount
    from QPGameRecordDB.dbo.ActivityStatistics where clientType = #{clientType}
    @if(!isEmpty(StartTime)){
        and CONVERT(VARCHAR(100), createTime, 23) >= CONVERT(VARCHAR(100), #{StartTime}, 23)
    @}
    @if(!isEmpty(EndTime)){
        and CONVERT(VARCHAR(100), createTime, 23) <= CONVERT(VARCHAR(100), #{EndTime}, 23)
    @}

list_details
===
    select a.Log_Id id,a.User_Id,a.Amount,a.LogTime from [QPGameUserDB].[dbo].[AA_ZZ_Log_PropChange] as a join [QPGameUserDB].[dbo].[AccountsInfo] as b on a.User_Id=b.UserID
    where a.Prop_Id=1
    @if(!isEmpty(clientType)){
        @if(clientType != '-1'){
            and b.ClientType=#{clientType}
        @}
    @}
    @if(!isEmpty(StartTime)){
        and CONVERT(VARCHAR(100), LogTime, 23) >= CONVERT(VARCHAR(100), #{StartTime}, 23)
    @}
    @if(!isEmpty(EndTime)){
        and CONVERT(VARCHAR(100), LogTime, 23) <= CONVERT(VARCHAR(100), #{EndTime}, 23)
    @}