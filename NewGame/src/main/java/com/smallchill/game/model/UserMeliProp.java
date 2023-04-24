package com.smallchill.game.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;

@DbName(name = "gameuserdb")
@Table(name = "UserMeliProp")
@BindID(name = "userid")
@SuppressWarnings("serial")
public class UserMeliProp extends BaseModel {
    private Integer userid ;
    private Integer meilipropcount ;
    private Integer tadyusecount;
    private Date changetime;

    public UserMeliProp(){}
    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getMeilipropcount() {
        return meilipropcount;
    }

    public void setMeilipropcount(Integer meilipropcount) {
        this.meilipropcount = meilipropcount;
    }

    public Integer getTadyusecount() {
        return tadyusecount;
    }

    public void setTadyusecount(Integer tadyusecount) {
        this.tadyusecount = tadyusecount;
    }

    public Date getChangetime() {
        return changetime;
    }

    public void setChangetime(Date changetime) {
        this.changetime = changetime;
    }

    @Override
    public String toString() {
        return "UserMeliProp{" +
                "userid=" + userid +
                ", meilipropcount=" + meilipropcount +
                ", tadyusecount=" + tadyusecount +
                ", changetime='" + changetime + '\'' +
                '}';
    }
}
