all_list
===
    select DISTINCT t.UserID,t.proxy,t.ClientType,t.downUser,t.downRechargeUser,t.downRebate,t.ddownRebate,t.rebateTotal,t.todayDownNew,t.todayDownTotal,t.todayRebate 
    from (select
    a.UserID,
    a.AgentLevel proxy,
    (select ClientType from [QPGameUserDB].[dbo].[AccountsInfo] as x where a.UserID=x.UserID) as ClientType,
    (select count(DISTINCT x.UserID) from [QPGameUserDB].[dbo].[PlayerShare] as x where x.BindParentUserID=a.UserID) downUser,
    (select count(DISTINCT RechargeUserID) from [QPGameUserDB].[dbo].[PlayerShareRechargeRebate] as x where x.AgentUserID=a.UserID and x.AgentType=1) downRechargeUser,
    (select isnull(sum(Rebate),0) from [QPGameUserDB].[dbo].[PlayerShareRechargeRebate] as x where x.AgentUserID=a.UserID and x.AgentType=1) as downRebate,
    (select isnull(sum(Rebate),0) from [QPGameUserDB].[dbo].[PlayerShareRechargeRebate] as x where x.AgentUserID=a.UserID and x.AgentType=2) as ddownRebate,
    (select isnull(sum(Rebate),0) from [QPGameUserDB].[dbo].[PlayerShareRechargeRebate] as x where x.AgentUserID=a.UserID and x.AgentType in (1,2)) as rebateTotal,
    (select count(DISTINCT x.UserID) from [QPGameUserDB].[dbo].[PlayerShare] as x where x.BindParentUserID=a.UserID and CONVERT(VARCHAR(100), x.BindTime, 23)=CONVERT(VARCHAR(100), getdate(), 23)) as todayDownNew,
    (select isnull(sum(RechargeAmount),0)/10000 from [QPGameUserDB].[dbo].[PlayerShareRechargeRebate] as x where x.AgentUserID=a.UserID and x.AgentType=0 AND CONVERT(VARCHAR(100), x.Time, 23)=CONVERT(VARCHAR(100), getdate(), 23)) as todayDownTotal,
    (select isnull(sum(Rebate),0) from [QPGameUserDB].[dbo].[PlayerShareRechargeRebate] as x where x.AgentUserID=a.UserID AND CONVERT(VARCHAR(100), x.Time, 23)=CONVERT(VARCHAR(100), getdate(), 23)) as todayRebate 
    from [QPGameUserDB].[dbo].[PlayerShare] as a) t 
    where 1=1
    @if(!isEmpty(clientType)){
        @if(clientType!='-1'){
         and t.ClientType=#{clientType}
        @}
    @}
    @if(!isEmpty(start_rebate)){
        and t.rebateTotal>=#{start_rebate}
    @}
    @if(!isEmpty(end_rebate)){
        and t.rebateTotal<=#{end_rebate}
    @}
    @if(!isEmpty(startRecharge)){
        and t.downRechargeUser>=#{startRecharge}
    @}
    @if(!isEmpty(endRecharge)){
        and t.downRechargeUser<=#{endRecharge}
    @}
    @if(!isEmpty(agentType)){
        and t.proxy=#{agentType}
    @}
    @if(!isEmpty(user_Id)){
        and t.UserID=#{user_Id}
    @}
        

summary_data
===
    select  (select count(1) from [QPGameUserDB].[dbo].[PlayerShare] where AgentLevel=1) as vipAgent,
    (select count(DISTINCT UserID) from [QPGameUserDB].[dbo].[PlayerShare] where AgentLevel=0) as agent,
    (select isnull(sum(Rebate),0) from [QPGameUserDB].[dbo].[PlayerShareRechargeRebate] where AgentType in (1,2)) as totalRebate

summary_today
===
    select (select count(1) from [QPGameUserDB].[dbo].[PlayerShare] where BindParentUserID>0 and DateDiff(dd,BindTime,getdate())=0) as NewtoDayDown,
    (select isnull(sum(RechargeAmount),0)/10000 from [QPGameUserDB].[dbo].[PlayerShareRechargeRebate] where AgentType>0 and DateDiff(dd,Time,getdate())=0) as NewRecharge,
    (select isnull(sum(Rebate),0) from [QPGameUserDB].[dbo].[PlayerShareRechargeRebate] where AgentType>0 and DateDiff(dd,Time,getdate())=0) as NewRebate

query_summary
===
    select (count(DISTINCT t.UserID)-isnull(sum(t.proxy),0)) OrdinaryAgent,isnull(sum(t.proxy),0) vipProxy,isnull(sum(t.rebateTotal),0) rebateTotals,
    isnull(sum(t.todayDownNew),0) todayDownNewTotal,isnull(sum(t.todayDownTotal),0) todayDownTotals,isnull(sum(t.todayRebate),0) todayRebates 
    from (select
    a.UserID,
    a.AgentLevel proxy,
    (select ClientType from [QPGameUserDB].[dbo].[AccountsInfo] as x where a.UserID=x.UserID) as ClientType,
    (select count(DISTINCT x.UserID) from [QPGameUserDB].[dbo].[PlayerShare] as x where x.BindParentUserID=a.UserID) downUser,
    (select count(DISTINCT RechargeUserID) from [QPGameUserDB].[dbo].[PlayerShareRechargeRebate] as x where x.AgentUserID=a.UserID and x.AgentType=1) downRechargeUser,
    (select isnull(sum(Rebate),0) from [QPGameUserDB].[dbo].[PlayerShareRechargeRebate] as x where x.AgentUserID=a.UserID and x.AgentType=1) as downRebate,
    (select isnull(sum(Rebate),0) from [QPGameUserDB].[dbo].[PlayerShareRechargeRebate] as x where x.AgentUserID=a.UserID and x.AgentType=2) as ddownRebate,
    (select isnull(sum(Rebate),0) from [QPGameUserDB].[dbo].[PlayerShareRechargeRebate] as x where x.AgentUserID=a.UserID and x.AgentType in (1,2)) as rebateTotal,
    (select count(DISTINCT x.UserID) from [QPGameUserDB].[dbo].[PlayerShare] as x where x.BindParentUserID=a.UserID and CONVERT(VARCHAR(100), x.BindTime, 23)=CONVERT(VARCHAR(100), getdate(), 23)) as todayDownNew,
    (select isnull(sum(RechargeAmount),0)/10000 from [QPGameUserDB].[dbo].[PlayerShareRechargeRebate] as x where x.AgentUserID=a.UserID and x.AgentType=0 AND CONVERT(VARCHAR(100), x.Time, 23)=CONVERT(VARCHAR(100), getdate(), 23)) as todayDownTotal,
    (select isnull(sum(Rebate),0) from [QPGameUserDB].[dbo].[PlayerShareRechargeRebate] as x where x.AgentUserID=a.UserID AND CONVERT(VARCHAR(100), x.Time, 23)=CONVERT(VARCHAR(100), getdate(), 23)) as todayRebate 
    from [QPGameUserDB].[dbo].[PlayerShare] as a) t 
    where 1=1
    @if(!isEmpty(clientType)){
        and t.ClientType=#{clientType}
    @}
    @if(!isEmpty(start_rebate)){
        and t.rebateTotal>=#{start_rebate}
    @}
    @if(!isEmpty(end_rebate)){
        and t.rebateTotal<=#{end_rebate}
    @}
    @if(!isEmpty(startRecharge)){
        and t.downRechargeUser>=#{startRecharge}
    @}
    @if(!isEmpty(endRecharge)){
        and t.downRechargeUser<=#{endRecharge}
    @}
    @if(!isEmpty(agentType)){
        and t.proxy=#{agentType}
    @}
    @if(!isEmpty(user_Id)){
        and t.UserID=#{user_Id}
    @}