package com.smallchill.system.treasure.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.Table;

@Table(name = "CheckIn_CheckInAward")
@DbName(name = "gameroomitemdb")
@BindID(name = "id")
public class CheckConfig extends BaseModel {

    private Integer id;
    private Integer Day;
    private Long Award;
    private Integer AwardType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDay() {
        return Day;
    }

    public void setDay(Integer day) {
        Day = day;
    }

    public Long getAward() {
        return Award;
    }

    public void setAward(Long award) {
        Award = award;
    }

    public Integer getAwardType() {
        return AwardType;
    }

    public void setAwardType(Integer awardType) {
        AwardType = awardType;
    }
}
