package com.smallchill.game.model;

import java.math.BigDecimal;
import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "treasure")
@Table(name = "LivcardBuildStream")
@BindID(name = "buildid")
@SuppressWarnings("serial")
public class LivcardBuildStream extends BaseModel {
	private Integer buildid ;
	private Integer buildcount ;
	private Integer cardtypeid ;
	private Integer downloadcount ;
	private Integer gold ;
	private String adminname ;
	private String buildaddr ;
	private byte[] buildcardpacket ;
	private BigDecimal cardprice ;
	private BigDecimal currency ;
	private String noteinfo ;
	private Date builddate ;
	
	public LivcardBuildStream() {
	}
	@AutoID
	@SeqID(name = "SEQ_LivcardBuildStream")
	public Integer getBuildid(){
		return  buildid;
	}
	public void setBuildid(Integer buildid ){
		this.buildid = buildid;
	}
	
	public Integer getBuildcount(){
		return  buildcount;
	}
	public void setBuildcount(Integer buildcount ){
		this.buildcount = buildcount;
	}
	
	public Integer getCardtypeid(){
		return  cardtypeid;
	}
	public void setCardtypeid(Integer cardtypeid ){
		this.cardtypeid = cardtypeid;
	}
	
	public Integer getDownloadcount(){
		return  downloadcount;
	}
	public void setDownloadcount(Integer downloadcount ){
		this.downloadcount = downloadcount;
	}
	
	public Integer getGold(){
		return  gold;
	}
	public void setGold(Integer gold ){
		this.gold = gold;
	}
	
	public String getAdminname(){
		return  adminname;
	}
	public void setAdminname(String adminname ){
		this.adminname = adminname;
	}
	
	public String getBuildaddr(){
		return  buildaddr;
	}
	public void setBuildaddr(String buildaddr ){
		this.buildaddr = buildaddr;
	}
	
	public byte[] getBuildcardpacket(){
		return  buildcardpacket;
	}
	public void setBuildcardpacket(byte[] buildcardpacket ){
		this.buildcardpacket = buildcardpacket;
	}
	
	public BigDecimal getCardprice(){
		return  cardprice;
	}
	public void setCardprice(BigDecimal cardprice ){
		this.cardprice = cardprice;
	}
	
	public BigDecimal getCurrency(){
		return  currency;
	}
	public void setCurrency(BigDecimal currency ){
		this.currency = currency;
	}
	
	public String getNoteinfo(){
		return  noteinfo;
	}
	public void setNoteinfo(String noteinfo ){
		this.noteinfo = noteinfo;
	}
	
	public Date getBuilddate(){
		return  builddate;
	}
	public void setBuilddate(Date builddate ){
		this.builddate = builddate;
	}
}
