package com.smallchill.common.base;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.smallchill.core.base.controller.BladeController;
import com.smallchill.core.constant.ConstCache;
import com.smallchill.core.constant.ConstCacheKey;
import com.smallchill.core.constant.ConstCurd;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.game.model.PlayerOperationLog;
import com.smallchill.game.model.UserMedalChangeInfo;
import com.smallchill.game.service.CommonService;

/**
 * 用于拓展controller类
 */
public class BaseController extends BladeController implements ConstCurd, ConstCache, ConstCacheKey {
	
	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	protected Session getSession() {
		return getSubject().getSession();
	}
	
	@Autowired
	private CommonService commonService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PlayerOperationLog genPlayerOperationLog(Integer id,Integer type) {
		Map paras = new HashMap();
		paras.put("UserID", id);
		Map info = commonService.getInfoByOne("player_search.new_detail", paras);
		
		String operateName = JSON.toJSONString(ShiroKit.getUser().getName()).replaceAll("\"", "");
		String userName = JSON.toJSONString(info.get("NickName")).replaceAll("\"", "");
		String tipsName = JSON.toJSONString(info.get("tipsName")).replaceAll("\"", "");
		if(StrKit.notBlank(tipsName)) {
			userName = userName + "["+tipsName+"]";
		}
		PlayerOperationLog log = new PlayerOperationLog();
		log.setUserid(id);
		log.setNickname(userName);
		log.setOperatorid(Integer.parseInt(JSON.toJSONString(ShiroKit.getUser().getId())));
		log.setOperatorname(operateName);
		log.setInserttime(new Date());
		if(info.get("Businessman") != null) {
			log.setUsertypeid(Integer.parseInt(JSON.toJSONString(info.get("Businessman"))));
		}
		log.setUsertypename(JSON.toJSONString(info.get("TypeName")).replaceAll("\"", ""));
		log.setStatus(1);
		log.setType(type);
		return log;
	}
	
	public void doLog(String id,Integer type) {
		PlayerOperationLog log = genPlayerOperationLog(Integer.parseInt(id), type);
		if(type == 2) {
			log.setDescription("操作员<span class=\"text-red\">【" + log.getOperatorname() + "】</span>锁定了玩家<span class=\"text-red\">【" + log.getNickname() + "-"+id+"】</span>");
		} else if(type == 15) {
			log.setDescription("操作员<span class=\"text-red\">【" + log.getOperatorname() + "】</span>解锁了玩家<span class=\"text-red\">【" + log.getNickname() + "-"+id+"】</span>");
		} else if(type == 5) {
			log.setDescription("操作员<span class=\"text-red\">【" + log.getOperatorname() + "】</span>禁止了玩家<span class=\"text-red\">【" + log.getNickname() + "-"+id+"】</span>进行交易");
		} else if(type == 4) {
			log.setDescription("操作员<span class=\"text-red\">【" + log.getOperatorname() + "】</span>禁止了玩家<span class=\"text-red\">【" + log.getNickname() + "-"+id+"】</span>上榜");
		} else if(type == 6) {
			log.setDescription("操作员<span class=\"text-red\">【" + log.getOperatorname() + "】</span>允许玩家<span class=\"text-red\">【" + log.getNickname() + "-"+id+"】</span>交易");
		} else if(type == 7) {
			log.setDescription("操作员<span class=\"text-red\">【" + log.getOperatorname() + "】</span>允许玩家<span class=\"text-red\">【" + log.getNickname() + "-"+id+"】</span>上榜");
		} else if(type == 18) {
			log.setDescription("操作员<span class=\"text-red\">【" + log.getOperatorname() + "】</span>设置玩家<span class=\"text-red\">【" + log.getNickname() + "-"+id+"】</span>为内部员工");
		} else if(type == 19) {
			log.setDescription("操作员<span class=\"text-red\">【" + log.getOperatorname() + "】</span>升级玩家<span class=\"text-red\">【" + log.getNickname() + "-"+id+"】</span>");
		} else if(type == 20) {
			log.setDescription("操作员<span class=\"text-red\">【" + log.getOperatorname() + "】</span>删除玩家<span class=\"text-red\">【" + log.getNickname() + "-"+id+"】</span>VIP");
		} else if(type == 21) {
			log.setDescription("操作员<span class=\"text-red\">【" + log.getOperatorname() + "】</span>拒绝玩家<span class=\"text-red\">【" + log.getNickname() + "-"+id+"】</span>VIP");
		}
		log.setOperateip(HttpKit.getRequest().getRemoteAddr());
		try {
			Blade.create(PlayerOperationLog.class).save(log);
		} catch(Exception e)  {
			
		}
	}
	public void doLogByMessage(String id,Integer type,String beforeStr, String changeStr) {
		PlayerOperationLog log = genPlayerOperationLog(Integer.parseInt(id), type);
		if(type == 8) {
			log.setDescription("操作员<span class=\"text-red\">【" + log.getOperatorname() + "】</span>更改了玩家<span class=\"text-red\">【" + log.getNickname() + "-"+id+"】</span>密码,更改后：" + changeStr);
		} else if(type == 9) {
			log.setDescription("操作员<span class=\"text-red\">【" + log.getOperatorname() + "】</span>更改了玩家<span class=\"text-red\">【" + log.getNickname() + "-"+id+"】</span>备注名,更改前：" + beforeStr + "; 更改后：" + changeStr);
		} else if(type == 10) {
			log.setDescription("操作员<span class=\"text-red\">【" + log.getOperatorname() + "】</span>更改了玩家<span class=\"text-red\">【" + log.getNickname() + "-"+id+"】</span>类型,更改前：" + beforeStr + "; 更改后：" + changeStr);
		} else if(type == 26) {
			log.setDescription("操作员<span class=\"text-red\">【" + log.getOperatorname() + "】</span>设置VIP<span class=\"text-red\">【" + log.getNickname() + "-"+id+"】</span>号为仓库号。");
		} else if(type == 27) {
			log.setDescription("操作员<span class=\"text-red\">【" + log.getOperatorname() + "】</span>取消VIP<span class=\"text-red\">【" + log.getNickname() + "-"+id+"】</span>号的仓库号。");
		}
		log.setOperateip(HttpKit.getRequest().getRemoteAddr());
		try {
			Blade.create(PlayerOperationLog.class).save(log);
		} catch(Exception e)  {
		}
	}

	public void doLogByGold(Integer id,Integer type, Long changeGold, Long beforeGold, String operateType,String remark) {
		PlayerOperationLog log = genPlayerOperationLog(id, type);
		log.setOperatereasontype(operateType);
		log.setBeforegold(beforeGold);
		log.setGold(changeGold);
		log.setAftergold(changeGold + beforeGold);
		log.setRemark(remark);
		log.setOperateip(HttpKit.getRequest().getRemoteAddr());
		if(type == 0) {
			log.setDescription("操作员<span class=\"text-red\">【" + log.getOperatorname() + "】</span>修改玩家<span class=\"text-red\">【" + log.getNickname() + "-"+id+"】</span>金币，更改值:" + changeGold);
		}if(type == 25) {
			log.setDescription("操作员<span class=\"text-red\">【" + log.getOperatorname() + "】</span>修改玩家<span class=\"text-red\">【" + log.getNickname() + "-"+id+"】</span>银行金币，更改值:" + changeGold);
		} else if(type == 1) {
			log.setDescription("操作员<span class=\"text-red\">【" + log.getOperatorname() + "】</span>修改玩家<span class=\"text-red\">【" + log.getNickname() + "-"+id+"】</span>奖券，更改值:" + changeGold);
			// 奖券变动表
			UserMedalChangeInfo userMedal = new UserMedalChangeInfo();
			userMedal.setUserid(id);
			userMedal.setNickname(log.getNickname());
			userMedal.setOperationuserid(log.getOperatorid());
			userMedal.setOperationname(log.getOperatorname());
			userMedal.setInserttime(new Date());
			userMedal.setClientip(log.getOperateip());
			userMedal.setMedalchange(log.getGold());
			userMedal.setBeforemedal(log.getBeforegold());
			userMedal.setEntermedal(log.getGold()+log.getBeforegold());
			userMedal.setRemark("系统扣除奖券");
			try {
				Blade.create(UserMedalChangeInfo.class).save(userMedal);
			} catch(Exception e)  {
				
			}
		} else if(type == 11) {
			log.setDescription("操作员<span class=\"text-red\">【" + log.getOperatorname() + "】</span>为玩家<span class=\"text-red\">【" + log.getNickname() + "-"+id+"】</span>充值，充值金额:" + changeGold);
		} else if(type == 12) {
			log.setDescription("操作员<span class=\"text-red\">【" + log.getOperatorname() + "】</span>退回礼物<br/>退回金币:<span class=\"text-red\">" + changeGold+"</span><br/>" + remark);
		} else if(type == 14) {
			log.setAftergold(changeGold);
			log.setDescription("操作员<span class=\"text-red\">【" + log.getOperatorname() + "】</span>修改玩家<span class=\"text-red\">【" + log.getNickname() + "-"+id+"】</span>作弊率:" + remark);
		} else if(type == 22) {
			log.setDescription("操作员<span class=\"text-red\">【" + log.getOperatorname() + "】</span>联合锁定玩家<span class=\"text-red\">【" + log.getNickname() + "-"+id+"】</span>，操作理由:" + remark);
		} else if(type == 23) {
			log.setDescription("操作员<span class=\"text-red\">【" + log.getOperatorname() + "】</span>联合禁止玩家<span class=\"text-red\">【" + log.getNickname() + "-"+id+"】</span>交易，操作理由:" + remark);
		} else if(type == 2) {
			log.setDescription("操作员<span class=\"text-red\">【" + log.getOperatorname() + "】</span>锁定了玩家<span class=\"text-red\">【" + log.getNickname() + "-"+id+"】</span>，操作理由:" + remark);
		} else if(type == 5) {
			log.setDescription("操作员<span class=\"text-red\">【" + log.getOperatorname() + "】</span>禁止了玩家<span class=\"text-red\">【" + log.getNickname() + "-"+id+"】</span>交易，操作理由:" + remark);
		} else if(type == 24) {
			log.setDescription("操作员<span class=\"text-red\">【" + log.getOperatorname() + "】</span>赠送玩家<span class=\"text-red\">【" + log.getNickname() + "-"+id+"】</span>金币，赠送金币数:" + changeGold);
		}
		try {
			Blade.create(PlayerOperationLog.class).save(log);
		} catch(Exception e)  {
			
		}
	}
	public void doLogByBackGold(Integer id,String sourceNickName,Integer targetUserID,String targetNickName,Date sendTime,Integer type, Long changeGold, Long beforeGold) {
		PlayerOperationLog log = genPlayerOperationLog(id, type);
		log.setUserid(id);
		log.setNickname(sourceNickName);
		log.setTargetuserid(targetUserID);
		log.setTargetusername(targetNickName);
		log.setSendtime(sendTime);
		log.setBeforegold(beforeGold);
		log.setGold(changeGold);
		log.setAftergold(changeGold + beforeGold);
		if(type == 12) {
			log.setDescription("操作员<span class=\"text-red\">【" + log.getOperatorname() + "】</span>退回礼物<br/>退回金币:<span class=\"text-red\">" + changeGold+"</span><br/>" + "从账户【" + targetNickName+ "-"+id+"】退回到账户【" + sourceNickName + "-"+targetUserID+"】");
		}
		log.setOperateip(HttpKit.getRequest().getRemoteAddr());
		try {
			Blade.create(PlayerOperationLog.class).save(log);
		} catch(Exception e)  {
			
		}
	}
}
