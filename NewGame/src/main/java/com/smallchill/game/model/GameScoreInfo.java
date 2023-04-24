package com.smallchill.game.model;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "treasure")
@Table(name = "GameScoreInfo")
@BindID(name = "userid")
@SuppressWarnings("serial")
public class GameScoreInfo extends BaseModel {
	private Integer userid ;
	private Integer alllogontimes ;
	private Integer drawcount ;
	private Integer fleecount ;
	private Integer lostcount ;
	private Integer masterorder ;
	private Integer masterright ;
	private Integer onlinetimecount ;
	private Integer playtimecount ;
	private Integer userright ;
	private Integer wincount ;
	private Long highscore ;
	private Long insurescore ;
	private String lastlogonip ;
	private String lastlogonmachine ;
	private String registerip ;
	private String registermachine ;
	private Long revenue ;
	private Long score ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date lastlogondate ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date registerdate ;
	
	public GameScoreInfo() {
	}
	@AssignID
	public Integer getUserid(){
		return  userid;
	}
	public void setUserid(Integer userid ){
		this.userid = userid;
	}
	
	public Integer getAlllogontimes(){
		return  alllogontimes;
	}
	public void setAlllogontimes(Integer alllogontimes ){
		this.alllogontimes = alllogontimes;
	}
	
	public Integer getDrawcount(){
		return  drawcount;
	}
	public void setDrawcount(Integer drawcount ){
		this.drawcount = drawcount;
	}
	
	public Integer getFleecount(){
		return  fleecount;
	}
	public void setFleecount(Integer fleecount ){
		this.fleecount = fleecount;
	}
	
	public Integer getLostcount(){
		return  lostcount;
	}
	public void setLostcount(Integer lostcount ){
		this.lostcount = lostcount;
	}
	
	public Integer getMasterorder(){
		return  masterorder;
	}
	public void setMasterorder(Integer masterorder ){
		this.masterorder = masterorder;
	}
	
	public Integer getMasterright(){
		return  masterright;
	}
	public void setMasterright(Integer masterright ){
		this.masterright = masterright;
	}
	
	public Integer getOnlinetimecount(){
		return  onlinetimecount;
	}
	public void setOnlinetimecount(Integer onlinetimecount ){
		this.onlinetimecount = onlinetimecount;
	}
	
	public Integer getPlaytimecount(){
		return  playtimecount;
	}
	public void setPlaytimecount(Integer playtimecount ){
		this.playtimecount = playtimecount;
	}
	
	public Integer getUserright(){
		return  userright;
	}
	public void setUserright(Integer userright ){
		this.userright = userright;
	}
	
	public Integer getWincount(){
		return  wincount;
	}
	public void setWincount(Integer wincount ){
		this.wincount = wincount;
	}
	
	public Long getHighscore(){
		return  highscore;
	}
	public void setHighscore(Long highscore ){
		this.highscore = highscore;
	}
	
	public Long getInsurescore(){
		return  insurescore;
	}
	public void setInsurescore(Long insurescore ){
		this.insurescore = insurescore;
	}
	
	public String getLastlogonip(){
		return  lastlogonip;
	}
	public void setLastlogonip(String lastlogonip ){
		this.lastlogonip = lastlogonip;
	}
	
	public String getLastlogonmachine(){
		return  lastlogonmachine;
	}
	public void setLastlogonmachine(String lastlogonmachine ){
		this.lastlogonmachine = lastlogonmachine;
	}
	
	public String getRegisterip(){
		return  registerip;
	}
	public void setRegisterip(String registerip ){
		this.registerip = registerip;
	}
	
	public String getRegistermachine(){
		return  registermachine;
	}
	public void setRegistermachine(String registermachine ){
		this.registermachine = registermachine;
	}
	
	public Long getRevenue(){
		return  revenue;
	}
	public void setRevenue(Long revenue ){
		this.revenue = revenue;
	}
	
	public Long getScore(){
		return  score;
	}
	public void setScore(Long score ){
		this.score = score;
	}
	
	public Date getLastlogondate(){
		return  lastlogondate;
	}
	public void setLastlogondate(Date lastlogondate ){
		this.lastlogondate = lastlogondate;
	}
	
	public Date getRegisterdate(){
		return  registerdate;
	}
	public void setRegisterdate(Date registerdate ){
		this.registerdate = registerdate;
	}
}
