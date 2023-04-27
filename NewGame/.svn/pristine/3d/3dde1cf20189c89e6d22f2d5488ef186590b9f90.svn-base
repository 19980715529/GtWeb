package com.smallchill.system.model;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;

/**
 * 菜单关系表
 */
@Table(name = "blade_relation")
@BindID(name = "id")
@SuppressWarnings("serial")
public class Relation extends BaseModel {
	private Integer id; // 主键
	private Integer menuid; // 菜单id
	private Integer roleid; // 角色id

	@AutoID
	@SeqID(name = "SEQ_RELATION")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMenuid() {
		return menuid;
	}

	public void setMenuid(Integer menuid) {
		this.menuid = menuid;
	}

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}
}
