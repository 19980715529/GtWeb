package com.smallchill.system.treasure.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import lombok.Data;
import org.beetl.sql.core.annotatoin.Table;

import java.math.BigDecimal;

@Table(name = "AutoReviewConfig")
@BindID(name="id")
@Data
public class AutoAuditConfig extends BaseModel {
    private Integer id;
    private Integer auto;
    private Integer param1;
    private Integer param2;
    private Integer param3;
    private BigDecimal param4;
    private Integer param5;
    private Integer param6;
}
