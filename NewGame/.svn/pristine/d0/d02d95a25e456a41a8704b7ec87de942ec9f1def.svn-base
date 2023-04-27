new_physical_exchange
===
	  select s.Order_No OrderID,s.USER_ID UserID,s.BuyTime BuyDate,
	  s.TotalAmount TotalAmount,s.CurrentState OrderStatus,s.PhoneNumber MobilePhone,a.GameID,a.BeautifulID
	  ,g.GoodsName AwardName,s.ExpressInformation SolveNote,(case when (a.tipsName is null or a.tipsName='') then a.NickName else (a.NickName+'<span class="text-red">['+a.tipsName+']</span>') end) NickName,s.HandleTime
	   FROM 
	  [QPGameUserDB].[dbo].[AA_Shop_Goods_UserGoods] s,
	  [QPGameUserDB].[dbo].[AccountsInfo] a,
	  [QPGameUserDB].[dbo].[AA_Shop_Goods] g
	  where s.User_Id=a.UserID and s.Goods_Id=g.Goods_Id
	  @if(!isEmpty(BuyDate_dategt)){
	 	and DATEDIFF(DAY,s.BuyTime,#{BuyDate_dategt})<=0
	  @}
	  @if(!isEmpty(BuyDate_datelt)){
	 	and DATEDIFF(DAY,s.BuyTime,#{BuyDate_datelt})>=0
	  @}
	  @if(!isEmpty(OrderID)){
	 	and s.Order_No =#{OrderID}
	  @}
	  @if(!isEmpty(UserID)){
	 	and s.User_Id =#{UserID}
	  @}
	  @if(!isEmpty(GameID)){
	 	and a.GameID =#{GameID}
	  @}
	  @if(!isEmpty(SendStatus)){
	 	and s.CurrentState =#{SendStatus}
	  @}
	  @if(!isEmpty(PhoneNumber)){
	 	and s.PhoneNumber =#{PhoneNumber}
	  @}
	  @if(!isEmpty(AwardName)){
	 	and g.GoodsName like '%'+#{AwardName}+'%'
	  @}
	  @if(!isEmpty(MemberType)){
	 	@if(MemberType=='-1'){
			and a.vipLevel>0 and a.vipLevel<=6
		@} else if(MemberType=='-2'){
			and a.Businessman=1
		@} else {
			and a.vipLevel=#{MemberType}
		@}
	  @}