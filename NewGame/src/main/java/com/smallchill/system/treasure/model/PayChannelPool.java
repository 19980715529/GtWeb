package com.smallchill.system.treasure.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import lombok.Data;
import org.beetl.sql.core.annotatoin.Table;

import java.math.BigDecimal;

@Table(name = "Pay_ChannelPool")
@BindID(name = "id")
@Data
public class PayChannelPool extends BaseModel {
    private Integer id;
    private Integer mcId; // 商户号id
    private Integer cid; // 大渠道id
    private Integer type;
    private Integer isOpen;
    private String param;
    private String currencyType; // 币种单位
    private BigDecimal formalitiesCost; // 手续费
    private BigDecimal fee; // 固定手续费
    private String name; // 小渠道名称

}
