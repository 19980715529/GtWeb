list
===
select * from blade_user 

role_user_list
===
SELECT account, createtime
  FROM [RYPlatformManagerDB].[dbo].[blade_user] 
  where 1=1
  @if(!isEmpty(roles)){
  	and roleid like '%'+#{roles}+'%'
  @}

sqlserverlist
===
select TOP 100 u.*,
	d.name as SEXNAME,
	e.name as STATUSNAME,
	dept.simpleName as DEPTNAME,
	(select STUFF((
            SELECT ',' + NAME
            FROM blade.dbo.blade_role
            FOR XML PATH('')
            ), 1, 1, '') FROM blade.dbo.blade_role r where CHARINDEX((','+cast(u.ROLEID as varchar)+','),(','+cast(ID as varchar)+','))>0) ROLENAME
from blade.dbo.blade_user u 
	left join (select num,name from blade.dbo.blade_dict where code=101) d on u.sex=d.num 
	left join (select num,name from blade.dbo.blade_dict where code=901) e on u.status=e.num 
	left join (select id,simpleName from blade.dbo.blade_dept) dept on u.deptId=dept.id