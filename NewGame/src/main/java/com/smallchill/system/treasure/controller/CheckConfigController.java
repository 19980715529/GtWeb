package com.smallchill.system.treasure.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.game.service.CommonService;
import com.smallchill.system.treasure.model.CheckConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/checkconfig")
public class CheckConfigController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/modules/platform/checkconfig/";
    private static String CODE = "checkconfig";
    private static String LIST_SOURCE = "checkconfig.all_list";
    private static String PREFIX = "checkconfig";
    @Resource
    private CommonService commonService;

    @DoControllerLog(name="进入签到配置界面")
    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH+"checkconfig.html";
    }

    /**
     * 列表查询
     */
    @Json
    @RequestMapping(KEY_LIST)
    //@Permission({ ADMINISTRATOR, ADMIN })
    public Object list() {
        Object gird = new Object();
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
        Map infoByOne = commonService.getInfoByOne("checkconfig.check_one", CMap.init().set("id", id));
        mm.put("checkconfig",infoByOne);
        mm.put("code",CODE);
        return BASE_PATH + "checkconfig_edit.html";
    }

//    @DoControllerLog(name="进入渠道添加界面")
    @RequestMapping(KEY_ADD)
    @Permission({ ADMINISTRATOR, ADMIN })
    public String add(ModelMap mm) {
        if(ShiroKit.lacksRole(ADMINISTRATOR)){
            return REDIRECT_UNAUTH;
        }
        mm.put("code", CODE);
        return BASE_PATH+"checkconfig_add.html";
    }

    /**
     * 添加保存
     */
    @Json
    @RequestMapping(KEY_SAVE)
    public AjaxResult save(){
        CheckConfig mapping = mapping(PREFIX, CheckConfig.class);
        Blade blade = Blade.create(CheckConfig.class);
        boolean temp = blade.save(mapping);
        if (temp) {
            CacheKit.removeAll(SYS_CACHE);
            return success(SAVE_SUCCESS_MSG);
        } else {
            return error(SAVE_FAIL_MSG);
        }
    }

    @Json
    @RequestMapping(KEY_UPDATE)
    @Permission(ADMINISTRATOR)
    public AjaxResult update() {
        CheckConfig channel = mapping(PREFIX, CheckConfig.class);
        Blade blade = Blade.create(CheckConfig.class);
        boolean temp = blade.update(channel);
        if (temp) {
            CacheKit.removeAll(SYS_CACHE);
            return success(UPDATE_SUCCESS_MSG);
        } else {
            return error(UPDATE_FAIL_MSG);
        }
    }
    /**
     *删除
     */
    @Json
    @RequestMapping(KEY_REMOVE)
    @Permission(ADMINISTRATOR)
    public AjaxResult remove(@RequestParam String ids) {
        int temp = Blade.create(CheckConfig.class).deleteByIds(ids);
        if (temp > 0) {
            CacheKit.removeAll(SYS_CACHE);
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }
}
