package com.smallchill.system.treasure.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import lombok.Data;
import org.beetl.sql.core.annotatoin.Table;

/**
 * @Description TODO
 * @classNameAnnouncementManagement
 * @Date 2023/3/21 11:15
 * @Version 1.0
 **/
@Table(name = "announcement_management")
@BindID(name = "id")
@Data
public class AnnouncementManagement extends BaseModel {
    private Integer id;
    private String title;
    private String content;
    private Integer isOpen;
    private Integer clientType;
    private Integer code;
}
