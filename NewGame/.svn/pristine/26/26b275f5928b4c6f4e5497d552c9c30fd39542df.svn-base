new_list
===
	SELECT CONVERT(VARCHAR(100), writedate, 23) ApplyDate,ios1,bus1,total1,ios3,bus3,total3,ios7,bus7,total7,ios30,bus30,total30,newios,newbus,newtotal
	FROM [QPGameRecordDB].[dbo].[PayUserLogin_Record]
	where 1=1
	@if(!isEmpty(startTime)){
	  	and DATEDIFF(DAY,writedate,#{startTime})<=0
	  @}
	  @if(!isEmpty(endTime)){
		 and DATEDIFF(DAY,writedate,#{endTime})>=0
	  @}
	  @if(!isEmpty(PlatformID)){
	  	@if(PlatformID=='0'){
			and (clientType=0 or clientType is null)
		@}else{
		 	and clientType =#{PlatformID}
		@}
	  @}
	order by writedate desc

new_list1
===
	SELECT CONVERT(VARCHAR(100), writedate, 23) ApplyDate,ios1,bus1,total1
	FROM [QPGameRecordDB].[dbo].[PayUserLogin_Record]
	where 1=1
	@if(!isEmpty(startTime)){
	  	and DATEDIFF(DAY,writedate,#{startTime})<=0
	  @}
	  @if(!isEmpty(endTime)){
		 and DATEDIFF(DAY,writedate,#{endTime})>=0
	  @}
	  @if(!isEmpty(PlatformID)){
	  	@if(PlatformID=='0'){
			and (clientType=0 or clientType is null)
		@}else{
		 	and clientType =#{PlatformID}
		@}
	  @}
	order by writedate desc

new_list3
===
	SELECT CONVERT(VARCHAR(100), writedate, 23) ApplyDate,ios3,bus3,total3
	FROM [QPGameRecordDB].[dbo].[PayUserLogin_Record]
	where 1=1
	@if(!isEmpty(startTime)){
	  	and DATEDIFF(DAY,writedate,#{startTime})<=0
	  @}
	  @if(!isEmpty(endTime)){
		 and DATEDIFF(DAY,writedate,#{endTime})>=0
	  @}
	  @if(!isEmpty(PlatformID)){
	  	@if(PlatformID=='0'){
			and (clientType=0 or clientType is null)
		@}else{
		 	and clientType =#{PlatformID}
		@}
	  @}
	order by writedate desc

new_list7
===
	SELECT CONVERT(VARCHAR(100), writedate, 23) ApplyDate,ios7,bus7,total7
	FROM [QPGameRecordDB].[dbo].[PayUserLogin_Record]
	where 1=1
	@if(!isEmpty(startTime)){
	  	and DATEDIFF(DAY,writedate,#{startTime})<=0
	  @}
	  @if(!isEmpty(endTime)){
		 and DATEDIFF(DAY,writedate,#{endTime})>=0
	  @}
	  @if(!isEmpty(PlatformID)){
	  	@if(PlatformID=='0'){
			and (clientType=0 or clientType is null)
		@}else{
		 	and clientType =#{PlatformID}
		@}
	  @}
	order by writedate desc

new_list30
===
	SELECT CONVERT(VARCHAR(100), writedate, 23) ApplyDate,ios30,bus30,total30
	FROM [QPGameRecordDB].[dbo].[PayUserLogin_Record]
	where 1=1
	@if(!isEmpty(startTime)){
	  	and DATEDIFF(DAY,writedate,#{startTime})<=0
	  @}
	  @if(!isEmpty(endTime)){
		 and DATEDIFF(DAY,writedate,#{endTime})>=0
	  @}
	  @if(!isEmpty(PlatformID)){
	  	@if(PlatformID=='0'){
			and (clientType=0 or clientType is null)
		@}else{
		 	and clientType =#{PlatformID}
		@}
	  @}
	order by writedate desc

new_listnew
===
	SELECT CONVERT(VARCHAR(100), writedate, 23) ApplyDate,newios,newbus,newtotal
	FROM [QPGameRecordDB].[dbo].[PayUserLogin_Record]
	where 1=1
	@if(!isEmpty(startTime)){
	  	and DATEDIFF(DAY,writedate,#{startTime})<=0
	  @}
	  @if(!isEmpty(endTime)){
		 and DATEDIFF(DAY,writedate,#{endTime})>=0
	  @}
	  @if(!isEmpty(PlatformID)){
	  	@if(PlatformID=='0'){
			and (clientType=0 or clientType is null)
		@}else{
		 	and clientType =#{PlatformID}
		@}
	  @}
	order by writedate desc

