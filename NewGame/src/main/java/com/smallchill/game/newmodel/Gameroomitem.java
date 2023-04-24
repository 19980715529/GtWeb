package com.smallchill.game.newmodel;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameroomitemdb")
@Table(name = "GameRoomItem")
@BindID(name = "serverid")
@SuppressWarnings("serial")
public class Gameroomitem extends BaseModel {
	private Integer kindid ;
	private Integer serverid ;
	private Integer bloodscorerate ;
	private Integer cheatrate ;
	private Integer currencygold ;
	private Integer eatcheatrate ;
	private Integer isused ;
	private Integer outcheatrate ;
	private Integer revenue ;
	private Integer roomtype ;
	private Integer autoaddblooddailylast ;
	private Integer autoaddblooddailymax ;
	private Integer autoaddblooddailytimecdinsec ;
	private Integer autoaddblooddailytimesvalue ;
	private Integer bused ;
	private Integer cheatlowline ;
	private Integer lessbloodvalue ;
	private Integer maxbloodvalue ;
	private Integer nottaxscore ;
	private Integer present_20 ;
	private Integer present_40 ;
	private Integer present_80 ;
	private Integer taxlimitvalue ;
	private Integer taxrate ;
	private Long bloodscore ;
	private Long limitscore ;
	private Long maxeatscore ;
	private Long outscore ;
	private Long realscore ;
	private String roomname ;
	private Long todayscore ;
	private Long ltotalscore ;
	private Long todaycheatblood ;
	private Long todaytaxscore ;
	private Long totalcheatblood ;
	private Long totaltaxscore ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date lastupdatetime ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date autoaddblooddailylasttime ;
	
	public Gameroomitem() {
	}
	@AssignID
	public Integer getKindid(){
		return  kindid;
	}
	public void setKindid(Integer kindid ){
		this.kindid = kindid;
	}
	@AssignID
	public Integer getServerid(){
		return  serverid;
	}
	public void setServerid(Integer serverid ){
		this.serverid = serverid;
	}
	
	public Integer getBloodscorerate(){
		return  bloodscorerate;
	}
	public void setBloodscorerate(Integer bloodscorerate ){
		this.bloodscorerate = bloodscorerate;
	}
	
	public Integer getCheatrate(){
		return  cheatrate;
	}
	public void setCheatrate(Integer cheatrate ){
		this.cheatrate = cheatrate;
	}
	
	public Integer getCurrencygold(){
		return  currencygold;
	}
	public void setCurrencygold(Integer currencygold ){
		this.currencygold = currencygold;
	}
	
	public Integer getEatcheatrate(){
		return  eatcheatrate;
	}
	public void setEatcheatrate(Integer eatcheatrate ){
		this.eatcheatrate = eatcheatrate;
	}
	
	public Integer getIsused(){
		return  isused;
	}
	public void setIsused(Integer isused ){
		this.isused = isused;
	}
	
	public Integer getOutcheatrate(){
		return  outcheatrate;
	}
	public void setOutcheatrate(Integer outcheatrate ){
		this.outcheatrate = outcheatrate;
	}
	
	public Integer getRevenue(){
		return  revenue;
	}
	public void setRevenue(Integer revenue ){
		this.revenue = revenue;
	}
	
	public Integer getRoomtype(){
		return  roomtype;
	}
	public void setRoomtype(Integer roomtype ){
		this.roomtype = roomtype;
	}
	
	public Integer getAutoaddblooddailylast(){
		return  autoaddblooddailylast;
	}
	public void setAutoaddblooddailylast(Integer autoaddblooddailylast ){
		this.autoaddblooddailylast = autoaddblooddailylast;
	}
	
	public Integer getAutoaddblooddailymax(){
		return  autoaddblooddailymax;
	}
	public void setAutoaddblooddailymax(Integer autoaddblooddailymax ){
		this.autoaddblooddailymax = autoaddblooddailymax;
	}
	
	public Integer getAutoaddblooddailytimecdinsec(){
		return  autoaddblooddailytimecdinsec;
	}
	public void setAutoaddblooddailytimecdinsec(Integer autoaddblooddailytimecdinsec ){
		this.autoaddblooddailytimecdinsec = autoaddblooddailytimecdinsec;
	}
	
	public Integer getAutoaddblooddailytimesvalue(){
		return  autoaddblooddailytimesvalue;
	}
	public void setAutoaddblooddailytimesvalue(Integer autoaddblooddailytimesvalue ){
		this.autoaddblooddailytimesvalue = autoaddblooddailytimesvalue;
	}
	
	public Integer getBused(){
		return  bused;
	}
	public void setBused(Integer bused ){
		this.bused = bused;
	}
	
	public Integer getCheatlowline(){
		return  cheatlowline;
	}
	public void setCheatlowline(Integer cheatlowline ){
		this.cheatlowline = cheatlowline;
	}
	
	public Integer getLessbloodvalue(){
		return  lessbloodvalue;
	}
	public void setLessbloodvalue(Integer lessbloodvalue ){
		this.lessbloodvalue = lessbloodvalue;
	}
	
	public Integer getMaxbloodvalue(){
		return  maxbloodvalue;
	}
	public void setMaxbloodvalue(Integer maxbloodvalue ){
		this.maxbloodvalue = maxbloodvalue;
	}
	
	public Integer getNottaxscore(){
		return  nottaxscore;
	}
	public void setNottaxscore(Integer nottaxscore ){
		this.nottaxscore = nottaxscore;
	}
	
	public Integer getTaxlimitvalue(){
		return  taxlimitvalue;
	}
	public void setTaxlimitvalue(Integer taxlimitvalue ){
		this.taxlimitvalue = taxlimitvalue;
	}
	
	public Integer getTaxrate(){
		return  taxrate;
	}
	public void setTaxrate(Integer taxrate ){
		this.taxrate = taxrate;
	}
	
	public Long getBloodscore(){
		return  bloodscore;
	}
	public void setBloodscore(Long bloodscore ){
		this.bloodscore = bloodscore;
	}
	
	public Long getLimitscore(){
		return  limitscore;
	}
	public void setLimitscore(Long limitscore ){
		this.limitscore = limitscore;
	}
	
	public Long getMaxeatscore(){
		return  maxeatscore;
	}
	public void setMaxeatscore(Long maxeatscore ){
		this.maxeatscore = maxeatscore;
	}
	
	public Long getOutscore(){
		return  outscore;
	}
	public void setOutscore(Long outscore ){
		this.outscore = outscore;
	}
	
	public Long getRealscore(){
		return  realscore;
	}
	public void setRealscore(Long realscore ){
		this.realscore = realscore;
	}
	
	public String getRoomname(){
		return  roomname;
	}
	public void setRoomname(String roomname ){
		this.roomname = roomname;
	}
	
	public Long getTodayscore(){
		return  todayscore;
	}
	public void setTodayscore(Long todayscore ){
		this.todayscore = todayscore;
	}
	
	public Long getLtotalscore(){
		return  ltotalscore;
	}
	public void setLtotalscore(Long ltotalscore ){
		this.ltotalscore = ltotalscore;
	}
	
	public Long getTodaycheatblood(){
		return  todaycheatblood;
	}
	public void setTodaycheatblood(Long todaycheatblood ){
		this.todaycheatblood = todaycheatblood;
	}
	
	public Long getTodaytaxscore(){
		return  todaytaxscore;
	}
	public void setTodaytaxscore(Long todaytaxscore ){
		this.todaytaxscore = todaytaxscore;
	}
	
	public Long getTotalcheatblood(){
		return  totalcheatblood;
	}
	public void setTotalcheatblood(Long totalcheatblood ){
		this.totalcheatblood = totalcheatblood;
	}
	
	public Long getTotaltaxscore(){
		return  totaltaxscore;
	}
	public void setTotaltaxscore(Long totaltaxscore ){
		this.totaltaxscore = totaltaxscore;
	}
	
	public Date getLastupdatetime(){
		return  lastupdatetime;
	}
	public void setLastupdatetime(Date lastupdatetime ){
		this.lastupdatetime = lastupdatetime;
	}
	
	public Date getAutoaddblooddailylasttime(){
		return  autoaddblooddailylasttime;
	}
	public void setAutoaddblooddailylasttime(Date autoaddblooddailylasttime ){
		this.autoaddblooddailylasttime = autoaddblooddailylasttime;
	}
	public Integer getPresent_20() {
		return present_20;
	}
	public void setPresent_20(Integer present_20) {
		this.present_20 = present_20;
	}
	public Integer getPresent_40() {
		return present_40;
	}
	public void setPresent_40(Integer present_40) {
		this.present_40 = present_40;
	}
	public Integer getPresent_80() {
		return present_80;
	}
	public void setPresent_80(Integer present_80) {
		this.present_80 = present_80;
	}
}
