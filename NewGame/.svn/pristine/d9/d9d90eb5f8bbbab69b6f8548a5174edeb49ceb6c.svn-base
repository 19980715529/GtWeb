package com.smallchill.game.newmodel.gameuserdb;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameuserdb")
@Table(name = "AA_Recharge_ThirdPayConfig")
@BindID(name = "id")
@SuppressWarnings("serial")
public class AARechargeThirdPayConfig extends BaseModel {
	private Integer id ;
	private String appId ;
	private String charset ;
	private String format ;
	private int payon;
	private String partner_private_key ;
	private String serverUrl ;
	private String sign_type ;
	private String third_public_key ;
	private Integer type ;
	private Date client_create_time;
	private Date update_time;
	
	public AARechargeThirdPayConfig() {
	}
	@AutoID
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public String getAppId(){
		return  appId;
	}
	public void setAppId(String appId ){
		this.appId = appId;
	}
	
	public String getCharset(){
		return  charset;
	}
	public void setCharset(String charset ){
		this.charset = charset;
	}
	
	public String getFormat(){
		return  format;
	}
	public void setFormat(String format ){
		this.format = format;
	}
	
	public String getPartner_private_key(){
		return  partner_private_key;
	}
	public void setPartner_private_key(String partner_private_key ){
		this.partner_private_key = partner_private_key;
	}
	
	public String getServerUrl(){
		return  serverUrl;
	}
	public void setServerUrl(String serverUrl ){
		this.serverUrl = serverUrl;
	}
	
	public String getSign_type(){
		return  sign_type;
	}
	public void setSign_type(String sign_type ){
		this.sign_type = sign_type;
	}
	
	public String getThird_public_key(){
		return  third_public_key;
	}
	public void setThird_public_key(String third_public_key ){
		this.third_public_key = third_public_key;
	}

	public Integer getType() {
		return type;
	}

	public int getPayon() {
		return payon;
	}

	public void setPayon(int payon) {
		this.payon = payon;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getClient_create_time() {
		return client_create_time;
	}

	public void setClient_create_time(Date client_create_time) {
		this.client_create_time = client_create_time;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	@Override
	public String toString() {
		return "AARechargeThirdPayConfig{" +
				"id=" + id +
				", appId='" + appId + '\'' +
				", charset='" + charset + '\'' +
				", format='" + format + '\'' +
				", payon=" + payon +
				", partner_private_key='" + partner_private_key + '\'' +
				", serverUrl='" + serverUrl + '\'' +
				", sign_type='" + sign_type + '\'' +
				", third_public_key='" + third_public_key + '\'' +
				", type=" + type +
				", client_create_time=" + client_create_time +
				", update_time=" + update_time +
				'}';
	}
}
