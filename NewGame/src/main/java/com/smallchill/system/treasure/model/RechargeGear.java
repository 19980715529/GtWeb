package com.smallchill.system.treasure.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import lombok.Data;
import org.beetl.sql.core.annotatoin.Table;

import java.math.BigDecimal;

@Table(name = "Pay_RechargeGear")
@BindID(name = "id")
@Data
public class RechargeGear extends BaseModel {
    private Integer id;
    private Long gold;
    private BigDecimal getExtra;
    private Long amount;
    private Integer sort;
    private Integer isDel; // 是否删除
    private String skuId; // 商品id
}
