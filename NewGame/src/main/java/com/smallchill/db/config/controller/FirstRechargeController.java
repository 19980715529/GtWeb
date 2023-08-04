package com.smallchill.db.config.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.db.config.model.ActiveList;
import com.smallchill.db.config.model.FirstRecharge;
import com.smallchill.game.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @classNameFirstRechargeController
 * @Date 2023/2/25 17:45
 * @Version 1.0
 **/
@Controller
@RequestMapping("/firstrechage")
public class FirstRechargeController extends BaseController implements ConstShiro {

    private static String BASE_PATH = "/db/firstrechage/";
    private static String CODE = "firstrechage";
    private static String LIST_SOURCE = "firstrechage.new_list";
    private static String PREFIX = "firstrechage";
    @Autowired
    private CommonService commonService;
    @DoControllerLog(name="进入充首充配置页面")
    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "firstrechage.html";
    }

    @Json
    @RequestMapping(KEY_LIST)
    public Object list() {
        Object gird = paginate(LIST_SOURCE);
        return gird;
    }

    /**
     * 添加
     */
    @RequestMapping(KEY_ADD)
    @Permission({ ADMINISTRATOR, ADMIN })
    public String add(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "firstrechage_add.html";
    }
    /**
     * 添加保存
     */
    @Json
    @RequestMapping(KEY_SAVE)
    @Permission({ADMINISTRATOR,ADMIN})
    public AjaxResult save(ModelMap mm){
        FirstRecharge firstRecharge = mapping(PREFIX, FirstRecharge.class);
        int i = Blade.create(FirstRecharge.class).saveRtId(firstRecharge);
        mm.put("code",CODE);
        if (i>0){
            CacheKit.removeAll(SYS_CACHE);
            return success(SAVE_SUCCESS_MSG);
        }else {
            return fail(SAVE_FAIL_MSG);
        }
    }
    /**
     * 打开修改界面gameroomitem
     */
    @RequestMapping(KEY_EDIT+"/{id}")
    @Permission({ADMINISTRATOR,ADMIN})
    public String update(@PathVariable Integer id, ModelMap mm){
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",id);
        Map infoByOne = commonService.getInfoByOne("firstrechage.one_firstrechage", map);
        mm.put("firstrechage",infoByOne);
        mm.put("code",CODE);
        return BASE_PATH + "firstrechage_edit.html";
    }
    /**
     * 修改数据
     */
    @Json
    @RequestMapping(KEY_UPDATE)
    @Permission({ADMINISTRATOR,ADMIN})
    public AjaxResult update(ModelMap mm){
        FirstRecharge firstRecharge = mapping(PREFIX, FirstRecharge.class);
        boolean update = Blade.create(FirstRecharge.class).update(firstRecharge);
        mm.put("code",CODE);
        if (!update){
            return fail(UPDATE_FAIL_MSG);
        }
        CacheKit.removeAll(SYS_CACHE);
        return success(UPDATE_SUCCESS_MSG);
    }

    /**
     * 删除
     */
    @Json
    @RequestMapping(KEY_REMOVE)
    @Permission(ADMINISTRATOR)
    public AjaxResult remove(@RequestParam String ids, ModelMap mm) {
        Integer[] Ids = Convert.toIntArray(ids);
        boolean temp = Blade.create(FirstRecharge.class).updateBy("isDel=0", "id IN (#{join(ids)})", CMap.init().set("ids", Ids));
        mm.put("code",CODE);
        if (temp) {
            CacheKit.removeAll(SYS_CACHE);
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }

}
