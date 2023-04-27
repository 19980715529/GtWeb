package com.smallchill.system.treasure.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.Table;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//用户表
@Table(name = "Recharge_channel")
@BindID(name = "id")
public class RechargeChannel extends BaseModel {
    private Integer id; // 渠道id
    private String channel_name; //渠道名称

    private List<Map>  types = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public List<Map> getTypes() {
        return types;
    }

    public void setTypes(List<Map> types) {
        this.types = types;
    }
}
