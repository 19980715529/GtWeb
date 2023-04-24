package com.smallchill.system.treasure.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;

/**
 * @Description TODO
 * @classNameGoldChangeRecord
 * @Date 2023/3/16 10:13
 * @Version 1.0
 **/
@DbName(name = "gamerecorddb")
@Table(name="GoldChangeRecord")
@BindID(name = "id")
public class GoldChangeRecord extends BaseModel {
    private Integer id;
    private Integer User_Id;
    private Long PreAmount;
    private Long Amount;
    private Long AftAmount;
    private String Remark;
    private Date LogTime;
    private Integer ChangeType_Id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(Integer user_Id) {
        User_Id = user_Id;
    }

    public Long getPreAmount() {
        return PreAmount;
    }

    public void setPreAmount(Long preAmount) {
        PreAmount = preAmount;
    }

    public Long getAmount() {
        return Amount;
    }

    public void setAmount(Long amount) {
        Amount = amount;
    }

    public Long getAftAmount() {
        return AftAmount;
    }

    public void setAftAmount(Long aftAmount) {
        AftAmount = aftAmount;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public Date getLogTime() {
        return LogTime;
    }

    public void setLogTime(Date logTime) {
        LogTime = logTime;
    }

    public Integer getChangeType_Id() {
        return ChangeType_Id;
    }

    public void setChangeType_Id(Integer changeType_Id) {
        ChangeType_Id = changeType_Id;
    }
}
