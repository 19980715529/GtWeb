package com.smallchill.db.config.model;

import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameroomitemdb")
@Table(name = "GameKindItem")
@BindID(name = "KindID")
@SuppressWarnings("serial")
public class GameKindItem extends BaseModel {
	private Integer kindid ;
	private Integer androidversion ;
	private Integer areaindex ;
	private Integer cheatepercent ;
	private Integer iosversion ;
	private Integer isused ;
	private Integer joinid ;
	private Integer maxversion ;
	private Integer nullity ;
	private Integer partfromid ;
	private Integer recommend ;
	private Integer sortid ;
	private Integer typeid ;
	private String databasename ;
	private String gzurl ;
	private String kindname ;
	private String processname ;
	
	public GameKindItem() {
	}
	
	@AssignID
	public Integer getKindid(){
		return  kindid;
	}
	public void setKindid(Integer kindid ){
		this.kindid = kindid;
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
	
	public Integer getRecommend(){
		return  recommend;
	}
	public void setRecommend(Integer recommend ){
		this.recommend = recommend;
	}
	
	public Integer getSortid(){
		return  sortid;
	}
	public void setSortid(Integer sortid ){
		this.sortid = sortid;
	}
	
	public Integer getTypeid(){
		return  typeid;
	}
	public void setTypeid(Integer typeid ){
		this.typeid = typeid;
	}
	
	public String getKindname(){
		return  kindname;
	}
	public void setKindname(String kindname ){
		this.kindname = kindname;
	}
	
	public String getProcessname(){
		return  processname;
	}
	public void setProcessname(String processname ){
		this.processname = processname;
	}

	public Integer getAndroidversion() {
		return androidversion;
	}

	public void setAndroidversion(Integer androidversion) {
		this.androidversion = androidversion;
	}

	public Integer getAreaindex() {
		return areaindex;
	}

	public void setAreaindex(Integer areaindex) {
		this.areaindex = areaindex;
	}

	public Integer getCheatepercent() {
		return cheatepercent;
	}

	public void setCheatepercent(Integer cheatepercent) {
		this.cheatepercent = cheatepercent;
	}

	public Integer getIosversion() {
		return iosversion;
	}

	public void setIosversion(Integer iosversion) {
		this.iosversion = iosversion;
	}

	public Integer getIsused() {
		return isused;
	}

	public void setIsused(Integer isused) {
		this.isused = isused;
	}

	public Integer getMaxversion() {
		return maxversion;
	}

	public void setMaxversion(Integer maxversion) {
		this.maxversion = maxversion;
	}

	public Integer getPartfromid() {
		return partfromid;
	}

	public void setPartfromid(Integer partfromid) {
		this.partfromid = partfromid;
	}

	public String getDatabasename() {
		return databasename;
	}

	public void setDatabasename(String databasename) {
		this.databasename = databasename;
	}

	public String getGzurl() {
		return gzurl;
	}

	public void setGzurl(String gzurl) {
		this.gzurl = gzurl;
	}
}