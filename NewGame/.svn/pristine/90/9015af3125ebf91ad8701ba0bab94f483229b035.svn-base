new_list
===
    select * from [QPGameUserDB].[dbo].[SendPhoneCodeRecord] where 1=1
    @if(!isEmpty(phone) && phone !=""){
        and Phone =#{phone}
    @}
    @if(!isEmpty(userId) && userId !=""){
        and UserID =#{userId}
    @}