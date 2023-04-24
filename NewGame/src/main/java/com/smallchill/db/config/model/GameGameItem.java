package com.smallchill.db.config.model;

import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "platform")
@Table(name = "GameGameItem")
@BindID(name = "gameid")
@SuppressWarnings("serial")
public class GameGameItem extends BaseModel {
	private Integer gameid ;
	private Integer clientversion ;
	private Integer serverversion ;
	private Integer suporttype ;
	private String clientexename ;
	private String databaseaddr ;
	private String databasename ;
	private String gamename ;
	private String serverdllname ;
	
	public GameGameItem() {
	}
	
	public Integer getGameid(){
		return  gameid;
	}
	public void setGameid(Integer gameid ){
		this.gameid = gameid;
	}
	
	public Integer getClientversion(){
		return  clientversion;
	}
	public void setClientversion(Integer clientversion ){
		this.clientversion = clientversion;
	}
	
	public Integer getServerversion(){
		return  serverversion;
	}
	public void setServerversion(Integer serverversion ){
		this.serverversion = serverversion;
	}
	
	public Integer getSuporttype(){
		return  suporttype;
	}
	public void setSuporttype(Integer suporttype ){
		this.suporttype = suporttype;
	}
	
	public String getClientexename(){
		return  clientexename;
	}
	public void setClientexename(String clientexename ){
		this.clientexename = clientexename;
	}
	
	public String getDatabaseaddr(){
		return  databaseaddr;
	}
	public void setDatabaseaddr(String databaseaddr ){
		this.databaseaddr = databaseaddr;
	}
	
	public String getDatabasename(){
		return  databasename;
	}
	public void setDatabasename(String databasename ){
		this.databasename = databasename;
	}
	
	public String getGamename(){
		return  gamename;
	}
	public void setGamename(String gamename ){
		this.gamename = gamename;
	}
	
	public String getServerdllname(){
		return  serverdllname;
	}
	public void setServerdllname(String serverdllname ){
		this.serverdllname = serverdllname;
	}
}
