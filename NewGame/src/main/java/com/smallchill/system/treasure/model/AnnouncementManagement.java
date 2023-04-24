package com.smallchill.system.treasure.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.Table;

/**
 * @Description TODO
 * @classNameAnnouncementManagement
 * @Date 2023/3/21 11:15
 * @Version 1.0
 **/
@Table(name = "announcement_management")
@BindID(name = "id")
public class AnnouncementManagement extends BaseModel {
    private Integer id;
    private String title;
    private String content;
    private Integer isOpen;

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

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
}
