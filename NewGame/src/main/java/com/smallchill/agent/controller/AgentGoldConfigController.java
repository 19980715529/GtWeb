package com.smallchill.agent.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.game.service.CommonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Map;
@Controller
@RequestMapping("/agentGoldConfig")
public class AgentGoldConfigController extends BaseController implements ConstShiro {

    private static String BASE_PATH = "/agent/agentGoldConfig/";
    private static String CODE = "agentGoldConfig";
    private static String LIST_SOURCE = "agentGoldConfig.all_list";
    private static String PREFIX = "agentGoldConfig";

    @Resource
    private CommonService commonService;

    @DoControllerLog(name="代理金币配置")
    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH+"agentGoldConfig.html";
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
     * 打开修改界面gameroomitem
     */
    @RequestMapping(KEY_EDIT+"/{id}")
    @Permission({ADMINISTRATOR,ADMIN})
    public String update(@PathVariable Integer id, ModelMap mm){
        Map agentGoldConfig = commonService.getInfoByOne("agentGoldConfig.one_config", CMap.init().set("InviteNum",id));
        mm.put("agentGoldConfig",agentGoldConfig);
        mm.put("code",CODE);
        return BASE_PATH + "agentGoldConfig_edit.html";
    }
    @Json
    @RequestMapping(KEY_UPDATE)
    public AjaxResult update(ModelMap mm){
        String Award = HttpKit.getRequest().getParameter("agentGoldConfig.Award");
        String InviteNum = HttpKit.getRequest().getParameter("agentGoldConfig.InviteNum");
        int update = Db.update("[QPServerInfoDB].[dbo].[InviteAwardConfig]","InviteNum",
                CMap.init().set("InviteNum", InviteNum).set("Award", Award));
        if (update<=0){
            return fail(UPDATE_FAIL_MSG);
        }
        CacheKit.removeAll(SYS_CACHE);
        return success(UPDATE_SUCCESS_MSG);
    }

}
