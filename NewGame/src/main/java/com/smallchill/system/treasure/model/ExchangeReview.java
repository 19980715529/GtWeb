package com.smallchill.system.treasure.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import lombok.Data;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Date;

@Table(name = "Exchange_review")
@Data
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
    private Integer sourcePlatform; // 包id
    private BigDecimal amount; // 兑换的钱
    private Long gold; // 消耗的金额
    private Long consumptionCode; // 消耗打码量
    private Integer status = 2;//兑换进度，1：待支付，2：待审合，3：完成关闭，4：已完成5：已退回，6: 支付失败，7,失败关闭,8：待发送
    private String feedback; // 反馈
    private String operator; // 操作人员
    private Integer channelId; // 支付渠道id
    private Date createTime; // 创建时间
    private Date endTime; // 完成时间
    private BigDecimal money; //发送的第三方的钱
    private String channelName; // 大渠道名称
    private String msg; // 三方反馈信息
    private Integer auditMethod; // 三方反馈信息
}
