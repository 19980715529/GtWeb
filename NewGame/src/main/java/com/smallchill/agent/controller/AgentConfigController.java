package com.smallchill.agent.controller;

import com.smallchill.agent.intercept.AgentConfigValidator;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
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
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/agentConfig")
public class AgentConfigController  extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/agent/agentconfig/";
    private static String CODE = "agentConfig";
    private static String LIST_SOURCE = "agentConfig.all_list";
    private static String PREFIX = "agentConfig";
    @Resource
    private CommonService commonService;

    @DoControllerLog(name="代理配置")
    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH+"agentConfig.html";
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
        Map agentConfig = commonService.getInfoByOne("agentConfig.one_config", CMap.init().set("id",id));
        mm.put("agentConfig",agentConfig);
        mm.put("code",CODE);
        return BASE_PATH + "agentConfig_edit.html";
    }
    @Json
    @RequestMapping(KEY_UPDATE)
    public AjaxResult update(ModelMap mm){
        String value = HttpKit.getRequest().getParameter("agentConfig.value");
        String id = HttpKit.getRequest().getParameter("agentConfig.id");
        int update = Db.update("QPGameUserDB.dbo.config","id", CMap.init().set("id", id).set("value", value));
        if (update<=0){
            return fail(UPDATE_FAIL_MSG);
        }
        CacheKit.removeAll(SYS_CACHE);
        return success(UPDATE_SUCCESS_MSG);
    }


}
