list
===
select r.*,d.simpleName deptname,(select name from blade_role where id=r.pId) pname from blade_role r left join blade_dept d on r.deptId=d.id

diy
===
select 0 as "id", 0 as "pId",'顶级' as "name",'true' as "open" from  blade_role 
union
select ID as "id", pId as "pId",name as "name",(case when (pId=0 or pId is null) then 'true' else 'false' end) as "open" from  blade_role

all_list
===
	select t1.*,t2.name pname from (SELECT * FROM [RYPlatformManagerDB].[dbo].[blade_role] 
	where 1=1
	@if(!isEmpty(ids)){
	 and id in (#{join(ids)})
	@}
	@if(!isEmpty(ids)){
	 or pid in (#{join(ids)})
	@}
	) t1
	  left join 
	  (select id,name FROM [RYPlatformManagerDB].[dbo].[blade_role]) t2
	  on t1.pid=t2.id
	  
authority_list
===
	select t1.*,t2.mid,
	(case when (t2.mid is null) then 'false' else 'true' end) checked
	 from (select t1.*,t2.id pId from 
	(select id,name,pcode,levels from [RYPlatformManagerDB].[dbo].[blade_menu]
	where 1=1 and status=1
	@if(!isEmpty(isSysRole)){
	 and rolelevel<>'1'
	@}
	) t1
	left join
	(select * from [RYPlatformManagerDB].[dbo].[blade_menu]) t2
	on t1.pcode=t2.code) t1
	left join
	(SELECT id mid FROM [RYPlatformManagerDB].[dbo].[blade_menu]
	  where id in (select menuid FROM [RYPlatformManagerDB].[dbo].[blade_relation] 
	  where roleid=#{roleId})) t2
	  on t1.id=t2.mid