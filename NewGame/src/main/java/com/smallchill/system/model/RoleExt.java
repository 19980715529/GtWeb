package com.smallchill.system.model;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;

@Table(name = "blade_role_ext")
@BindID(name = "id")
@SuppressWarnings("serial")
//角色代理表
public class RoleExt extends BaseModel {
	private Integer id; //主键
	private String rolein; //额外附加的权限
	private String roleout; //额外剔除的权限
	private Integer userid; //用户id

	@AutoID
	@SeqID(name = "SEQ_ROLE_EXT")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRolein() {
		return rolein;
	}

	public void setRolein(String rolein) {
		this.rolein = rolein;
	}

	public String getRoleout() {
		return roleout;
	}

	public void setRoleout(String roleout) {
		this.roleout = roleout;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

}
