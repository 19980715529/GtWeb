all_list
===
    * 查询gm配置
    select *,(select name from blade_dict_data where agentId=[value] AND pcode='AgentLevel') name from gm_recharge_config

one_config
===
    * 查询gm充值配置
    select *,(select name from blade_dict_data where agentId=[value] AND pcode='AgentLevel') name from gm_recharge_config where id=#{id}