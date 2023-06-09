package com.smallchill.system.treasure.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Date;

@Table(name = "Exchange_review")
@BindID(name = "id")
public class ExchangeReview extends BaseModel {
    private Integer id;
    private String orderNumber; //订单号
    private String pfOrderNum; // 平台订单号
    private Integer userId; // 用户id
    private String phone; // 电话
    private String bankNumber; // 银行卡号
    private String cardholder; // 持卡人
    private String bank; // 银行
    private String sourcePlatform; // 来源平台
    private BigDecimal amount; // 兑换的钱
    private Long gold; // 消耗的金额
    private Long consumptionCode; // 消耗打码量
    private Integer status = 2;//兑换进度，1：待支付，2：待审合，3：完成关闭，4：已完成5：已退回，6: 支付失败，7,失败关闭,8：待发送
    private String feedback; // 反馈
    private String operator; // 操作人员
    private Integer channelId; // 支付渠道id
    private Date createTime; // 创建时间
    private Date endTime; // 完成时间
    private String nickname; //玩家昵称
    private BigDecimal money; //发送的第三方的钱
    private String channelName; // 大渠道名称
    private String msg; // 三方反馈信息
    private String tipsName;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getCardholder() {
        return cardholder;
    }

    public void setCardholder(String cardholder) {
        this.cardholder = cardholder;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getSourcePlatform() {
        return sourcePlatform;
    }

    public void setSourcePlatform(String sourcePlatform) {
        this.sourcePlatform = sourcePlatform;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getGold() {
        return gold;
    }

    public void setGold(Long Gold) {
        this.gold = Gold;
    }

    public Long getConsumptionCode() {
        return consumptionCode;
    }

    public void setConsumptionCode(Long consumptionCode) {
        this.consumptionCode = consumptionCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getPfOrderNum() {
        return pfOrderNum;
    }

    public void setPfOrderNum(String pfOrderNum) {
        this.pfOrderNum = pfOrderNum;
    }

    public String getTipsName() {
        return tipsName;
    }
    public void setTipsName(String tipsName) {
        this.tipsName = tipsName;
    }
}
