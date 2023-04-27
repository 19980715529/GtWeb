list
===
	select vipLevel,buyTimes,buyOnceValue FROM [QPGameUserDB].[dbo].[L_VipConfig]
	@if(!isEmpty(vipLevel)){
	  where vipLevel=#{vipLevel}
	  @}