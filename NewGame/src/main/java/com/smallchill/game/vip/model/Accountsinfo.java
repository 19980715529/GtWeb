package com.smallchill.game.vip.model;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "account")
@Table(name = "AccountsInfo")
@BindID(name = "UserID")
@SuppressWarnings("serial")
public class Accountsinfo extends BaseModel {
	private Integer userid ;
	private Integer agentid ;
	private Integer customfacever ;
	private Integer customid ;
	private Integer experience ;
	private Integer faceid ;
	private Integer gameid ;
	private Integer gamelogontimes ;
	private Integer gender ;
	private Integer growlevelid ;
	private Integer isandroid ;
	private Integer loveliness ;
	private Integer masterorder ;
	private Integer masterright ;
	private Integer memberorder ;
	private Integer membertypeid ;
	private Integer moormachine ;
	private Integer nullity ;
	private Integer onlinetimecount ;
	private Integer passwordid ;
	private Integer platformid ;
	private Integer playtimecount ;
	private Integer present ;
	private Integer protectid ;
	private Integer rankid ;
	private Integer registerorigin ;
	private Integer serviceright ;
	private Integer spreaderid ;
	private Integer stundown ;
	private Integer userright ;
	private Integer weblogontimes ;
	private String accounts ;
	private String compellation ;
	private String dynamicpass ;
	private Long highscore ;
	private String insurepass ;
	private String lastlogonip ;
	private String lastlogonmachine ;
	private String lastlogonmobile ;
	private String logonpass ;
	private String nickname ;
	private String passportid ;
	private String regaccounts ;
	private String registerip ;
	private String registermachine ;
	private String registermobile ;
	private String underwrite ;
	private Long usermedal ;
	private String useruin ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date dynamicpasstime ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date lastlogondate ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date memberoverdate ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date memberswitchdate ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date nullityoverdate ;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date registerdate ;
	
	public Accountsinfo() {
	}
	@AutoID
	@SeqID(name = "SEQ_Accountsinfo")
	public Integer getUserid(){
		return  userid;
	}
	public void setUserid(Integer userid ){
		this.userid = userid;
	}
	
	public Integer getAgentid(){
		return  agentid;
	}
	public void setAgentid(Integer agentid ){
		this.agentid = agentid;
	}
	
	public Integer getCustomfacever(){
		return  customfacever;
	}
	public void setCustomfacever(Integer customfacever ){
		this.customfacever = customfacever;
	}
	
	public Integer getCustomid(){
		return  customid;
	}
	public void setCustomid(Integer customid ){
		this.customid = customid;
	}
	
	public Integer getExperience(){
		return  experience;
	}
	public void setExperience(Integer experience ){
		this.experience = experience;
	}
	
	public Integer getFaceid(){
		return  faceid;
	}
	public void setFaceid(Integer faceid ){
		this.faceid = faceid;
	}
	
	public Integer getGameid(){
		return  gameid;
	}
	public void setGameid(Integer gameid ){
		this.gameid = gameid;
	}
	
	public Integer getGamelogontimes(){
		return  gamelogontimes;
	}
	public void setGamelogontimes(Integer gamelogontimes ){
		this.gamelogontimes = gamelogontimes;
	}
	
	public Integer getGender(){
		return  gender;
	}
	public void setGender(Integer gender ){
		this.gender = gender;
	}
	
	public Integer getGrowlevelid(){
		return  growlevelid;
	}
	public void setGrowlevelid(Integer growlevelid ){
		this.growlevelid = growlevelid;
	}
	
	public Integer getIsandroid(){
		return  isandroid;
	}
	public void setIsandroid(Integer isandroid ){
		this.isandroid = isandroid;
	}
	
	public Integer getLoveliness(){
		return  loveliness;
	}
	public void setLoveliness(Integer loveliness ){
		this.loveliness = loveliness;
	}
	
	public Integer getMasterorder(){
		return  masterorder;
	}
	public void setMasterorder(Integer masterorder ){
		this.masterorder = masterorder;
	}
	
	public Integer getMasterright(){
		return  masterright;
	}
	public void setMasterright(Integer masterright ){
		this.masterright = masterright;
	}
	
	public Integer getMemberorder(){
		return  memberorder;
	}
	public void setMemberorder(Integer memberorder ){
		this.memberorder = memberorder;
	}
	
	public Integer getMembertypeid(){
		return  membertypeid;
	}
	public void setMembertypeid(Integer membertypeid ){
		this.membertypeid = membertypeid;
	}
	
	public Integer getMoormachine(){
		return  moormachine;
	}
	public void setMoormachine(Integer moormachine ){
		this.moormachine = moormachine;
	}
	
	public Integer getNullity(){
		return  nullity;
	}
	public void setNullity(Integer nullity ){
		this.nullity = nullity;
	}
	
	public Integer getOnlinetimecount(){
		return  onlinetimecount;
	}
	public void setOnlinetimecount(Integer onlinetimecount ){
		this.onlinetimecount = onlinetimecount;
	}
	
	public Integer getPasswordid(){
		return  passwordid;
	}
	public void setPasswordid(Integer passwordid ){
		this.passwordid = passwordid;
	}
	
	public Integer getPlatformid(){
		return  platformid;
	}
	public void setPlatformid(Integer platformid ){
		this.platformid = platformid;
	}
	
	public Integer getPlaytimecount(){
		return  playtimecount;
	}
	public void setPlaytimecount(Integer playtimecount ){
		this.playtimecount = playtimecount;
	}
	
	public Integer getPresent(){
		return  present;
	}
	public void setPresent(Integer present ){
		this.present = present;
	}
	
	public Integer getProtectid(){
		return  protectid;
	}
	public void setProtectid(Integer protectid ){
		this.protectid = protectid;
	}
	
	public Integer getRankid(){
		return  rankid;
	}
	public void setRankid(Integer rankid ){
		this.rankid = rankid;
	}
	
	public Integer getRegisterorigin(){
		return  registerorigin;
	}
	public void setRegisterorigin(Integer registerorigin ){
		this.registerorigin = registerorigin;
	}
	
	public Integer getServiceright(){
		return  serviceright;
	}
	public void setServiceright(Integer serviceright ){
		this.serviceright = serviceright;
	}
	
	public Integer getSpreaderid(){
		return  spreaderid;
	}
	public void setSpreaderid(Integer spreaderid ){
		this.spreaderid = spreaderid;
	}
	
	public Integer getStundown(){
		return  stundown;
	}
	public void setStundown(Integer stundown ){
		this.stundown = stundown;
	}
	
	public Integer getUserright(){
		return  userright;
	}
	public void setUserright(Integer userright ){
		this.userright = userright;
	}
	
	public Integer getWeblogontimes(){
		return  weblogontimes;
	}
	public void setWeblogontimes(Integer weblogontimes ){
		this.weblogontimes = weblogontimes;
	}
	
	public String getAccounts(){
		return  accounts;
	}
	public void setAccounts(String accounts ){
		this.accounts = accounts;
	}
	
	public String getCompellation(){
		return  compellation;
	}
	public void setCompellation(String compellation ){
		this.compellation = compellation;
	}
	
	public String getDynamicpass(){
		return  dynamicpass;
	}
	public void setDynamicpass(String dynamicpass ){
		this.dynamicpass = dynamicpass;
	}
	
	public Long getHighscore(){
		return  highscore;
	}
	public void setHighscore(Long highscore ){
		this.highscore = highscore;
	}
	
	public String getInsurepass(){
		return  insurepass;
	}
	public void setInsurepass(String insurepass ){
		this.insurepass = insurepass;
	}
	
	public String getLastlogonip(){
		return  lastlogonip;
	}
	public void setLastlogonip(String lastlogonip ){
		this.lastlogonip = lastlogonip;
	}
	
	public String getLastlogonmachine(){
		return  lastlogonmachine;
	}
	public void setLastlogonmachine(String lastlogonmachine ){
		this.lastlogonmachine = lastlogonmachine;
	}
	
	public String getLastlogonmobile(){
		return  lastlogonmobile;
	}
	public void setLastlogonmobile(String lastlogonmobile ){
		this.lastlogonmobile = lastlogonmobile;
	}
	
	public String getLogonpass(){
		return  logonpass;
	}
	public void setLogonpass(String logonpass ){
		this.logonpass = logonpass;
	}
	
	public String getNickname(){
		return  nickname;
	}
	public void setNickname(String nickname ){
		this.nickname = nickname;
	}
	
	public String getPassportid(){
		return  passportid;
	}
	public void setPassportid(String passportid ){
		this.passportid = passportid;
	}
	
	public String getRegaccounts(){
		return  regaccounts;
	}
	public void setRegaccounts(String regaccounts ){
		this.regaccounts = regaccounts;
	}
	
	public String getRegisterip(){
		return  registerip;
	}
	public void setRegisterip(String registerip ){
		this.registerip = registerip;
	}
	
	public String getRegistermachine(){
		return  registermachine;
	}
	public void setRegistermachine(String registermachine ){
		this.registermachine = registermachine;
	}
	
	public String getRegistermobile(){
		return  registermobile;
	}
	public void setRegistermobile(String registermobile ){
		this.registermobile = registermobile;
	}
	
	public String getUnderwrite(){
		return  underwrite;
	}
	public void setUnderwrite(String underwrite ){
		this.underwrite = underwrite;
	}
	
	public Long getUsermedal(){
		return  usermedal;
	}
	public void setUsermedal(Long usermedal ){
		this.usermedal = usermedal;
	}
	
	public String getUseruin(){
		return  useruin;
	}
	public void setUseruin(String useruin ){
		this.useruin = useruin;
	}
	
	public Date getDynamicpasstime(){
		return  dynamicpasstime;
	}
	public void setDynamicpasstime(Date dynamicpasstime ){
		this.dynamicpasstime = dynamicpasstime;
	}
	
	public Date getLastlogondate(){
		return  lastlogondate;
	}
	public void setLastlogondate(Date lastlogondate ){
		this.lastlogondate = lastlogondate;
	}
	
	public Date getMemberoverdate(){
		return  memberoverdate;
	}
	public void setMemberoverdate(Date memberoverdate ){
		this.memberoverdate = memberoverdate;
	}
	
	public Date getMemberswitchdate(){
		return  memberswitchdate;
	}
	public void setMemberswitchdate(Date memberswitchdate ){
		this.memberswitchdate = memberswitchdate;
	}
	
	public Date getNullityoverdate(){
		return  nullityoverdate;
	}
	public void setNullityoverdate(Date nullityoverdate ){
		this.nullityoverdate = nullityoverdate;
	}
	
	public Date getRegisterdate(){
		return  registerdate;
	}
	public void setRegisterdate(Date registerdate ){
		this.registerdate = registerdate;
	}
}