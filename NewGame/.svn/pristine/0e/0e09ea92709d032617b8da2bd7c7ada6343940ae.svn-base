package com.smallchill.game.model;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "nativeWeb")
@Table(name = "AwardType")
@BindID(name = "typeid")
@SuppressWarnings("serial")
public class AwardType extends BaseModel {
	private Integer typeid ;
	private Integer nullity ;
	private Integer parentid ;
	private Integer sortid ;
	private String typename ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date collectdate ;
	
	public AwardType() {
	}
	@AutoID
	@SeqID(name = "SEQ_AwardType")
	public Integer getTypeid(){
		return  typeid;
	}
	public void setTypeid(Integer typeid ){
		this.typeid = typeid;
	}
	
	public Integer getNullity(){
		return  nullity;
	}
	public void setNullity(Integer nullity ){
		this.nullity = nullity;
	}
	
	public Integer getParentid(){
		return  parentid;
	}
	public void setParentid(Integer parentid ){
		this.parentid = parentid;
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
	
	public Date getCollectdate(){
		return  collectdate;
	}
	public void setCollectdate(Date collectdate ){
		this.collectdate = collectdate;
	}
}
