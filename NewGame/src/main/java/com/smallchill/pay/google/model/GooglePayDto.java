package com.smallchill.pay.google.model;

public class GooglePayDto {
 
    String packageName;
    String productId;
    String purchaseToken;
 
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
}