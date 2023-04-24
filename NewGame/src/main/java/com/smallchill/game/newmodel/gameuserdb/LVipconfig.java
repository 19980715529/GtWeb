package com.smallchill.game.newmodel.gameuserdb;

import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameuserdb")
@Table(name = "L_VipConfig")
@SuppressWarnings("serial")
public class LVipconfig extends BaseModel {
	private Integer buyoncevalue ;
	private Integer buytimes ;
	private Integer viplevel ;
	
	public LVipconfig() {
	}
	
	public Integer getBuyoncevalue(){
		return  buyoncevalue;
	}
	public void setBuyoncevalue(Integer buyoncevalue ){
		this.buyoncevalue = buyoncevalue;
	}
	
	public Integer getBuytimes(){
		return  buytimes;
	}
	public void setBuytimes(Integer buytimes ){
		this.buytimes = buytimes;
	}
	
	public Integer getViplevel(){
		return  viplevel;
	}
	public void setViplevel(Integer viplevel ){
		this.viplevel = viplevel;
	}
}