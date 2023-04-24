package com.smallchill.game.newmodel.gameuserdb;

import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameuserdb")
@Table(name = "AA_Shop_Goods")
@BindID(name = "Goods_Id")
@SuppressWarnings("serial")
public class AaShopGoods extends BaseModel {
	private Integer Goods_Id ;
	private Integer goodstype ;
	private Integer isonline ;
	private Integer relationamount ;
	private Integer showsort ;
	private Integer ticket ;
	private String goodsname ;
	private String remark ;
	
	public AaShopGoods() {
	}
	
	public Integer getGoodstype(){
		return  goodstype;
	}
	public void setGoodstype(Integer goodstype ){
		this.goodstype = goodstype;
	}
	
	public Integer getIsonline(){
		return  isonline;
	}
	public void setIsonline(Integer isonline ){
		this.isonline = isonline;
	}
	
	public Integer getRelationamount(){
		return  relationamount;
	}
	public void setRelationamount(Integer relationamount ){
		this.relationamount = relationamount;
	}
	
	public Integer getShowsort(){
		return  showsort;
	}
	public void setShowsort(Integer showsort ){
		this.showsort = showsort;
	}
	
	public Integer getTicket(){
		return  ticket;
	}
	public void setTicket(Integer ticket ){
		this.ticket = ticket;
	}
	
	public String getGoodsname(){
		return  goodsname;
	}
	public void setGoodsname(String goodsname ){
		this.goodsname = goodsname;
	}
	
	public String getRemark(){
		return  remark;
	}
	public void setRemark(String remark ){
		this.remark = remark;
	}

	public Integer getGoods_Id() {
		return Goods_Id;
	}

	public void setGoods_Id(Integer goods_Id) {
		Goods_Id = goods_Id;
	}
}