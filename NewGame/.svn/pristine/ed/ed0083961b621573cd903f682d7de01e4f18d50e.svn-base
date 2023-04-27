package com.smallchill.game.player.controller;

import com.alibaba.fastjson.JSON;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.game.model.UserRightType;
import com.smallchill.game.newmodel.AaGiverecord;
import com.smallchill.game.newmodel.Accountsinfo;
import com.smallchill.game.newmodel.Gamescoreinfo;
import com.smallchill.game.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstConfig;
import com.smallchill.core.constant.ConstShiro;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/player")
public class PlayerGiftLogController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/gameplayer/";
    private static String CODE = "player";
    @Autowired
    private CommonService commonService;

    @DoControllerLog(name = "进入赠送礼物记录列表页面")
    @RequestMapping(KEY_PLAYER_GIFT_LOG)
    //@Permission({ ADMINISTRATOR, ADMIN })
    public String giftlog(@RequestParam(name = "id", required = false) Integer id, ModelMap mm) {
        mm.put("code", CODE);
        mm.put("id", id);
        return BASE_PATH + "player_gift_log.html";
    }

    //	@SystemControllerLog(description = "获取赠送礼物记录列表")
    // 加载赠送
    @Json
    @RequestMapping("/gllist")
    //@Permission({ ADMINISTRATOR, ADMIN })
    public Object gllist(Model model) {
        Object gird = new Object();

        //String userid = parameter.substring(parameter.indexOf("UserID")+8,parameter.indexOf(","));
        //String starttime = parameter.substring(parameter.indexOf("startTime_gt")+15,parameter.indexOf("\"endTime_lt\"")-2);
		/*Map map = new HashMap();
		map.put("UserID", );
		*/
        gird = paginateBySelf("player_gift_log.old_gift_log");
        return gird;
    }

    @Json
    @RequestMapping("/total")
    public AjaxResult total() {
        String parameter = getRequest().getParameter("cont");
        Map m1 = new HashMap();
        if (parameter != null) {
            Map maps = (Map) JSON.parse(parameter);
            for (Object map : maps.entrySet()) {
                m1.put(((Map.Entry) map).getKey(), ((Map.Entry) map).getValue());
            }
        }
        Map total = commonService.getInfoByOne("player_gift_log.all_prop_count", m1);
        Map receive = commonService.getInfoByOne("player_gift_log.all_prop_receive_count", m1);
        total.put("receiveCount", receive.get("allcount"));
        return json(total);
    }

    @DoControllerLog(name="礼物退回")
    @Json
    @RequestMapping("/giftblack/{id}")
    @Transactional
    public AjaxResult gitfback(@PathVariable int id) {
        AaGiverecord giverecord = Blade.create(AaGiverecord.class).findById(id);
        Accountsinfo fromUser = Blade.create(Accountsinfo.class).findById(giverecord.getFrom_user());
        Accountsinfo toUser = Blade.create(Accountsinfo.class).findById(giverecord.getTo_user());
        if (fromUser == null || toUser == null) {
            return error("查无此人");
        }
		boolean isUpdate = false;
		if((((fromUser.getBusinessman() != null && fromUser.getBusinessman()==1) 
				&& (toUser.getBusinessman() == null || toUser.getBusinessman()==0)))){
			isUpdate = true;
		}
        Gamescoreinfo fromInscore = Blade.create(Gamescoreinfo.class).findById(fromUser.getUserid());
        Gamescoreinfo toInscore = Blade.create(Gamescoreinfo.class).findById(toUser.getUserid());
        
        try {
            //如果金额充足
            if (toInscore.getInsurescore() >= giverecord.getProp_count() && toInscore.getInsurescore() != 0) {
                //如果转出是商人
                if (fromUser.getBusinessman() == 1) {
                    if (fromInscore != null && toInscore != null) {
                        //赠送的增加金币
                        double send = 0D;
                        if(isUpdate && ConstConfig.SYS_ISBACKTAX.equalsIgnoreCase("true")) {
                        	send = fromInscore.getInsurescore() + giverecord.getProp_count() * 0.98;
                        } else {
                        	send = fromInscore.getInsurescore() + giverecord.getProp_count();
                        }
                        Blade.create(Gamescoreinfo.class).updateBy(" insurescore=" + (send), " UserID=" + giverecord.getFrom_user(), null);
                        //接受的减少金币
                        double back = 0D;
                        if(isUpdate) {
                        	back = toInscore.getInsurescore() - giverecord.getProp_count() * 0.98;
                        } else {
                        	back = toInscore.getInsurescore() - giverecord.getProp_count();
                        }
                        if (back < 0) {
                            back = 0L;
                        }
                        Blade.create(Gamescoreinfo.class).updateBy(" insurescore=" + (back), " UserID=" + giverecord.getTo_user(), null);
                        //修改记录状态
                        Blade.create(AaGiverecord.class).updateBy("[open] = 2", "id=" + giverecord.getId(), null);
                        return success("退回成功");
                    } else {
                        return error("无法查询该用户银行金币");
                    }
                } else {
                    //如果转出是玩家
                    if (fromInscore != null && toInscore != null) {
                        //赠送的增加金币
                        long send = fromInscore.getInsurescore() + giverecord.getProp_count();
                        Blade.create(Gamescoreinfo.class).updateBy(" insurescore=" + (send), " UserID=" + giverecord.getFrom_user(), null);
                        //接受的减少金币
                        Long back = toInscore.getInsurescore() - giverecord.getProp_count();
                        if (back < 0) {
                            back = 0L;
                        }
                        Blade.create(Gamescoreinfo.class).updateBy(" insurescore=" + (back), " UserID=" + giverecord.getTo_user(), null);
                        //修改记录状态
                        Blade.create(AaGiverecord.class).updateBy("[open]=2", "id=" + giverecord.getId(), null);
                        return success("退回成功");
                    } else {
                        return error("无法查询该用户银行金币");
                    }
                }
            } else {
                //如果金额不足
                if (toInscore.getInsurescore() == 0) {
                    return error("无法退回,当前需退回者金币为0");
                } else {
                    //剩多少扣多少
                    if (fromInscore != null && toInscore != null) {
                        //赠送的增加金币
                        long send = fromInscore.getInsurescore() + toInscore.getInsurescore();
                        Blade.create(Gamescoreinfo.class).updateBy(" insurescore=" + (send), " UserID=" + giverecord.getFrom_user(), null);
                        //接受的金币归 0
                        Blade.create(Gamescoreinfo.class).updateBy(" insurescore=0", " UserID=" + giverecord.getTo_user(), null);
                        //修改记录状态
                        Blade.create(AaGiverecord.class).updateBy("[open]=2", "id=" + giverecord.getId(), null);
                        return success("退回成功");
                    } else {
                        return error("无法查询该用户银行金币");
                    }
                }
            }
        } catch (Exception e) {
            return error("运行失败!");
        } finally {
            String sourceNickName = fromUser.getNickname();
            if (StrKit.notBlank(fromUser.getTipsname())) {
                sourceNickName = sourceNickName + "[" + fromUser.getTipsname() + "]";
            }
            String targetNickName = toUser.getNickname();
            if (StrKit.notBlank(toUser.getTipsname())) {
                targetNickName = targetNickName + "[" + toUser.getTipsname() + "]";
            }
            doLogByBackGold(giverecord.getFrom_user(), sourceNickName, giverecord.getTo_user(), targetNickName, giverecord.getGivetime(), UserRightType.UR_GIFT_REFUND.code(), giverecord.getProp_count(), Long.valueOf(giverecord.getId()));
        }
    }
}