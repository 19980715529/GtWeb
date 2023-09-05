package com.smallchill.proxy.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import lombok.Data;
import org.beetl.sql.core.annotatoin.Table;

@Table(name = "gm_recharge_config")
@BindID(name = "id")
@Data
public class GmRechargeConfig extends BaseModel {
    private Integer id;
    private Integer agentId;
    private Long rechargeRatio;
    private Long minRecharge;
    private Long gift;
}
