package com.smallchill.proxy.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import lombok.Data;
import org.beetl.sql.core.annotatoin.Table;

@Table(name = "TransferConfig")
@BindID(name = "id")
@Data
public class TransferConfig extends BaseModel {
    private Integer id;
    private Long level1;
    private Long level2;
    private Long level3;
    private Long level4;
    private Long level5;
    private Long level6;
    private Long level7;
    private Long level8;
    private Integer isOpen;
    private Integer agentId;
}
