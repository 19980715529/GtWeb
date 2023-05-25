package com.smallchill.system.treasure.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import lombok.Data;
import org.beetl.sql.core.annotatoin.Table;

import java.math.BigDecimal;


@Table(name = "Pay_channelList")
@BindID(name = "id")
@Data
public class PayChannelList extends BaseModel {
    private Integer id;
    private Integer chPoolId; // 渠道池id  cPoolId
    private Integer minLimit;
    private Integer maxLimit;
    private BigDecimal channelTax;
    private Integer isOpen;
    private Integer sort;
    private BigDecimal exchangeRatio;
    private BigDecimal giveRatio;
    private Integer isLabel;
    private BigDecimal winConf;
    private Integer clientType;
    private BigDecimal fee; // 固定费用
}
