new_list
===
    select e.id,e.title,e.content,e.amount,e.attachment,
    isnull((select c.sender from [QPServerInfoDB].[dbo].[CustomEmaolTitle] as c where c.id=e.senderId),'') as emailSender
    from [RYPlatformManagerDB].[dbo].[email_model] as e
one_emailmodel
===
    select * from [RYPlatformManagerDB].[dbo].[email_model] where id =#{id}

one_email
===
    select title,content,senderId from [RYPlatformManagerDB].[dbo].[email_model] where id =#{id}
sender_list
===
    select id,sender from [QPServerInfoDB].[dbo].[CustomEmaolTitle]