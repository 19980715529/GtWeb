package com.smallchill.game.model;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "nativeWeb")
@Table(name = "ConfigInfo")
@BindID(name = "configid")
@SuppressWarnings("serial")
public class ConfigInfo extends BaseModel {
	private Integer configid ;
	private Integer sortid ;
	private String configkey ;
	private String configname ;
	private String configstring ;
	private String field1 ;
	private String field2 ;
	private String field3 ;
	private String field4 ;
	private String field5 ;
	private String field6 ;
	private String field7 ;
	private String field8 ;
	private Integer platformid ;
	
	public ConfigInfo() {
	}
	@AutoID
	@SeqID(name = "SEQ_ConfigInfo")
	public Integer getConfigid(){
		return  configid;
	}
	public void setConfigid(Integer configid ){
		this.configid = configid;
	}
	
	public Integer getSortid(){
		return  sortid;
	}
	public void setSortid(Integer sortid ){
		this.sortid = sortid;
	}
	
	public String getConfigkey(){
		return  configkey;
	}
	public void setConfigkey(String configkey ){
		this.configkey = configkey;
	}
	
	public String getConfigname(){
		return  configname;
	}
	public void setConfigname(String configname ){
		this.configname = configname;
	}
	
	public String getConfigstring(){
		return  configstring;
	}
	public void setConfigstring(String configstring ){
		this.configstring = configstring;
	}
	
	public String getField1(){
		return  field1;
	}
	public void setField1(String field1 ){
		this.field1 = field1;
	}
	
	public String getField2(){
		return  field2;
	}
	public void setField2(String field2 ){
		this.field2 = field2;
	}
	
	public String getField3(){
		return  field3;
	}
	public void setField3(String field3 ){
		this.field3 = field3;
	}
	
	public String getField4(){
		return  field4;
	}
	public void setField4(String field4 ){
		this.field4 = field4;
	}
	
	public String getField5(){
		return  field5;
	}
	public void setField5(String field5 ){
		this.field5 = field5;
	}
	
	public String getField6(){
		return  field6;
	}
	public void setField6(String field6 ){
		this.field6 = field6;
	}
	
	public String getField7(){
		return  field7;
	}
	public void setField7(String field7 ){
		this.field7 = field7;
	}
	
	public String getField8(){
		return  field8;
	}
	public void setField8(String field8 ){
		this.field8 = field8;
	}
	public Integer getPlatformid() {
		return platformid;
	}
	public void setPlatformid(Integer platformid) {
		this.platformid = platformid;
	}
}
