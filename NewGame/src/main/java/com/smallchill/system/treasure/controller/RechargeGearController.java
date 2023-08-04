package com.smallchill.system.treasure.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.db.config.model.FirstRecharge;
import com.smallchill.game.service.CommonService;
import com.smallchill.system.treasure.meta.intercept.RechargeGearValidator;
import com.smallchill.system.treasure.model.RechargeGear;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping("/rechargegear")
public class RechargeGearController extends BaseController implements ConstShiro {
    private static String BASE_PATH = "/modules/platform/rechargegear/";
    private static String CODE = "rechargegear";
    private static String LIST_SOURCE = "recharge_gear.all_list";
    private static String PREFIX = "rechargegear";

    @Resource
    private CommonService commonService;

    @DoControllerLog(name="进入渠道界面界面")
    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH+"rechargegear.html";
    }

    /**
     * 列表查询
     */
    @Json
    @RequestMapping(KEY_LIST)
    //@Permission({ ADMINISTRATOR, ADMIN })
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
        Object firstBy = Blade.create(RechargeGear.class).findFirstBy("id=#{id}", CMap.init().set("id",id));
        mm.put("rechargegear",firstBy);
        mm.put("code",CODE);
        return BASE_PATH + "rechargegear_edit.html";
    }

    @DoControllerLog(name="进入渠道添加界面")
    @RequestMapping(KEY_ADD)
    @Permission({ ADMINISTRATOR, ADMIN })
    public String add(ModelMap mm) {
        if(ShiroKit.lacksRole(ADMINISTRATOR)){
            return REDIRECT_UNAUTH;
        }
        mm.put("code", CODE);
        return BASE_PATH+"rechargegear_add.html";
    }

    /**
     * 添加保存
     */
    @Json
    @Before(RechargeGearValidator.class)
    @RequestMapping(KEY_SAVE)
    public AjaxResult save(){
        RechargeGear mapping = mapping(PREFIX,RechargeGear.class);
        Blade blade = Blade.create(RechargeGear.class);
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
    @Before(RechargeGearValidator.class)
    @Permission(ADMINISTRATOR)
    public AjaxResult update() {
        RechargeGear channel = mapping(PREFIX, RechargeGear.class);
        Blade blade = Blade.create(RechargeGear.class);
        boolean temp = blade.update(channel);
        if (temp) {
            CacheKit.removeAll(SYS_CACHE);
            return success(UPDATE_SUCCESS_MSG);
        } else {
            return error(UPDATE_FAIL_MSG);
        }
    }

    /**
     删除
     */
    @Json
    @RequestMapping(KEY_REMOVE)
    @Permission(ADMINISTRATOR)
    public AjaxResult remove(@RequestParam String ids) {
        Integer[] Ids = Convert.toIntArray(ids);
        boolean temp = Blade.create(RechargeGear.class).updateBy("isDel=0", "id IN (#{join(ids)})", CMap.init().set("ids", Ids));
        if (temp) {
            CacheKit.removeAll(SYS_CACHE);
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }
}
