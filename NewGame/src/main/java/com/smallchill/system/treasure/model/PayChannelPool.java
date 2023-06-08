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
    private String code;
    private String currencyType; // 币种单位
    private BigDecimal collectRate; // 代收手续费
    private BigDecimal paymentRate; // 代付手续费
    private BigDecimal fee; // 固定手续费
    private String name; // 小渠道名称

    private Integer minLimit; // 渠道最小限额
    private Integer maxLimit; // 渠道最大限额
    private Integer isLabel; // 标签状态
    private BigDecimal channelTax; // 我方渠道税率
    private Integer exchangeRatio; // 兑换金币比率
    private BigDecimal giveRatio; // 赠送比率

    private Integer sort; // 排序
    private BigDecimal winConf; // 总赢配置
    private BigDecimal money;//我方收取手续费


}
