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
@RequestMapping("/feedbackdata")
public class FeedbackDataController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/activity/feedbackdata/";
    private static String CODE = "feedbackdata";
    private static String LIST_SOURCE = "feedbackdata.all_list";
    private static String PREFIX = "feedbackdata";
    @Resource
    private CommonService commonService;

    @DoControllerLog(name="反馈数据分析")
    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH+"feedbackdata.html";
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
