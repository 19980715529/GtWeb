all_list
===
    * 查询转账挡位配置
    select *,(select name from blade_dict_data where agentId=[value] AND pcode='AgentLevel') name from TransferConfig

one_info
===
    * 查询单条数据
    select *,(select name from blade_dict_data where agentId=[value] AND pcode='AgentLevel') name from TransferConfig where id=#{id}