new_gold_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p.ChangeType_Id)as typeremark
	,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg,Time_Id
	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_22] as p with (nolock)
	where p.Prop_Id=1
	@if(!isEmpty(UserID)){
	 and p.User_Id =#{UserID}
	@}
	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
	@if(!isEmpty(ServerID)){
	 and p.ServerId =#{ServerID}
	@}
	@if(!isEmpty(ChangeType_Id)){
	 	@if(ChangeType_Id=='-4'){
		 	and p.ChangeType_Id in (5,102)
	  	@} else {
	  		and p.ChangeType_Id =#{ChangeType_Id}
	  	@}
	@}
	@if(!isEmpty(orderBy)){
	order by p.Time_Id asc
	@}

new_room_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_22] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and p51.Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and p51.Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId