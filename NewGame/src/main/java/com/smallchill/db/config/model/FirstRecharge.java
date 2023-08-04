package com.smallchill.db.config.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import lombok.Data;
import org.beetl.sql.core.annotatoin.Table;

import java.math.BigDecimal;

/**
 * @Description TODO
 * @classNameFirstRecharge
 * @Date 2023/2/25 17:39
 * @Version 1.0
 **/
@Table(name = "First_charge_config")
@Data
@BindID(name = "id")
public class FirstRecharge extends BaseModel {
    private Integer id;
    private BigDecimal amount; // 充值的钱
    private Long gold; // 获得金币
    private Integer sort; //排序
    private Long give_gold; // 赠送的数量
    private Integer type; //赠送类型 1：金币 2：转盘
    private Integer isDel; // 是否删除
    private String skuId; // 商品id
}
