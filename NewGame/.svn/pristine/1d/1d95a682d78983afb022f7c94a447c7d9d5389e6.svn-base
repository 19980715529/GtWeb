list
===
	  select NewType,OldType,IsOnline,IsCanSeeAll,IsForbidActivity,IsForbidGift,IsForbidSign,[canSeeCustomHead],[RechargeGift],[IsUserThirdRecharge]
	  ,(case when canSeeCustomHead=0 then '否' else '是' end) canSeeCustomHeadName
	  ,(case when RechargeGift=0 then '否' else '是' end) RechargeGiftName
	  ,IsUserThirdRecharge as IsUserThirdRechargeName
	  ,(case when IsOnline=0 then '关闭' else '开启' end) IsOnlineName
	  ,(case when IsCanSeeAll=0 then '禁止查看' else '允许查看' end) IsCanSeeAllName
	  ,(case when IsForbidActivity=0 then '禁止活动' else '允许活动' end) IsForbidActivityName
	  ,(case when IsForbidGift=0 then '禁止赠送礼物' else '允许赠送礼物' end) IsForbidGiftName
	  ,(case when IsForbidSign=0 then '禁止签到' else '允许签到' end) IsForbidSignName
	    ,(select name from [QPGameUserDB].[dbo].[AccountTypeName] a where a.clientType=c.NewType) NewPackageName 
	  ,(select name from [QPGameUserDB].[dbo].[AccountTypeName] a where a.clientType=c.OldType) OldPackageName 
	  FROM [login].[dbo].[ClientTypeMap_New] c where 1=1
	@if(!isEmpty(NewType)){
	 and NewType = #{NewType}
	@}
	@if(!isEmpty(NewPackageName)){
	 and NewType in (select clientType from [QPGameUserDB].[dbo].[AccountTypeName] a where a.name like '%'+#{NewPackageName}+'%')
	@}