package com.smallchill.system.game.model;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;

@Table(name = "gameweb_coupon_log")
@BindID(name = "id")
@SuppressWarnings("serial")
//用户表
public class Coupon extends BaseModel {
	private Integer id; //主键
	private Integer userid; //玩家id
	private String nickname; //昵称
	private Integer adminid; //管理员id
	private String adminname; //管理员姓名
	private String operatetype; //操作类型
	private Integer operatetypeid; //操作类型id
	private String description; //说明
	private Long couponnum; //奖券数
	private Byte numtype; //类型
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date createtime; //创建时间

	@AutoID //mysql自增
	@SeqID(name = "SEQ_COIN") //oracle sequence自增
	//两者只需要写一个,根据数据库不同来选择
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getAdminid() {
		return adminid;
	}

	public void setAdminid(Integer adminid) {
		this.adminid = adminid;
	}

	public String getAdminname() {
		return adminname;
	}

	public void setAdminname(String adminname) {
		this.adminname = adminname;
	}

	public Integer getOperatetypeid() {
		return operatetypeid;
	}

	public void setOperatetypeid(Integer operatetypeid) {
		this.operatetypeid = operatetypeid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Byte getNumtype() {
		return numtype;
	}

	public void setNumtype(Byte numtype) {
		this.numtype = numtype;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getOperatetype() {
		return operatetype;
	}

	public void setOperatetype(String operatetype) {
		this.operatetype = operatetype;
	}

	public Long getCouponnum() {
		return couponnum;
	}

	public void setCouponnum(Long couponnum) {
		this.couponnum = couponnum;
	}
}
