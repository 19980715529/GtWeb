package com.smallchill.game.model.request;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;
@DbName(name = "gameuserdb")
@Table(name = "AA_ExchangeCode_Generate")
@BindID(name = "id")
public class ServerGenerated {
    int id;
    String UserID;
    String ExchangeBatch;
    int ExchangeNum;
    Date ExchangeGenerate;
    @AutoID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getExchangeBatch() {
        return ExchangeBatch;
    }

    public void setExchangeBatch(String exchangeBatch) {
        ExchangeBatch = exchangeBatch;
    }

    public int getExchangeNum() {
        return ExchangeNum;
    }

    public void setExchangeNum(int exchangeNum) {
        ExchangeNum = exchangeNum;
    }

    public Date getExchangeGenerate() {
        return ExchangeGenerate;
    }

    public void setExchangeGenerate(Date exchangeGenerate) {
        ExchangeGenerate = exchangeGenerate;
    }

    @Override
    public String toString() {
        return "ServerGenerated{" +
                "id=" + id +
                ", UserID='" + UserID + '\'' +
                ", ExchangeBatch='" + ExchangeBatch + '\'' +
                ", ExchangeNum=" + ExchangeNum +
                ", ExchangeGenerate=" + ExchangeGenerate +
                '}';
    }
}
