package com.smallchill.proxy.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.game.service.CommonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("/summary")
public class DailySummaryController  extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/proxy/summary/";
    private static String CODE = "summary";
    private static String LIST_SOURCE = "summary.all_list";
    private static String PREFIX = "summary";

    @Resource
    private CommonService commonService;

    @DoControllerLog(name="进入代理每日数据汇")
    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH+"summary.html";
    }

    /**
     * 列表查询
     */
    @Json
    @RequestMapping(KEY_LIST)
    public Object list() {
        Object gird;
        // 解析查询条件
//        gird = paginateBySelf(LIST_SOURCE);
        HashMap<String, Object> map = new HashMap<>();
        map.put("page",0);
        map.put("records",0);
        map.put("rows",new ArrayList<>());
        map.put("total",0);
        return json(map);
    }
}
