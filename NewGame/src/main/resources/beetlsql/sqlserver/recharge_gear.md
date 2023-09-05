all_list
===
    select id,gold,getExtra,amount,sort,isDel,skuId,(select name from blade_dict_data where pcode='GearLabel' and label=[value]) label from Pay_RechargeGear where isDel=1
find_gear_by_id
===
    select *,(select name from blade_dict_data where pcode='GearLabel' and label=[value]) name from Pay_RechargeGear where id=#{id}