package com.smallchill.db.config.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;

/**
 * @Description TODO
 * @classNameEmailRecords
 * @Date 2023/3/14 21:00
 * @Version 1.0
 **/
@Table(name = "email_records")
@BindID(name = "id")
public class EmailRecords extends BaseModel {
    private Integer id;
    private String title; // 标题
    private String content; // 内容
    private Integer emailType; //  根据表 QPGameUserDB.dbo.AA_ZZ_Log_PropChange_ChangeType表示
    private String emailSender; // 邮件上的发件人
    private Integer range; // 发送范围: 0:全服，1：A，2：B，3，C
    private Date sendTime = new Date(); // 发送时间
    private String sender; // 发送人，管理员发送填写管理员名称，系统发送填写system
    private Integer attachment; // 附件类型：1：金币，2：转盘
    private Integer amount; // 数量
    private Integer userId; // 玩家id
    private Integer senderId; // 发件id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getEmailType() {
        return emailType;
    }

    public void setEmailType(Integer emailType) {
        this.emailType = emailType;
    }

    public String getEmailSender() {
        return emailSender;
    }

    public void setEmailSender(String emailSender) {
        this.emailSender = emailSender;
    }

    public Integer getRange() {
        return range;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Integer getAttachment() {
        return attachment;
    }

    public void setAttachment(Integer attachment) {
        this.attachment = attachment;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }
}
