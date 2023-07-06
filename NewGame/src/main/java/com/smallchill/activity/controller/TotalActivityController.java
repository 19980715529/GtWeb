package com.smallchill.activity.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.game.service.CommonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/totalactivity")
public class TotalActivityController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/activity/totalactivity/";
    private static String CODE = "totalactivity";
    private static String LIST_SOURCE = "totalactivity.all_list";
    private static String PREFIX = "totalactivity";
    @Resource
    private CommonService commonService;

    @DoControllerLog(name="整体数据界面")
    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH+"totalactivity.html";
    }

    /**
     * 列表查询
     */
    @Json
    @RequestMapping(KEY_LIST)
    public Object list() {
        Object gird;
        // 解析查询条件
        gird = paginateBySelf(LIST_SOURCE);
        return gird;
    }
}
