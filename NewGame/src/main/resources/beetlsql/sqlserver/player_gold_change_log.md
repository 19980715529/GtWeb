new_gold_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg
	,isnull((SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p.ChangeType_Id),'') as typeremark
	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p.ServerId),'') as roomname
	from [QPGameUserDB].[dbo].[AA_ZZ_Log_PropChange] as p with (nolock)
	where p.Prop_Id=1
	@if(!isEmpty(UserID)){
	 and p.User_Id =#{UserID}
	@}
	 @if(!isEmpty(BeginTime)){
		 and CONVERT(VARCHAR(100), p.LogTime, 20) >= CONVERT(VARCHAR(100), #{BeginTime}, 20)
	  @}
	  @if(!isEmpty(EndTime)){
		 and CONVERT(VARCHAR(100), p.LogTime, 20) <= CONVERT(VARCHAR(100), #{EndTime}, 20)
	  @}
	@if(!isEmpty(ServerID)){
	 and p.ServerId =#{ServerID}
	@}
	@if(!isEmpty(ChangeType_Id)){
	 	@if(ChangeType_Id=='-4'){
		 	and p.ChangeType_Id in (5,207)
	  	@}else if(ChangeType_Id=='-8') {
	  		and p.ChangeType_Id in (206,211)
	  	@}else if(ChangeType_Id=='-9'){
            and p.ChangeType_Id in (209,210)
        @}else{
            and p.ChangeType_Id =#{ChangeType_Id}
        @}
	@}
	@if(!isEmpty(orderBy)){
	order by p.LogTime asc
	@}

1_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_1] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	
3_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_3] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	
4_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_4] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	

5_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_5] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
6_change_log
===
    select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_6] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}

7_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_7] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	

8_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_8] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	
9_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_9] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	
11_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_11] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	
13_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_13] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    @if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	
    	
14_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_14] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	
15_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_15] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	
    	
16_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_16] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    @if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	

17_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_17] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	
18_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_18] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    @if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	
19_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
        	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_19] as p51 with (nolock)
        	where p51.Prop_Id=1
        	@if(!isEmpty(UserID)){
        	 and p51.User_Id =#{UserID}
        	@}
      @if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
        	@if(!isEmpty(ServerID)){
        	 and p51.ServerId =#{ServerID}
        	@}
        	@if(!isEmpty(ChangeType_Id)){
        	 	@if(ChangeType_Id=='-4'){
        		 	and p51.ChangeType_Id in (5,102)
        	  	@} else {
        	  		and p51.ChangeType_Id =#{ChangeType_Id}
        	  	@}
        	@}
        	
20_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_20] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    @if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	
    	
21_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_21] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    @if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	

22_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_22] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
   @if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	

25_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_25] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}

27_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_27] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	
28_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_28] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	    	
29_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_29] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	   	
32_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_32] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	  	
33_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_33] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	   	
34_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_34] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	   	
35_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_35] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	  	
38_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_38] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}

39_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_39] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
40_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_40] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	   	
41_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_41] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
42_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_42] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
43_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_43] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
44_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_44] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	 	
45_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_45] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	   	
47_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_47] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	  	
49_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_49] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
50_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_50] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@} 	
51_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_51] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
52_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_52] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	 	
53_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_53] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
54_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_54] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
55_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_55] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
57_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_57] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
58_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_58] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	  	
59_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_59] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	   	
61_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_61] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
62_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_62] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
63_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_63] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	 	
64_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_64] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	  	
65_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_65] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	  	
66_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_66] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	   	
67_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_67] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
       	
70_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg as exgamemsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_70] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	 	
71_change_log
===
	select [User_Id] as user_id,[PreAmount] as preamount,[Amount] as amount ,[AftAmount] as aftamount
      ,[Remark] as remark,[LogTime] as logtime,[ServerId] as serverid
      ,[CheatRate] as cheatrate,[CheatLimit] as cheatlimit,ExGameMsg,Time_Id
	,(SELECT b.Remark FROM QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType as b  WHERE b.ChangeType_Id = p51.ChangeType_Id)as typeremark
	,(select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId) as roomname
    	from [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_71] as p51 with (nolock)
    	where p51.Prop_Id=1
    	@if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	@if(!isEmpty(ServerID)){
    	 and p51.ServerId =#{ServerID}
    	@}
    	@if(!isEmpty(ChangeType_Id)){
    	 	@if(ChangeType_Id=='-4'){
    		 	and p51.ChangeType_Id in (5,102)
    	  	@} else {
    	  		and p51.ChangeType_Id =#{ChangeType_Id}
    	  	@}
    	@}
    	  	
allgame
===
    select distinct(KindID) from  [QPServerInfoDB].[dbo].[GameRoomItem] where  KindID !=0
new_room_gold_change
===
	SELECT SUM(Amount) TScore,0 as ServerId ,'大厅' as RoomName 
     FROM [QPGameUserDB].[dbo].[AA_ZZ_Log_PropChange] as p with (nolock), [QPGameUserDB].[dbo].[AccountsInfo] as a with (nolock) 
     where p.User_Id=a.UserID and p.ServerId = 0 
	  @if(!isEmpty(UserID)){
	 and p.User_Id =#{UserID}
	@}
	  @if(!isEmpty(GameID)){
	 and a.GameID =#{GameID}
	@}
	 @if(!isEmpty(InsertTime_dategt)){
		 and CONVERT(VARCHAR(100), p.LogTime, 20) >= CONVERT(VARCHAR(100), #{InsertTime_dategt}, 20)
	  @}
	  @if(!isEmpty(InsertTime_datelt)){
		 and CONVERT(VARCHAR(100), p.LogTime, 20) <= CONVERT(VARCHAR(100), #{InsertTime_datelt}, 20)
	  @}
1_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_1] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
3_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_3] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
4_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_4] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
5_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_5] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    @if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
6_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_6] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
7_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_7] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    @if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
        
        
8_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_8] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    @if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
        
9_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_9] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    @if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
                  
11_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_11] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    @if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
13_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_13] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    @if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
14_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_14] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    @if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
15_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_15] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    @if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
16_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_16] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    @if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
17_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_17] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    @if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
18_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_18] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    @if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
19_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_19] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
20_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_20] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
21_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_21] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
22_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_22] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
25_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_25] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
	   @if(!isEmpty(InsertTime_dategt)){
	    		 and Time_Id>=#{InsertTime_dategt}
	    	  @}
	    	  @if(!isEmpty(InsertTime_datelt)){
	    		 and Time_Id<=#{InsertTime_datelt}
	    	  @}
	    	group by p51.ServerId
27_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_27] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
28_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_28] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
29_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_29] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
32_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_32] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
33_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_33] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
34_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_34] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
35_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_35] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
38_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_38] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId

39_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_39] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
40_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_40] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
41_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_41] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
42_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_42] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
43_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_43] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
44_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_44] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
45_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_45] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
47_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_47] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
49_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_49] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
50_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_50] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
51_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_51] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    @if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
52_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_52] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    @if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
53_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_53] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
54_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_54] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
55_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_55] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
57_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_57] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
58_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_58] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
59_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_59] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
61_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_61] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
62_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_62] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
63_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_63] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
64_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_64] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
65_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_65] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
66_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_66] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
67_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_67] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
70_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_70] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId
71_gold_change
===
    SELECT SUM(Amount) TScore,p51.ServerId
    	,isnull((select RoomName from [QPServerInfoDB].[dbo].[GameRoomItem] gr where gr.ServerID=p51.ServerId),'大厅') as RoomName
    	FROM [QPGameRecordDB].[dbo].[AA_ZZ_Log_PropChange_71] as p51 with (nolock)
    	where p51.User_Id =#{UserID}
    	  @if(!isEmpty(UserID)){
    	 and p51.User_Id =#{UserID}
    	@}
    	@if(!isEmpty(InsertTime_dategt)){
    		 and Time_Id>=#{InsertTime_dategt}
    	  @}
    	  @if(!isEmpty(InsertTime_datelt)){
    		 and Time_Id<=#{InsertTime_datelt}
    	  @}
    	group by p51.ServerId

new_send
===
	SELECT isnull(SUM(prop_count),0) TScore,'-2' as ServerId
	,'赠送' as RoomName
	FROM [QPGameUserDB].[dbo].[AA_GiveRecord] as p with (nolock)
	where p.from_user =#{UserID}
	 @if(!isEmpty(InsertTime_dategt)){
	 	and time>=#{InsertTime_dategt}
	  @}
	  @if(!isEmpty(InsertTime_datelt)){
	  and time<=#{InsertTime_datelt}
	  @}

new_receive
===
	SELECT isnull(SUM(prop_count),0) TScore,'-3' as ServerId
	,'接收' as RoomName
	FROM [QPGameUserDB].[dbo].[AA_GiveRecord] as p with (nolock)
	where p.to_user =#{UserID}
	 @if(!isEmpty(InsertTime_dategt)){
	 	and time>=#{InsertTime_dategt}
	  @}
	  @if(!isEmpty(InsertTime_datelt)){
	  and time<=#{InsertTime_datelt}
	  @}

new_recharge
===
	SELECT isnull(SUM(Amount),0) TScore,'-4' as ServerId
    	,'充值' as RoomName
    	FROM [QPGameUserDB].[dbo].[AA_ZZ_Log_PropChange] as p with (nolock)
    	where p.ChangeType_Id  in (5,207,215)
    	@if(!isEmpty(UserID)){
    	 and p.User_Id =#{UserID}
    	@}
	 @if(!isEmpty(BeginTime)){
		 and CONVERT(VARCHAR(100), p.LogTime, 20) >= CONVERT(VARCHAR(100), #{BeginTime}, 20)
	  @}
	  @if(!isEmpty(EndTime)){
		 and CONVERT(VARCHAR(100), p.LogTime, 20) <= CONVERT(VARCHAR(100), #{EndTime}, 20)
	  @}
new_exchange
===
    SELECT isnull(SUM(Amount),0) TScore,'-8' as ServerId
    	,'兑换' as RoomName
    	FROM [QPGameUserDB].[dbo].[AA_ZZ_Log_PropChange] as p with (nolock)
    	where p.ChangeType_Id  in (206,211)
    	@if(!isEmpty(UserID)){
    	 and p.User_Id =#{UserID}
    	@}
	 @if(!isEmpty(BeginTime)){
		 and CONVERT(VARCHAR(100), p.LogTime, 20) >= CONVERT(VARCHAR(100), #{BeginTime}, 20)
	  @}
	  @if(!isEmpty(EndTime)){
		 and CONVERT(VARCHAR(100), p.LogTime, 20) <= CONVERT(VARCHAR(100), #{EndTime}, 20)
	  @}
email_give_gold
===
    SELECT isnull(SUM(Amount),0) TScore,'-9' as ServerId
    	,'邮件' as RoomName
    	FROM [QPGameUserDB].[dbo].[AA_ZZ_Log_PropChange] as p with (nolock)
    	where p.ChangeType_Id in (209,210)
    	@if(!isEmpty(UserID)){
    	 and p.User_Id =#{UserID}
    	@}
	 @if(!isEmpty(BeginTime)){
		 and CONVERT(VARCHAR(100), p.LogTime, 20) >= CONVERT(VARCHAR(100), #{BeginTime}, 20)
	  @}
	  @if(!isEmpty(EndTime)){
		 and CONVERT(VARCHAR(100), p.LogTime, 20) <= CONVERT(VARCHAR(100), #{EndTime}, 20)
	  @}
	  
bank_withdrawals
===
    SELECT isnull(SUM(Amount),0) TScore,'-5' as ServerId
    	,'银行取款' as RoomName
    	FROM [QPGameUserDB].[dbo].[AA_ZZ_Log_PropChange] as p with (nolock)
    	where p.ChangeType_Id = 40
    	@if(!isEmpty(UserID)){
    	 and p.User_Id =#{UserID}
    	@}
	 @if(!isEmpty(BeginTime)){
		 and CONVERT(VARCHAR(100), p.LogTime, 20) >= CONVERT(VARCHAR(100), #{BeginTime}, 20)
	  @}
	  @if(!isEmpty(EndTime)){
		 and CONVERT(VARCHAR(100), p.LogTime, 20) <= CONVERT(VARCHAR(100), #{EndTime}, 20)
	  @}
    	  
bank_deposit
===
    SELECT isnull(SUM(Amount),0) TScore,'-6' as ServerId
        	,'银行存款' as RoomName
        	FROM [QPGameUserDB].[dbo].[AA_ZZ_Log_PropChange] as p with (nolock)
        	where p.ChangeType_Id = 41
        	@if(!isEmpty(UserID)){
        	 and p.User_Id =#{UserID}
        	@}
	 @if(!isEmpty(BeginTime)){
		 and CONVERT(VARCHAR(100), p.LogTime, 20) >= CONVERT(VARCHAR(100), #{BeginTime}, 20)
	  @}
	  @if(!isEmpty(EndTime)){
		 and CONVERT(VARCHAR(100), p.LogTime, 20) <= CONVERT(VARCHAR(100), #{EndTime}, 20)
	  @}
rotary_give_gold
===
    select isnull(SUM(Award),0) TScore,'-7' as ServerId,'转盘金币奖励' as RoomName FROM [QPGameRecordDB].[dbo].[Turntable_History] as p with (nolock) 
    where Fake=1
    @if(!isEmpty(UserID)){
        and p.UserID =#{UserID}
    @}
    @if(!isEmpty(BeginTime)){
		 and CONVERT(VARCHAR(100), p.Time, 20) >= CONVERT(VARCHAR(100), #{BeginTime}, 20)
	  @}
	  @if(!isEmpty(EndTime)){
		 and CONVERT(VARCHAR(100), p.Time, 20) <= CONVERT(VARCHAR(100), #{EndTime}, 20)
	  @}
        	  
shuiguo777
===
    SELECT *  FROM  