package com.smallchill.agent.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.common.vo.ShiroUser;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.game.service.CommonService;
import com.smallchill.system.model.UserPack;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/agentSummary")
public class AgentSummaryController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/agent/agentSummary/";
    private static String CODE = "agentSummary";
    private static String LIST_SOURCE = "agentSummary.all_list";
    private static String PREFIX = "agentSummary";

    @Resource
    private CommonService commonService;

    @DoControllerLog(name="代理数据汇总")
    @RequestMapping("/")
    public String index(ModelMap mm) {
        ShiroUser user = ShiroKit.getUser();
        Integer id =(Integer) user.getId();
        // 查询包id
        Blade blade = Blade.create(UserPack.class);
        UserPack pack = blade.findFirstBy("uid=#{uid}", CMap.init().set("uid", id));
        if (pack!=null){
            String clientType = pack.getClientType();
            Integer[] ids = Convert.toIntArray(clientType);
            mm.put("clientType", ids[0]);
        }else {
            mm.put("clientType", -9);
        }
        mm.put("code", CODE);
        return BASE_PATH+"agentSummary.html";
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
        return json(gird);
    }

    /**
     * 代理数据汇总
     */
    @Json
    @RequestMapping("/summary")
    public AjaxResult summary(){
        Map<String, Object> map = new HashMap<>();
        // 汇总数据
        Map totalInfo = commonService.getInfoByOne("agentSummary.summary_data", null);
        map.put("totalInfo",totalInfo);
        // 每日数据
        Map toDayInfo = commonService.getInfoByOne("agentSummary.summary_today", null);
        map.put("toDayInfo",toDayInfo);
        return json(map);
    };

    @Json
    @PostMapping("/query")
    public AjaxResult query(@RequestBody(required = false) Map<String,Integer> param){
        LOGGER.error(param);
        Map map = commonService.getInfoByOne("agentSummary.query_summary", param);
        return json(map);
    }

}
