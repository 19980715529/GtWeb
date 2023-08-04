all_list
===
    select c.id,a.Time,a.RechargeUserID,a.AgentUserID,a.Rebate,(a.RechargeAmount/10000) as amount,
    c.RechargeUserID AgentUpUserID,
    d.ClientType clientType,
    (c.Rebate+b.Rebate) AgentUpRebate from 
    [QPGameUserDB].[dbo].PlayerShareRechargeRebate as a 
    join [QPGameUserDB].[dbo].PlayerShareRechargeRebate as b ON a.AgentUserID=b.RechargeUserID  
    JOIN [QPGameUserDB].[dbo].PlayerShareRechargeRebate as c ON b.AgentUserID=c.RechargeUserID
    JOIN [QPGameUserDB].[dbo].[AccountsInfo] as d ON d.UserID=a.RechargeUserID
    where 1=1
    @if(!isEmpty(clientType)){
        and d.ClientType=#{clientType}
    @}
    @if(!isEmpty(AgentUserID)){
        and a.AgentUserID=#{AgentUserID}
    @}
    @if(!isEmpty(downUserID)){
        and a.RechargeUserID=#{downUserID}
    @}
    @if(!isEmpty(StartTime)){
        and CONVERT(VARCHAR(100), a.Time, 23) >= CONVERT(VARCHAR(100), #{StartTime}, 23)
    @}
    @if(!isEmpty(EndTime)){
        and CONVERT(VARCHAR(100), a.Time, 23) <= CONVERT(VARCHAR(100), #{EndTime}, 23)
    @}