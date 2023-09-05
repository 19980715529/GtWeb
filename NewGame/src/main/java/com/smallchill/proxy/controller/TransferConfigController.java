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
import com.smallchill.proxy.model.TransferConfig;
import com.smallchill.system.model.DictData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/transferconfig")
public class TransferConfigController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/proxy/transferconfig/";
    private static String CODE = "transferconfig";
    private static String LIST_SOURCE = "transferconfig.all_list";
    private static String PREFIX = "transferconfig";

    @Resource
    private CommonService commonService;

    @DoControllerLog(name="转账快捷配置界面")
    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH+"transferconfig.html";
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
        Object tr = commonService.getInfoByOne("transferconfig.one_info", CMap.init().set("id",id));
        mm.put("model", tr);
        mm.put("code", CODE);
        return BASE_PATH + "transferconfig_edit.html";
    }

    @Json
//    @Before(DictDataValidator.class)
    @PostMapping(KEY_UPDATE)
    public AjaxResult update(TransferConfig transferConfig){
        LOGGER.error(JsonKit.toJson(transferConfig));
        boolean temp = Blade.create(TransferConfig.class).update(transferConfig);
        if (temp) {
            CacheKit.removeAll(SYS_CACHE);
            return success(UPDATE_SUCCESS_MSG);
        } else {
            return error(UPDATE_FAIL_MSG);
        }
    }

    @Json
    @RequestMapping("/update/status")
    public AjaxResult update_open(TransferConfig transferConfig){
        LOGGER.error(JsonKit.toJson(getRequest().getParameterMap()));
        boolean temp = Blade.create(TransferConfig.class).update(transferConfig);
        if (temp) {
            CacheKit.removeAll(SYS_CACHE);
            return success(UPDATE_SUCCESS_MSG);
        } else {
            return error(UPDATE_FAIL_MSG);
        }
    }
}
