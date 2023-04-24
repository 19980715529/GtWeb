package com.smallchill.game.newmodel.gameuserdb;

import java.util.Date;

import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameuserdb")
@Table(name = "AA_Shop_Goods_UserGoods")
@BindID(name = "Order_No")
@SuppressWarnings("serial")
public class AaShopGoodsUsergoods extends BaseModel {
	private String Order_No ;
	private Integer amount ;
	private Integer currentstate ;
	private Integer Goods_Id ;
	private Integer ticket ;
	private Integer User_Id ;
	private Integer gid ;
	private String address ;
	private String expressinformation ;
	private String phonenumber ;
	private Long totalamount ;
	private String username ;
	private String zipcode ;
	private Date buytime ;
	private Date handletime ;
	
	public AaShopGoodsUsergoods() {
	}
	
	public Integer getAmount(){
		return  amount;
	}
	public void setAmount(Integer amount ){
		this.amount = amount;
	}
	
	public Integer getCurrentstate(){
		return  currentstate;
	}
	public void setCurrentstate(Integer currentstate ){
		this.currentstate = currentstate;
	}
	
	public Integer getTicket(){
		return  ticket;
	}
	public void setTicket(Integer ticket ){
		this.ticket = ticket;
	}
	
	public Integer getGid(){
		return  gid;
	}
	public void setGid(Integer gid ){
		this.gid = gid;
	}
	
	public String getAddress(){
		return  address;
	}
	public void setAddress(String address ){
		this.address = address;
	}
	
	public String getExpressinformation(){
		return  expressinformation;
	}
	public void setExpressinformation(String expressinformation ){
		this.expressinformation = expressinformation;
	}
	
	public String getPhonenumber(){
		return  phonenumber;
	}
	public void setPhonenumber(String phonenumber ){
		this.phonenumber = phonenumber;
	}
	
	public Long getTotalamount(){
		return  totalamount;
	}
	public void setTotalamount(Long totalamount ){
		this.totalamount = totalamount;
	}
	
	public String getUsername(){
		return  username;
	}
	public void setUsername(String username ){
		this.username = username;
	}
	
	public String getZipcode(){
		return  zipcode;
	}
	public void setZipcode(String zipcode ){
		this.zipcode = zipcode;
	}
	
	public Date getBuytime(){
		return  buytime;
	}
	public void setBuytime(Date buytime ){
		this.buytime = buytime;
	}
	
	public Date getHandletime(){
		return  handletime;
	}
	public void setHandletime(Date handletime ){
		this.handletime = handletime;
	}

	public String getOrder_No() {
		return Order_No;
	}

	public void setOrder_No(String order_No) {
		Order_No = order_No;
	}

	public Integer getGoods_Id() {
		return Goods_Id;
	}

	public void setGoods_Id(Integer goods_Id) {
		Goods_Id = goods_Id;
	}

	public Integer getUser_Id() {
		return User_Id;
	}

	public void setUser_Id(Integer user_Id) {
		User_Id = user_Id;
	}
}