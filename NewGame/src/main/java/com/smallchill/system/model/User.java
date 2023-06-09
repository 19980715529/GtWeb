package com.smallchill.system.model;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;

//用户表
@Table(name = "blade_user")
@BindID(name = "id")
@SuppressWarnings("serial")
public class User extends BaseModel {
	private Integer id; //主键
	private String account; //账号
	private Integer deptid; //部门id
	private String email; //邮箱
	private String name; //姓名
	private String password; //密码
	private String salt; //密码盐
	private String phone; //手机号
	private String roleid; //角色id
	private Integer sex; //性别
	private Integer status; //状态
	private Integer version; //版本号
	private Date birthday; //生日
	private Date createtime; //创建时间
	private Date lastlogintime; //最後登錄時間时间
	private String lastloginip; //最後登錄IP
	private String googlesecret; //谷歌秘钥

	@AutoID //mysql自增
	@SeqID(name = "SEQ_USER") //oracle sequence自增
	//两者只需要写一个,根据数据库不同来选择
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getDeptid() {
		return deptid;
	}

	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getLastlogintime() {
		return lastlogintime;
	}

	public void setLastlogintime(Date lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

	public String getLastloginip() {
		return lastloginip;
	}

	public void setLastloginip(String lastloginip) {
		this.lastloginip = lastloginip;
	}

	public String getGooglesecret() {
		return googlesecret;
	}

	public void setGooglesecret(String googlesecret) {
		this.googlesecret = googlesecret;
	}
}
