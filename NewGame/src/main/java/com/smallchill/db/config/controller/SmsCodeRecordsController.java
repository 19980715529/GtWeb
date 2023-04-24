package com.smallchill.db.config.controller;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.toolbox.kit.CharsetKit;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.kit.URLKit;
import com.smallchill.game.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @classNameSmsCodeRecordsController
 * @Date 2023/3/2 20:20
 * @Version 1.0
 **/
@Controller
@RequestMapping("/smscoderecords")
public class SmsCodeRecordsController extends BaseController implements ConstShiro {

    private static String BASE_PATH = "/db/smscoderecords/";
    private static String CODE = "smscoderecords";
    private static String LIST_SOURCE = "smscoderecords.new_list";
    private static String PREFIX = "smscoderecords";

    @Autowired
    private CommonService commonService;
    @DoControllerLog(name="进入短信验证码记录列表页面")
    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "smscoderecords.html";
    }

    @Json
    @RequestMapping(KEY_LIST)
    //@Permission({ ADMINISTRATOR, ADMIN })
    public Object list() {
        Object gird = null;
        gird = paginateBySelf(LIST_SOURCE);
        return gird;
    }
}
