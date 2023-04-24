package com.smallchill.system.nativeweb.model;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;
@DbName(name = "nativeWeb")
@Table(name = "News")
@BindID(name = "newsId")
@SuppressWarnings("serial")
//公告配置表
public class News extends BaseModel {
	private Integer newsId; //唯一标识
	private Integer popId; //弹出窗口
	private String subject; //主标题
	private String subject1; //副标题
	private Short onTop;//置顶标识
	private Short onTopAll;//总置顶标识
	private Short isElite;//精华标识
	private Short isHot;//热点标识
	private Short isLock;//锁定标识 0-锁定；1-发布
	private Short isDelete;//删除标识
	private Short isLinks;//外部链接标识
	private String linkUrl;//外部链接地址
	private String body;//公告内容
	private String formattedBody;//带格式公告内容
	private String highLight;//标题颜色
	private Short classID;//新闻类别 1-电脑端新闻;2-电脑端公告;3-移动端公告;4-房卡公告
	private String gameRange;//手机公告显示范围
	private String imageUrl;//游戏图标
	private Integer userId; //发布人标识
	private String issueIP; //发布地址
	private String lastModifyIP; //最后更新地址
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date issueDate;//发布时间
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date lastModifyDate;//更新时间
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date startTime;//发送开始时间
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date endTime;//发送结束时间
	private Integer interval;//间隔时间
	private Integer ptypeid ;

	@AutoID //mysql自增
	@SeqID(name = "SEQ_NEWS") //oracle sequence自增
	//两者只需要写一个,根据数据库不同来选择
	public Integer getNewsId() {
		return newsId;
	}
	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}
	public Integer getPopId() {
		return popId;
	}
	public void setPopId(Integer popId) {
		this.popId = popId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSubject1() {
		return subject1;
	}
	public void setSubject1(String subject1) {
		this.subject1 = subject1;
	}
	public Short getOnTop() {
		return onTop;
	}
	public void setOnTop(Short onTop) {
		this.onTop = onTop;
	}
	public Short getOnTopAll() {
		return onTopAll;
	}
	public void setOnTopAll(Short onTopAll) {
		this.onTopAll = onTopAll;
	}
	public Short getIsElite() {
		return isElite;
	}
	public void setIsElite(Short isElite) {
		this.isElite = isElite;
	}
	public Short getIsHot() {
		return isHot;
	}
	public void setIsHot(Short isHot) {
		this.isHot = isHot;
	}
	public Short getIsLock() {
		return isLock;
	}
	public void setIsLock(Short isLock) {
		this.isLock = isLock;
	}
	public Short getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Short isDelete) {
		this.isDelete = isDelete;
	}
	public Short getIsLinks() {
		return isLinks;
	}
	public void setIsLinks(Short isLinks) {
		this.isLinks = isLinks;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getFormattedBody() {
		return formattedBody;
	}
	public void setFormattedBody(String formattedBody) {
		this.formattedBody = formattedBody;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getHighLight() {
		return highLight;
	}
	public Integer getInterval() {
		return interval;
	}
	public void setInterval(Integer interval) {
		this.interval = interval;
	}
	public void setHighLight(String highLight) {
		this.highLight = highLight;
	}
	public Short getClassID() {
		return classID;
	}
	public void setClassID(Short classID) {
		this.classID = classID;
	}
	public String getGameRange() {
		return gameRange;
	}
	public void setGameRange(String gameRange) {
		this.gameRange = gameRange;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getIssueIP() {
		return issueIP;
	}
	public void setIssueIP(String issueIP) {
		this.issueIP = issueIP;
	}
	public String getLastModifyIP() {
		return lastModifyIP;
	}
	public void setLastModifyIP(String lastModifyIP) {
		this.lastModifyIP = lastModifyIP;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	public Date getLastModifyDate() {
		return lastModifyDate;
	}
	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	public Integer getPtypeid() {
		return ptypeid;
	}
	public void setPtypeid(Integer ptypeid) {
		this.ptypeid = ptypeid;
	}
}
