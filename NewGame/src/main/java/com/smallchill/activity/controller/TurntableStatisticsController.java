package com.smallchill.activity.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.game.service.CommonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping("/turntablestatistics")
public class TurntableStatisticsController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/activity/turntablestatistics/";
    private static String CODE = "turntablestatistics";
    private static String LIST_SOURCE = "turntablestatistics.all_list";

    private static String PREFIX = "turntablestatistics";
    @Resource
    private CommonService commonService;

    @DoControllerLog(name="签到数据统计")
    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH+"turntablestatistics.html";
    }

    @RequestMapping("/details")
    public String details(ModelMap mm, @RequestParam String createTime, @RequestParam Integer clientType){
        mm.put("code", CODE);
        mm.put("createTime", createTime);
        mm.put("clientType", clientType);
        return BASE_PATH+"turntablestatistics_details.html";
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

    /**
     * 列表查询
     */
    @Json
    @RequestMapping("/details/list")
    public Object details_list() {
        Object gird;
        // 解析查询条件
        gird = paginateBySelf("turntablestatistics.list_details");
        return gird;
    }
}