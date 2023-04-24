package com.smallchill.game.model.request;

public class WithdrawalRequest {
    String orderID;
    String UserID;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    @Override
    public String toString() {
        return "WithdrawalRequest{" +
                "orderID='" + orderID + '\'' +
                ", UserID='" + UserID + '\'' +
                '}';
    }
}
