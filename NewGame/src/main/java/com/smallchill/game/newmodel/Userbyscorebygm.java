package com.smallchill.game.newmodel;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameuserdb")
@Table(name = "UserByScoreByGM")
@BindID(name = "rid")
@SuppressWarnings("serial")
public class Userbyscorebygm extends BaseModel {
	private Integer rid ;
	private Integer buymoney ;
	private Integer gid ;
	private Integer score ;
	private Integer sendscore ;
	private Integer userid ;
	private String czip ;
	private Date writedate ;
	
	public Userbyscorebygm() {
	}
	@AutoID
	@SeqID(name = "SEQ_UserByScoreByGM")
	public Integer getRid(){
		return  rid;
	}
	public void setRid(Integer rid ){
		this.rid = rid;
	}
	
	public Integer getBuymoney(){
		return  buymoney;
	}
	public void setBuymoney(Integer buymoney ){
		this.buymoney = buymoney;
	}
	
	public Integer getGid(){
		return  gid;
	}
	public void setGid(Integer gid ){
		this.gid = gid;
	}
	
	public Integer getScore(){
		return  score;
	}
	public void setScore(Integer score ){
		this.score = score;
	}
	
	public Integer getSendscore(){
		return  sendscore;
	}
	public void setSendscore(Integer sendscore ){
		this.sendscore = sendscore;
	}
	
	public Integer getUserid(){
		return  userid;
	}
	public void setUserid(Integer userid ){
		this.userid = userid;
	}
	
	public String getCzip(){
		return  czip;
	}
	public void setCzip(String czip ){
		this.czip = czip;
	}
	
	public Date getWritedate(){
		return  writedate;
	}
	public void setWritedate(Date writedate ){
		this.writedate = writedate;
	}
}
