//package com.smallchill.game.newmodel.gameuserdb;
//
//import java.util.Date;
//
//import org.beetl.sql.core.annotatoin.Table;
//
//import com.smallchill.core.annotation.BindID;
//import com.smallchill.core.annotation.DbName;
//import com.smallchill.core.base.model.BaseModel;
//
//@DbName(name = "gameuserdb")
//@Table(name = "AA_Recharge_ThirdPayPlatform")
//@BindID(name = "id")
//@SuppressWarnings("serial")
//public class AaRechargeThirdpayplatform extends BaseModel {
//	private Integer id ;
//	private String app_id ;
//	private String createtime ;
//	private String para_id ;
//	private String para_key ;
//	private String platfrom ;
//	private String status ;
//	private Date update_time ;
//	
//	public AaRechargeThirdpayplatform() {
//	}
//	
//	public String getApp_id() {
//		return app_id;
//	}
//
//	public void setApp_id(String app_id) {
//		this.app_id = app_id;
//	}
//
//	public String getPara_id() {
//		return para_id;
//	}
//
//	public void setPara_id(String para_id) {
//		this.para_id = para_id;
//	}
//
//	public String getPara_key() {
//		return para_key;
//	}
//
//	public void setPara_key(String para_key) {
//		this.para_key = para_key;
//	}
//
//	public Date getUpdate_time() {
//		return update_time;
//	}
//
//	public void setUpdate_time(Date update_time) {
//		this.update_time = update_time;
//	}
//
//	public Integer getId(){
//		return  id;
//	}
//	public void setId(Integer id ){
//		this.id = id;
//	}
//	
//	public String getCreatetime(){
//		return  createtime;
//	}
//	public void setCreatetime(String createtime ){
//		this.createtime = createtime;
//	}
//	
//	public String getPlatfrom(){
//		return  platfrom;
//	}
//	public void setPlatfrom(String platfrom ){
//		this.platfrom = platfrom;
//	}
//	
//	public String getStatus(){
//		return  status;
//	}
//	public void setStatus(String status ){
//		this.status = status;
//	}
//}