package com.smallchill.system.treasure.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import lombok.Data;
import org.beetl.sql.core.annotatoin.Table;

/**
 * 大渠道ORM
 */
@Table(name = "Pay_channel")
@BindID(name = "id")
@Data
public class PayChannel extends BaseModel {
    private Integer id;
    private String cname; // 名称
    private String exchangeGear; // 挡位
    private Integer isRecharge; // 充值是否开启
    private Integer isExchange; // 兑换
    private Integer sort; // 排序
    private String code; // 编码
    private Integer isDel=0; // 是否删除
}
