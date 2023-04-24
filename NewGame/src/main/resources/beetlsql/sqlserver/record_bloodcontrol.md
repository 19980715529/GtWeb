new_list
===
	  SELECT WriteDate, Blood_Pool, lMaxEatScore, lTotalLimitScore
	  FROM QPGameUserDB.dbo.GameRoomBlood_Record 
	  where 1=1 
	 @if(!isEmpty(ServerID)){
	  and Serverid = #{ServerID}
	 @} 
	 @if(!isEmpty(startTime)){
		 and DATEDIFF(MINUTE, WriteDate, #{startTime}) <=0
	  @}
	  @if(!isEmpty(endTime)){
	 	and DATEDIFF(MINUTE, WriteDate, #{endTime}) >= 0
	  @}
	  @if(!isEmpty(orderBy)){
	 		order by WriteDate asc
	  @}