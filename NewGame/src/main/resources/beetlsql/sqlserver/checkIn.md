all_list
===
    select id,CONVERT(char(10),createTime,23) createTime,checkInUser,newCheckInUser,newCheckAmount,receiveCheckInUser
    from QPGameRecordDB.dbo.ActivityStatistics where clientType = #{clientType}
    @if(!isEmpty(StartTime)){
        and CONVERT(VARCHAR(100), createTime, 23) >= CONVERT(VARCHAR(100), #{StartTime}, 23)
    @}
    @if(!isEmpty(EndTime)){
        and CONVERT(VARCHAR(100), createTime, 23) <= CONVERT(VARCHAR(100), #{EndTime}, 23)
    @}


list_details
===
    select a.UserID,a.CheckDays,b.Amount,b.LogTime 
    from QPGameUserDB.dbo.CheckIn_PlayerCheckInfo as a 
    join QPGameUserDB.dbo.AA_ZZ_Log_PropChange as b on a.UserID=b.User_Id
    join QPGameUserDB.dbo.AccountsInfo as c on a.UserID = c.UserID
    where ChangeType_Id=223 
    @if(!isEmpty(clientType)){
        @if(clientType != '-1'){
            and c.ClientType=#{clientType}
        @}
    @}
    @if(!isEmpty(StartTime)){
        and CONVERT(VARCHAR(100), LogTime, 23) >= CONVERT(VARCHAR(100), #{StartTime}, 23)
    @}
    @if(!isEmpty(EndTime)){
        and CONVERT(VARCHAR(100), LogTime, 23) <= CONVERT(VARCHAR(100), #{EndTime}, 23)
    @}