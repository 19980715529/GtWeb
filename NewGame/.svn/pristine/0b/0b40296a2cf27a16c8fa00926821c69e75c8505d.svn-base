package com.smallchill.game.model;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "master")
@Table(name = "player_operation_log")
@BindID(name = "id")
@SuppressWarnings("serial")
public class PlayerOperationLog extends BaseModel {
	private Integer id ;
	private Integer operatereasonid ;
	private Integer operatorid ;
	private Integer type ;
	private Integer userid ;
	private Integer usertypeid ;
	private Long aftergold ;
	private Long beforegold ;
	private String description ;
	private Long gold ;
	private String nickname ;
	private String operatereasontype ;
	private String operatorname ;
	private String usertypename ;
	private Integer status ;
	private String operateip ;
	private String remark;
	private Integer targetuserid ;
	private String targetusername ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date inserttime ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date sendtime ;
	
	public PlayerOperationLog() {
	}
	@AutoID
	@SeqID(name = "SEQ_PlayerOperationLog")
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public Integer getOperatereasonid(){
		return  operatereasonid;
	}
	public void setOperatereasonid(Integer operatereasonid ){
		this.operatereasonid = operatereasonid;
	}
	
	public Integer getOperatorid(){
		return  operatorid;
	}
	public void setOperatorid(Integer operatorid ){
		this.operatorid = operatorid;
	}
	
	public Integer getType(){
		return  type;
	}
	public void setType(Integer type ){
		this.type = type;
	}
	
	public Integer getUserid(){
		return  userid;
	}
	public void setUserid(Integer userid ){
		this.userid = userid;
	}
	
	public Long getAftergold(){
		return  aftergold;
	}
	public void setAftergold(Long aftergold ){
		this.aftergold = aftergold;
	}
	
	public Long getBeforegold(){
		return  beforegold;
	}
	public void setBeforegold(Long beforegold ){
		this.beforegold = beforegold;
	}
	
	public String getDescription(){
		return  description;
	}
	public void setDescription(String description ){
		this.description = description;
	}
	
	public Long getGold(){
		return  gold;
	}
	public void setGold(Long gold ){
		this.gold = gold;
	}
	
	public String getNickname(){
		return  nickname;
	}
	public void setNickname(String nickname ){
		this.nickname = nickname;
	}
	
	public String getOperatereasontype(){
		return  operatereasontype;
	}
	public void setOperatereasontype(String operatereasontype ){
		this.operatereasontype = operatereasontype;
	}
	
	public String getOperatorname(){
		return  operatorname;
	}
	public void setOperatorname(String operatorname ){
		this.operatorname = operatorname;
	}
	
	public Date getInserttime(){
		return  inserttime;
	}
	public void setInserttime(Date inserttime ){
		this.inserttime = inserttime;
	}
	public String getUsertypename() {
		return usertypename;
	}
	public void setUsertypename(String usertypename) {
		this.usertypename = usertypename;
	}
	public Integer getUsertypeid() {
		return usertypeid;
	}
	public void setUsertypeid(Integer usertypeid) {
		this.usertypeid = usertypeid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getOperateip() {
		return operateip;
	}
	public void setOperateip(String operateip) {
		this.operateip = operateip;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getTargetuserid() {
		return targetuserid;
	}
	public void setTargetuserid(Integer targetuserid) {
		this.targetuserid = targetuserid;
	}
	public String getTargetusername() {
		return targetusername;
	}
	public void setTargetusername(String targetusername) {
		this.targetusername = targetusername;
	}
	public Date getSendtime() {
		return sendtime;
	}
	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

	@Override
	public String toString() {
		return "PlayerOperationLog{" +
				"id=" + id +
				", operatereasonid=" + operatereasonid +
				", operatorid=" + operatorid +
				", type=" + type +
				", userid=" + userid +
				", usertypeid=" + usertypeid +
				", aftergold=" + aftergold +
				", beforegold=" + beforegold +
				", description='" + description + '\'' +
				", gold=" + gold +
				", nickname='" + nickname + '\'' +
				", operatereasontype='" + operatereasontype + '\'' +
				", operatorname='" + operatorname + '\'' +
				", usertypename='" + usertypename + '\'' +
				", status=" + status +
				", operateip='" + operateip + '\'' +
				", remark='" + remark + '\'' +
				", targetuserid=" + targetuserid +
				", targetusername='" + targetusername + '\'' +
				", inserttime=" + inserttime +
				", sendtime=" + sendtime +
				'}';
	}
}
