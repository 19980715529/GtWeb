package com.smallchill.db.config.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

/**
 * @Description TODO
 * @classNameTurntAble
 * @Date 2023/2/23 14:54
 * @Version 1.0
 **/
@DbName(name = "gameroomitemdb")
@Table(name = "TurntableRewardTypeConfig")
@BindID(name = "id")
public class TurnTable extends BaseModel {
    @SeqID(name = "id")
    private Integer id;
    private Integer sourceType;
    private Integer rewardType;
    private Integer mediumWeight;
    private Long amountOfReward;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getRewardType() {
        return rewardType;
    }

    public void setRewardType(Integer rewardType) {
        this.rewardType = rewardType;
    }

    public Integer getMediumWeight() {
        return mediumWeight;
    }

    public void setMediumWeight(Integer mediumWeight) {
        this.mediumWeight = mediumWeight;
    }

    public Long getAmountOfReward() {
        return amountOfReward;
    }

    public void setAmountOfReward(Long amountOfReward) {
        this.amountOfReward = amountOfReward;
    }
}
