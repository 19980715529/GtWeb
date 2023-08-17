all_list
===
    select id,CONVERT(char(10),createTime,23) createTime,firstRechargeAmount,firstRecUser,firstRecMoney,firstRec1,firstRec2,firstRec3
    from QPGameRecordDB.dbo.ActivityStatistics where clientType = #{clientType}
    @if(!isEmpty(StartTime)){
        and CONVERT(VARCHAR(100), createTime, 23) >= CONVERT(VARCHAR(100), #{StartTime}, 23)
    @}
    @if(!isEmpty(EndTime)){
        and CONVERT(VARCHAR(100), createTime, 23) <= CONVERT(VARCHAR(100), #{EndTime}, 23)
    @}

list_details
===
    select id,userId,topUpAmount,gold,giftGold,createTime from RYPlatformManagerDB.dbo.Recharge_records 
    where orderStatus=2 and isFirstCharge=1
    @if(!isEmpty(clientType)){
        @if(clientType != '-1'){
            and packageName=#{clientType}
        @}
    @}
    @if(!isEmpty(StartTime)){
        and CONVERT(VARCHAR(100), createTime, 23) >= CONVERT(VARCHAR(100), #{StartTime}, 23)
    @}
    @if(!isEmpty(EndTime)){
        and CONVERT(VARCHAR(100), createTime, 23) <= CONVERT(VARCHAR(100), #{EndTime}, 23)
    @}