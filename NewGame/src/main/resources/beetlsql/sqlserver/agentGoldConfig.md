all_list
===
    select * from [QPServerInfoDB].[dbo].[InviteAwardConfig]

one_config
===
    select * from [QPServerInfoDB].[dbo].[InviteAwardConfig] where InviteNum=#{InviteNum}