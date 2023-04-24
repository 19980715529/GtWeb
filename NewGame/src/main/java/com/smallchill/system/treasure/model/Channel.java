package com.smallchill.system.treasure.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.Table;

import java.math.BigDecimal;

/**
 * @Description TODO
 * @classNameChannel
 * @Date 2023/2/17 18:02
 * @Version 1.0
 **/
@Table(name = "Channel")
@BindID(name="id")
public class Channel extends BaseModel {
    private Integer id;
    private String mcName;
    private String name;//小渠道名称
    private String type; // 判断删除：0充值，1兑换
    private Integer isOpen=1; // 是否开启
    private Integer sort; //游戏中排序
    private Long min = 0L; // 最小充值
    private Long max = 0L; //单次最大充值
    private Float channelTaxRate = 0.0F; // 汇率
    private Float give; //赠送
    private Integer pid; //父id
    private Float fee; // 费用
    private String channelName; // 大渠道名称
    private Float goldProportion; // 金币倍率
    private String code; // 渠道编码
    private String unit; // 单位
    private Integer isLabel; // 是否显示hot标签
    private BigDecimal winConf;

    public BigDecimal getWinConf() {
        return winConf;
    }

    public void setWinConf(BigDecimal winConf) {
        this.winConf = winConf;
    }

    public Integer getIsLabel() {
        return isLabel;
    }

    public void setIsLabel(Integer isLabel) {
        this.isLabel = isLabel;
    }

    public String getMcName() {
        return mcName;
    }

    public void setMcName(String mcName) {
        this.mcName = mcName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Long getMin() {
        return min;
    }

    public void setMin(Long min) {
        this.min = min;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    public Float getChannelTaxRate() {
        return channelTaxRate;
    }

    public void setChannelTaxRate(Float channelTaxRate) {
        this.channelTaxRate = channelTaxRate;
    }

    public Float getGive() {
        return give;
    }

    public void setGive(Float give) {
        this.give = give;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Float getFee() {
        return fee;
    }

    public void setFee(Float fee) {
        this.fee = fee;
    }


    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Float getGoldProportion() {
        return goldProportion;
    }

    public void setGoldProportion(Float goldProportion) {
        this.goldProportion = goldProportion;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
}
