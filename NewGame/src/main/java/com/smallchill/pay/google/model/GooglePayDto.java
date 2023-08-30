package com.smallchill.pay.google.model;

public class GooglePayDto {

    Integer cancel; // 用户是否取消，0：未取消，1：取消
    String orderNum; // 我方订单号
    String userId;
    String packageName;
    String productId;
    String purchaseToken;
    String orderId; // 谷歌订单号

    public Integer getCancel() {
        return cancel;
    }

    public void setCancel(Integer cancel) {
        this.cancel = cancel;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPackageName() {
        return packageName;
    }
 
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
 
    public String getProductId() {
        return productId;
    }
 
    public void setProductId(String productId) {
        this.productId = productId;
    }
 
    public String getPurchaseToken() {
        return purchaseToken;
    }
 
    public void setPurchaseToken(String purchaseToken) {
        this.purchaseToken = purchaseToken;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}