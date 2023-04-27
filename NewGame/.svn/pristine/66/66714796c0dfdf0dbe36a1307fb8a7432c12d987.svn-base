package com.smallchill.db.config.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.game.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description TODO
 * @classNameEmailConf 邮件管理
 * @Date 2023/3/14 10:48
 * @Version 1.0
 **/
@Controller
@RequestMapping("/emailconf")
public class EmailConfController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/db/email/emailconf/";
    private static String CODE = "emailconf";
    private static String LIST_SOURCE = "emailconf.new_list";
    private static String PREFIX = "emailconf";

    @Autowired
    private CommonService commonService;
    @DoControllerLog(name="进入邮件管理页面")
    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "emailconf.html";
    }
}
