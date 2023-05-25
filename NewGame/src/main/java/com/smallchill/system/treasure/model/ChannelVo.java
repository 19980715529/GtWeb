package com.smallchill.system.treasure.model;

import com.smallchill.core.base.model.BaseModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class ChannelVo extends BaseModel {
    private Integer id; // 渠道id
    private Integer isLabel;
    private String channel_name; //大渠道名称
    private List<Integer> exchangeGear;
    private Integer isRecharge;
    private Integer isExchange;
    private List<Map> types = new ArrayList<>();
}
