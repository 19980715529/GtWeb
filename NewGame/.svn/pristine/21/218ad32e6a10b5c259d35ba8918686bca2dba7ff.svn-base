package com.smallchill.db.config.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.Table;

import java.math.BigDecimal;

/**
 * @Description TODO
 * @classNameFirstRecharge
 * @Date 2023/2/25 17:39
 * @Version 1.0
 **/
@Table(name = "First_charge_config")
@BindID(name = "id")
public class FirstRecharge extends BaseModel {
    private Integer id;
    private BigDecimal amount; // 充值的钱
    private Long gold; // 获得金币
    private Integer sort; //排序
    private Long give_gold; // 赠送的数量
    private Integer type; //赠送类型 1：金币 2：转盘

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getGold() {
        return gold;
    }

    public void setGold(Long gold) {
        this.gold = gold;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getGive_gold() {
        return give_gold;
    }

    public void setGive_gold(Long give_gold) {
        this.give_gold = give_gold;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
