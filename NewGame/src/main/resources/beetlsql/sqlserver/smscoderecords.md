new_list
===
    select id,UserID,Phone,code,DATEADD(S,SendTime + 8 * 3600,'1970-01-01 00:00:00') SendTime from [QPGameUserDB].[dbo].[SendPhoneCodeRecord] where 1=1
    @if(!isEmpty(phone) && phone !=""){
        and Phone =#{phone}
    @}
    @if(!isEmpty(userId) && userId !=""){
        and UserID =#{userId}
    @}
    @if(!isEmpty(start_time)){
        and SendTime >=#{start_time}
    @}
    @if(!isEmpty(end_time)){
        and SendTime <=#{end_time}
    @}