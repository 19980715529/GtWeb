package com.smallchill.proxy.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.game.service.CommonService;
import com.smallchill.proxy.model.GmRechargeConfig;
import com.smallchill.proxy.model.TransferConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("/gmrechargeconfig")
public class GmRechargeConfigController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/proxy/gmrechargeconfig/";
    private static String CODE = "gmrechargeconfig";
    private static String LIST_SOURCE = "gmrechargeconfig.all_list";
    private static String PREFIX = "gmrechargeconfig";

    @Resource
    private CommonService commonService;

    @DoControllerLog(name="转账快捷配置界面")
    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH+"gmrechargeconfig.html";
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

    @RequestMapping(KEY_EDIT + "/{id}")
    public String edit(@PathVariable Integer id, ModelMap mm) {
        LOGGER.error(id);
        Object tr = commonService.getInfoByOne("gmrechargeconfig.one_config", CMap.init().set("id",id));
        mm.put("model", tr);
        mm.put("code", CODE);
        return BASE_PATH + "gmrechargeconfig_edit.html";
    }

    @Json
//    @Before(DictDataValidator.class)
    @PostMapping(KEY_UPDATE)
    public AjaxResult update(GmRechargeConfig gmRechargeConfig){
        LOGGER.error(JsonKit.toJson(gmRechargeConfig));
        boolean temp = Blade.create(GmRechargeConfig.class).update(gmRechargeConfig);
        if (temp) {
            CacheKit.removeAll(SYS_CACHE);
            return success(UPDATE_SUCCESS_MSG);
        } else {
            return error(UPDATE_FAIL_MSG);
        }
    }
}
