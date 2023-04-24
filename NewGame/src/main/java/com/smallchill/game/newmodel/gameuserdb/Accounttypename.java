package com.smallchill.game.newmodel.gameuserdb;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameuserdb")
@Table(name = "AccountTypeName")
@BindID(name = "clientType")
@SuppressWarnings("serial")
public class Accounttypename extends BaseModel {
	private Integer iswhitelistenabled ;
	private Integer checkstaus ;
	private Integer clienttype ;
	private Integer hottimes ;
	private Integer rankresultlevel ;
	private Integer rankstaus ;
	private Integer ranktimes ;
	private Integer updatetimes ;
	private String abcd ;
	private String asolink ;
	private String desc ;
	private String hotword ;
	private String lay1 ;
	private String lay2 ;
	private String link ;
	private String moneyhotwords ;
	private String name ;
	private String subname ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date oktime ;
	
	public Accounttypename() {
	}
	
	public Integer getIswhitelistenabled(){
		return  iswhitelistenabled;
	}
	public void setIswhitelistenabled(Integer iswhitelistenabled ){
		this.iswhitelistenabled = iswhitelistenabled;
	}
	
	public Integer getCheckstaus(){
		return  checkstaus;
	}
	public void setCheckstaus(Integer checkstaus ){
		this.checkstaus = checkstaus;
	}
	@AssignID
	public Integer getClienttype(){
		return  clienttype;
	}
	public void setClienttype(Integer clienttype ){
		this.clienttype = clienttype;
	}
	
	public Integer getHottimes(){
		return  hottimes;
	}
	public void setHottimes(Integer hottimes ){
		this.hottimes = hottimes;
	}
	
	public Integer getRankresultlevel(){
		return  rankresultlevel;
	}
	public void setRankresultlevel(Integer rankresultlevel ){
		this.rankresultlevel = rankresultlevel;
	}
	
	public Integer getRankstaus(){
		return  rankstaus;
	}
	public void setRankstaus(Integer rankstaus ){
		this.rankstaus = rankstaus;
	}
	
	public Integer getRanktimes(){
		return  ranktimes;
	}
	public void setRanktimes(Integer ranktimes ){
		this.ranktimes = ranktimes;
	}
	
	public Integer getUpdatetimes(){
		return  updatetimes;
	}
	public void setUpdatetimes(Integer updatetimes ){
		this.updatetimes = updatetimes;
	}
	
	public String getAbcd(){
		return  abcd;
	}
	public void setAbcd(String abcd ){
		this.abcd = abcd;
	}
	
	public String getAsolink(){
		return  asolink;
	}
	public void setAsolink(String asolink ){
		this.asolink = asolink;
	}
	
	public String getDesc(){
		return  desc;
	}
	public void setDesc(String desc ){
		this.desc = desc;
	}
	
	public String getHotword(){
		return  hotword;
	}
	public void setHotword(String hotword ){
		this.hotword = hotword;
	}
	
	public String getLay1(){
		return  lay1;
	}
	public void setLay1(String lay1 ){
		this.lay1 = lay1;
	}
	
	public String getLay2(){
		return  lay2;
	}
	public void setLay2(String lay2 ){
		this.lay2 = lay2;
	}
	
	public String getLink(){
		return  link;
	}
	public void setLink(String link ){
		this.link = link;
	}
	
	public String getMoneyhotwords(){
		return  moneyhotwords;
	}
	public void setMoneyhotwords(String moneyhotwords ){
		this.moneyhotwords = moneyhotwords;
	}
	
	public String getName(){
		return  name;
	}
	public void setName(String name ){
		this.name = name;
	}
	
	public String getSubname(){
		return  subname;
	}
	public void setSubname(String subname ){
		this.subname = subname;
	}
	
	public Date getOktime(){
		return  oktime;
	}
	public void setOktime(Date oktime ){
		this.oktime = oktime;
	}
}