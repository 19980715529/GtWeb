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
@Table(name = "ShareDetailInfo")
@BindID(name = "detailid")
@SuppressWarnings("serial")
//充值订单记录表
public class ShareDetailInfo extends BaseModel {
	private Integer detailid ;
	private Integer beforeroomcard ;
	private Integer cardtypeid ;
	private Integer gameid ;
	private Integer gold ;
	private Integer operuserid ;
	private Integer roomcard ;
	private Integer shareid ;
	private Integer userid ;
	private String accounts ;
	private BigDecimal beforecurrency ;
	private Long beforegold ;
	private BigDecimal currency ;
	private BigDecimal discountscale ;
	private String ipaddress ;
	private BigDecimal orderamount ;
	private String orderid ;
	private BigDecimal payamount ;
	private String serialid ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date applydate ;
	
	public ShareDetailInfo() {
	}
	@AutoID //mysql自增
	@SeqID(name = "SEQ_ShareDetailInfo") //oracle sequence自增
	public Integer getDetailid(){
		return  detailid;
	}
	public void setDetailid(Integer detailid ){
		this.detailid = detailid;
	}
	
	public Integer getBeforeroomcard(){
		return  beforeroomcard;
	}
	public void setBeforeroomcard(Integer beforeroomcard ){
		this.beforeroomcard = beforeroomcard;
	}
	
	public Integer getCardtypeid(){
		return  cardtypeid;
	}
	public void setCardtypeid(Integer cardtypeid ){
		this.cardtypeid = cardtypeid;
	}
	
	public Integer getGameid(){
		return  gameid;
	}
	public void setGameid(Integer gameid ){
		this.gameid = gameid;
	}
	
	public Integer getGold(){
		return  gold;
	}
	public void setGold(Integer gold ){
		this.gold = gold;
	}
	
	public Integer getOperuserid(){
		return  operuserid;
	}
	public void setOperuserid(Integer operuserid ){
		this.operuserid = operuserid;
	}
	
	public Integer getRoomcard(){
		return  roomcard;
	}
	public void setRoomcard(Integer roomcard ){
		this.roomcard = roomcard;
	}
	
	public Integer getShareid(){
		return  shareid;
	}
	public void setShareid(Integer shareid ){
		this.shareid = shareid;
	}
	
	public Integer getUserid(){
		return  userid;
	}
	public void setUserid(Integer userid ){
		this.userid = userid;
	}
	
	public String getAccounts(){
		return  accounts;
	}
	public void setAccounts(String accounts ){
		this.accounts = accounts;
	}
	
	public BigDecimal getBeforecurrency(){
		return  beforecurrency;
	}
	public void setBeforecurrency(BigDecimal beforecurrency ){
		this.beforecurrency = beforecurrency;
	}
	
	public Long getBeforegold(){
		return  beforegold;
	}
	public void setBeforegold(Long beforegold ){
		this.beforegold = beforegold;
	}
	
	public BigDecimal getCurrency(){
		return  currency;
	}
	public void setCurrency(BigDecimal currency ){
		this.currency = currency;
	}
	
	public BigDecimal getDiscountscale(){
		return  discountscale;
	}
	public void setDiscountscale(BigDecimal discountscale ){
		this.discountscale = discountscale;
	}
	
	public String getIpaddress(){
		return  ipaddress;
	}
	public void setIpaddress(String ipaddress ){
		this.ipaddress = ipaddress;
	}
	
	public BigDecimal getOrderamount(){
		return  orderamount;
	}
	public void setOrderamount(BigDecimal orderamount ){
		this.orderamount = orderamount;
	}
	
	public String getOrderid(){
		return  orderid;
	}
	public void setOrderid(String orderid ){
		this.orderid = orderid;
	}
	
	public BigDecimal getPayamount(){
		return  payamount;
	}
	public void setPayamount(BigDecimal payamount ){
		this.payamount = payamount;
	}
	
	public String getSerialid(){
		return  serialid;
	}
	public void setSerialid(String serialid ){
		this.serialid = serialid;
	}
	
	public Date getApplydate(){
		return  applydate;
	}
	public void setApplydate(Date applydate ){
		this.applydate = applydate;
	}
}
