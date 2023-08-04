package com.smallchill.agent.controller;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/subordinateQuery")
public class SubordinateQueryController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/agent/subordinateQuery/";
    private static String CODE = "subordinateQuery";
    private static String LIST_SOURCE = "subordinateQuery.all_list";

    private static String PREFIX = "subordinateQuery";

    @Resource
    private CommonService commonService;

    @DoControllerLog(name="代理数据汇总")
    @RequestMapping("/")
    public String index(ModelMap mm, @RequestParam(required = false) Integer AgentUserID) {
        mm.put("code", CODE);
        mm.put("AgentUserID",AgentUserID);
        return BASE_PATH+"subordinateQuery.html";
    }

    /**
     * 列表查询
     */
    @Json
    @RequestMapping(KEY_LIST)
    public Object list() {
        Object gird;
        // 解析查询条件
        String where = getRequest().getParameter("where");
        if (where==null || "".equals(where)){
            Map<String, Object> map = new HashMap<>();
            map.put("page",1);
            map.put("records",0);
            map.put("row",new ArrayList<>());
            map.put("total",0);
            return json(map);
        }
        gird = paginateBySelf(LIST_SOURCE);
        return json(gird);
    }
}
