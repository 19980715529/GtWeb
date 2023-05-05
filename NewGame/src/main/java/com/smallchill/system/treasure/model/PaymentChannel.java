package com.smallchill.system.treasure.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.Table;

import java.math.BigDecimal;
import java.math.BigInteger;

@Table(name = "paymentChannel")
@BindID(name = "id")
public class PaymentChannel extends BaseModel {
    private Integer id;
    private Integer cid;
    private BigInteger min;
    private BigInteger max;
    private BigDecimal channelTaxRate;
    private Integer isOpen;
    private Integer sort;
    private BigDecimal fee;
    private BigDecimal goldProportion;
    private BigDecimal give;
    private Integer isLabel;
    private String unit;
    private BigDecimal winConf;
    private BigDecimal clientType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public BigInteger getMin() {
        return min;
    }

    public void setMin(BigInteger min) {
        this.min = min;
    }

    public BigInteger getMax() {
        return max;
    }

    public void setMax(BigInteger max) {
        this.max = max;
    }

    public BigDecimal getChannelTaxRate() {
        return channelTaxRate;
    }

    public void setChannelTaxRate(BigDecimal channelTaxRate) {
        this.channelTaxRate = channelTaxRate;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getGoldProportion() {
        return goldProportion;
    }

    public void setGoldProportion(BigDecimal goldProportion) {
        this.goldProportion = goldProportion;
    }

    public BigDecimal getGive() {
        return give;
    }

    public void setGive(BigDecimal give) {
        this.give = give;
    }

    public Integer getIsLabel() {
        return isLabel;
    }

    public void setIsLabel(Integer isLabel) {
        this.isLabel = isLabel;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getWinConf() {
        return winConf;
    }

    public void setWinConf(BigDecimal winConf) {
        this.winConf = winConf;
    }

    public BigDecimal getClientType() {
        return clientType;
    }

    public void setClientType(BigDecimal clientType) {
        this.clientType = clientType;
    }
}
