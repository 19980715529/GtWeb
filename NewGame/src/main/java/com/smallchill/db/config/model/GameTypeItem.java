package com.smallchill.db.config.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.*;

@DbName(name = "platform")
@Table(name = "GameTypeItem")
@BindID(name = "typeid")
@SuppressWarnings("serial")
public class GameTypeItem extends BaseModel {
	private Integer typeid ;
	private Integer joinid ;
	private Integer nullity ;
	private Integer sortid ;
	private String typename ;
	
	public GameTypeItem() {
	}
	
	@AssignID
	public Integer getTypeid(){
		return  typeid;
	}
	public void setTypeid(Integer typeid ){
		this.typeid = typeid;
	}
	
	public Integer getJoinid(){
		return  joinid;
	}
	public void setJoinid(Integer joinid ){
		this.joinid = joinid;
	}
	
	public Integer getNullity(){
		return  nullity;
	}
	public void setNullity(Integer nullity ){
		this.nullity = nullity;
	}
	
	public Integer getSortid(){
		return  sortid;
	}
	public void setSortid(Integer sortid ){
		this.sortid = sortid;
	}
	
	public String getTypename(){
		return  typename;
	}
	public void setTypename(String typename ){
		this.typename = typename;
	}
}
