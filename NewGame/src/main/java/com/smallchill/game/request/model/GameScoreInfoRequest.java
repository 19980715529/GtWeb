package com.smallchill.game.request.model;

public class GameScoreInfoRequest {
	private Integer userid ;
	private Integer gameid ;
	private String type;
	private Long score ;
	private Long oldScore ;
	private Long userMedal ;
	private Long oldUserMedal ;
	private String remark;
	private String newPassword;
	private String nickName;
	private String relationType;
	private Integer membertypeId ;
	private Long cheatingscorenow ;//当前作弊分数
	private Long cheatingscoreset ;//设置作弊分数
	private Integer cheatingratenow ;//当前作弊率
	private Integer cheatingrateset ;//设置作弊率
	private Integer nullity ;//状态
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getScore() {
		return score;
	}
	public void setScore(Long score) {
		this.score = score;
	}
	public Long getOldScore() {
		return oldScore;
	}
	public void setOldScore(Long oldScore) {
		this.oldScore = oldScore;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Integer getMembertypeId() {
		return membertypeId;
	}
	public void setMembertypeId(Integer membertypeId) {
		this.membertypeId = membertypeId;
	}
	public Long getUserMedal() {
		return userMedal;
	}
	public void setUserMedal(Long userMedal) {
		this.userMedal = userMedal;
	}
	public Long getOldUserMedal() {
		return oldUserMedal;
	}
	public void setOldUserMedal(Long oldUserMedal) {
		this.oldUserMedal = oldUserMedal;
	}
	public Long getCheatingscorenow() {
		return cheatingscorenow;
	}
	public void setCheatingscorenow(Long cheatingscorenow) {
		this.cheatingscorenow = cheatingscorenow;
	}
	public Long getCheatingscoreset() {
		return cheatingscoreset;
	}
	public void setCheatingscoreset(Long cheatingscoreset) {
		this.cheatingscoreset = cheatingscoreset;
	}
	public Integer getCheatingratenow() {
		return cheatingratenow;
	}
	public void setCheatingratenow(Integer cheatingratenow) {
		this.cheatingratenow = cheatingratenow;
	}
	public Integer getCheatingrateset() {
		return cheatingrateset;
	}
	public void setCheatingrateset(Integer cheatingrateset) {
		this.cheatingrateset = cheatingrateset;
	}
	public Integer getNullity() {
		return nullity;
	}
	public void setNullity(Integer nullity) {
		this.nullity = nullity;
	}
	public Integer getGameid() {
		return gameid;
	}
	public void setGameid(Integer gameid) {
		this.gameid = gameid;
	}
	public String getRelationType() {
		return relationType;
	}
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
}
