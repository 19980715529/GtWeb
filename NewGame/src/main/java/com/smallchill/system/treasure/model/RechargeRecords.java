package com.smallchill.system.treasure.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.Table;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Table(name = "Recharge_records")
@BindID(name="id")
public class RechargeRecords extends BaseModel implements Delayed {

    private Integer id; // 充值id
    private String  orderNumber; // 订单id
    private Integer userId; // 用户id
    private BigDecimal topUpAmount; // 充值金额
    private Long  gold; // 获得金币
    private Integer orderStatus = 1; // 支付状态
    private Integer packageName; // 包名称
    private String channel; // 渠道
    private String channel_type; // 渠道类型
    private Date createTime =new Date(); // 创建时间
    private Date endTime; // 结束时间
    private String pfOrderNum; // 平台订单号
    private String urlPay; // 充值路径
    private Integer isFirstCharge; // 是否首充
    private Integer channelPid; // 用于统计不同商家的订单情况
    private Integer isThatTay; // 是否是注册当天充值
    private String msg;  // 三方反馈信息
    private String phone; // 电话
    private String operator; // 操作人员
    private Integer gear; // 操作人员

    private Long giftGold; // 赠送金币

    public Long getGiftGold() {
        return giftGold;
    }

    public void setGiftGold(Long giftGold) {
        this.giftGold = giftGold;
    }

    public Integer getGear() {
        return gear;
    }

    public void setGear(Integer gear) {
        this.gear = gear;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getIsThatTay() {
        return isThatTay;
    }

    public void setIsThatTay(Integer isThatTay) {
        this.isThatTay = isThatTay;
    }

    public Integer getChannelPid() {
        return channelPid;
    }

    public void setChannelPid(Integer channelPid) {
        this.channelPid = channelPid;
    }

    private Date afterDate = new Date(createTime.getTime() + 900000);



    public Integer getIsFirstCharge() {
        return isFirstCharge;
    }

    public void setIsFirstCharge(Integer isFirstCharge) {
        this.isFirstCharge = isFirstCharge;
    }

    public String getPfOrderNum() {
        return pfOrderNum;
    }

    public void setPfOrderNum(String pfOrderNum) {
        this.pfOrderNum = pfOrderNum;
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

    public BigDecimal getTopUpAmount() {
        return topUpAmount;
    }

    public void setTopUpAmount(BigDecimal topUpAmount) {
        this.topUpAmount = topUpAmount;
    }

    public Long getGold() {
        return gold;
    }

    public void setGold(Long gold) {
        this.gold = gold;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPackageName() {
        return packageName;
    }

    public void setPackageName(Integer packageName) {
        this.packageName = packageName;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannel_type() {
        return channel_type;
    }

    public void setChannel_type(String channel_type) {
        this.channel_type = channel_type;
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

    public String getUrlPay() {
        return urlPay;
    }

    public void setUrlPay(String urlPay) {
        this.urlPay = urlPay;
    }

    public Date getAfterDate() {
        return afterDate;
    }

    public void setAfterDate(Date afterDate) {
        this.afterDate = afterDate;
    }

    @Override
    public long getDelay(@NotNull TimeUnit unit) {
        // 过期时间
        long expire = createTime.getTime()+TimeUnit.MINUTES.toMillis(5);
        return unit.convert(afterDate.getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(@NotNull Delayed o) {
        return afterDate.compareTo(((RechargeRecords) o).getAfterDate());
    }
}
