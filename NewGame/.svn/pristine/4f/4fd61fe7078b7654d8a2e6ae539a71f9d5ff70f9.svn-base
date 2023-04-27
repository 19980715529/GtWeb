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
@BindID(name = "webid")
@SuppressWarnings("serial")
//充值类型表
public class GlobalWebInfo extends BaseModel {
	private Integer webid ;
	private Integer producttype ;
	private Integer sortid ;
	private BigDecimal attachcurrency ;
	private Long extrascore ;
	private Long presentcurrency ;
	private BigDecimal price ;
	private String productname ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date collectdate ;
	
	public GlobalWebInfo() {
	}
	@AutoID //mysql自增
	@SeqID(name = "SEQ_GlobalWebInfo") //oracle sequence自增
	public Integer getWebid(){
		return  webid;
	}
	public void setWebid(Integer webid ){
		this.webid = webid;
	}
	
	public Integer getProducttype(){
		return  producttype;
	}
	public void setProducttype(Integer producttype ){
		this.producttype = producttype;
	}
	
	public Integer getSortid(){
		return  sortid;
	}
	public void setSortid(Integer sortid ){
		this.sortid = sortid;
	}
	
	public BigDecimal getAttachcurrency(){
		return  attachcurrency;
	}
	public void setAttachcurrency(BigDecimal attachcurrency ){
		this.attachcurrency = attachcurrency;
	}
	
	public Long getExtrascore(){
		return  extrascore;
	}
	public void setExtrascore(Long extrascore ){
		this.extrascore = extrascore;
	}
	
	public Long getPresentcurrency(){
		return  presentcurrency;
	}
	public void setPresentcurrency(Long presentcurrency ){
		this.presentcurrency = presentcurrency;
	}
	
	public BigDecimal getPrice(){
		return  price;
	}
	public void setPrice(BigDecimal price ){
		this.price = price;
	}
	
	public String getProductname(){
		return  productname;
	}
	public void setProductname(String productname ){
		this.productname = productname;
	}
	
	public Date getCollectdate(){
		return  collectdate;
	}
	public void setCollectdate(Date collectdate ){
		this.collectdate = collectdate;
	}
}
