package com.smallchill.system.treasure.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.game.service.CommonService;
import com.smallchill.system.treasure.model.AutoAuditConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/autoaudit")
public class AutoAuditConfigController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/modules/platform/autoaudit/";
    private static String CODE = "autoaudit";
    private static String LIST_SOURCE = "autoaudit.all_list";
    private static String PREFIX = "autoaudit";

    @Resource
    private CommonService commonService;

    @DoControllerLog(name="进入自动审核配置界面")
    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH+"autoaudit.html";
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
     进入修改界面
     */
    @RequestMapping(KEY_EDIT+"/{id}")
    @Permission(ADMINISTRATOR)
    public String edit(@PathVariable Integer id, ModelMap mm){
        Object firstBy = Blade.create(AutoAuditConfig.class).findFirstBy("id=#{id}", CMap.init().set("id",id));
        mm.put("autoaudit",firstBy);
        mm.put("code",CODE);
        return BASE_PATH + "autoaudit_edit.html";
    }

    @Json
    @RequestMapping(KEY_UPDATE)
//    @Before(PayChannelValidator.class)
    @Permission(ADMINISTRATOR)
    public AjaxResult update() {
        AutoAuditConfig auto = mapping(PREFIX, AutoAuditConfig.class);
        Blade blade = Blade.create(AutoAuditConfig.class);
        boolean temp = blade.update(auto);
        if (temp) {
            CacheKit.removeAll(SYS_CACHE);
            return success(UPDATE_SUCCESS_MSG);
        } else {
            return error(UPDATE_FAIL_MSG);
        }
    }
}
