list
===
    select id,title,content,isOpen,(select name from login.dbo.ClientPos where clientType=a.clientType) as name,code
    from announcement_management as a where 1=1
    @if(!isEmpty(clientType)){
        @if(clientType!='-1'){
            and clientType=#{clientType}
        @}
	@}
announcement_one
===
    select * from announcement_management where id=#{id}
all_list
===
    select id,title,content,code from announcement_management where isOpen!=0
    @if(!isEmpty(clientType)){
	  and clientType=#{clientType}
	@}