gold_recharge_change_list
===
    select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
    ,[Remark] as remark,[LogTime] as logtime
	,isnull((SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p.ChangeType_Id),'') as typeremark
	from [QPGameRecordDB].[dbo].[GoldChangeRecord] as p with (nolock)
	where p.ChangeType_Id in (5,207)
	@if(!isEmpty(UserID)){
        and p.User_Id =#{UserID}
    @}
    @if(!isEmpty(BeginTime)){
        and CONVERT(VARCHAR(100), p.LogTime, 20) >= CONVERT(VARCHAR(100), #{BeginTime}, 20)
    @}
    @if(!isEmpty(EndTime)){
        and CONVERT(VARCHAR(100), p.LogTime, 20) <= CONVERT(VARCHAR(100), #{EndTime}, 20)
    @}
rotary_gold_bonus
===
    select [Userid] as user_id,[BeforeGold] as preamount,[Daya] as amount ,ISNULL([Daya],0)+ISNULL([BeforeGold],0) as aftamount
    ,'使用转盘获得' as remark,[times] as logtime
	,(CASE [UseType] WHEN 1 THEN '使用转盘,在线签到，获得' WHEN 2 THEN '使用转盘,每日签到，获得' WHEN 3 THEN '使用转盘,打码返利，获得' ELSE '' END) AS typeremark
	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_TurntableClaimHistory] as p with (nolock)
	where ClaimType=1
	@if(!isEmpty(UserID)){
        and p.Userid =#{UserID}
    @}
    @if(!isEmpty(BeginTime)){
        and CONVERT(VARCHAR(100), p.times, 20) >= CONVERT(VARCHAR(100), #{BeginTime}, 20)
    @}
    @if(!isEmpty(EndTime)){
        and CONVERT(VARCHAR(100), p.times, 20) <= CONVERT(VARCHAR(100), #{EndTime}, 20)
    @}
gold_exchange_change_list
===
    select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
    ,[Remark] as remark,[LogTime] as logtime
	,isnull((SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p.ChangeType_Id),'') as typeremark
	from [QPGameUserDB].[dbo].[AA_ZZ_Log_PropChange] as p with (nolock)
	where p.ChangeType_Id in (206,211)
	@if(!isEmpty(UserID)){
        and p.User_Id =#{UserID}
    @}
    @if(!isEmpty(BeginTime)){
        and CONVERT(VARCHAR(100), p.LogTime, 20) >= CONVERT(VARCHAR(100), #{BeginTime}, 20)
    @}
    @if(!isEmpty(EndTime)){
        and CONVERT(VARCHAR(100), p.LogTime, 20) <= CONVERT(VARCHAR(100), #{EndTime}, 20)
    @}