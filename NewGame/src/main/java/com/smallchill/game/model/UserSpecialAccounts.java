package com.smallchill.game.model;

import org.springframework.stereotype.Component;

@Component
public class UserSpecialAccounts {

    private String UserID ;
    private String NickName;
    private String LastLoginTime;
    private String LastLoginMachine;
    private String InsureScore;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getLastLoginTime() {
        return LastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        LastLoginTime = lastLoginTime;
    }

    public String getLastLoginMachine() {
        return LastLoginMachine;
    }

    public void setLastLoginMachine(String lastLoginMachine) {
        LastLoginMachine = lastLoginMachine;
    }

    public String getInsureScore() {
        return InsureScore;
    }

    public void setInsureScore(String insureScore) {
        InsureScore = insureScore;
    }

    @Override
    public String toString() {
        return "UserSpecialAccounts{" +
                "UserID='" + UserID + '\'' +
                ", NickName='" + NickName + '\'' +
                ", LastLoginTime='" + LastLoginTime + '\'' +
                ", LastLoginMachine='" + LastLoginMachine + '\'' +
                ", InsureScore='" + InsureScore + '\'' +
                '}';
    }
}

