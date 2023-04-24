package com.smallchill.game.newmodel.serverinfodb;

import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.base.model.BaseModel;

@DbName(name = "gameroomitemdb")
@Table(name = "L_QuestionUser")
@BindID(name = "userid")
@SuppressWarnings("serial")
public class LQuestionuser extends BaseModel {
	private Integer userid ;
	private String reson ;
	
	public LQuestionuser() {
	}
	
	@AssignID
	public Integer getUserid(){
		return  userid;
	}
	public void setUserid(Integer userid ){
		this.userid = userid;
	}
	
	public String getReson(){
		return  reson;
	}
	public void setReson(String reson ){
		this.reson = reson;
	}
}