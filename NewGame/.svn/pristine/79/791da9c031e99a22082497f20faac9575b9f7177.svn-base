package com.smallchill.db.config.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.Table;

/**
 * @Description TODO
 * @classNameActiveList
 * @Date 2023/2/20 18:01
 * @Version 1.0
 **/
@DbName(name = "gameroomitemdb")
@Table(name = "ActiveList")
@BindID(name = "id")
public class ActiveList extends BaseModel {
    private Integer id;
    private Integer activeID; // 活动id
    private Integer subActiveID; // 子活动id
    private String activeName; // 活动名称
    private Integer param1; // 参数1
    private Integer param2; // 参数2
    private Integer param3; // 参数3
    private Integer isOpen = 1; // 是否开启
    private Integer reward; //  活动奖励
    private Integer rewardType; // 活动类型

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActiveID() {
        return activeID;
    }

    public void setActiveID(Integer activeID) {
        this.activeID = activeID;
    }

    public Integer getSubActiveID() {
        return subActiveID;
    }

    public void setSubActiveID(Integer subActiveID) {
        this.subActiveID = subActiveID;
    }

    public String getActiveName() {
        return activeName;
    }

    public void setActiveName(String activeName) {
        this.activeName = activeName;
    }

    public Integer getParam1() {
        return param1;
    }

    public void setParam1(Integer param1) {
        this.param1 = param1;
    }

    public Integer getParam2() {
        return param2;
    }

    public void setParam2(Integer param2) {
        this.param2 = param2;
    }

    public Integer getParam3() {
        return param3;
    }

    public void setParam3(Integer param3) {
        this.param3 = param3;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public Integer getReward() {
        return reward;
    }

    public void setReward(Integer reward) {
        this.reward = reward;
    }

    public Integer getRewardType() {
        return rewardType;
    }

    public void setRewardType(Integer rewardType) {
        this.rewardType = rewardType;
    }
}
