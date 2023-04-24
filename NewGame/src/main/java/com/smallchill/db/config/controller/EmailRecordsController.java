package com.smallchill.db.config.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.common.vo.ShiroUser;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.db.config.meta.intercept.EmailSendValidator;
import com.smallchill.db.config.model.EmailRecords;
import com.smallchill.game.service.CommonService;
import com.smallchill.system.treasure.utils.SendHttp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @classNameEmailRecordsController
 * @Date 2023/3/14 16:07
 * @Version 1.0
 **/
@Controller
@RequestMapping("/emailrecords")
public class EmailRecordsController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/db/email/emailrecords/";
    private static String CODE = "emailrecords";
    private static String LIST_SOURCE = "emailrecords.new_list";
    private static String PREFIX = "emailrecords";

    @Autowired
    private CommonService commonService;
    @DoControllerLog(name="进入邮件记录页面")
    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "emailrecords.html";
    }
    @Json
    @RequestMapping(KEY_LIST)
    public Object list(){
        Object grid = paginateBySelf(LIST_SOURCE);
        return grid;
    }
    /**
     * 发送邮件
     */
    @Json
    @Before(EmailSendValidator.class)
    @RequestMapping("/send")
    public AjaxResult send(){
        EmailRecords emailRecords = mapping(PREFIX, EmailRecords.class);
        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("UserId",emailRecords.getUserId());
        List<Map> feeList = commonService.getInfoList("db_Shop_Prop_UserProp.one_user", userMap);
        if (feeList.size() == 0){
            return fail("玩家不存在");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("gold",emailRecords.getAmount());
        map.put("title",emailRecords.getTitle());
        map.put("toUserid",emailRecords.getUserId());
        map.put("content",emailRecords.getContent());
        // 根据发件人id查询发件人名称
        String sender = Db.queryStr("select sender from [QPServerInfoDB].[dbo].[CustomEmaolTitle] where id=#{id}",
                CMap.init().set("id", emailRecords.getSenderId()));
        emailRecords.setEmailSender(sender);
        map.put("senderId",emailRecords.getSenderId());
        map.put("goldType",emailRecords.getEmailType());
        SendHttp.sendEmail(map);
        ShiroUser user = ShiroKit.getUser();
        String username = Func.toStr(user.getName());
        emailRecords.setSender(username);
        boolean update = Blade.create(EmailRecords.class).save(emailRecords);
        if (!update){
            return fail("发送失败");
        }
        CacheKit.removeAll(SYS_CACHE);
        return success("发送成功");
    }
}
