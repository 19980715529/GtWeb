package com.smallchill.db.config.model.request;

import java.math.BigDecimal;
import java.util.Date;

import com.smallchill.core.base.model.BaseModel;

@SuppressWarnings("serial")
public class LivcardBuildStreamRequest extends BaseModel {
	private Integer buildid ;
	private Integer buildcount ;
	private Integer cardtypeid ;
	private Integer downloadcount ;
	private Integer gold ;
	private String adminname ;
	private String buildaddr ;
	private byte[] buildcardpacket ;
	private BigDecimal cardprice ;
	private BigDecimal currency ;
	private String noteinfo ;
	private Date builddate ;
	private Integer cardid ;
	private Integer nullity ;
	private Integer userange ;
	private String password ;
	private String salesperson ;
	private String serialid ;
	private Date applydate ;
	private Date validdate ;
	private String prefix;
	private Integer serialLength;
	private String passType;
	private Integer passLength;
	
	public LivcardBuildStreamRequest() {
	}
	
	public Integer getBuildid(){
		return  buildid;
	}
	public void setBuildid(Integer buildid ){
		this.buildid = buildid;
	}
	
	public Integer getBuildcount(){
		return  buildcount;
	}
	public void setBuildcount(Integer buildcount ){
		this.buildcount = buildcount;
	}
	
	public Integer getCardtypeid(){
		return  cardtypeid;
	}
	public void setCardtypeid(Integer cardtypeid ){
		this.cardtypeid = cardtypeid;
	}
	
	public Integer getDownloadcount(){
		return  downloadcount;
	}
	public void setDownloadcount(Integer downloadcount ){
		this.downloadcount = downloadcount;
	}
	
	public Integer getGold(){
		return  gold;
	}
	public void setGold(Integer gold ){
		this.gold = gold;
	}
	
	public String getAdminname(){
		return  adminname;
	}
	public void setAdminname(String adminname ){
		this.adminname = adminname;
	}
	
	public String getBuildaddr(){
		return  buildaddr;
	}
	public void setBuildaddr(String buildaddr ){
		this.buildaddr = buildaddr;
	}
	
	public byte[] getBuildcardpacket(){
		return  buildcardpacket;
	}
	public void setBuildcardpacket(byte[] buildcardpacket ){
		this.buildcardpacket = buildcardpacket;
	}
	
	public BigDecimal getCardprice(){
		return  cardprice;
	}
	public void setCardprice(BigDecimal cardprice ){
		this.cardprice = cardprice;
	}
	
	public BigDecimal getCurrency(){
		return  currency;
	}
	public void setCurrency(BigDecimal currency ){
		this.currency = currency;
	}
	
	public String getNoteinfo(){
		return  noteinfo;
	}
	public void setNoteinfo(String noteinfo ){
		this.noteinfo = noteinfo;
	}
	
	public Date getBuilddate(){
		return  builddate;
	}
	public void setBuilddate(Date builddate ){
		this.builddate = builddate;
	}
	public Integer getCardid() {
		return cardid;
	}
	public void setCardid(Integer cardid) {
		this.cardid = cardid;
	}
	public Integer getNullity() {
		return nullity;
	}
	public void setNullity(Integer nullity) {
		this.nullity = nullity;
	}
	public Integer getUserange() {
		return userange;
	}
	public void setUserange(Integer userange) {
		this.userange = userange;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalesperson() {
		return salesperson;
	}
	public void setSalesperson(String salesperson) {
		this.salesperson = salesperson;
	}
	public String getSerialid() {
		return serialid;
	}
	public void setSerialid(String serialid) {
		this.serialid = serialid;
	}
	public Date getApplydate() {
		return applydate;
	}
	public void setApplydate(Date applydate) {
		this.applydate = applydate;
	}
	public Date getValiddate() {
		return validdate;
	}
	public void setValiddate(Date validdate) {
		this.validdate = validdate;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public Integer getSerialLength() {
		return serialLength;
	}
	public void setSerialLength(Integer serialLength) {
		this.serialLength = serialLength;
	}
	public String getPassType() {
		return passType;
	}
	public void setPassType(String passType) {
		this.passType = passType;
	}
	public Integer getPassLength() {
		return passLength;
	}
	public void setPassLength(Integer passLength) {
		this.passLength = passLength;
	}
}
