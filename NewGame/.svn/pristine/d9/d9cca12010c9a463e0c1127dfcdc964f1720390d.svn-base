package com.smallchill.game.newmodel.treasuredb;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;

@DbName(name = "treasuredb")
@Table(name = "SpecialAccounts")
@BindID(name = "id")
public class SpecialAccounts {
    private int id;
    private int NowHasID;
    private int OnceID;
    private Date UpdateDate;

    public SpecialAccounts(){}
    @AutoID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNowHasID() {
        return NowHasID;
    }

    public void setNowHasID(int nowHasID) {
        NowHasID = nowHasID;
    }

    public int getOnceID() {
        return OnceID;
    }

    public void setOnceID(int onceID) {
        OnceID = onceID;
    }

    public Date getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(Date updateDate) {
        UpdateDate = updateDate;
    }

    @Override
    public String toString() {
        return "SpecialAccounts{" +
                "id=" + id +
                ", NowHasID=" + NowHasID +
                ", OnceID=" + OnceID +
                ", UpdateDate=" + UpdateDate +
                '}';
    }
}
