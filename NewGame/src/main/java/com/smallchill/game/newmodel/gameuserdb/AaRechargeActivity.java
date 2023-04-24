package com.smallchill.game.newmodel.gameuserdb;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameuserdb")
@Table(name = "AA_Recharge_Activity")
@BindID(name = "index")
@SuppressWarnings("serial")
public class AaRechargeActivity extends BaseModel {
	private Integer index ;
	private Integer buytimes ;
	private Integer clienttype ;
	private Integer extragold ;
	private Integer extraticket ;
	private Integer productid ;
	private String pid ;
	private Date begintime ;
	private Date endtime ;
	
	public AaRechargeActivity() {
	}
	@AssignID
	public Integer getIndex(){
		return  index;
	}
	public void setIndex(Integer index ){
		this.index = index;
	}
	
	public Integer getBuytimes(){
		return  buytimes;
	}
	public void setBuytimes(Integer buytimes ){
		this.buytimes = buytimes;
	}
	
	public Integer getExtragold(){
		return  extragold;
	}
	public void setExtragold(Integer extragold ){
		this.extragold = extragold;
	}
	
	public Integer getExtraticket(){
		return  extraticket;
	}
	public void setExtraticket(Integer extraticket ){
		this.extraticket = extraticket;
	}
	
	public Integer getProductid(){
		return  productid;
	}
	public void setProductid(Integer productid ){
		this.productid = productid;
	}
	
	public String getPid(){
		return  pid;
	}
	public void setPid(String pid ){
		this.pid = pid;
	}
	
	public Date getBegintime(){
		return  begintime;
	}
	public void setBegintime(Date begintime ){
		this.begintime = begintime;
	}
	
	public Date getEndtime(){
		return  endtime;
	}
	public void setEndtime(Date endtime ){
		this.endtime = endtime;
	}
	public Integer getClienttype() {
		return clienttype;
	}
	public void setClienttype(Integer clienttype) {
		this.clienttype = clienttype;
	}
}