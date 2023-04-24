package com.smallchill.db.config.model;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "platform")
@Table(name = "BloodPondConfig")
@BindID(name = "id")
@SuppressWarnings("serial")
public class Bloodpondconfig extends BaseModel {
	private Integer id ;
	private Integer controlnullity ;
	private Integer flasealerttimes1 ;
	private Integer flasealerttimes2 ;
	private Integer flasecheatate1 ;
	private Integer flasecheatate2 ;
	private Integer flasecheatate3 ;
	private Integer ismustcontrol ;
	private Integer nullity ;
	private Integer state ;
	private Integer truealerttimes1 ;
	private Integer truealerttimes2 ;
	private Integer truecheatate1 ;
	private Integer truecheatate2 ;
	private Integer truecheatate3 ;
	private Long bloodpondbank ;
	private Long bloodpondnowcontrolcount ;
	private Long bloodpondnowcount ;
	private Long bloodpondval ;
	private String bloodpondbalancename ;
	private Long bloodpondbalanceval ;
	private Long bloodpondbalancevalout ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date begintime ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date createtime ;
	
	public Bloodpondconfig() {
	}
	
	@AutoID
	@SeqID(name = "SEQ_BLOOD")
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public Integer getControlnullity(){
		return  controlnullity;
	}
	public void setControlnullity(Integer controlnullity ){
		this.controlnullity = controlnullity;
	}
	
	public Integer getFlasealerttimes1(){
		return  flasealerttimes1;
	}
	public void setFlasealerttimes1(Integer flasealerttimes1 ){
		this.flasealerttimes1 = flasealerttimes1;
	}
	
	public Integer getFlasealerttimes2(){
		return  flasealerttimes2;
	}
	public void setFlasealerttimes2(Integer flasealerttimes2 ){
		this.flasealerttimes2 = flasealerttimes2;
	}
	
	public Integer getFlasecheatate1(){
		return  flasecheatate1;
	}
	public void setFlasecheatate1(Integer flasecheatate1 ){
		this.flasecheatate1 = flasecheatate1;
	}
	
	public Integer getFlasecheatate2(){
		return  flasecheatate2;
	}
	public void setFlasecheatate2(Integer flasecheatate2 ){
		this.flasecheatate2 = flasecheatate2;
	}
	
	public Integer getFlasecheatate3(){
		return  flasecheatate3;
	}
	public void setFlasecheatate3(Integer flasecheatate3 ){
		this.flasecheatate3 = flasecheatate3;
	}
	
	public Integer getIsmustcontrol(){
		return  ismustcontrol;
	}
	public void setIsmustcontrol(Integer ismustcontrol ){
		this.ismustcontrol = ismustcontrol;
	}
	
	public Integer getNullity(){
		return  nullity;
	}
	public void setNullity(Integer nullity ){
		this.nullity = nullity;
	}
	
	public Integer getState(){
		return  state;
	}
	public void setState(Integer state ){
		this.state = state;
	}
	
	public Integer getTruealerttimes1(){
		return  truealerttimes1;
	}
	public void setTruealerttimes1(Integer truealerttimes1 ){
		this.truealerttimes1 = truealerttimes1;
	}
	
	public Integer getTruealerttimes2(){
		return  truealerttimes2;
	}
	public void setTruealerttimes2(Integer truealerttimes2 ){
		this.truealerttimes2 = truealerttimes2;
	}
	
	public Integer getTruecheatate1(){
		return  truecheatate1;
	}
	public void setTruecheatate1(Integer truecheatate1 ){
		this.truecheatate1 = truecheatate1;
	}
	
	public Integer getTruecheatate2(){
		return  truecheatate2;
	}
	public void setTruecheatate2(Integer truecheatate2 ){
		this.truecheatate2 = truecheatate2;
	}
	
	public Integer getTruecheatate3(){
		return  truecheatate3;
	}
	public void setTruecheatate3(Integer truecheatate3 ){
		this.truecheatate3 = truecheatate3;
	}
	
	public Long getBloodpondbank(){
		return  bloodpondbank;
	}
	public void setBloodpondbank(Long bloodpondbank ){
		this.bloodpondbank = bloodpondbank;
	}
	
	public Long getBloodpondnowcontrolcount(){
		return  bloodpondnowcontrolcount;
	}
	public void setBloodpondnowcontrolcount(Long bloodpondnowcontrolcount ){
		this.bloodpondnowcontrolcount = bloodpondnowcontrolcount;
	}
	
	public Long getBloodpondnowcount(){
		return  bloodpondnowcount;
	}
	public void setBloodpondnowcount(Long bloodpondnowcount ){
		this.bloodpondnowcount = bloodpondnowcount;
	}
	
	public Long getBloodpondval(){
		return  bloodpondval;
	}
	public void setBloodpondval(Long bloodpondval ){
		this.bloodpondval = bloodpondval;
	}
	
	public String getBloodpondbalancename(){
		return  bloodpondbalancename;
	}
	public void setBloodpondbalancename(String bloodpondbalancename ){
		this.bloodpondbalancename = bloodpondbalancename;
	}
	
	public Long getBloodpondbalanceval(){
		return  bloodpondbalanceval;
	}
	public void setBloodpondbalanceval(Long bloodpondbalanceval ){
		this.bloodpondbalanceval = bloodpondbalanceval;
	}
	
	public Long getBloodpondbalancevalout(){
		return  bloodpondbalancevalout;
	}
	public void setBloodpondbalancevalout(Long bloodpondbalancevalout ){
		this.bloodpondbalancevalout = bloodpondbalancevalout;
	}
	
	public Date getBegintime(){
		return  begintime;
	}
	public void setBegintime(Date begintime ){
		this.begintime = begintime;
	}
	
	public Date getCreatetime(){
		return  createtime;
	}
	public void setCreatetime(Date createtime ){
		this.createtime = createtime;
	}
}
