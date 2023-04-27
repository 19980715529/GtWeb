package com.smallchill.game.newmodel.gameuserdb;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameuserdb")
@Table(name = "User_SendGoosRecord")
@BindID(name = "nIndex")
@SuppressWarnings("serial")
public class UserSendgoosrecord extends BaseModel {
	private Integer nindex ;
	private Integer buycount ;
	private Integer sellcount ;
	private Integer userid ;
	private Integer vipUserid ;
	private Long buyscore ;
	private Long sellscore ;
	private Date recorddate ;
	
	public UserSendgoosrecord() {
	}
	@AutoID
	@SeqID(name = "SEQ_User_SendGoosRecord")
	public Integer getNindex(){
		return  nindex;
	}
	public void setNindex(Integer nindex ){
		this.nindex = nindex;
	}
	
	public Integer getBuycount(){
		return  buycount;
	}
	public void setBuycount(Integer buycount ){
		this.buycount = buycount;
	}
	
	public Integer getSellcount(){
		return  sellcount;
	}
	public void setSellcount(Integer sellcount ){
		this.sellcount = sellcount;
	}
	
	public Integer getUserid(){
		return  userid;
	}
	public void setUserid(Integer userid ){
		this.userid = userid;
	}
	
	public Integer getVipUserid(){
		return  vipUserid;
	}
	public void setVipUserid(Integer vipUserid ){
		this.vipUserid = vipUserid;
	}
	
	public Long getBuyscore(){
		return  buyscore;
	}
	public void setBuyscore(Long buyscore ){
		this.buyscore = buyscore;
	}
	
	public Long getSellscore(){
		return  sellscore;
	}
	public void setSellscore(Long sellscore ){
		this.sellscore = sellscore;
	}
	
	public Date getRecorddate(){
		return  recorddate;
	}
	public void setRecorddate(Date recorddate ){
		this.recorddate = recorddate;
	}
}