package com.smallchill.system.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.smallchill.core.constant.ConstShiro.ADMIN;
import static com.smallchill.core.constant.ConstShiro.ADMINISTRATOR;
@Controller
@RequestMapping("/user_pack")
public class UserPackController extends BaseController {
    private static String LIST_SOURCE = "user_pack.list";
    private static String BASE_PATH = "/system/user_pack/";
    private static String CODE = "user_pack";
    private static String PREFIX = "user_pack";

    @DoControllerLog(name="进入用户包管理界面")
    @RequestMapping("/")
    //@Permission({ ADMINISTRATOR, ADMIN })
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "user_pack.html";
    }

    @Json
    @RequestMapping(KEY_LIST)
    @Permission({ ADMINISTRATOR, ADMIN })
    public Object list(ModelMap mm) {
        return paginateBySelf("");
    }

}
