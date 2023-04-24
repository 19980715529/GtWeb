package com.smallchill.game.newmodel;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameuserdb")
@Table(name = "AA_GiveRecord")
@BindID(name = "id")
@SuppressWarnings("serial")
public class AaGiverecord extends BaseModel {
	private Integer id ;
	private Integer from_user ;
	private Integer open ;
	private Integer prop_id ;
	private Integer time ;
	private Integer to_user ;
	private Long prop_count ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date givetime ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date receivetime ;
	
	public AaGiverecord() {
	}
	@AutoID
	@SeqID(name = "SEQ_AA_GiveRecord")
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	public Integer getOpen(){
		return  open;
	}
	public void setOpen(Integer open ){
		this.open = open;
	}
	public Integer getTime(){
		return  time;
	}
	public void setTime(Integer time ){
		this.time = time;
	}
	
	public Date getGivetime(){
		return  givetime;
	}
	public void setGivetime(Date givetime ){
		this.givetime = givetime;
	}
	
	public Date getReceivetime(){
		return  receivetime;
	}
	public void setReceivetime(Date receivetime ){
		this.receivetime = receivetime;
	}
	public Integer getFrom_user() {
		return from_user;
	}
	public void setFrom_user(Integer from_user) {
		this.from_user = from_user;
	}
	public Integer getProp_id() {
		return prop_id;
	}
	public void setProp_id(Integer prop_id) {
		this.prop_id = prop_id;
	}
	public Integer getTo_user() {
		return to_user;
	}
	public void setTo_user(Integer to_user) {
		this.to_user = to_user;
	}
	public Long getProp_count() {
		return prop_count;
	}
	public void setProp_count(Long prop_count) {
		this.prop_count = prop_count;
	}
}
