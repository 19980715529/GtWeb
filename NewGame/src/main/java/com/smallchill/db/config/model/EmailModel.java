package com.smallchill.db.config.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.Table;

/**
 * @Description TODO
 * @classNameEmailModel
 * @Date 2023/3/14 19:08
 * @Version 1.0
 **/
@Table(name = "email_model")
@BindID(name = "id")
public class EmailModel extends BaseModel {
    private Integer id;
    private String title;// 邮件标题
    private String content; // 邮件内容
    private Integer amount;  // 数量
    private Integer attachment;  // 附件类型1:金币2：转盘
    private Integer senderId;  // 发件人

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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAttachment() {
        return attachment;
    }

    public void setAttachment(Integer attachment) {
        this.attachment = attachment;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }
}
