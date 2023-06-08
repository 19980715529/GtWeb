package com.smallchill.system.treasure.model;

import com.smallchill.core.annotation.BindID;
import lombok.Data;
import org.beetl.sql.core.annotatoin.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;


@Table(name = "Pay_walletRecords")
@BindID(name = "id")
@Data
public class WalletRecords {

    private Integer id;
    private String channelName;//大渠道名称
    private Integer mcId;//商户id
    private BigDecimal collectRate;//代收费率
    private BigDecimal paymentRate;//代付费率
    private BigDecimal payFee;//代付附加费用
    private BigDecimal collectAmount=new BigDecimal("0");//代收数额
    private BigDecimal  collectFee=new BigDecimal("0");//代收手续费
    private Integer sucNum=0;//成功次数
    private BigDecimal paymentAmount=new BigDecimal("0");//代付数额
    private BigDecimal paymentFee=new BigDecimal("0");//代付手续费
    private Integer paymentNum=0;//代付次数
    private BigDecimal netProfit=new BigDecimal("0");//净利润
    private BigDecimal totalFee=new BigDecimal("0");//总手续费
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createTime=new Date();//创建时间
    private Integer collectNum=0;//代收次数
    private Integer clientType;//包id
}
