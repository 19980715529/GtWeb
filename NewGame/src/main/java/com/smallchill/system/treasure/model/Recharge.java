package com.smallchill.system.treasure.model;

import java.math.BigDecimal;
import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;
@DbName(name = "treasure")
@Table(name = "GlobalWebInfo")
@BindID(name = "webId")
@SuppressWarnings("serial")
//充值配置表
public class Recharge extends BaseModel {
	private Integer webId; //唯一标识
	private String productName; //充值名称
	private Integer productType; //充值类型
	private BigDecimal price; //充值金额
	private BigDecimal attachCurrency; //首冲赠送游戏豆
	private Long presentCurrency; //充值获得游戏豆
	private Long extraScore; //额外赠送
	private Integer sortID; //排序
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date collectDate;//添加时间
	
	@AutoID //mysql自增
	@SeqID(name = "SEQ_RECHARGE") //oracle sequence自增
	//两者只需要写一个,根据数据库不同来选择
	public Integer getWebId() {
		return webId;
	}
	public void setWebId(Integer webId) {
		this.webId = webId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getAttachCurrency() {
		return attachCurrency;
	}
	public void setAttachCurrency(BigDecimal attachCurrency) {
		this.attachCurrency = attachCurrency;
	}
	public Long getPresentCurrency() {
		return presentCurrency;
	}
	public void setPresentCurrency(Long presentCurrency) {
		this.presentCurrency = presentCurrency;
	}
	public Long getExtraScore() {
		return extraScore;
	}
	public void setExtraScore(Long extraScore) {
		this.extraScore = extraScore;
	}
	public Integer getSortID() {
		return sortID;
	}
	public void setSortID(Integer sortID) {
		this.sortID = sortID;
	}
	public Date getCollectDate() {
		return collectDate;
	}
	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}
}
