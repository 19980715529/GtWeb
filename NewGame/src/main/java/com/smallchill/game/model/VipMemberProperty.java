package com.smallchill.game.model;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "account")
@Table(name = "MemberProperty")
@BindID(name = "memberorder")
@SuppressWarnings("serial")
public class VipMemberProperty extends BaseModel {
	private Integer memberorder ;
	private Integer daygiftid ;
	private Integer daypresent ;
	private Integer gift ;
	private Integer insurerate ;
	private Integer littlelama ;
	private Integer notice ;
	private Integer onlinereward ;
	private Integer relieffund ;
	private Integer shoprate ;
	private Integer sign ;
	private Integer taskrate ;
	private Integer userright ;
	private Integer visiting ;
	private String collectnote ;
	private String membername ;
	private Long scorecount ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date collectdate ;
	
	public VipMemberProperty() {
	}
	@AutoID
	@SeqID(name = "SEQ_VipMemberProperty")
	public Integer getMemberorder(){
		return  memberorder;
	}
	public void setMemberorder(Integer memberorder ){
		this.memberorder = memberorder;
	}
	
	public Integer getDaygiftid(){
		return  daygiftid;
	}
	public void setDaygiftid(Integer daygiftid ){
		this.daygiftid = daygiftid;
	}
	
	public Integer getDaypresent(){
		return  daypresent;
	}
	public void setDaypresent(Integer daypresent ){
		this.daypresent = daypresent;
	}
	
	public Integer getGift(){
		return  gift;
	}
	public void setGift(Integer gift ){
		this.gift = gift;
	}
	
	public Integer getInsurerate(){
		return  insurerate;
	}
	public void setInsurerate(Integer insurerate ){
		this.insurerate = insurerate;
	}
	
	public Integer getLittlelama(){
		return  littlelama;
	}
	public void setLittlelama(Integer littlelama ){
		this.littlelama = littlelama;
	}
	
	public Integer getNotice(){
		return  notice;
	}
	public void setNotice(Integer notice ){
		this.notice = notice;
	}
	
	public Integer getOnlinereward(){
		return  onlinereward;
	}
	public void setOnlinereward(Integer onlinereward ){
		this.onlinereward = onlinereward;
	}
	
	public Integer getRelieffund(){
		return  relieffund;
	}
	public void setRelieffund(Integer relieffund ){
		this.relieffund = relieffund;
	}
	
	public Integer getShoprate(){
		return  shoprate;
	}
	public void setShoprate(Integer shoprate ){
		this.shoprate = shoprate;
	}
	
	public Integer getSign(){
		return  sign;
	}
	public void setSign(Integer sign ){
		this.sign = sign;
	}
	
	public Integer getTaskrate(){
		return  taskrate;
	}
	public void setTaskrate(Integer taskrate ){
		this.taskrate = taskrate;
	}
	
	public Integer getUserright(){
		return  userright;
	}
	public void setUserright(Integer userright ){
		this.userright = userright;
	}
	
	public Integer getVisiting(){
		return  visiting;
	}
	public void setVisiting(Integer visiting ){
		this.visiting = visiting;
	}
	
	public String getCollectnote(){
		return  collectnote;
	}
	public void setCollectnote(String collectnote ){
		this.collectnote = collectnote;
	}
	
	public String getMembername(){
		return  membername;
	}
	public void setMembername(String membername ){
		this.membername = membername;
	}
	
	public Long getScorecount(){
		return  scorecount;
	}
	public void setScorecount(Long scorecount ){
		this.scorecount = scorecount;
	}
	
	public Date getCollectdate(){
		return  collectdate;
	}
	public void setCollectdate(Date collectdate ){
		this.collectdate = collectdate;
	}
}
