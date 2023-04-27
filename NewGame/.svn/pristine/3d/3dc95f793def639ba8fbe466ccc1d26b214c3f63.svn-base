list
===
	SELECT ActivityCore_Id,Title,Remark,SortNo,IsOnline,ImgUrl
	,(case IsOnline when 1 then '是'  else '否'  end) IsOnlineName
	FROM QPGameUserDB.[dbo].[AA_ActivityCore]
	where 1=1
	@if(!isEmpty(ActivityCore_Id)){
	 and ActivityCore_Id = #{ActivityCore_Id}
	@}
	@if(!isEmpty(Title)){
	 and [Title] like '%'+#{Title}+'%'
	@}
