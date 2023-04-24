package com.smallchill.game.model;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "treasure")
@Table(name = "RecordInsure")
@BindID(name = "recordid")
@SuppressWarnings("serial")
public class RecordInsure extends BaseModel {
	private Integer recordid ;
	private Integer giftinfoid ;
	private Integer isgameplaza ;
	private Integer isreceive ;
	private Integer kindid ;
	private Integer serverid ;
	private Integer sourceuserid ;
	private Integer targetuserid ;
	private Integer tradetype ;
	private String clientip ;
	private String collectnote ;
	private Long revenue ;
	private Long sourcebank ;
	private Long sourcegold ;
	private String sourceusernicname ;
	private Long swapscore ;
	private Long targetbank ;
	private Long targetgold ;
	private String targetusername ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date collectdate ;
	
	public RecordInsure() {
	}
	@AutoID
	@SeqID(name = "SEQ_RecordInsure")
	public Integer getRecordid(){
		return  recordid;
	}
	public void setRecordid(Integer recordid ){
		this.recordid = recordid;
	}
	
	public Integer getGiftinfoid(){
		return  giftinfoid;
	}
	public void setGiftinfoid(Integer giftinfoid ){
		this.giftinfoid = giftinfoid;
	}
	
	public Integer getIsgameplaza(){
		return  isgameplaza;
	}
	public void setIsgameplaza(Integer isgameplaza ){
		this.isgameplaza = isgameplaza;
	}
	
	public Integer getIsreceive(){
		return  isreceive;
	}
	public void setIsreceive(Integer isreceive ){
		this.isreceive = isreceive;
	}
	
	public Integer getKindid(){
		return  kindid;
	}
	public void setKindid(Integer kindid ){
		this.kindid = kindid;
	}
	
	public Integer getServerid(){
		return  serverid;
	}
	public void setServerid(Integer serverid ){
		this.serverid = serverid;
	}
	
	public Integer getSourceuserid(){
		return  sourceuserid;
	}
	public void setSourceuserid(Integer sourceuserid ){
		this.sourceuserid = sourceuserid;
	}
	
	public Integer getTargetuserid(){
		return  targetuserid;
	}
	public void setTargetuserid(Integer targetuserid ){
		this.targetuserid = targetuserid;
	}
	
	public Integer getTradetype(){
		return  tradetype;
	}
	public void setTradetype(Integer tradetype ){
		this.tradetype = tradetype;
	}
	
	public String getClientip(){
		return  clientip;
	}
	public void setClientip(String clientip ){
		this.clientip = clientip;
	}
	
	public String getCollectnote(){
		return  collectnote;
	}
	public void setCollectnote(String collectnote ){
		this.collectnote = collectnote;
	}
	
	public Long getRevenue(){
		return  revenue;
	}
	public void setRevenue(Long revenue ){
		this.revenue = revenue;
	}
	
	public Long getSourcebank(){
		return  sourcebank;
	}
	public void setSourcebank(Long sourcebank ){
		this.sourcebank = sourcebank;
	}
	
	public Long getSourcegold(){
		return  sourcegold;
	}
	public void setSourcegold(Long sourcegold ){
		this.sourcegold = sourcegold;
	}
	
	public String getSourceusernicname(){
		return  sourceusernicname;
	}
	public void setSourceusernicname(String sourceusernicname ){
		this.sourceusernicname = sourceusernicname;
	}
	
	public Long getSwapscore(){
		return  swapscore;
	}
	public void setSwapscore(Long swapscore ){
		this.swapscore = swapscore;
	}
	
	public Long getTargetbank(){
		return  targetbank;
	}
	public void setTargetbank(Long targetbank ){
		this.targetbank = targetbank;
	}
	
	public Long getTargetgold(){
		return  targetgold;
	}
	public void setTargetgold(Long targetgold ){
		this.targetgold = targetgold;
	}
	
	public String getTargetusername(){
		return  targetusername;
	}
	public void setTargetusername(String targetusername ){
		this.targetusername = targetusername;
	}
	
	public Date getCollectdate(){
		return  collectdate;
	}
	public void setCollectdate(Date collectdate ){
		this.collectdate = collectdate;
	}
}
